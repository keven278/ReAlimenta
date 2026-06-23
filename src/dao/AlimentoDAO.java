package dao;

import model.Alimento;
import model.Comerciante;
import util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AlimentoDAO {

    public void inserirAlimento(Alimento alimento) {
        String sql = """
            INSERT INTO alimento
            (nome_alimento, categoria, validade, imagem_alimento,
             promocao, valor, quantidade, descricao, id_comerciante)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
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
            statement.setDouble(6, alimento.getValor());
            statement.setInt(7, alimento.getQuantidade());
            statement.setString(8, alimento.getDescricao());
            statement.setInt(9, alimento.getComerciante().getIdComerciante());

            statement.executeUpdate();

            System.out.println("Alimento inserido com sucesso");

        } catch (SQLException e) {
            System.out.println("Erro ao inserir alimento: " + e.getMessage());
        }
    }

    public ArrayList<Alimento> listarPorComerciante(int idComerciante) {
        ArrayList<Alimento> alimentos = new ArrayList<>();

        String sql = """
            SELECT *
            FROM alimento
            WHERE id_comerciante = ?
            ORDER BY nome_alimento
            """;

        try (
                Connection conn = Conexao.getConnection();
                PreparedStatement statement = conn.prepareStatement(sql)
        ) {
            statement.setInt(1, idComerciante);

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Comerciante comerciante = new Comerciante();
                    comerciante.setIdComerciante(idComerciante);

                    Alimento alimento = new Alimento();

                    alimento.setIdAlimento(rs.getInt("id_alimento"));
                    alimento.setNomeAlimento(rs.getString("nome_alimento"));
                    alimento.setCategoria(rs.getString("categoria"));
                    alimento.setValidade(rs.getDate("validade").toLocalDate());
                    alimento.setImagemAlimento(rs.getString("imagem_alimento"));
                    alimento.setPromocao(rs.getBoolean("promocao"));
                    alimento.setValor(rs.getDouble("valor"));
                    alimento.setQuantidade(rs.getInt("quantidade"));
                    alimento.setDescricao(rs.getString("descricao"));
                    alimento.setComerciante(comerciante);

                    alimentos.add(alimento);
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar alimentos: " + e.getMessage());
        }

        return alimentos;
    }

    public void marcarComoPromocao(int idAlimento) {
        String sql = "UPDATE alimento SET promocao = true WHERE id_alimento = ?";

        try (
                Connection conn = Conexao.getConnection();
                PreparedStatement statement = conn.prepareStatement(sql)
        ) {
            statement.setInt(1, idAlimento);
            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao marcar alimento como promoção: " + e.getMessage());
        }
    }
}