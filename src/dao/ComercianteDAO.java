package dao;

import model.Comerciante;
import util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ComercianteDAO {

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public void inserirComerciante(Comerciante comerciante) {
        int id_usuario = usuarioDAO.inserirUsuario(comerciante);

        if (id_usuario == -1) {
            System.out.println("Erro: usuário não foi cadastrado.");
            return;
        }

        String sql = "INSERT INTO comerciante (id_usuario, cnpj, endereco, nome_comercio) VALUES (?, ?, ?, ?)";

        try (
                Connection conn = Conexao.getConnection();
                PreparedStatement statement = conn.prepareStatement(sql)
        ) {
            statement.setInt(1, id_usuario);
            statement.setString(2, comerciante.getCnpj());
            statement.setString(3, comerciante.getEndereco());
            statement.setString(4, comerciante.getNomeComercio());

            statement.executeUpdate();

            System.out.println("Comerciante inserido com sucesso.");

        } catch (SQLException e) {
            System.out.println("Erro ao inserir comerciante: " + e.getMessage());
        }
    }
}