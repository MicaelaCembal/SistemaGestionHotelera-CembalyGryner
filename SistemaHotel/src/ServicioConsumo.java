public class ServicioConsumo implements IServicio {
    private int idServicio;
    private String nombre;
    private double precio;

    public ServicioConsumo() {}

    public ServicioConsumo(int idServicio, String nombre, double precio) {
        this.idServicio = idServicio;
        this.nombre = nombre;
        this.precio = precio;
    }

    @Override
    public double calcularPrecio() { return precio; }

    @Override
    public String getNombre() { return nombre; }

    public int getIdServicio()          { return idServicio; }
    public void setIdServicio(int id)   { this.idServicio = id; }
    public void setNombre(String n)     { this.nombre = n; }
    public double getPrecio()           { return precio; }
    public void setPrecio(double p)     { this.precio = p; }

    @Override
    public String toString() {
        return "ServicioConsumo{id=" + idServicio + ", nombre=" + nombre + ", precio=" + precio + "}";
    }
}