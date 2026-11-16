package co.edu.uniquindio.poo.cinemauq.controller;

import co.edu.uniquindio.poo.cinemauq.model.Cine;
import co.edu.uniquindio.poo.cinemauq.model.Cliente;
import co.edu.uniquindio.poo.cinemauq.model.Venta;

import java.util.List;

public class VentaController {

    Cine cine;

    public VentaController(Cine cine) {
        this.cine = cine;
    }

    public boolean crearVenta(Venta venta) {
        return cine.registrarVenta(venta);
    }

    public List<Venta> obtenerListaVentas() {
        return cine.getVentas();
    }

    public Venta buscarVenta(String codigo) {
        return cine.buscarVenta(codigo);
    }

    public boolean actualizarVenta(Venta venta) {
        return cine.actualizarVenta(venta);
    }

    public Venta venderEntrada(Cliente cliente, String codigoFuncion, String codigoAsiento) {
        return cine.venderEntrada(cliente, codigoFuncion, codigoAsiento);
    }

    public boolean cancelarVenta(String codigo) {
        return cine.cancelarVenta(codigo);
    }
}
