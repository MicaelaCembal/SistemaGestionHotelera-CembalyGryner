package modelo;

import java.time.LocalDateTime;

public class Promocion {

    private int idPromocion;
    private String nombre;
    private double porcentajeDescuento;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private TipoPromocion tipo;

    public Promocion() {
    }

    public boolean esAplicable(LocalDateTime fecha) {
        return fecha.isAfter(fechaInicio) && fecha.isBefore(fechaFin);
    }

    public double calcularDescuento(double monto) {
        return monto * porcentajeDescuento / 100;
    }

    public int getIdPromocion() {
        return idPromocion;
    }

    public void setIdPromocion(int idPromocion) {
        this.idPromocion = idPromocion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    public void setPorcentajeDescuento(double porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

    public TipoPromocion getTipo() {
        return tipo;
    }

    public void setTipo(TipoPromocion tipo) {
        this.tipo = tipo;
    }
}