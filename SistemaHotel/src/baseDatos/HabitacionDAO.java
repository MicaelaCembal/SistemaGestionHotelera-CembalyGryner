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
                // Aquí podrías cargar el modelo.TipoHabitacion con otro DAO si fuera necesario
                lista.add(h);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }
}