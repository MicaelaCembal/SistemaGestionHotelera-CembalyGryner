import java.time.LocalDateTime;

public class Pago {
    private int idPago;
    private int idReserva;
    private IMedioPago medioPago;
    private double monto;
    private LocalDateTime fechaPago;
    private EstadoPago estado;

    public void registrarPago() {
        // Implementación
    }

    public void reembolsar() {
        // Implementación
    }

    public EstadoPago verEstado() {
        return this.estado;
    }

    public int getIdPago() {
        return idPago;
    }

    public void setIdPago(int id) {
        this.idPago = id;
    }

    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int id) {
        this.idReserva = id;
    }

    public IMedioPago getMedioPago() {
        return medioPago;
    }

    public void setMedioPago(IMedioPago m) {
        this.medioPago = m;
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

    public void setFechaPago(LocalDateTime f) {
        this.fechaPago = f;
    }

    public EstadoPago getEstado() {
        return estado;
    }

    public void setEstado(EstadoPago e) {
        this.estado = e;
    }
}
