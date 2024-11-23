package org.example.Model;

public class CategoriaAtividade {

    private Integer id; // id único da categoria
    private String nome; // Nome da categoria (ex: "Transporte", "Energia", etc)
    private String descricao; // Descrição adicional da categoria

    // Construtor padrão
    public CategoriaAtividade() {}

    // Construtor com todos os parâmetros
    public CategoriaAtividade(Integer id, String nome, String descricao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
    }

    // Getters e setters
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "CategoriaAtividade{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}
