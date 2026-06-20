package baseDatos;

import modelo.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class ReservaDAO {
    private ConexionDB conexionDB = new ConexionDB();

    public boolean insertar(Reserva reserva) {
        String sql = "INSERT INTO reserva (idHuesped, idHabitacion, idPromocion, fechaCheckin, fechaCheckout, costoTotal, estado) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = conexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, reserva.getHuesped().getIdHuesped());
            ps.setInt(2, reserva.getHabitacion().getIdHabitacion());

            if (reserva.getPromocion() != null) {
                ps.setInt(3, reserva.getPromocion().getIdPromocion());
            } else {
                ps.setNull(3, Types.INTEGER);
            }

            ps.setDate(4, Date.valueOf(reserva.getFechaCheckin().toLocalDate()));
            ps.setDate(5, Date.valueOf(reserva.getFechaCheckout().toLocalDate()));

            ps.setDouble(6, reserva.getCostoTotal());
            ps.setString(7, reserva.getEstado().toString());

            int filas = ps.executeUpdate();
            if (filas > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) reserva.setIdReserva(rs.getInt(1));
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar reserva: " + e.getMessage());
        }
        return false;
    }

    public Reserva buscarReservaPendientePorDni(int dni) {
        String sql = """
            SELECT r.*, h.numero, h.piso, hu.nombre, hu.apellido 
            FROM reserva r 
            JOIN huesped hu ON r.idHuesped = hu.idHuesped 
            JOIN habitacion h ON r.idHabitacion = h.idHabitacion
            WHERE hu.dni = ? AND r.estado = 'PENDIENTE'
            ORDER BY r.fechaCheckin ASC LIMIT 1
        """;

        try (Connection conn = conexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, dni);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Reserva r = new Reserva();
                r.setIdReserva(rs.getInt("idReserva"));
                r.setCostoTotal(rs.getDouble("costoTotal"));
                r.setEstado(EstadoReserva.valueOf(rs.getString("estado")));

                Habitacion hab = new Habitacion();
                hab.setIdHabitacion(rs.getInt("idHabitacion"));
                hab.setNumero(rs.getInt("numero"));
                hab.setPiso(rs.getInt("piso"));
                r.setHabitacion(hab);

                Huesped hue = new Huesped();
                hue.setIdHuesped(rs.getInt("idHuesped"));
                hue.setNombre(rs.getString("nombre"));
                hue.setApellido(rs.getString("apellido"));
                r.setHuesped(hue);

                return r;
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar reserva por DNI: " + e.getMessage());
        }
        return null;
    }

    public List<Reserva> listarReservasActivas() {
        List<Reserva> lista = new ArrayList<>();
        String sql = "SELECT * FROM reserva WHERE estado IN ('PENDIENTE', 'CONFIRMADA')";

        try (Connection conn = conexionDB.conectar();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Reserva r = new Reserva();
                r.setIdReserva(rs.getInt("idReserva"));
                r.setEstado(EstadoReserva.valueOf(rs.getString("estado")));
                lista.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public void actualizarEstado(int idReserva, String nuevoEstado) {
        String sql = "UPDATE reserva SET estado = ? WHERE idReserva = ?";
        try (Connection conn = conexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nuevoEstado);
            ps.setInt(2, idReserva);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Reserva buscarReservaPorDni(int dni) {

        String sql = """
        SELECT r.*, h.numero, h.piso, hu.nombre, hu.apellido
        FROM reserva r
        JOIN huesped hu ON r.idHuesped = hu.idHuesped
        JOIN habitacion h ON r.idHabitacion = h.idHabitacion
        WHERE hu.dni = ?
        ORDER BY r.idReserva DESC
        LIMIT 1
    """;

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
                h.setPiso(rs.getInt("piso"));

                r.setHabitacion(h);

                return r;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}