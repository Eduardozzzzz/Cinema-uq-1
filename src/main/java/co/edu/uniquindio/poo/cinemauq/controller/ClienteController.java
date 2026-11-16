package co.edu.uniquindio.poo.cinemauq.controller;

import co.edu.uniquindio.poo.cinemauq.model.Cine;
import co.edu.uniquindio.poo.cinemauq.model.Cliente;

import java.util.List;

public class ClienteController {

    Cine cine;

    public ClienteController(Cine cine) {
        this.cine = cine;
    }

    public boolean crearCliente(Cliente cliente) {
        return cine.registrarCliente(cliente);
    }

    public List<Cliente> obtenerListaClientes() {
        return cine.getClientes();
    }

    public Cliente buscarCliente(String identificacion) {
        return cine.buscarCliente(identificacion);
    }

    public boolean actualizarCliente(Cliente cliente) {
        return cine.actualizarCliente(cliente);
    }
}
