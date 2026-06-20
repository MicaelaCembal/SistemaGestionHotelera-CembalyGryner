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

    public void registrarObserver(IObserver o) {
        observadores.add(o);
    }

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

    public List<Habitacion> obtenerHabitacionesDisponibles() {
        return habitacionDAO.listarDisponibles();
    }

    public String realizarReserva(int dniHuesped, int numHab, LocalDate checkIn, LocalDate checkOut) {
        if (!checkOut.isAfter(checkIn)) {
            String errorFecha = "ERROR LÓGICO: La fecha de salida (" + checkOut + ") no puede ser anterior o igual a la de entrada (" + checkIn + ")";
            avisar(errorFecha);
            return "Error: Fechas inválidas.";
        }

        Huesped hue = huespedDAO.buscarPorDni(dniHuesped);
        if (hue == null) return "Huésped no encontrado.";

        Habitacion hab = habitacionDAO.buscarPorNumero(numHab);
        if (hab == null) return "Habitación no encontrada.";

        LocalDateTime inicio = checkIn.atStartOfDay();
        LocalDateTime fin = checkOut.atStartOfDay();

        if (reservaDAO.existeSolapamiento(hab.getIdHabitacion(), inicio, fin)) {
            String conflicto = "CONFLICTO: La habitación " + numHab + " ya está ocupada entre " + checkIn + " y " + checkOut;
            avisar(conflicto);
            return "Error: Habitación no disponible en esas fechas.";
        }

        Reserva r = new Reserva();
        r.setHuesped(hue);
        r.setHabitacion(hab);
        r.setFechaCheckin(inicio);
        r.setFechaCheckout(fin);
        r.setEstado(EstadoReserva.PENDIENTE);
        r.setCostoTotal(r.calcularCostoTotal());

        if (reservaDAO.insertar(r)) {
            avisar("RESERVA CONFIRMADA: Hab " + numHab + " para " + hue.getApellido());
            return "Reserva Exitosa.";
        }
        return "Error al guardar reserva.";
    }

    public String procesarCheckIn(int dni) {
        Reserva r = reservaDAO.buscarReservaPendientePorDni(dni);
        if (r == null) return "No hay reserva pendiente.";
        habitacionDAO.actualizarEstado(r.getHabitacion().getIdHabitacion(), "OCUPADO");
        Estadia e = new Estadia();
        e.setIdReserva(r.getIdReserva());
        e.setFechaIngresoReal(LocalDateTime.now());
        if (estadiaDAO.insertar(e)) {
            avisar("CHECK-IN: DNI " + dni + " ingresó al hotel.");
            return "Check-in completado.";
        }
        return "Error.";
    }

    public String procesarCheckOut(int dni) {
        Reserva reserva = reservaDAO.buscarReservaPorDni(dni);
        if (reserva == null) return "No se encontró reserva.";
        habitacionDAO.actualizarEstado(reserva.getHabitacion().getIdHabitacion(), "LIMPIEZA");
        avisar("CHECK-OUT: DNI " + dni + " salió. Habitación enviada a limpieza.");
        return "Check-out completado.";
    }
}