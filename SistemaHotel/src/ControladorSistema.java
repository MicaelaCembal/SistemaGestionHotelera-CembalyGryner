public class ControladorSistema {
    private static ControladorSistema instancia;
    private Usuario usuarioActual;

    private ControladorSistema() {}

    public static ControladorSistema getInstancia() {
        if (instancia == null) {
            instancia = new ControladorSistema();
        }
        return instancia;
    }

    public void setUsuarioActual(Usuario usuario) {
        this.usuarioActual = usuario;
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public boolean tienePermiso(String accion) {
        if (usuarioActual != null) {
            return usuarioActual.tienePermiso(accion);
        }
        return false;
    }
}