package model;

import java.time.LocalDate;

public class Alimento {
    private int id_alimento;
    private String nome_alimento;
    private String categoria;
    private LocalDate validade;
    private String imagem_alimento;
    private boolean promocao;
    private int quantidade;
    private String descricao;
    
    private Comerciante comerciante;

    public Alimento() {
    }
    public Alimento(int id_alimento, String nome_alimento, String categoria, LocalDate validade,String imagem_alimento,boolean promocao , int quantidade, String descricao,
         Comerciante comerciante) {
        this.id_alimento = id_alimento;
        this.nome_alimento = nome_alimento;
        this.categoria = categoria;
        this.validade = validade;
        this.imagem_alimento = imagem_alimento;
        this.promocao = promocao;
        this.quantidade = quantidade;
        this.descricao = descricao;
        
        this.comerciante = comerciante;
    }
    public int getIdAlimento(){
        return id_alimento;
    }
    public void setIdAlimento(int id){
        this.id_alimento=id;
    }
    public String getNomeAlimento(){
        return nome_alimento;
    }
    public void setNomeAlimento(String nome){
        this.nome_alimento = nome;
    }
    public String getCategoria(){
        return categoria;
    }
    public void setCategoria(String categoria){
        this.categoria = categoria;
    }
    public LocalDate getValidade(){
        return validade;
    }
    public void setValidade(LocalDate validade){
    this.validade = validade;
    }

public String getImagemAlimento() {
    return imagem_alimento;
}

public void setImagemAlimento(String imagem_alimento) {
    this.imagem_alimento = imagem_alimento;
}

public boolean isPromocao() {
    return promocao;
}

public void setPromocao(boolean promocao) {
    this.promocao = promocao;
}

public int getQuantidade() {
    return quantidade;
}

public void setQuantidade(int quantidade) {
    this.quantidade = quantidade;
}

public String getDescricao() {
    return descricao;
}

public void setDescricao(String descricao) {
    this.descricao = descricao;
}

public Comerciante getComerciante() {
    return comerciante;
}

public void setComerciante(Comerciante comerciante) {
    this.comerciante = comerciante;
}
}