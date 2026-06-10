public class ControladorSistema {
    private Usuario usuarioActual;
    private UsuarioDAO usuarioDAO;

    public ControladorSistema() {
        this.usuarioDAO = new UsuarioDAO();
    }

    public boolean login(String username, String password) {
        Usuario user = usuarioDAO.validarUsuario(username, password);
        if (user != null) {
            this.usuarioActual = user;
            return true;
        }
        return false;
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public boolean tienePermiso(String accion) {
        // Lógica de permisos según el rol del usuarioActual
        return usuarioActual != null && usuarioActual.getRol() == RolUsuario.ADMIN;
    }
}