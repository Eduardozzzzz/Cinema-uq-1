package co.edu.uniquindio.poo.cinemauq.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ClienteTest {
    @Test
    @DisplayName("El cliente guarda su identificación, su nombre y su tipo")
    public void debeGuardarLosDatosDelCliente() {
        Cliente cliente = new Cliente("1", "Cliente 1", TipoCliente.GENERAL);

        assertEquals("1", cliente.getIdentificacion());
        assertEquals("Cliente 1", cliente.getNombre());
        assertEquals(TipoCliente.GENERAL, cliente.getTipoCliente());
    }

    @Test
    @DisplayName("El cliente hereda de Persona")
    public void elClienteDebeSerUnaPersona() {
        Cliente cliente = new Cliente("1", "Cliente 1", TipoCliente.GENERAL);

        assertTrue(cliente instanceof Persona);
    }

    @Test
    @DisplayName("Cada tipo de cliente tiene su propio precio")
    public void cadaTipoDeClienteDebeTenerSuPrecio() {
        assertEquals(15000.0, TipoCliente.GENERAL.getPrecio(), 0.001);
        assertEquals(12000.0, TipoCliente.ESTUDIANTE.getPrecio(), 0.001);
        assertEquals(10000.0, TipoCliente.NIÑO.getPrecio(), 0.001);
        assertEquals(11000.0, TipoCliente.ADULTO_MAYOR.getPrecio(), 0.001);
    }

    @Test
    @DisplayName("Se puede cambiar el tipo de cliente")
    public void debeCambiarElTipoDeCliente() {
        Cliente cliente = new Cliente("1", "Cliente 1", TipoCliente.GENERAL);

        cliente.setTipoCliente(TipoCliente.ESTUDIANTE);

        assertEquals(TipoCliente.ESTUDIANTE, cliente.getTipoCliente());
        assertEquals(12000.0, cliente.getTipoCliente().getPrecio(), 0.001);
    }
}
