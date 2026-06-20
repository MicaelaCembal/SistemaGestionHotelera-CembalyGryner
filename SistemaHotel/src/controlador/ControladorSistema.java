package controlador;

import baseDatos.*;
import modelo.*;
import java.time.LocalDateTime;
import java.util.List;

public class ControladorSistema {
    private static ControladorSistema instancia;

    private Usuario usuarioActual;

    // Fachada hacia los DAOs
    private UsuarioDAO usuarioDAO;
    private HuespedDAO huespedDAO;
    private HabitacionDAO habitacionDAO;
    private ReservaDAO reservaDAO;

    private ControladorSistema() {
        this.usuarioDAO = new UsuarioDAO();
        this.huespedDAO = new HuespedDAO();
        this.habitacionDAO = new HabitacionDAO();
        this.reservaDAO = new ReservaDAO();
    }

    public static synchronized ControladorSistema getInstancia() {
        if (instancia == null) {
            instancia = new ControladorSistema();
        }
        return instancia;
    }

    public boolean login(String username, String password) {
        Usuario user = usuarioDAO.autenticar(username, password);

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

    public void registrarHuesped(String nombre, String apellido, int dni, int tel, String email) {
        Huesped h = new Huesped();
        h.setNombre(nombre);
        h.setApellido(apellido);
        h.setDni(dni);
        h.setTelefono(tel);
        h.setEmail(email);
        h.setCategoria(CategoriaHuesped.REGULAR);
        huespedDAO.insertar(h);
    }

    public Huesped buscarHuespedPorDni(int dni) {
        return huespedDAO.buscarPorDni(dni);
    }

    public boolean crearReserva(Huesped h, Habitacion hab, LocalDateTime entrada, LocalDateTime salida) {
        // Verificación de reglas de negocio (Disponibilidad)
        if (!hab.estaDisponible(entrada, salida)) {
            return false;
        }

        Reserva nueva = new Reserva();
        nueva.setHuesped(h);
        nueva.setHabitacion(hab);
        nueva.setFechaCheckin(entrada);
        nueva.setFechaCheckout(salida);
        nueva.setEstado(EstadoReserva.PENDIENTE);
        nueva.calcularCostoTotal();

        int id = reservaDAO.crear(nueva);
        return id != -1;
    }

    public void procesarCheckIn(Reserva reserva) {
        reserva.getHabitacion().ocupar();
        habitacionDAO.actualizarEstado(reserva.getHabitacion().getIdHabitacion(), "OCUPADO");

        Estadia estadia = new Estadia();
        estadia.setIdReserva(reserva.getIdReserva());
        estadia.iniciarEstadia();
    }

    public List<Habitacion> obtenerHabitacionesDisponibles() {
        return habitacionDAO.listarDisponibles();
    }

    public double consultarOcupacion(Hotel hotel, LocalDateTime fecha) {
        // La fachada delega la lógica pesada al modelo (Hotel)
        return hotel.calcularOcupacion(fecha);
    }

    public boolean tienePermiso(String accion) {
        return usuarioActual != null && usuarioActual.tienePermiso(accion);
    }
}