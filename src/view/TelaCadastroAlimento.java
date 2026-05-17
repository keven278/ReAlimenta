package view;
import util.EstiloReAlimenta;
import util.EstiloReAlimenta.RoundedPanel;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;

public class TelaCadastroAlimento extends JFrame {

    // Categorias — substituir por SELECT MySQL no futuro
    private static final String[] CATEGORIAS = {
            "Selecionar categoria...", "Laticínios", "Padaria", "Frutas e Verduras",
            "Carnes", "Bebidas", "Grãos e Cereais", "Congelados", "Outros"
    };

    // Campos do formulário
    private JTextField    campoNome;
    private JComboBox<String> campoCategoria;
    private JTextField    campoValidade;
    private JSpinner      campoQuantidade;
    private JTextArea     campoDescricao;

    // Upload de imagem
    private JLabel        previewImagem;
    private File          arquivoImagem;
    private JLabel        labelCaminhoImagem;

    public TelaCadastroAlimento() {
        configurarJanela();
        construirLayout();
        setVisible(true);
    }

    private void configurarJanela() {
        setTitle("ReAlimenta | Cadastro de Alimento");
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

    // Sidebar (Alimentos em destaque)
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
        sidebar.add(criarItemMenuAtivo(FontAwesomeSolid.APPLE_ALT, "Alimentos"));
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

    // Conteúdo principal
    private JScrollPane criarConteudo() {
        JPanel conteudo = new JPanel();
        conteudo.setLayout(new BoxLayout(conteudo, BoxLayout.Y_AXIS));
        conteudo.setBackground(EstiloReAlimenta.FUNDO);
        conteudo.setBorder(new EmptyBorder(32, 32, 32, 32));

        // Cabeçalho
        JLabel titulo = EstiloReAlimenta.criarTitulo("Cadastrar Alimento");
        JLabel sub    = EstiloReAlimenta.criarSubtitulo("Preencha os dados do alimento. Ele será usado em promoções e doações.");
        conteudo.add(titulo);
        conteudo.add(Box.createVerticalStrut(4));
        conteudo.add(sub);
        conteudo.add(Box.createVerticalStrut(28));

        // Grid: formulário (esq) + upload imagem (dir)
        JPanel grid = new JPanel(new GridLayout(1, 2, 24, 0));
        grid.setOpaque(false);
        grid.add(criarPainelFormulario());
        grid.add(criarPainelUploadImagem());
        conteudo.add(grid);
        conteudo.add(Box.createVerticalStrut(24));

        // Botão salvar
        JPanel rodape = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        rodape.setOpaque(false);
        JButton btnSalvar = EstiloReAlimenta.criarBotaoPrimario("  Salvar Alimento", e -> salvarAlimento());
        btnSalvar.setPreferredSize(new Dimension(200, 44));
        rodape.add(btnSalvar);
        conteudo.add(rodape);

        JScrollPane scroll = new JScrollPane(conteudo);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(EstiloReAlimenta.FUNDO);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        return scroll;
    }

    // Painel do formulário
    private JPanel criarPainelFormulario() {
        RoundedPanel painel = new RoundedPanel(14, EstiloReAlimenta.BRANCO);
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBorder(new EmptyBorder(24, 24, 24, 24));

        JLabel tituloCard = new JLabel("Informações do Alimento");
        tituloCard.setFont(new Font("Segoe UI", Font.BOLD, 15));
        tituloCard.setForeground(EstiloReAlimenta.TEXTO);
        painel.add(tituloCard);
        painel.add(Box.createVerticalStrut(20));

        // Nome
        painel.add(EstiloReAlimenta.criarLabel("Nome do Alimento *"));
        painel.add(Box.createVerticalStrut(6));
        campoNome = new JTextField();
        painel.add(EstiloReAlimenta.criarCampoTexto(campoNome, "Ex: Pão de Forma Integral", FontAwesomeSolid.CARROT));
        painel.add(Box.createVerticalStrut(16));

        // Categoria
        painel.add(EstiloReAlimenta.criarLabel("Categoria *"));
        painel.add(Box.createVerticalStrut(6));
        campoCategoria = new JComboBox<String>(CATEGORIAS);
        campoCategoria.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        campoCategoria.setBackground(EstiloReAlimenta.BRANCO);
        campoCategoria.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        campoCategoria.setAlignmentX(Component.LEFT_ALIGNMENT);
        painel.add(campoCategoria);
        painel.add(Box.createVerticalStrut(16));

        // Data de validade
        painel.add(EstiloReAlimenta.criarLabel("Data de Validade *"));
        painel.add(Box.createVerticalStrut(6));
        campoValidade = new JTextField();
        painel.add(EstiloReAlimenta.criarCampoTexto(campoValidade, "DD/MM/AAAA", FontAwesomeSolid.CALENDAR_ALT));
        painel.add(Box.createVerticalStrut(16));

        // Quantidade
        painel.add(EstiloReAlimenta.criarLabel("Quantidade Disponível *"));
        painel.add(Box.createVerticalStrut(6));
        SpinnerNumberModel modelo = new SpinnerNumberModel(1, 1, 9999, 1);
        campoQuantidade = new JSpinner(modelo);
        campoQuantidade.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        campoQuantidade.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        campoQuantidade.setAlignmentX(Component.LEFT_ALIGNMENT);
        painel.add(campoQuantidade);
        painel.add(Box.createVerticalStrut(16));

        // Descrição
        painel.add(EstiloReAlimenta.criarLabel("Descrição"));
        painel.add(Box.createVerticalStrut(6));
        campoDescricao = new JTextArea(4, 20);
        campoDescricao.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        campoDescricao.setForeground(EstiloReAlimenta.TEXTO);
        campoDescricao.setLineWrap(true);
        campoDescricao.setWrapStyleWord(true);
        campoDescricao.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(EstiloReAlimenta.BORDA, 1, true),
                new EmptyBorder(8, 10, 8, 10)));
        campoDescricao.setAlignmentX(Component.LEFT_ALIGNMENT);
        JScrollPane scrollDesc = new JScrollPane(campoDescricao);
        scrollDesc.setBorder(null);
        scrollDesc.setAlignmentX(Component.LEFT_ALIGNMENT);
        scrollDesc.setMaximumSize(new Dimension(Integer.MAX_VALUE, 110));
        painel.add(scrollDesc);

