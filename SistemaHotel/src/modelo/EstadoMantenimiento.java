package modelo;

public class EstadoMantenimiento implements EstadoHabitacion {

    @Override
    public void ocupar() {
        System.out.println("No se puede ocupar una habitación en mantenimiento.");
    }

    @Override
    public void liberar() {
        System.out.println("La habitación quedó disponible.");
    }

    @Override
    public void iniciarLimpieza() {
        System.out.println("La habitación pasó a limpieza.");
    }

    @Override
    public void iniciarMantenimiento() {
        System.out.println("La habitación ya está en mantenimiento.");
    }
}