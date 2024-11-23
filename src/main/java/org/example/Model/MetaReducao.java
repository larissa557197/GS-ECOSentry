package org.example.Model;

public class MetaReducao {
    private Integer id;
    private Double metaEmissao;    // Meta de emissão
    private Double progressoAtual; // Progresso atual
    private String nome;
    private String descricao;

    // Construtor padrão
    public MetaReducao() {}

    // Construtor com todos os campos
    public MetaReducao(Integer id, Double metaEmissao, Double progressoAtual, String nome, String descricao) {
        this.id = id;
        this.metaEmissao = metaEmissao;
        this.progressoAtual = progressoAtual;
        this.nome = nome;
        this.descricao = descricao;
    }

    // Construtor para inicializar apenas a meta de emissão
    public MetaReducao(Double metaEmissao) {
        this.metaEmissao = metaEmissao;
        this.progressoAtual = 0.0; // Inicia o progresso com 0 por padrão

    }

    // Atualiza o progresso com a redução informada
    public void atualizarProgresso(Double reducao) {
        if (reducao != null) {
            this.progressoAtual = (this.progressoAtual == null ? 0.0 : this.progressoAtual) + reducao;
        }
    }

    // Verifica se a meta foi atingida
    public boolean metaAtingida() {
        return this.progressoAtual != null && this.metaEmissao != null && this.progressoAtual >= this.metaEmissao;
    }

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getProgressoAtual() {
        return progressoAtual;
    }

    public void setProgressoAtual(Double progressoAtual) {
        this.progressoAtual = progressoAtual;
    }

    public Double getMetaEmissao() {
        return metaEmissao;
    }

    public void setMetaEmissao(Double metaEmissao) {
        this.metaEmissao = metaEmissao;
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
}
