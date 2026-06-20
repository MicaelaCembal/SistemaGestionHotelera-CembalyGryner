package baseDatos;

import modelo.Huesped;
import java.sql.*;

public class HuespedDAO {
    private ConexionDB conexionDB = new ConexionDB();

    public boolean insertar(Huesped huesped) {
        String sql = "INSERT INTO huesped (nombre, apellido, dni, telefono, email, categoria, cantVisitas, fechaNacimiento) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = conexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, huesped.getNombre());
            ps.setString(2, huesped.getApellido());
            ps.setInt(3, huesped.getDni());
            ps.setInt(4, huesped.getTelefono());
            ps.setString(5, huesped.getEmail());
            ps.setString(6, huesped.getCategoria().toString());
            ps.setInt(7, 0);

            // Convertimos LocalDate de Java a Date de SQL (solo fecha)
            if (huesped.getFechaNacimiento() != null) {
                ps.setDate(8, Date.valueOf(huesped.getFechaNacimiento()));
            } else {
                ps.setNull(8, Types.DATE);
            }

            int filas = ps.executeUpdate();
            if (filas > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) huesped.setIdHuesped(rs.getInt(1));
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error SQL al insertar: " + e.getMessage());
        }
        return false;
    }

    public boolean existeDni(int dni) {
        String sql = "SELECT COUNT(*) FROM huesped WHERE dni = ?";
        try (Connection conn = conexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, dni);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1) > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }
}