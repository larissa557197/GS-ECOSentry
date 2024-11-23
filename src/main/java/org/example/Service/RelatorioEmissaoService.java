package org.example.Service;

import org.example.DAO.EmissaoDAO;
import org.example.Exception.DatabaseException;
import org.example.Model.CategoriaAtividade;
import org.example.Model.Emissao;
import org.example.Model.RelatorioEmissao;

import java.util.List;

public class RelatorioEmissaoService {

    private RelatorioEmissao relatorioEmissao;
    private EmissaoDAO emissaoDAO;

    public RelatorioEmissaoService() {
        this.relatorioEmissao = new RelatorioEmissao();
        this.emissaoDAO = new EmissaoDAO();
    }

    /// Método para calcular a emissão total
    public String calcularEmissaoTotal(List<Emissao> emissaoList) {
        if (emissaoList == null || emissaoList.isEmpty()) {
            return "Nenhuma emissão encontrada.";
        }
        return relatorioEmissao.calcularEmissaoTotal(emissaoList);
    }

    // Método para calcular a emissão por categoria
    public String calcularEmissaoPorCategoria(List<Emissao> emissaoList, CategoriaAtividade categoriaAtividade) {
        double emissaoTotalPorCategoria = 0.0;

        for (Emissao emissao : emissaoList) {
            // Verifique se a categoria da emissão não é null antes de fazer a comparação
            if (emissao.getCategoriaAtividade() != null &&
                    emissao.getCategoriaAtividade().equals(categoriaAtividade)) {
                emissaoTotalPorCategoria += emissao.getValorEmissao();
            }
        }

        // Retorne o valor total da emissão para a categoria específica
        return String.format("Emissão total para a categoria '%s': %.2f", categoriaAtividade.getNome(), emissaoTotalPorCategoria);
    }

    // Método para gerar relatório de emissões
    public List<Emissao> gerarRelatorioEmissoes() throws DatabaseException {
        return emissaoDAO.listarEmissoes();
    }
}
