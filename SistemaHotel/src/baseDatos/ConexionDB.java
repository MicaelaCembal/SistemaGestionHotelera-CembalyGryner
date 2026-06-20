package baseDatos;

import java.sql.*;

public class ConexionDB {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "hotel";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "mysql";

    public Connection conectar() {
        Connection conexion = null;
        try {
            Class.forName(DRIVER);
            conexion = DriverManager.getConnection(URL + DB_NAME, USUARIO, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.err.println("Error: no se encontro el driver JDBC.");
        } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos: " + e.getMessage());
        }
        return conexion;
    }

    public void cerrarConexion(Connection conexion) {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexion: " + e.getMessage());
        }
    }

    public void crearBaseYTablas() {
        Connection conexion = null;
        Statement stmt = null;

        try {
            Class.forName(DRIVER);
            conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
            stmt = conexion.createStatement();

            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DB_NAME);
            stmt.executeUpdate("USE " + DB_NAME);

            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS usuario (
                    idUsuario     INT PRIMARY KEY AUTO_INCREMENT,
                    username      VARCHAR(100) NOT NULL UNIQUE,
                    password      VARCHAR(100) NOT NULL,
                    rol           ENUM('ADMIN', 'RECEPCIONISTA') NOT NULL
                )
            """);
            System.out.println("Tabla 'usuario' verificada.");

            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS huesped (
                    idHuesped        INT PRIMARY KEY AUTO_INCREMENT,
                    nombre           VARCHAR(100) NOT NULL,
                    apellido         VARCHAR(100) NOT NULL,
                    dni              INT NOT NULL UNIQUE,
                    telefono         INT,
                    email            VARCHAR(150),
                    categoria        ENUM('REGULAR', 'FRECUENTE', 'VIP') NOT NULL DEFAULT 'REGULAR',
                    cantVisitas      INT DEFAULT 0,
                    fechaNacimiento  DATE
                )
            """);
            System.out.println("Tabla 'huesped' verificada.");

            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS hotel (
                    idHotel    INT PRIMARY KEY AUTO_INCREMENT,
                    nombre     VARCHAR(150) NOT NULL,
                    direccion  VARCHAR(200),
                    ciudad     VARCHAR(100),
                    categoria  INT
                )
            """);
            System.out.println("Tabla 'hotel' verificada.");

            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS tipo_habitacion (
                    idTipoHabitacion  INT PRIMARY KEY AUTO_INCREMENT,
                    nombre            VARCHAR(100) NOT NULL,
                    capacidad         INT NOT NULL,
                    precioBajoNoche   DOUBLE NOT NULL,
                    precioAltoNoche   DOUBLE NOT NULL
                )
            """);
            System.out.println("Tabla 'tipo_habitacion' verificada.");

            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS habitacion (
                    idHabitacion      INT PRIMARY KEY AUTO_INCREMENT,
                    idHotel           INT NOT NULL,
                    numero            INT NOT NULL,
                    piso              INT NOT NULL,
                    idTipoHabitacion  INT NOT NULL,
                    estado            ENUM('DISPONIBLE', 'OCUPADO', 'LIMPIEZA', 'MANTENIMIENTO') NOT NULL DEFAULT 'DISPONIBLE',
                    FOREIGN KEY (idHotel)          REFERENCES hotel(idHotel),
                    FOREIGN KEY (idTipoHabitacion) REFERENCES tipo_habitacion(idTipoHabitacion)
                )
            """);
            System.out.println("Tabla 'habitacion' verificada.");

            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS promocion (
                    idPromocion         INT PRIMARY KEY AUTO_INCREMENT,
                    nombre              VARCHAR(150),
                    porcentajeDescuento DOUBLE,
                    fechaInicio         DATETIME,
                    fechaFin            DATETIME,
                    tipo                ENUM('TEMPORADA_ALTA', 'TEMPORADA_BAJA', 'DESCUENTO_PUNTUAL') NOT NULL
                )
            """);
            System.out.println("Tabla 'promocion' verificada.");

            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS reserva (
                    idReserva       INT PRIMARY KEY AUTO_INCREMENT,
                    idHuesped       INT NOT NULL,
                    idHabitacion    INT NOT NULL,
                    idPromocion     INT,
                    fechaCheckin    DATETIME NOT NULL,
                    fechaCheckout   DATETIME NOT NULL,
                    costoTotal      DOUBLE DEFAULT 0,
                    estado          ENUM('PENDIENTE', 'PAGADO', 'REEMBOLSADO') NOT NULL DEFAULT 'PENDIENTE',
                    FOREIGN KEY (idHuesped)    REFERENCES huesped(idHuesped),
                    FOREIGN KEY (idHabitacion) REFERENCES habitacion(idHabitacion),
                    FOREIGN KEY (idPromocion)  REFERENCES promocion(idPromocion)
                )
            """);
            System.out.println("Tabla 'reserva' verificada.");

            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS estadia (
                    idEstadia           INT PRIMARY KEY AUTO_INCREMENT,
                    idReserva           INT NOT NULL,
                    fechaIngresoReal    DATETIME,
                    fechaEgresoReal     DATETIME,
                    cantidadInt         INT DEFAULT 0,
                    subtotalServicios   DOUBLE DEFAULT 0,
                    FOREIGN KEY (idReserva) REFERENCES reserva(idReserva)
                )
            """);
            System.out.println("Tabla 'estadia' verificada.");

            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS servicio (
                    idServicio  INT PRIMARY KEY AUTO_INCREMENT,
                    nombre      VARCHAR(150) NOT NULL,
                    tipo        ENUM('CONSUMO', 'ACTIVIDAD') NOT NULL,
                    precio      DOUBLE NOT NULL
                )
            """);
            System.out.println("Tabla 'servicio' verificada.");

            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS servicio_estadia (
                    idServicioEstadia  INT PRIMARY KEY AUTO_INCREMENT,
                    idEstadia          INT NOT NULL,
                    idServicio         INT NOT NULL,
                    cantidad           INT NOT NULL DEFAULT 1,
                    fecha              DATETIME NOT NULL,
                    FOREIGN KEY (idEstadia)  REFERENCES estadia(idEstadia),
                    FOREIGN KEY (idServicio) REFERENCES servicio(idServicio)
                )
            """);
            System.out.println("Tabla 'servicio_estadia' verificada.");

            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS pago (
                    idPago      INT PRIMARY KEY AUTO_INCREMENT,
                    idReserva   INT NOT NULL,
                    medioPago   ENUM('TARJETA', 'EFECTIVO', 'TRANSFERENCIA') NOT NULL,
                    monto       DOUBLE NOT NULL,
                    estado      ENUM('PENDIENTE', 'PAGADO', 'REEMBOLSADO') NOT NULL DEFAULT 'PENDIENTE',
                    FOREIGN KEY (idReserva) REFERENCES reserva(idReserva)
                )
            """);
            System.out.println("Tabla 'pago' verificada.");

            // USUARIOS POR DEFECTO
            ResultSet rsUser = stmt.executeQuery("SELECT COUNT(*) FROM usuario");
            if (rsUser.next() && rsUser.getInt(1) == 0) {
                stmt.executeUpdate("INSERT INTO usuario (username, password, rol) VALUES ('admin', '1234', 'ADMIN'), ('recep', '4321', 'RECEPCIONISTA')");
            }

            // HOTEL POR DEFECTO
            ResultSet rsHotel = stmt.executeQuery("SELECT COUNT(*) FROM hotel");
            if (rsHotel.next() && rsHotel.getInt(1) == 0) {
                stmt.executeUpdate("INSERT INTO hotel (nombre, direccion, ciudad, categoria) VALUES ('Grand Hotel Central', 'Av. Siempre Viva 123', 'Buenos Aires', 5)");
            }

            // TIPOS DE HABITACIÓN POR DEFECTO
            ResultSet rsTipo = stmt.executeQuery("SELECT COUNT(*) FROM tipo_habitacion");
            if (rsTipo.next() && rsTipo.getInt(1) == 0) {
                stmt.executeUpdate("INSERT INTO tipo_habitacion (nombre, capacidad, precioBajoNoche, precioAltoNoche) VALUES " +
                        "('Simple', 1, 5000, 7000), " +
                        "('Doble', 2, 8500, 11000), " +
                        "('Suite', 4, 15000, 22000)");
            }

            // HABITACIONES POR DEFECTO
            ResultSet rsHab = stmt.executeQuery("SELECT COUNT(*) FROM habitacion");
            if (rsHab.next() && rsHab.getInt(1) == 0) {
                stmt.executeUpdate("INSERT INTO habitacion (idHotel, numero, piso, idTipoHabitacion, estado) VALUES " +
                        "(1, 101, 1, 1, 'DISPONIBLE'), " +
                        "(1, 102, 1, 1, 'DISPONIBLE'), " +
                        "(1, 201, 2, 2, 'DISPONIBLE'), " +
                        "(1, 202, 2, 2, 'DISPONIBLE'), " +
                        "(1, 301, 3, 3, 'DISPONIBLE')");
            }

            System.out.println("Datos iniciales cargados con éxito.");

        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error al crear la base o tablas: " + e.getMessage());
        } finally {
            try { if (stmt != null) stmt.close(); } catch (SQLException e) {
                System.err.println("Error al cerrar el Statement: " + e.getMessage());
            }
            cerrarConexion(conexion);
        }
    }
}