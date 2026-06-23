package dao;

import model.Promocao;
import util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import java.util.List;
import model.Alimento;
import model.Comerciante;

import java.sql.ResultSet;
import java.util.ArrayList;

public class PromocaoDAO {

    public void inserirPromocao(Promocao promocao) {

        String sql = """
                INSERT INTO promocao
                (id_alimento, inicio_promocao, fim_promocao, percentual_desconto)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection conn = Conexao.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1,promocao.getAlimento().getIdAlimento());
            statement.setDate(2,Date.valueOf(promocao.getInicioPromocao()));
            statement.setDate(3,Date.valueOf(promocao.getFimPromocao()));
            statement.setInt(4,promocao.getPercentualDesconto());

            statement.executeUpdate();

            AlimentoDAO alimentoDAO = new AlimentoDAO();
            alimentoDAO.marcarComoPromocao(promocao.getAlimento().getIdAlimento());

            System.out.println("Promoção cadastrada com sucesso.");

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar promoção: " + e.getMessage());
        }
    }
    public List<Promocao> listarPromocoesAtivas() {
        List<Promocao> promocoes = new ArrayList<>();

        String sql = """
            SELECT p.id_promocao, p.inicio_promocao, p.fim_promocao, p.percentual_desconto,
                   a.id_alimento, a.nome_alimento, a.categoria, a.validade,
                   a.imagem_alimento, a.promocao, a.valor, a.quantidade, a.descricao,
                   c.id_comerciante
            FROM promocao p
            INNER JOIN alimento a ON p.id_alimento = a.id_alimento
            INNER JOIN comerciante c ON a.id_comerciante = c.id_comerciante
            WHERE CURDATE() BETWEEN p.inicio_promocao AND p.fim_promocao
            """;

        try (
                Connection conn = Conexao.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()
        ) {
            while (rs.next()) {
                Comerciante comerciante = new Comerciante();
                comerciante.setIdComerciante(rs.getInt("id_comerciante"));

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

                Promocao promocao = new Promocao();
                promocao.setIdPromocao(rs.getInt("id_promocao"));
                promocao.setAlimento(alimento);
                promocao.setInicioPromocao(rs.getDate("inicio_promocao").toLocalDate());
                promocao.setFimPromocao(rs.getDate("fim_promocao").toLocalDate());
                promocao.setPercentualDesconto(rs.getInt("percentual_desconto"));

                promocoes.add(promocao);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar promoções ativas: " + e.getMessage());
        }

        return promocoes;
    }
    public List<Promocao> listarPromocoesPorComerciante(int id_comerciante) {
        List<Promocao> promocoes = new ArrayList<>();

        String sql = """
            SELECT p.id_promocao, p.inicio_promocao, p.fim_promocao, p.percentual_desconto,
                   a.id_alimento, a.nome_alimento, a.categoria, a.validade,
                   a.imagem_alimento, a.promocao, a.valor, a.quantidade, a.descricao,
                   c.id_comerciante
            FROM promocao p
            INNER JOIN alimento a ON p.id_alimento = a.id_alimento
            INNER JOIN comerciante c ON a.id_comerciante = c.id_comerciante
            WHERE c.id_comerciante = ?
            ORDER BY p.inicio_promocao DESC
            """;

        try (
                Connection conn = Conexao.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, id_comerciante);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Comerciante comerciante = new Comerciante();
                    comerciante.setIdComerciante(rs.getInt("id_comerciante"));

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

                    Promocao promocao = new Promocao();
                    promocao.setIdPromocao(rs.getInt("id_promocao"));
                    promocao.setAlimento(alimento);
                    promocao.setInicioPromocao(rs.getDate("inicio_promocao").toLocalDate());
                    promocao.setFimPromocao(rs.getDate("fim_promocao").toLocalDate());
                    promocao.setPercentualDesconto(rs.getInt("percentual_desconto"));

                    promocoes.add(promocao);
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar promoções do comerciante: " + e.getMessage());
        }

        return promocoes;
    }
}