import java.sql.*;

public class UsuarioDAO {
    
    public Usuario validarUsuario(String username, String password) {
        String sql = "SELECT * FROM Usuario WHERE username = ? AND password = ?";
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(
                        rs.getString("username"),
                        rs.getString("password"),
                        RolUsuario.valueOf(rs.getString("rol"))
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en DAO al validar usuario: " + e.getMessage());
        }
        return null; // Si no lo encuentra o hay error
    }

    // CrearUsuario, etc.
}