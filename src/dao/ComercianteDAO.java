package dao;
import model.Comerciante;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;


public class ComercianteDAO {
    String sql = "INSERT INTO comerciante(nome, telefone, senha, cnpj, endereco, nome_loja) VALUES (?, ?, ?, ?, ?, ?)";

    try(
            Connection conn = Conexao.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql)

    ) {
        statement.setString(1, comerciante.getnome());


    }
}
