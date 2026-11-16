package co.edu.uniquindio.poo.cinemauq.model;

public class Cliente extends Persona {
    private TipoCliente tipoCliente;

    public Cliente(String identificacion, String nombre, TipoCliente tipoCliente) {
        super(identificacion, nombre);
        this.tipoCliente = tipoCliente;
    }

    public TipoCliente getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(TipoCliente tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public String toString() {
        return super.toString() + " " + tipoCliente;
    }
}
