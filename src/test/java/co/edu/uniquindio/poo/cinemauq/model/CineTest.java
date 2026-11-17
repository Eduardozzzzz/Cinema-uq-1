package co.edu.uniquindio.poo.cinemauq.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CineTest {
    private final LocalDate HOY = LocalDate.of(2026, 11, 16);

    private Cine cineDePrueba() {
        Cine cine = new Cine("Cinema", "123", "Calle 1");
        Pelicula pelicula1 = new Pelicula("P1", "Pelicula 1", "Accion", 120, Clasificacion.TODOS);
        Pelicula pelicula2 = new Pelicula("P2", "Pelicula 2", "Accion", 90, Clasificacion.TODOS);
        Sala sala1 = new Sala("S1", "Sala 1", 20);
        Sala sala2 = new Sala("S2", "Sala 2", 10);
        Funcion f1 = new Funcion("F1", pelicula1, sala1, HOY, LocalTime.of(14, 30), 15000);
        Funcion f2 = new Funcion("F2", pelicula2, sala2, HOY, LocalTime.of(16, 0), 12000);
        Cliente cliente1 = new Cliente("1", "Cliente 1", TipoCliente.GENERAL);
        Cliente cliente2 = new Cliente("2", "Cliente 2", TipoCliente.ESTUDIANTE);
        cine.registrarPelicula(pelicula1);
        cine.registrarPelicula(pelicula2);
        cine.registrarSala(sala1);
        cine.registrarSala(sala2);
        cine.registrarFuncion(f1);
        cine.registrarFuncion(f2);
        cine.registrarCliente(cliente1);
        cine.registrarCliente(cliente2);
        return cine;
    }

    @Test
    @DisplayName("Registrar una película nueva devuelve true y queda guardada")
    public void debeRegistrarUnaPeliculaNueva() {
        Cine cine = new Cine("Cinema", "123", "Calle 1");
        Pelicula pelicula = new Pelicula("P1", "Pelicula 1", "Accion", 120, Clasificacion.TODOS);

        assertTrue(cine.registrarPelicula(pelicula));
        assertNotNull(cine.buscarPelicula("P1"));
        assertEquals("Pelicula 1", cine.buscarPelicula("P1").getNombre());
        assertEquals(1, cine.getPeliculas().size());
    }

    @Test
    @DisplayName("No se puede registrar dos veces la misma película")
    public void noDebeRegistrarUnaPeliculaConCodigoRepetido() {
        Cine cine = cineDePrueba();
        Pelicula repetida = new Pelicula("P1", "Otra pelicula", "Accion", 100, Clasificacion.TODOS);

        assertFalse(cine.registrarPelicula(repetida));
        assertEquals(2, cine.getPeliculas().size());
    }

    @Test
    @DisplayName("La búsqueda de película no distingue mayúsculas")
    public void debeBuscarUnaPeliculaSinImportarMayusculas() {
        Cine cine = cineDePrueba();

        assertNotNull(cine.buscarPelicula("p1"));
        assertEquals("P1", cine.buscarPelicula("p1").getCodigo());
    }

    @Test
    @DisplayName("Buscar una película que no existe devuelve null")
    public void debeRetornarNullCuandoLaPeliculaNoExiste() {
        Cine cine = cineDePrueba();

        assertNull(cine.buscarPelicula("P99"));
    }

    @Test
    @DisplayName("Deshabilitar una película la saca de cartelera sin borrarla")
    public void debeDeshabilitarUnaPelicula() {
        Cine cine = cineDePrueba();

        assertTrue(cine.deshabilitarPelicula("P1"));
        assertEquals(EstadoPelicula.NO_DISPONIBLE, cine.buscarPelicula("P1").getEstado());
        assertEquals(2, cine.getPeliculas().size());
        assertIterableEquals(List.of(cine.buscarPelicula("P2")), cine.consultarPeliculasDisponibles());
    }

    @Test
    @DisplayName("Deshabilitar una película inexistente devuelve false")
    public void noDebeDeshabilitarUnaPeliculaQueNoExiste() {
        Cine cine = cineDePrueba();

        assertFalse(cine.deshabilitarPelicula("P99"));
    }

    @Test
    @DisplayName("Registrar una sala crea sus asientos automáticamente")
    public void debeRegistrarUnaSalaYGenerarSusAsientos() {
        Cine cine = new Cine("Cinema", "123", "Calle 1");
        Sala sala = new Sala("S1", "Sala 1", 20);

        assertTrue(cine.registrarSala(sala));
        assertEquals(20, cine.buscarSala("S1").getAsientos().size());
    }

    @Test
    @DisplayName("No se puede programar una función en una sala fuera de servicio")
    public void noDebeRegistrarUnaFuncionEnUnaSalaFueraDeServicio() {
        Cine cine = cineDePrueba();
        cine.deshabilitarSala("S2");
        Sala sala2 = cine.buscarSala("S2");
        Pelicula pelicula = cine.buscarPelicula("P2");
        Funcion nueva = new Funcion("F9", pelicula, sala2, HOY, LocalTime.of(20, 0), 12000);

        assertThrows(SalaNoDisponibleException.class, () -> cine.registrarFuncion(nueva));
        assertNull(cine.buscarFuncion("F9"));
    }

    @Test
    @DisplayName("No se puede programar dos veces la función con el mismo código")
    public void noDebeRegistrarUnaFuncionConCodigoRepetido() {
        Cine cine = cineDePrueba();
        Pelicula pelicula = cine.buscarPelicula("P1");
        Sala sala = cine.buscarSala("S1");
        Funcion repetida = new Funcion("F1", pelicula, sala, HOY, LocalTime.of(21, 0), 15000);

        assertFalse(cine.registrarFuncion(repetida));
    }

    @Test
    @DisplayName("Eliminar una función la quita de la lista")
    public void debeEliminarUnaFuncion() {
        Cine cine = cineDePrueba();

        assertTrue(cine.eliminarFuncion("F2"));
        assertNull(cine.buscarFuncion("F2"));
        assertFalse(cine.eliminarFuncion("F2"));
    }

    @Test
    @DisplayName("Vender una entrada genera la venta y ocupa el asiento")
    public void debeVenderUnaEntradaYMarcarElAsientoOcupado() {
        Cine cine = cineDePrueba();
        Cliente cliente1 = cine.buscarCliente("1");

        Venta venta = cine.venderEntrada(cliente1, "F1", "A1");

        assertEquals("V1", venta.getCodigo());
        assertEquals(EstadoVenta.ACTIVA, venta.getEstado());
        assertEquals(15000.0, venta.getValorPagado(), 0.001);
        assertEquals(EstadoAsiento.OCUPADO, cine.buscarSala("S1").buscarAsiento("A1").getEstado());
        assertEquals(1, cine.getVentas().size());
    }

    @Test
    @DisplayName("El valor pagado depende del tipo de cliente")
    public void debeCobrarSegunElTipoDeCliente() {
        Cine cine = cineDePrueba();
        Cliente cliente2 = cine.buscarCliente("2");

        Venta venta = cine.venderEntrada(cliente2, "F1", "A2");

        assertEquals(12000.0, venta.getValorPagado(), 0.001);
    }

    @Test
    @DisplayName("No se puede vender para una función que no existe")
    public void noDebeVenderSiLaFuncionNoExiste() {
        Cine cine = cineDePrueba();
        Cliente cliente1 = cine.buscarCliente("1");

        assertThrows(FuncionNoEncontradaException.class, () -> cine.venderEntrada(cliente1, "F99", "A1"));
        assertEquals(0, cine.getVentas().size());
    }

    @Test
    @DisplayName("No se puede vender un asiento que ya está ocupado")
    public void noDebeVenderUnAsientoOcupado() {
        Cine cine = cineDePrueba();
        Cliente cliente1 = cine.buscarCliente("1");
        Cliente cliente2 = cine.buscarCliente("2");
        cine.venderEntrada(cliente1, "F1", "A1");

        assertThrows(AsientoOcupadoException.class, () -> cine.venderEntrada(cliente2, "F1", "A1"));
        assertEquals(1, cine.getVentas().size());
    }

    @Test
    @DisplayName("No se puede vender un asiento que no existe en la sala")
    public void noDebeVenderUnAsientoQueNoExiste() {
        Cine cine = cineDePrueba();
        Cliente cliente1 = cine.buscarCliente("1");

        assertThrows(IllegalArgumentException.class, () -> cine.venderEntrada(cliente1, "F1", "Z99"));
    }

    @Test
    @DisplayName("No se puede vender si la función no está programada")
    public void noDebeVenderSiLaFuncionNoEstaProgramada() {
        Cine cine = cineDePrueba();
        Cliente cliente1 = cine.buscarCliente("1");
        cine.buscarFuncion("F1").setEstado(EstadoFuncion.FINALIZADA);

        assertThrows(IllegalStateException.class, () -> cine.venderEntrada(cliente1, "F1", "A1"));
    }

    @Test
    @DisplayName("No se puede vender si la sala ya no tiene asientos libres")
    public void noDebeVenderSiNoHayAsientosDisponibles() {
        Cine cine = new Cine("Cinema", "123", "Calle 1");
        Pelicula pelicula = new Pelicula("P1", "Pelicula 1", "Accion", 120, Clasificacion.TODOS);
        Sala sala = new Sala("S1", "Sala 1", 1);
        Funcion funcion = new Funcion("F1", pelicula, sala, HOY, LocalTime.of(14, 30), 15000);
        Cliente cliente1 = new Cliente("1", "Cliente 1", TipoCliente.GENERAL);
        cine.registrarPelicula(pelicula);
        cine.registrarSala(sala);
        cine.registrarFuncion(funcion);
        cine.registrarCliente(cliente1);
        cine.venderEntrada(cliente1, "F1", "A1");

        assertThrows(SinAsientosDisponiblesException.class, () -> cine.venderEntrada(cliente1, "F1", "A1"));
    }

    @Test
    @DisplayName("Los códigos de venta se generan de forma consecutiva")
    public void debeGenerarCodigosDeVentaConsecutivos() {
        Cine cine = cineDePrueba();
        Cliente cliente1 = cine.buscarCliente("1");

        Venta v1 = cine.venderEntrada(cliente1, "F1", "A1");
        Venta v2 = cine.venderEntrada(cliente1, "F1", "A2");
        Venta v3 = cine.venderEntrada(cliente1, "F1", "A3");

        assertEquals("V1", v1.getCodigo());
        assertEquals("V2", v2.getCodigo());
        assertEquals("V3", v3.getCodigo());
    }

    @Test
    @DisplayName("Cancelar una venta libera el asiento")
    public void debeCancelarUnaVentaYLiberarElAsiento() {
        Cine cine = cineDePrueba();
        Cliente cliente1 = cine.buscarCliente("1");
        Venta venta = cine.venderEntrada(cliente1, "F1", "A1");

        assertTrue(cine.cancelarVenta(venta.getCodigo()));
        assertEquals(EstadoVenta.CANCELADA, venta.getEstado());
        assertEquals(EstadoAsiento.DISPONIBLE, cine.buscarSala("S1").buscarAsiento("A1").getEstado());
    }

    @Test
    @DisplayName("No se puede cancelar una venta que no existe")
    public void noDebeCancelarUnaVentaQueNoExiste() {
        Cine cine = cineDePrueba();

        assertThrows(VentaNoEncontradaException.class, () -> cine.cancelarVenta("V99"));
    }

    @Test
    @DisplayName("No se puede cancelar dos veces la misma venta")
    public void noDebeCancelarDosVecesLaMismaVenta() {
        Cine cine = cineDePrueba();
        Cliente cliente1 = cine.buscarCliente("1");
        Venta venta = cine.venderEntrada(cliente1, "F1", "A1");
        cine.cancelarVenta(venta.getCodigo());

        assertThrows(IllegalStateException.class, () -> cine.cancelarVenta(venta.getCodigo()));
    }

    @Test
    @DisplayName("Consultar funciones programadas excluye las canceladas")
    public void debeConsultarSoloLasFuncionesProgramadas() {
        Cine cine = cineDePrueba();

        assertEquals(2, cine.consultarFuncionesProgramadas().size());
        cine.buscarFuncion("F2").setEstado(EstadoFuncion.CANCELADA);
        assertEquals(1, cine.consultarFuncionesProgramadas().size());
    }

    @Test
    @DisplayName("Consultar funciones por fecha devuelve solo las de ese día")
    public void debeConsultarFuncionesPorFecha() {
        Cine cine = cineDePrueba();

        assertEquals(2, cine.consultarFuncionesPorFecha(HOY).size());
        assertEquals(0, cine.consultarFuncionesPorFecha(HOY.plusDays(1)).size());
    }

    @Test
    @DisplayName("Consultar asientos disponibles descuenta los vendidos")
    public void debeConsultarLosAsientosDisponiblesDeUnaFuncion() {
        Cine cine = cineDePrueba();
        Cliente cliente1 = cine.buscarCliente("1");

        assertEquals(20, cine.consultarAsientosDisponibles("F1").size());
        cine.venderEntrada(cliente1, "F1", "A1");
        assertEquals(19, cine.consultarAsientosDisponibles("F1").size());
    }

    @Test
    @DisplayName("Consultar asientos de una función inexistente lanza excepción")
    public void noDebeConsultarAsientosDeUnaFuncionInexistente() {
        Cine cine = cineDePrueba();

        assertThrows(FuncionNoEncontradaException.class, () -> cine.consultarAsientosDisponibles("F99"));
    }

    @Test
    @DisplayName("El reporte de entradas cuenta solo las ventas activas")
    public void debeContarSoloLasEntradasDeVentasActivas() {
        Cine cine = cineDePrueba();
        Cliente cliente1 = cine.buscarCliente("1");
        Venta v1 = cine.venderEntrada(cliente1, "F1", "A1");
        cine.venderEntrada(cliente1, "F1", "A2");

        assertEquals(2, cine.totalEntradasVendidas());
        cine.cancelarVenta(v1.getCodigo());
        assertEquals(1, cine.totalEntradasVendidas());
    }

    @Test
    @DisplayName("El reporte de ingresos suma solo las ventas activas")
    public void debeSumarSoloLosIngresosDeVentasActivas() {
        Cine cine = cineDePrueba();
        Cliente cliente1 = cine.buscarCliente("1");
        Cliente cliente2 = cine.buscarCliente("2");
        Venta v1 = cine.venderEntrada(cliente1, "F1", "A1");
        cine.venderEntrada(cliente2, "F1", "A2");

        assertEquals(27000.0, cine.totalIngresos(), 0.001);
        cine.cancelarVenta(v1.getCodigo());
        assertEquals(12000.0, cine.totalIngresos(), 0.001);
    }

    @Test
    @DisplayName("La película más vendida es la que tiene más entradas activas")
    public void debeEncontrarLaPeliculaMasVendida() {
        Cine cine = cineDePrueba();
        Cliente cliente1 = cine.buscarCliente("1");
        cine.venderEntrada(cliente1, "F1", "A1");
        cine.venderEntrada(cliente1, "F1", "A2");
        cine.venderEntrada(cliente1, "F2", "A1");

        assertEquals("P1", cine.peliculaMasVendida().getCodigo());
        assertEquals(2, cine.entradasVendidasPelicula(cine.buscarPelicula("P1")));
        assertEquals(1, cine.entradasVendidasPelicula(cine.buscarPelicula("P2")));
    }

    @Test
    @DisplayName("Sin ventas no hay película más vendida")
    public void debeRetornarNullSiNoHayVentas() {
        Cine cine = cineDePrueba();

        assertNull(cine.peliculaMasVendida());
        assertEquals(0, cine.totalEntradasVendidas());
        assertEquals(0.0, cine.totalIngresos(), 0.001);
    }

    @Test
    @DisplayName("El reporte de ocupación muestra total, disponibles y ocupados")
    public void debeGenerarElReporteDeOcupacion() {
        Cine cine = cineDePrueba();
        Cliente cliente1 = cine.buscarCliente("1");
        cine.venderEntrada(cliente1, "F1", "A1");

        assertEquals("Total: 20    Disponibles: 19    Ocupados: 1", cine.reporteOcupacion("F1"));
    }

    @Test
    @DisplayName("El reporte de ocupación de una función inexistente lanza excepción")
    public void noDebeGenerarReporteDeUnaFuncionInexistente() {
        Cine cine = cineDePrueba();

        assertThrows(FuncionNoEncontradaException.class, () -> cine.reporteOcupacion("F99"));
    }
}
