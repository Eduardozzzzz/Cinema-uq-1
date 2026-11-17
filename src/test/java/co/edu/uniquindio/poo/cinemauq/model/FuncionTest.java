package co.edu.uniquindio.poo.cinemauq.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class FuncionTest {
    private final LocalDate FECHA = LocalDate.of(2026, 11, 16);
    private final LocalTime HORA = LocalTime.of(14, 30);

    private Funcion funcionDePrueba() {
        Pelicula pelicula = new Pelicula("P1", "Pelicula 1", "Accion", 120, Clasificacion.TODOS);
        Sala sala = new Sala("S1", "Sala 1", 20);
        return new Funcion("F1", pelicula, sala, FECHA, HORA, 15000);
    }

    @Test
    @DisplayName("La función nace en estado PROGRAMADA")
    public void laFuncionDebeIniciarProgramada() {
        Funcion funcion = funcionDePrueba();

        assertEquals(EstadoFuncion.PROGRAMADA, funcion.getEstado());
    }

    @Test
    @DisplayName("La función guarda todos sus datos")
    public void debeGuardarLosDatosDeLaFuncion() {
        Funcion funcion = funcionDePrueba();

        assertEquals("F1", funcion.getCodigo());
        assertEquals("Pelicula 1", funcion.getPelicula().getNombre());
        assertEquals("S1", funcion.getSala().getCodigo());
        assertEquals(FECHA, funcion.getFecha());
        assertEquals(HORA, funcion.getHora());
        assertEquals(15000.0, funcion.getValorBase(), 0.001);
    }

    @Test
    @DisplayName("Al inicio todos los asientos de la sala están disponibles")
    public void debeContarTodosLosAsientosDisponiblesAlInicio() {
        Funcion funcion = funcionDePrueba();

        assertEquals(20, funcion.getCantidadAsientos());
        assertEquals(20, funcion.getCantidadAsientosDisponibles());
        assertEquals(0, funcion.getCantidadAsientosOcupados());
    }

    @Test
    @DisplayName("Ocupar un asiento baja los disponibles y sube los ocupados")
    public void debeDescontarElAsientoCuandoSeOcupa() {
        Funcion funcion = funcionDePrueba();
        Asiento asiento = funcion.getSala().buscarAsiento("A1");

        asiento.setEstado(EstadoAsiento.OCUPADO);

        assertEquals(19, funcion.getCantidadAsientosDisponibles());
        assertEquals(1, funcion.getCantidadAsientosOcupados());
        assertEquals(20, funcion.getCantidadAsientos());
    }

    @Test
    @DisplayName("La lista de asientos disponibles excluye los ocupados")
    public void debeListarSoloLosAsientosDisponibles() {
        Funcion funcion = funcionDePrueba();
        funcion.getSala().buscarAsiento("A1").setEstado(EstadoAsiento.OCUPADO);
        funcion.getSala().buscarAsiento("A2").setEstado(EstadoAsiento.OCUPADO);

        assertEquals(18, funcion.getAsientosDisponibles().size());
        for (Asiento asiento : funcion.getAsientosDisponibles()) {
            assertEquals(EstadoAsiento.DISPONIBLE, asiento.getEstado());
        }
    }

    @Test
    @DisplayName("Se puede cambiar el estado de la función")
    public void debeCambiarElEstadoDeLaFuncion() {
        Funcion funcion = funcionDePrueba();

        funcion.setEstado(EstadoFuncion.CANCELADA);

        assertEquals(EstadoFuncion.CANCELADA, funcion.getEstado());
        assertNotEquals(EstadoFuncion.PROGRAMADA, funcion.getEstado());
    }
}
