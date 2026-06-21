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

public class TelaDoacoesComerciante extends JFrame {
    // Mock de doações — substituir por DoacaoDAO.listarPorComerciante()
    private List<Doacao> listaDoacao   = new ArrayList<Doacao>();
    private List<Doacao> listaFiltrada = new ArrayList<Doacao>();
    private static final String[] FILTRO_STATUS = {"Todos", "Disponível", "Retirado", "Expirada"};
    private JTextField     campoBusca;
    private JComboBox<String> campoFiltro;
    private JPanel         painelCards;

    // Construtor
    private Comerciante comerciante;

    public TelaDoacoesComerciante(Comerciante comerciante) {
        this.comerciante = comerciante;
        popularMocks();
        listaFiltrada.addAll(listaDoacao);
        configurarJanela();
        construirLayout();
        setVisible(true);
    }

    // Configuração
    private void configurarJanela() {
        setTitle("ReAlimenta | Doações Disponíveis");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(1100, 700));
        setSize(1280, 800);
        setLocationRelativeTo(null);
        getContentPane().setBackground(EstiloReAlimenta.FUNDO);
    }

    private void construirLayout() {
        setLayout(new BorderLayout());
        add(criarSidebar(), BorderLayout.WEST);
        add(criarConteudo(), BorderLayout.CENTER);
    }

    // Sidebar — Doações em destaque
    private JPanel criarSidebar() {
        JPanel sidebar = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setPaint(new GradientPaint(
                        0, 0, EstiloReAlimenta.VERDE_PRINCIPAL,
                        0, getHeight(), new Color(0x0A, 0x3D, 0x26)));
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(220, 0));
        sidebar.setBorder(new EmptyBorder(24, 12, 24, 12));

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
        sidebar.add(criarItemMenuAtivo(FontAwesomeSolid.HAND_HOLDING_HEART, "Doações"));
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

    // Item ativo: reutiliza EstiloReAlimenta.criarItemMenu() + destaque de borda
    private JPanel criarItemMenuAtivo(FontAwesomeSolid icone, String texto) {
        JPanel item = EstiloReAlimenta.criarItemMenu(icone, texto, null);
        item.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 60), 1, true),
                BorderFactory.createEmptyBorder(0, 8, 0, 8)));
        return item;
    }

    // Conteúdo central
    private JScrollPane criarConteudo() {
        JPanel conteudo = new JPanel();
        conteudo.setLayout(new BoxLayout(conteudo, BoxLayout.Y_AXIS));
        conteudo.setBackground(EstiloReAlimenta.FUNDO);
        conteudo.setBorder(new EmptyBorder(32, 32, 32, 32));

        conteudo.add(criarCabecalhoPagina());
        conteudo.add(Box.createVerticalStrut(24));
        conteudo.add(criarBarraFiltros());
        conteudo.add(Box.createVerticalStrut(24));

        // Grid de cards dinâmico
        painelCards = new JPanel(new GridLayout(0, 3, 20, 20));
        painelCards.setOpaque(false);
        painelCards.setAlignmentX(Component.LEFT_ALIGNMENT);
        renderizarCards();
        conteudo.add(painelCards);

        JScrollPane scroll = new JScrollPane(conteudo);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(EstiloReAlimenta.FUNDO);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        return scroll;
    }

    // Cabeçalho com botão "+ Nova Doação"
    private JPanel criarCabecalhoPagina() {
        JPanel p = new JPanel();
        p.setOpaque(false);
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

        JLabel titulo = EstiloReAlimenta.criarTitulo("Doações Disponíveis");
        JLabel sub    = EstiloReAlimenta.criarSubtitulo("Gerencie as doações cadastradas.");
        p.add(titulo);
        p.add(Box.createVerticalStrut(4));
        p.add(sub);
        p.add(Box.createVerticalStrut(16));

        JButton btnNova = EstiloReAlimenta.criarBotaoPrimario("+ Nova Doação", e -> {dispose();new TelaNovaDoacao();});
        btnNova.setPreferredSize(new Dimension(200, 44));
        JPanel painelBotao = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        painelBotao.setOpaque(false);
        painelBotao.add(btnNova);
        p.add(painelBotao);

        return p;
    }

    // Barra de busca e filtro de status
    private JPanel criarBarraFiltros() {
        JPanel barra = new JPanel(new BorderLayout(12, 0));
        barra.setOpaque(false);

        campoBusca = new JTextField();
        JPanel wrapperBusca = EstiloReAlimenta.criarCampoTexto(campoBusca, "Buscar doação...", FontAwesomeSolid.SEARCH);
        wrapperBusca.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        barra.add(wrapperBusca, BorderLayout.CENTER);

        campoFiltro = new JComboBox<String>(FILTRO_STATUS);
        campoFiltro.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        campoFiltro.setBackground(EstiloReAlimenta.BRANCO);
        campoFiltro.setPreferredSize(new Dimension(160, 44));
        campoFiltro.addActionListener(e -> filtrarCards());
        barra.add(campoFiltro, BorderLayout.EAST);

        campoBusca.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent e) { filtrarCards(); }
        });

        return barra;
    }

    // Renderização dos cards
    private void renderizarCards() {
        painelCards.removeAll();
        if (listaFiltrada.isEmpty()) {
            JLabel vazio = new JLabel("Nenhuma doação encontrada.");
            vazio.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            vazio.setForeground(EstiloReAlimenta.TEXTO_SUAVE);
            painelCards.setLayout(new FlowLayout(FlowLayout.LEFT));
            painelCards.add(vazio);
        } else {
            painelCards.setLayout(new GridLayout(0, 3, 20, 20));
            for (Doacao d : listaFiltrada) {
                painelCards.add(criarCardDoacao(d));
            }
        }
        painelCards.revalidate();
        painelCards.repaint();
    }

    private JPanel criarCardDoacao(Doacao d) {
        RoundedPanel card = new RoundedPanel(14, EstiloReAlimenta.BRANCO);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(0, 0, 16, 0));

        // — Área da imagem (placeholder colorido) —
        JPanel areaImagem = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(EstiloReAlimenta.VERDE_CLARO);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 14, 14);
                // Ícone centralizado
                FontIcon fi = FontIcon.of(FontAwesomeSolid.BOX_OPEN, 40, EstiloReAlimenta.VERDE_PRINCIPAL);
                int x = (getWidth() - fi.getIconWidth()) / 2;
                int y = (getHeight() - fi.getIconHeight()) / 2;
                fi.paintIcon(this, g2, x, y);
                g2.dispose();
            }
        };
        areaImagem.setPreferredSize(new Dimension(0, 130));
        areaImagem.setMaximumSize(new Dimension(Integer.MAX_VALUE, 130));
        areaImagem.setAlignmentX(Component.LEFT_ALIGNMENT);

        // TODO: exibir imagem real via ImageIO quando disponível no banco
        card.add(areaImagem);

        // — Conteúdo do card —
        JPanel corpo = new JPanel();
        corpo.setOpaque(false);
        corpo.setLayout(new BoxLayout(corpo, BoxLayout.Y_AXIS));
        corpo.setBorder(new EmptyBorder(14, 16, 0, 16));

        // Badge de status
        JPanel linhaBadge = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        linhaBadge.setOpaque(false);
        linhaBadge.add(criarBadgeStatus(d.status));
        corpo.add(linhaBadge);
        corpo.add(Box.createVerticalStrut(10));

        // Nome do alimento
        JLabel lblNome = new JLabel(d.nomeAlimento);
        lblNome.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblNome.setForeground(EstiloReAlimenta.TEXTO);
        lblNome.setAlignmentX(Component.LEFT_ALIGNMENT);
        corpo.add(lblNome);
        corpo.add(Box.createVerticalStrut(6));

        // Quantidade + horário
        corpo.add(criarInfoLinha(FontAwesomeSolid.BOXES, "Quantidade: " + d.quantidade + " un."));
        corpo.add(Box.createVerticalStrut(4));
        corpo.add(criarInfoLinha(FontAwesomeSolid.CLOCK, "Retirada: " + d.horarioRetirada));
        corpo.add(Box.createVerticalStrut(14));

        // Separador
        JSeparator sepCard = new JSeparator();
        sepCard.setForeground(EstiloReAlimenta.BORDA);
        sepCard.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        sepCard.setAlignmentX(Component.LEFT_ALIGNMENT);
        corpo.add(sepCard);
        corpo.add(Box.createVerticalStrut(12));

        // Botões de ação
        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        botoes.setOpaque(false);
        botoes.add(criarBotaoAcao("Ver Detalhes", FontAwesomeSolid.EYE, new Color(0x25, 0x63, 0xEB), () -> JOptionPane.showMessageDialog(this, "Detalhes: " + d.nomeAlimento)));
        botoes.add(criarBotaoAcao("Editar", FontAwesomeSolid.EDIT, EstiloReAlimenta.VERDE_PRINCIPAL, () -> JOptionPane.showMessageDialog(this, "Editar: " + d.nomeAlimento)));
        botoes.add(criarBotaoIconeSimples(FontAwesomeSolid.TRASH_ALT, new Color(0xDC, 0x26, 0x26), () -> confirmarExclusao(d)));
        corpo.add(botoes);
        card.add(corpo);
        return card;
    }

    private JLabel criarBadgeStatus(String status) {
        JLabel badge = new JLabel(status);
        badge.setFont(new Font("Segoe UI", Font.BOLD, 11));
        badge.setForeground(Color.WHITE);
        badge.setOpaque(true);
        badge.setBorder(new EmptyBorder(3, 10, 3, 10));

        switch (status) {
            case "Disponível":
                badge.setBackground(new Color(0x16, 0xA3, 0x4A));
                break;
            case "Retirado":
                badge.setBackground(new Color(0x25, 0x63, 0xEB));
                break;
            case "Expirada":
                badge.setBackground(new Color(0x6B, 0x72, 0x80));
                break;
            default:
                badge.setBackground(EstiloReAlimenta.TEXTO_SUAVE);
        }
        return badge;
    }

    private JPanel criarInfoLinha(FontAwesomeSolid icone, String texto) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        p.setOpaque(false);
        p.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.add(EstiloReAlimenta.criarIcone(icone, 13, EstiloReAlimenta.TEXTO_SUAVE));
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lbl.setForeground(EstiloReAlimenta.TEXTO_SUAVE);
        p.add(lbl);
        return p;
    }

    private JButton criarBotaoAcao(String texto, FontAwesomeSolid icone, Color cor, Runnable acao) {
        JButton btn = new JButton(texto) {
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
                Color base = new Color(cor.getRed(), cor.getGreen(), cor.getBlue(), 20);
                g2.setColor(hover ? new Color(cor.getRed(), cor.getGreen(), cor.getBlue(), 40) : base);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.setColor(cor);
                g2.setStroke(new BasicStroke(1f));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 8, 8);
                super.paintComponent(g);
            }
        };
        btn.setIcon(FontIcon.of(icone, 13, cor));
        btn.setText("  " + texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 11));
        btn.setForeground(cor);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(110, 30));
        btn.addActionListener(e -> acao.run());
        return btn;
    }

    private JButton criarBotaoIconeSimples(FontAwesomeSolid icone, Color cor, Runnable acao) {
        JButton btn = new JButton(FontIcon.of(icone, 14, cor));
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(30, 30));
        btn.addActionListener(e -> acao.run());
        return btn;
    }

    // Filtragem
    private void filtrarCards() {
        String busca  = campoBusca.getText().toLowerCase().trim();
        String status = (String) campoFiltro.getSelectedItem();
        listaFiltrada.clear();
        for (Doacao d : listaDoacao) {
            boolean matchBusca  = busca.isEmpty() || d.nomeAlimento.toLowerCase().contains(busca);
            boolean matchStatus = "Todos".equals(status) || d.status.equals(status);
            if (matchBusca && matchStatus) listaFiltrada.add(d);
        }
        renderizarCards();
    }

    private void confirmarExclusao(Doacao d) {
        int res = JOptionPane.showConfirmDialog(this,
                "Deseja excluir a doação de \"" + d.nomeAlimento + "\"?",
                "Confirmar exclusão", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (res == JOptionPane.YES_OPTION) {
            listaDoacao.remove(d);
            filtrarCards();
        }
    }

    // Mock de dados
    private void popularMocks() {
        // TODO: substituir por DoacaoDAO.listarPorComerciante(comercianteId)
        listaDoacao.add(new Doacao("Pão Integral",       8,  "14:00 - 18:00", "Disponível"));
        listaDoacao.add(new Doacao("Iogurte Natural",    12, "10:00 - 12:00", "Disponível"));
        listaDoacao.add(new Doacao("Leite Integral",     5,  "09:00 - 11:00", "Retirado"));
        listaDoacao.add(new Doacao("Maçã Gala",          20, "15:00 - 17:00", "Disponível"));
        listaDoacao.add(new Doacao("Peito de Frango",    3,  "08:00 - 10:00", "Expirada"));
        listaDoacao.add(new Doacao("Arroz Parboilizado", 10, "11:00 - 13:00", "Disponível"));
    }

    // Modelo interno — mover para model/Doacao.java futuramente
    public static class Doacao {
        public String nomeAlimento;
        public int    quantidade;
        public String horarioRetirada;
        public String status;

        public Doacao(String nomeAlimento, int quantidade, String horarioRetirada, String status) {
            this.nomeAlimento   = nomeAlimento;
            this.quantidade     = quantidade;
            this.horarioRetirada = horarioRetirada;
            this.status         = status;
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