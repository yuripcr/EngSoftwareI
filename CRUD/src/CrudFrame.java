import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CrudFrame extends JFrame {

    private JTextField idField, nomeField, senhaField, tipoField;
    private JButton btnCadastrar, btnAlterar, btnExcluir, btnConsultar, btnSair, btnLimpar;
    private JLabel usuarioLogadoLabel;

    public CrudFrame(Gerente gerente) {
        setTitle("Gerenciamento de usuários");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font inputFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 15);

        // Painel topo para usuário logado e botão sair
        JPanel topoPanel = new JPanel(new BorderLayout());
        topoPanel.setBackground(new Color(34, 34, 34));
        topoPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        usuarioLogadoLabel = new JLabel("Usuário: " + gerente.getNome());
        usuarioLogadoLabel.setForeground(Color.WHITE);
        usuarioLogadoLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        topoPanel.add(usuarioLogadoLabel, BorderLayout.WEST);

        btnSair = new JButton("Sair");
        btnSair.setFont(buttonFont);
        btnSair.setBackground(new Color(244, 67, 54)); ;
        btnSair.setForeground(Color.WHITE);
        btnSair.setFocusPainted(false);
        btnSair.setCursor(new Cursor(Cursor.HAND_CURSOR));
        topoPanel.add(btnSair, BorderLayout.EAST);

        add(topoPanel, BorderLayout.NORTH);

        // Formulário
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(18, 18, 18)); // dark background
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblId = new JLabel("ID:");
        lblId.setFont(labelFont);
        lblId.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(lblId, gbc);

        idField = new JTextField(15);
        idField.setFont(inputFont);
        gbc.gridx = 1;
        formPanel.add(idField, gbc);

        JLabel lblNome = new JLabel("Nome:");
        lblNome.setFont(labelFont);
        lblNome.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(lblNome, gbc);

        nomeField = new JTextField(15);
        nomeField.setFont(inputFont);
        gbc.gridx = 1;
        formPanel.add(nomeField, gbc);

        JLabel lblSenha = new JLabel("Senha:");
        lblSenha.setFont(labelFont);
        lblSenha.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(lblSenha, gbc);

        senhaField = new JTextField(15);
        senhaField.setFont(inputFont);
        gbc.gridx = 1;
        formPanel.add(senhaField, gbc);

        JLabel lblTipo = new JLabel("Tipo Usuário:");
        lblTipo.setFont(labelFont);
        lblTipo.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(lblTipo, gbc);

        tipoField = new JTextField(15);
        tipoField.setFont(inputFont);
        gbc.gridx = 1;
        formPanel.add(tipoField, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Painel botões
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnPanel.setBackground(new Color(28, 28, 28)); // dark panel

        btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.setFont(buttonFont);
        btnCadastrar.setBackground(new Color(76, 175, 80));
        btnCadastrar.setForeground(Color.WHITE);
        btnCadastrar.setFocusPainted(false);
        btnCadastrar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnAlterar = new JButton("Alterar");
        btnAlterar.setFont(buttonFont);
        btnAlterar.setBackground(new Color(33, 150, 243));
        btnAlterar.setForeground(Color.WHITE);
        btnAlterar.setFocusPainted(false);
        btnAlterar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnExcluir = new JButton("Excluir");
        btnExcluir.setFont(buttonFont);
        btnExcluir.setBackground(new Color(244, 67, 54));
        btnExcluir.setForeground(Color.WHITE);
        btnExcluir.setFocusPainted(false);
        btnExcluir.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnConsultar = new JButton("Consultar");
        btnConsultar.setFont(buttonFont);
        btnConsultar.setBackground(new Color(255, 193, 7));
        btnConsultar.setForeground(Color.BLACK);
        btnConsultar.setFocusPainted(false);
        btnConsultar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnLimpar = new JButton("Limpar");
        btnLimpar.setFont(buttonFont);
        btnLimpar.setBackground(new Color(96, 96, 96));
        btnLimpar.setForeground(Color.WHITE);
        btnLimpar.setFocusPainted(false);
        btnLimpar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnPanel.add(btnCadastrar);
        btnPanel.add(btnAlterar);
        btnPanel.add(btnExcluir);
        btnPanel.add(btnConsultar);
        btnPanel.add(btnLimpar);

        add(btnPanel, BorderLayout.SOUTH);

        // Ações dos botões
        btnCadastrar.addActionListener(_ -> {
            try {
                int id = Integer.parseInt(idField.getText());
                String nome = nomeField.getText();
                String senha = senhaField.getText();
                String tipo = tipoField.getText();

                Usuario u = new Usuario(id, nome, senha, tipo);
                boolean sucesso = gerente.cadastrarUsuario(u);

                if (sucesso) {
                    JOptionPane.showMessageDialog(this, "Usuário cadastrado com sucesso.");
                    limparCampos();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro: ID já existe ou falha no cadastro.");
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID inválido!");
            }
        });

        btnAlterar.addActionListener(_ -> {
            try {
                int id = Integer.parseInt(idField.getText());
                String nome = nomeField.getText();
                String senha = senhaField.getText();
                String tipo = tipoField.getText();

                Usuario u = new Usuario(id, nome, senha, tipo);
                gerente.alterarUsuario(u);
                JOptionPane.showMessageDialog(this, "Usuário alterado com sucesso.");
                limparCampos();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID inválido!");
            }
        });

        btnExcluir.addActionListener(_ -> {
            try {
                int id = Integer.parseInt(idField.getText());
                gerente.excluirUsuario(id);
                JOptionPane.showMessageDialog(this, "Usuário excluído com sucesso.");
                limparCampos();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID inválido!");
            }
        });

        btnConsultar.addActionListener(_ -> {
            String idText = idField.getText().trim();

            if (idText.isEmpty()) {
                mostrarTodosUsuarios(); // exibe todos
            } else {
                try {
                    int id = Integer.parseInt(idText);
                    String sql = "SELECT * FROM usuarios WHERE id = ?";

                    try (Connection conn = ConexaoDB.conectar();
                         PreparedStatement stmt = conn.prepareStatement(sql)) {

                        stmt.setInt(1, id);
                        ResultSet rs = stmt.executeQuery();

                        if (rs.next()) {
                            nomeField.setText(rs.getString("nome"));
                            senhaField.setText(rs.getString("senha"));
                            tipoField.setText(rs.getString("tipoUsuario"));
                            JOptionPane.showMessageDialog(this, "Usuário encontrado.");
                        } else {
                            JOptionPane.showMessageDialog(this, "Usuário não encontrado.");
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "ID inválido!");
                }
            }
        });

        btnLimpar.addActionListener(_ -> limparCampos());

        btnSair.addActionListener(_ -> {
            this.dispose();
            new LoginFrame().setVisible(true);
        });
    }


    private void limparCampos() {
        idField.setText("");
        nomeField.setText("");
        senhaField.setText("");
        tipoField.setText("");
    }

    private void mostrarTodosUsuarios() {
        String sql = "SELECT * FROM usuarios";

        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            String[] colunas = {"ID", "Nome", "Senha", "Tipo Usuário"};
            java.util.List<Object[]> dados = new java.util.ArrayList<>();

            while (rs.next()) {
                Object[] linha = {
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("senha"),
                    rs.getString("tipoUsuario")
                };
                dados.add(linha);
            }

            Object[][] dataArray = new Object[dados.size()][colunas.length];
            for (int i = 0; i < dados.size(); i++) {
                dataArray[i] = dados.get(i);
            }

            JTable tabela = new JTable(dataArray, colunas);
            JScrollPane scrollPane = new JScrollPane(tabela);

            JFrame frameTabela = new JFrame("Todos os Usuários");
            frameTabela.setSize(800, 600);
            frameTabela.setLocationRelativeTo(this);
            frameTabela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frameTabela.add(scrollPane);
            frameTabela.setVisible(true);

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao carregar usuários.");
        }
    }
}
