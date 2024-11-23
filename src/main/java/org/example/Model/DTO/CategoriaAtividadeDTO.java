package org.example.Model.DTO;

public class CategoriaAtividadeDTO {

    private int id;
    private String nome;

    // Construtor padrão
    public CategoriaAtividadeDTO() {
    }

    // Construtor com parâmetros
    public CategoriaAtividadeDTO(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}

