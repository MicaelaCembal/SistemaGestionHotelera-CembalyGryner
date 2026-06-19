import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Reserva {
    private int idReserva;
    private Huesped huesped;
    private Habitacion habitacion;
    private Promocion promocion;
    private LocalDateTime fechaCheckin;
    private LocalDateTime fechaCheckout;
    private double costoTotal;
    private EstadoReserva estado;

    public Reserva() {}

    public Reserva(int idReserva, Huesped huesped, Habitacion habitacion, LocalDateTime fechaCheckin, LocalDateTime fechaCheckout, double costoTotal, EstadoReserva estado) {
        this.idReserva = idReserva;
        this.huesped = huesped;
        this.habitacion = habitacion;
        this.promocion = promocion;
        this.fechaCheckin = fechaCheckin;
        this.fechaCheckout = fechaCheckout;
        this.costoTotal = costoTotal;
        this.estado = estado;
    }

    public Reserva(int idReserva, Huesped huesped, Habitacion habitacion, Promocion promocion, LocalDateTime fechaCheckin, LocalDateTime fechaCheckout, double costoTotal, EstadoReserva estado) {
        this(idReserva, huesped, habitacion, fechaCheckin, fechaCheckout, costoTotal, estado);
        this.promocion = promocion;
    }

    public void confirmarReserva() {
        this.estado = EstadoReserva.PENDIENTE;
    }

    public void cancelarReserva() {
        this.estado = EstadoReserva.CANCELADA;
    }

    public double calcularCostoTotal() {
        if (habitacion == null || habitacion.getTipoHabitacion() == null) return 0;
        long noches = ChronoUnit.DAYS.between(fechaCheckin, fechaCheckout);
        double base = noches * habitacion.getTipoHabitacion().verPrecioBase();
        if (promocion != null && promocion.esAplicable(fechaCheckin)) {
            base -= promocion.calcularDescuento(base);
        }
        this.costoTotal = base;
        return costoTotal;
    }

    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public Huesped getHuesped() {
        return huesped;
    }

    public void setHuesped(Huesped huesped) {
        this.huesped = huesped;
    }

    public Habitacion getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(Habitacion habitacion) {
        this.habitacion = habitacion;
    }

    public Promocion getPromocion() {
        return promocion;
    }

    public void setPromocion(Promocion promocion) {
        this.promocion = promocion;
    }

    public LocalDateTime getFechaCheckin() {
        return fechaCheckin;
    }

    public void setFechaCheckin(LocalDateTime fechaCheckin) {
        this.fechaCheckin = fechaCheckin;
    }

    public LocalDateTime getFechaCheckout() {
        return fechaCheckout;
    }

    public void setFechaCheckout(LocalDateTime fechaCheckout) {
        this.fechaCheckout = fechaCheckout;
    }

    public double getCostoTotal() {
        return costoTotal;
    }

    public void setCostoTotal(double costoTotal) {
        this.costoTotal = costoTotal;
    }

    public EstadoReserva getEstado() {
        return estado;
    }

    public void setEstado(EstadoReserva estado) {
        this.estado = estado;
    }
}
