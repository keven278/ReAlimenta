package dao;

import model.Alimento;
import util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;

public class AlimentoDAO {

    public void inserir(Alimento alimento) {
        String sql ="INSERT INTO Alimento" + "id, nome, categoria, validade, quantidade, descricao, imagem" +
                "VALUES (?,?,?,?,?,?,?)";

        try(
                Connection conn = Conexao.getConnection();
                PreparedStatement statement = conn.prepareStatement(sql)
        ){
            statement.setString(1, alimento.getNome());
            statement.setString(2, alimento.getCategoria());
            statement.setString(3, alimento.getValidade().toString());
            statement.setInt(   4, alimento.getQuantidade());
            statement.setString(5, alimento.getDescricao());
            statement.setString(6, alimento.getImagem());
            statement.setString(7, alimento.getComerciante().getSenha());

            statement.executeUpdate();
            statement.close();
            conn.close();

            System.out.println("Alimento inserido com sucesso");

        } catch (SQLException e) {
            System.out.println("Erro ao inserir alimento");
        }
    }
    public ArrayList<Alimento> listar()
        ArrayList<Alimento> alimentos = new ArrayList<>();

        String SQL = "SELECT * FROM Alimento";

        try()



    }







