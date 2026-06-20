import controlador.ControladorSistema;
import baseDatos.ConexionDB;
import modelo.Habitacion;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.DateTimeException;
import java.util.List;

// Este Main es para testeo rápido por consola.

public class MainConsola {

    public static void main(String[] args) {
        ConexionDB conexionDB = new ConexionDB();
        conexionDB.crearBaseYTablas();

        ControladorSistema sistema = ControladorSistema.getInstancia();
        Scanner scanner = new Scanner(System.in);
        System.out.println("--- SISTEMA HOTELERO (MODO CONSOLA) ---");

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
                System.out.println("2. Ver habitaciones disponibles");
                System.out.println("3. Salir");
                System.out.print("Seleccione opción: ");

                try {
                    int opcion = Integer.parseInt(scanner.nextLine());

                    if (opcion == 1) {
                        System.out.println("\n-- FORMULARIO DE REGISTRO --");
                        System.out.print("Nombre: ");
                        String nom = scanner.nextLine();
                        System.out.print("Apellido: ");
                        String ape = scanner.nextLine();
                        System.out.print("DNI (solo números): ");
                        int dni = Integer.parseInt(scanner.nextLine());
                        System.out.print("Teléfono: ");
                        int tel = Integer.parseInt(scanner.nextLine());
                        System.out.print("Email: ");
                        String mail = scanner.nextLine();

                        // --- Pedir Fecha de Nacimiento ---
                        System.out.println("Fecha de Nacimiento:");
                        System.out.print("Año (ej: 1990): ");
                        int anio = Integer.parseInt(scanner.nextLine());
                        System.out.print("Mes (1-12): ");
                        int mes = Integer.parseInt(scanner.nextLine());
                        System.out.print("Día (1-31): ");
                        int dia = Integer.parseInt(scanner.nextLine());

                        // Validación de fecha inválida (ej. 31 de febrero)
                        LocalDate fechaNac = LocalDate.of(anio, mes, dia);

                        String resultado = sistema.registrarHuesped(nom, ape, dni, tel, mail, fechaNac);
                        System.out.println("\n>>> " + resultado);

                    } else if (opcion == 2) {
                        // todo: ver habitaciones disponibles.

                    } else if (opcion == 3) {
                        salir = true;
                    } else {
                        System.out.println("Opción no válida.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error: Ingrese un número válido para la opción, DNI o fecha.");
                } catch (DateTimeException e) {
                    System.out.println("Error: La fecha ingresada es inválida (verifique mes y día).");
                } catch (Exception e) {
                    System.out.println("Error inesperado: " + e.getMessage());
                }
            }
        } else {
            System.out.println("Credenciales inválidas.");
        }
        scanner.close();
        System.out.println("Programa finalizado.");
    }
}