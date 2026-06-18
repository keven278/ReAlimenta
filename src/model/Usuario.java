package model;

public abstract class Usuario {

    private int id_usuario;
    private String senha;
    private String nome;
    private String telefone;
    private String email;

    public Usuario() {
    }

    public Usuario(int id_usuario, String nome, String telefone, String email, String senha) {
        this.id_usuario = id_usuario;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.senha = senha;
    }

    public int getIdUsuario() {
        return id_usuario;
    }

    public void setIdUsuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}