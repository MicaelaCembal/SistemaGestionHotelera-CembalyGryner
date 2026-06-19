import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Huesped {

    private int idHuesped;
    private String nombre;
    private String apellido;
    private int dni;
    private String email;
    private int telefono;
    private CategoriaHuesped categoria;
    private LocalDateTime fechaRegistro;
    private List<Reserva> reservas;


    public Huesped() {
        this.reservas = new ArrayList<>();
    }


    public void actualizarDatos(String nombre, String apellido, int dni, String email, int telefono, CategoriaHuesped categoria) {

        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.email = email;
        this.telefono = telefono;
        this.categoria = categoria;
    }


    public List<Reserva> verHistorialReserva() {
        return reservas;
    }


    public CategoriaHuesped verCategoria() {
        return categoria;
    }


    public int getIdHuesped() {
        return idHuesped;
    }

    public void setIdHuesped(int idHuesped) {
        this.idHuesped = idHuesped;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public CategoriaHuesped getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaHuesped categoria) {
        this.categoria = categoria;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }

}