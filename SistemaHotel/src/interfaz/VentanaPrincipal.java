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

            JOptionPane.showMessageDialog(
                    null,
                    "Botón funcionando"
            );

        });

    }

    public Container getPanelPrincipal() {
        return panelPrincipal;
    }
}
