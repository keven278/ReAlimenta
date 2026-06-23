package controller;

import dao.PromocaoDAO;
import model.Promocao;

import java.util.List;

public class PromocaoController {

    private PromocaoDAO promocaoDAO;

    public PromocaoController() {
        this.promocaoDAO = new PromocaoDAO();
    }

    public void cadastrarPromocao(Promocao promocao) {
        promocaoDAO.inserirPromocao(promocao);
    }

    public List<Promocao> listarPromocoesAtivas() {
        return promocaoDAO.listarPromocoesAtivas();
    }

    public List<Promocao> listarPromocoesPorComerciante(int idComerciante) {
        return promocaoDAO.listarPromocoesPorComerciante(idComerciante);
    }
}