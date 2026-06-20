package view;
import util.EstiloReAlimenta;
import util.EstiloReAlimenta.RoundedPanel;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import dao.ConsumidorDAO;
import model.Consumidor;
public class TelaCadastroConsumidor extends JFrame {
    private final JFrame   telaPai;
    private JTextField     campoNome;
    private JTextField     campoCpf;
    private JTextField     campoTelefone;
    private JTextField     campoEmail;
    private JPasswordField campoSenha;
    private JPasswordField campoConfirmar;

    public TelaCadastroConsumidor(JFrame telaPai) {
        this.telaPai = telaPai;
        configurarJanela();
        construirInterface();
        setVisible(true);
    }

    // CONFIGURAÇÃO DA JANELA
    private void configurarJanela() {
        setTitle("ReAlimenta | Cadastro de Consumidor");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override public void windowClosing(WindowEvent e) { voltarLogin(); }
        });
        setSize(580, 720);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(EstiloReAlimenta.FUNDO);
        setLayout(new BorderLayout());
    }

    private void construirInterface() {
        // Cabeçalho com ícone FontAwesome — sem PNG, sem emoji
        add(EstiloReAlimenta.criarCabecalho(
                "Cadastro de Consumidor",
                "Preencha os dados abaixo para criar sua conta",
                FontAwesomeSolid.USER_PLUS,
                this::voltarLogin
        ), BorderLayout.NORTH);

        add(EstiloReAlimenta.criarAreaScroll(criarCardFormulario()), BorderLayout.CENTER);
    }

    // CARD DE FORMULÁRIO
    private JPanel criarCardFormulario() {
        RoundedPanel card = new RoundedPanel(16, EstiloReAlimenta.BRANCO);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(32, 36, 32, 36));
        card.setPreferredSize(new Dimension(460, 640));

        // NOME COMPLETO
        card.add(EstiloReAlimenta.criarLabel("Nome Completo"));
        card.add(Box.createVerticalStrut(5));
        campoNome = new JTextField();
        card.add(EstiloReAlimenta.criarCampoTexto(
                campoNome, "Digite seu nome completo", FontAwesomeSolid.USER));
        card.add(Box.createVerticalStrut(13));

        // CPF
        card.add(EstiloReAlimenta.criarLabel("CPF"));
        card.add(Box.createVerticalStrut(5));
        campoCpf = new JTextField();
        card.add(EstiloReAlimenta.criarCampoTexto(
                campoCpf, "Digite seu CPF", FontAwesomeSolid.ID_CARD));
        card.add(Box.createVerticalStrut(13));

        // TELEFONE
        card.add(EstiloReAlimenta.criarLabel("Telefone"));
        card.add(Box.createVerticalStrut(5));
        campoTelefone = new JTextField();
        card.add(EstiloReAlimenta.criarCampoTexto(
                campoTelefone, "Digite seu telefone", FontAwesomeSolid.PHONE));
        card.add(Box.createVerticalStrut(13));

        // EMAIL
        card.add(EstiloReAlimenta.criarLabel("E-mail"));
        card.add(Box.createVerticalStrut(5));
        campoEmail = new JTextField();
        card.add(EstiloReAlimenta.criarCampoTexto(
                campoEmail, "Digite seu e-mail", FontAwesomeSolid.ENVELOPE));
        card.add(Box.createVerticalStrut(13));

        // SENHA — LOCK + EYE encapsulados em criarCampoSenha
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
        card.add(Box.createVerticalStrut(10));

        // Dica
        JLabel lblDica = new JLabel("Sua senha deve ter pelo menos 6 caracteres.");
        lblDica.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblDica.setForeground(EstiloReAlimenta.TEXTO_SUAVE);
        lblDica.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(lblDica);
        card.add(Box.createVerticalStrut(20));

        // BOTÃO REGISTRAR
        card.add(EstiloReAlimenta.criarBotaoPrimario("Registrar", e -> realizarCadastro()));
        card.add(Box.createVerticalStrut(16));

        // Link voltar
        card.add(criarLinkVoltar());
        return card;
    }

    // LINK VOLTAR
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
        lblLink.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) { voltarLogin(); }
        });

        linha.add(lblTexto);
        linha.add(lblLink);
        return linha;
    }

    //  AÇÕES
    private void realizarCadastro() {
        String nome     = campoNome.getText().trim();
        String cpf      = campoCpf.getText().trim();
        String telefone = campoTelefone.getText().trim();
        String email    = campoEmail.getText().trim();
        String senha    = new String(campoSenha.getPassword());
        String confirma = new String(campoConfirmar.getPassword());

        if (nome.isEmpty() || nome.equals("Digite seu nome completo")) {
            mostrarErro("O campo Nome Completo é obrigatório."); return;
        }
        if (cpf.isEmpty() || cpf.equals("Digite seu CPF")) {
            mostrarErro("O campo CPF é obrigatório."); return;
        }
        if (telefone.isEmpty() || telefone.equals("Digite seu telefone")) {
            mostrarErro("O campo Telefone é obrigatório."); return;
        }
        if (email.isEmpty() || email.equals("Digite seu e-mail")) {
            mostrarErro("O campo E-mail é obrigatório."); return;
        }
        if (senha.length() < 6) {
            mostrarErro("A senha deve ter pelo menos 6 caracteres."); return;
        }
        if (!senha.equals(confirma)) {
            mostrarErro("As senhas não coincidem."); return;
        }

        Consumidor consumidor = new Consumidor(
                0,
                0,
                nome,
                telefone,
                email,
                senha,
                cpf
        );

        ConsumidorDAO consumidorDAO = new ConsumidorDAO();
        consumidorDAO.inserirConsumidor(consumidor);

        JOptionPane.showMessageDialog(this,
                "Consumidor cadastrado com sucesso!",
                "Cadastro Realizado",
                JOptionPane.INFORMATION_MESSAGE);

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