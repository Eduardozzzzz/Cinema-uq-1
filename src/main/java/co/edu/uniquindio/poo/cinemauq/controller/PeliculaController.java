package co.edu.uniquindio.poo.cinemauq.controller;

import co.edu.uniquindio.poo.cinemauq.model.Cine;
import co.edu.uniquindio.poo.cinemauq.model.Pelicula;

import java.util.List;

public class PeliculaController {

    Cine cine;

    public PeliculaController(Cine cine) {
        this.cine = cine;
    }

    public boolean crearPelicula(Pelicula pelicula) {
        return cine.registrarPelicula(pelicula);
    }

    public List<Pelicula> obtenerListaPeliculas() {
        return cine.getPeliculas();
    }

    public Pelicula buscarPelicula(String codigo) {
        return cine.buscarPelicula(codigo);
    }

    public boolean actualizarPelicula(Pelicula pelicula) {
        return cine.actualizarPelicula(pelicula);
    }

    public boolean deshabilitarPelicula(String codigo) {
        return cine.deshabilitarPelicula(codigo);
    }

    public List<Pelicula> obtenerPeliculasDisponibles() {
        return cine.consultarPeliculasDisponibles();
    }
}
