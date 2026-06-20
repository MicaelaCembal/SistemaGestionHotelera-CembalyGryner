package controlador;

import baseDatos.*;
import modelo.*;
import java.time.LocalDate;

public class ControladorSistema {
    private static ControladorSistema instancia;
    private Usuario usuarioActual;
    private HuespedDAO huespedDAO = new HuespedDAO();
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    private ControladorSistema() {}

    public static synchronized ControladorSistema getInstancia() {
        if (instancia == null) instancia = new ControladorSistema();
        return instancia;
    }

    public boolean login(String u, String p) {
        Usuario user = usuarioDAO.autenticar(u, p);
        if (user != null) {
            this.usuarioActual = user;
            return true;
        }
        return false;
    }

    public Usuario getUsuarioActual() { return usuarioActual; }

    public String registrarHuesped(String nombre, String apellido, int dni, int tel, String email, LocalDate fechaNac) {
        if (usuarioActual == null) return "Error: Sin sesión.";
        if (huespedDAO.existeDni(dni)) return "Error: DNI ya registrado.";

        Huesped h = new Huesped();
        h.setNombre(nombre);
        h.setApellido(apellido);
        h.setDni(dni);
        h.setTelefono(tel);
        h.setEmail(email);
        h.setCategoria(CategoriaHuesped.REGULAR);
        h.setFechaNacimiento(fechaNac);

        if (huespedDAO.insertar(h)) {
            return "Éxito: Huésped guardado.";
        } else {
            return "Error: No se pudo guardar en la base de datos.";
        }
    }
}