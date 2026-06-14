package dao;

import model.Doacao;
import model.Consumidor;
import model.Promocao;
import  model.Solicitacao;

import util.Conexao;
import java.sql.*;


public class SolicitacaoDAO {
    public void inserir(Solicitacao solicitacao) {
        String sql ="INSERT INTO Solicitação" + "Consumidor, Doação, Quantidade, DataSolicitacao, Status" +
                "VALUES (?,?,?,?,?,?,?)";
        try(
                Connection conn = Conexao.getConnection();
                PreparedStatement statement = conn.prepareStatement(sql)
        ){
            statement.setInt(1, solicitacao.getConsumidor().getId());
            statement.setInt(2, solicitacao.getDoacao().getId());
            statement.setInt(3, solicitacao.getQuantidade());
            statement.setTimestamp(4, Timestamp.valueOf(solicitacao.getDataSolicitacao()));
            statement.setString(5, solicitacao.getStatus());


            statement.executeUpdate();
            statement.close();
            conn.close();

            System.out.println("Promoção inserida  com sucesso");

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar promoção");
        }
    }
}



