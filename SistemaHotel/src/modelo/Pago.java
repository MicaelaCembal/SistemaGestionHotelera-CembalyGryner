package modelo;

import java.time.LocalDateTime;

public class Pago {

    private int idPago;
    private int idReserva;
    private IMedioPago medioPago;
    private double monto;
    private LocalDateTime fechaPago;
    private EstadoPago estado;

    public Pago() {
        this.estado = EstadoPago.PENDIENTE;
    }

    public void registrarPago() {

        if (medioPago != null && medioPago.validar() && medioPago.procesarPago(monto)) {
            estado = EstadoPago.PAGADO;
            fechaPago = LocalDateTime.now();
        } else {
            estado = EstadoPago.PENDIENTE;
        }

    }

    public void reembolsar() {
        estado = EstadoPago.REEMBOLSADO;
    }

    public EstadoPago verEstado() {
        return estado;
    }

    public int getIdPago() {
        return idPago;
    }

    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }

    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public IMedioPago getMedioPago() {
        return medioPago;
    }

    public void setMedioPago(IMedioPago medioPago) {
        this.medioPago = medioPago;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public LocalDateTime getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDateTime fechaPago) {
        this.fechaPago = fechaPago;
    }

    public EstadoPago getEstado() {
        return estado;
    }

    public void setEstado(EstadoPago estado) {
        this.estado = estado;
    }
}