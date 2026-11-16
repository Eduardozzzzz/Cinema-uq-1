package co.edu.uniquindio.poo.cinemauq.model;

import java.util.ArrayList;
import java.util.List;

public class Sala {
    private String codigo;
    private String nombre;
    private int capacidad;
    private EstadoSala estado;
    private List<Asiento> asientos;

    public Sala(String codigo, String nombre, int capacidad) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.estado = EstadoSala.DISPONIBLE;
        this.asientos = new ArrayList<>();
        generarAsientos();
    }

    public void generarAsientos() {
        asientos.clear();
        for (int i = 0; i < capacidad; i++) {
            String fila = String.valueOf((char) ('A' + i / 10));
            int numero = (i % 10) + 1;
            asientos.add(new Asiento(fila + numero, fila, numero));
        }
    }

    public Asiento buscarAsiento(String codigo) {
        for (Asiento asiento : asientos) {
            if (asiento.getCodigo().equalsIgnoreCase(codigo)) {
                return asiento;
            }
        }
        return null;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public EstadoSala getEstado() {
        return estado;
    }

    public void setEstado(EstadoSala estado) {
        this.estado = estado;
    }

    public List<Asiento> getAsientos() {
        return asientos;
    }

    public void setAsientos(List<Asiento> asientos) {
        this.asientos = asientos;
    }

    public String toString() {
        return codigo + " - " + nombre + " (" + capacidad + ")";
    }
}
