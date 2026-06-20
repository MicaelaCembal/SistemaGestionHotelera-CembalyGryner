package modelo;

public class Usuario {
    private int idUsuario;
    private String username;
    private String password;
    private RolUsuario rol;

    public Usuario(int idUsuario, String username, String password, RolUsuario rol) {
        this.idUsuario = idUsuario;
        this.username = username;
        this.password = password;
        this.rol = rol;
    }

    public boolean validarPassword(String passwordIngresada) {
        return this.password.equals(passwordIngresada);
    }

    public boolean tienePermiso(String accion) {
        if (this.rol == RolUsuario.ADMIN) return true;
        if (this.rol == RolUsuario.RECEPCIONISTA && accion.equals("CREAR_RESERVA")) return true;
        return false;
    }
    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public RolUsuario getRol() { return rol; }
    public void setRol(RolUsuario rol) { this.rol = rol; }
}