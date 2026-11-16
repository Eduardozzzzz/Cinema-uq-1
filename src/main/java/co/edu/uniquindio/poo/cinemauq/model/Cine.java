package co.edu.uniquindio.poo.cinemauq.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Cine {

    private String nombre;
    private String nit;
    private String direccion;

    private List<Pelicula> peliculas;
    private List<Sala> salas;
    private List<Funcion> funciones;
    private List<Cliente> clientes;
    private List<Venta> ventas;

    private int consecutivoVenta = 0;

    public Cine(String nombre, String nit, String direccion) {
        this.nombre = nombre;
        this.nit = nit;
        this.direccion = direccion;
        this.peliculas = new ArrayList<>();
        this.salas = new ArrayList<>();
        this.funciones = new ArrayList<>();
        this.clientes = new ArrayList<>();
        this.ventas = new ArrayList<>();
    }

    public boolean registrarPelicula(Pelicula pelicula) {
        if (buscarPelicula(pelicula.getCodigo()) != null) {
            return false;
        }
        peliculas.add(pelicula);
        return true;
    }

    public Pelicula buscarPelicula(String codigo) {
        for (Pelicula pelicula : peliculas) {
            if (pelicula.getCodigo().equalsIgnoreCase(codigo)) {
                return pelicula;
            }
        }
        return null;
    }

    public boolean actualizarPelicula(Pelicula pelicula) {
        for (int i = 0; i < peliculas.size(); i++) {
            if (peliculas.get(i).getCodigo().equalsIgnoreCase(pelicula.getCodigo())) {
                peliculas.set(i, pelicula);
                return true;
            }
        }
        return false;
    }

    public boolean deshabilitarPelicula(String codigo) {
        Pelicula pelicula = buscarPelicula(codigo);
        if (pelicula == null) {
            return false;
        }
        pelicula.setEstado(EstadoPelicula.NO_DISPONIBLE);
        return true;
    }

    public boolean registrarSala(Sala sala) {
        if (buscarSala(sala.getCodigo()) != null) {
            return false;
        }
        salas.add(sala);
        return true;
    }

    public Sala buscarSala(String codigo) {
        for (Sala sala : salas) {
            if (sala.getCodigo().equalsIgnoreCase(codigo)) {
                return sala;
            }
        }
        return null;
    }

    public boolean actualizarSala(Sala sala) {
        for (int i = 0; i < salas.size(); i++) {
            if (salas.get(i).getCodigo().equalsIgnoreCase(sala.getCodigo())) {
                salas.set(i, sala);
                return true;
            }
        }
        return false;
    }

    public boolean deshabilitarSala(String codigo) {
        Sala sala = buscarSala(codigo);
        if (sala == null) {
            return false;
        }
        sala.setEstado(EstadoSala.FUERA_DE_SERVICIO);
        return true;
    }

    public boolean registrarFuncion(Funcion funcion) {
        if (buscarFuncion(funcion.getCodigo()) != null) {
            return false;
        }
        if (funcion.getSala().getEstado() == EstadoSala.FUERA_DE_SERVICIO) {
            throw new SalaNoDisponibleException("La sala " + funcion.getSala().getCodigo() + " está fuera de servicio");
        }
        funciones.add(funcion);
        return true;
    }

    public Funcion buscarFuncion(String codigo) {
        for (Funcion funcion : funciones) {
            if (funcion.getCodigo().equalsIgnoreCase(codigo)) {
                return funcion;
            }
        }
        return null;
    }

    public boolean actualizarFuncion(Funcion funcion) {
        for (int i = 0; i < funciones.size(); i++) {
            if (funciones.get(i).getCodigo().equalsIgnoreCase(funcion.getCodigo())) {
                funciones.set(i, funcion);
                return true;
            }
        }
        return false;
    }

    public boolean eliminarFuncion(String codigo) {
        Funcion funcion = buscarFuncion(codigo);
        if (funcion == null) {
            return false;
        }
        return funciones.remove(funcion);
    }

    public boolean registrarCliente(Cliente cliente) {
        if (buscarCliente(cliente.getIdentificacion()) != null) {
            return false;
        }
        clientes.add(cliente);
        return true;
    }

    public Cliente buscarCliente(String identificacion) {
        for (Cliente cliente : clientes) {
            if (cliente.getIdentificacion().equalsIgnoreCase(identificacion)) {
                return cliente;
            }
        }
        return null;
    }

    public boolean actualizarCliente(Cliente cliente) {
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getIdentificacion().equalsIgnoreCase(cliente.getIdentificacion())) {
                clientes.set(i, cliente);
                return true;
            }
        }
        return false;
    }

    public boolean registrarVenta(Venta venta) {
        if (buscarVenta(venta.getCodigo()) != null) {
            return false;
        }
        ventas.add(venta);
        return true;
    }

    public Venta buscarVenta(String codigo) {
        for (Venta venta : ventas) {
            if (venta.getCodigo().equalsIgnoreCase(codigo)) {
                return venta;
            }
        }
        return null;
    }

    public boolean actualizarVenta(Venta venta) {
        for (int i = 0; i < ventas.size(); i++) {
            if (ventas.get(i).getCodigo().equalsIgnoreCase(venta.getCodigo())) {
                ventas.set(i, venta);
                return true;
            }
        }
        return false;
    }

    public Venta venderEntrada(Cliente cliente, String codigoFuncion, String codigoAsiento) {
        Funcion funcion = buscarFuncion(codigoFuncion);
        if (funcion == null) {
            throw new FuncionNoEncontradaException("La función " + codigoFuncion + " no existe");
        }
        if (funcion.getEstado() != EstadoFuncion.PROGRAMADA) {
            throw new IllegalStateException("La función " + codigoFuncion + " no está programada");
        }
        if (funcion.getCantidadAsientosDisponibles() == 0) {
            throw new SinAsientosDisponiblesException("La función " + codigoFuncion + " no tiene asientos disponibles");
        }
        Asiento asiento = funcion.getSala().buscarAsiento(codigoAsiento);
        if (asiento == null) {
            throw new IllegalArgumentException("El asiento " + codigoAsiento + " no existe en la sala " + funcion.getSala().getCodigo());
        }
        if (asiento.getEstado() == EstadoAsiento.OCUPADO) {
            throw new AsientoOcupadoException("El asiento " + codigoAsiento + " ya está ocupado");
        }
        Venta venta = new Venta(generarCodigoVenta(), cliente, funcion, asiento, LocalDateTime.now(), cliente.getTipoCliente().getPrecio());
        asiento.setEstado(EstadoAsiento.OCUPADO);
        ventas.add(venta);
        return venta;
    }

    public String generarCodigoVenta() {
        String codigo;
        do {
            consecutivoVenta++;
            codigo = "V" + consecutivoVenta;
        } while (buscarVenta(codigo) != null);
        return codigo;
    }

    public boolean cancelarVenta(String codigo) {
        Venta venta = buscarVenta(codigo);
        if (venta == null) {
            throw new VentaNoEncontradaException("La venta " + codigo + " no existe");
        }
        if (!venta.cancelar()) {
            throw new IllegalStateException("La venta " + codigo + " ya estaba cancelada");
        }
        return true;
    }

    public List<Pelicula> consultarPeliculasDisponibles() {
        List<Pelicula> disponibles = new ArrayList<>();
        for (Pelicula pelicula : peliculas) {
            if (pelicula.getEstado() == EstadoPelicula.DISPONIBLE) {
                disponibles.add(pelicula);
            }
        }
        return disponibles;
    }

    public List<Funcion> consultarFuncionesProgramadas() {
        List<Funcion> programadas = new ArrayList<>();
        for (Funcion funcion : funciones) {
            if (funcion.getEstado() == EstadoFuncion.PROGRAMADA) {
                programadas.add(funcion);
            }
        }
        return programadas;
    }

    public List<Funcion> consultarFuncionesPorFecha(LocalDate fecha) {
        List<Funcion> programadas = new ArrayList<>();
        for (Funcion funcion : funciones) {
            if (funcion.getFecha().equals(fecha)) {
                programadas.add(funcion);
            }
        }
        return programadas;
    }

    public List<Asiento> consultarAsientosDisponibles(String codigoFuncion) {
        Funcion funcion = buscarFuncion(codigoFuncion);
        if (funcion == null) {
            throw new FuncionNoEncontradaException("La función " + codigoFuncion + " no existe");
        }
        return funcion.getAsientosDisponibles();
    }

    public int totalEntradasVendidas() {
        int total = 0;
        for (Venta venta : ventas) {
            if (venta.getEstado() == EstadoVenta.ACTIVA) {
                total++;
            }
        }
        return total;
    }

    public double totalIngresos() {
        double total = 0;
        for (Venta venta : ventas) {
            if (venta.getEstado() == EstadoVenta.ACTIVA) {
                total += venta.getValorPagado();
            }
        }
        return total;
    }

    public Pelicula peliculaMasVendida() {
        Pelicula masVendida = null;
        int mayorCantidad = 0;
        for (Pelicula pelicula : peliculas) {
            int cantidad = entradasVendidasPelicula(pelicula);
            if (cantidad > mayorCantidad) {
                mayorCantidad = cantidad;
                masVendida = pelicula;
            }
        }
        return masVendida;
    }

    public int entradasVendidasPelicula(Pelicula pelicula) {
        int cantidad = 0;
        for (Venta venta : ventas) {
            if (venta.getEstado() == EstadoVenta.ACTIVA
                    && venta.getFuncion().getPelicula().getCodigo().equalsIgnoreCase(pelicula.getCodigo())) {
                cantidad++;
            }
        }
        return cantidad;
    }

    public String reporteOcupacion(String codigoFuncion) {
        Funcion funcion = buscarFuncion(codigoFuncion);
        if (funcion == null) {
            throw new FuncionNoEncontradaException("La función " + codigoFuncion + " no existe");
        }
        return "Total: " + funcion.getCantidadAsientos()
                + "    Disponibles: " + funcion.getCantidadAsientosDisponibles()
                + "    Ocupados: " + funcion.getCantidadAsientosOcupados();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public List<Pelicula> getPeliculas() {
        return peliculas;
    }

    public void setPeliculas(List<Pelicula> peliculas) {
        this.peliculas = peliculas;
    }

    public List<Sala> getSalas() {
        return salas;
    }

    public void setSalas(List<Sala> salas) {
        this.salas = salas;
    }

    public List<Funcion> getFunciones() {
        return funciones;
    }

    public void setFunciones(List<Funcion> funciones) {
        this.funciones = funciones;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public List<Venta> getVentas() {
        return ventas;
    }

    public void setVentas(List<Venta> ventas) {
        this.ventas = ventas;
    }

    public String toString() {
        return nombre + " NIT " + nit + " " + direccion;
    }
}
