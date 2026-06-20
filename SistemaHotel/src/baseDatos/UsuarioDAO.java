package baseDatos;

import modelo.Usuario;
import modelo.RolUsuario;
import java.sql.*;

public class UsuarioDAO {
    private ConexionDB conexionDB = new ConexionDB();

    public Usuario autenticar(String username, String password) {
        String sql = "SELECT * FROM usuario WHERE username = ? AND password = ?";
        try (Connection conn = conexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Usuario(
                        rs.getInt("idUsuario"),
                        rs.getString("username"),
                        rs.getString("password"),
                        RolUsuario.valueOf(rs.getString("rol"))
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}