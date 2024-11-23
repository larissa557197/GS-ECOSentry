package org.example.Model;

import jakarta.json.bind.annotation.JsonbDateFormat;

import java.time.LocalDate;
import java.util.Date;

public class Relatorio {

    private int idRelatorio;
    private String descricao;
    //@JsonbDateFormat("yyyy-MM-dd") // Formato que corresponde ao valor "2024-11-19"
    private LocalDate dataGeracao;
    private String conteudo;
    private String categoria;
    private double emissaoTotal;

    public Relatorio() {}

    public Relatorio(int idRelatorio, String descricao, LocalDate dataGeracao, String conteudo, String categoria, double emissaoTotal) {
        this.idRelatorio = idRelatorio;
        this.descricao = descricao;
        this.dataGeracao = dataGeracao;
        this.conteudo = conteudo;
        this.categoria = categoria;
        this.emissaoTotal = emissaoTotal;
    }

    public int getIdRelatorio() {
        return idRelatorio;
    }

    public void setIdRelatorio(int idRelatorio) {
        this.idRelatorio = idRelatorio;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataGeracao() {
        return dataGeracao;
    }

    public void setDataGeracao(LocalDate dataGeracao) {
        this.dataGeracao = dataGeracao;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getEmissaoTotal() {
        return emissaoTotal;
    }

    public void setEmissaoTotal(double emissaoTotal) {
        this.emissaoTotal = emissaoTotal;
    }

    @Override
    public String toString() {
        return "Relatorio{" +
                "idRelatorio=" + idRelatorio +
                ", descricao='" + descricao + '\'' +
                ", dataGeracao=" + dataGeracao +
                ", conteudo='" + conteudo + '\'' +
                ", categoria='" + categoria + '\'' +
                ", emissaoTotal=" + emissaoTotal +
                '}';
    }
}
