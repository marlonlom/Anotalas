package com.example.demo.anotalas.model.repositorio;

import com.example.demo.anotalas.model.items.Nota;

import java.util.List;

/**
 * Definicion de repositorio para centralizar el manejo de la
 * informacion de las notas.
 *
 * @author demo
 */
public interface NotasRepositorio {

    /**
     * Realizar registro de nueva nota, o realizar la
     * edición de una nota existente.
     *
     * @param nota       nota seleccionada
     * @param crearNuevo true/false si se contempla la creacion de una nueva nota
     * @return nota actualizada
     */
    boolean guardarNota(Nota nota, boolean crearNuevo);

    /**
     * Eliminar una nota usando su numero de referencia.
     *
     * @param idNota codigo de la nota a eliminar
     * @return true/false si la nota fue eliminada
     */
    boolean eliminarNota(Long idNota);

    /**
     * Listar las notas.
     *
     * @return lista de notas
     */
    List<Nota> listarTodos();

    /**
     * Encontrar nota por codigo
     *
     * @param idNota codigo nota
     * @return nota encontrada
     */
    Nota encontrarPorId(Long idNota);
}
