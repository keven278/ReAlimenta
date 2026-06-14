package model;
import java.time.LocalDate;

public class Doacao {
    private int id;
    private Alimento alimento;
    private int quantidade;
    private LocalDate dataLimite;
    private String horarioRetirada;
    private String observacoes;
    private String status;

    public Doacao() {
    }

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public Alimento getAlimento() {return alimento;}
    public void setAlimento(Alimento alimento) {this.alimento = alimento;}
    public int getQuantidade() {return  quantidade;}
    public void setQuantidade(int quantidade) {this.quantidade = quantidade;}
    public LocalDate getDataLimite() {return dataLimite;}
    public void setDataLimite(LocalDate dataLimite) {this.dataLimite = dataLimite;}
    public String getHorarioRetirada() {return horarioRetirada;}
    public void setHorarioRetirada(String horarioRetirada) {this.horarioRetirada = horarioRetirada;}
    public String getObservacoes() {return observacoes;}
    public void setObservacoes(String observacoes) {this.observacoes = observacoes;}
    public String getStatus() {return status;}
    public void setStatus(String status) {this.status = status;}
}