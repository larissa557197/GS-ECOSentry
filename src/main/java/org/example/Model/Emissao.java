package org.example.Model;

import java.time.LocalDate;

public class Emissao {

    private Integer id;
    private String nome;
    private Integer idCategoria;
    private CategoriaAtividade categoriaAtividade;
    private LocalDate dataEmissao;
    private double valorEmissao;  // Campo para o valor da emissão


    // Construtor padrão
    //public Emissao() {}

    // Construtor com parâmetros


    public Emissao(Integer id, String nome, Integer idCategoria, CategoriaAtividade categoriaAtividade, LocalDate dataEmissao, double valorEmissao) {
        this.id = id;
        this.nome = nome;
        this.idCategoria = idCategoria;
        this.categoriaAtividade = categoriaAtividade;
        this.dataEmissao = dataEmissao;
        this.valorEmissao = valorEmissao;
    }



    public Emissao(int idEmissao, String nome, double valorEmissao, LocalDate dataEmissao) {
    }

    public Emissao() {

    }

    // Getters e setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(LocalDate dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getValorEmissao() {
        return valorEmissao;
    }

    public void setValorEmissao(double valorEmissao) {
        this.valorEmissao = valorEmissao;
    }

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public CategoriaAtividade getCategoriaAtividade() {
        return categoriaAtividade;
    }

    public void setCategoriaAtividade(CategoriaAtividade categoriaAtividade) {
        this.categoriaAtividade = categoriaAtividade;
    }
}
