package baseDatos;

import modelo.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HabitacionDAO {
    private ConexionDB conexionDB = new ConexionDB();

    public void actualizarEstado(int idHabitacion, String estado) {
        String sql = "UPDATE habitacion SET estado = ? WHERE idHabitacion = ?";
        try (Connection conn = conexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, estado);
            ps.setInt(2, idHabitacion);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public List<Habitacion> listarDisponibles() {
        List<Habitacion> lista = new ArrayList<>();
        String sql = "SELECT * FROM habitacion WHERE estado = 'DISPONIBLE'";
        try (Connection conn = conexionDB.conectar();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Habitacion h = new Habitacion();
                h.setIdHabitacion(rs.getInt("idHabitacion"));
                h.setNumero(rs.getInt("numero"));
                lista.add(h);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }
    public Habitacion buscarPorNumero(int numero) {
        String sql = "SELECT * FROM habitacion WHERE numero = ?";
        try (Connection conn = conexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, numero);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Habitacion h = new Habitacion();
                h.setIdHabitacion(rs.getInt("idHabitacion"));
                h.setNumero(rs.getInt("numero"));
                h.setPiso(rs.getInt("piso"));
                return h;
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }
}