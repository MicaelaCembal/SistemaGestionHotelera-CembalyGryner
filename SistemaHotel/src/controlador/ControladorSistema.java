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
    private ServicioDAO servicioDAO = new ServicioDAO();
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
        if (user != null) { this.usuarioActual = user; return true; }
        return false;
    }

    public String registrarHuesped(String n, String a, int d, int t, String e, LocalDate f) {
        Huesped h = new Huesped();
        h.setNombre(n); h.setApellido(a); h.setDni(d); h.setTelefono(t); h.setEmail(e); h.setFechaNacimiento(f);
        h.setCategoria(CategoriaHuesped.REGULAR);
        if (huespedDAO.insertar(h)) {
            avisar("HUESPED: " + n + " " + a + " registrado.");
            return "Exito.";
        }
        return "Error.";
    }

    public List<Habitacion> obtenerTodasLasHabitaciones() { return habitacionDAO.listarTodas(); }

    public String realizarReserva(int dni, int numH, LocalDate in, LocalDate out) {
        if (in.isBefore(LocalDate.now())) return "Error: Fecha pasada.";
        Huesped hue = huespedDAO.buscarPorDni(dni);
        Habitacion hab = habitacionDAO.buscarPorNumero(numH);
        if (hue == null || hab == null) return "Error: No encontrado.";

        if (reservaDAO.existeSolapamiento(hab.getIdHabitacion(), in.atStartOfDay(), out.atStartOfDay())) {
            avisar("OVERBOOKING en Hab " + numH);
            return "Error: Habitación ocupada.";
        }

        Reserva r = new Reserva();
        r.setHuesped(hue); r.setHabitacion(hab);
        r.setFechaCheckin(in.atStartOfDay()); r.setFechaCheckout(out.atStartOfDay());
        r.setEstado(EstadoReserva.PENDIENTE);
        r.calcularCostoTotal();

        if (reservaDAO.insertar(r)) {
            avisar("RESERVA: Hab " + numH + " para " + hue.getApellido());
            return "Reserva Exitosa. Total Hab: $" + r.getCostoTotal();
        }
        return "Error.";
    }

    public String procesarCheckIn(int dni) {
        Reserva r = reservaDAO.buscarReservaPendientePorDni(dni);
        if (r == null) return "No hay reserva PENDIENTE.";
        if (!(r.getHabitacion().getEstado() instanceof EstadoDisponible)) return "Habitación no lista.";

        habitacionDAO.actualizarEstado(r.getHabitacion().getIdHabitacion(), "OCUPADO");
        reservaDAO.actualizarEstado(r.getIdReserva(), "CONFIRMADA");

        Estadia e = new Estadia();
        e.setIdReserva(r.getIdReserva());
        e.setFechaIngresoReal(LocalDateTime.now());

        if (estadiaDAO.insertar(e, r.getHabitacion().getTipoHabitacion().getPrecioBaseNoche())) {
            avisar("CHECK-IN: DNI " + dni + " ingresó.");
            return "Check-in completado.";
        }
        return "Error.";
    }

    public List<IServicio> obtenerMenuServicios() { return servicioDAO.listarTodos(); }

    public String asignarServicioAEstadia(int dni, int idServicio, int cantidad) {
        Reserva r = reservaDAO.buscarReservaConfirmadaPorDni(dni);
        if (r == null) return "No hay estadía activa.";
        Estadia e = estadiaDAO.buscarActivaPorReserva(r.getIdReserva());
        if (e == null) return "Estadía no encontrada.";

        estadiaDAO.registrarServicio(e.getIdEstadia(), idServicio, cantidad);
        avisar("SERVICIO: Cargado a DNI " + dni);
        return "Servicio cargado.";
    }

    public String procesarCheckOut(int dni) {
        Reserva r = reservaDAO.buscarReservaConfirmadaPorDni(dni);
        if (r == null) return "No hay estadía activa para este DNI.";

        Estadia e = estadiaDAO.buscarActivaPorReserva(r.getIdReserva());
        if (e == null) return "Error al recuperar estadía.";

        double totalServicios = estadiaDAO.calcularTotalServicios(e.getIdEstadia());
        double totalFinal = r.getCostoTotal() + totalServicios;

        if (estadiaDAO.cerrarEstadia(e.getIdEstadia(), totalServicios, totalFinal)) {
            habitacionDAO.actualizarEstado(r.getHabitacion().getIdHabitacion(), "LIMPIEZA");
            reservaDAO.actualizarEstado(r.getIdReserva(), "FINALIZADA");

            avisar("CHECK-OUT: DNI " + dni + ". TOTAL: $" + totalFinal + " (Hab: $" + r.getCostoTotal() + " + Serv: $" + totalServicios + ")");
            return "Check-out exitoso. Total: $" + totalFinal;
        }
        return "Error.";
    }

    public Usuario getUsuarioActual() { return usuarioActual; }
}