package view;
import util.EstiloReAlimenta;
import util.EstiloReAlimenta.RoundedPanel;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.swing.FontIcon;
import model.Comerciante;
import controller.PromocaoController;
import model.Promocao;
import java.time.LocalDate;

public class TelaMinhasPromocoes extends JFrame {

    // ===== Constantes de layout =====
    private static final int MARGEM_TABELA        = 24;
    private static final int ESPACO_LINHAS         = 12;
    private static final int ALTURA_LINHA          = 56;
    private static final int LARGURA_COLUNA_STATUS = 110;
    private static final int LARGURA_COLUNA_ACOES  = 90;
    private static final int ESPACO_COLUNAS        = 8;

    private static final String[] FILTRO_CATEGORIAS = {
            "Todas", "Laticínios", "Padaria", "Frutas e Verduras", "Carnes", "Bebidas", "Grãos e Cereais"
    };

    private List<Promocao> listaPromocoes = new ArrayList<Promocao>();
    private List<Promocao> listaFiltrada  = new ArrayList<Promocao>();
    private JTextField campoBusca;
    private JComboBox<String> campoFiltro;
    private JPanel painelLista;

    private Comerciante comerciante;

    public TelaMinhasPromocoes(Comerciante comerciante) {
        this.comerciante = comerciante;
        carregarPromocoes();
        configurarJanela();
        construirLayout();
        setVisible(true);
    }

