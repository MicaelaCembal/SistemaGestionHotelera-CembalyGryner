import java.time.LocalDateTime;

public class Reserva {
    private int idReserva;
    private int idHuesped;
    private Habitacion habitacion;

    private LocalDateTime fechaCheckIn;
    private LocalDateTime fechaCheckOut;

    private EstadoReserva estado;
    private double costoTotal;
}
