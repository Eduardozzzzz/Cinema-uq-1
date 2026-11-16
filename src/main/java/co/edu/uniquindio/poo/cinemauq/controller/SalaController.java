package co.edu.uniquindio.poo.cinemauq.controller;

import co.edu.uniquindio.poo.cinemauq.model.Cine;
import co.edu.uniquindio.poo.cinemauq.model.Sala;

import java.util.List;

public class SalaController {

    Cine cine;

    public SalaController(Cine cine) {
        this.cine = cine;
    }

    public boolean crearSala(Sala sala) {
        return cine.registrarSala(sala);
    }

    public List<Sala> obtenerListaSalas() {
        return cine.getSalas();
    }

    public Sala buscarSala(String codigo) {
        return cine.buscarSala(codigo);
    }

    public boolean actualizarSala(Sala sala) {
        return cine.actualizarSala(sala);
    }

    public boolean deshabilitarSala(String codigo) {
        return cine.deshabilitarSala(codigo);
    }
}
