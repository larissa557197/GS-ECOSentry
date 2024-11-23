package org.example.DAO;

import org.example.Exception.DatabaseException;
import org.example.Exception.RelatorioNotFoundException;
import org.example.Model.Relatorio;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RelatorioDAO {

    private final CriaConexao criarConexao;

    public RelatorioDAO() {
        this.criarConexao = new CriaConexao();
    }

    public boolean inserirRelatorio(Relatorio relatorio) throws DatabaseException {
        String sql = "INSERT INTO tb_relatorios (descricao, data_geracao, conteudo, categoria, emissao_total) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = criarConexao.pegarConexao();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, relatorio.getDescricao());
            stmt.setDate(2, java.sql.Date.valueOf(relatorio.getDataGeracao()));
            stmt.setString(3, relatorio.getConteudo());
            stmt.setString(4, relatorio.getCategoria());
            stmt.setDouble(5, relatorio.getEmissaoTotal());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DatabaseException("Erro ao inserir o relatório: " + e.getMessage());
        }
    }


    public List<Relatorio> obterRelatorios() throws DatabaseException {
        List<Relatorio> relatorios = new ArrayList<>();
        String query = "SELECT id_relatorio, descricao, data_geracao, conteudo, categoria, emissao_total FROM tb_relatorios";

        try (Connection connection = criarConexao.pegarConexao();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int idRelatorio = rs.getInt("id_relatorio");
                String descricao = rs.getString("descricao");
                LocalDate dataGeracao = rs.getDate("data_geracao").toLocalDate();
                String conteudo = rs.getString("conteudo");
                String categoria = rs.getString("categoria");
                double emissaoTotal = rs.getDouble("emissao_total");

                relatorios.add(new Relatorio(idRelatorio, descricao, dataGeracao, conteudo, categoria, emissaoTotal));
            }
            return relatorios;

        } catch (SQLException e) {
            throw new DatabaseException("Erro ao obter relatórios: " + e.getMessage());
        }
    }


    public Relatorio obterRelatorioPorId(int idRelatorio) throws RelatorioNotFoundException, DatabaseException {
        String sql = "SELECT * FROM tb_relatorios WHERE id_relatorio = ?";
        try (Connection connection = criarConexao.pegarConexao();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, idRelatorio);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Aqui, é importante verificar se as colunas existem e têm valores válidos
                    // Supondo que o construtor do Relatorio lide com valores null corretamente
                    return new Relatorio(
                            rs.getInt("id_relatorio"),
                            rs.getString("descricao"),
                            rs.getDate("data_geracao") != null ? rs.getDate("data_geracao").toLocalDate() : null,
                            rs.getString("conteudo"),
                            rs.getString("categoria"),
                            rs.getDouble("emissao_total")
                    );
                } else {
                    throw new RelatorioNotFoundException("Relatório com ID " + idRelatorio + " não foi encontrado.");
                }
            }
        } catch (SQLException e) {
            // Log do erro real, caso necessário
            throw new DatabaseException("Erro ao acessar o banco de dados: " + e.getMessage());
        }
    }



    public boolean atualizarRelatorio(Relatorio relatorio) throws DatabaseException {
        String sql = "UPDATE tb_relatorios SET descricao = ?, data_geracao = ?, conteudo = ?, categoria = ?, emissao_total = ? WHERE id_relatorio = ?";
        try (Connection connection = criarConexao.pegarConexao();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, relatorio.getDescricao());
            stmt.setDate(2, java.sql.Date.valueOf(relatorio.getDataGeracao()));
            stmt.setString(3, relatorio.getConteudo());
            stmt.setString(4, relatorio.getCategoria());
            stmt.setDouble(5, relatorio.getEmissaoTotal());
            stmt.setInt(6, relatorio.getIdRelatorio());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DatabaseException("Erro ao atualizar o relatório com ID " + relatorio.getIdRelatorio() + ": " + e.getMessage());
        }
    }


    public boolean deletarRelatorio(int id) throws DatabaseException {
        String sql = "DELETE FROM tb_relatorios WHERE id_relatorio = ?";
        try (Connection connection = criarConexao.pegarConexao();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DatabaseException("Erro ao deletar o relatório com ID " + id + ": " + e.getMessage());
        }
    }


    public List<Relatorio> buscarRelatoriosPorCategoria(String categoria) throws DatabaseException {
        String sql = "SELECT * FROM tb_relatorios WHERE categoria = ?";
        List<Relatorio> relatorios = new ArrayList<>();

        try (Connection connection = criarConexao.pegarConexao();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, categoria);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Relatorio relatorio = new Relatorio(
                            rs.getInt("id_relatorio"),
                            rs.getString("descricao"),
                            rs.getDate("data_geracao").toLocalDate(),
                            rs.getString("conteudo"),
                            rs.getString("categoria"),
                            rs.getDouble("emissao_total")
                    );
                    relatorios.add(relatorio);
                }
            }
            return relatorios;

        } catch (SQLException e) {
            throw new DatabaseException("Erro ao buscar relatórios pela categoria '" + categoria + "': " + e.getMessage());
        }
    }

}