    private void configurarJanela() {
        setTitle("ReAlimenta | Minhas Promoções");
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

    // ===================== SIDEBAR (não alterada) =====================
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
        sidebar.add(criarItemMenuAtivo(FontAwesomeSolid.TAG, "Promoções"));
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

    // ===================== CONTEÚDO =====================
    private JScrollPane criarConteudo() {
        JPanel conteudo = new JPanel();
        conteudo.setLayout(new BoxLayout(conteudo, BoxLayout.Y_AXIS));
        conteudo.setBackground(EstiloReAlimenta.FUNDO);
        conteudo.setBorder(new EmptyBorder(32, 32, 32, 32));
        conteudo.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Título
        JLabel titulo = EstiloReAlimenta.criarTitulo("Minhas Promoções");
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        conteudo.add(titulo);
        conteudo.add(Box.createVerticalStrut(4));

        // Subtítulo
        JLabel subtitulo = EstiloReAlimenta.criarSubtitulo("Gerencie as promoções dos seus alimentos cadastrados.");
        subtitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        conteudo.add(subtitulo);
        conteudo.add(Box.createVerticalStrut(20));

        // Barra de ações
        JPanel barra = criarBarraAcoes();
        barra.setAlignmentX(Component.LEFT_ALIGNMENT);
        conteudo.add(barra);
        conteudo.add(Box.createVerticalStrut(18));

        // Cabeçalho
        JPanel cabecalho = criarCabecalhoTabela();
        cabecalho.setAlignmentX(Component.LEFT_ALIGNMENT);
        conteudo.add(cabecalho);
        conteudo.add(Box.createVerticalStrut(6));

        // Lista
        painelLista = new JPanel();
        painelLista.setLayout(new BoxLayout(painelLista, BoxLayout.Y_AXIS));
        painelLista.setOpaque(false);
        painelLista.setAlignmentX(Component.LEFT_ALIGNMENT);
        renderizarLista();
        conteudo.add(painelLista);

        // Scroll
        JScrollPane scroll = new JScrollPane(conteudo);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(EstiloReAlimenta.FUNDO);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        return scroll;
    }

    // ----- Barra de ações: botão + busca + filtro, todos na mesma linha -----
    private JPanel criarBarraAcoes() {
        JPanel barra = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        barra.setOpaque(false);
        barra.setAlignmentX(Component.LEFT_ALIGNMENT);
        barra.setMaximumSize(new Dimension(Integer.MAX_VALUE, 46));

        JButton btnNova = EstiloReAlimenta.criarBotaoPrimario("+ Nova Promoção", e -> { dispose(); new TelaNovaPromocao(comerciante); });
        btnNova.setPreferredSize(new Dimension(190, 44));
        barra.add(btnNova);

        campoBusca = new JTextField();
        JPanel wrapperBusca = EstiloReAlimenta.criarCampoTexto(
                campoBusca, "Buscar promoção...", FontAwesomeSolid.SEARCH);
        wrapperBusca.setPreferredSize(new Dimension(500, 44));
        wrapperBusca.setMaximumSize(new Dimension(500, 44));
        wrapperBusca.setMinimumSize(new Dimension(500, 44));
        barra.add(wrapperBusca);

        campoFiltro = new JComboBox<String>(FILTRO_CATEGORIAS);
        campoFiltro.setFont(EstiloReAlimenta.FONTE_CAMPO);
        campoFiltro.setBackground(EstiloReAlimenta.BRANCO);
        campoFiltro.setPreferredSize(new Dimension(180, 44));
        campoFiltro.addActionListener(e -> filtrarLista());
        barra.add(campoFiltro);

        campoBusca.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                filtrarLista();
            }
        });

        return barra;
    }

    private final double[] PESOS = {
            2.2, // alimento
            1.5, // categoria
            1.5, // preco original
            1.2, // desconto
            1.5, // preco promo
            1.5, // validade
            1.1, // status
            0.8  // ações
    };
    private static final int[] LARGURAS_COLUNA = {
            150, // alimento
            100, // categoria
            110, // preco original
            90,  // desconto
            110, // preco promo
            100, // validade
            90,  // status
            80   // ações
    };

    private GridBagConstraints colunaFlex(int gx) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = gx;
        c.gridy = 0;
        c.weightx = PESOS[gx];
        c.weighty = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(0, 0, 0, ESPACO_COLUNAS);
        return c;
    }

    private GridBagConstraints colunaFixa(int gx, int largura, boolean ultima) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = gx; c.gridy = 0; c.weightx = 0; c.weighty = 1;
        c.ipadx = largura;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.CENTER;
        c.insets = ultima ? new Insets(0, 0, 0, 0) : new Insets(0, 0, 0, ESPACO_COLUNAS);
        return c;
    }

    private JLabel criarTituloColuna(String texto, int gx) {
        JLabel lbl = new JLabel(texto, SwingConstants.CENTER);
        lbl.setFont(EstiloReAlimenta.FONTE_LABEL);
        lbl.setForeground(EstiloReAlimenta.TEXTO_SUAVE);
        lbl.setPreferredSize(new Dimension(LARGURAS_COLUNA[gx], 20));
        return lbl;
    }

    private JPanel criarCabecalhoTabela() {
        JPanel cabecalho = new JPanel(new GridBagLayout());
        cabecalho.setOpaque(false);
        cabecalho.setAlignmentX(Component.LEFT_ALIGNMENT);
        cabecalho.setBorder(new EmptyBorder(0, MARGEM_TABELA, 0, MARGEM_TABELA));
        cabecalho.setMaximumSize(new Dimension(Integer.MAX_VALUE, 24));

        cabecalho.add(criarTituloColuna("Alimento", 0), colunaFlex(0));
        cabecalho.add(criarTituloColuna("Categoria", 1), colunaFlex(1));
        cabecalho.add(criarTituloColuna("Preço Original", 2), colunaFlex(2));
        cabecalho.add(criarTituloColuna("Desconto", 3), colunaFlex(3));
        cabecalho.add(criarTituloColuna("Preço Promo.", 4), colunaFlex(4));
        cabecalho.add(criarTituloColuna("Validade", 5), colunaFlex(5));
        cabecalho.add(criarTituloColuna("Status", 6), colunaFlex(6));
        cabecalho.add(criarTituloColuna("Ações", 7), colunaFlex(7));

        return cabecalho;
    }

    private void renderizarLista() {
        painelLista.removeAll();
        if (listaFiltrada.isEmpty()) {
            JLabel vazio = new JLabel("Nenhuma promoção encontrada.");
            vazio.setFont(EstiloReAlimenta.FONTE_CAMPO);
            vazio.setForeground(EstiloReAlimenta.TEXTO_SUAVE);
            painelLista.add(Box.createVerticalStrut(32));
            painelLista.add(vazio);
        } else {
            for (Promocao p : listaFiltrada) {
                JPanel linha = criarLinhaPromocao(p);
                linha.setAlignmentX(Component.LEFT_ALIGNMENT);
                painelLista.add(linha);
                painelLista.add(Box.createVerticalStrut(ESPACO_LINHAS));
            }
        }
        painelLista.revalidate();
        painelLista.repaint();
    }

    private JPanel criarLinhaPromocao(Promocao p) {
        RoundedPanel linha = new RoundedPanel(10, EstiloReAlimenta.BRANCO);
        linha.setLayout(new GridBagLayout());
        linha.setBorder(new EmptyBorder(0, MARGEM_TABELA, 0, MARGEM_TABELA));
        linha.setAlignmentX(Component.LEFT_ALIGNMENT);
        linha.setPreferredSize(new Dimension(0, 64));
        linha.setMaximumSize(new Dimension(Integer.MAX_VALUE, ALTURA_LINHA));
        linha.setMinimumSize(new Dimension(Integer.MAX_VALUE, 64));

        int gx = 0;

        JPanel celNome = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        celNome.setOpaque(false);
        celNome.setPreferredSize(new Dimension(LARGURAS_COLUNA[0], 30));
        celNome.add(EstiloReAlimenta.criarIcone(FontAwesomeSolid.APPLE_ALT, 20, EstiloReAlimenta.VERDE_PRINCIPAL));
        JLabel lblNome = new JLabel(p.getAlimento().getNomeAlimento());
        lblNome.setFont(EstiloReAlimenta.FONTE_BOTAO);
        lblNome.setForeground(EstiloReAlimenta.TEXTO);
        celNome.add(lblNome);
        linha.add(celNome, colunaFlex(gx++));

        linha.add(criarCelula(p.getAlimento().getCategoria(), false, gx), colunaFlex(gx++));
        linha.add(criarCelula("R$ " + String.format("%.2f", p.getAlimento().getValor()), false, gx), colunaFlex(gx++));
        linha.add(criarCelula(p.getPercentualDesconto() + "%", true, gx), colunaFlex(gx++));
        linha.add(criarCelula("R$ " + String.format("%.2f", calcularPrecoPromo(p.getAlimento().getValor(), p.getPercentualDesconto())), false, gx), colunaFlex(gx++));
        linha.add(criarCelula(p.getFimPromocao().toString(), false, gx), colunaFlex(gx++));

        JPanel celStatus = criarColunaStatus(p);
        celStatus.setPreferredSize(new Dimension(LARGURAS_COLUNA[6], 30));
        linha.add(celStatus, colunaFlex(gx++));

        JPanel celAcoes = criarColunaAcoes(p);
        celAcoes.setPreferredSize(new Dimension(LARGURAS_COLUNA[7], 30));
        linha.add(celAcoes, colunaFlex(gx++));

        return linha;
    }

    private JLabel criarCelula(String texto, boolean destaque, int gx) {
        JLabel lbl = new JLabel(texto, SwingConstants.CENTER);
        lbl.setFont(destaque ? EstiloReAlimenta.FONTE_BOTAO : EstiloReAlimenta.FONTE_CAMPO);
        lbl.setForeground(destaque ? new Color(0x16, 0xA3, 0x4A) : EstiloReAlimenta.TEXTO);
        lbl.setPreferredSize(new Dimension(LARGURAS_COLUNA[gx], 20));
        return lbl;
    }

    // Coluna Status — largura fixa, badge verde centralizado
    private JPanel criarColunaStatus(Promocao p) {
        JPanel cel = new JPanel(new GridBagLayout());
        cel.setOpaque(false);

        String status = LocalDate.now().isAfter(p.getFimPromocao()) ? "Expirada" : "Ativa";
        boolean ativa = "Ativa".equals(status);

        JLabel badge = new JLabel(status, SwingConstants.CENTER);
        badge.setFont(EstiloReAlimenta.FONTE_LABEL);
        badge.setForeground(Color.WHITE);
        badge.setBackground(ativa ? EstiloReAlimenta.VERDE_PRINCIPAL : EstiloReAlimenta.TEXTO_SUAVE);
        badge.setOpaque(true);
        badge.setBorder(new EmptyBorder(4, 14, 4, 14));

        cel.add(badge);
        return cel;
    }

    // Coluna Ações — Editar e Excluir centralizados
    private JPanel criarColunaAcoes(Promocao p) {
        JPanel cel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        cel.setOpaque(false);

        cel.add(criarBotaoIcone(FontAwesomeSolid.EDIT, new Color(0x25, 0x63, 0xEB),
                () -> JOptionPane.showMessageDialog(this, "Editar: " + p.getAlimento().getNomeAlimento())));
        cel.add(criarBotaoIcone(FontAwesomeSolid.TRASH_ALT, new Color(0xDC, 0x26, 0x26),
                () -> confirmarExclusao(p)));

        return cel;
    }

    private JButton criarBotaoIcone(FontAwesomeSolid icone, Color cor, Runnable acao) {
        JButton btn = new JButton(FontIcon.of(icone, 15, cor));
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(28, 28));
        btn.addActionListener(e -> acao.run());
        return btn;
    }

    private void confirmarExclusao(Promocao p) {
        int res = JOptionPane.showConfirmDialog(this,
                "Deseja excluir a promoção de \"" + p.getAlimento().getNomeAlimento() + "\"?",
                "Confirmar exclusão", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (res == JOptionPane.YES_OPTION) {
            listaPromocoes.remove(p);
            filtrarLista();
        }
    }

    private double calcularPrecoPromo(double preco, int desconto) {
        return preco * (1 - desconto / 100.0);
    }

    private void filtrarLista() {
        String busca = campoBusca.getText().toLowerCase().trim();
        String categoria = (String) campoFiltro.getSelectedItem();
        listaFiltrada.clear();
        for (Promocao p : listaPromocoes) {
            boolean matchBusca = busca.isEmpty() || p.getAlimento().getNomeAlimento().toLowerCase().contains(busca) || p.getAlimento().getCategoria().toLowerCase().contains(busca);
            boolean matchCat = "Todas".equals(categoria) || p.getAlimento().getCategoria().equals(categoria);
            if (matchBusca && matchCat) listaFiltrada.add(p);
        }
        renderizarLista();
    }

    private void carregarPromocoes() {
        PromocaoController controller = new PromocaoController();
        listaPromocoes = controller.listarPromocoesPorComerciante(comerciante.getIdComerciante());
        listaFiltrada.clear();
        listaFiltrada.addAll(listaPromocoes);
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