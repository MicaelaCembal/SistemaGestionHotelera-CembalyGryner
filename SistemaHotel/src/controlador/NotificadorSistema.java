package controlador;

public class NotificadorSistema implements IObserver {
    @Override
    public void notificar(String mensaje) {
        System.out.println("\n[OBSERVER NOTIFICACIÓN]: " + mensaje);
    }
}
