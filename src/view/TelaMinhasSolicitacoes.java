package view;
import util.EstiloReAlimenta;
import util.EstiloReAlimenta.RoundedPanel;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.List;

public class TelaMinhasSolicitacoes extends JFrame {
    private final String nomeUsuario;
    private static final String STATUS_CONFIRMADA = "CONFIRMADA";
    private static final String STATUS_RETIRADA   = "RETIRADA";
    private static final String STATUS_CANCELADA  = "CANCELADA";
    // Cores por status
    private static final Color COR_CONFIRMADA = new Color(0x2E, 0x86, 0xC1); // azul
    private static final Color COR_RETIRADA   = new Color(0x1F, 0x7A, 0x4D); // verde
    private static final Color COR_CANCELADA  = new Color(0xC0, 0x39, 0x2B); // vermelho

    // MODELO DE DADOS
    // Futuramente substituir por model/Solicitacao.java + DAO
    private static class DadosSolicitacao {
        String nomeAlimento;
        String estabelecimento;
        String dataSolicitacao;
        String horarioRetirada;
        String status;
        FontAwesomeSolid iconeAlimento;
        Color corAlimento;

        DadosSolicitacao(String nomeAlimento, String estabelecimento, String dataSolicitacao, String horarioRetirada, String status, FontAwesomeSolid iconeAlimento, Color corAlimento) {
            this.nomeAlimento    = nomeAlimento;
            this.estabelecimento = estabelecimento;
            this.dataSolicitacao = dataSolicitacao;
            this.horarioRetirada = horarioRetirada;
            this.status          = status;
            this.iconeAlimento   = iconeAlimento;
            this.corAlimento     = corAlimento;
        }
    }

    // Dados mockados — substituir futuramente por List<Solicitacao> via DAO
    private final List<DadosSolicitacao> solicitacoes = Arrays.asList(
            new DadosSolicitacao(
                    "Bananas Maduras",
                    "Hortifruti do Z\u00e9",
                    "Solicitado em 14/05/2025",
                    "Retirada at\u00e9 18h00",
                    STATUS_CONFIRMADA,
                    FontAwesomeSolid.APPLE_ALT,
                    new Color(0xF1, 0xC4, 0x0F)
            ),
            new DadosSolicitacao(
                    "Lasanha \u00e0 Bolonhesa",
                    "Restaurante da Mara",
                    "Solicitado em 13/05/2025",
                    "Retirada at\u00e9 20h00",
                    STATUS_RETIRADA,
                    FontAwesomeSolid.UTENSILS,
                    new Color(0xE7, 0x4C, 0x3C)
            ),
            new DadosSolicitacao(
                    "P\u00e3es Variados",
                    "Padaria P\u00e3o de Ouro",
                    "Solicitado em 12/05/2025",
                    "Retirada at\u00e9 17h30",
                    STATUS_CANCELADA,
                    FontAwesomeSolid.BREAD_SLICE,
                    new Color(0xE6, 0x7E, 0x22)
            ),
            new DadosSolicitacao(
                    "Pizza Marguerita",
                    "Pizzaria Bela Napoli",
                    "Solicitado em 10/05/2025",
                    "Retirada at\u00e9 21h30",
                    STATUS_RETIRADA,
                    FontAwesomeSolid.PIZZA_SLICE,
                    new Color(0xE6, 0x7E, 0x22)
            ),
            new DadosSolicitacao(
                    "Salada Completa",
                    "Restaurante NaturalVida",
                    "Solicitado em 09/05/2025",
                    "Retirada at\u00e9 19h00",
                    STATUS_CONFIRMADA,
                    FontAwesomeSolid.LEAF,
                    new Color(0x27, 0xAE, 0x60)
            )
    );

    //  CONSTRUTOR
    public TelaMinhasSolicitacoes(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
        configurarJanela();
        construirInterface();
        setVisible(true);
    }

