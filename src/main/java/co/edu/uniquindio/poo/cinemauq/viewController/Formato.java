package co.edu.uniquindio.poo.cinemauq.viewController;

public class Formato {

    public static String dinero(double valor) {
        return "$" + String.format("%,.0f", valor).replace(",", ".");
    }
}
