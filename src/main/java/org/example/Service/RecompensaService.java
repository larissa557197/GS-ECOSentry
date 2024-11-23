package org.example.Service;

import org.example.DAO.RecompensaDAO;
import org.example.Exception.DatabaseException;
import org.example.Model.Recompensa;

import java.util.List;

public class RecompensaService {

    private final RecompensaDAO recompensaDAO;

    public RecompensaService() {
        this.recompensaDAO = new RecompensaDAO();  // Instanciando o DAO
    }

    // Método para criar uma nova recompensa
    public boolean criarRecompensa(String nome, int pontosNecessarios, String descricao) throws DatabaseException {
        // Aqui você pode colocar regras de negócio, como verificar se os pontos são positivos, se o nome não está vazio, etc
        if (nome == null || nome.trim().isEmpty()) {
            throw new DatabaseException("Nome da recompensa não pode ser vazio.");
        }
        if (pontosNecessarios <= 0) {
            throw new DatabaseException("Os pontos necessários devem ser positivos.");
        }

        // Chama o método do DAO para criar a recompensa no banco de dados
        return recompensaDAO.criarRecompensa(nome, pontosNecessarios, descricao);
    }

    // Método para listar todas as recompensas
    public List<Recompensa> listarRecompensas() throws DatabaseException {
        return recompensaDAO.listarRecompensas(); // Chama o método do DAO para listar as recompensas
    }

    // Método para atualizar uma recompensa
    public boolean atualizarRecompensa(Recompensa recompensa) throws DatabaseException {
        // Aqui você pode validar os dados antes de chamar o DAO
        if (recompensa.getNome() == null || recompensa.getNome().trim().isEmpty()) {
            throw new DatabaseException("Nome da recompensa não pode ser vazio.");
        }
        if (recompensa.getPontosNecessarios() <= 0) {
            throw new DatabaseException("Os pontos necessários devem ser positivos.");
        }

        return recompensaDAO.atualizarRecompensa(recompensa); // Chama o método do DAO para atualizar a recompensa
    }

    // Método para excluir uma recompensa
    public boolean excluirRecompensa(int id) throws DatabaseException {
        if (id <= 0) {
            throw new DatabaseException("ID da recompensa inválido.");
        }
        return recompensaDAO.excluirRecompensa(id); // Chama o método do DAO para excluir a recompensa
    }

    // Método para buscar uma recompensa por ID
    public Recompensa buscarRecompensaPorId(int id) throws DatabaseException {
        if (id <= 0) {
            throw new DatabaseException("ID da recompensa inválido.");
        }
        return recompensaDAO.buscarRecompensaPorId(id); // Chama o método do DAO para buscar a recompensa
    }

    // Método para verificar se o usuário pode resgatar a recompensa
    public boolean podeResgatar(int idRecompensa, int pontosUsuario) throws DatabaseException {
        if (idRecompensa <= 0) {
            throw new DatabaseException("ID da recompensa inválido.");
        }
        if (pontosUsuario < 0) {
            throw new DatabaseException("Pontos do usuário não podem ser negativos.");
        }
        return recompensaDAO.podeResgatar(idRecompensa, pontosUsuario); // Chama o método do DAO para verificar o resgate
    }
}
