package co.edu.uniquindio.poo.cinemauq.controller;

import co.edu.uniquindio.poo.cinemauq.model.Cine;
import co.edu.uniquindio.poo.cinemauq.model.Funcion;
import co.edu.uniquindio.poo.cinemauq.model.Pelicula;

import java.time.LocalDate;
import java.util.List;

public class ReporteController {

    Cine cine;

    public ReporteController(Cine cine) {
        this.cine = cine;
    }

    public int totalEntradasVendidas() {
        return cine.totalEntradasVendidas();
    }

    public double totalIngresos() {
        return cine.totalIngresos();
    }

    public String peliculaMasVendida() {
        Pelicula pelicula = cine.peliculaMasVendida();
        if (pelicula == null) {
            return "Sin ventas registradas";
        }
        return pelicula.getNombre() + "  (" + cine.entradasVendidasPelicula(pelicula) + " entradas)";
    }

    public String reporteOcupacion(String codigoFuncion) {
        return cine.reporteOcupacion(codigoFuncion);
    }

    public List<Funcion> funcionesPorFecha(LocalDate fecha) {
        return cine.consultarFuncionesPorFecha(fecha);
    }
}
