package interfaz;

import controlador.ControladorSistema;
import javax.swing.*;
import java.time.LocalDate;

public class VentanaHuesped {
    private JPanel panelHuesped;
    private JLabel txtNombre;
    private JLabel txtApellido;
    private JLabel txtDni;
    private JLabel txtTelefono;
    private JLabel txtEmail;
    private JLabel txtAnio;
    private JLabel txtMes;
    private JLabel txtDia;
    private JButton btnRegistrar;
    private JTextField textFieldNombre;
    private JTextField textFieldApellido;
    private JTextField textFieldDni;
    private JTextField textFieldTelefono;
    private JTextField textFieldEmail;
    private JTextField textFieldAnio;
    private JTextField textFieldMes;
    private JTextField textFieldDia;
    private JLabel lblResultado;

    public VentanaHuesped() {
        btnRegistrar.addActionListener(e -> {
            try {
                String nombre   = textFieldNombre.getText().trim();
                String apellido = textFieldApellido.getText().trim();
                int dni         = Integer.parseInt(textFieldDni.getText().trim());
                int telefono    = Integer.parseInt(textFieldTelefono.getText().trim());
                String email    = textFieldEmail.getText().trim();
                int anio        = Integer.parseInt(textFieldAnio.getText().trim());
                int mes         = Integer.parseInt(textFieldMes.getText().trim());
                int dia         = Integer.parseInt(textFieldDia.getText().trim());

                if (nombre.isEmpty() || apellido.isEmpty() || email.isEmpty()) {
                    lblResultado.setText("Por favor completá todos los campos.");
                    return;
                }

                LocalDate fechaNac = LocalDate.of(anio, mes, dia);

                String resultado = ControladorSistema.getInstancia()
                        .registrarHuesped(nombre, apellido, dni, telefono, email, fechaNac);

                lblResultado.setText("✔ " + resultado);
                limpiarCampos();

            } catch (NumberFormatException ex) {
                lblResultado.setText("Error: DNI, teléfono y fecha deben ser números.");
            } catch (Exception ex) {
                lblResultado.setText("Error: " + ex.getMessage());
            }
        });
    }

    private void limpiarCampos() {
        textFieldNombre.setText("");
        textFieldApellido.setText("");
        textFieldDni.setText("");
        textFieldTelefono.setText("");
        textFieldEmail.setText("");
        textFieldAnio.setText("");
        textFieldMes.setText("");
        textFieldDia.setText("");
    }

    public JPanel getPanelHuesped() {
        return panelHuesped;
    }
}