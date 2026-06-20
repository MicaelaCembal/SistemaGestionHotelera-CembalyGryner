package controlador;

import baseDatos.*;
import modelo.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ControladorSistema {
    private static ControladorSistema instancia;

    private Usuario usuarioActual;

    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private HuespedDAO huespedDAO = new HuespedDAO();
    private HabitacionDAO habitacionDAO = new HabitacionDAO();
    private ReservaDAO reservaDAO = new ReservaDAO();
    private EstadiaDAO estadiaDAO = new EstadiaDAO();

    private ControladorSistema() {}

    public static synchronized ControladorSistema getInstancia() {
        if (instancia == null) {
            instancia = new ControladorSistema();
        }
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

    public void logout() {
        this.usuarioActual = null;
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public String registrarHuesped(String nombre, String apellido, int dni, int tel, String email, LocalDate fechaNac) {
        if (usuarioActual == null) return "Error: Debe iniciar sesión.";

        // El permiso se verifica en el modelo Usuario
        if (!usuarioActual.tienePermiso("REGISTRAR_HUESPED")) {
            return "Error: No tiene permisos suficientes.";
        }

        if (huespedDAO.existeDni(dni)) {
            return "Error: El DNI " + dni + " ya se encuentra registrado.";
        }

        Huesped h = new Huesped();
        h.setNombre(nombre);
        h.setApellido(apellido);
        h.setDni(dni);
        h.setTelefono(tel);
        h.setEmail(email);
        h.setCategoria(CategoriaHuesped.REGULAR);
        h.setFechaNacimiento(fechaNac);

        if (huespedDAO.insertar(h)) {
            return "ÉXITO: Huésped guardado correctamente.";
        } else {
            return "Error técnico: No se pudo guardar en la base de datos.";
        }
    }

    public List<Habitacion> obtenerHabitacionesDisponibles() {
        return habitacionDAO.listarDisponibles();
    }

    public String procesarCheckIn(int dniHuesped) {
        if (usuarioActual == null) return "Error: Debe iniciar sesión.";

        if (!usuarioActual.tienePermiso("CHECK_IN")) {
            return "Error: Su rol no permite realizar check-ins.";
        }

        Reserva reserva = reservaDAO.buscarReservaPendientePorDni(dniHuesped);

        if (reserva == null) {
            return "Error: No existe una reserva PENDIENTE para el DNI: " + dniHuesped;
        }

        reserva.getHabitacion().ocupar();

        habitacionDAO.actualizarEstado(reserva.getHabitacion().getIdHabitacion(), "OCUPADO");

        Estadia nuevaEstadia = new Estadia();
        nuevaEstadia.setIdReserva(reserva.getIdReserva());
        nuevaEstadia.setFechaIngresoReal(LocalDateTime.now());

        if (estadiaDAO.insertar(nuevaEstadia)) {
            return "Check-in exitoso. Habitación " + reserva.getHabitacion().getNumero() + " ocupada.";
        } else {
            return "Error en registro de estadía.";
        }
    }

    public boolean tienePermiso(String accion) {
        return usuarioActual != null && usuarioActual.tienePermiso(accion);
    }
}