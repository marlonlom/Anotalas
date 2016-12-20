package com.example.demo.anotalas.ui.listas;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.demo.anotalas.R;
import com.example.demo.anotalas.model.items.Nota;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase tipo adaptador que permite listar las notas en pantalla.
 *
 * @author demo
 */
class ListadoNotasAdapter extends RecyclerView.Adapter<ListadoNotasAdapter.ViewHolder> {

    /**
     * Listado de notas a mostrar.
     */
    private List<Nota> mNotas;

    ListadoNotasAdapter() {
        setNotas(new ArrayList<Nota>());
    }

    /**
     * Obtiene el listado de notas.
     *
     * @return listado de notas
     */
    List<Nota> getNotas() {
        return mNotas;
    }

    /**
     * Modifica la lista de notas a mostrar.
     *
     * @param notas listado notas
     */
    void setNotas(List<Nota> notas) {
        this.mNotas = notas;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_listado_notas_item, parent, false);
        return new ViewHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Nota nota = mNotas.get(position);
        holder.mTextTituloNota.setText(nota.getTitulo());
    }

    @Override
    public int getItemCount() {
        return mNotas != null && !mNotas.isEmpty() ? mNotas.size() : 0;
    }

    /**
     * Clase tipo modelo de vista, que representa un elemento de la lista de notas.
     *
     * @author demo
     */
    class ViewHolder extends RecyclerView.ViewHolder {

        /**
         * Textview que representa el titulo de la nota.
         */
        TextView mTextTituloNota;

        ViewHolder(View itemView) {
            super(itemView);
            mTextTituloNota = (TextView) itemView.findViewById(R.id.tv_listado_item_titulo);
        }
    }
}
