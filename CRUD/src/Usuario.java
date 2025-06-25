public class Usuario {
    private int id;
    private String nome, senha, tipoUsuario;

    public Usuario(int id, String nome, String senha, String tipoUsuario) {
        this.id = id;
        this.nome = nome;
        this.senha = senha;
        this.tipoUsuario = tipoUsuario;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }


    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

}
