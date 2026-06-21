package dao;

import model.Promocao;
import util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class PromocaoDAO {
    public void inserir(Promocao promocao) {
        String sql = "INSERT INTO Promocao (Alimento, Desconto, dataInicio, dataFim, obsevacoes, ativa) VALUES (?,?,?,?,?,?)";
        try(
                Connection conn = Conexao.getConnection();
                PreparedStatement statement = conn.prepareStatement(sql)
        ){
            statement.setInt(1, promocao.getAlimento().getIdAlimento());
            statement.setDouble(2, promocao.getIdPromocao());
            statement.setString(3, promocao.getInicioPromocao().toString());
            statement.setString(4, promocao.getFimPromocao().toString());
            statement.setBoolean(5, promocao.getAlimento().isPromocao());

            statement.executeUpdate();
            statement.close();
            conn.close();

            System.out.println("Promoção inserida  com sucesso");

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar promoção");
        }
    }
}











