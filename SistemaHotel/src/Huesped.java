import java.time.LocalDateTime;
import java.util.List;

public class Huesped {
    private int idHuesped;
    private String nombre;
    private String apellido;
    private String email;
    private int telefono;
    private CategoriaHuesped categoria;
    private LocalDateTime  fechaRegistro;


    public void actualizarDatos(String nombre, String apellido, int dni, String email, int telefono, CategoriaHuesped categoria){

    }

    public List<Reserva> verHistorialReserva(){
        return null;
    }

    public CategoriaHuesped verCategoria(){
        return categoria;
    }
}
