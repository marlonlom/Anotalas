package com.example.demo.anotalas.ui.edicion;

import android.content.Context;

import com.example.demo.anotalas.model.datos.NotasSQLiteHelper;
import com.example.demo.anotalas.model.items.Nota;

/**
 * Clase tipo contrato para aplicar al modelo vista presentador (MVP),
 * para el manejo de la informacion de una nota seleccionada.
 *
 * @author demo
 */
final class EdicionNotas {
    /**
     * Definicion de interface tipo vista, que permite controlar y realizar las
     * operaciones de negocio relacionados con la informacion de una nota
     * seleccionada.
     *
     * @author demo
     */
    interface View {
        /**
         * Permite habilitar la pantalla de edicion de nota.
         */
        void habilitarEdicion();

        /**
         * Premite realizar las validaciones previas al guardado de una nota,
         * bien sea de registro nuevo o de edicion.
         */
        void prepararGuardadoNota();

        /**
         * Mostrar mensaje exito guardado nota.
         *
         * @param crearNuevo true/false
         */
        void mostrarMensajeExito(boolean crearNuevo);

        /**
         * Mostrar mensaje exito guardado nota.
         *
         * @param crearNuevo true/false
         */
        void mostrarMensajeError(boolean crearNuevo);
    }

    /**
     * Coase tipo presentador, que permite controlar y realizar las
     * operaciones de negocio relacionados con la informacion de
     * una nota seleccionada.
     *
     * @author demo
     */
    static class Presenter {

        private final NotasSQLiteHelper mModel;
        private final EdicionNotas.View mView;
        private final Context mContext;

        Presenter(EdicionNotas.View view, Context context) {
            mView = view;
            mContext = context;
            mModel = new NotasSQLiteHelper(mContext);
        }

        /**
         * Encontrar nota usando el codigo.
         *
         * @param idNota codigo nota
         * @return nota encontrada, nulo si no
         */
        Nota encontrarNota(Long idNota) {
            return mModel.encontrarPorId(idNota);
        }

        void aplicarGuardadoNota(Nota notaGuardar, boolean crearNuevo) {
            boolean guardado = mModel.guardarNota(notaGuardar, crearNuevo);
            if (guardado) {
                mView.mostrarMensajeExito(crearNuevo);
            } else {
                mView.mostrarMensajeError(crearNuevo);
            }
        }
    }
}
