import java.time.LocalDateTime;
import java.util.List;

public class Huesped {
    private int idHuesped;
    private String nombre;
    private String apellido;
    private int dni;
    private String email;
    private int telefono;
    private CategoriaHuesped categoria;
    private int cantVisitas;
    private LocalDateTime fechaNacimiento;

    public Huesped() {}

    public Huesped(int idHuesped, String nombre, String apellido, int dni, int telefono,
                   String email, CategoriaHuesped categoria, int cantVisitas, LocalDateTime fechaNacimiento) {
        this.idHuesped = idHuesped;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.telefono = telefono;
        this.email = email;
        this.categoria = categoria;
        this.cantVisitas = cantVisitas;
        this.fechaNacimiento = fechaNacimiento;
    }

    public void actualizarDatos(String nombre, String apellido, int dni, String email,
                                int telefono, CategoriaHuesped categoria) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.email = email;
        this.telefono = telefono;
        this.categoria = categoria;
    }

    public List<Reserva> verHistorialReserva() {
        return null;
    }

    public CategoriaHuesped verCategoria() {
        return categoria;
    }

    public int getIdHuesped() {
        return idHuesped;
    }

    public void setIdHuesped(int id) {
        this.idHuesped = id;
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

    public int getCantVisitas() {
        return cantVisitas;
    }

    public void setCantVisitas(int cantVisitas) {
        this.cantVisitas = cantVisitas;
    }

    public LocalDateTime getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDateTime fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    @Override
    public String toString() {
        return "Huesped{id=" + idHuesped + ", nombre=" + nombre + " " + apellido +
                ", dni=" + dni + ", categoria=" + categoria + "}";
    }
}