    // CONFIGURAÇÃO DA JANELA
    private void configurarJanela() {
        setTitle("ReAlimenta | Minhas Solicita\u00e7\u00f5es");
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
        // Item atual destacado
        sidebar.add(criarItemMenuAtivo(FontAwesomeSolid.CLIPBOARD_LIST, "Minhas Solicita\u00e7\u00f5es"));
        sidebar.add(EstiloReAlimenta.criarItemMenu(FontAwesomeSolid.BELL, "Notifica\u00e7\u00f5es", new Runnable() {
            @Override public void run() { navegarNotificacoes(); }
        }));

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
        conteudo.add(criarLegendaStatus());
        conteudo.add(Box.createVerticalStrut(16));
        conteudo.add(criarListagem());
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
        titLinha.add(EstiloReAlimenta.criarIcone(FontAwesomeSolid.CLIPBOARD_LIST, 20, EstiloReAlimenta.VERDE_PRINCIPAL));
        JLabel lblTit = new JLabel("Minhas Solicita\u00e7\u00f5es");
        lblTit.setFont(EstiloReAlimenta.FONTE_TITULO);
        lblTit.setForeground(EstiloReAlimenta.TEXTO);
        titLinha.add(lblTit);
        esq.add(titLinha);

        JLabel lblSub = new JLabel("Acompanhe todas as suas solicita\u00e7\u00f5es.");
        lblSub.setFont(EstiloReAlimenta.FONTE_SUBTITULO);
        lblSub.setForeground(EstiloReAlimenta.TEXTO_SUAVE);
        esq.add(lblSub);

        header.add(esq, BorderLayout.WEST);

        JLabel lblQtd = new JLabel(solicitacoes.size() + " solicita\u00e7\u00f5es");
        lblQtd.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblQtd.setForeground(EstiloReAlimenta.TEXTO_SUAVE);
        header.add(lblQtd, BorderLayout.EAST);

        return header;
    }

