package baseDatos;

import modelo.*;
import java.sql.*;
import java.time.LocalDateTime;

public class ReservaDAO {
    private ConexionDB conexionDB = new ConexionDB();

    public boolean insertar(Reserva reserva) {
        String sql = "INSERT INTO reserva (idHuesped, idHabitacion, idPromocion, fechaCheckin, fechaCheckout, costoTotal, estado) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = conexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, reserva.getHuesped().getIdHuesped());
            ps.setInt(2, reserva.getHabitacion().getIdHabitacion());
            if (reserva.getPromocion() != null) ps.setInt(3, reserva.getPromocion().getIdPromocion());
            else ps.setNull(3, Types.INTEGER);
            ps.setTimestamp(4, Timestamp.valueOf(reserva.getFechaCheckin()));
            ps.setTimestamp(5, Timestamp.valueOf(reserva.getFechaCheckout()));
            ps.setDouble(6, reserva.getCostoTotal());
            ps.setString(7, reserva.getEstado().toString());
            int filas = ps.executeUpdate();
            if (filas > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) reserva.setIdReserva(rs.getInt(1));
                return true;
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public boolean existeSolapamiento(int idHabitacion, LocalDateTime inicio, LocalDateTime fin) {
        // Lógica de solapamiento: (InicioPedido < FinExistente) Y (FinPedido > InicioExistente)
        String sql = "SELECT COUNT(*) FROM reserva WHERE idHabitacion = ? AND estado != 'CANCELADA' " +
                "AND (fechaCheckin < ? AND fechaCheckout > ?)";

        try (Connection conn = conexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idHabitacion);
            ps.setTimestamp(2, Timestamp.valueOf(fin));
            ps.setTimestamp(3, Timestamp.valueOf(inicio));

            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1) > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public Reserva buscarReservaPendientePorDni(int dni) {
        String sql = "SELECT r.*, h.numero, h.piso, hu.nombre, hu.apellido FROM reserva r " +
                "JOIN huesped hu ON r.idHuesped = hu.idHuesped JOIN habitacion h ON r.idHabitacion = h.idHabitacion " +
                "WHERE hu.dni = ? AND r.estado = 'PENDIENTE' ORDER BY r.fechaCheckin ASC LIMIT 1";
        try (Connection conn = conexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, dni);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Reserva r = new Reserva();
                r.setIdReserva(rs.getInt("idReserva"));
                Habitacion hab = new Habitacion();
                hab.setIdHabitacion(rs.getInt("idHabitacion"));
                hab.setNumero(rs.getInt("numero"));
                r.setHabitacion(hab);
                return r;
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public Reserva buscarReservaPorDni(int dni) {
        String sql = "SELECT r.*, h.idHabitacion, h.numero FROM reserva r " +
                "JOIN huesped hu ON r.idHuesped = hu.idHuesped JOIN habitacion h ON r.idHabitacion = h.idHabitacion " +
                "WHERE hu.dni = ? ORDER BY r.idReserva DESC LIMIT 1";
        try (Connection conn = conexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, dni);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Reserva r = new Reserva();
                r.setIdReserva(rs.getInt("idReserva"));
                Habitacion h = new Habitacion();
                h.setIdHabitacion(rs.getInt("idHabitacion"));
                h.setNumero(rs.getInt("numero"));
                r.setHabitacion(h);
                return r;
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }
}