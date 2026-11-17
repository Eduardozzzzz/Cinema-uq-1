package co.edu.uniquindio.poo.cinemauq.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PeliculaTest {
    @Test
    @DisplayName("La película guarda todos sus datos")
    public void debeGuardarLosDatosDeLaPelicula() {
        Pelicula pelicula = new Pelicula("P1", "Pelicula 1", "Accion", 120, Clasificacion.TODOS);

        assertEquals("P1", pelicula.getCodigo());
        assertEquals("Pelicula 1", pelicula.getNombre());
        assertEquals("Accion", pelicula.getGenero());
        assertEquals(120, pelicula.getDuracion());
        assertEquals(Clasificacion.TODOS, pelicula.getClasificacion());
    }

    @Test
    @DisplayName("La película nace DISPONIBLE")
    public void laPeliculaDebeIniciarDisponible() {
        Pelicula pelicula = new Pelicula("P1", "Pelicula 1", "Accion", 120, Clasificacion.TODOS);

        assertEquals(EstadoPelicula.DISPONIBLE, pelicula.getEstado());
    }

    @Test
    @DisplayName("Una película se puede deshabilitar y volver a habilitar")
    public void debeCambiarElEstadoDeLaPelicula() {
        Pelicula pelicula = new Pelicula("P1", "Pelicula 1", "Accion", 120, Clasificacion.TODOS);

        pelicula.setEstado(EstadoPelicula.NO_DISPONIBLE);
        assertEquals(EstadoPelicula.NO_DISPONIBLE, pelicula.getEstado());

        pelicula.setEstado(EstadoPelicula.DISPONIBLE);
        assertEquals(EstadoPelicula.DISPONIBLE, pelicula.getEstado());
    }

    @Test
    @DisplayName("Se pueden modificar los datos de la película")
    public void debeActualizarLosDatosDeLaPelicula() {
        Pelicula pelicula = new Pelicula("P1", "Pelicula 1", "Accion", 120, Clasificacion.TODOS);

        pelicula.setNombre("Pelicula 1 modificada");
        pelicula.setGenero("Terror");
        pelicula.setDuracion(130);
        pelicula.setClasificacion(Clasificacion.MAYORES_18);

        assertEquals("Pelicula 1 modificada", pelicula.getNombre());
        assertEquals("Terror", pelicula.getGenero());
        assertEquals(130, pelicula.getDuracion());
        assertEquals(Clasificacion.MAYORES_18, pelicula.getClasificacion());
    }
}
