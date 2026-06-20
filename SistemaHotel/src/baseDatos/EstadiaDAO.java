package baseDatos;

import modelo.Estadia;
import java.sql.*;

public class EstadiaDAO {
    private ConexionDB conexionDB = new ConexionDB();

    public boolean insertar(Estadia estadia) {
        String sql = "INSERT INTO estadia (idReserva, fechaIngresoReal, subtotalServicios, totalFacturado) VALUES (?, ?, ?, ?)";
        try (Connection conn = conexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, estadia.getIdReserva());
            ps.setTimestamp(2, Timestamp.valueOf(estadia.getFechaIngresoReal()));
            ps.setDouble(3, 0.0);
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

    public boolean finalizarEstadia(int idReserva, double subtotalServicios, double totalFacturado) {
        String sql = "UPDATE estadia SET fechaEgresoReal = ?, subtotalServicios = ?, totalFacturado = ? WHERE idReserva = ? AND fechaEgresoReal IS NULL";
        try (Connection conn = conexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setTimestamp(1, Timestamp.valueOf(java.time.LocalDateTime.now()));
            ps.setDouble(2, subtotalServicios);
            ps.setDouble(3, totalFacturado);
            ps.setInt(4, idReserva);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}