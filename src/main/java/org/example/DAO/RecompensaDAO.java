package org.example.DAO;

import org.example.Exception.DatabaseException;
import org.example.Model.Recompensa;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.example.DAO.CriaConexao.pegarConexao;

public class RecompensaDAO {

    // Método para adicionar uma nova recompensa
    public boolean criarRecompensa(String nome, int pontosNecessarios, String descricao) throws DatabaseException {
        String sql = "INSERT INTO tb_recompensa (nome, pontos_necessarios, descricao) VALUES (?, ?, ?)";

        try (Connection conn = pegarConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setInt(2, pontosNecessarios);
            stmt.setString(3, descricao); // Passando a descrição para o banco

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Erro ao criar recompensa: " + e.getMessage());
        }
    }


    // Método para listar todas as recompensas
    public List<Recompensa> listarRecompensas() throws DatabaseException {
        List<Recompensa> recompensas = new ArrayList<>();
        String sql = "SELECT * FROM tb_recompensa";

        try (Connection connection = pegarConexao();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet resultSet = stmt.executeQuery()) {

            while (resultSet.next()) {
                Recompensa recompensa = new Recompensa(
                        resultSet.getInt("id_recompensa"),
                        resultSet.getString("nome"),
                        resultSet.getInt("pontos_necessarios"),
                        resultSet.getString("descricao") // Inclua a descrição aqui
                );
                recompensas.add(recompensa);
            }

        } catch (SQLException e) {
            throw new DatabaseException("Erro ao listar recompensas: " + e.getMessage());
        }
        return recompensas;
    }

    // Método para atualizar uma recompensa
    public boolean atualizarRecompensa(Recompensa recompensa) throws DatabaseException {
        String sql = "UPDATE tb_recompensa SET nome = ?, pontos_necessarios = ?, descricao = ? WHERE id_recompensa = ?";

        try (Connection connection = pegarConexao();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, recompensa.getNome());
            stmt.setInt(2, recompensa.getPontosNecessarios());
            stmt.setString(3, recompensa.getDescricao()); // Adicione a descrição aqui
            stmt.setInt(4, recompensa.getId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // Retorna true se a atualização foi bem-sucedida
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao atualizar recompensa: " + e.getMessage());
        }
    }


    // Método para excluir uma recompensa
    public boolean excluirRecompensa(int id) throws DatabaseException {
        String sql = "DELETE FROM tb_recompensa WHERE id_recompensa = ?";

        try (Connection connection = pegarConexao();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao excluir recompensa: " + e.getMessage());
        }
    }

    // Método para buscar uma recompensa por ID
    public Recompensa buscarRecompensaPorId(int id) throws DatabaseException {
        String sql = "SELECT * FROM tb_recompensa WHERE id_recompensa = ?";

        try (Connection connection = pegarConexao();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                return new Recompensa(
                        resultSet.getInt("id_recompensa"),
                        resultSet.getString("nome"),
                        resultSet.getInt("pontos_necessarios"),
                        resultSet.getString("descricao") // Inclua a descrição aqui
                );
            }

        } catch (SQLException e) {
            throw new DatabaseException("Erro ao buscar recompensa por ID: " + e.getMessage());
        }
        return null; // Retorna null se não encontrado
    }

    // Método para verificar se o usuário tem pontos suficientes para resgatar a recompensa
    public boolean podeResgatar(int idRecompensa, int pontosUsuario) throws DatabaseException {
        String sql = "SELECT pontos_necessarios FROM tb_recompensa WHERE id_recompensa = ?";

        try (Connection connection = pegarConexao();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, idRecompensa);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                int pontosNecessarios = resultSet.getInt("pontos_necessarios");
                return pontosUsuario >= pontosNecessarios;
            } else {
                // Lançar exceção se a recompensa não for encontrada
                throw new DatabaseException("Recompensa não encontrada com o ID: " + idRecompensa);
            }
        } catch (SQLException e) {
            // Lançar DatabaseException em caso de erro de SQL
            throw new DatabaseException("Erro ao verificar resgaste de recompensa: " + e.getMessage());
        }
    }
}
