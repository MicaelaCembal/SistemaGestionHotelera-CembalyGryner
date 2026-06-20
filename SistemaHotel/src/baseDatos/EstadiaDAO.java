package baseDatos;

import modelo.Estadia;
import java.sql.*;

public class EstadiaDAO {
    private ConexionDB conexionDB = new ConexionDB();

    public boolean insertar(Estadia estadia) {
        String sql = "INSERT INTO estadia (idReserva, fechaIngresoReal, cantidadInt, subtotalServicios) VALUES (?, ?, ?, ?)";
        try (Connection conn = conexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, estadia.getIdReserva());
            ps.setTimestamp(2, Timestamp.valueOf(estadia.getFechaIngresoReal()));
            ps.setInt(3, 1); // Por defecto 1 persona
            ps.setDouble(4, 0.0);

            int filas = ps.executeUpdate();
            if (filas > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) estadia.setIdEstadia(rs.getInt(1));
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}