package model;

import java.time.LocalDate;

public class Alimento {
    private int id;
    private String nome;
    private String categoria;
    private LocalDate validade;
    private int quantidade;
    private String descricao;
    private String imagem;
    private Comerciante comerciante;

    public Alimento() {
    }
    public Alimento(int id, String nome, String categoria, LocalDate validade, int quantidade, String descricao, String imagem,  Comerciante comerciante) {
        this.id = id;
        this.nome = nome;
        this.categoria = categoria;
        this.validade = validade;
        this.quantidade = quantidade;
        this.descricao = descricao;
        this.imagem = imagem;
        this.comerciante = new Comerciante();
    }

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public String getNome() {return nome;}
    public void setNome(String nome) {this.nome = nome;}
    public String getCategoria() {return categoria;}
    public void setCategoria(String categoria) {this.categoria = categoria;}
    public LocalDate getValidade() {return validade;}
    public void setValidade(LocalDate validade) {this.validade = validade;}
    public int getQuantidade() {return quantidade;}
    public void setQuantidade(int quantidade) {this.quantidade = quantidade;}
    public String getDescricao() {return descricao;}
    public void setDescricao(String descricao) {this.descricao = descricao;}
    public String getImagem() {return imagem;}
    public void setImagem(String imagem) {this.imagem = imagem;}
    public Comerciante getComerciante() {return comerciante;}
    public void setComerciante(Comerciante comerciante) {this.comerciante = comerciante;}

}