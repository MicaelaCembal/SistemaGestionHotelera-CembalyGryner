package interfaz;

import controlador.ControladorSistema;
import javax.swing.*;
import java.time.LocalDate;

public class VentanaReserva {
    private JPanel panelReserva;
    private JLabel textFieldHabitacion;
    private JLabel textFieldAnioSalida;
    private JLabel textFieldMesSalida;
    private JLabel textFieldDiaSalida;
    private JButton btnReservar;
    private JLabel textFieldDniHuesped;
    private JTextField textFieldDiaSalidaReserva;
    private JTextField textFieldMesSalidareserva;
    private JTextField textFieldAnioSalidaReserva;
    private JTextField textFieldNHabitacion;
    private JTextField textFielddnihuespedReserva;
    private JLabel lblResultado;

    public VentanaReserva() {
        btnReservar.addActionListener(e -> {
            try {
                int dni        = Integer.parseInt(textFielddnihuespedReserva.getText().trim());
                int numHab     = Integer.parseInt(textFieldNHabitacion.getText().trim());
                int anio       = Integer.parseInt(textFieldAnioSalidaReserva.getText().trim());
                int mes        = Integer.parseInt(textFieldMesSalidareserva.getText().trim());
                int dia        = Integer.parseInt(textFieldDiaSalidaReserva.getText().trim());

                LocalDate checkIn  = LocalDate.now();
                LocalDate checkOut = LocalDate.of(anio, mes, dia);

                if (checkOut.isBefore(checkIn) || checkOut.isEqual(checkIn)) {
                    lblResultado.setText("Error: la fecha de salida debe ser posterior a hoy.");
                    return;
                }

                String resultado = ControladorSistema.getInstancia().realizarReserva(dni, numHab, checkIn, checkOut);

                lblResultado.setText("✔ " + resultado);
                limpiarCampos();

            } catch (NumberFormatException ex) {
                lblResultado.setText("Error: todos los campos deben ser números.");
            } catch (Exception ex) {
                lblResultado.setText("Error: " + ex.getMessage());
            }
        });
    }

    private void limpiarCampos() {
        textFielddnihuespedReserva.setText("");
        textFieldNHabitacion.setText("");
        textFieldAnioSalidaReserva.setText("");
        textFieldMesSalidareserva.setText("");
        textFieldDiaSalidaReserva.setText("");
    }

    public JPanel getPanelReserva() {
        return panelReserva;
    }
}