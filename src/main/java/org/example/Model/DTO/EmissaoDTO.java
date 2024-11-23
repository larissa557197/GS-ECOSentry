package org.example.Model.DTO;

import java.time.LocalDate;

public class EmissaoDTO {
    private int id;
    private LocalDate data;
    private double valorEmissao;
    private int categoriaId;
    private String categoriaNome;

    // Construtor com todos os parâmetros
    public EmissaoDTO(int id, LocalDate data, double valorEmissao, int categoriaId, String categoriaNome) {
        this.id = id;
        this.data = data;
        this.valorEmissao = valorEmissao;
        this.categoriaId = categoriaId;
        this.categoriaNome = categoriaNome;
    }

    // Construtor padrão
    public EmissaoDTO() {
    }

    // Getters e Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public double getValorEmissao() {
        return valorEmissao;
    }

    public void setValorEmissao(double valorEmissao) {
        this.valorEmissao = valorEmissao;
    }

    public int getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(int categoriaId) {
        this.categoriaId = categoriaId;
    }

    public String getCategoriaNome() {
        return categoriaNome;
    }

    public void setCategoriaNome(String categoriaNome) {
        this.categoriaNome = categoriaNome;
    }
}
