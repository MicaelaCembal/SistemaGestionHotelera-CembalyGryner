public class Usuario {
    private String username;
    private String password;
    private RolUsuario rol;

    public Usuario(String username, String password, RolUsuario rol) {
        this.username = username;
        this.password = password;
        this.rol = rol;
    }

    public boolean login(String passwordIngresada) {
        if (this.password.equals(passwordIngresada)) {
            // Si la contraseña es correcta, se notifica al controlador
            ControladorSistema.getInstancia().setUsuarioActual(this);
            System.out.println("Login exitoso. Bienvenido, " + this.username);
            return true;
        } else {
            System.out.println("Error: Contraseña incorrecta.");
            return false;
        }
    }

    public boolean tienePermiso(String accion) {
        // El ADMIN tiene todos los permisos, el RECEPCIONISTA no.
        if (this.rol == RolUsuario.ADMIN) return true;
        if (this.rol == RolUsuario.RECEPCIONISTA && accion.equals("CREAR_RESERVA")) return true; // Un solo permiso por el momento
        return false;
    }

    public String getUsername() { return username; }
    public RolUsuario getRol() { return rol; }
}