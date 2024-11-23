package org.example.DAO;

import org.example.Exception.DatabaseException;
import org.example.Model.MetaReducao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.example.DAO.CriaConexao.pegarConexao;

public class MetaReducaoDAO {

    // Criar nova meta de redução
    public boolean criarMeta(MetaReducao metaReducao) throws DatabaseException {
        String sql = "INSERT INTO tb_meta_reducao (meta_emissao, progresso_atual, nome, descricao) VALUES (?, ?, ?, ?)";
        try (Connection conn = pegarConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, metaReducao.getMetaEmissao());
            stmt.setDouble(2, metaReducao.getProgressoAtual());
            stmt.setString(3, metaReducao.getNome());
            stmt.setString(4, metaReducao.getDescricao());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao criar a meta de redução.");
        }
    }



    // Listar todas as metas de redução
    public List<MetaReducao> listarTodasMetas() throws DatabaseException {
        List<MetaReducao> metas = new ArrayList<>();
        String sql = "SELECT * FROM tb_meta_reducao";

        try (Connection connection = pegarConexao();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                MetaReducao meta = new MetaReducao();
                meta.setId(rs.getInt("id_meta"));
                meta.setMetaEmissao(rs.getDouble("meta_emissao"));
                meta.setProgressoAtual(rs.getDouble("progresso_atual"));
                meta.setNome(rs.getString("nome"));
                meta.setDescricao(rs.getString("descricao"));
                metas.add(meta);
            }

        } catch (SQLException e) {
            throw new DatabaseException("Erro ao listar as metas de redução.");
        }
        return metas;
    }


    // Método para atualizar uma meta de redução
    public void atualizarMeta(MetaReducao meta) throws DatabaseException {
        // Verifique os valores antes de executar a query
        if (meta.getNome() == null || meta.getDescricao() == null) {
            throw new DatabaseException("Nome ou descrição não podem ser nulos no banco de dados.");
        }

        System.out.println("Camada DAO - Nome: " + meta.getNome());
        System.out.println("Camada DAO - Descrição: " + meta.getDescricao());

        String sql = "UPDATE tb_meta_reducao " +
                "SET progresso_atual = ?, meta_emissao = ?, nome = ?, descricao = ? " +
                "WHERE id_meta = ?";

        try (Connection conn = pegarConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            conn.setAutoCommit(false); // Controle manual da transação

            stmt.setDouble(1, meta.getProgressoAtual());
            stmt.setDouble(2, meta.getMetaEmissao());
            stmt.setString(3, meta.getNome());
            stmt.setString(4, meta.getDescricao());
            stmt.setInt(5, meta.getId());

            System.out.println("SQL Nome: " + meta.getNome());
            System.out.println("SQL Descrição: " + meta.getDescricao());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                conn.commit();
                System.out.println("Meta atualizada com sucesso!");
            } else {
                conn.rollback();
                System.out.println("Nenhuma linha foi atualizada.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Erro ao atualizar meta: " + e.getMessage());
        }
    }





    // Excluir meta de redução
    public boolean excluirMeta(int id) throws DatabaseException {
        String sql = "DELETE FROM tb_meta_reducao WHERE id_meta = ?";
        try (Connection conn = pegarConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Meta de redução excluída com sucesso.");
            return true;
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao excluir a meta de redução.");
        }
    }

    // Buscar meta por id
    public MetaReducao buscarMetaPorId(int idMeta) throws DatabaseException {
        MetaReducao meta = null;
        String sql = "SELECT * FROM tb_meta_reducao WHERE id_meta = ?";
        try (Connection conn = pegarConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idMeta);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                meta = new MetaReducao();
                meta.setId(rs.getInt("id_meta"));
                meta.setMetaEmissao(rs.getDouble("meta_emissao"));
                meta.setProgressoAtual(rs.getDouble("progresso_atual"));
                meta.setNome(rs.getString("nome"));
                meta.setDescricao(rs.getString("descricao"));
            }

        } catch (SQLException e) {
            throw new DatabaseException("Erro ao buscar a meta de redução pelo ID.");
        }
        return meta;
    }


}



