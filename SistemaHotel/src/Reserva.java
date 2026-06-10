import java.time.LocalDateTime;

public class Reserva {
    private int idReserva;
    private int idHuesped;
    private Habitacion habitacion;

    private LocalDateTime fechaCheckIn;
    private LocalDateTime fechaCheckOut;

    private EstadoReserva estado;
    private double costoTotal;
    public void confirmarReserva(){

    }

    public void cancelarReserva(){

    }

    public void modificarReserva(){

    }

    public void checkIn(){

    }

    public void checkOut(){

    }

    public double calcularCostoTotal(){
        return 0;
    }

    public void agregarServicioExtra(IServicio servicio){

    }
}
