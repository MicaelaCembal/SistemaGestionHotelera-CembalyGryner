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
            avisar("HUESPED: " + n + " " + a + " registrado correctamente.");
            return "Éxito.";
        }
        return "Error al registrar.";
    }

    public List<Habitacion> obtenerTodasLasHabitaciones() {
        return habitacionDAO.listarTodas();
    }

    public String realizarReserva(int dni, int numH, LocalDate in, LocalDate out) {
        if (in.isBefore(LocalDate.now())) {
            avisar("ERROR: No se pueden realizar reservas para fechas pasadas.");
            return "Error: Fecha de entrada inválida.";
        }

        Huesped hue = huespedDAO.buscarPorDni(dni);
        Habitacion hab = habitacionDAO.buscarPorNumero(numH);
        if (hue == null || hab == null) return "Error: Huesped o Habitación no encontrados.";

        if (reservaDAO.existeSolapamiento(hab.getIdHabitacion(), in.atStartOfDay(), out.atStartOfDay())) {
            avisar("ALERTA OVERBOOKING: Intento de reserva en Hab " + numH + " (Ocupada).");
            return "Error: La habitación no está disponible en esas fechas.";
        }

        Reserva r = new Reserva();
        r.setHuesped(hue); r.setHabitacion(hab);
        r.setFechaCheckin(in.atStartOfDay()); r.setFechaCheckout(out.atStartOfDay());
        r.setEstado(EstadoReserva.PENDIENTE);
        r.calcularCostoTotal();

        if (reservaDAO.insertar(r)) {
            avisar("RESERVA: Confirmada Hab " + numH + " para " + hue.getApellido());
            return "Reserva Exitosa. Total Base: $" + r.getCostoTotal();
        }
        return "Error al procesar reserva.";
    }

    public String procesarCheckIn(int dni) {
        Reserva r = reservaDAO.buscarReservaPendientePorDni(dni);
        if (r == null) return "No hay reservas pendientes para este DNI.";

        if (!(r.getHabitacion().getEstado() instanceof EstadoDisponible)) {
            avisar("BLOQUEO CHECK-IN: Hab " + r.getHabitacion().getNumero() + " no está lista.");
            return "Error: La habitación no está disponible (Estado: " + r.getHabitacion().getEstado().getClass().getSimpleName() + ")";
        }

        habitacionDAO.actualizarEstado(r.getHabitacion().getIdHabitacion(), "OCUPADO");
        reservaDAO.actualizarEstado(r.getIdReserva(), "CONFIRMADA");

        Estadia e = new Estadia();
        e.setIdReserva(r.getIdReserva());
        e.setFechaIngresoReal(LocalDateTime.now());

        double precioBase = r.getHabitacion().getTipoHabitacion().getPrecioBaseNoche();
        if (estadiaDAO.insertar(e, precioBase)) {
            avisar("CHECK-IN: El huésped DNI " + dni + " ha ingresado.");
            return "Check-in completado. Hab: " + r.getHabitacion().getNumero();
        }
        return "Error en la base de datos.";
    }

    public List<IServicio> obtenerMenuServicios() {
        return servicioDAO.listarTodos();
    }

    public String asignarServicioAEstadia(int dni, int idServicio, int cantidad) {
        Reserva r = reservaDAO.buscarReservaConfirmadaPorDni(dni);
        if (r == null) return "No hay una estadía en curso para este DNI.";

        Estadia e = estadiaDAO.buscarActivaPorReserva(r.getIdReserva());
        if (e == null) return "No se encontró registro de estadía abierta.";

        estadiaDAO.registrarServicio(e.getIdEstadia(), idServicio, cantidad);
        avisar("SERVICIO: Cargado a DNI " + dni + " (Estadia ID: " + e.getIdEstadia() + ")");
        return "Servicio cargado con éxito.";
    }

    public String procesarCheckOut(int dni) {
        Reserva r = reservaDAO.buscarReservaConfirmadaPorDni(dni);
        if (r == null) return "No hay estadía en curso.";

        Estadia e = estadiaDAO.buscarActivaPorReserva(r.getIdReserva());
        double subtotalServicios = estadiaDAO.calcularTotalServicios(e.getIdEstadia());
        double totalFinal = r.getCostoTotal() + subtotalServicios;

        if (estadiaDAO.cerrarEstadia(e.getIdEstadia(), subtotalServicios, totalFinal)) {
            habitacionDAO.actualizarEstado(r.getHabitacion().getIdHabitacion(), "LIMPIEZA");
            reservaDAO.actualizarEstado(r.getIdReserva(), "CANCELADA"); // Marcamos como finalizada/fuera de flujo

            avisar("CHECK-OUT: DNI " + dni + ". Factura: Hab $" + r.getCostoTotal() + " + Serv $" + subtotalServicios + " = TOTAL: $" + totalFinal);
            return "Check-out exitoso. Total a pagar: $" + totalFinal;
        }
        return "Error al cerrar estadía.";
    }

    public Usuario getUsuarioActual() { return usuarioActual; }
}