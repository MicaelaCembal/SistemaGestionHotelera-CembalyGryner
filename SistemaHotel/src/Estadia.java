import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Estadia {
    private int idEstadia;
    private int idReserva;
    private LocalDateTime fechaIngresoReal;
    private LocalDateTime fechaEgresoReal;
    private List<ServicioEstadia> serviciosEstadia;

    public Estadia() {
        this.serviciosEstadia = new ArrayList<>();
    }

    public void iniciarEstadia() {
        // Implementación
    }

    public void finalizarEstadia() {
        // Implementación
    }

    public void agregarServicio(IServicio servicio, int cantidad) {
        // Implementación
    }

    public List<ServicioEstadia> listarServicios() {
        return this.serviciosEstadia;
    }

    public double calcularSubtotalServicios() {
        // Implementación
        return 0.0;
    }
}
