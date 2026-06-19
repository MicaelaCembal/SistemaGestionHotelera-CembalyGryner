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
    public int getIdPromocion() {
        return idPromocion;
    }

    public void setIdPromocion(int id) {
        this.idPromocion = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String n) {
        this.nombre = n;
    }

    public double getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    public void setPorcentajeDescuento(double p) {
        this.porcentajeDescuento = p;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime f) {
        this.fechaInicio = f;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDateTime f) {
        this.fechaFin = f;
    }

    public TipoPromocion getTipo() {
        return tipo;
    }

    public void setTipo(TipoPromocion t) {
        this.tipo = t;
    }
}