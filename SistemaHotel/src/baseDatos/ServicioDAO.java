package baseDatos;

import modelo.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicioDAO {
    private ConexionDB conexionDB = new ConexionDB();

    public List<IServicio> listarTodos() {
        List<IServicio> lista = new ArrayList<>();
        String sql = "SELECT * FROM servicio";
        try (Connection conn = conexionDB.conectar();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                String tipo = rs.getString("tipo");
                if (tipo.equals("CONSUMO")) {
                    lista.add(new ServicioConsumo(rs.getInt("idServicio"), rs.getString("nombre"), rs.getDouble("precio")));
                } else {
                    lista.add(new ServicioActividad(rs.getInt("idServicio"), rs.getString("nombre"), rs.getDouble("precio")));
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }
}