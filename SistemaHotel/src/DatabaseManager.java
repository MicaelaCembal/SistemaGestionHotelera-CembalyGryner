import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DatabaseManager {
    private static String URL;
    private static String DB_NAME;
    private static String USER;
    private static String PASS;

    static {
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream("config.properties"));
            URL = prop.getProperty("db.url");
            DB_NAME = prop.getProperty("db.name");
            USER = prop.getProperty("db.user");
            PASS = prop.getProperty("db.password");
        } catch (IOException e) {
            System.err.println("Error cargando configuración: " + e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL + DB_NAME, USER, PASS);
    }

    public static void inicializarBaseDeDatos() {
        try (Connection tempConn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = tempConn.createStatement()) {
  
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DB_NAME);
            System.out.println("Base de datos verificada/creada.");

            try (Connection conn = getConnection(); Statement dbStmt = conn.createStatement()) {

                String sqlTabla = "CREATE TABLE IF NOT EXISTS Usuario (" +
                        "username VARCHAR(50) PRIMARY KEY," +
                        "password VARCHAR(50) NOT NULL," +
                        "rol VARCHAR(20) NOT NULL)";
                dbStmt.executeUpdate(sqlTabla);

                ResultSet rs = dbStmt.executeQuery("SELECT COUNT(*) FROM Usuario");
                if (rs.next() && rs.getInt(1) == 0) {
                    dbStmt.executeUpdate("INSERT INTO Usuario VALUES ('admin', '123', 'ADMIN')"); // La primera vez que se ejecuta el código, se crea un admin
                    System.out.println("Usuario 'admin' creado por defecto.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error de SQL: " + e.getMessage());
        }
    }
}