        painel.add(Box.createVerticalGlue());
        return painel;
    }

    // Painel upload de imagem
    private JPanel criarPainelUploadImagem() {
        RoundedPanel painel = new RoundedPanel(14, EstiloReAlimenta.BRANCO);
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBorder(new EmptyBorder(24, 24, 24, 24));

        JLabel tituloCard = new JLabel("Imagem do Alimento");
        tituloCard.setFont(new Font("Segoe UI", Font.BOLD, 15));
        tituloCard.setForeground(EstiloReAlimenta.TEXTO);
        painel.add(tituloCard);
        painel.add(Box.createVerticalStrut(4));
        painel.add(EstiloReAlimenta.criarSubtitulo("Adicione uma foto para identificar o alimento"));
        painel.add(Box.createVerticalStrut(20));

        // Área de pré-visualização
        previewImagem = new JLabel("Nenhuma imagem selecionada") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getIcon() == null) {
                    // Estado vazio com tracejado
                    g2.setColor(EstiloReAlimenta.VERDE_CLARO);
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 14, 14);
                    float[] dash = {8f, 4f};
                    g2.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10f, dash, 0f));
                    g2.setColor(EstiloReAlimenta.VERDE_PRINCIPAL);
                    g2.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 14, 14);
                    g2.setColor(EstiloReAlimenta.VERDE_PRINCIPAL);
                    g2.setFont(new Font("Segoe UI", Font.PLAIN, 13));
                    FontMetrics fm = g2.getFontMetrics();
                    String t = "Clique para selecionar";
                    g2.drawString(t, (getWidth() - fm.stringWidth(t)) / 2, getHeight() / 2 + 20);
                }
                g2.dispose();
                super.paintComponent(g);
            }
        };
        previewImagem.setHorizontalAlignment(SwingConstants.CENTER);
        previewImagem.setVerticalAlignment(SwingConstants.CENTER);
        previewImagem.setPreferredSize(new Dimension(0, 220));
        previewImagem.setMaximumSize(new Dimension(Integer.MAX_VALUE, 220));
        previewImagem.setAlignmentX(Component.LEFT_ALIGNMENT);
        previewImagem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        previewImagem.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) { selecionarImagem(); }
        });
        painel.add(previewImagem);
        painel.add(Box.createVerticalStrut(12));

        // Caminho do arquivo
        labelCaminhoImagem = new JLabel("Nenhum arquivo selecionado");
        labelCaminhoImagem.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        labelCaminhoImagem.setForeground(EstiloReAlimenta.TEXTO_SUAVE);
        labelCaminhoImagem.setAlignmentX(Component.LEFT_ALIGNMENT);
        painel.add(labelCaminhoImagem);
        painel.add(Box.createVerticalStrut(16));

        // Botões de upload
        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        btnRow.setOpaque(false);
        btnRow.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton btnSelecionar = EstiloReAlimenta.criarBotaoPrimario("Selecionar Imagem", e -> selecionarImagem());
        btnSelecionar.setPreferredSize(new Dimension(160, 40));
        JButton btnRemover = EstiloReAlimenta.criarBotaoSecundario("Remover", e -> removerImagem());
        btnRemover.setPreferredSize(new Dimension(100, 40));

        btnRow.add(btnSelecionar);
        btnRow.add(btnRemover);
        painel.add(btnRow);
        painel.add(Box.createVerticalStrut(20));

        // Dica
        JLabel dica = new JLabel("Formatos aceitos: JPG, PNG, WEBP — Máx. 5MB");
        dica.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        dica.setForeground(EstiloReAlimenta.TEXTO_SUAVE);
        dica.setAlignmentX(Component.LEFT_ALIGNMENT);
        painel.add(dica);
        painel.add(Box.createVerticalGlue());
        return painel;
    }

    // Ações
    private void selecionarImagem() {
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Selecionar imagem do alimento");
        fc.setFileFilter(new FileNameExtensionFilter("Imagens (JPG, PNG, WEBP)", "jpg", "jpeg", "png", "webp"));
        if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            arquivoImagem = fc.getSelectedFile();
            labelCaminhoImagem.setText(arquivoImagem.getName());
            // Carregar pré-visualização
            ImageIcon img = new ImageIcon(arquivoImagem.getAbsolutePath());
            Image scaled = img.getImage().getScaledInstance(previewImagem.getWidth() > 0 ? previewImagem.getWidth() : 300, 200, Image.SCALE_SMOOTH);
            previewImagem.setIcon(new ImageIcon(scaled));
            previewImagem.setText(null);
            previewImagem.repaint();
        }
    }

    private void removerImagem() {
        arquivoImagem = null;
        previewImagem.setIcon(null);
        previewImagem.setText("Nenhuma imagem selecionada");
        labelCaminhoImagem.setText("Nenhum arquivo selecionado");
        previewImagem.repaint();
    }

    private void salvarAlimento() {
        // Validação básica — lógica de integração com DAO aqui no futuro
        String nome = campoNome.getText().trim();
        String validade = campoValidade.getText().trim();
        String categoria = (String) campoCategoria.getSelectedItem();
        int quantidade = (Integer) campoQuantidade.getValue();
        String descricao = campoDescricao.getText().trim();

        if (nome.isEmpty() || nome.equals("Ex: Pão de Forma Integral")) {
            JOptionPane.showMessageDialog(this, "Por favor, informe o nome do alimento.", "Campo obrigatório", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if ("Selecionar categoria...".equals(categoria)) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione uma categoria.", "Campo obrigatório", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (validade.isEmpty() || validade.equals("DD/MM/AAAA")) {
            JOptionPane.showMessageDialog(this, "Por favor, informe a data de validade.", "Campo obrigatório", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // TODO: AlimentoDAO.salvar(new Alimento(nome, categoria, validade, quantidade, descricao, arquivoImagem))
        JOptionPane.showMessageDialog(this, "Alimento \"" + nome + "\" salvo com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }

    // Navegação
    private void navegarDashboard()    { dispose(); new TelaDashboardComerciante(); }
    private void navegarAlimentos()    { dispose(); new TelaCadastroAlimento(); }
    private void navegarPromocoes()    { dispose(); new TelaMinhasPromocoes(); }
    private void navegarDoacoes()      { dispose(); new TelaDoacoesComerciante(); }
    private void navegarSolicitacoes() { dispose(); new TelaSolicitacoesComerciante(); }
    private void navegarNotificacoes() { dispose(); new TelaNotificacoesComerciante(); }
    private void sair()                { dispose(); new TelaLogin(); }

}