package model;

import java.time.LocalDate;

public class Notificacao {

    private int id_notificacao;
    private Consumidor consumidor;
    private String mensagem;
    private LocalDate data_notificacao;

    public Notificacao() {
    }

    public Notificacao(int id_notificacao,
                       Consumidor consumidor,
                       String mensagem,
                       LocalDate data_notificacao) {

        this.id_notificacao = id_notificacao;
        this.consumidor = consumidor;
        this.mensagem = mensagem;
        this.data_notificacao = data_notificacao;
    }

    public int getIdNotificacao() {
        return id_notificacao;
    }

    public void setIdNotificacao(int id_notificacao) {
        this.id_notificacao = id_notificacao;
    }

    public Consumidor getConsumidor() {
        return consumidor;
    }

    public void setConsumidor(Consumidor consumidor) {
        this.consumidor = consumidor;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public LocalDate getDataNotificacao() {
        return data_notificacao;
    }

    public void setDataNotificacao(LocalDate data_notificacao) {
        this.data_notificacao = data_notificacao;
    }
}