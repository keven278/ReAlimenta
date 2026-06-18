package dao;

import model.Doacao;
import model.Consumidor;
import model.Promocao;
import model.Solicitacao;

import util.Conexao;
import java.sql.*;


public class SolicitacaoDAO {
    public void inserir(Solicitacao solicitacao) {
        String sql = "INSERT INTO Solicitacao (Consumidor, Doação, Quantidade, DataSolicitacao, Status) VALUES (?,?,?,?,?)";
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

            System.out.println("Solicitação cadastrada com sucesso");

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar solicitação");
        }

    }

    public Solicitacao buscarPorConsumidor(Consumidor consumidor) {
        String sql = "SELECT * FROM Solicitacao WHERE consumidor_id = ?";
        try (
                Connection conn = Conexao.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, consumidor.getId());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Solicitacao solicitacao = new Solicitacao();
                solicitacao.setIdSolicitacao(rs.getInt("id"));
                solicitacao.setConsumidor(consumidor); // já temos o objeto
                solicitacao.setQuantidade(rs.getInt("quantidade"));
                solicitacao.setDataSolicitacao(rs.getTimestamp("dataSolicitacao").toLocalDateTime());
                solicitacao.setStatus(rs.getString("status"));
                return solicitacao;
            }
        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
        }
        return null;
    }


    public void atualizar(Solicitacao solicitacao) {
        String sql = "UPDATE Solicitacao SET consumidor_id = ?, quantidade = ?, status = ? WHERE id = ?";
        try (
                Connection conn = Conexao.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, solicitacao.getConsumidor().getId());
            stmt.setInt(2, solicitacao.getQuantidade());
            stmt.setString(3, solicitacao.getStatus());
            stmt.setInt(4, solicitacao.getIdSolicitacao());

            stmt.executeUpdate();
            System.out.println("Solicitação atualizada com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar: " + e.getMessage());
        }
    }


    public void excluir(int id) {
        String sql = "DELETE FROM Solicitacao WHERE id = ?";
        try (
                Connection conn = Conexao.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, id);

            stmt.executeUpdate();
            System.out.println("Solicitação excluída com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao excluir: " + e.getMessage());
        }
    }

}



