package controlador;

public class NotificadorSistema implements IObserver {
    @Override
    public void notificar(String mensaje) {
        // En una app real, esto podría enviar un Email o escribir en un Log de auditoría
        if (mensaje.contains("OVERBOOKING")) {
            System.err.println("\n[SISTEMA DE SEGURIDAD - ALERTA]: " + mensaje);
        } else {
            System.out.println("\n[NOTIFICACIÓN]: " + mensaje);
        }
    }
}