package view;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class TelaCadastroComerciante extends JFrame {
    private final JFrame telaPai;
    private JTextField    campoProprietario;
    private JTextField    campoLoja;
    private JTextField    campoCnpj;
    private JTextField    campoEndereco;
    private JTextField    campoTelefone;
    private JPasswordField campoSenha;
    private JPasswordField campoConfirmar;

    public TelaCadastroComerciante(JFrame telaPai) {
        this.telaPai = telaPai;
        configurarJanela();
        construirInterface();
        setVisible(true);
    }

    // Configurações básicas do JFrame
    private void configurarJanela() {
        setTitle("ReAlimenta | Cadastro de Comerciante");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override public void windowClosing(WindowEvent e) { voltarLogin(); }
        });
        setSize(580, 700);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(TelaLogin.FUNDO);
        setLayout(new BorderLayout());
    }

    // Monta header + área de conteúdo rolável
    private void construirInterface() {
        add(criarCabecalho(), BorderLayout.NORTH);
        add(criarAreaConteudo(), BorderLayout.CENTER);
    }

    // CABEÇALHO
    // Faixa cor verde escuro no topo com botão voltar e título
    private JPanel criarCabecalho() {
        JPanel header = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(
                        0, 0, TelaLogin.VERDE_PRINCIPAL,
                        getWidth(), 0, new Color(0x1A, 0x7A, 0x4A));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        header.setLayout(new BorderLayout());
        header.setPreferredSize(new Dimension(0, 76));
        header.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

        // Botão voltar
        header.add(criarBotaoVoltar(), BorderLayout.WEST);

        // título + subtítulo no bloco central
        JPanel centro = new JPanel();
        centro.setOpaque(false);
        centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));

        JLabel lblTitulo = new JLabel("Cadastro de Comerciante");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setForeground(TelaLogin.BRANCO);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblSub = new JLabel("Preencha os dados abaixo para criar sua conta");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSub.setForeground(new Color(255, 255, 255, 200));
        lblSub.setAlignmentX(Component.CENTER_ALIGNMENT);

        centro.add(Box.createVerticalGlue());
        centro.add(lblTitulo);
        centro.add(Box.createVerticalStrut(2));
        centro.add(lblSub);
        centro.add(Box.createVerticalGlue());
        header.add(centro, BorderLayout.CENTER);

        // Ícone loja no canto direito
        JLabel icone = new JLabel("🏪");
        icone.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        icone.setForeground(new Color(255, 255, 255, 180));
        header.add(icone, BorderLayout.EAST);

        return header;
    }

    // Botão "← Voltar" estilizado
    private JButton criarBotaoVoltar() {
        JButton btn = new JButton("←") {
            boolean hover = false;
            { addMouseListener(new MouseAdapter() {
                @Override public void mouseEntered(MouseEvent e) { hover = true; repaint(); }
                @Override public void mouseExited(MouseEvent e)  { hover = false; repaint(); }
            }); }
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (hover) {
                    g2.setColor(new Color(255, 255, 255, 40));
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                }
                g2.setColor(TelaLogin.BRANCO);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 20));
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(getText(),
                        (getWidth()  - fm.stringWidth(getText())) / 2,
                        (getHeight() + fm.getAscent() - fm.getDescent()) / 2);
                g2.dispose();
            }
        };
        btn.setPreferredSize(new Dimension(40, 40));
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addActionListener(e -> voltarLogin());
        return btn;
    }

    // CONTEÚDO

    // Área scrollável com o card de formulário
    private JScrollPane criarAreaConteudo() {
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(TelaLogin.FUNDO);
        wrapper.setBorder(BorderFactory.createEmptyBorder(24, 0, 24, 0));
        wrapper.add(criarCardFormulario());

        JScrollPane scroll = new JScrollPane(wrapper);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(TelaLogin.FUNDO);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        // Velocidade de scroll suave
        scroll.getVerticalScrollBar().setUnitIncrement(12);
        return scroll;
    }

    // Card branco com todos os campos do comerciante
    private JPanel criarCardFormulario() {
        TelaLogin.RoundedPanel card =
                new TelaLogin.RoundedPanel(16, TelaLogin.BRANCO);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(
                BorderFactory.createEmptyBorder(32, 36, 32, 36)
        );
        card.setPreferredSize(new Dimension(460, 650));

        // NOME DO PROPRIETÁRIO
        card.add(criarLabel("Nome do Proprietário"));
        card.add(Box.createVerticalStrut(5));
        campoProprietario = new JTextField();
        card.add(criarCampoTexto(campoProprietario, "Digite seu nome completo", "assets/icons/user.png"));
        card.add(Box.createVerticalStrut(13));

        // NOME DA LOJA
        card.add(criarLabel("Nome da Loja"));
        card.add(Box.createVerticalStrut(5));
        campoLoja = new JTextField();
        card.add(criarCampoTexto(campoLoja, "Digite o nome da sua loja", "assets/icons/store.png"));
        card.add(Box.createVerticalStrut(13));

        // CNPJ
        card.add(criarLabel("CNPJ"));
        card.add(Box.createVerticalStrut(5));
        campoCnpj = new JTextField();
        card.add(criarCampoTexto(campoCnpj, "Digite seu CNPJ", "assets/icons/document.png"));
        card.add(Box.createVerticalStrut(13));

        // ENDEREÇO
        card.add(criarLabel("Endereço"));
        card.add(Box.createVerticalStrut(5));
        campoEndereco = new JTextField();
        card.add(criarCampoTexto(campoEndereco, "Digite o endereço completo da loja", "assets/icons/location.png"));
        card.add(Box.createVerticalStrut(13));

        // TELEFONE
        card.add(criarLabel("Telefone"));
        card.add(Box.createVerticalStrut(5));
        campoTelefone = new JTextField();
        card.add(criarCampoTexto(campoTelefone, "Digite o telefone de contato", "assets/icons/phone.png"));
        card.add(Box.createVerticalStrut(13));

        // SEPARADOR
        card.add(criarSeparador());
        card.add(Box.createVerticalStrut(13));

        // SENHA
        card.add(criarLabel("Senha"));
        card.add(Box.createVerticalStrut(5));
        campoSenha = new JPasswordField();
        card.add(criarCampoSenha(campoSenha, "Digite sua senha"));
        card.add(Box.createVerticalStrut(13));

        // CONFIRMAR SENHA
        card.add(criarLabel("Confirmar Senha"));
        card.add(Box.createVerticalStrut(5));
        campoConfirmar = new JPasswordField();
        card.add(criarCampoSenha(campoConfirmar, "Confirme sua senha"));
        card.add(Box.createVerticalStrut(22));

        // BOTÃO
        card.add(
                criarBotaoPrimario("Registrar", e -> realizarCadastro())
        );
        card.add(Box.createVerticalStrut(16));

        // LINK LOGIN
        card.add(criarLinkVoltar());
        return card;
    }

    // Linha divisória entre (dados da loja e credenciais)
    private JPanel criarSeparador() {
        JPanel p = new JPanel(new BorderLayout(8, 0));
        p.setOpaque(false);
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));
        p.setAlignmentX(Component.LEFT_ALIGNMENT);

        JSeparator sep = new JSeparator();
        sep.setForeground(TelaLogin.BORDA);

        JLabel lbl = new JLabel(" Credenciais de acesso ");
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lbl.setForeground(TelaLogin.TEXTO_SUAVE);

        p.add(sep, BorderLayout.CENTER);
        // Adiciona label centralizado manualmente via wrapper
        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        wrapper.setOpaque(false);
        wrapper.add(lbl);

        // Substitui center por composição visual simples
        JPanel linha = new JPanel(new BorderLayout(4, 0));
        linha.setOpaque(false);
        linha.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));
        linha.setAlignmentX(Component.LEFT_ALIGNMENT);
        JSeparator e1 = new JSeparator(); e1.setForeground(TelaLogin.BORDA);
        JSeparator e2 = new JSeparator(); e2.setForeground(TelaLogin.BORDA);
        linha.add(e1, BorderLayout.WEST);
        linha.add(lbl, BorderLayout.CENTER);
        linha.add(e2, BorderLayout.EAST);
        return linha;
    }

    // Link "Já tem uma conta? Voltar para o login"
    private JPanel criarLinkVoltar() {
        JPanel linha = new JPanel(new FlowLayout(FlowLayout.CENTER, 4, 0));
        linha.setOpaque(false);
        linha.setMaximumSize(new Dimension(Integer.MAX_VALUE, 22));
        linha.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblTexto = new JLabel("Já tem uma conta?");
        lblTexto.setFont(TelaLogin.FONTE_LINK);
        lblTexto.setForeground(TelaLogin.TEXTO_SUAVE);

        JLabel lblLink = new JLabel("<html><u>Voltar para o login</u></html>");
        lblLink.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblLink.setForeground(TelaLogin.VERDE_SECUNDARIO);
        lblLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblLink.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) { voltarLogin(); }
        });

        linha.add(lblTexto);
        linha.add(lblLink);
        return linha;
    }

    // COMPONENTES

    private JLabel criarLabel(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(TelaLogin.FONTE_LABEL);
        lbl.setForeground(TelaLogin.TEXTO);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        return lbl;
    }

    private JPanel criarCampoTexto(
            JTextField campo,
            String placeholder,
            String caminhoIcone
    ) {

        campo.setFont(TelaLogin.FONTE_CAMPO);
        campo.setForeground(TelaLogin.TEXTO);
        campo.setCaretColor(TelaLogin.VERDE_PRINCIPAL);
        campo.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 4));
        campo.setOpaque(false);

        TelaLogin.adicionarPlaceholder(campo, placeholder);

        JPanel w = criarWrapper();

        ImageIcon icon = new ImageIcon(caminhoIcone);

        Image img = icon.getImage().getScaledInstance(
                20,
                20,
                Image.SCALE_SMOOTH
        );

        JLabel lb = new JLabel(new ImageIcon(img));

        w.add(lb, BorderLayout.WEST);
        w.add(campo, BorderLayout.CENTER);

        return w;
    }

    private JPanel criarCampoSenha(JPasswordField campo, String placeholder) {

        campo.setFont(TelaLogin.FONTE_CAMPO);
        campo.setForeground(TelaLogin.TEXTO);
        campo.setCaretColor(TelaLogin.VERDE_PRINCIPAL);
        campo.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 4));
        campo.setOpaque(false);
        campo.setEchoChar('●');

        JPanel w = criarWrapper();

        // Ícone cadeado
        ImageIcon lockIcon = new ImageIcon("assets/icons/lock.png");

        Image lockImg = lockIcon.getImage().getScaledInstance(
                20,
                20,
                Image.SCALE_SMOOTH
        );

        JLabel lb = new JLabel(new ImageIcon(lockImg));

        // Ícone olho
        ImageIcon eyeIcon = new ImageIcon("assets/icons/eye.png");
        Image eyeImg = eyeIcon.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH);
        JLabel olho = new JLabel(new ImageIcon(eyeImg));
        olho.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        olho.addMouseListener(new MouseAdapter() {
            boolean visivel = false;
            @Override
            public void mouseClicked(MouseEvent e) {
                visivel = !visivel;
                campo.setEchoChar(visivel ? (char) 0 : '●');
            }
        });

        w.add(lb, BorderLayout.WEST);
        w.add(campo, BorderLayout.CENTER);
        w.add(olho, BorderLayout.EAST);

        return w;
    }

    private JPanel criarWrapper() {
        JPanel p = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(TelaLogin.BRANCO);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.setColor(TelaLogin.BORDA);
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 10, 10);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        p.setOpaque(false);
        p.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        p.setAlignmentX(Component.LEFT_ALIGNMENT);
        return p;
    }

    private JButton criarBotaoPrimario(String texto, ActionListener acao) {
        JButton btn = new JButton(texto) {
            boolean hover = false;
            { addMouseListener(new MouseAdapter() {
                @Override public void mouseEntered(MouseEvent e) { hover = true; repaint(); }
                @Override public void mouseExited(MouseEvent e)  { hover = false; repaint(); }
            }); }
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(hover ? TelaLogin.VERDE_SECUNDARIO : TelaLogin.VERDE_PRINCIPAL);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.setColor(TelaLogin.BRANCO);
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(getText(),
                        (getWidth()  - fm.stringWidth(getText())) / 2,
                        (getHeight() + fm.getAscent() - fm.getDescent()) / 2);
                g2.dispose();
            }
        };
        btn.setFont(TelaLogin.FONTE_BOTAO);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.addActionListener(acao);
        return btn;
    }

    //  AÇÕES

    // Validação do formulário de comerciante
    private void realizarCadastro() {
        String prop     = campoProprietario.getText().trim();
        String loja     = campoLoja.getText().trim();
        String cnpj     = campoCnpj.getText().trim();
        String endereco = campoEndereco.getText().trim();
        String tel      = campoTelefone.getText().trim();
        String senha    = new String(campoSenha.getPassword());
        String confirma = new String(campoConfirmar.getPassword());

        // Verifica se ainda estão com placeholder
        String[] placeholders = {
                "Digite seu nome completo", "Digite o nome da sua loja",
                "Digite seu CNPJ", "Digite o endereço completo da loja",
                "Digite o telefone de contato"
        };
        String[] valores = { prop, loja, cnpj, endereco, tel };
        String[] nomes   = { "Nome do Proprietário", "Nome da Loja", "CNPJ", "Endereço", "Telefone" };

        for (int i = 0; i < valores.length; i++) {
            if (valores[i].isEmpty() || valores[i].equals(placeholders[i])) {
                mostrarErro("O campo \"" + nomes[i] + "\" é obrigatório."); return;
            }
        }
        if (senha.length() < 6) {
            mostrarErro("A senha deve ter pelo menos 6 caracteres."); return;
        }
        if (!senha.equals(confirma)) {
            mostrarErro("As senhas não coincidem."); return;
        }

        JOptionPane.showMessageDialog(this,
                "Comerciante cadastrado com sucesso!\n(Integração com banco de dados pendente)",
                "Cadastro Realizado", JOptionPane.INFORMATION_MESSAGE);
        voltarLogin();
    }

    private void mostrarErro(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Atenção", JOptionPane.WARNING_MESSAGE);
    }

    // Fecha esta tela e voltar para  TelaLogin
    private void voltarLogin() {
        dispose();
        if (telaPai != null) telaPai.setVisible(true);
    }
}