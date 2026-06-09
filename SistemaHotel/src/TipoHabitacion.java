public class TipoHabitacion {
    private int idTipoHabitacion;
    private String nombre;
    private int capacidad;
    private double precioBaseNoche;

    public double verPrecioBase(){
        return precioBaseNoche;
    }

    public int verCapacidad(){
        return capacidad;
    }
}
