package org.example.DAO;

import org.example.Exception.DatabaseException;
import org.example.Model.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    // criar usuário
    public boolean criarUser(Usuario usuario) throws DatabaseException {
        String sql = "INSERT INTO tb_usuario (nome,  senha) VALUES (?, ?)";  // Incluindo senha
        try (Connection connection = CriaConexao.pegarConexao();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getSenha());// Definindo a senha
            stmt.executeUpdate();
            System.out.println("Usuário criado com sucesso.");
            return true;

        } catch (SQLException e) {
            throw new DatabaseException("Erro ao criar o usuário: " + e.getMessage());
        }
    }

    // listar usuários
    public List<Usuario> listarUsuario() throws DatabaseException {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM tb_usuario";

        try (Connection connection = CriaConexao.pegarConexao();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(resultSet.getInt("id_user"));
                usuario.setNome(resultSet.getString("nome"));
                usuario.setSenha(resultSet.getString("senha"));  // Recuperando a senha
                usuarios.add(usuario);
            }

        } catch (SQLException e) {
            throw new DatabaseException("Erro ao listar os usuários: " + e.getMessage());
        }
        return usuarios;
    }


    // atualizar usuário
    public boolean atualizarUsuario(Usuario usuario) throws DatabaseException {
        String sql = "UPDATE tb_usuario SET nome = ?, senha = ? WHERE id_user = ?";

        try (Connection conn = CriaConexao.pegarConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getSenha());
            stmt.setInt(3, usuario.getId());
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            throw new DatabaseException("Erro ao atualizar o usuário: " + e.getMessage());
        }
    }


    // excluir usuário
    public boolean excluirUsuario(Integer id) throws DatabaseException {
        String sql = "DELETE FROM tb_usuario WHERE id_user = ?";

        try (Connection conn = CriaConexao.pegarConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            throw new DatabaseException("Erro ao excluir o usuário: " + e.getMessage());
        }
    }


    /* buscar usuário por email
    public Usuario buscarPorEmail(String email) throws DatabaseException {
        String sql = "SELECT * FROM tb_usuario WHERE email = ?";

        try (Connection conn = CriaConexao.pegarConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Usuario(
                        rs.getInt("id_user"),
                        rs.getString("nome"),
                        rs.getString("email")
                );
            }
            return null;

        } catch (SQLException e) {
            throw new DatabaseException("Erro ao buscar o usuário por email: " + e.getMessage());
        }
    } */



}
