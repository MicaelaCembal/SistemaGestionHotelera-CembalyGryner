import controlador.ControladorSistema;
import controlador.NotificadorSistema;
import baseDatos.ConexionDB;
import modelo.Habitacion;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.DateTimeException;
import java.util.List;
import modelo.Pago;
import modelo.PagoTarjeta;
import modelo.PagoEfectivo;
import modelo.PagoTransferencia;
import modelo.PaqueteServicios;
import modelo.ServicioActividad;
import modelo.ServicioConsumo;

public class MainConsola {

    public static void main(String[] args) {
        ConexionDB conexionDB = new ConexionDB();
        conexionDB.crearBaseYTablas();

        ControladorSistema sistema = ControladorSistema.getInstancia();
        sistema.registrarObserver(new NotificadorSistema());

        Scanner scanner = new Scanner(System.in);
        System.out.println("--- SISTEMA HOTELERO (MODO CONSOLA) ---");

        System.out.print("Usuario: ");
        String user = scanner.nextLine();
        System.out.print("Password: ");
        String pass = scanner.nextLine();

        if (sistema.login(user, pass)) {
            System.out.println("\nLogin exitoso. Bienvenido: " + sistema.getUsuarioActual().getUsername());
            System.out.println("Rol: " + sistema.getUsuarioActual().getRol());

            boolean salir = false;
            while (!salir) {
                System.out.println("\n--- MENÚ PRINCIPAL ---");
                System.out.println("1. Registrar nuevo Huésped");
                System.out.println("2. Ver habitaciones disponibles");
                System.out.println("3. Crear Reserva (Control de Overbooking)");
                System.out.println("4. Check-in");
                System.out.println("5. Check-out");
                System.out.println("6. Registrar Pago");
                System.out.println("7. Probar Servicios (Composite)");
                System.out.println("8. Salir");
                System.out.print("Seleccione opción: ");

                try {
                    int opcion = Integer.parseInt(scanner.nextLine());

                    if (opcion == 1) {
                        System.out.println("\n-- FORMULARIO DE REGISTRO --");
                        System.out.print("Nombre: ");
                        String nom = scanner.nextLine();
                        System.out.print("Apellido: ");
                        String ape = scanner.nextLine();
                        System.out.print("DNI: ");
                        int dni = Integer.parseInt(scanner.nextLine());
                        System.out.print("Teléfono: ");
                        int tel = Integer.parseInt(scanner.nextLine());
                        System.out.print("Email: ");
                        String mail = scanner.nextLine();

                        System.out.println("Fecha de Nacimiento:");
                        System.out.print("Año (ej: 1990): ");
                        int anio = Integer.parseInt(scanner.nextLine());
                        System.out.print("Mes (1-12): ");
                        int mes = Integer.parseInt(scanner.nextLine());
                        System.out.print("Día (1-31): ");
                        int dia = Integer.parseInt(scanner.nextLine());

                        LocalDate fechaNac = LocalDate.of(anio, mes, dia);
                        String resultado = sistema.registrarHuesped(nom, ape, dni, tel, mail, fechaNac);
                        System.out.println("\n>>> " + resultado);

                    } else if (opcion == 2) {
                        System.out.println("\n-- HABITACIONES DISPONIBLES --");
                        List<Habitacion> disponibles = sistema.obtenerHabitacionesDisponibles();
                        if (disponibles.isEmpty()) {
                            System.out.println("No hay habitaciones libres en este momento.");
                        } else {
                            for (Habitacion h : disponibles) {
                                System.out.println("Habitación N°: " + h.getNumero() + " | Piso: " + h.getPiso());
                            }
                        }

                    } else if (opcion == 3) {
                        System.out.println("\n-- NUEVA RESERVA --");
                        System.out.print("DNI del Huésped: ");
                        int dni = Integer.parseInt(scanner.nextLine());
                        System.out.print("Número de Habitación: ");
                        int numHab = Integer.parseInt(scanner.nextLine());

                        System.out.println("Fecha de Entrada:");
                        System.out.print("Año: "); int aI = Integer.parseInt(scanner.nextLine());
                        System.out.print("Mes: "); int mI = Integer.parseInt(scanner.nextLine());
                        System.out.print("Día: "); int dI = Integer.parseInt(scanner.nextLine());
                        LocalDate checkIn = LocalDate.of(aI, mI, dI);

                        System.out.println("Fecha de Salida:");
                        System.out.print("Año: "); int aS = Integer.parseInt(scanner.nextLine());
                        System.out.print("Mes: "); int mS = Integer.parseInt(scanner.nextLine());
                        System.out.print("Día: "); int dS = Integer.parseInt(scanner.nextLine());
                        LocalDate checkOut = LocalDate.of(aS, mS, dS);

                        String res = sistema.realizarReserva(dni, numHab, checkIn, checkOut);
                        System.out.println("\n>>> RESULTADO: " + res);

                    } else if (opcion == 4) {
                        System.out.println("\n-- PROCESO DE CHECK-IN --");
                        System.out.print("Ingrese DNI del huésped: ");
                        int dni = Integer.parseInt(scanner.nextLine());
                        String resultado = sistema.procesarCheckIn(dni);
                        System.out.println("\n>>> " + resultado);

                    } else if (opcion == 5) {
                        System.out.println("\n-- PROCESO DE CHECK-OUT --");
                        System.out.print("Ingrese DNI del huésped: ");
                        int dni = Integer.parseInt(scanner.nextLine());
                        String resultado = sistema.procesarCheckOut(dni);
                        System.out.println("\n>>> " + resultado);

                    } else if (opcion == 6) {
                        System.out.println("\n-- REGISTRAR PAGO --");
                        Pago pago = new Pago();
                        System.out.print("Monto: ");
                        double monto = Double.parseDouble(scanner.nextLine());
                        pago.setMonto(monto);

                        System.out.println("Seleccione medio de pago:");
                        System.out.println("1. Tarjeta | 2. Efectivo | 3. Transferencia");
                        int medio = Integer.parseInt(scanner.nextLine());

                        if (medio == 1) pago.setMedioPago(new PagoTarjeta());
                        else if (medio == 2) pago.setMedioPago(new PagoEfectivo());
                        else pago.setMedioPago(new PagoTransferencia());

                        pago.registrarPago();
                        System.out.println("Estado final del pago: " + pago.getEstado());

                    } else if (opcion == 7) {
                        System.out.println("\n-- PRUEBA DE SERVICIOS (COMPOSITE) --");
                        PaqueteServicios paquete = new PaqueteServicios("Pack Relax & Bar");
                        paquete.agregarServicio(new ServicioConsumo(1, "Minibar Premium", 2500));
                        paquete.agregarServicio(new ServicioActividad(2, "Sesión de Masajes", 5000));

                        System.out.println("Contenido del Paquete: " + paquete.getNombre());
                        System.out.println("Precio total calculado: $" + paquete.calcularPrecio());

                    } else if (opcion == 8) {
                        salir = true;
                    } else {
                        System.out.println("Opción no válida.");
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Error: Formato numérico incorrecto.");
                } catch (DateTimeException e) {
                    System.err.println("Error: La fecha ingresada no es válida.");
                } catch (Exception e) {
                    System.err.println("Error inesperado: " + e.getMessage());
                }
            }
        } else {
            System.out.println("Acceso denegado. Credenciales incorrectas.");
        }
        scanner.close();
        System.out.println("Saliendo del sistema...");
    }
}