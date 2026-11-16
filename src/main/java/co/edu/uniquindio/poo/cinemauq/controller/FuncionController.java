package co.edu.uniquindio.poo.cinemauq.controller;

import co.edu.uniquindio.poo.cinemauq.model.Asiento;
import co.edu.uniquindio.poo.cinemauq.model.Cine;
import co.edu.uniquindio.poo.cinemauq.model.Funcion;

import java.time.LocalDate;
import java.util.List;

public class FuncionController {

    Cine cine;

    public FuncionController(Cine cine) {
        this.cine = cine;
    }

    public boolean crearFuncion(Funcion funcion) {
        return cine.registrarFuncion(funcion);
    }

    public List<Funcion> obtenerListaFunciones() {
        return cine.getFunciones();
    }

    public Funcion buscarFuncion(String codigo) {
        return cine.buscarFuncion(codigo);
    }

    public boolean actualizarFuncion(Funcion funcion) {
        return cine.actualizarFuncion(funcion);
    }

    public boolean eliminarFuncion(String codigo) {
        return cine.eliminarFuncion(codigo);
    }

    public List<Funcion> obtenerFuncionesProgramadas() {
        return cine.consultarFuncionesProgramadas();
    }

    public List<Funcion> obtenerFuncionesPorFecha(LocalDate fecha) {
        return cine.consultarFuncionesPorFecha(fecha);
    }

    public List<Asiento> obtenerAsientosDisponibles(String codigoFuncion) {
        return cine.consultarAsientosDisponibles(codigoFuncion);
    }
}
