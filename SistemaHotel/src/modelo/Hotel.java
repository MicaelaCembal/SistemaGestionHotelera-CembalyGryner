package modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Hotel {

    private int idHotel;
    private String nombre;
    private String direccion;
    private String ciudad;
    private String categoria;
    private List<Habitacion> habitaciones;


    public Hotel() {
        this.habitaciones = new ArrayList<>();
    }


    public void agregarHabitacion(Habitacion habitacion) {
        habitaciones.add(habitacion);
    }


    public void eliminarHabitacion(Habitacion habitacion) {
        habitaciones.remove(habitacion);
    }


    public List<Habitacion> listarHabitaciones() {
        return habitaciones;
    }


    public double calcularOcupacion(LocalDateTime fecha) {

        if (habitaciones.isEmpty()) {
            return 0;
        }

        int ocupadas = 0;

        for (Habitacion habitacion : habitaciones) {
            if (habitacion.getEstado() instanceof EstadoOcupado) {
                ocupadas++;
            }
        }

        return (double) ocupadas * 100 / habitaciones.size();
    }


    public int getIdHotel() {
        return idHotel;
    }

    public void setIdHotel(int idHotel) {
        this.idHotel = idHotel;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public List<Habitacion> getHabitaciones() {
        return habitaciones;
    }

    public void setHabitaciones(List<Habitacion> habitaciones) {
        this.habitaciones = habitaciones;
    }

}