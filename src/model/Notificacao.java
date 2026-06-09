package model;
import java.time.LocalDateTime;

public class Notificacao {
    private int id;
    private String titulo;
    private String descricao;
    private LocalDateTime dataHora;
    private boolean lida;
    private Usuario usuario;

    public Notificacao() {
    }

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public String getTitulo() {return titulo;}
    public void setTitulo(String titulo) {this.titulo = titulo;}
    public String getDescricao() {return descricao;}
    public void setDescricao(String descricao) {this.descricao = descricao;}
    public LocalDateTime getDataHora() {return dataHora;}
    public void setDataHora(LocalDateTime dataHora) {this.dataHora = dataHora;}
    public boolean isLida() {return lida;}
    public void setLida(boolean lida) {this.lida = lida;}
    public Usuario getUsuario() {return usuario;}
    public void setUsuario(Usuario usuario) {this.usuario = usuario;}
}