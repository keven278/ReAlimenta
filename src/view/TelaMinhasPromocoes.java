package view;
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
import model.Comerciante;
import controller.PromocaoController;
import model.Promocao;
import java.time.LocalDate;

public class TelaMinhasPromocoes extends JFrame {
    private static final String[] FILTRO_CATEGORIAS = {
            "Todas", "Laticínios", "Padaria", "Frutas e Verduras", "Carnes", "Bebidas", "Grãos e Cereais"
    };

    // Lista de promoções — será carregada via DAO no futuro
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

    // Sidebar (Promoções em destaque)
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
        sidebar.add(EstiloReAlimenta.criarItemMenu(FontAwesomeSolid.SIGN_OUT_ALT, "Sair",
                this::sair));
        return sidebar;
    }

    // Conteúdo
    private JScrollPane criarConteudo() {
        JPanel conteudo = new JPanel();
        conteudo.setLayout(new BoxLayout(conteudo, BoxLayout.Y_AXIS));
        conteudo.setBackground(EstiloReAlimenta.FUNDO);
        conteudo.setBorder(new EmptyBorder(32, 32, 32, 32));

        conteudo.add(criarCabecalhoPagina());
        conteudo.add(Box.createVerticalStrut(24));
        JPanel barraFiltros = criarBarraFiltros();
        conteudo.add(barraFiltros);
        conteudo.add(Box.createVerticalStrut(20));

        // Cabeçalho da tabela
        conteudo.add(criarCabecalhoTabela());
        conteudo.add(Box.createVerticalStrut(8));

        // Lista dinâmica de promoções
        painelLista = new JPanel();
        painelLista.setLayout(new BoxLayout(painelLista, BoxLayout.Y_AXIS));
        painelLista.setOpaque(false);
        renderizarLista();
        conteudo.add(painelLista);

        JScrollPane scroll = new JScrollPane(conteudo);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(EstiloReAlimenta.FUNDO);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        return scroll;
    }

    private JPanel criarCabecalhoPagina() {
        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setOpaque(false);
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));

        // TÍTULO
        painelPrincipal.add(EstiloReAlimenta.criarTitulo("Minhas Promoções"));
        painelPrincipal.add(Box.createVerticalStrut(4));

        // SUBTÍTULO
        painelPrincipal.add(EstiloReAlimenta.criarSubtitulo("Gerencie as promoções dos seus alimentos cadastrados."));
        painelPrincipal.add(Box.createVerticalStrut(20));

        // BOTÃO NOVA PROMOÇÃO
        JButton btnNova = EstiloReAlimenta.criarBotaoPrimario("+ Nova Promoção", e -> { dispose(); new TelaNovaPromocao(comerciante); });
        btnNova.setPreferredSize(new Dimension(220, 46));
        JPanel painelBotao = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        painelBotao.setOpaque(false);
        painelBotao.add(btnNova);
        painelPrincipal.add(painelBotao);

        return painelPrincipal;
    }

    private JPanel criarBarraFiltros() {
        JPanel barra = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        barra.setOpaque(false);
        barra.setAlignmentX(Component.LEFT_ALIGNMENT);
        barra.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        barra.setPreferredSize(new Dimension(800, 50));

        campoBusca = new JTextField();
        JPanel wrapperBusca = EstiloReAlimenta.criarCampoTexto(
                campoBusca,
                "Buscar promoção...",
                FontAwesomeSolid.SEARCH
        );

        wrapperBusca.setPreferredSize(new Dimension(400, 44));
        wrapperBusca.setMaximumSize(new Dimension(400, 44));
        wrapperBusca.setMinimumSize(new Dimension(400, 44));

        barra.add(wrapperBusca);

        campoFiltro = new JComboBox<String>(FILTRO_CATEGORIAS);
        campoFiltro.setFont(new Font("Segoe UI", Font.PLAIN, 13));
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

    private JPanel criarCabecalhoTabela() {
        JPanel header = new JPanel(new GridLayout(1, 7, 8, 0));
        header.setOpaque(false);
        header.setBorder(new EmptyBorder(0, 16, 8, 16));

        String[] colunas = { "Alimento", "Categoria", "Preço Original", "Desconto", "Preço Promo.", "Validade", "Status" };
        for (String col : colunas) {
            JLabel lbl = new JLabel(col);
            lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
            lbl.setForeground(EstiloReAlimenta.TEXTO_SUAVE);
            header.add(lbl);
        }
        return header;
    }

    // Renderiza os itens da lista filtrada
    private void renderizarLista() {
        painelLista.removeAll();
        if (listaFiltrada.isEmpty()) {
            JLabel vazio = new JLabel("Nenhuma promoção encontrada.");
            vazio.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            vazio.setForeground(EstiloReAlimenta.TEXTO_SUAVE);
            vazio.setAlignmentX(Component.LEFT_ALIGNMENT);
            painelLista.add(Box.createVerticalStrut(32));
            painelLista.add(vazio);
        } else {
            for (Promocao p : listaFiltrada) {
                painelLista.add(criarLinhaPromocao(p));
                painelLista.add(Box.createVerticalStrut(8));
            }
        }
        painelLista.revalidate();
        painelLista.repaint();
    }

    private JPanel criarLinhaPromocao(Promocao p) {
        RoundedPanel linha = new RoundedPanel(10, EstiloReAlimenta.BRANCO);
        linha.setLayout(new GridLayout(1, 7, 8, 0));
        linha.setBorder(new EmptyBorder(14, 16, 14, 16));
        // Imagem + nome
        JPanel celNome = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        celNome.setOpaque(false);
        JLabel icoProduto = EstiloReAlimenta.criarIcone(FontAwesomeSolid.APPLE_ALT, 20, EstiloReAlimenta.VERDE_PRINCIPAL);
        JLabel lblNome = new JLabel(p.getAlimento().getNomeAlimento());
        lblNome.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblNome.setForeground(EstiloReAlimenta.TEXTO);
        celNome.add(icoProduto);
        celNome.add(lblNome);
        linha.add(celNome);

        linha.add(criarCelula(p.getAlimento().getCategoria(), false));
        linha.add(criarCelula("R$ " + String.format("%.2f", p.getAlimento().getValor()), false));
        linha.add(criarCelula(p.getPercentualDesconto() + "%", true));
        linha.add(criarCelula("R$ " + String.format("%.2f", calcularPrecoPromo(p.getAlimento().getValor(), p.getPercentualDesconto())), false));
        linha.add(criarCelula(p.getFimPromocao().toString(), false));
        linha.add(criarColunaAcoes(p));
        return linha;
    }

    private JLabel criarCelula(String texto, boolean destaque) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("Segoe UI", destaque ? Font.BOLD : Font.PLAIN, 13));
        lbl.setForeground(destaque ? new Color(0x16, 0xA3, 0x4A) : EstiloReAlimenta.TEXTO);
        return lbl;
    }

    private JPanel criarColunaAcoes(Promocao p) {
        JPanel cel = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        cel.setOpaque(false);

        String status = LocalDate.now().isAfter(p.getFimPromocao()) ? "Expirada" : "Ativa";
        boolean ativa = "Ativa".equals(status);

        JLabel badge = new JLabel(status);
        badge.setFont(new Font("Segoe UI", Font.BOLD, 11));
        badge.setForeground(Color.WHITE);
        badge.setBackground(ativa ? new Color(0x16, 0xA3, 0x4A) : new Color(0x6B, 0x72, 0x80));
        badge.setOpaque(true);
        badge.setBorder(new EmptyBorder(2, 8, 2, 8));
        cel.add(badge);

        JButton btnEditar = criarBotaoIcone(FontAwesomeSolid.EDIT, new Color(0x25, 0x63, 0xEB),
                () -> JOptionPane.showMessageDialog(this, "Editar: " + p.getAlimento().getNomeAlimento()));
        cel.add(btnEditar);

        JButton btnExcluir = criarBotaoIcone(FontAwesomeSolid.TRASH_ALT, new Color(0xDC, 0x26, 0x26),
                () -> confirmarExclusao(p));
        cel.add(btnExcluir);

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

    // Filtragem
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

        listaPromocoes = controller.listarPromocoesPorComerciante(
                comerciante.getIdComerciante()
        );

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