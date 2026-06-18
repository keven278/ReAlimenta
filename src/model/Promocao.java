package model;

import java.time.LocalDate;

public class Promocao {

    private int id_promocao;
    private Alimento alimento;
    private LocalDate inicio_promocao;
    private LocalDate fim_promocao;

    public Promocao() {
    }

    public Promocao(int id_promocao,
                    Alimento alimento,
                    LocalDate inicio_promocao,
                    LocalDate fim_promocao) {

        this.id_promocao = id_promocao;
        this.alimento = alimento;
        this.inicio_promocao = inicio_promocao;
        this.fim_promocao = fim_promocao;
    }

    public int getIdPromocao() {
        return id_promocao;
    }

    public void setIdPromocao(int id_promocao) {
        this.id_promocao = id_promocao;
    }

    public Alimento getAlimento() {
        return alimento;
    }

    public void setAlimento(Alimento alimento) {
        this.alimento = alimento;
    }

    public LocalDate getInicioPromocao() {
        return inicio_promocao;
    }

    public void setInicioPromocao(LocalDate inicio_promocao) {
        this.inicio_promocao = inicio_promocao;
    }

    public LocalDate getFimPromocao() {
        return fim_promocao;
    }

    public void setFimPromocao(LocalDate fim_promocao) {
        this.fim_promocao = fim_promocao;
    }
}