package dao;
import model.Usuario;
import model.Consumidor;
import model.Comerciante;
import util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;

public class UsuarioDAO {

    public int inserirUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuario (senha, nome, telefone, email) VALUES (?, ?, ?, ?)";

        try (
                Connection conn = Conexao.getConnection();
                PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, usuario.getSenha());
            statement.setString(2, usuario.getNome());
            statement.setString(3, usuario.getTelefone());
            statement.setString(4, usuario.getEmail());

            statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao inserir usuário: " + e.getMessage());
        }

        return -1;
    }

    public Usuario login(String email, String senha) {
        String sqlUsuario = """
                SELECT id_usuario, nome, telefone, email, senha
                FROM usuario
                WHERE email = ? AND senha = ?
                """;

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlUsuario)) {
            stmt.setString(1, email);
            stmt.setString(2, senha);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int id_usuario = rs.getInt("id_usuario");
                String nome = rs.getString("nome");
                String telefone = rs.getString("telefone");
                String emailBanco = rs.getString("email");
                String senhaBanco = rs.getString("senha");
                Consumidor consumidor = buscarConsumidor(conn, id_usuario, nome, telefone, emailBanco, senhaBanco);
                if (consumidor != null) {
                    return consumidor;
                }

                Comerciante comerciante = buscarComerciante(conn, id_usuario, nome, telefone, emailBanco, senhaBanco);
                if (comerciante != null) {
                    return comerciante;
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao realizar login");
            e.printStackTrace();
        }

        return null;
    }

    private Consumidor buscarConsumidor(Connection conn,
                                        int id_usuario,
                                        String nome,
                                        String telefone,
                                        String email,
                                        String senha) throws SQLException {

        String sql = "SELECT id_consumidor, cpf FROM consumidor WHERE id_usuario = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id_usuario);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int id_consumidor = rs.getInt("id_consumidor");
                String cpf = rs.getString("cpf");

                return new Consumidor(
                        id_consumidor,
                        id_usuario,
                        nome,
                        telefone,
                        email,
                        senha,
                        cpf
                );
            }
        }

        return null;
    }
    private Comerciante buscarComerciante(Connection conn,
                                          int id_usuario,
                                          String nome,
                                          String telefone,
                                          String email,
                                          String senha) throws SQLException {

        String sql = """
            SELECT id_comerciante, cnpj, endereco, nome_comercio
            FROM comerciante
            WHERE id_usuario = ?
            """;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id_usuario);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int id_comerciante = rs.getInt("id_comerciante");
                String cnpj = rs.getString("cnpj");
                String endereco = rs.getString("endereco");
                String nome_comercio = rs.getString("nome_comercio");

                return new Comerciante(
                        id_comerciante,
                        id_usuario,
                        nome,
                        telefone,
                        email,
                        senha,
                        cnpj,
                        endereco,
                        nome_comercio
                );
            }
        }

        return null;
    }

}