package model;

public class Doacao {

    private int id_doacao;
    private Solicitacao solicitacao;
    private Consumidor consumidor;
    private Comerciante comerciante;
    private int quantidade_doacao;

    public Doacao() {
    }

    public Doacao(int id_doacao,
                  Solicitacao solicitacao,
                  Consumidor consumidor,
                  Comerciante comerciante,
                  int quantidade_doacao) {

        this.id_doacao = id_doacao;
        this.solicitacao = solicitacao;
        this.consumidor = consumidor;
        this.comerciante = comerciante;
        this.quantidade_doacao = quantidade_doacao;
    }

    public int getIdDoacao() {
        return id_doacao;
    }

    public void setIdDoacao(int id_doacao) {
        this.id_doacao = id_doacao;
    }

    public Solicitacao getSolicitacao() {
        return solicitacao;
    }

    public void setSolicitacao(Solicitacao solicitacao) {
        this.solicitacao = solicitacao;
    }

    public Consumidor getConsumidor() {
        return consumidor;
    }

    public void setConsumidor(Consumidor consumidor) {
        this.consumidor = consumidor;
    }

    public Comerciante getComerciante() {
        return comerciante;
    }

    public void setComerciante(Comerciante comerciante) {
        this.comerciante = comerciante;
    }

    public int getQuantidadeDoacao() {
        return quantidade_doacao;
    }

    public void setQuantidadeDoacao(int quantidade_doacao) {
        this.quantidade_doacao = quantidade_doacao;
    }
}