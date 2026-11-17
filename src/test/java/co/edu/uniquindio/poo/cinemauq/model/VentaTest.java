package co.edu.uniquindio.poo.cinemauq.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class VentaTest {
    private Asiento asientoOcupado;

    private Venta ventaDePrueba() {
        Pelicula pelicula = new Pelicula("P1", "Pelicula 1", "Accion", 120, Clasificacion.TODOS);
        Sala sala = new Sala("S1", "Sala 1", 20);
        Funcion funcion = new Funcion("F1", pelicula, sala, LocalDate.of(2026, 11, 16), LocalTime.of(14, 30), 15000);
        Cliente cliente = new Cliente("1", "Cliente 1", TipoCliente.GENERAL);
        asientoOcupado = sala.buscarAsiento("A1");
        asientoOcupado.setEstado(EstadoAsiento.OCUPADO);
        return new Venta("V1", cliente, funcion, asientoOcupado, LocalDateTime.of(2026, 11, 16, 10, 0), 15000);
    }

    @Test
    @DisplayName("La venta nace en estado ACTIVA")
    public void laVentaDebeIniciarActiva() {
        Venta venta = ventaDePrueba();

        assertEquals(EstadoVenta.ACTIVA, venta.getEstado());
    }

    @Test
    @DisplayName("La venta guarda todos sus datos")
    public void debeGuardarLosDatosDeLaVenta() {
        Venta venta = ventaDePrueba();

        assertEquals("V1", venta.getCodigo());
        assertEquals("Cliente 1", venta.getCliente().getNombre());
        assertEquals("F1", venta.getFuncion().getCodigo());
        assertEquals("A1", venta.getAsiento().getCodigo());
        assertEquals(15000.0, venta.getValorPagado(), 0.001);
        assertNotNull(venta.getFechaHora());
    }

    @Test
    @DisplayName("Cancelar cambia el estado y deja el asiento disponible")
    public void cancelarDebeCambiarElEstadoYLiberarElAsiento() {
        Venta venta = ventaDePrueba();

        assertTrue(venta.cancelar());
        assertEquals(EstadoVenta.CANCELADA, venta.getEstado());
        assertEquals(EstadoAsiento.DISPONIBLE, asientoOcupado.getEstado());
    }

    @Test
    @DisplayName("Una venta no se puede cancelar dos veces")
    public void noDebeCancelarDosVecesLaMismaVenta() {
        Venta venta = ventaDePrueba();
        venta.cancelar();

        assertFalse(venta.cancelar());
        assertEquals(EstadoVenta.CANCELADA, venta.getEstado());
    }
}
