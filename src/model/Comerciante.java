package model;

public class Comerciante extends Usuario {

    private int id_comerciante;
    private String cnpj;
    private String endereco;
    private String nome_comercio;

    public Comerciante() {
    }

    public Comerciante(int id_comerciante,
                       int id_usuario,
                       String nome,
                       String telefone,
                       String email,
                       String senha,
                       String cnpj,
                       String endereco,
                       String nome_comercio) {

        super(id_usuario, nome, telefone, email, senha);

        this.id_comerciante = id_comerciante;
        this.cnpj = cnpj;
        this.endereco = endereco;
        this.nome_comercio = nome_comercio;
    }

    public int getIdComerciante() {
        return id_comerciante;
    }

    public void setIdComerciante(int id_comerciante) {
        this.id_comerciante = id_comerciante;
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

    public String getNomeComercio() {
        return nome_comercio;
    }

    public void setNomeComercio(String nome_comercio) {
        this.nome_comercio = nome_comercio;
    }
}