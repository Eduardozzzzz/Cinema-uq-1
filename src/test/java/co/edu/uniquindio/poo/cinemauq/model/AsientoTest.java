package co.edu.uniquindio.poo.cinemauq.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AsientoTest {
    @Test
    @DisplayName("El asiento guarda su código, su fila y su número")
    public void debeGuardarLosDatosDelAsiento() {
        Asiento asiento = new Asiento("A1", "A", 1);

        assertEquals("A1", asiento.getCodigo());
        assertEquals("A", asiento.getFila());
        assertEquals(1, asiento.getNumero());
    }

    @Test
    @DisplayName("El asiento nace DISPONIBLE")
    public void elAsientoDebeIniciarDisponible() {
        Asiento asiento = new Asiento("A1", "A", 1);

        assertEquals(EstadoAsiento.DISPONIBLE, asiento.getEstado());
    }

    @Test
    @DisplayName("Un asiento se puede ocupar y volver a liberar")
    public void debeCambiarElEstadoDelAsiento() {
        Asiento asiento = new Asiento("A1", "A", 1);

        asiento.setEstado(EstadoAsiento.OCUPADO);
        assertEquals(EstadoAsiento.OCUPADO, asiento.getEstado());

        asiento.setEstado(EstadoAsiento.DISPONIBLE);
        assertEquals(EstadoAsiento.DISPONIBLE, asiento.getEstado());
    }
}
