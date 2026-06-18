package dao;

import model.Alimento;
import model.Doacao;
import util.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class DoacaoDAO {
    public void inserir(Doacao doacao) {
        String sql ="INSERT INTO Doacao (Alimento, Quantidade, dataLimite, horarioRetirada, obsevacoes, status) VALUES (?,?,?,?,?,?)";
        try(
                Connection conn = Conexao.getConnection();
                PreparedStatement statement = conn.prepareStatement(sql)
        ){
            statement.setInt(1, doacao.getAlimento().getId());
            statement.setInt(2, doacao.getQuantidade());
            statement.setString(3, doacao.getHorarioRetirada());
            statement.setString(4, doacao.getObservacoes());
            statement.setString(5, doacao.getStatus());

            statement.executeUpdate();
            statement.close();
            conn.close();

            System.out.println("Doação cadastrada com sucesso");

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar Doacao");
        }
    }
}












