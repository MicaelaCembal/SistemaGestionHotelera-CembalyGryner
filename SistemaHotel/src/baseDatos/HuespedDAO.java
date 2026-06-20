package baseDatos;

import modelo.Huesped;
import modelo.CategoriaHuesped;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HuespedDAO {
    private ConexionDB conexionDB = new ConexionDB();

    public void insertar(Huesped h) {
        String sql = "INSERT INTO huesped (nombre, apellido, dni, telefono, email, categoria) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = conexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, h.getNombre());
            ps.setString(2, h.getApellido());
            ps.setInt(3, h.getDni());
            ps.setInt(4, h.getTelefono());
            ps.setString(5, h.getEmail());
            ps.setString(6, h.getCategoria().toString());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) h.setIdHuesped(rs.getInt(1));
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public Huesped buscarPorDni(int dni) {
        String sql = "SELECT * FROM huesped WHERE dni = ?";
        try (Connection conn = conexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, dni);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Huesped h = new Huesped();
                h.setIdHuesped(rs.getInt("idHuesped"));
                h.setNombre(rs.getString("nombre"));
                h.setApellido(rs.getString("apellido"));
                h.setDni(rs.getInt("dni"));
                h.setCategoria(CategoriaHuesped.valueOf(rs.getString("categoria")));
                return h;
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }
}