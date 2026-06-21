package dao;

import model.Consumidor;
import util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConsumidorDAO {

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public void inserirConsumidor(Consumidor consumidor) {
        int id_usuario = usuarioDAO.inserirUsuario(consumidor);

        if (id_usuario == -1) {
            System.out.println("Erro: usuário não foi cadastrado.");
            return;
        }

        String sql = "INSERT INTO consumidor (id_usuario, cpf) VALUES (?, ?)";

        try (
                Connection conn = Conexao.getConnection();
                PreparedStatement statement = conn.prepareStatement(sql)
        ) {
            statement.setInt(1, id_usuario);
            statement.setString(2, consumidor.getCpf());

            statement.executeUpdate();

            System.out.println("Consumidor inserido com sucesso.");

        } catch (SQLException e) {
            System.out.println("Erro ao inserir consumidor: " + e.getMessage());
        }
    }
}