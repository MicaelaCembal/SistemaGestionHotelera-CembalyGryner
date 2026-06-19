import java.time.LocalDateTime;

public class ServicioEstadia {

    private int idServicioEstadia;
    private int idEstadia;
    private int idServicio;
    private int cantidad;
    private LocalDateTime fecha;
    private double subtotal;
    private IServicio servicio;


    public ServicioEstadia(int idServicioEstadia, int idEstadia, IServicio servicio, int cantidad, LocalDateTime fecha) {

        this.idServicioEstadia = idServicioEstadia;
        this.idEstadia = idEstadia;
        this.servicio = servicio;
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.subtotal = calcularSubtotal();
    }


    public ServicioEstadia(int idServicioEstadia, int idEstadia, int idServicio, int cantidad, LocalDateTime fecha, double subtotal, IServicio servicio) {

        this.idServicioEstadia = idServicioEstadia;
        this.idEstadia = idEstadia;
        this.idServicio = idServicio;
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.subtotal = subtotal;
        this.servicio = servicio;
    }


    public double calcularSubtotal() {

        if (servicio == null) {
            return 0;
        }

        subtotal = servicio.calcularPrecio() * cantidad;
        return subtotal;
    }


    public int getIdServicioEstadia() {
        return idServicioEstadia;
    }

    public void setIdServicioEstadia(int idServicioEstadia) {
        this.idServicioEstadia = idServicioEstadia;
    }

    public int getIdEstadia() {
        return idEstadia;
    }

    public void setIdEstadia(int idEstadia) {
        this.idEstadia = idEstadia;
    }

    public int getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(int idServicio) {
        this.idServicio = idServicio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public IServicio getServicio() {
        return servicio;
    }

    public void setServicio(IServicio servicio) {
        this.servicio = servicio;
    }

}