import java.time.LocalDateTime;

public class Habitacion {
    private int idHabitacion;
    private int idHotel;
    private int numero;
    private int piso;
    private EstadoHabitacion estado;

    public boolean estaDisponible(LocalDateTime fechaIngreso, LocalDateTime fechaSalida){
        return false;
    }

    public void cambiarEstado(EstadoHabitacion estado){

    }

    public int getIdHabitacion() {
        return idHabitacion;
    }

    public TipoHabitacion getTipoHabitacion() {
        return getTipoHabitacion();
    }

}
