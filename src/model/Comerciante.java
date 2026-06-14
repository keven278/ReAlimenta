package model;

public class Comerciante extends Usuario {
    private String cnpj;
    private String endereco;
    private String nomeLoja;

    public Comerciante() {
    }
    public Comerciante(int id, String nome, String telefone, String senha, String cnpj, String endereco, String nomeLoja) {
        super(id, nome, telefone, senha);
        this.cnpj = cnpj;
        this.endereco = endereco;
        this.nomeLoja = nomeLoja;
    }

    public String getCnpj() {
        return cnpj;
    }
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
    public String getEndereco() {
        return endereco;
    }
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
    public String getNomeLoja() {
        return nomeLoja;
    }
    public void setNomeLoja(String nomeLoja) {
        this.nomeLoja = nomeLoja;
    }



}