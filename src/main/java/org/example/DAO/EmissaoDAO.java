package org.example.DAO;

import org.example.Exception.DatabaseException;
import org.example.Model.Emissao;
import org.example.Model.CategoriaAtividade;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.example.DAO.CriaConexao.conn;
import static org.example.DAO.CriaConexao.pegarConexao;

public class EmissaoDAO {

    private CategoriaAtividadeDAO categoriaAtividadeDAO;

    // Construtor da classe, inicializando o DAO de CategoriaAtividade
    public EmissaoDAO() {
        this.categoriaAtividadeDAO = new CategoriaAtividadeDAO();
    }

    // Método para criar uma nova Emissão

    public void criarEmissao(Emissao emissao) throws DatabaseException {
        try {
            if (conn == null || conn.isClosed()) {
                conn = CriaConexao.pegarConexao();
            }

            String sql = "INSERT INTO tb_emissao (id_emissao, nome, id_categoria, data_emissao, valor_emissao) " +
                    "VALUES (seq_emissao.NEXTVAL, ?, ?, ?, ?)";

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, emissao.getNome());
                stmt.setInt(2, emissao.getIdCategoria());
                stmt.setDate(3, emissao.getDataEmissao() != null ? java.sql.Date.valueOf(emissao.getDataEmissao()) : null);
                stmt.setDouble(4, emissao.getValorEmissao());

                int linhasAfetadas = stmt.executeUpdate();
                if (linhasAfetadas > 0) {
                    System.out.println("Emissão criada com sucesso!");
                } else {
                    throw new DatabaseException("Erro: Nenhuma linha foi afetada ao criar a emissão.");
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao conectar ou executar a operação no banco de dados: " + e.getMessage());
        }
    }


    // Método para listar todas as emissões com suas categorias
    public List<Emissao> listarEmissoesComCategorias() throws DatabaseException {
        List<Emissao> emissoes = new ArrayList<>();
        String sql = "SELECT e.id_emissao, e.nome, e.valor_emissao, e.data_emissao, c.id AS categoria_id, c.nome AS categoria_nome " +
                "FROM tb_emissao e JOIN tb_categoria_atividade c ON e.id_categoria = c.id";

        try (Connection conn = pegarConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Emissao emissao = new Emissao();
                emissao.setId(rs.getInt("id_emissao"));
                emissao.setNome(rs.getString("nome"));
                emissao.setValorEmissao(rs.getDouble("valor_emissao"));

                Date dataEmissao = rs.getDate("data_emissao");
                if (dataEmissao != null) {
                    emissao.setDataEmissao(dataEmissao.toLocalDate());
                }

                CategoriaAtividade categoria = new CategoriaAtividade();
                categoria.setId(rs.getInt("categoria_id"));
                categoria.setNome(rs.getString("categoria_nome"));
                emissao.setCategoriaAtividade(categoria);

                emissoes.add(emissao);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao listar emissões com categorias: " + e.getMessage());
        }

        return emissoes;
    }


