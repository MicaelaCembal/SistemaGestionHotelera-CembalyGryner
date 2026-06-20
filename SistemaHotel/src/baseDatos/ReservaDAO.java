package baseDatos;

import modelo.Reserva;
import java.sql.*;

public class ReservaDAO {
    private ConexionDB conexionDB = new ConexionDB();

    public int crear(Reserva r) {
        String sql = "INSERT INTO reserva (idHuesped, idHabitacion, fechaCheckin, fechaCheckout, costoTotal, estado) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = conexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, r.getHuesped().getIdHuesped());
            ps.setInt(2, r.getHabitacion().getIdHabitacion());
            ps.setTimestamp(3, Timestamp.valueOf(r.getFechaCheckin()));
            ps.setTimestamp(4, Timestamp.valueOf(r.getFechaCheckout()));
            ps.setDouble(5, r.getCostoTotal());
            ps.setString(6, r.getEstado().toString());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return -1;
    }
}