package org.example.Service;

import org.example.DAO.UsuarioDAO;
import org.example.Exception.DatabaseException;
import org.example.Model.Usuario;

import java.util.List;

public class UsuarioService {

    private UsuarioDAO usuarioDAO;

    public UsuarioService() {
        usuarioDAO = new UsuarioDAO();
    }

    // método para criar um novo usuário
    public boolean criarUsuario(Usuario usuario) {
        try {
            return usuarioDAO.criarUser(usuario);  // Usando salvarUsuario ao invés de criarUser
        } catch (DatabaseException e) {
            System.out.println("Erro ao criar usuário: " + e.getMessage());
            return false;
        }
    }

    /* método para salvar usuário com validações
    public boolean salvarUsuario(Usuario usuario) {
        // Verificar se o nome está vazio
        if (usuario.getNome() == null || usuario.getNome().trim().isEmpty()) {
            System.out.println("Nome do usuário não pode ser vazio.");
            return false;
        }

        // Verificar se o email está vazio
        if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
            System.out.println("E-mail do usuário não pode ser vazio");
            return false;
        }

        // Verificar se o email é válido
        if (!isEmailValido(usuario.getEmail())) {
            System.out.println("E-mail inválido");
            return false;
        }

        try {
            // Verifica se já existe um usuário com o mesmo e-mail
            if (usuarioDAO.buscarPorEmail(usuario.getEmail()) != null) {
                System.out.println("Já existe um email cadastrado");
                return false;
            }

            // Se todas as validações passarem, salvar o usuário no banco
            return usuarioDAO.salvarUsuario(usuario);

        } catch (DatabaseException e) {
            System.out.println("Erro ao salvar usuário: " + e.getMessage());
            return false;
        }
    }*/

    /* Método para validar o formato do e-mail
    private boolean isEmailValido(String email) {
        // Verifica se o email contém um "@" e um ponto (básico)
        return email.contains("@") && email.contains(".");
    } */

    // Método para listar todos os usuários
    public List<Usuario> listarUsuarios() {
        try {
            return usuarioDAO.listarUsuario();
        } catch (DatabaseException e) {
            System.out.println("Erro ao listar usuários: " + e.getMessage());
            return null; // Retorna null em caso de erro
        }
    }

    // Método para atualizar um usuário
    public boolean atualizarUsuario(Usuario usuario) {
        try {
            return usuarioDAO.atualizarUsuario(usuario);
        } catch (DatabaseException e) {
            System.out.println("Erro ao atualizar usuário: " + e.getMessage());
            return false;
        }
    }

    // Método para excluir um usuário
    public boolean excluirUsuario(int id) {
        try {
            return usuarioDAO.excluirUsuario(id);
        } catch (DatabaseException e) {
            System.out.println("Erro ao excluir usuário: " + e.getMessage());
            return false;
        }
    }
}
