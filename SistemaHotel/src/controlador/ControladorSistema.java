package controlador;

import baseDatos.*;
import modelo.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ControladorSistema {
    private static ControladorSistema instancia;
    private Usuario usuarioActual;
    private List<IObserver> observadores = new ArrayList<>();

    private HuespedDAO huespedDAO = new HuespedDAO();
    private HabitacionDAO habitacionDAO = new HabitacionDAO();
    private ReservaDAO reservaDAO = new ReservaDAO();
    private EstadiaDAO estadiaDAO = new EstadiaDAO();
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    private ControladorSistema() {}

    public static synchronized ControladorSistema getInstancia() {
        if (instancia == null) instancia = new ControladorSistema();
        return instancia;
    }

    public void registrarObserver(IObserver o) { observadores.add(o); }

    private void avisar(String msg) {
        for (IObserver o : observadores) o.notificar(msg);
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

    public String registrarHuesped(String n, String a, int d, int t, String e, LocalDate f) {
        Huesped h = new Huesped();
        h.setNombre(n); h.setApellido(a); h.setDni(d); h.setTelefono(t); h.setEmail(e); h.setFechaNacimiento(f);
        h.setCategoria(CategoriaHuesped.REGULAR);
        if (huespedDAO.insertar(h)) {
            avisar("Huésped registrado: " + n + " " + a);
            return "Éxito.";
        }
        return "Error.";
    }

    public List<Habitacion> obtenerTodasLasHabitaciones() {
        return habitacionDAO.listarTodas();
    }

    public String realizarReserva(int dniHuesped, int numHab, LocalDate checkIn, LocalDate checkOut) {
        if (checkIn.isBefore(LocalDate.now())) {
            avisar("ERROR: Fecha de entrada inválida.");
            return "Error.";
        }
        if (!checkOut.isAfter(checkIn)) {
            avisar("ERROR: Rango de fechas inválido.");
            return "Error.";
        }
        Huesped hue = huespedDAO.buscarPorDni(dniHuesped);
        if (hue == null) return "Huésped no encontrado.";
        Habitacion hab = habitacionDAO.buscarPorNumero(numHab);
        if (hab == null) return "Habitación no encontrada.";

        LocalDateTime inicio = checkIn.atStartOfDay();
        LocalDateTime fin = checkOut.atStartOfDay();

        if (reservaDAO.existeSolapamiento(hab.getIdHabitacion(), inicio, fin)) {
            avisar("ALERTA DE OVERBOOKING: Hab " + numHab + " ocupada.");
            return "Error: Habitación ocupada.";
        }

        Reserva r = new Reserva();
        r.setHuesped(hue);
        r.setHabitacion(hab);
        r.setFechaCheckin(inicio);
        r.setFechaCheckout(fin);
        r.setEstado(EstadoReserva.PENDIENTE);
        r.calcularCostoTotal();

        if (reservaDAO.insertar(r)) {
            avisar("RESERVA CONFIRMADA: Hab " + numHab + " para " + hue.getApellido());
            return "Reserva Exitosa.";
        }
        return "Error.";
    }

    public String procesarCheckIn(int dni) {
        Reserva r = reservaDAO.buscarReservaPendientePorDni(dni);
        if (r == null) return "No hay reserva PENDIENTE.";

        if (!(r.getHabitacion().getEstado() instanceof EstadoDisponible)) {
            avisar("BLOQUEO: Hab " + r.getHabitacion().getNumero() + " en estado " + r.getHabitacion().getEstado().getClass().getSimpleName());
            return "Error: Habitación no disponible.";
        }

        habitacionDAO.actualizarEstado(r.getHabitacion().getIdHabitacion(), "OCUPADO");
        reservaDAO.actualizarEstado(r.getIdReserva(), "CONFIRMADA");

        Estadia e = new Estadia();
        e.setIdReserva(r.getIdReserva());
        e.setFechaIngresoReal(LocalDateTime.now());
        if (estadiaDAO.insertar(e)) {
            avisar("CHECK-IN: DNI " + dni + " ingresó.");
            return "Check-in completado.";
        }
        return "Error.";
    }

    public String procesarCheckOut(int dni) {
        Reserva r = reservaDAO.buscarReservaConfirmadaPorDni(dni);
        if (r == null) return "No hay estadía activa para este DNI.";

        habitacionDAO.actualizarEstado(r.getHabitacion().getIdHabitacion(), "LIMPIEZA");
        reservaDAO.actualizarEstado(r.getIdReserva(), "CANCELADA");

        avisar("CHECK-OUT: DNI " + dni + " salió. Habitación enviada a limpieza.");
        return "Check-out completado.";
    }
}