package controller;

import dao.PromocaoDAO;
import model.Promocao;
import model.Solicitacao;

public class PromocaoController {
    private PromocaoDAO dao = new PromocaoDAO();


    public void cadastrarPromocao(Promocao promocao){
        promocao.setIdPromocao(promocao.getIdPromocao());
        dao.inserir(promocao);
    }

}
