package interfaz;

import controlador.ControladorSistema;
import modelo.*;
import javax.swing.*;

public class VentanaPago {

    private JPanel panelPago;
    private JLabel textFieldMonto;
    private JLabel textFieldMedioPago;
    private JButton btnRegistrar;
    private JLabel textFieldDniPago;
    private JComboBox<String> comboMedioPago;
    private JTextField textFieldMontoPagar;
    private JTextField textFielddnihuespedPago;
    private JLabel lblResultado;

    public VentanaPago() {
        btnRegistrar.addActionListener(e -> {
            try {
                int dni      = Integer.parseInt(textFielddnihuespedPago.getText().trim());
                double monto = Double.parseDouble(textFieldMontoPagar.getText().trim());
                String medio = (String) comboMedioPago.getSelectedItem();

                if (monto <= 0) {
                    lblResultado.setText("Error: el monto debe ser mayor a 0.");
                    return;
                }

                Pago pago = new Pago();
                pago.setMonto(monto);

                switch (medio) {
                    case "Tarjeta"       -> pago.setMedioPago(new PagoTarjeta());
                    case "Efectivo"      -> pago.setMedioPago(new PagoEfectivo());
                    case "Transferencia" -> pago.setMedioPago(new PagoTransferencia());
                }

                pago.registrarPago();
                lblResultado.setText("✔ Pago registrado. Estado: " + pago.getEstado());
                limpiarCampos();

            } catch (NumberFormatException ex) {
                lblResultado.setText("Error: DNI y monto deben ser números.");
            } catch (Exception ex) {
                lblResultado.setText("Error: " + ex.getMessage());
            }
        });
    }

    private void limpiarCampos() {
        textFielddnihuespedPago.setText("");
        textFieldMontoPagar.setText("");
        comboMedioPago.setSelectedIndex(0);
    }

    public JPanel getPanelPago() {
        return panelPago;
    }
}