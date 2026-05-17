package view;
import util.EstiloReAlimenta;
import util.EstiloReAlimenta.RoundedPanel;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.List;

public class TelaNotificacoes extends JFrame {
    private final String nomeUsuario;
    //  TIPOS DE NOTIFICAÇÃO
    // Futuramente virão do backend com enum Tipo
    private static final String TIPO_SOLICITACAO  = "SOLICITACAO";
    private static final String TIPO_DOACAO       = "DOACAO";
    private static final String TIPO_PROMOCAO     = "PROMOCAO";
    private static final String TIPO_SISTEMA      = "SISTEMA";

    // MODELO DE DADOS
    // Futuramente substituir por model/Notificacao.java + DAO
    private static class DadosNotificacao {
        String titulo;
        String descricao;
        String dataHora;
        String tipo;
        boolean lida;

        DadosNotificacao(String titulo, String descricao, String dataHora, String tipo, boolean lida) {
            this.titulo    = titulo;
            this.descricao = descricao;
            this.dataHora  = dataHora;
            this.tipo      = tipo;
            this.lida      = lida;
        }
    }

    // Dados mockados — substituir futuramente por List<Notificacao> via DAO
    private final List<DadosNotificacao> notificacoes = Arrays.asList(
            new DadosNotificacao(
                    "Solicita\u00e7\u00e3o confirmada!",
                    "Sua solicita\u00e7\u00e3o de \"Bananas Maduras\" foi confirmada. Retire at\u00e9 18h00.",
                    "Hoje \u00e0s 14h32",
                    TIPO_SOLICITACAO,
                    false
            ),
            new DadosNotificacao(
                    "Nova doa\u00e7\u00e3o dispon\u00edvel",
                    "A Padaria P\u00e3o de Ouro adicionou 30 p\u00e3es frescos para doa\u00e7\u00e3o. Corra!",
                    "Hoje \u00e0s 11h15",
                    TIPO_DOACAO,
                    false
            ),
            new DadosNotificacao(
                    "Promo\u00e7\u00e3o imperd\u00edvel",
                    "50% de desconto em Bolo de Cenoura na Confeitaria Doce Arte. V\u00e1lido at\u00e9 18h.",
                    "Hoje \u00e0s 09h00",
                    TIPO_PROMOCAO,
                    false
            ),
            new DadosNotificacao(
                    "Solicita\u00e7\u00e3o retirada",
                    "Voc\u00ea retirou com sucesso a \"Lasanha \u00e0 Bolonhesa\" do Restaurante da Mara. Obrigado!",
                    "Ontem \u00e0s 20h10",
                    TIPO_SOLICITACAO,
                    true
            ),
            new DadosNotificacao(
                    "Bem-vindo ao ReAlimenta!",
                    "Seu cadastro foi realizado com sucesso. Explore promo\u00e7\u00f5es e doa\u00e7\u00f5es perto de voc\u00ea.",
                    "14/05/2025",
                    TIPO_SISTEMA,
                    true
            ),
            new DadosNotificacao(
                    "Nova promo\u00e7\u00e3o na sua regi\u00e3o",
                    "A Pizzaria Bela Napoli est\u00e1 com 30% de desconto em pizzas. Dispon\u00edvel at\u00e9 21h30.",
                    "13/05/2025",
                    TIPO_PROMOCAO,
                    true
            )
    );

    // Painel de listagem referenciado para atualização de estado (marcar como lido)
    private JPanel painelListagem;

    // CONSTRUTOR
    public TelaNotificacoes(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
        configurarJanela();
        construirInterface();
        setVisible(true);
    }

