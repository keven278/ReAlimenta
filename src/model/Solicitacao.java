package model;
import java.time.LocalDateTime;

public class Solicitacao {
    private int id;
    private Consumidor consumidor;
    private Doacao doacao;
    private int quantidade;
    private LocalDateTime dataSolicitacao;
    private String status;

    public Solicitacao() {
    }

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public Consumidor getConsumidor() {return consumidor;}
    public void setConsumidor(Consumidor consumidor) {this.consumidor = consumidor;}
    public Doacao getDoacao() {return doacao;}
    public void setDoacao(Doacao doacao) {this.doacao = doacao;}
    public int getQuantidade() {return quantidade;}
    public void setQuantidade(int quantidade) {this.quantidade = quantidade;}
    public LocalDateTime getDataSolicitacao() {return dataSolicitacao;}
    public void setDataSolicitacao(LocalDateTime dataSolicitacao) {this.dataSolicitacao = dataSolicitacao;}
    public String getStatus() {return status;}
    public void setStatus(String status) {this.status = status;}
}