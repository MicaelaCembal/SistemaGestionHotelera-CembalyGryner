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
}
