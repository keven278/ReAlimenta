package dao;
import model.Comerciante;
import util.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

    public class ComercianteDAO {
        public void inserir(Comerciante comerciante){
            String sql = "INSERT INTO comerciante(nome,telefone,senha,cnpj,endereco,nomeLoja) VALUES (?,?,?,?,?,?)";
            try(
                    Connection conn = Conexao.getConnection();
                    PreparedStatement statement = conn.prepareStatement(sql)
            ){
                statement.setString(1, comerciante.getNome());
                statement.setString(2, comerciante.getTelefone());
                statement.setString(3, comerciante.getSenha());
                statement.setString(4, comerciante.getCnpj());
                statement.setString(5, comerciante.getEndereco());
                statement.setString(6, comerciante.getNomeLoja());
                statement.executeUpdate();

            } catch (SQLException e) {
                System.out.println("Erro ao inserir comerciante: " + e.getMessage());
            }
        }
    }





