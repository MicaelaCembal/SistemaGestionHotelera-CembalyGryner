import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
    
        DatabaseManager.inicializarBaseDeDatos();

        ControladorSistema sistema = new ControladorSistema();
        Scanner sc = new Scanner(System.in);

        System.out.println("=== LOGIN HOTEL ===");
        System.out.print("Usuario: ");
        String user = sc.nextLine();
        System.out.print("Contraseña: ");
        String pass = sc.nextLine();

        if (sistema.login(user, pass)) {
            System.out.println("Bienvenido, " + sistema.getUsuarioActual().getUsername());
            System.out.println("Rol: " + sistema.getUsuarioActual().getRol());
            //
        } else {
            System.out.println("Credenciales incorrectas. Saliendo...");
        }
        
        sc.close();
    }
}