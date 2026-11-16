package co.edu.uniquindio.poo.cinemauq.model;

public enum TipoCliente {
    GENERAL(15000),
    ESTUDIANTE(12000),
    NIÑO(10000),
    ADULTO_MAYOR(11000);

    private double precio;

    TipoCliente(double precio) {
        this.precio = precio;
    }

    public double getPrecio() {
        return precio;
    }
}
