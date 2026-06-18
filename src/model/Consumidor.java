package model;

public class Consumidor extends Usuario {

    private int id_consumidor;
    private String cpf;

    public Consumidor() {
    }

    public Consumidor(int id_consumidor,
                      int id_usuario,
                      String nome,
                      String telefone,
                      String email,
                      String senha,
                      String cpf) {

        super(id_usuario, nome, telefone, email, senha);

        this.id_consumidor = id_consumidor;
        this.cpf = cpf;
    }

    public int getIdConsumidor() {
        return id_consumidor;
    }

    public void setIdConsumidor(int id_consumidor) {
        this.id_consumidor = id_consumidor;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}