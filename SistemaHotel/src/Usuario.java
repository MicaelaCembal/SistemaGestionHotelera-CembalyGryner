public class Usuario {
    private String username;
    private String password;
    private RolUsuario rol;

    public Usuario(String username, String password, RolUsuario rol) {
        this.username = username;
        this.password = password;
        this.rol = rol;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public RolUsuario getRol() { return rol; }
}