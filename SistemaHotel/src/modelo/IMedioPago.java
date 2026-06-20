package modelo;

public interface IMedioPago {
    boolean procesarPago(double monto);
    boolean validar();
}