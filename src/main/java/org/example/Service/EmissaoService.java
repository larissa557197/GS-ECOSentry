package org.example.Service;

import org.example.Exception.DatabaseException;
import org.example.Model.CategoriaAtividade;
import org.example.Model.Emissao;
import org.example.DAO.EmissaoDAO;

import java.util.List;

public class EmissaoService {

    private  EmissaoDAO emissaoDAO;

    public EmissaoService (){
        emissaoDAO = new EmissaoDAO();
    }

    // Método para criar uma nova emissão
    public Emissao criarEmissao(Emissao emissao) throws DatabaseException {
        try {
            emissaoDAO.criarEmissao(emissao);
            System.out.println("Emissão criada com sucesso!");
            return emissao;
        } catch (DatabaseException e) {
            throw new DatabaseException("Erro ao criar emissão: " + e.getMessage());
        }
    }

    // Método para listar todas as emissões
    public List<Emissao> listarEmissoes() {
        try {
            return emissaoDAO.listarEmissoes();
        } catch (DatabaseException e) {
            System.err.println("Erro ao listar emissões: " + e.getMessage());
            return null;
        }
    }

    // Método para listar todas as emissões com suas categorias
    public List<Emissao> listarEmissoesComCategorias() {
        try {
            return emissaoDAO.listarEmissoesComCategorias();
        } catch (DatabaseException e) {
            System.err.println("Erro ao listar emissões com categorias: " + e.getMessage());
            return null;
        }
    }

    // Método para salvar uma emissão (verifica se a categoria existe antes de salvar)
    public boolean salvarEmissao(Emissao emissao) throws DatabaseException {
        try {
            return emissaoDAO.salvarEmissao(emissao);
        } catch (DatabaseException e) {
            // Lançando a exceção personalizada com a mensagem e o SQLException original
            throw new DatabaseException("Erro ao salvar emissão: " + e.getMessage());
        }
    }

    // Método para atualizar uma emissão
    public boolean atualizarEmissao(int id, Emissao emissao) throws Exception {

        return emissaoDAO.atualizarEmissao(id, emissao);
    }



    // Método para excluir uma emissão
    public boolean excluirEmissao(Integer id) throws DatabaseException {
        try {
            return emissaoDAO.excluirEmissao(id);
        } catch (DatabaseException e) {
            // Lançando a exceção personalizada com a mensagem e o SQLExceção original
            throw new DatabaseException("Erro ao excluir emissão: " + e.getMessage());
        }
    }

    // Método para verificar se a categoria existe
    public boolean categoriaExiste(Integer id) throws DatabaseException {
        try {
            return emissaoDAO.categoriaExiste(id);
        } catch (DatabaseException e) {
            // Lançando a exceção personalizada com a mensagem e o SQLExceção original
            throw new DatabaseException("Erro ao verificar categoria: " + e.getMessage());
        }
    }



    // Método para calcular a emissão total
    public String calcularEmissaoTotal(List<Emissao> emissaoList) {
        if (emissaoList == null || emissaoList.isEmpty()) {
            return "Nenhuma emissão encontrada.";
        }
        double totalEmissao = 0.0;
        for (Emissao emissao : emissaoList) {
            totalEmissao += emissao.getValorEmissao();
        }
        return String.format("Emissão total: %.2f", totalEmissao);
    }

    // Método para calcular a emissão por categoria
    public String calcularEmissaoPorCategoria(List<Emissao> emissaoList, CategoriaAtividade categoriaAtividade) {
        double emissaoTotalPorCategoria = 0.0;

        for (Emissao emissao : emissaoList) {
            if (emissao.getCategoriaAtividade() != null && emissao.getCategoriaAtividade().equals(categoriaAtividade)) {
                emissaoTotalPorCategoria += emissao.getValorEmissao();
            }
        }

        return String.format("Emissão total para a categoria '%s': %.2f", categoriaAtividade.getNome(), emissaoTotalPorCategoria);
    }


    public boolean idExiste(Integer id) {
        return emissaoDAO.idExiste(id);
    }

}
