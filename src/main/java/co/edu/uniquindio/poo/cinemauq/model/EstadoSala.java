package co.edu.uniquindio.poo.cinemauq.model;

/*
 * EstadoSala = si la sala se puede usar o no.
 *
 * DISPONIBLE           -> la sala esta operativa (se puede programar alli).
 * FUERA_DE_SERVICIO    -> la sala esta cerrada (por mantenimiento, por ejemplo).
 *
 * El enunciado dice: "El sistema debera impedir registrar una funcion si la sala se
 * encuentra fuera de servicio". Esa regla se implementa en Cine.registrarFuncion().
 */
public enum EstadoSala {
    DISPONIBLE,
    FUERA_DE_SERVICIO
}
