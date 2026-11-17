package co.edu.uniquindio.poo.cinemauq.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SalaTest {
    @Test
    @DisplayName("La sala genera un asiento por cada puesto de su capacidad")
    public void debeGenerarLosAsientosSegunLaCapacidad() {
        Sala sala = new Sala("S1", "Sala 1", 20);

        assertEquals(20, sala.getAsientos().size());
        assertEquals(20, sala.getCapacidad());
    }

    @Test
    @DisplayName("Los asientos se nombran por fila y número: A1..A10, B1..")
    public void debeNombrarLosAsientosPorFilaYNumero() {
        Sala sala = new Sala("S1", "Sala 1", 25);

        assertEquals("A1", sala.getAsientos().get(0).getCodigo());
        assertEquals("A10", sala.getAsientos().get(9).getCodigo());
        assertEquals("B1", sala.getAsientos().get(10).getCodigo());
        assertEquals("C5", sala.getAsientos().get(24).getCodigo());
        assertEquals("C", sala.getAsientos().get(24).getFila());
        assertEquals(5, sala.getAsientos().get(24).getNumero());
    }

    @Test
    @DisplayName("Todos los asientos nacen disponibles")
    public void losAsientosDebenIniciarDisponibles() {
        Sala sala = new Sala("S1", "Sala 1", 20);

        for (Asiento asiento : sala.getAsientos()) {
            assertEquals(EstadoAsiento.DISPONIBLE, asiento.getEstado());
        }
    }

    @Test
    @DisplayName("La sala nace en estado DISPONIBLE")
    public void laSalaDebeIniciarDisponible() {
        Sala sala = new Sala("S1", "Sala 1", 20);

        assertEquals(EstadoSala.DISPONIBLE, sala.getEstado());
    }

    @Test
    @DisplayName("Se puede buscar un asiento por su código")
    public void debeBuscarUnAsientoExistente() {
        Sala sala = new Sala("S1", "Sala 1", 20);

        assertNotNull(sala.buscarAsiento("A1"));
        assertNotNull(sala.buscarAsiento("b1"));
        assertEquals("A1", sala.buscarAsiento("a1").getCodigo());
    }

    @Test
    @DisplayName("Buscar un asiento inexistente devuelve null")
    public void debeRetornarNullSiElAsientoNoExiste() {
        Sala sala = new Sala("S1", "Sala 1", 20);

        assertNull(sala.buscarAsiento("Z99"));
    }

    @Test
    @DisplayName("Al cambiar la capacidad se pueden regenerar los asientos")
    public void debeRegenerarLosAsientosAlCambiarLaCapacidad() {
        Sala sala = new Sala("S1", "Sala 1", 20);

        sala.setCapacidad(5);
        sala.generarAsientos();

        assertEquals(5, sala.getAsientos().size());
        assertEquals("A5", sala.getAsientos().get(4).getCodigo());
        assertNull(sala.buscarAsiento("A6"));
    }

    @Test
    @DisplayName("Una sala con capacidad cero no tiene asientos")
    public void unaSalaSinCapacidadNoDebeTenerAsientos() {
        Sala sala = new Sala("S1", "Sala 1", 0);

        assertEquals(0, sala.getAsientos().size());
    }
}