    // LEGENDA DE STATUS
    private JPanel criarLegendaStatus() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 0));
        p.setOpaque(false);
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));

        p.add(criarBadgeStatus(STATUS_CONFIRMADA));
        p.add(criarBadgeStatus(STATUS_RETIRADA));
        p.add(criarBadgeStatus(STATUS_CANCELADA));
        return p;
    }

    // LISTAGEM
    private JPanel criarListagem() {
        JPanel painel = new JPanel();
        painel.setOpaque(false);
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));

        if (solicitacoes.isEmpty()) {
            JLabel lblVazio = new JLabel("Voc\u00ea ainda n\u00e3o fez nenhuma solicita\u00e7\u00e3o.");
            lblVazio.setFont(EstiloReAlimenta.FONTE_SUBTITULO);
            lblVazio.setForeground(EstiloReAlimenta.TEXTO_SUAVE);
            lblVazio.setAlignmentX(Component.CENTER_ALIGNMENT);
            painel.add(Box.createVerticalStrut(40));
            painel.add(lblVazio);
            return painel;
        }
        for (DadosSolicitacao s : solicitacoes) {
            painel.add(criarCard(s));
            painel.add(Box.createVerticalStrut(14));
        }
        return painel;
    }

    private JPanel criarCard(final DadosSolicitacao s) {
        RoundedPanel card = new RoundedPanel(12, EstiloReAlimenta.BRANCO) {
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
                    g2.setColor(new Color(0, 0, 0, hover ? 11 : 6));
                    g2.fillRoundRect(i, i, getWidth() - i * 2, getHeight() - i * 2, 12 + i, 12 + i);
                }
                g2.setColor(hover ? new Color(0xF8, 0xFF, 0xFA) : EstiloReAlimenta.BRANCO);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        card.setLayout(new BorderLayout(0, 0));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 110));

        // Faixa colorida esquerda
        JPanel faixa = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(s.corAlimento);
                g2.fillRoundRect(0, 0, getWidth() + 20, getHeight(), 12, 12);
                g2.dispose();
            }
        };
        faixa.setPreferredSize(new Dimension(80, 0));
        faixa.setLayout(new GridBagLayout());
        faixa.add(EstiloReAlimenta.criarIcone(s.iconeAlimento, 26, Color.WHITE));
        card.add(faixa, BorderLayout.WEST);

        // Info central
        JPanel info = new JPanel();
        info.setOpaque(false);
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setBorder(BorderFactory.createEmptyBorder(14, 18, 14, 10));

        JLabel lblNome = new JLabel(s.nomeAlimento);
        lblNome.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblNome.setForeground(EstiloReAlimenta.TEXTO);
        info.add(lblNome);

        JLabel lblLocal = new JLabel(s.estabelecimento);
        lblLocal.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblLocal.setForeground(EstiloReAlimenta.TEXTO_SUAVE);
        info.add(lblLocal);

        info.add(Box.createVerticalStrut(6));

        JPanel rodape = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        rodape.setOpaque(false);
        rodape.add(criarIconeLinha(FontAwesomeSolid.CALENDAR_ALT, s.dataSolicitacao));
        rodape.add(criarIconeLinha(FontAwesomeSolid.CLOCK, s.horarioRetirada));
        info.add(rodape);

        card.add(info, BorderLayout.CENTER);

        // Status + botão (direita)
        JPanel dir = new JPanel();
        dir.setOpaque(false);
        dir.setLayout(new BoxLayout(dir, BoxLayout.Y_AXIS));
        dir.setBorder(BorderFactory.createEmptyBorder(14, 0, 14, 20));
        dir.setPreferredSize(new Dimension(160, 0));

        JLabel badge = criarBadgeStatus(s.status);
        badge.setAlignmentX(Component.RIGHT_ALIGNMENT);
        dir.add(badge);

        // Texto descritivo do status
        JLabel lblStatusDesc = new JLabel(resolverDescricaoStatus(s.status));
        lblStatusDesc.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        lblStatusDesc.setForeground(EstiloReAlimenta.TEXTO_SUAVE);
        lblStatusDesc.setAlignmentX(Component.RIGHT_ALIGNMENT);
        dir.add(lblStatusDesc);

        dir.add(Box.createVerticalGlue());

        JButton btnDetalhes = EstiloReAlimenta.criarBotaoSecundario("Ver detalhes", new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) { mostrarDetalhes(s); }
        });
        btnDetalhes.setMaximumSize(new Dimension(140, 34));
        btnDetalhes.setPreferredSize(new Dimension(140, 34));
        btnDetalhes.setAlignmentX(Component.RIGHT_ALIGNMENT);
        dir.add(btnDetalhes);

        card.add(dir, BorderLayout.EAST);
        return card;
    }

    // HELPERS
    private JLabel criarBadgeStatus(final String status) {
        final Color corBadge = resolverCorStatus(status);
        JLabel badge = new JLabel(status) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(corBadge);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        badge.setFont(new Font("Segoe UI", Font.BOLD, 10));
        badge.setForeground(Color.WHITE);
        badge.setOpaque(false);
        badge.setBorder(BorderFactory.createEmptyBorder(3, 10, 3, 10));
        badge.setHorizontalAlignment(SwingConstants.CENTER);
        return badge;
    }

    private Color resolverCorStatus(String status) {
        if (STATUS_CONFIRMADA.equals(status)) return COR_CONFIRMADA;
        if (STATUS_RETIRADA.equals(status))   return COR_RETIRADA;
        return COR_CANCELADA;
    }

    private String resolverDescricaoStatus(String status) {
        if (STATUS_CONFIRMADA.equals(status)) return "Pronto para retirada";
        if (STATUS_RETIRADA.equals(status))   return "J\u00e1 retirado";
        return "Encerrada";
    }

    private JPanel criarIconeLinha(FontAwesomeSolid icone, String texto) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        p.setOpaque(false);
        p.add(EstiloReAlimenta.criarIcone(icone, 11, EstiloReAlimenta.TEXTO_SUAVE));
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lbl.setForeground(EstiloReAlimenta.TEXTO_SUAVE);
        p.add(lbl);
        return p;
    }

    // AÇÕES
    private void mostrarDetalhes(DadosSolicitacao s) {
        JOptionPane.showMessageDialog(this,
                "Alimento: " + s.nomeAlimento
                        + "\nEstabelecimento: " + s.estabelecimento
                        + "\n" + s.dataSolicitacao
                        + "\n" + s.horarioRetirada
                        + "\nStatus: " + s.status + " \u2014 " + resolverDescricaoStatus(s.status)
                        + "\n\n(Detalhes completos via backend \u2014 em desenvolvimento)",
                "Detalhes da Solicita\u00e7\u00e3o", JOptionPane.INFORMATION_MESSAGE);
    }

    private void navegarDashboard()    { dispose(); new TelaDashboardConsumidor(nomeUsuario); }
    private void navegarPromocoes()    { dispose(); new TelaPromocao(nomeUsuario); }
    private void navegarDoacoes()      { dispose(); new TelaDoacoes(nomeUsuario); }
    private void navegarNotificacoes() { dispose(); new TelaNotificacoes(nomeUsuario); }

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
