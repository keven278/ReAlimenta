package controller;

import dao.SolicitacaoDAO;
import model.Alimento;
import model.Consumidor;
import model.Solicitacao;

    public class SolicitacaoController {
        private SolicitacaoDAO dao = new SolicitacaoDAO();


        public void cadastrarSolicitacao(Consumidor consumidor) {
            Solicitacao solicitacao = new Solicitacao();
            solicitacao.setConsumidor(consumidor);
            dao.inserir(solicitacao);
        }

        public Solicitacao consultarSolicitacao(Consumidor consumidor) {
            return dao.buscarPorConsumidor(consumidor);
        }

        public void atualizarSolicitacao(int id_solicitacao, Consumidor consumidor) {
            Solicitacao solicitacao = new Solicitacao();
            solicitacao.setIdSolicitacao(id_solicitacao);
            solicitacao.setConsumidor(consumidor);
            dao.atualizar(solicitacao);

            System.out.println("Mensagem para View: Dados atualizados com sucesso no sistema!");
        }

        public void excluirSolicitacao(int id_solicitacao) {
            dao.excluir(id_solicitacao);
            System.out.println("Mensagem para View: Registro removido com sucesso do sistema!");
        }


    }





