package org.example.Model;

public class Recompensa {
    private Integer id; // Alterado para Integer para permitir valores nulos
    private String nome;
    private int pontosNecessarios;
    private String descricao;

    // Construtor para quando o ID é gerado pelo banco
    public Recompensa(Integer id, String nome, int pontosNecessarios, String descricao) {
        this.id = id;
        this.nome = nome;
        this.pontosNecessarios = pontosNecessarios;
        this.descricao = descricao;
    }

    // Construtor sem ID, usado para inserções
    public Recompensa(String nome, int pontosNecessarios, String descricao) {
        this.nome = nome;
        this.pontosNecessarios = pontosNecessarios;
        this.descricao = descricao;
    }

    // construtor padrão
    public Recompensa(){}

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getPontosNecessarios() {
        return pontosNecessarios;
    }

    public void setPontosNecessarios(int pontosNecessarios) {
        this.pontosNecessarios = pontosNecessarios;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "Recompensa{id=" + id + ", nome='" + nome + "', pontosNecessarios=" + pontosNecessarios + '}';
    }
}

