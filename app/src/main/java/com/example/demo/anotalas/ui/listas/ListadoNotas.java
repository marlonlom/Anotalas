package com.example.demo.anotalas.ui.listas;

import android.content.Context;

import com.example.demo.anotalas.model.datos.NotasSQLiteHelper;
import com.example.demo.anotalas.model.items.Nota;

import java.util.List;

/**
 * Clase tipo contrato para aplicar al modelo vista presentador (MVP),
 * para el manejo de la lista de notas.
 *
 * @author demo
 */
final class ListadoNotas {

    /**
     * Definicion de interface tipo vista, que permite controlar y realizar las
     * operaciones de negocio relacionados con la lista de notas.
     *
     * @author demo
     */
    interface View {
        void mostrarVistaCreacionNotas();

        void mostrarListadoNotas();

        void mostrarErrorListado();
    }

    /**
     * Coase tipo presentador, que permite controlar y realizar las
     * operaciones de negocio relacionados con la lista de notas.
     *
     * @author demo
     */
    static class Presenter {

        private final NotasSQLiteHelper mModel;
        private final View mView;
        private final Context mContext;

        Presenter(ListadoNotas.View view, Context context) {
            mView = view;
            mContext = context;
            mModel = new NotasSQLiteHelper(mContext);
        }

        void obtenerListadoNotas() {
            final List<Nota> notas = mModel.listarTodos();
            if (notas == null || notas.isEmpty()) {
                mView.mostrarErrorListado();
            } else {
                mView.mostrarListadoNotas();
            }
        }
    }
}
