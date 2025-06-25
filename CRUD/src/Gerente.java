import java.sql.*;

public class Gerente extends Usuario {

    public Gerente(int id, String nome, String senha) {
        super(id, nome, senha, "gerente");
    }

    public boolean cadastrarUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuarios (id, nome, senha, tipoUsuario) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexaoDB.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, usuario.getId());
            stmt.setString(2, usuario.getNome());
            stmt.setString(3, usuario.getSenha());
            stmt.setString(4, usuario.getTipoUsuario());

            stmt.executeUpdate();
            return true;

        } catch (SQLIntegrityConstraintViolationException e) {
            return false;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void excluirUsuario(int id) {
        String sql = "DELETE FROM usuarios WHERE id = ?";

        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Usuário excluído com sucesso.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void alterarUsuario(Usuario usuario) {
        String sql = "UPDATE usuarios SET nome = ?, senha = ?, tipoUsuario = ? WHERE id = ?";

        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getSenha());
            stmt.setString(3, usuario.getTipoUsuario());
            stmt.setInt(4, usuario.getId());

            stmt.executeUpdate();
            System.out.println("Usuário alterado com sucesso.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void consultarUsuario(int id) {
        String sql = "SELECT * FROM usuarios WHERE id = ?";

        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Nome: " + rs.getString("nome"));
                System.out.println("Senha: " + rs.getString("senha"));
                System.out.println("Tipo: " + rs.getString("tipoUsuario"));
            } else {
                System.out.println("Usuário não encontrado.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
