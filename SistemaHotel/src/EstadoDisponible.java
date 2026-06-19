public class EstadoDisponible implements EstadoHabitacion {

    @Override
    public void ocupar() {
        System.out.println("La habitación pasó a estado ocupado.");
    }

    @Override
    public void liberar() {
        System.out.println("La habitación ya está disponible.");
    }

    @Override
    public void iniciarLimpieza() {
        System.out.println("La habitación pasó a limpieza.");
    }

    @Override
    public void iniciarMantenimiento() {
        System.out.println("La habitación pasó a mantenimiento.");
    }
}