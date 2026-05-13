package view;
import util.EstiloReAlimenta;
import util.EstiloReAlimenta.RoundedPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TelaCadastroConsumidor extends JFrame {
    private final JFrame    telaPai;
    private JTextField      campoNome;
    private JTextField      campoCpf;
    private JPasswordField  campoSenha;
    private JPasswordField  campoConfirmar;

    public TelaCadastroConsumidor(JFrame telaPai) {
        this.telaPai = telaPai;
        configurarJanela();
        construirInterface();
        setVisible(true);
    }

    // Configuração básica da janela
    private void configurarJanela() {
        setTitle("ReAlimenta | Cadastro de Consumidor");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override public void windowClosing(WindowEvent e) { voltarLogin(); }
        });
        setSize(560, 640);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(EstiloReAlimenta.FUNDO);
        setLayout(new BorderLayout());
    }

    private void construirInterface() {
        add(EstiloReAlimenta.criarCabecalho(
                "Cadastro de Consumidor",
                "Preencha os dados abaixo para criar sua conta",
                "👤",
                this::voltarLogin
        ), BorderLayout.NORTH);
        add(EstiloReAlimenta.criarAreaScroll(criarCardFormulario()), BorderLayout.CENTER);
    }

    // ── CARD DE FORMULÁRIO ───────────────────────────────────────
    private JPanel criarCardFormulario() {
        RoundedPanel card = new RoundedPanel(16, EstiloReAlimenta.BRANCO);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(32, 36, 32, 36));
        card.setPreferredSize(new Dimension(460, 520));

        // NOME COMPLETO
        card.add(EstiloReAlimenta.criarLabel("Nome Completo"));
        card.add(Box.createVerticalStrut(5));
        campoNome = new JTextField();
        card.add(EstiloReAlimenta.criarCampoTexto(
                campoNome, "Digite seu nome completo", "assets/icons/user.png"));
        card.add(Box.createVerticalStrut(13));

        // CPF
        card.add(EstiloReAlimenta.criarLabel("CPF"));
        card.add(Box.createVerticalStrut(5));
        campoCpf = new JTextField();
        card.add(EstiloReAlimenta.criarCampoTexto(
                campoCpf, "Digite seu CPF", "assets/icons/document.png"));
        card.add(Box.createVerticalStrut(13));

        // SENHA
        card.add(EstiloReAlimenta.criarLabel("Senha"));
        card.add(Box.createVerticalStrut(5));
        campoSenha = new JPasswordField();
        card.add(EstiloReAlimenta.criarCampoSenha(campoSenha, "Digite sua senha"));
        card.add(Box.createVerticalStrut(13));

        // CONFIRMAR SENHA
        card.add(EstiloReAlimenta.criarLabel("Confirmar Senha"));
        card.add(Box.createVerticalStrut(5));
        campoConfirmar = new JPasswordField();
        card.add(EstiloReAlimenta.criarCampoSenha(campoConfirmar, "Confirme sua senha"));
        card.add(Box.createVerticalStrut(16));

        // Dica de senha
        JLabel lblDica = new JLabel("Sua senha deve ter pelo menos 6 caracteres.");
        lblDica.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblDica.setForeground(EstiloReAlimenta.TEXTO_SUAVE);
        lblDica.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(lblDica);
        card.add(Box.createVerticalStrut(20));

        // BOTÃO REGISTRAR
        card.add(EstiloReAlimenta.criarBotaoPrimario("Registrar", e -> realizarCadastro()));
        card.add(Box.createVerticalStrut(16));

        // Link de volta ao login
        card.add(criarLinkVoltar());
        return card;
    }

    // Link "Já tem uma conta? Voltar para o login"
    private JPanel criarLinkVoltar() {
        JPanel linha = new JPanel(new FlowLayout(FlowLayout.CENTER, 4, 0));
        linha.setOpaque(false);
        linha.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblTexto = new JLabel("Já tem uma conta?");
        lblTexto.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblTexto.setForeground(EstiloReAlimenta.TEXTO_SUAVE);

        JLabel lblLink = new JLabel("<html><u>Voltar para o login</u></html>");
        lblLink.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblLink.setForeground(EstiloReAlimenta.VERDE_SECUNDARIO);
        lblLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblLink.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseClicked(java.awt.event.MouseEvent e) { voltarLogin(); }
        });

        linha.add(lblTexto);
        linha.add(lblLink);
        return linha;
    }

    //  AÇÕES
    private void realizarCadastro() {
        String nome     = campoNome.getText().trim();
        String cpf      = campoCpf.getText().trim();
        String senha    = new String(campoSenha.getPassword());
        String confirma = new String(campoConfirmar.getPassword());

        if (nome.isEmpty() || nome.equals("Digite seu nome completo")) {
            mostrarErro("O campo Nome Completo é obrigatório."); return;
        }
        if (cpf.isEmpty() || cpf.equals("Digite seu CPF")) {
            mostrarErro("O campo CPF é obrigatório."); return;
        }
        if (senha.length() < 6) {
            mostrarErro("A senha deve ter pelo menos 6 caracteres."); return;
        }
        if (!senha.equals(confirma)) {
            mostrarErro("As senhas não coincidem."); return;
        }
        JOptionPane.showMessageDialog(this,
                "Consumidor cadastrado com sucesso!\n(Integração com banco de dados pendente)",
                "Cadastro Realizado", JOptionPane.INFORMATION_MESSAGE);
        voltarLogin();
    }

    private void mostrarErro(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Atenção", JOptionPane.WARNING_MESSAGE);
    }

    private void voltarLogin() {
        dispose();
        if (telaPai != null) telaPai.setVisible(true);
    }
}