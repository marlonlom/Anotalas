package com.example.demo.anotalas.ui.listas;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.demo.anotalas.R;
import com.example.demo.anotalas.model.items.Nota;
import com.example.demo.anotalas.ui.edicion.EdicionNotasActivity;

import java.util.List;

import static com.example.demo.anotalas.ui.Constantes.PARAM_ACTION;
import static com.example.demo.anotalas.ui.Constantes.RESULT_CREAR;

/**
 * Clase tipo actividad, que permite mostrar el listado de las notas.
 *
 * @author demo
 */
public final class ListadoNotasActivity extends AppCompatActivity implements ListadoNotas.View {

    /**
     * Componente tipo presentador, el cual controla las acciones
     * y eventos relacionados con el listado de notas.
     */
    private ListadoNotas.Presenter mPresenter;

    /**
     * Recyclerview que representa el listado de notas.
     */
    private RecyclerView mListaNotas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mPresenter = new ListadoNotas.Presenter(this, this);

        prepararListadoNotas();
    }

    /**
     * Obtener listado de notas para mostrar en la pantalla.
     */
    private void prepararListadoNotas() {
        mListaNotas = (RecyclerView) findViewById(R.id.rv_listado_notas);
        mListaNotas.setHasFixedSize(true);
        mListaNotas.setLayoutManager(new LinearLayoutManager(this));
        mListaNotas.setAdapter(new ListadoNotasAdapter());
        mListaNotas.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        mListaNotas.addOnItemTouchListener(new ListadoNotasOnItemTouch(this,
                new ListadoNotasOnItemTouch.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Nota nota = ((ListadoNotasAdapter) mListaNotas.getAdapter()).getNotas().get(position);
                        Snackbar.make(findViewById(R.id.main_content),
                                "Clicked : " + nota.getTitulo(), Snackbar.LENGTH_SHORT).show();
                    }
                }));
        findViewById(R.id.tv_listado_vacio).setVisibility(View.GONE);
        mPresenter.obtenerListadoNotas();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_add) {
            this.mostrarVistaCreacionNotas();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void mostrarVistaCreacionNotas() {
        final Intent intent = new Intent(ListadoNotasActivity.this, EdicionNotasActivity.class);
        intent.putExtra(PARAM_ACTION, RESULT_CREAR);
        startActivityForResult(intent, RESULT_CREAR);
    }

    @Override
    public void mostrarListadoNotas(List<Nota> notas) {
        findViewById(R.id.tv_listado_vacio).setVisibility(View.GONE);
        findViewById(R.id.rv_listado_notas).setVisibility(View.VISIBLE);
        ((ListadoNotasAdapter) mListaNotas.getAdapter()).setNotas(notas);
    }

    @Override
    public void mostrarErrorListado() {
        findViewById(R.id.tv_listado_vacio).setVisibility(View.VISIBLE);
        findViewById(R.id.rv_listado_notas).setVisibility(View.GONE);
    }
}
