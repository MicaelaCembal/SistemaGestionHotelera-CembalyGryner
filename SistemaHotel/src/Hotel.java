import java.time.LocalDateTime;
import java.util.List;

public class Hotel {

    private int idHotel;
    private String nombre;
    private String direccion;
    private String ciudad;
    private String categoria;

    public void agregarHabitacion(Habitacion habitacion) {

    }

    public void eliminarHabitacion(Habitacion habitacion) {

    }

    public List<Habitacion> listarHabitaciones() {
        return null;
    }

    public double calcularOcupacion(LocalDateTime fecha) {
        return 0;
    }

    public int getIdHotel() {
        return idHotel;
    }

    public void setIdHotel(int id) {
        this.idHotel = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}