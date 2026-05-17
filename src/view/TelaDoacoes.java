package view;
import util.EstiloReAlimenta;
import util.EstiloReAlimenta.RoundedPanel;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.List;

public class TelaDoacoes extends JFrame {
    private final String nomeUsuario;

    // MODELO DE DADOS
    // Futuramente substituir por model/Doacao.java + DAO
    private static class DadosDoacao {
        String nome;
        String descricao;
        String quantidade;
        String estabelecimento;
        String horario;
        Color corIcone;
        FontAwesomeSolid icone;

        DadosDoacao(String nome, String descricao, String quantidade, String estabelecimento, String horario, Color corIcone, FontAwesomeSolid icone) {
            this.nome           = nome;
            this.descricao      = descricao;
            this.quantidade     = quantidade;
            this.estabelecimento= estabelecimento;
            this.horario        = horario;
            this.corIcone       = corIcone;
            this.icone          = icone;
        }
    }

    // Dados mockados — substituir futuramente por List<Doacao> do DAO
    private final List<DadosDoacao> doacoes = Arrays.asList(
            new DadosDoacao(
                    "Bananas Maduras",
                    "Bananas em bom estado, \u00f3timas para vitamina ou consumo direto.",
                    "~2 kg dispon\u00edveis",
                    "Hortifruti do Z\u00e9 \u00b7 Feira Central, Banca 12",
                    "Retirada at\u00e9 18h00",
                    new Color(0xF1, 0xC4, 0x0F),
                    FontAwesomeSolid.APPLE_ALT
            ),
            new DadosDoacao(
                    "P\u00e3es Variados",
                    "Sortimento do dia: baguetes, p\u00e3o franc\u00eas e integral. Ainda frescos.",
                    "~30 unidades",
                    "Padaria P\u00e3o de Ouro \u00b7 Rua do P\u00e3o, 77",
                    "Retirada at\u00e9 17h30",
                    new Color(0xE6, 0x7E, 0x22),
                    FontAwesomeSolid.BREAD_SLICE
            ),
            new DadosDoacao(
                    "Saladas Diversas",
                    "Mix de folhas e legumes frescos, colhidos hoje de manha.",
                    "~1,5 kg",
                    "Restaurante NaturalVida \u00b7 Av. Sa\u00fade, 200",
                    "Retirada at\u00e9 19h00",
                    new Color(0x27, 0xAE, 0x60),
                    FontAwesomeSolid.LEAF
            ),
            new DadosDoacao(
                    "Frutas Mistas",
                    "Ma\u00e7\u00e3s, peras e laranjas pr\u00f3ximas do vencimento, perfeitas para consumo imediato.",
                    "~3 kg",
                    "Supermercado BomPreco \u00b7 Av. Principal, 500",
                    "Retirada at\u00e9 20h00",
                    new Color(0xE7, 0x4C, 0x3C),
                    FontAwesomeSolid.APPLE_ALT
            ),
            new DadosDoacao(
                    "Iogurtes Naturais",
                    "Iogurtes naturais de 200g, vencimento amanha. Refrigerados at\u00e9 a retirada.",
                    "~12 unidades",
                    "Laticin\u00edos Vale Verde \u00b7 Rua das Industrias, 88",
                    "Retirada at\u00e9 16h00",
                    new Color(0x2E, 0x86, 0xC1),
                    FontAwesomeSolid.GLASS_WHISKEY
            )
    );

    // CONSTRUTOR
    public TelaDoacoes(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
        configurarJanela();
        construirInterface();
        setVisible(true);
    }

