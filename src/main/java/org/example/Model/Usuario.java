package org.example.Model;

public class Usuario {

    private int id;
    private String nome;
    private String senha;

    // Construtor padrão
    public Usuario() {}

    // Construtor com parâmetros
    public Usuario(int id, String nome, String senha) {
        this.id = id;
        this.nome = nome;
        this.senha = senha;
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    // Método toString() para fácil visualização
    @Override
    public String toString() {
        return "Usuario [id=" + id + ", nome=" + nome + ", senha=" + senha + "]";
    }
}
