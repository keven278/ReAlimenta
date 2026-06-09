package model;
import java.time.LocalDate;

public class Promocao {
    private int id;
    private Alimento alimento;
    private double desconto;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private String observacoes;
    private boolean ativa;

    public Promocao() {}
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public Alimento getAlimento() {return alimento;}
    public void setAlimento(Alimento alimento) {this.alimento = alimento;}
    public double getDesconto() {return desconto;}
    public void setDesconto(double desconto) {this.desconto = desconto;}
    public LocalDate getDataInicio() {return dataInicio;}
    public void setDataInicio(LocalDate dataInicio) {this.dataInicio = dataInicio;}
    public LocalDate getDataFim() {return dataFim;}
    public void setDataFim(LocalDate dataFim) {this.dataFim = dataFim;}
    public String getObservacoes() {return observacoes;}
    public void setObservacoes(String observacoes) {this.observacoes = observacoes;}
    public boolean isAtiva() {return ativa;}
    public void setAtiva(boolean ativa) {this.ativa = ativa;}
}