    // CONFIGURAÇÃO DA JANELA
    private void configurarJanela() {
        setTitle("ReAlimenta | Notifica\u00e7\u00f5es");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 680);
        setLocationRelativeTo(null);
        setResizable(true);
        setMinimumSize(new Dimension(860, 560));
        getContentPane().setBackground(EstiloReAlimenta.FUNDO);
        setLayout(new BorderLayout());
    }
    private void construirInterface() {
        add(criarSidebar(), BorderLayout.WEST);
        add(criarConteudo(), BorderLayout.CENTER);
    }
    // SIDEBAR
    private JPanel criarSidebar() {
        JPanel sidebar = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setPaint(new GradientPaint(
                        0, 0, EstiloReAlimenta.VERDE_PRINCIPAL,
                        0, getHeight(), new Color(0x0D, 0x4A, 0x2C)));
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        sidebar.setPreferredSize(new Dimension(220, 0));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(BorderFactory.createEmptyBorder(28, 16, 28, 16));

        sidebar.add(EstiloReAlimenta.criarLogoMaca());
        sidebar.add(Box.createVerticalStrut(8));

        JLabel lblApp = new JLabel("ReAlimenta");
        lblApp.setFont(new Font("Segoe UI", Font.BOLD, 17));
        lblApp.setForeground(EstiloReAlimenta.BRANCO);
        lblApp.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebar.add(lblApp);
        sidebar.add(Box.createVerticalStrut(4));

        JLabel lblUsuario = new JLabel(nomeUsuario);
        lblUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblUsuario.setForeground(new Color(255, 255, 255, 180));
        lblUsuario.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebar.add(lblUsuario);
        sidebar.add(Box.createVerticalStrut(6));

        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(255, 255, 255, 50));
        sep.setMaximumSize(new Dimension(180, 1));
        sep.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebar.add(sep);
        sidebar.add(Box.createVerticalStrut(18));

        sidebar.add(EstiloReAlimenta.criarItemMenu(FontAwesomeSolid.HOME, "In\u00edcio", new Runnable() {
            @Override public void run() { navegarDashboard(); }
        }));
        sidebar.add(EstiloReAlimenta.criarItemMenu(FontAwesomeSolid.TAG, "Promo\u00e7\u00f5es", new Runnable() {
            @Override public void run() { navegarPromocoes(); }
        }));
        sidebar.add(EstiloReAlimenta.criarItemMenu(FontAwesomeSolid.GIFT, "Doa\u00e7\u00f5es", new Runnable() {
            @Override public void run() { navegarDoacoes(); }
        }));
        sidebar.add(EstiloReAlimenta.criarItemMenu(FontAwesomeSolid.CLIPBOARD_LIST, "Minhas Solicita\u00e7\u00f5es", new Runnable() {
            @Override public void run() { navegarSolicitacoes(); }
        }));
        // Item atual destacado
        sidebar.add(criarItemMenuAtivo(FontAwesomeSolid.BELL, "Notifica\u00e7\u00f5es"));

        sidebar.add(Box.createVerticalGlue());

        sidebar.add(EstiloReAlimenta.criarItemMenu(FontAwesomeSolid.SIGN_OUT_ALT, "Sair", new Runnable() {
            @Override public void run() { confirmarSaida(); }
        }));

        sidebar.add(Box.createVerticalStrut(10));
        JLabel lblVersao = new JLabel("\u26e8  Vers\u00e3o 1.0.0");
        lblVersao.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        lblVersao.setForeground(new Color(255, 255, 255, 100));
        lblVersao.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebar.add(lblVersao);

        return sidebar;
    }

    private JPanel criarItemMenuAtivo(FontAwesomeSolid icone, String texto) {
        JPanel item = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255, 255, 255, 45));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        item.setOpaque(false);
        item.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        item.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));
        item.add(EstiloReAlimenta.criarIcone(icone, 16, EstiloReAlimenta.BRANCO));
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lbl.setForeground(EstiloReAlimenta.BRANCO);
        item.add(lbl);
        return item;
    }

    // CONTEÚDO PRINCIPAL
    private JComponent criarConteudo() {
        JPanel conteudo = new JPanel();
        conteudo.setLayout(new BoxLayout(conteudo, BoxLayout.Y_AXIS));
        conteudo.setBackground(EstiloReAlimenta.FUNDO);
        conteudo.setBorder(BorderFactory.createEmptyBorder(28, 32, 28, 32));

        conteudo.add(criarHeader());
        conteudo.add(Box.createVerticalStrut(20));

        painelListagem = criarListagem();
        conteudo.add(painelListagem);
        conteudo.add(Box.createVerticalStrut(24));

        JScrollPane scroll = new JScrollPane(conteudo);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(EstiloReAlimenta.FUNDO);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.getVerticalScrollBar().setUnitIncrement(14);
        return scroll;
    }

    // HEADER
    private JPanel criarHeader() {
        JPanel header = new JPanel(new BorderLayout(0, 4));
        header.setOpaque(false);
        header.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));

        JPanel esq = new JPanel();
        esq.setOpaque(false);
        esq.setLayout(new BoxLayout(esq, BoxLayout.Y_AXIS));

        JPanel titLinha = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        titLinha.setOpaque(false);
        titLinha.add(EstiloReAlimenta.criarIcone(FontAwesomeSolid.BELL, 20, EstiloReAlimenta.VERDE_PRINCIPAL));
        JLabel lblTit = new JLabel("Notifica\u00e7\u00f5es");
        lblTit.setFont(EstiloReAlimenta.FONTE_TITULO);
        lblTit.setForeground(EstiloReAlimenta.TEXTO);
        titLinha.add(lblTit);

        // Badge com quantidade de não lidas
        long naoLidas = contarNaoLidas();
        if (naoLidas > 0) {
            JLabel badgeNaoLidas = criarBadgeContador((int) naoLidas);
            titLinha.add(badgeNaoLidas);
        }
        esq.add(titLinha);

        JLabel lblSub = new JLabel("Veja tudo que acontece no ReAlimenta.");
        lblSub.setFont(EstiloReAlimenta.FONTE_SUBTITULO);
        lblSub.setForeground(EstiloReAlimenta.TEXTO_SUAVE);
        esq.add(lblSub);

        header.add(esq, BorderLayout.WEST);

        // Botão "Marcar todas como lidas" (direita)
        JButton btnMarcar = EstiloReAlimenta.criarBotaoSecundario("Marcar todas como lidas", new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) { marcarTodasComoLidas(); }
        });
        btnMarcar.setMaximumSize(new Dimension(220, 36));
        btnMarcar.setPreferredSize(new Dimension(220, 36));
        header.add(btnMarcar, BorderLayout.EAST);

        return header;
    }

    // LISTAGEM
    private JPanel criarListagem() {
        JPanel painel = new JPanel();
        painel.setOpaque(false);
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));

        if (notificacoes.isEmpty()) {
            JLabel lblVazio = new JLabel("Voc\u00ea n\u00e3o tem notifica\u00e7\u00f5es.");
            lblVazio.setFont(EstiloReAlimenta.FONTE_SUBTITULO);
            lblVazio.setForeground(EstiloReAlimenta.TEXTO_SUAVE);
            lblVazio.setAlignmentX(Component.CENTER_ALIGNMENT);
            painel.add(Box.createVerticalStrut(40));
            painel.add(lblVazio);
            return painel;
        }

        for (DadosNotificacao n : notificacoes) {
            painel.add(criarCard(n));
            painel.add(Box.createVerticalStrut(10));
        }
        return painel;
    }

    private JPanel criarCard(final DadosNotificacao n) {
        // Cor de fundo: não lida = fundo levemente verde, lida = branco
        final Color corFundo     = n.lida ? EstiloReAlimenta.BRANCO : new Color(0xF0, 0xFB, 0xF4);
        final Color corFundoHov  = n.lida ? new Color(0xF8, 0xFF, 0xFA) : new Color(0xE6, 0xF7, 0xED);

        RoundedPanel card = new RoundedPanel(12, corFundo) {
            boolean hover = false;
            {
                addMouseListener(new MouseAdapter() {
                    @Override public void mouseEntered(MouseEvent e) { hover = true;  repaint(); }
                    @Override public void mouseExited (MouseEvent e) { hover = false; repaint(); }
                });
            }
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                for (int i = 5; i > 0; i--) {
                    g2.setColor(new Color(0, 0, 0, hover ? 10 : 5));
                    g2.fillRoundRect(i, i, getWidth() - i * 2, getHeight() - i * 2, 12 + i, 12 + i);
                }
                g2.setColor(hover ? corFundoHov : corFundo);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);

                // Borda lateral colorida para não lidas
                if (!n.lida) {
                    g2.setColor(EstiloReAlimenta.VERDE_PRINCIPAL);
                    g2.fillRoundRect(0, 0, 4, getHeight(), 4, 4);
                }
                g2.dispose();
                super.paintComponent(g);
            }
        };
        card.setLayout(new BorderLayout(0, 0));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));

        // Ícone esquerdo por tipo
        JPanel painelIcone = new JPanel(new GridBagLayout());
        painelIcone.setOpaque(false);
        painelIcone.setPreferredSize(new Dimension(68, 0));
        painelIcone.setBorder(BorderFactory.createEmptyBorder(0, n.lida ? 10 : 14, 0, 0));

        FontAwesomeSolid iconeTipo = resolverIconeTipo(n.tipo);
        Color corTipo = resolverCorTipo(n.tipo);
        JLabel lblIcone = EstiloReAlimenta.criarIcone(iconeTipo, 22, corTipo);
        painelIcone.add(lblIcone);

        card.add(painelIcone, BorderLayout.WEST);

        // Informações centrais
        JPanel info = new JPanel();
        info.setOpaque(false);
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setBorder(BorderFactory.createEmptyBorder(14, 8, 14, 10));

        JPanel linTitulo = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        linTitulo.setOpaque(false);

        JLabel lblTitulo = new JLabel(n.titulo);
        lblTitulo.setFont(new Font("Segoe UI", n.lida ? Font.PLAIN : Font.BOLD, 14));
        lblTitulo.setForeground(EstiloReAlimenta.TEXTO);
        linTitulo.add(lblTitulo);

        if (!n.lida) {
            linTitulo.add(criarPontoNaoLido());
        }
        info.add(linTitulo);

        JLabel lblDesc = new JLabel(n.descricao);
        lblDesc.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblDesc.setForeground(EstiloReAlimenta.TEXTO_SUAVE);
        info.add(lblDesc);

        card.add(info, BorderLayout.CENTER);

        // Data/hora (direita)
        JPanel dir = new JPanel();
        dir.setOpaque(false);
        dir.setLayout(new BoxLayout(dir, BoxLayout.Y_AXIS));
        dir.setBorder(BorderFactory.createEmptyBorder(14, 0, 14, 20));
        dir.setPreferredSize(new Dimension(120, 0));

        JLabel lblData = new JLabel(n.dataHora);
        lblData.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblData.setForeground(EstiloReAlimenta.TEXTO_SUAVE);
        lblData.setAlignmentX(Component.RIGHT_ALIGNMENT);
        dir.add(lblData);

        // Tag do tipo
        dir.add(Box.createVerticalStrut(6));
        JLabel tagTipo = criarTagTipo(n.tipo, corTipo);
        tagTipo.setAlignmentX(Component.RIGHT_ALIGNMENT);
        dir.add(tagTipo);

        card.add(dir, BorderLayout.EAST);
        return card;
    }

    // HELPERS VISUAIS
    private JLabel criarBadgeContador(final int quantidade) {
        JLabel badge = new JLabel(String.valueOf(quantidade)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(0xE7, 0x4C, 0x3C));
                g2.fillOval(0, 0, getWidth(), getHeight());
                g2.dispose();
                super.paintComponent(g);
            }
        };
        badge.setFont(new Font("Segoe UI", Font.BOLD, 10));
        badge.setForeground(Color.WHITE);
        badge.setOpaque(false);
        badge.setHorizontalAlignment(SwingConstants.CENTER);
        badge.setPreferredSize(new Dimension(20, 20));
        return badge;
    }

    private JLabel criarPontoNaoLido() {
        JLabel ponto = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(EstiloReAlimenta.VERDE_PRINCIPAL);
                g2.fillOval(0, 4, 8, 8);
                g2.dispose();
            }
        };
        ponto.setPreferredSize(new Dimension(10, 16));
        ponto.setOpaque(false);
        return ponto;
    }

    private JLabel criarTagTipo(String texto, final Color cor) {
        JLabel tag = new JLabel(resolverLabelTipo(texto)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // fundo levemente colorido
                Color fundo = new Color(cor.getRed(), cor.getGreen(), cor.getBlue(), 30);
                g2.setColor(fundo);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 6, 6);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        tag.setFont(new Font("Segoe UI", Font.BOLD, 9));
        tag.setForeground(cor);
        tag.setOpaque(false);
        tag.setBorder(BorderFactory.createEmptyBorder(2, 6, 2, 6));
        tag.setHorizontalAlignment(SwingConstants.CENTER);
        return tag;
    }

    // RESOLVERS
    private FontAwesomeSolid resolverIconeTipo(String tipo) {
        if (TIPO_SOLICITACAO.equals(tipo))  return FontAwesomeSolid.CLIPBOARD_LIST;
        if (TIPO_DOACAO.equals(tipo))       return FontAwesomeSolid.GIFT;
        if (TIPO_PROMOCAO.equals(tipo))     return FontAwesomeSolid.TAG;
        return FontAwesomeSolid.INFO_CIRCLE; // SISTEMA
    }

    private Color resolverCorTipo(String tipo) {
        if (TIPO_SOLICITACAO.equals(tipo))  return new Color(0x2E, 0x86, 0xC1);
        if (TIPO_DOACAO.equals(tipo))       return new Color(0x27, 0xAE, 0x60);
        if (TIPO_PROMOCAO.equals(tipo))     return new Color(0xE6, 0x7E, 0x22);
        return EstiloReAlimenta.TEXTO_SUAVE; // SISTEMA
    }

    private String resolverLabelTipo(String tipo) {
        if (TIPO_SOLICITACAO.equals(tipo))  return "Solicita\u00e7\u00e3o";
        if (TIPO_DOACAO.equals(tipo))       return "Doa\u00e7\u00e3o";
        if (TIPO_PROMOCAO.equals(tipo))     return "Promo\u00e7\u00e3o";
        return "Sistema";
    }

    private long contarNaoLidas() {
        int count = 0;
        for (DadosNotificacao n : notificacoes) {
            if (!n.lida) count++;
        }
        return count;
    }

    // AÇÕES
    private void marcarTodasComoLidas() {
        for (DadosNotificacao n : notificacoes) {
            n.lida = true;
        }
        JOptionPane.showMessageDialog(this,
                "Todas as notifica\u00e7\u00f5es foram marcadas como lidas.\n(Atualiza\u00e7\u00e3o via backend \u2014 em desenvolvimento)",
                "Notifica\u00e7\u00f5es", JOptionPane.INFORMATION_MESSAGE);
        // Recarrega a tela para refletir estado atualizado
        dispose();
        new TelaNotificacoes(nomeUsuario);
    }

    private void navegarDashboard()    { dispose(); new TelaDashboardConsumidor(nomeUsuario); }
    private void navegarPromocoes()    { dispose(); new TelaPromocao(nomeUsuario); }
    private void navegarDoacoes()      { dispose(); new TelaDoacoes(nomeUsuario); }
    private void navegarSolicitacoes() { dispose(); new TelaMinhasSolicitacoes(nomeUsuario); }

    private void confirmarSaida() {
        int op = JOptionPane.showConfirmDialog(this,
                "Deseja realmente sair do ReAlimenta?",
                "Sair", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (op == JOptionPane.YES_OPTION) {
            dispose();
            new TelaLogin();
        }
    }
}