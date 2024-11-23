package org.example.Service;

import org.example.DAO.CategoriaAtividadeDAO;
import org.example.Exception.CategoriaAtividadeException;
import org.example.Exception.CategoriaAtividadeInvalidaException;
import org.example.Exception.DatabaseException;
import org.example.Model.CategoriaAtividade;

import java.util.List;

public class CategoriaAtividadeService {

    private final CategoriaAtividadeDAO categoriaAtividadeDAO = new CategoriaAtividadeDAO();

    // método para criar uma categoria
    // Método para criar uma categoria
    public boolean criarCategoria(CategoriaAtividade categoria) {
        try {
            return categoriaAtividadeDAO.salvarCategoria(categoria); // Chama o DAO para salvar
        } catch (DatabaseException e) {
            // Aqui podemos logar a exceção e retornar falso, indicando que falhou
            System.err.println("Erro ao salvar categoria: " + e.getMessage());
            return false;
        }
    }

    // Método para listar todas as categorias
    public List<CategoriaAtividade> listarCategorias() {
        try {
            return categoriaAtividadeDAO.listarCategorias();  // Método que pode lançar DatabaseException
        } catch (DatabaseException e) {
            // Tratar a exceção aqui
            System.err.println("Erro ao listar categorias: " + e.getMessage());
            return null;  // Retorna null ou uma lista vazia
        }
    }

    // Método para salvar uma nova categoria com validação
    public boolean salvarCategoria(CategoriaAtividade categoria) throws DatabaseException, CategoriaAtividadeInvalidaException {
        if (categoria == null || categoria.getNome() == null || categoria.getNome().isEmpty()) {
            throw new CategoriaAtividadeInvalidaException("Nome da categoria é obrigatório.");
        }

        // Verifica se a categoria já existe
        if (categoriaExiste(categoria.getNome())) {
            throw new CategoriaAtividadeInvalidaException("Categoria já existe.");
        }

        try {
            return categoriaAtividadeDAO.salvarCategoria(categoria);  // Salva a categoria no DAO
        } catch (DatabaseException e) {
            throw new DatabaseException("Erro ao salvar categoria: " + e.getMessage());
        }
    }

    // Método auxiliar para verificar se a categoria existe
    private boolean categoriaExiste(String nomeCategoria) throws DatabaseException {
        return categoriaAtividadeDAO.categoriaExiste(Integer.parseInt(nomeCategoria));  // Método DAO já trata a exceção
    }

    // Método para atualizar uma categoria
    public boolean atualizarCategoria(CategoriaAtividade categoria) throws DatabaseException, CategoriaAtividadeException {
        if (categoria == null || categoria.getId() == null) {
            throw new CategoriaAtividadeException("A categoria e o ID da categoria não podem ser nulos.");
        }

        // Chama o método atualizarCategoria do DAO
        return categoriaAtividadeDAO.atualizarCategoria(categoria);
    }

    // Método para excluir uma categoria
    public boolean excluirCategoria(Integer idCategoria) throws DatabaseException, CategoriaAtividadeInvalidaException {
        if (idCategoria == null) {
            throw new CategoriaAtividadeInvalidaException("O ID da categoria não pode ser nulo.");
        }

        // Chama o método excluirCategoria do DAO para excluir a categoria do banco de dados
        return categoriaAtividadeDAO.excluirCategoria(idCategoria);
    }

}