    // CONFIGURAÇÃO DA JANELA
    private void configurarJanela() {
        setTitle("ReAlimenta | Doa\u00e7\u00f5es");
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

    // SIDEBAR — padrão idêntico ao Dashboard e TelaPromocao
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

        sidebar.add(EstiloReAlimenta.criarItemMenu(FontAwesomeSolid.HOME, "In\u00edcio", new Runnable() {@Override public void run() { navegarDashboard(); }}));
        sidebar.add(EstiloReAlimenta.criarItemMenu(FontAwesomeSolid.TAG, "Promo\u00e7\u00f5es", new Runnable() {@Override public void run() { navegarPromocoes(); }}));
        // Item atual destacado com fundo branco semitransparente
        sidebar.add(criarItemMenuAtivo(FontAwesomeSolid.GIFT, "Doa\u00e7\u00f5es"));
        sidebar.add(EstiloReAlimenta.criarItemMenu(FontAwesomeSolid.CLIPBOARD_LIST, "Minhas Solicita\u00e7\u00f5es", new Runnable() {@Override public void run() { navegarSolicitacoes(); }}));
        sidebar.add(EstiloReAlimenta.criarItemMenu(FontAwesomeSolid.BELL, "Notifica\u00e7\u00f5es", new Runnable() {@Override public void run() { navegarNotificacoes(); }}));

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

    // Item de menu com destaque (tela atual)
    private JPanel criarItemMenuAtivo(FontAwesomeSolid icone, String texto) {
        JPanel item = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        item.setOpaque(false);
        item.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        item.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));

