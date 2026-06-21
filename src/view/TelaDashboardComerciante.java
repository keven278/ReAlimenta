package view;
import model.Comerciante;
import util.EstiloReAlimenta;
import util.EstiloReAlimenta.RoundedPanel;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;

public class TelaDashboardComerciante extends JFrame {
    // Mock de dados — substituir por chamadas DAO/backend
    private int totalAlimentos    = 12;
    private int totalPromocoes    = 4;
    private int totalDoacoes      = 3;
    private int totalSolicitacoes = 7;
    private Comerciante comerciante;

    private List<AlimentoValidade>    proximosValidade    = new ArrayList<AlimentoValidade>();
    private List<SolicitacaoRecente>  solicitacoesRecentes = new ArrayList<SolicitacaoRecente>();

    public TelaDashboardComerciante(Comerciante comerciante) {
        this.comerciante = comerciante;

        popularMocks();
        configurarJanela();
        construirLayout();
        setVisible(true);
    }

    // Configuração do JFrame
    private void configurarJanela() {
        setTitle("ReAlimenta | Dashboard Comerciante");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(1100, 700));
        setSize(1280, 800);
        setLocationRelativeTo(null);
        getContentPane().setBackground(EstiloReAlimenta.FUNDO);
    }

    // Layout principal: sidebar + conteúdo
    private void construirLayout() {
        setLayout(new BorderLayout());
        add(criarSidebar(), BorderLayout.WEST);
        add(criarConteudo(), BorderLayout.CENTER);
    }

    // Sidebar verde
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

        // Logo + nome app
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

        // Separador
        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(255, 255, 255, 40));
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        sidebar.add(sep);
        sidebar.add(Box.createVerticalStrut(16));
        sidebar.add(criarItemMenuAtivo(FontAwesomeSolid.HOME, "Dashboard"));
        sidebar.add(Box.createVerticalStrut(4));
        sidebar.add(EstiloReAlimenta.criarItemMenu(FontAwesomeSolid.APPLE_ALT, "Alimentos", this::navegarAlimentos));
        sidebar.add(Box.createVerticalStrut(4));
        sidebar.add(EstiloReAlimenta.criarItemMenu(FontAwesomeSolid.TAG, "Promoções", this::navegarPromocoes));
        sidebar.add(Box.createVerticalStrut(4));
        sidebar.add(EstiloReAlimenta.criarItemMenu(FontAwesomeSolid.HAND_HOLDING_HEART, "Doações", this::navegarDoacoes));
        sidebar.add(Box.createVerticalStrut(4));
        sidebar.add(EstiloReAlimenta.criarItemMenu(FontAwesomeSolid.INBOX, "Solicitações", this::navegarSolicitacoes));
        sidebar.add(Box.createVerticalStrut(4));
        sidebar.add(EstiloReAlimenta.criarItemMenu(FontAwesomeSolid.BELL, "Notificações", this::navegarNotificacoes));
        sidebar.add(Box.createVerticalStrut(4));
        sidebar.add(Box.createVerticalGlue());

        JSeparator sep2 = new JSeparator();
        sep2.setForeground(new Color(255, 255, 255, 40));
        sep2.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        sidebar.add(sep2);
        sidebar.add(Box.createVerticalStrut(12));
        sidebar.add(EstiloReAlimenta.criarItemMenu(FontAwesomeSolid.SIGN_OUT_ALT, "Sair", this::sair));

        return sidebar;
    }

    // Área de conteúdo scrollável
    private JScrollPane criarConteudo() {
        JPanel conteudo = new JPanel();
        conteudo.setLayout(new BoxLayout(conteudo, BoxLayout.Y_AXIS));
        conteudo.setBackground(EstiloReAlimenta.FUNDO);
        conteudo.setBorder(new EmptyBorder(32, 32, 32, 32));

        // Cabeçalho da página
        conteudo.add(criarCabecalhoPagina());
        conteudo.add(Box.createVerticalStrut(28));

        // Cards de resumo
        conteudo.add(criarLinhaCards());
        conteudo.add(Box.createVerticalStrut(28));

        // Atalhos rápidos
        conteudo.add(criarSecaoAtalhosRapidos());
        conteudo.add(Box.createVerticalStrut(28));

        // Seções inferiores lado a lado
        JPanel inferior = new JPanel(new GridLayout(1, 2, 20, 0));
        inferior.setOpaque(false);
        inferior.add(criarSecaoProximosValidade());
        inferior.add(criarSecaoSolicitacoesRecentes());
        conteudo.add(inferior);

        JScrollPane scroll = new JScrollPane(conteudo);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(EstiloReAlimenta.FUNDO);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        return scroll;
    }

    // Cabeçalho da página com saudação
    private JPanel criarCabecalhoPagina() {
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);

        JPanel esq = new JPanel();
        esq.setOpaque(false);
        esq.setLayout(new BoxLayout(esq, BoxLayout.Y_AXIS));
        JLabel titulo = EstiloReAlimenta.criarTitulo("Olá, Comerciante! 👋");
        JLabel sub    = EstiloReAlimenta.criarSubtitulo("Aqui está um resumo do seu negócio hoje.");
        esq.add(titulo);
        esq.add(Box.createVerticalStrut(4));
        esq.add(sub);
        p.add(esq, BorderLayout.WEST);

        // Data atual (mock)
        JLabel data = new JLabel("Sáb, 17 de maio de 2025");
        data.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        data.setForeground(EstiloReAlimenta.TEXTO_SUAVE);
        p.add(data, BorderLayout.EAST);

        return p;
    }

    // Linha com 4 cards de resumo
    private JPanel criarLinhaCards() {
        JPanel linha = new JPanel(new GridLayout(1, 4, 16, 0));
        linha.setOpaque(false);
        linha.add(criarCard("Alimentos", String.valueOf(totalAlimentos), FontAwesomeSolid.APPLE_ALT, new Color(0x16, 0xA3, 0x4A)));
        linha.add(criarCard("Promoções Ativas", String.valueOf(totalPromocoes), FontAwesomeSolid.TAG, new Color(0x25, 0x63, 0xEB)));
        linha.add(criarCard("Doações Ativas", String.valueOf(totalDoacoes), FontAwesomeSolid.HAND_HOLDING_HEART, new Color(0xDC, 0x26, 0x26)));
        linha.add(criarCard("Solicitações", String.valueOf(totalSolicitacoes), FontAwesomeSolid.INBOX, new Color(0xD9, 0x77, 0x07)));
        return linha;
    }

    private JPanel criarCard(String titulo, String valor, FontAwesomeSolid icone, Color corIcone) {
        RoundedPanel card = new RoundedPanel(14, EstiloReAlimenta.BRANCO);
        card.setLayout(new BorderLayout());
        card.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel topo = new JPanel(new BorderLayout());
        topo.setOpaque(false);
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblTitulo.setForeground(EstiloReAlimenta.TEXTO_SUAVE);
        topo.add(lblTitulo, BorderLayout.WEST);
        JLabel ico = EstiloReAlimenta.criarIcone(icone, 18, corIcone);
        topo.add(ico, BorderLayout.EAST);
        card.add(topo, BorderLayout.NORTH);

        JLabel lblValor = new JLabel(valor);
        lblValor.setFont(new Font("Segoe UI", Font.BOLD, 34));
        lblValor.setForeground(EstiloReAlimenta.TEXTO);
        card.add(lblValor, BorderLayout.CENTER);

        return card;
    }

    // Seção de atalhos rápidos
    private JPanel criarSecaoAtalhosRapidos() {
        RoundedPanel secao = new RoundedPanel(14, EstiloReAlimenta.BRANCO);
        secao.setLayout(new BoxLayout(secao, BoxLayout.Y_AXIS));
        secao.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel titulo = EstiloReAlimenta.criarTitulo("Ações Rápidas");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        secao.add(titulo);
        secao.add(Box.createVerticalStrut(16));

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        botoes.setOpaque(false);

        botoes.add(criarBotaoAtalho(FontAwesomeSolid.PLUS_CIRCLE, "Cadastrar Alimento", e -> new TelaCadastroAlimento(comerciante)));
        botoes.add(criarBotaoAtalho(FontAwesomeSolid.TAG, "Nova Promoção", e -> new TelaNovaPromocao(comerciante)));
        botoes.add(criarBotaoAtalho(FontAwesomeSolid.HAND_HOLDING_HEART, "Nova Doação", e -> { dispose(); new TelaNovaDoacao(); }));
        secao.add(botoes);
        return secao;
    }

    private JButton criarBotaoAtalho(FontAwesomeSolid icone, String texto, ActionListener acao) {
        JButton btn = new JButton(texto) {
            boolean hover = false;
            {
                addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseEntered(java.awt.event.MouseEvent e) { hover = true; repaint(); }
                    public void mouseExited(java.awt.event.MouseEvent e)  { hover = false; repaint(); }
                });
            }
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(hover ? EstiloReAlimenta.VERDE_CLARO : new Color(0xF0, 0xFD, 0xF4));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.setColor(EstiloReAlimenta.VERDE_PRINCIPAL);
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
                super.paintComponent(g);
            }
        };
        btn.setIcon(org.kordamp.ikonli.swing.FontIcon.of(icone, 16, EstiloReAlimenta.VERDE_PRINCIPAL));
        btn.setText("  " + texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setForeground(EstiloReAlimenta.VERDE_PRINCIPAL);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(200, 42));
        if (acao != null) btn.addActionListener(acao);
        return btn;
    }

    // Seção: Próximos da validade
    private JPanel criarSecaoProximosValidade() {
        RoundedPanel secao = new RoundedPanel(14, EstiloReAlimenta.BRANCO);
        secao.setLayout(new BoxLayout(secao, BoxLayout.Y_AXIS));
        secao.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("⚠ Próximos da Validade");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 15));
        titulo.setForeground(EstiloReAlimenta.TEXTO);
        secao.add(titulo);
        secao.add(Box.createVerticalStrut(4));
        secao.add(EstiloReAlimenta.criarSubtitulo("Alimentos com validade próxima"));
        secao.add(Box.createVerticalStrut(14));

        for (AlimentoValidade av : proximosValidade) {
            secao.add(criarItemValidade(av));
            secao.add(Box.createVerticalStrut(8));
        }
        return secao;
    }

    private JPanel criarItemValidade(AlimentoValidade av) {
        JPanel item = new JPanel(new BorderLayout(12, 0));
        item.setOpaque(false);
        item.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(EstiloReAlimenta.BORDA, 1, true),
                new EmptyBorder(10, 12, 10, 12)));

        JPanel esq = new JPanel();
        esq.setOpaque(false);
        esq.setLayout(new BoxLayout(esq, BoxLayout.Y_AXIS));
        JLabel nome = new JLabel(av.nome);
        nome.setFont(new Font("Segoe UI", Font.BOLD, 13));
        nome.setForeground(EstiloReAlimenta.TEXTO);
        JLabel qtd = new JLabel("Quantidade: " + av.quantidade);
        qtd.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        qtd.setForeground(EstiloReAlimenta.TEXTO_SUAVE);
        esq.add(nome);
        esq.add(qtd);
        item.add(esq, BorderLayout.CENTER);

        // Badge de alerta
        JLabel badge = new JLabel(av.prazoTexto);
        badge.setFont(new Font("Segoe UI", Font.BOLD, 11));
        badge.setForeground(Color.WHITE);
        badge.setBackground(av.prazoUrgente ? new Color(0xDC, 0x26, 0x26) : new Color(0xD9, 0x77, 0x07));
        badge.setOpaque(true);
        badge.setBorder(new EmptyBorder(3, 8, 3, 8));
        item.add(badge, BorderLayout.EAST);

        return item;
    }

    // Seção: Solicitações recentes
    private JPanel criarSecaoSolicitacoesRecentes() {
        RoundedPanel secao = new RoundedPanel(14, EstiloReAlimenta.BRANCO);
        secao.setLayout(new BoxLayout(secao, BoxLayout.Y_AXIS));
        secao.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("📋 Solicitações Recentes");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 15));
        titulo.setForeground(EstiloReAlimenta.TEXTO);
        secao.add(titulo);
        secao.add(Box.createVerticalStrut(4));
        secao.add(EstiloReAlimenta.criarSubtitulo("Últimas solicitações de consumidores"));
        secao.add(Box.createVerticalStrut(14));

        for (SolicitacaoRecente sr : solicitacoesRecentes) {
            secao.add(criarItemSolicitacao(sr));
            secao.add(Box.createVerticalStrut(8));
        }
        return secao;
    }

    private JPanel criarItemSolicitacao(SolicitacaoRecente sr) {
        JPanel item = new JPanel(new BorderLayout(12, 0));
        item.setOpaque(false);
        item.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(EstiloReAlimenta.BORDA, 1, true),
                new EmptyBorder(10, 12, 10, 12)));

        // Avatar circular com inicial
        JLabel avatar = new JLabel(String.valueOf(sr.nomeConsumidor.charAt(0))) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(EstiloReAlimenta.VERDE_CLARO);
                g2.fillOval(0, 0, getWidth(), getHeight());
                g2.setColor(EstiloReAlimenta.VERDE_PRINCIPAL);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 15));
                FontMetrics fm = g2.getFontMetrics();
                String t = getText();
                g2.drawString(t, (getWidth() - fm.stringWidth(t)) / 2, (getHeight() + fm.getAscent() - fm.getDescent()) / 2);
                g2.dispose();
            }
        };
        avatar.setPreferredSize(new Dimension(36, 36));
        avatar.setMinimumSize(new Dimension(36, 36));
        item.add(avatar, BorderLayout.WEST);

        JPanel centro = new JPanel();
        centro.setOpaque(false);
        centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));
        JLabel nome = new JLabel(sr.nomeConsumidor);
        nome.setFont(new Font("Segoe UI", Font.BOLD, 13));
        nome.setForeground(EstiloReAlimenta.TEXTO);
        JLabel detalhe = new JLabel(sr.qtdItens + " item(s) — " + sr.horario);
        detalhe.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        detalhe.setForeground(EstiloReAlimenta.TEXTO_SUAVE);
        centro.add(nome);
        centro.add(detalhe);
        item.add(centro, BorderLayout.CENTER);

        // Badge status
        boolean isPendente = sr.status.equalsIgnoreCase("Pendente");
        JLabel badge = new JLabel(sr.status);
        badge.setFont(new Font("Segoe UI", Font.BOLD, 11));
        badge.setForeground(Color.WHITE);
        badge.setBackground(isPendente ? new Color(0xD9, 0x77, 0x07) : new Color(0x16, 0xA3, 0x4A));
        badge.setOpaque(true);
        badge.setBorder(new EmptyBorder(3, 8, 3, 8));
        item.add(badge, BorderLayout.EAST);

        return item;
    }

    // Mock de dados
    private void popularMocks() {
        proximosValidade.add(new AlimentoValidade("Pão de Forma Integral", 5, "Vence em 1 dia", true));
        proximosValidade.add(new AlimentoValidade("Iogurte Natural", 12, "Vence em 3 dias", true));
        proximosValidade.add(new AlimentoValidade("Leite Semidesnatado", 8, "Vence em 5 dias", false));
        proximosValidade.add(new AlimentoValidade("Queijo Minas Frescal", 3, "Vence em 6 dias", false));

        solicitacoesRecentes.add(new SolicitacaoRecente("Maria Silva",    2, "14:30", "Pendente"));
        solicitacoesRecentes.add(new SolicitacaoRecente("João Pereira",   1, "13:15", "Confirmado"));
        solicitacoesRecentes.add(new SolicitacaoRecente("Ana Costa",      3, "11:50", "Pendente"));
        solicitacoesRecentes.add(new SolicitacaoRecente("Carlos Mendes",  1, "10:05", "Confirmado"));
    }

    // Classes internas de modelo
    public static class AlimentoValidade {
        public String  nome;
        public int     quantidade;
        public String  prazoTexto;
        public boolean prazoUrgente;
        public AlimentoValidade(String nome, int quantidade, String prazoTexto, boolean prazoUrgente) {
            this.nome        = nome;
            this.quantidade  = quantidade;
            this.prazoTexto  = prazoTexto;
            this.prazoUrgente = prazoUrgente;
        }
    }

    public static class SolicitacaoRecente {
        public String nomeConsumidor;
        public int    qtdItens;
        public String horario;
        public String status;
        public SolicitacaoRecente(String nomeConsumidor, int qtdItens, String horario, String status) {
            this.nomeConsumidor = nomeConsumidor;
            this.qtdItens       = qtdItens;
            this.horario        = horario;
            this.status         = status;
        }
    }

    // Navegação
    private void navegarDashboard()    { dispose(); new TelaDashboardComerciante(comerciante); }
    private void navegarAlimentos()    { dispose(); new TelaCadastroAlimento(comerciante); }
    private void navegarPromocoes()    { dispose(); new TelaMinhasPromocoes(comerciante); }
    private void navegarDoacoes()      { dispose(); new TelaDoacoesComerciante(comerciante); }
    private void navegarSolicitacoes() { dispose(); new TelaSolicitacoesComerciante(comerciante); }
    private void navegarNotificacoes() { dispose(); new TelaNotificacoesComerciante(comerciante); }
    private void sair()                { dispose(); new TelaLogin(); }

}