package interfaz;

import controlador.ControladorSistema;
import modelo.Habitacion;
import javax.swing.*;
import java.util.List;

public class VentanaHabitaciones {
    private JPanel panelHabitaciones;
    private JList listaHabitaciones;
    private JButton btnRefrescar;

    public VentanaHabitaciones() {
        cargarHabitaciones();

        btnRefrescar.addActionListener(e -> cargarHabitaciones());
    }

    private void cargarHabitaciones() {
        List<Habitacion> disponibles = ControladorSistema.getInstancia().obtenerHabitacionesDisponibles();

        DefaultListModel<String> modelo = new DefaultListModel<>();

        if (disponibles.isEmpty()) {
            modelo.addElement("No hay habitaciones disponibles.");
        } else {
            for (Habitacion h : disponibles) {
                modelo.addElement("Habitación N°" + h.getNumero() + " | Piso: " + h.getPiso());
            }
        }

        listaHabitaciones.setModel(modelo);
    }

    public JPanel getPanelHabitaciones() {
        return panelHabitaciones;
    }
}