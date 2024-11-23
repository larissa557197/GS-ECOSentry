package org.example.DAO;

import org.example.Exception.CategoriaAtividadeException;
import org.example.Exception.CategoriaAtividadeInvalidaException;
import org.example.Exception.CategoriaAtividadeNaoEncontradaException;
import org.example.Exception.DatabaseException;
import org.example.Model.CategoriaAtividade;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoriaAtividadeDAO {



    public CategoriaAtividadeDAO() {
        // Inicializando DataSource corretamente
    }

    // Método para criar uma nova categoria
    public boolean criarCategoria(CategoriaAtividade categoria) throws CategoriaAtividadeException {
        if (categoria == null || categoria.getNome() == null || categoria.getNome().isEmpty()) {
            throw new CategoriaAtividadeInvalidaException("O nome da categoria não pode ser nulo ou vazio.");
        }

        String sql = "INSERT INTO tb_categoria_atividade (nome) VALUES (?)";
        try (Connection connection = CriaConexao.pegarConexao();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, categoria.getNome());
            preparedStatement.executeUpdate();
            return true;

        } catch (SQLException e) {
            throw new CategoriaAtividadeException("Erro ao criar categoria: " + categoria.getNome());
        }
    }


    // Método para listar todas as categorias
    public List<CategoriaAtividade> listarCategorias() throws DatabaseException {
        List<CategoriaAtividade> categorias = new ArrayList<>();
        String sql = "SELECT * FROM tb_categoria_atividade";

        try (Connection connection = CriaConexao.pegarConexao();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                CategoriaAtividade categoria = new CategoriaAtividade();
                categoria.setId(resultSet.getInt("id"));
                categoria.setNome(resultSet.getString("nome"));
                categorias.add(categoria);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao listar categorias");
        }
        return categorias;
    }


    // Método para salvar uma nova categoria
    public boolean salvarCategoria(CategoriaAtividade categoria) throws DatabaseException {
        String sql = "INSERT INTO tb_categoria_atividade (nome) VALUES (?)";
        try (Connection connection = CriaConexao.pegarConexao();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, categoria.getNome());
            preparedStatement.executeUpdate(); // Executa a atualização
            return true;  // Sucesso
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao salvar categoria");
        }
    }

    // Método para verificar se uma categoria existe pelo ID
    public boolean categoriaExiste(int id) throws DatabaseException {
        String sql = "SELECT COUNT(*) FROM tb_categoria_atividade WHERE id = ?";
        try (Connection conn = CriaConexao.pegarConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // Retorna true se a categoria existir
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao verificar se categoria existe");
        }
        return false; // Categoria não encontrada
    }

    public boolean excluirCategoria(Integer idCategoria) throws DatabaseException {
        String deleteEmissaoSql = "DELETE FROM tb_emissao WHERE id_categoria = ?";
        String deleteCategoriaSql = "DELETE FROM tb_categoria_atividade WHERE id = ?";

        try (Connection connection = CriaConexao.pegarConexao()) {
            // Iniciar transação
            connection.setAutoCommit(false);

            // Excluir registros dependentes na tabela filha
            try (PreparedStatement deleteEmissaoStmt = connection.prepareStatement(deleteEmissaoSql)) {
                deleteEmissaoStmt.setInt(1, idCategoria);
                deleteEmissaoStmt.executeUpdate();
            }

            // Excluir categoria
            try (PreparedStatement deleteCategoriaStmt = connection.prepareStatement(deleteCategoriaSql)) {
                deleteCategoriaStmt.setInt(1, idCategoria);
                int rowsAffected = deleteCategoriaStmt.executeUpdate();

                if (rowsAffected == 0) {
                    // Caso não exclua nenhum registro (categoria não encontrada)
                    return false;
                }

                // Comitar a transação
                connection.commit();
                return true;
            } catch (SQLException e) {
                connection.rollback();  // Reverter em caso de erro
                throw new DatabaseException("Erro ao excluir categoria: " + e.getMessage());
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao excluir categoria: " + e.getMessage());
        }
    }



    // Método para atualizar uma categoria
    public boolean atualizarCategoria(CategoriaAtividade categoria) throws DatabaseException {
        String sql = "UPDATE tb_categoria_atividade SET nome = ? WHERE id = ?";
        try (Connection connection = CriaConexao.pegarConexao();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, categoria.getNome());
            preparedStatement.setInt(2, categoria.getId());
            preparedStatement.executeUpdate(); // Executa a atualização
            return true;  // Sucesso
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao atualizar categoria");
        }
    }

    // Método para buscar uma categoria por ID
    public CategoriaAtividade buscarCategoriaPorId(Integer idCategoria) throws CategoriaAtividadeNaoEncontradaException, CategoriaAtividadeException {
        if (idCategoria == null) {
            throw new CategoriaAtividadeInvalidaException("O ID da categoria não pode ser nulo.");
        }

        String sql = "SELECT * FROM tb_categoria_atividade WHERE id = ?";
        try (Connection connection = CriaConexao.pegarConexao();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, idCategoria);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    CategoriaAtividade categoria = new CategoriaAtividade();
                    categoria.setId(resultSet.getInt("id"));
                    categoria.setNome(resultSet.getString("nome"));
                    return categoria;
                } else {
                    throw new CategoriaAtividadeNaoEncontradaException("Categoria não encontrada com ID: " + idCategoria);
                }
            }
        } catch (SQLException e) {
            throw new CategoriaAtividadeException("Erro ao buscar categoria com ID: " + idCategoria);
        }
    }


}