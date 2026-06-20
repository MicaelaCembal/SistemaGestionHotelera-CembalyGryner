import controlador.ControladorSistema;
import modelo.Huesped;
import baseDatos.ConexionDB;
import java.util.Scanner;

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
            System.out.println("¡Bienvenido " + sistema.getUsuarioActual().getUsername() + "!");

            System.out.println("1. Registrar Huesped");
            System.out.println("2. Ver habitaciones disponibles");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();

            if (opcion == 1) {
                System.out.println("Registro de nuevo huésped:");
                // sistema.registrarHuesped(...) etc
                System.out.println("Huésped registrado con éxito");
            }
        } else {
            System.out.println("Error: Credenciales incorrectas.");
        }

        scanner.close();
    }
}