    // Método para salvar uma Emissão
    public boolean salvarEmissao(Emissao emissao) throws DatabaseException {
        if (!categoriaAtividadeDAO.categoriaExiste(emissao.getCategoriaAtividade().getId())) {
            throw new DatabaseException("Categoria não encontrada com id: " + emissao.getCategoriaAtividade().getId());
        }

        String sql = "INSERT INTO tb_emissao (nome, valor_emissao, id_categoria, data_emissao) VALUES (?, ?, ?, ?)";

        try (Connection connection = pegarConexao();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, emissao.getNome());
            stmt.setDouble(2, emissao.getValorEmissao());
            stmt.setInt(3, emissao.getCategoriaAtividade().getId());

            if (emissao.getDataEmissao() != null) {
                stmt.setDate(4, Date.valueOf(emissao.getDataEmissao()));
            } else {
                stmt.setDate(4, null);
            }

            stmt.executeUpdate();
            System.out.println("Emissão criada com sucesso.");
            return true;
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao salvar emissão: " + e.getMessage());
        }
    }


    // Método para atualizar uma Emissão
    public boolean atualizarEmissao(int id, Emissao emissao) throws DatabaseException {
        if (id <= 0) {
            throw new DatabaseException("ID inválido. Deve ser maior que zero.");
        }
        if (emissao == null) {
            throw new DatabaseException("Objeto Emissao não pode ser nulo.");
        }
        if (emissao.getNome() == null || emissao.getNome().isEmpty()) {
            throw new DatabaseException("Nome da emissão não pode ser vazio ou nulo.");
        }
        if (emissao.getIdCategoria() <= 0) {
            throw new DatabaseException("ID da categoria deve ser maior que zero.");
        }

        if (emissao.getValorEmissao() < 0) {
            throw new DatabaseException("Valor da emissão não pode ser negativo.");
        }

        String sql = "UPDATE tb_emissao SET nome = ?, id_categoria = ?, data_emissao = ?, valor_emissao = ? WHERE id_emissao = ?";
        try (PreparedStatement stmt = CriaConexao.pegarConexao().prepareStatement(sql)) {
            stmt.setString(1, emissao.getNome());
            stmt.setInt(2, emissao.getIdCategoria());
            stmt.setDate(3, java.sql.Date.valueOf(emissao.getDataEmissao()));
            stmt.setDouble(4, emissao.getValorEmissao());
            stmt.setInt(5, id);

            boolean sucesso = stmt.executeUpdate() > 0;

            if (!sucesso) {
                throw new DatabaseException("Nenhuma linha foi atualizada. Verifique o ID e os dados.");
            }

            return sucesso;
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao atualizar emissão: " + e.getMessage());
        }
    }



    // Método para excluir uma Emissão
    public boolean excluirEmissao(Integer id) throws DatabaseException {
        String sql = "DELETE FROM tb_emissao WHERE id_emissao = ?";

        try (Connection conn = pegarConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id); // Define o ID da emissão a ser excluída
            int result = stmt.executeUpdate(); // Executa a exclusão, mas não verifica o número de linhas afetadas

            // Se a exceção SQLException não for lançada, o item foi excluído (ou não existia).
            // Aqui podemos retornar true, pois não houve erro, mesmo que não tenha sido excluído nada.
            return result > 0; // Caso `result` seja 0, significa que nenhum item foi excluído, ou seja, não havia a emissão.

        } catch (SQLException e) {
            throw new DatabaseException("Erro ao excluir a emissão."); // Lança a exceção com o motivo
        }
    }

    // Método para listar todas as emissões (simples)
    public List<Emissao> listarEmissoes() throws DatabaseException {
        List<Emissao> emissoes = new ArrayList<>();
        String sql = "SELECT * FROM tb_emissao";

        try (Connection conn = pegarConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Emissao emissao = new Emissao(
                        rs.getInt("id_emissao"),
                        rs.getString("nome"),
                        rs.getDouble("valor_emissao"),
                        rs.getDate("data_emissao").toLocalDate()
                );
                emissoes.add(emissao);
            }

        } catch (SQLException e) {
            throw new DatabaseException("Erro ao listar as emissões."); // Lança a exceção com o motivo
        }
        return emissoes;
    }


    // Método para verificar se uma categoria existe pelo ID
    public boolean categoriaExiste(Integer id) throws DatabaseException {
        String sql = "SELECT COUNT(*) FROM tb_categoria_atividade WHERE id = ?";

        try (Connection conn = pegarConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id); // Define o ID da categoria a ser verificado
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // Se > 0, a categoria existe
                }
            }

        } catch (SQLException e) {
            throw new DatabaseException("Erro ao verificar se a categoria existe."); // Lança a exceção com o motivo
        }
        return false; // Caso a categoria não exista, retorna false
    }

    public boolean idExiste(int id){
        String sql = "SELECT COUNT(*) FROM tb_emissao WHERE id_emissao = ?";
        try (Connection conn = pegarConexao();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
