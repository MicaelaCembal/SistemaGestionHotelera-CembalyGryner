package modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Estadia {
    private int idEstadia;
    private Reserva reserva;
    private LocalDateTime fechaIngresoReal;
    private LocalDateTime fechaEgresoReal;
    private List<ServicioEstadia> serviciosEstadia;
    private int idReserva;

    public Estadia() {
        this.serviciosEstadia = new ArrayList<>();
    }

    public Estadia(int idEstadia, Reserva reserva, LocalDateTime fechaIngresoReal, LocalDateTime fechaEgresoReal, int cantidadInt, double subtotalServicios) {

        this.idEstadia = idEstadia;
        this.reserva = reserva;
        this.fechaIngresoReal = fechaIngresoReal;
        this.fechaEgresoReal = fechaEgresoReal;
        this.serviciosEstadia = new ArrayList<>();

        if (reserva != null) {
            this.idReserva = reserva.getIdReserva();
        }
    }



    public void iniciarEstadia() {
        this.fechaIngresoReal = LocalDateTime.now();
        if (reserva != null && reserva.getHabitacion() != null) {
            reserva.getHabitacion().ocupar();
        }
    }

    public void finalizarEstadia() {
        this.fechaEgresoReal = LocalDateTime.now();
        if (reserva != null && reserva.getHabitacion() != null) {
            reserva.getHabitacion().iniciarLimpieza();
        }
    }

    public void agregarServicio(IServicio servicio, int cantidad) {
        ServicioEstadia se = new ServicioEstadia(0, this.idEstadia, servicio, cantidad, LocalDateTime.now());
        serviciosEstadia.add(se);
    }

    public List<ServicioEstadia> listarServicios() {
        return serviciosEstadia;
    }

    public double calcularSubtotalServicios() {
        return serviciosEstadia.stream()
                .mapToDouble(ServicioEstadia::calcularSubtotal)
                .sum();
    }

    public int getIdEstadia() {
        return idEstadia;
    }

    public void setIdEstadia(int idEstadia) {
        this.idEstadia = idEstadia;
    }

    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public LocalDateTime getFechaIngresoReal() {
        return fechaIngresoReal;
    }

    public void setFechaIngresoReal(LocalDateTime fechaIngresoReal) {
        this.fechaIngresoReal = fechaIngresoReal;
    }

    public LocalDateTime getFechaEgresoReal() {
        return fechaEgresoReal;
    }

    public void setFechaEgresoReal(LocalDateTime fechaEgresoReal) {
        this.fechaEgresoReal = fechaEgresoReal;
    }

    public List<ServicioEstadia> getServiciosEstadia() {
        return serviciosEstadia;
    }

    public void setServiciosEstadia(List<ServicioEstadia> serviciosEstadia) {
        this.serviciosEstadia = serviciosEstadia;
    }
}
