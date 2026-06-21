package view;
import model.Comerciante;
import util.EstiloReAlimenta;
import util.EstiloReAlimenta.RoundedPanel;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.swing.FontIcon;

public class TelaNotificacoesComerciante extends JFrame {
    // Mock de notificações — substituir por NotificacaoDAO.listarPorComerciante()
    private List<Notificacao> listaNotificacoes = new ArrayList<Notificacao>();
    private JPanel painelLista;

    // Construtor
    private Comerciante comerciante;
    public TelaNotificacoesComerciante(Comerciante comerciante) {
        this.comerciante=comerciante;
        popularMocks();
        configurarJanela();
        construirLayout();
        setVisible(true);
    }

    // Configuração do JFrame
    private void configurarJanela() {
        setTitle("ReAlimenta | Notificações");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(1100, 700));
        setSize(1280, 800);
        setLocationRelativeTo(null);
        getContentPane().setBackground(EstiloReAlimenta.FUNDO);
    }

    // Layout principal
    private void construirLayout() {
        setLayout(new BorderLayout());
        add(criarSidebar(), BorderLayout.WEST);
        add(criarConteudo(), BorderLayout.CENTER);
    }

    // Sidebar — Notificações em destaque
    // Item ativo: reutiliza EstiloReAlimenta.criarItemMenu() + destaque de borda
    private JPanel criarItemMenuAtivo(FontAwesomeSolid icone, String texto) {
        JPanel item = EstiloReAlimenta.criarItemMenu(icone, texto, null);
        item.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 60), 1, true),
                BorderFactory.createEmptyBorder(0, 8, 0, 8)));
        return item;
    }
    private JPanel criarSidebar() {
        JPanel sidebar = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setPaint(new GradientPaint(0, 0, EstiloReAlimenta.VERDE_PRINCIPAL, 0, getHeight(), new Color(0x0A, 0x3D, 0x26)));
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(220, 0));
        sidebar.setBorder(new EmptyBorder(24, 12, 24, 12));

        // Logo + nome
        JLabel logo = EstiloReAlimenta.criarLogoMaca();
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel nomeSistema = new JLabel("ReAlimenta");
        nomeSistema.setFont(new Font("Segoe UI", Font.BOLD, 18));
        nomeSistema.setForeground(EstiloReAlimenta.BRANCO);
        nomeSistema.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel perfil = new JLabel("Comerciante");
        perfil.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        perfil.setForeground(new Color(255, 255, 255, 180));
        perfil.setAlignmentX(Component.CENTER_ALIGNMENT);

        sidebar.add(logo);
        sidebar.add(Box.createVerticalStrut(8));
        sidebar.add(nomeSistema);
        sidebar.add(perfil);
        sidebar.add(Box.createVerticalStrut(32));

        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(255, 255, 255, 40));
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        sidebar.add(sep);
        sidebar.add(Box.createVerticalStrut(16));
        sidebar.add(EstiloReAlimenta.criarItemMenu(FontAwesomeSolid.HOME, "Dashboard", this::navegarDashboard));
        sidebar.add(Box.createVerticalStrut(4));
        sidebar.add(EstiloReAlimenta.criarItemMenu(FontAwesomeSolid.APPLE_ALT, "Alimentos", this::navegarAlimentos));
        sidebar.add(Box.createVerticalStrut(4));
        sidebar.add(EstiloReAlimenta.criarItemMenu(FontAwesomeSolid.TAG, "Promoções", this::navegarPromocoes));
        sidebar.add(Box.createVerticalStrut(4));
        sidebar.add(EstiloReAlimenta.criarItemMenu(FontAwesomeSolid.HAND_HOLDING_HEART, "Doações", this::navegarDoacoes));
        sidebar.add(Box.createVerticalStrut(4));
        sidebar.add(EstiloReAlimenta.criarItemMenu(FontAwesomeSolid.INBOX, "Solicitações", this::navegarSolicitacoes));
        sidebar.add(Box.createVerticalStrut(4));
        sidebar.add(criarItemMenuAtivo(FontAwesomeSolid.BELL, "Notificações"));
        sidebar.add(Box.createVerticalStrut(4));
        sidebar.add(Box.createVerticalGlue());

        JSeparator sep2 = new JSeparator();
        sep2.setForeground(new Color(255, 255, 255, 40));
        sep2.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        sidebar.add(sep2);
        sidebar.add(Box.createVerticalStrut(12));

        sidebar.add(EstiloReAlimenta.criarItemMenu(FontAwesomeSolid.SIGN_OUT_ALT, "Sair",
                this::sair));
        return sidebar;
    }

    // Conteúdo central com scroll
    private JScrollPane criarConteudo() {
        JPanel conteudo = new JPanel();
        conteudo.setLayout(new BoxLayout(conteudo, BoxLayout.Y_AXIS));
        conteudo.setBackground(EstiloReAlimenta.FUNDO);
        conteudo.setBorder(new EmptyBorder(32, 32, 32, 32));

        // Cabeçalho da página
        conteudo.add(criarCabecalhoPagina());
        conteudo.add(Box.createVerticalStrut(24));

        // Painel de notificações (dinâmico)
        painelLista = new JPanel();
        painelLista.setLayout(new BoxLayout(painelLista, BoxLayout.Y_AXIS));
        painelLista.setOpaque(false);
        renderizarNotificacoes();
        conteudo.add(painelLista);

        JScrollPane scroll = new JScrollPane(conteudo);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(EstiloReAlimenta.FUNDO);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        return scroll;
    }

    // Cabeçalho da página
    private JPanel criarCabecalhoPagina() {
        JPanel p = new JPanel(new BorderLayout(0, 0));
        p.setOpaque(false);
        p.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Título + subtítulo
        JPanel esq = new JPanel();
        esq.setOpaque(false);
        esq.setLayout(new BoxLayout(esq, BoxLayout.Y_AXIS));
        esq.add(EstiloReAlimenta.criarTitulo("Notificações"));
        esq.add(Box.createVerticalStrut(4));
        esq.add(EstiloReAlimenta.criarSubtitulo("Acompanhe atividades relacionadas ao seu negócio."));
        p.add(esq, BorderLayout.WEST);

        // Botão "Marcar todas como lidas"
        JButton btnMarcar = criarBotaoMarcarLidas();
        JPanel painelBotao = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        painelBotao.setOpaque(false);
        painelBotao.add(btnMarcar);
        p.add(painelBotao, BorderLayout.EAST);
        return p;
    }

    private JButton criarBotaoMarcarLidas() {
        JButton btn = new JButton() {
            boolean hover = false;
            {
                addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) { hover = true;  repaint(); }
                    public void mouseExited(MouseEvent e)  { hover = false; repaint(); }
                });
            }
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color base = hover ? EstiloReAlimenta.VERDE_CLARO : EstiloReAlimenta.BRANCO;
                g2.setColor(base);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.setColor(EstiloReAlimenta.VERDE_PRINCIPAL);
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setIcon(FontIcon.of(FontAwesomeSolid.CHECK_DOUBLE, 14, EstiloReAlimenta.VERDE_PRINCIPAL));
        btn.setText("  Marcar todas como lidas");
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setForeground(EstiloReAlimenta.VERDE_PRINCIPAL);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(220, 42));
        btn.addActionListener(e -> marcarTodasComoLidas());
        return btn;
    }

    // Renderização da lista de notificações
    private void renderizarNotificacoes() {
        painelLista.removeAll();

        long naoLidas = 0;
        for (Notificacao n : listaNotificacoes) {
            if (!n.lida) naoLidas++;
        }

        // Contador de não lidas
        if (naoLidas > 0) {
            JPanel linhaContador = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
            linhaContador.setOpaque(false);
            linhaContador.setAlignmentX(Component.LEFT_ALIGNMENT);

            JLabel lblContador = new JLabel(naoLidas + " não lida" + (naoLidas > 1 ? "s" : ""));
            lblContador.setFont(new Font("Segoe UI", Font.BOLD, 13));
            lblContador.setForeground(EstiloReAlimenta.TEXTO_SUAVE);
            linhaContador.add(lblContador);

            painelLista.add(linhaContador);
            painelLista.add(Box.createVerticalStrut(14));
        }

        if (listaNotificacoes.isEmpty()) {
            JLabel vazio = new JLabel("Nenhuma notificação no momento.");
            vazio.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            vazio.setForeground(EstiloReAlimenta.TEXTO_SUAVE);
            vazio.setAlignmentX(Component.LEFT_ALIGNMENT);
            painelLista.add(Box.createVerticalStrut(32));
            painelLista.add(vazio);
        } else {
            for (Notificacao n : listaNotificacoes) {
                painelLista.add(criarCardNotificacao(n));
                painelLista.add(Box.createVerticalStrut(10));
            }
        }
        painelLista.revalidate();
        painelLista.repaint();
    }

    // Card individual de notificação
    private JPanel criarCardNotificacao(Notificacao n) {
        // Cor e ícone conforme o tipo
        Color corTipo    = resolverCor(n.tipo);
        FontAwesomeSolid icone = resolverIcone(n.tipo);

        // Fundo do card: levemente colorido se não lida
        Color fundoCard = n.lida ? EstiloReAlimenta.BRANCO : misturarCores(corTipo, EstiloReAlimenta.BRANCO, 0.06f);
        RoundedPanel card = new RoundedPanel(12, fundoCard) {
            boolean hover = false;
            {
                addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) { hover = true;  repaint(); }
                    public void mouseExited(MouseEvent e)  { hover = false; repaint(); }
                    public void mouseClicked(MouseEvent e) { marcarComoLida(n); }
                });
            }
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Indicador colorido lateral
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(corTipo);
                g2.fillRoundRect(0, 0, 5, getHeight(), 6, 6);
                // Overlay de hover
                if (hover) {
                    g2.setColor(new Color(0, 0, 0, 8));
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                }
                g2.dispose();
            }
        };
        card.setLayout(new BorderLayout(16, 0));
        card.setBorder(new EmptyBorder(16, 20, 16, 20));
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);

        // — Ícone esquerdo —
        JPanel painelIcone = new JPanel();
        painelIcone.setOpaque(false);
        painelIcone.setLayout(new BoxLayout(painelIcone, BoxLayout.Y_AXIS));

        JPanel circulo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(misturarCores(corTipo, Color.WHITE, 0.18f));
                g2.fillOval(0, 0, getWidth(), getHeight());
                g2.dispose();
                super.paintComponent(g);
            }
        };
        circulo.setOpaque(false);
        circulo.setLayout(new BorderLayout());
        circulo.setPreferredSize(new Dimension(44, 44));
        circulo.setMinimumSize(new Dimension(44, 44));
        circulo.setMaximumSize(new Dimension(44, 44));

        JLabel lblIcone = EstiloReAlimenta.criarIcone(icone, 20, corTipo);
        lblIcone.setHorizontalAlignment(SwingConstants.CENTER);
        lblIcone.setVerticalAlignment(SwingConstants.CENTER);
        circulo.add(lblIcone, BorderLayout.CENTER);

        painelIcone.add(Box.createVerticalGlue());
        painelIcone.add(circulo);
        painelIcone.add(Box.createVerticalGlue());
        card.add(painelIcone, BorderLayout.WEST);

        // Corpo central
        JPanel corpo = new JPanel();
        corpo.setOpaque(false);
        corpo.setLayout(new BoxLayout(corpo, BoxLayout.Y_AXIS));

        JPanel linhaTitulo = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        linhaTitulo.setOpaque(false);

        JLabel lblTitulo = new JLabel(n.titulo);
        lblTitulo.setFont(new Font("Segoe UI", n.lida ? Font.PLAIN : Font.BOLD, 14));
        lblTitulo.setForeground(EstiloReAlimenta.TEXTO);
        linhaTitulo.add(lblTitulo);

        // Bolinha de não lida
        if (!n.lida) {
            JLabel bolinha = new JLabel() {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(corTipo);
                    g2.fillOval(0, (getHeight() - 8) / 2, 8, 8);
                    g2.dispose();
                }
            };
            bolinha.setPreferredSize(new Dimension(10, 20));
            linhaTitulo.add(bolinha);
        }
        corpo.add(linhaTitulo);
        corpo.add(Box.createVerticalStrut(4));

        JLabel lblDescricao = new JLabel(n.descricao);
        lblDescricao.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblDescricao.setForeground(EstiloReAlimenta.TEXTO_SUAVE);
        lblDescricao.setAlignmentX(Component.LEFT_ALIGNMENT);
        corpo.add(lblDescricao);
        card.add(corpo, BorderLayout.CENTER);

        // — Lado direito: horário + botão marcar lida —
        JPanel dir = new JPanel();
        dir.setOpaque(false);
        dir.setLayout(new BoxLayout(dir, BoxLayout.Y_AXIS));

        JLabel lblHorario = new JLabel(n.horario);
        lblHorario.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblHorario.setForeground(EstiloReAlimenta.TEXTO_SUAVE);
        lblHorario.setAlignmentX(Component.RIGHT_ALIGNMENT);
        dir.add(lblHorario);
        dir.add(Box.createVerticalStrut(8));

        if (!n.lida) {
            JButton btnLer = criarBotaoMiniLida(n, corTipo);
            btnLer.setAlignmentX(Component.RIGHT_ALIGNMENT);
            dir.add(btnLer);
        } else {
            JLabel lida = new JLabel("Lida");
            lida.setFont(new Font("Segoe UI", Font.PLAIN, 11));
            lida.setForeground(new Color(0xA0, 0xAE, 0xC0));
            lida.setAlignmentX(Component.RIGHT_ALIGNMENT);
            dir.add(lida);
        }
        dir.add(Box.createVerticalGlue());
        card.add(dir, BorderLayout.EAST);
        return card;
    }

    // Mini botão "Marcar como lida" no card
    private JButton criarBotaoMiniLida(Notificacao n, Color corTipo) {
        JButton btn = new JButton("Marcar como lida") {
            boolean hover = false;
            {
                addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) { hover = true;  repaint(); }
                    public void mouseExited(MouseEvent e)  { hover = false; repaint(); }
                });
            }
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(hover ? misturarCores(corTipo, Color.WHITE, 0.22f) : misturarCores(corTipo, Color.WHITE, 0.12f));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(new Font("Segoe UI", Font.BOLD, 11));
        btn.setForeground(corTipo);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(148, 26));
        btn.addActionListener(e -> marcarComoLida(n));
        return btn;
    }

    // Ações
    private void marcarComoLida(Notificacao n) {
        if (!n.lida) {
            n.lida = true;
            // TODO: NotificacaoDAO.marcarComoLida(n.id)
            renderizarNotificacoes();
        }
    }

    private void marcarTodasComoLidas() {
        for (Notificacao n : listaNotificacoes) {
            n.lida = true;
        }
        // TODO: NotificacaoDAO.marcarTodasComoLidas(comercianteId)
        renderizarNotificacoes();
    }

    // Helpers visuais
    /** Mistura duas cores: fator 0.0 = 100% cor1, 1.0 = 100% cor2 */
    private Color misturarCores(Color c1, Color c2, float fator) {
        float f2 = 1f - fator;
        return new Color(
                Math.min(255, (int)(c1.getRed()   * f2 + c2.getRed()   * fator)),
                Math.min(255, (int)(c1.getGreen() * f2 + c2.getGreen() * fator)),
                Math.min(255, (int)(c1.getBlue()  * f2 + c2.getBlue()  * fator))
        );
    }

    // Retorna a cor associada ao tipo da notificação
    private Color resolverCor(TipoNotificacao tipo) {
        switch (tipo) {
            case DOACAO_RETIRADA:    return new Color(0x16, 0xA3, 0x4A); // verde
            case NOVA_SOLICITACAO:   return new Color(0x25, 0x63, 0xEB); // azul
            case PROMOCAO_ATIVA:     return new Color(0xD9, 0x77, 0x07); // laranja
            case DOACAO_VENCIMENTO:  return new Color(0xDC, 0x26, 0x26); // vermelho
            case SISTEMA:            return new Color(0x7C, 0x3A, 0xED); // roxo
            default:                 return EstiloReAlimenta.TEXTO_SUAVE;
        }
    }

    // Retorna o ícone FontAwesome associado ao tipo
    private FontAwesomeSolid resolverIcone(TipoNotificacao tipo) {
        switch (tipo) {
            case DOACAO_RETIRADA:    return FontAwesomeSolid.CHECK_CIRCLE;
            case NOVA_SOLICITACAO:   return FontAwesomeSolid.USER;
            case PROMOCAO_ATIVA:     return FontAwesomeSolid.TAG;
            case DOACAO_VENCIMENTO:  return FontAwesomeSolid.EXCLAMATION_TRIANGLE;
            case SISTEMA:            return FontAwesomeSolid.BELL;
            default:                 return FontAwesomeSolid.INFO_CIRCLE;
        }
    }

    // Mock de dados — substituir por NotificacaoDAO
    private void popularMocks() {
        // TODO: carregar notificações do banco
        // TODO: integrar NotificacaoDAO

        listaNotificacoes.add(new Notificacao(
                1,
                TipoNotificacao.NOVA_SOLICITACAO,
                "Nova solicitação de retirada",
                "Você recebeu uma nova solicitação para a doação 'Salada Completa'.",
                "Agora",
                false
        ));
        listaNotificacoes.add(new Notificacao(
                2,
                TipoNotificacao.DOACAO_VENCIMENTO,
                "Doação próxima do vencimento",
                "A doação 'Sopa de Legumes' expira em 2 dias.",
                "Há 15 min",
                false
        ));
        listaNotificacoes.add(new Notificacao(
                3,
                TipoNotificacao.DOACAO_RETIRADA,
                "Doação retirada com sucesso",
                "A doação 'Marmita de Feijoada' foi retirada por João da Silva.",
                "Há 1 hora",
                false
        ));
        listaNotificacoes.add(new Notificacao(
                4,
                TipoNotificacao.PROMOCAO_ATIVA,
                "Promoção ativa",
                "A promoção 'Desconto no Macarrão' está ativa desde hoje.",
                "Hoje, 10:30",
                true
        ));
        listaNotificacoes.add(new Notificacao(
                5,
                TipoNotificacao.NOVA_SOLICITACAO,
                "Nova solicitação de retirada",
                "Ana Costa solicitou retirada da doação 'Frango Assado'.",
                "Ontem, 18:45",
                true
        ));
        listaNotificacoes.add(new Notificacao(
                6,
                TipoNotificacao.DOACAO_RETIRADA,
                "Doação retirada com sucesso",
                "A doação 'Arroz e Feijão' foi retirada por Carlos Mendes.",
                "Ontem, 14:20",
                true
        ));
        listaNotificacoes.add(new Notificacao(
                7,
                TipoNotificacao.SISTEMA,
                "Sistema",
                "Seja bem-vindo ao ReAlimenta! Gerencie suas doações e promoções aqui.",
                "17/05/2025",
                true
        ));
    }

    // Modelo interno — mover para model/Notificacao.java futuramente
    public enum TipoNotificacao {
        DOACAO_RETIRADA,
        NOVA_SOLICITACAO,
        PROMOCAO_ATIVA,
        DOACAO_VENCIMENTO,
        SISTEMA
    }

    public static class Notificacao {
        public int             id;
        public TipoNotificacao tipo;
        public String          titulo;
        public String          descricao;
        public String          horario;
        public boolean         lida;

        public Notificacao(int id, TipoNotificacao tipo, String titulo, String descricao, String horario, boolean lida) {
            this.id        = id;
            this.tipo      = tipo;
            this.titulo    = titulo;
            this.descricao = descricao;
            this.horario   = horario;
            this.lida      = lida;
        }
    }
    //  Navegação
    private void navegarDashboard()    { dispose(); new TelaDashboardComerciante(comerciante); }
    private void navegarAlimentos()    { dispose(); new TelaCadastroAlimento(comerciante); }
    private void navegarPromocoes()    { dispose(); new TelaMinhasPromocoes(comerciante); }
    private void navegarDoacoes()      { dispose(); new TelaDoacoesComerciante(comerciante); }
    private void navegarSolicitacoes() { dispose(); new TelaSolicitacoesComerciante(comerciante); }
    private void navegarNotificacoes() { dispose(); new TelaNotificacoesComerciante(comerciante); }
    private void sair()                { dispose(); new TelaLogin(); }

}