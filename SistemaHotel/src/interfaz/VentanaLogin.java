package interfaz;

import controlador.ControladorSistema; // Importante importar el controlador
import javax.swing.*;

public class VentanaLogin {

    private JPanel panelInicio;
    private JPasswordField txtPassword;
    private JTextField txtUsuario;
    private JButton btnIngresar;

    public VentanaLogin() {

        btnIngresar.addActionListener(e -> {

            String usuario = txtUsuario.getText();
            String password = String.valueOf(txtPassword.getPassword());

            if (ControladorSistema.getInstancia().login(usuario, password)) {

                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panelInicio);
                frame.dispose();

                JFrame nuevaVentana = new JFrame("Sistema Hotelero");
                VentanaPrincipal principal = new VentanaPrincipal();

                nuevaVentana.setContentPane(principal.getPanelPrincipal());
                nuevaVentana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                nuevaVentana.setSize(800, 600);
                nuevaVentana.setLocationRelativeTo(null);
                nuevaVentana.setVisible(true);

            } else {
                JOptionPane.showMessageDialog(
                        null,
                        "Usuario o contraseña incorrectos.\nVerifique los datos e intente nuevamente.",
                        "Error de inicio de sesión",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });
    }

    public JPanel getPanelInicio() {
        return panelInicio;
    }
}