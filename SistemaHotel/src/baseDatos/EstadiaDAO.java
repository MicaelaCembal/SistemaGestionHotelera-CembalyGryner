package baseDatos;

import modelo.Estadia;
import modelo.ServicioEstadia;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstadiaDAO {
    private ConexionDB conexionDB = new ConexionDB();

    public boolean insertar(Estadia estadia, double precioBase) {
        String sql = "INSERT INTO estadia (idReserva, fechaIngresoReal, precioBaseHabitacion) VALUES (?, ?, ?)";
        try (Connection conn = conexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, estadia.getIdReserva());
            ps.setTimestamp(2, Timestamp.valueOf(estadia.getFechaIngresoReal()));
            ps.setDouble(3, precioBase);
            if (ps.executeUpdate() > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) estadia.setIdEstadia(rs.getInt(1));
                return true;
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public Estadia buscarActivaPorReserva(int idReserva) {
        String sql = "SELECT * FROM estadia WHERE idReserva = ? AND fechaEgresoReal IS NULL";
        try (Connection conn = conexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idReserva);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Estadia e = new Estadia();
                e.setIdEstadia(rs.getInt("idEstadia"));
                e.setIdReserva(rs.getInt("idReserva"));
                return e;
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public void registrarServicio(int idEstadia, int idServicio, int cant) {
        String sql = "INSERT INTO servicio_estadia (idEstadia, idServicio, cantidad, fecha) VALUES (?, ?, ?, ?)";
        try (Connection conn = conexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idEstadia);
            ps.setInt(2, idServicio);
            ps.setInt(3, cant);
            ps.setTimestamp(4, Timestamp.valueOf(java.time.LocalDateTime.now()));
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public double calcularTotalServicios(int idEstadia) {
        String sql = "SELECT SUM(s.precio * se.cantidad) FROM servicio_estadia se " +
                "JOIN servicio s ON se.idServicio = s.idServicio WHERE se.idEstadia = ?";
        try (Connection conn = conexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idEstadia);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getDouble(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    public boolean cerrarEstadia(int idEstadia, double subtotalServicios, double totalFacturado) {
        String sql = "UPDATE estadia SET fechaEgresoReal = ?, subtotalServicios = ?, totalFacturado = ? WHERE idEstadia = ?";
        try (Connection conn = conexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setTimestamp(1, Timestamp.valueOf(java.time.LocalDateTime.now()));
            ps.setDouble(2, subtotalServicios);
            ps.setDouble(3, totalFacturado);
            ps.setInt(4, idEstadia);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }
}