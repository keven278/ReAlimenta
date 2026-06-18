package dao;

import model.Comerciante;
import model.Consumidor;
import util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConsumidorDAO {
    public void inserir(Consumidor consumidor) {
        String sql = "INSERT INTO consumidor (nome, telefone, senha, cpf, emaiL) VALUES (?, ?, ?, ?, ?)";
        try(
                Connection conn = Conexao.getConnection();
                PreparedStatement statement = conn.prepareStatement(sql)
        ){
            statement.setString(1, consumidor.getNome());
            statement.setString(2, consumidor.getTelefone());
            statement.setString(3, consumidor.getSenha());
            statement.setString(4, consumidor.getCpf());
            statement.setString(5, consumidor.getEmail());
            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao inserir comerciante: " + e.getMessage());
        }
    }
}




