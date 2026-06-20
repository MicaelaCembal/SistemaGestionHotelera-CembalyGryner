package modelo;

import java.util.ArrayList;
import java.util.List;

public class PaqueteServicios implements IServicio {

    private String nombre;
    private List<IServicio> servicios;

    public PaqueteServicios(String nombre) {
        this.nombre = nombre;
        this.servicios = new ArrayList<>();
    }

    public void agregarServicio(IServicio servicio) {
        servicios.add(servicio);
    }

    public void quitarServicio(IServicio servicio) {
        servicios.remove(servicio);
    }

    @Override
    public double calcularPrecio() {

        double total = 0;

        for(IServicio s : servicios){
            total += s.calcularPrecio();
        }

        return total;
    }

    @Override
    public String getNombre() {
        return nombre;
    }
}