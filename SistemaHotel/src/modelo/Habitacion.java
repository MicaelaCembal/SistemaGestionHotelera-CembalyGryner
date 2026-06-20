package modelo;

import java.time.LocalDateTime;

public class Habitacion {

    private int idHabitacion;
    private int idHotel;
    private int numero;
    private int piso;
    private TipoHabitacion tipoHabitacion;
    private EstadoHabitacion estado;

    public Habitacion() {
        this.estado = new EstadoDisponible();
    }

    public void cambiarEstado(EstadoHabitacion estado) {
        this.estado = estado;
    }

    public void ocupar() {
        cambiarEstado(new EstadoOcupado());
    }

    public void liberar() {
        cambiarEstado(new EstadoDisponible());
    }

    public void iniciarLimpieza() {
        cambiarEstado(new EstadoLimpieza());
    }

    public void iniciarMantenimiento() {
        cambiarEstado(new EstadoMantenimiento());
    }

    public int getIdHabitacion() {
        return idHabitacion;
    }

    public void setIdHabitacion(int idHabitacion) {
        this.idHabitacion = idHabitacion;
    }

    public int getIdHotel() {
        return idHotel;
    }

    public void setIdHotel(int idHotel) {
        this.idHotel = idHotel;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getPiso() {
        return piso;
    }

    public void setPiso(int piso) {
        this.piso = piso;
    }

    public TipoHabitacion getTipoHabitacion() {
        return tipoHabitacion;
    }

    public void setTipoHabitacion(TipoHabitacion tipoHabitacion) {
        this.tipoHabitacion = tipoHabitacion;
    }

    public EstadoHabitacion getEstado() {
        return estado;
    }

    public void setEstado(EstadoHabitacion estado) {
        this.estado = estado;
    }
}