package com.example.demo.anotalas.ui.listas;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Clase tipo listener que responde a eventos de seleccion de elementos de la
 * lista de notas.
 *
 * @author demo
 */
final class ListadoNotasOnItemTouch implements RecyclerView.OnItemTouchListener {

    /**
     * Gestor de eventos de seleccion.
     */
    private final GestureDetector mGestureDetector;

    /**
     * Listener para seleccion de elementos de lista.
     */
    private final OnItemClickListener mListener;

    ListadoNotasOnItemTouch(Context context, OnItemClickListener listener) {
        mListener = listener;
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View childView = rv.findChildViewUnder(e.getX(), e.getY());
        if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
            mListener.onItemClick(childView, rv.getChildAdapterPosition(childView));
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        /*TODO No se hace nada aqui */
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        /*TODO No se hace nada aqui */
    }

    /**
     * Definicion de comportamiento relacionado con la seleccion de elementos
     * de lista de notas.
     *
     * @author demo
     */
    interface OnItemClickListener {

        /**
         * Permite dar respuesta a una seleccion de elementos de la lista de notas.
         *
         * @param view     elemento perteneciente a la lista
         * @param position posicion del elemento seleccionado
         */
        void onItemClick(View view, int position);
    }

}