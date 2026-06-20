package interfaz;

import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal {
    private JButton btnReserva;
    private JButton btnPago;
    private JButton btnHuesped;
    private JButton btnHabitaciones;
    private JPanel panelPrincipal;
    private JLabel titulo;

    public VentanaPrincipal() {

        btnHuesped.addActionListener(e -> {
            JFrame frame = new JFrame("Registrar Huésped");
            VentanaHuesped ventana = new VentanaHuesped();
            frame.setContentPane(ventana.getPanelHuesped());
            frame.setSize(500, 450);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setVisible(true);
        });

        btnReserva.addActionListener(e -> {
            JFrame frame = new JFrame("Nueva Reserva");
            VentanaReserva ventana = new VentanaReserva();
            frame.setContentPane(ventana.getPanelReserva());
            frame.setSize(500, 400);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setVisible(true);
        });

        btnHabitaciones.addActionListener(e -> {
            JFrame frame = new JFrame("Habitaciones Disponibles");
            VentanaHabitaciones ventana = new VentanaHabitaciones();
            frame.setContentPane(ventana.getPanelHabitaciones());
            frame.setSize(500, 400);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setVisible(true);
        });

        btnPago.addActionListener(e -> {
            JFrame frame = new JFrame("Registrar Pago");
            VentanaPago ventana = new VentanaPago();
            frame.setContentPane(ventana.getPanelPago());
            frame.setSize(500, 350);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setVisible(true);
        });
    }

    public Container getPanelPrincipal() {
        return panelPrincipal;
    }
}