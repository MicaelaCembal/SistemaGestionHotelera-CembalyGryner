public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    //USUARIOS DE PRUEBA. ESTO IRIA EN UNA BASE DE DATOS.
    List<Usuario> listaUsuarios = new ArrayList<>();
    listaUsuarios.add(new Usuario("admin", "1234", RolUsuario.ADMIN));
    listaUsuarios.add(new Usuario("recepcion1", "hotel2024", RolUsuario.RECEPCIONISTA));

    System.out.println("=== SISTEMA DE GESTIÓN HOTELERA ===");

    System.out.print("Ingrese su nombre de usuario: ");
    String userDigitado = sc.nextLine();
    System.out.print("Ingrese su contraseña: ");
    String passDigitado = sc.nextLine();

    Usuario usuarioEncontrado = null;
    for (Usuario u : listaUsuarios) {
        if (u.getUsername().equals(userDigitado)) {
            usuarioEncontrado = u;
            break;
        }
    }

    if (usuarioEncontrado != null) {
        if (usuarioEncontrado.login(passDigitado)) {
            System.out.println("\n-------------------------------------------");
            System.out.println("Bienvenido/a: " + ControladorSistema.getInstancia().getUsuarioActual().getUsername());
            System.out.println("Rol: " + ControladorSistema.getInstancia().getUsuarioActual().getRol());
            System.out.println("-------------------------------------------");
        }
    } else {
        System.out.println("Error: El usuario no existe.");
    }

    sc.close();
}