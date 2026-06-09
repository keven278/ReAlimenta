package model;

public class Consumidor extends Usuario {
    private String cpf;
    private String email;

    public Consumidor() {
    }
    public Consumidor(int id, String nome, String telefone, String senha, String cpf, String email) {
        super(id, nome, telefone, senha);
        this.cpf = cpf;
        this.email = email;
    }

    public String getCpf() {return cpf;}
    public void setCpf(String cpf) {this.cpf = cpf;}
    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}
}