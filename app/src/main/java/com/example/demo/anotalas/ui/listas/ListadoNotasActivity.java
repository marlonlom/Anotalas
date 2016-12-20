package com.example.demo.anotalas.ui.listas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.demo.anotalas.R;
import com.example.demo.anotalas.ui.edicion.EdicionNotasActivity;

import static com.example.demo.anotalas.ui.Constantes.PARAM_ACTION;
import static com.example.demo.anotalas.ui.Constantes.RESULT_CREAR;

/**
 * Clase tipo actividad, que permite mostrar el listado de las notas.
 *
 * @author demo
 */
public final class ListadoNotasActivity extends AppCompatActivity implements ListadoNotas.View {

    private ListadoNotas.Presenter mPresenter;

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
    public void mostrarListadoNotas() {
        Toast.makeText(this, "listado exitoso", Toast.LENGTH_LONG).show();
    }

    @Override
    public void mostrarErrorListado() {
        findViewById(R.id.tv_listado_vacio).setVisibility(View.VISIBLE);
    }
}
