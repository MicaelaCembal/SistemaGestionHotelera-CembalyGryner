public class TipoHabitacion {
    private int idTipoHabitacion;
    private String nombre;
    private int capacidad;
    private double precioBaseNoche;

    public TipoHabitacion() {
    }

    public int getIdTipoHabitacion() {
        return idTipoHabitacion;
    }

    public void setIdTipoHabitacion(int idTipoHabitacion) {
        this.idTipoHabitacion = idTipoHabitacion;
    }

    public TipoHabitacion(int idTipoHabitacion) {

        this.idTipoHabitacion = idTipoHabitacion;
    }

    public double verPrecioBase(){
        return precioBaseNoche;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public double getPrecioBaseNoche() {
        return precioBaseNoche;
    }

    public void setPrecioBaseNoche(double precioBaseNoche) {
        this.precioBaseNoche = precioBaseNoche;
    }

    public int verCapacidad(){
        return capacidad;
    }
}
