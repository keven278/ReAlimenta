package dao;

import model.Alimento;
import util.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;

public class AlimentoDAO {

    public void inserirAlimento(Alimento alimento) {
        String sql = """
            INSERT INTO alimento
            (nome_alimento, categoria, validade, imagem_alimento,
             promocao, quantidade, descricao, id_comerciante)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            """;

        try (
                Connection conn = Conexao.getConnection();
                PreparedStatement statement = conn.prepareStatement(sql)
        ) {
            statement.setString(1, alimento.getNomeAlimento());
            statement.setString(2, alimento.getCategoria());
            statement.setDate(3, java.sql.Date.valueOf(alimento.getValidade()));
            statement.setString(4, alimento.getImagemAlimento());
            statement.setBoolean(5, alimento.isPromocao());
            statement.setInt(6, alimento.getQuantidade());
            statement.setString(7, alimento.getDescricao());
            statement.setInt(8, alimento.getComerciante().getIdComerciante());

            statement.executeUpdate();

            System.out.println("Alimento inserido com sucesso");

        } catch (SQLException e) {
            System.out.println("Erro ao inserir alimento: " + e.getMessage());
        }
    }


    }