        // Fundo destacado
        item = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8)) {
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

        JLabel lblTexto = new JLabel(texto);
        lblTexto.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblTexto.setForeground(EstiloReAlimenta.BRANCO);
        item.add(lblTexto);

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
        conteudo.add(criarBarraBusca());
        conteudo.add(Box.createVerticalStrut(20));
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
        titLinha.add(EstiloReAlimenta.criarIcone(FontAwesomeSolid.GIFT, 20, EstiloReAlimenta.VERDE_PRINCIPAL));
        JLabel lblTit = new JLabel("Doa\u00e7\u00f5es");
        lblTit.setFont(EstiloReAlimenta.FONTE_TITULO);
        lblTit.setForeground(EstiloReAlimenta.TEXTO);
        titLinha.add(lblTit);
        esq.add(titLinha);

        JLabel lblSub = new JLabel("Ajude a combater o desperd\u00edcio doando alimentos.");
        lblSub.setFont(EstiloReAlimenta.FONTE_SUBTITULO);
        lblSub.setForeground(EstiloReAlimenta.TEXTO_SUAVE);
        esq.add(lblSub);

        header.add(esq, BorderLayout.WEST);

        JLabel lblQtd = new JLabel(doacoes.size() + " doa\u00e7\u00f5es dispon\u00edveis");
        lblQtd.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblQtd.setForeground(EstiloReAlimenta.TEXTO_SUAVE);
        header.add(lblQtd, BorderLayout.EAST);

        return header;
    }

    // BARRA DE BUSCA + FILTROS
    private JPanel criarBarraBusca() {
        RoundedPanel painel = new RoundedPanel(10, EstiloReAlimenta.BRANCO);
        painel.setLayout(new FlowLayout(FlowLayout.LEFT, 12, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        painel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 58));

        // Campo de busca
        JPanel wrapBusca = criarWrapperCampo();
        wrapBusca.add(EstiloReAlimenta.criarIcone(FontAwesomeSolid.SEARCH, 13, EstiloReAlimenta.TEXTO_SUAVE), BorderLayout.WEST);
        final JTextField campoBusca = new JTextField();
        campoBusca.setFont(EstiloReAlimenta.FONTE_CAMPO);
        campoBusca.setBorder(BorderFactory.createEmptyBorder(0, 6, 0, 0));
        campoBusca.setOpaque(false);
        campoBusca.setForeground(new Color(0xA0, 0xAE, 0xC0));
        campoBusca.setText("Buscar doa\u00e7\u00e3o...");
        campoBusca.setColumns(18);
        campoBusca.addFocusListener(new FocusAdapter() {
            @Override public void focusGained(FocusEvent e) {
                if (campoBusca.getText().equals("Buscar doa\u00e7\u00e3o...")) {
                    campoBusca.setText("");
                    campoBusca.setForeground(EstiloReAlimenta.TEXTO);
                }
            }
            @Override public void focusLost(FocusEvent e) {
                if (campoBusca.getText().isEmpty()) {
                    campoBusca.setText("Buscar doa\u00e7\u00e3o...");
                    campoBusca.setForeground(new Color(0xA0, 0xAE, 0xC0));
                }
            }
        });
        wrapBusca.add(campoBusca, BorderLayout.CENTER);
        painel.add(wrapBusca);

        // Botão Filtros
        painel.add(criarBotaoFiltro());

        return painel;
    }

    private JPanel criarWrapperCampo() {
        JPanel p = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(EstiloReAlimenta.FUNDO);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.setColor(EstiloReAlimenta.BORDA);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 8, 8);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        p.setOpaque(false);
        p.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10));
        p.setPreferredSize(new Dimension(260, 36));
        return p;
    }

    private JButton criarBotaoFiltro() {
        JButton btn = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(EstiloReAlimenta.VERDE_CLARO);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setIcon(org.kordamp.ikonli.swing.FontIcon.of(FontAwesomeSolid.FILTER, 13, EstiloReAlimenta.VERDE_PRINCIPAL));
        btn.setText("Filtros");
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setForeground(EstiloReAlimenta.VERDE_PRINCIPAL);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(100, 36));
        return btn;
    }

    // LISTAGEM DE CARDS
    private JPanel criarListagem() {
        JPanel painel = new JPanel();
        painel.setOpaque(false);
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));

        for (DadosDoacao d : doacoes) {
            painel.add(criarCard(d));
            painel.add(Box.createVerticalStrut(14));
        }
        return painel;
    }

    private JPanel criarCard(final DadosDoacao d) {
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
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

        // Faixa colorida esquerda com ícone
        JPanel faixa = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(d.corIcone);
                g2.fillRoundRect(0, 0, getWidth() + 20, getHeight(), 12, 12);
                g2.dispose();
            }
        };
        faixa.setPreferredSize(new Dimension(80, 0));
        faixa.setLayout(new GridBagLayout());
        faixa.add(EstiloReAlimenta.criarIcone(d.icone, 26, Color.WHITE));
        card.add(faixa, BorderLayout.WEST);

        // Informações centrais
        JPanel info = new JPanel();
        info.setOpaque(false);
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setBorder(BorderFactory.createEmptyBorder(14, 18, 14, 10));

        JLabel lblNome = new JLabel(d.nome);
        lblNome.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblNome.setForeground(EstiloReAlimenta.TEXTO);
        info.add(lblNome);

        JLabel lblDesc = new JLabel(d.descricao);
        lblDesc.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblDesc.setForeground(EstiloReAlimenta.TEXTO_SUAVE);
        info.add(lblDesc);

        info.add(Box.createVerticalStrut(6));

        JPanel rodape = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        rodape.setOpaque(false);
        rodape.add(criarIconeLinha(FontAwesomeSolid.BOX_OPEN, d.quantidade));
        rodape.add(criarIconeLinha(FontAwesomeSolid.STORE, d.estabelecimento));
        rodape.add(criarIconeLinha(FontAwesomeSolid.CLOCK, d.horario));
        info.add(rodape);

        card.add(info, BorderLayout.CENTER);

        // Botão direita
        JPanel dir = new JPanel(new GridBagLayout());
        dir.setOpaque(false);
        dir.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
        dir.setPreferredSize(new Dimension(140, 0));

        JButton btnSolicitar = EstiloReAlimenta.criarBotaoPrimario("Solicitar", new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) { mostrarConfirmacao(d); }
        });
        btnSolicitar.setMaximumSize(new Dimension(120, 36));
        btnSolicitar.setPreferredSize(new Dimension(120, 36));
        dir.add(btnSolicitar);

        card.add(dir, BorderLayout.EAST);
        return card;
    }

    //  HELPERS
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
    private void mostrarConfirmacao(DadosDoacao d) {
        int op = JOptionPane.showConfirmDialog(this,
                "Deseja solicitar a doa\u00e7\u00e3o de \"" + d.nome + "\"?\n"
                        + "Estabelecimento: " + d.estabelecimento + "\n"
                        + d.horario
                        + "\n\n(Confirma\u00e7\u00e3o via backend \u2014 em desenvolvimento)",
                "Confirmar Solicita\u00e7\u00e3o",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (op == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(this,
                    "Solicita\u00e7\u00e3o enviada!\nAcompanhe em \"Minhas Solicita\u00e7\u00f5es\".",
                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void navegarDashboard()    { dispose(); new TelaDashboardConsumidor(nomeUsuario); }
    private void navegarPromocoes()    { dispose(); new TelaPromocao(nomeUsuario); }
    private void navegarSolicitacoes() { dispose(); new TelaMinhasSolicitacoes(nomeUsuario); }
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