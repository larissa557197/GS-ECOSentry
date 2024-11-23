package org.example.Model.DTO;

public class ClienteDTO { // ENTIDADEA
    private int id;
    private String nome;
    private String cpf ;

   public ClienteDTO(){ // construtor padrão

   }
    public ClienteDTO(int id, String nome, String cpf, String senha) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
    }

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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

}
