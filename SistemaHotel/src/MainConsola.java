import controlador.ControladorSistema;
import controlador.NotificadorSistema;
import baseDatos.ConexionDB;
import modelo.*;
import java.util.Scanner;
import java.time.LocalDate;
import java.util.List;

public class MainConsola {

    public static void main(String[] args) {
        ConexionDB conexionDB = new ConexionDB();
        conexionDB.crearBaseYTablas();

        ControladorSistema sistema = ControladorSistema.getInstancia();
        sistema.registrarObserver(new NotificadorSistema());

        Scanner scanner = new Scanner(System.in);
        System.out.println("--- SISTEMA HOTELERO ---");

        System.out.print("Usuario: ");
        String user = scanner.nextLine();
        System.out.print("Password: ");
        String pass = scanner.nextLine();

        if (sistema.login(user, pass)) {
            System.out.println("\nLogin exitoso. Rol: " + sistema.getUsuarioActual().getRol());

            boolean salir = false;
            while (!salir) {
                System.out.println("\n--- MENÚ PRINCIPAL ---");
                System.out.println("1. Registrar nuevo Huésped");
                System.out.println("2. Ver todas las habitaciones y estados");
                System.out.println("3. Crear Reserva");
                System.out.println("4. Check-in");
                System.out.println("5. Check-out (Facturación)");
                System.out.println("6. Registrar Pago (Strategy)");
                System.out.println("7. Cargar Servicio a Estancia (Composite)");
                System.out.println("8. Salir");
                System.out.print("Seleccione opción: ");

                try {
                    int opcion = Integer.parseInt(scanner.nextLine());

                    if (opcion == 1) {
                        System.out.print("Nombre: "); String nom = scanner.nextLine();
                        System.out.print("Apellido: "); String ape = scanner.nextLine();
                        System.out.print("DNI: "); int dni = Integer.parseInt(scanner.nextLine());
                        System.out.print("Teléfono: "); int tel = Integer.parseInt(scanner.nextLine());
                        System.out.print("Email: "); String mail = scanner.nextLine();
                        System.out.println("Fecha Nacimiento (Año Mes Día):");
                        LocalDate fNac = LocalDate.of(scanner.nextInt(), scanner.nextInt(), scanner.nextInt());
                        scanner.nextLine();
                        System.out.println(">>> " + sistema.registrarHuesped(nom, ape, dni, tel, mail, fNac));

                    } else if (opcion == 2) {
                        System.out.println("\n-- LISTADO DE HABITACIONES --");
                        List<Habitacion> todas = sistema.obtenerTodasLasHabitaciones();
                        for (Habitacion h : todas) {
                            String nombreEstado = h.getEstado().getClass().getSimpleName().replace("Estado", "").toUpperCase();
                            System.out.println("Hab N°: " + h.getNumero() + " | Piso: " + h.getPiso() + " | ESTADO: [" + nombreEstado + "] | Precio: $" + h.getTipoHabitacion().getPrecioBaseNoche());
                        }

                    } else if (opcion == 3) {
                        System.out.print("DNI Huésped: "); int dniR = Integer.parseInt(scanner.nextLine());
                        System.out.print("Número Habitación: "); int numH = Integer.parseInt(scanner.nextLine());
                        System.out.println("Fecha Entrada (Año Mes Día):");
                        LocalDate fIn = LocalDate.of(scanner.nextInt(), scanner.nextInt(), scanner.nextInt());
                        System.out.println("Fecha Salida (Año Mes Día):");
                        LocalDate fOut = LocalDate.of(scanner.nextInt(), scanner.nextInt(), scanner.nextInt());
                        scanner.nextLine();
                        System.out.println(">>> " + sistema.realizarReserva(dniR, numH, fIn, fOut));

                    } else if (opcion == 4) {
                        System.out.print("DNI para Check-in: ");
                        int dniCI = Integer.parseInt(scanner.nextLine());
                        System.out.println(">>> " + sistema.procesarCheckIn(dniCI));

                    } else if (opcion == 5) {
                        System.out.print("DNI para Check-out: ");
                        int dniCO = Integer.parseInt(scanner.nextLine());
                        System.out.println(">>> " + sistema.procesarCheckOut(dniCO));

                    } else if (opcion == 6) {
                        System.out.print("Monto a pagar: "); double m = Double.parseDouble(scanner.nextLine());
                        Pago pago = new Pago();
                        pago.setMonto(m);
                        System.out.println("Seleccione Medio: 1.Tarjeta 2.Efectivo 3.Transferencia");
                        int med = Integer.parseInt(scanner.nextLine());
                        if (med == 1) pago.setMedioPago(new PagoTarjeta());
                        else if (med == 2) pago.setMedioPago(new PagoEfectivo());
                        else pago.setMedioPago(new PagoTransferencia());
                        pago.registrarPago();
                        System.out.println("Estado del pago: " + pago.getEstado());

                    } else if (opcion == 7) {
                        System.out.print("Ingrese DNI del huésped alojado: ");
                        int dniS = Integer.parseInt(scanner.nextLine());

                        List<IServicio> menu = sistema.obtenerMenuServicios();
                        System.out.println("\n-- MENÚ DE SERVICIOS DISPONIBLES --");
                        for (IServicio s : menu) {
                            int idMostrable = 0;
                            if (s instanceof ServicioConsumo) idMostrable = ((ServicioConsumo)s).getIdServicio();
                            if (s instanceof ServicioActividad) idMostrable = ((ServicioActividad)s).getIdServicio();
                            System.out.println(idMostrable + ". " + s.getNombre() + " [$" + s.calcularPrecio() + "]");
                        }

                        System.out.print("Seleccione ID del servicio: ");
                        int idServ = Integer.parseInt(scanner.nextLine());
                        System.out.print("Cantidad: ");
                        int cant = Integer.parseInt(scanner.nextLine());

                        System.out.println(">>> " + sistema.asignarServicioAEstadia(dniS, idServ, cant));

                    } else if (opcion == 8) {
                        salir = true;
                    }
                } catch (Exception e) {
                    System.out.println("Error en la operación: " + e.getMessage());
                }
            }
        } else {
            System.out.println("Credenciales incorrectas.");
        }
        scanner.close();
        System.out.println("Sistema cerrado.");
    }
}