package modelo;

public class PagoEfectivo implements IMedioPago {

    @Override
    public boolean procesarPago(double monto) {
        return monto > 0;
    }

    @Override
    public boolean validar() {
        return true;
    }
}