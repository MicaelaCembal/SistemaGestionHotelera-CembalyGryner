import baseDatos.ConexionDB;
import interfaz.VentanaLogin;
import interfaz.VentanaPrincipal;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        ConexionDB conexionDB = new ConexionDB();
        conexionDB.crearBaseYTablas();

        SwingUtilities.invokeLater(() -> {

            JFrame frame = new JFrame("Sistema de Gestión Hotelera");

            VentanaLogin login = new VentanaLogin();

            frame.setContentPane(login.getPanelInicio());

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            frame.setSize(800, 600);

            frame.setLocationRelativeTo(null);

            frame.setVisible(true);

        });

    }

}