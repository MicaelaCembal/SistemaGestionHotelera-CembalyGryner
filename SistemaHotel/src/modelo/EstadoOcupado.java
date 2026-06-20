package modelo;

public class EstadoOcupado implements EstadoHabitacion {

    @Override
    public void ocupar() {
        System.out.println("La habitación ya está ocupada.");
    }

    @Override
    public void liberar() {
        System.out.println("La habitación fue liberada.");
    }

    @Override
    public void iniciarLimpieza() {
        System.out.println("No se puede limpiar una habitación ocupada.");
    }

    @Override
    public void iniciarMantenimiento() {
        System.out.println("No se puede enviar a mantenimiento una habitación ocupada.");
    }
}