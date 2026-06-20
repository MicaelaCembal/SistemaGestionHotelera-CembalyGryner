package controlador;

import baseDatos.*;
import modelo.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ControladorSistema {
    private static ControladorSistema instancia;
    private Usuario usuarioActual;

    private List<IObserver> observadores = new ArrayList<>();

    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private HuespedDAO huespedDAO = new HuespedDAO();
    private HabitacionDAO habitacionDAO = new HabitacionDAO();
    private ReservaDAO reservaDAO = new ReservaDAO();
    private EstadiaDAO estadiaDAO = new EstadiaDAO();

    private ControladorSistema() {}

    public static synchronized ControladorSistema getInstancia() {
        if (instancia == null) instancia = new ControladorSistema();
        return instancia;
    }

    public void registrarObserver(IObserver o) { observadores.add(o); }
    private void avisar(String msg) { for(IObserver o : observadores) o.notificar(msg); }

    public boolean login(String u, String p) {
        Usuario user = usuarioDAO.autenticar(u, p);
        if (user != null) {
            this.usuarioActual = user;
            return true;
        }
        return false;
    }

    public Usuario getUsuarioActual() { return usuarioActual; }

    public String registrarHuesped(String n, String a, int d, int t, String e, LocalDate f) {
        Huesped h = new Huesped();
        h.setNombre(n); h.setApellido(a); h.setDni(d); h.setTelefono(t); h.setEmail(e); h.setFechaNacimiento(f);
        h.setCategoria(CategoriaHuesped.REGULAR);
        if (huespedDAO.insertar(h)) {
            avisar("Nuevo huésped registrado: " + n + " " + a);
            return "Éxito.";
        }
        return "Error.";
    }

    public List<Habitacion> obtenerHabitacionesDisponibles() {
        return habitacionDAO.listarDisponibles();
    }

    public String realizarReserva(int dniHuesped, int numHab, LocalDate checkIn, LocalDate checkOut) {
        Huesped hue = huespedDAO.buscarPorDni(dniHuesped);
        if (hue == null) return "Huésped no encontrado.";

        Habitacion hab = habitacionDAO.buscarPorNumero(numHab);
        if (hab == null) return "Habitación no encontrada.";

        Reserva r = new Reserva();
        r.setHuesped(hue);
        r.setHabitacion(hab);
        r.setFechaCheckin(checkIn.atStartOfDay());
        r.setFechaCheckout(checkOut.atStartOfDay());
        r.setEstado(EstadoReserva.PENDIENTE);
        r.setCostoTotal(5000.0); // todo: Esto es un valor de prueba. Ver manejo de costos dependiendo del tipo de habitacion

        if (reservaDAO.insertar(r)) {
            avisar("Reserva creada para el DNI " + dniHuesped + " en hab. " + numHab);
            return "Reserva Exitosa.";
        }
        return "Error al reservar.";
    }

    public String procesarCheckIn(int dni) {
        Reserva r = reservaDAO.buscarReservaPendientePorDni(dni);
        if (r == null) return "No hay reserva pendiente.";

        habitacionDAO.actualizarEstado(r.getHabitacion().getIdHabitacion(), "OCUPADO");
        Estadia e = new Estadia();
        e.setIdReserva(r.getIdReserva());
        e.setFechaIngresoReal(java.time.LocalDateTime.now());

        if (estadiaDAO.insertar(e)) {
            avisar("CHECK-IN REALIZADO: El huésped con DNI " + dni + " ha ingresado.");
            return "Check-in completado.";
        }
        return "Error.";
    }
}