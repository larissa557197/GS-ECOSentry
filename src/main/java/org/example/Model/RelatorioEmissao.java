package org.example.Model;

import java.util.List;

public class RelatorioEmissao {

    // Método para calcular a emissão total de uma lista de emissões
    public String calcularEmissaoTotal(List<Emissao> emissaoList) {
        if (emissaoList == null || emissaoList.isEmpty()) {
            return "A lista de emissões está vazia ou não foi fornecida.";
        }
        double total = emissaoList.stream()
                .mapToDouble(Emissao::getValorEmissao)
                .sum();
        return "Emissão total: " + total;
    }

    // Método para calcular a emissão total por categoria
    public String calcularEmissaoPorCategoria(List<Emissao> emissaoList, CategoriaAtividade categoriaAtividade) {
        if (emissaoList == null || emissaoList.isEmpty()) {
            return "A lista de emissões está vazia ou não foi fornecida.";
        }
        if (categoriaAtividade == null) {
            return "A categoria de atividade não foi fornecida.";
        }
        double totalPorCategoria = emissaoList.stream()
                .filter(emissao -> emissao.getCategoriaAtividade().equals(categoriaAtividade))
                .mapToDouble(Emissao::getValorEmissao)
                .sum();

        if (totalPorCategoria == 0) {
            return "Nenhuma emissão encontrada para a categoria fornecida.";
        }
        return "Emissão total para a categoria " + categoriaAtividade.getNome() + ": " + totalPorCategoria;
    }
}
