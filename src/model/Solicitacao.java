package model;

import java.time.LocalDate;

public class Solicitacao {

    private int id_solicitacao;
    private Consumidor consumidor;
    private Comerciante comerciante;
    private Alimento alimento;

    private LocalDate inicio_doacao;
    private LocalDate fim_doacao;

    private boolean doacao_feita;
    private int quantidade_doacao;

    public Solicitacao() {
    }

    public Solicitacao(int id_solicitacao,
                       Consumidor consumidor,
                       Comerciante comerciante,
                       Alimento alimento,
                       LocalDate inicio_doacao,
                       LocalDate fim_doacao,
                       boolean doacao_feita,
                       int quantidade_doacao) {

        this.id_solicitacao = id_solicitacao;
        this.consumidor = consumidor;
        this.comerciante = comerciante;
        this.alimento = alimento;
        this.inicio_doacao = inicio_doacao;
        this.fim_doacao = fim_doacao;
        this.doacao_feita = doacao_feita;
        this.quantidade_doacao = quantidade_doacao;
    }

    public int getIdSolicitacao() {
        return id_solicitacao;
    }

    public void setIdSolicitacao(int id_solicitacao) {
        this.id_solicitacao = id_solicitacao;
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

    public Alimento getAlimento() {
        return alimento;
    }

    public void setAlimento(Alimento alimento) {
        this.alimento = alimento;
    }

    public LocalDate getInicioDoacao() {
        return inicio_doacao;
    }

    public void setInicioDoacao(LocalDate inicio_doacao) {
        this.inicio_doacao = inicio_doacao;
    }

    public LocalDate getFimDoacao() {
        return fim_doacao;
    }

    public void setFimDoacao(LocalDate fim_doacao) {
        this.fim_doacao = fim_doacao;
    }

    public boolean isDoacaoFeita() {
        return doacao_feita;
    }

    public void setDoacaoFeita(boolean doacao_feita) {
        this.doacao_feita = doacao_feita;
    }

    public int getQuantidadeDoacao() {
        return quantidade_doacao;
    }

    public void setQuantidadeDoacao(int quantidade_doacao) {
        this.quantidade_doacao = quantidade_doacao;
    }
}