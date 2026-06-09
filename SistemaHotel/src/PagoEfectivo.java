public class PagoEfectivo implements IMedioPago {
    @Override
    public boolean procesarPago(double monto) {
        // Implementación
        return true;
    }

    @Override
    public boolean validar() {
        // Implementación
        return true;
    }
}