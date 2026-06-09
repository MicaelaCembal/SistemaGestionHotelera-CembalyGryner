import java.time.LocalDateTime;

public class Promocion {
    private int idPromocion;
    private String nombre;
    private double porcentajeDescuento;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private TipoPromocion tipo;
    
    public boolean esAplicable(LocalDateTime fecha) {
        // Implementación
        return false;
    }

    public double calcularDescuento(double monto) {
        // Implementación
        return 0.0;
    }
}