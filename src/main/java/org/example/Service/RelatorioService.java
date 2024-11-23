package org.example.Service;

import org.example.DAO.RelatorioDAO;
import org.example.DAO.EmissaoDAO;
import org.example.Exception.DatabaseException;
import org.example.Exception.RelatorioNotFoundException;
import org.example.Model.CategoriaAtividade;
import org.example.Model.Emissao;
import org.example.Model.Relatorio;
import org.example.Model.RelatorioEmissao;

import java.sql.SQLException;
import java.util.List;

public class RelatorioService {

    private RelatorioDAO relatorioDAO;
    private EmissaoDAO emissaoDAO;

    public RelatorioService() {
        this.relatorioDAO = new RelatorioDAO();
        this.emissaoDAO = new EmissaoDAO();
    }

    public boolean inserirRelatorio(Relatorio relatorio) throws DatabaseException {
       return relatorioDAO.inserirRelatorio(relatorio);
    }


    public List<Relatorio> gerarRelatorios() throws DatabaseException {
        return relatorioDAO.obterRelatorios();
    }


    // Método para gerar relatórios específicos com base no ID
    public Relatorio gerarRelatorioPorId(int idRelatorio) throws RelatorioNotFoundException, DatabaseException {
        return relatorioDAO.obterRelatorioPorId(idRelatorio);
    }


    public boolean atualizarRelatorio(Relatorio relatorio) throws DatabaseException {
        return relatorioDAO.atualizarRelatorio(relatorio);
    }

    public boolean deletarRelatorio(int id) throws DatabaseException {
        return relatorioDAO.deletarRelatorio(id);
    }

    public List<Relatorio> buscarRelatoriosPorCategoria(String categoria) throws DatabaseException {
        return relatorioDAO.buscarRelatoriosPorCategoria(categoria);
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


}
