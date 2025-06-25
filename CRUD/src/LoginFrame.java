import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.*;

public class LoginFrame extends JFrame {

    private JTextField nomeField;
    private JPasswordField senhaField;
    private JButton btnLogin;
    private JLabel statusLabel;

    public LoginFrame() {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        setTitle("Gerenciamento de usuários");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null); // opcional para centralizar se não estiver maximizado

        // Adiciona os componentes etc...

        setVisible(true);
        setResizable(false);

        // Painel principal com BorderLayout e fundo escuro
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(18, 18, 18));

        // Painel central para centralizar o card
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false); // transparente para ver fundo do mainPanel

        // Card com tamanho fixo e bordas arredondadas
        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(400, 450));
        card.setBackground(new Color(30, 30, 30));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(80, 80, 80), 1),
                new EmptyBorder(30, 30, 30, 30)
        ));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        // Título
        JLabel titleLabel = new JLabel("Bem-vindo");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setForeground(new Color(100, 180, 255));
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        card.add(titleLabel);

        JLabel subTitle = new JLabel("Faça login para continuar");
        subTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        subTitle.setForeground(Color.LIGHT_GRAY);
        subTitle.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subTitle.setBorder(new EmptyBorder(10, 0, 25, 0));
        card.add(subTitle);

        // Campo Nome
        nomeField = new JTextField();
        nomeField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        nomeField.setForeground(Color.WHITE);
        nomeField.setBackground(new Color(40, 40, 40));
        nomeField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        nomeField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        addPlaceholder(nomeField, "Nome de usuário");

        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        JLabel usuarioLabel = new JLabel("Usuário");
        usuarioLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(usuarioLabel);

        card.add(Box.createRigidArea(new Dimension(0, 5)));

        nomeField.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(nomeField);

        card.add(Box.createRigidArea(new Dimension(0, 20)));
        // Campo Senha
        senhaField = new JPasswordField();
        senhaField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        senhaField.setForeground(Color.WHITE);
        senhaField.setBackground(new Color(40, 40, 40));
        senhaField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        senhaField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        addPlaceholder(senhaField, "Senha");

        JLabel senhaLabel = new JLabel("Senha");
        senhaLabel.setForeground(Color.WHITE);
        senhaLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(senhaLabel);

        card.add(Box.createRigidArea(new Dimension(0, 5)));

        senhaField.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(senhaField);

        card.add(Box.createRigidArea(new Dimension(0, 30)));

        // Botão Entrar estilizado
        btnLogin = new JButton("Entrar");
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setBackground(new Color(76, 175, 80));
        btnLogin.setFocusPainted(false);
        btnLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLogin.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        btnLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLogin.setBackground(new Color(56, 142, 60));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLogin.setBackground(new Color(76, 175, 80));
            }
        });
        card.add(btnLogin);

        card.add(Box.createRigidArea(new Dimension(0, 25)));

        // Label status
        statusLabel = new JLabel(" ");
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        statusLabel.setForeground(new Color(244, 67, 54));
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(statusLabel);

        centerPanel.add(card);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        add(mainPanel);

        // Ação do botão login
        btnLogin.addActionListener(_ -> realizarLogin());

        // Enter ativa o botão
        getRootPane().setDefaultButton(btnLogin);
    }

    private void addPlaceholder(JTextComponent field, String placeholder) {
        field.setText(placeholder);
        field.setForeground(Color.GRAY);
        field.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.WHITE);
                    if (field instanceof JPasswordField) {
                        ((JPasswordField) field).setEchoChar('\u2022');
                    }
                }
            }
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setForeground(Color.GRAY);
                    field.setText(placeholder);
                    if (field instanceof JPasswordField) {
                        ((JPasswordField) field).setEchoChar((char) 0);
                    }
                }
            }
        });

        if (field instanceof JPasswordField) {
            ((JPasswordField) field).setEchoChar((char) 0);
        }
    }

    private void realizarLogin() {
        String nome = nomeField.getText().trim();
        String senha = new String(senhaField.getPassword());

        if (nome.equals("Nome de usuário")) nome = "";
        if (senha.equals("Senha")) senha = "";

        if (nome.isEmpty() || senha.isEmpty()) {
            statusLabel.setText("Preencha todos os campos");
            return;
        }

        if (loginGerente(nome, senha)) {
            Gerente gerente = carregarGerente(nome, senha);
            new CrudFrame(gerente).setVisible(true);
            this.dispose();
        } else {
            statusLabel.setText("Usuário ou senha incorretos");
        }
    }

    private boolean loginGerente(String nome, String senha) {
        String sql = "SELECT * FROM usuarios WHERE nome = ? AND senha = ? AND tipoUsuario = 'gerente'";
        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);
            stmt.setString(2, senha);
            ResultSet rs = stmt.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Gerente carregarGerente(String nome, String senha) {
        String sql = "SELECT * FROM usuarios WHERE nome = ? AND senha = ? AND tipoUsuario = 'gerente'";
        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);
            stmt.setString(2, senha);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                return new Gerente(id, nome, senha);
            } else {
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
