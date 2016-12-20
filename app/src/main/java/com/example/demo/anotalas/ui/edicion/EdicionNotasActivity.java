package com.example.demo.anotalas.ui.edicion;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.demo.anotalas.R;
import com.example.demo.anotalas.model.items.Nota;
import com.example.demo.anotalas.ui.Constantes;

/**
 * Clase tipo activity que permite realizar operaciones de consulta,
 * registro, edicion y eliminado de un registro de nota.
 *
 * @author demo
 */
public class EdicionNotasActivity extends AppCompatActivity implements EdicionNotas.View {

    private EdicionNotas.Presenter mPresenter;
    private int mAccion;
    private long mIdNota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        mPresenter = new EdicionNotas.Presenter(this, this);
        prepararToolbar();
        prepararDatosObtenidos();
        prepararBotonEdicion();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edicion, menu);
        supportInvalidateOptionsMenu();
        Bundle extra = getBundleExtras();
        final int accion = extra != null ? extra.getInt(Constantes.PARAM_ACTION) : -1;
        final boolean modoLectura = accion > -1 && accion == Constantes.RESULT_DETALLE;
        menu.findItem(R.id.action_edit).setVisible(modoLectura);
        menu.findItem(R.id.action_delete).setVisible(modoLectura);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_edit:
                this.habilitarEdicion();
                break;
            case R.id.action_delete:
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    /**
     * Preparar datos enviados deste vistas anteriores, y de acuerdo a la accion a
     * realizar, carga la pantalla de creacion, edicion o de lectura.
     */
    private void prepararDatosObtenidos() {
        Bundle extra = getBundleExtras();

        if (extra == null) return;

        mAccion = extra.getInt(Constantes.PARAM_ACTION);
        switch (mAccion) {
            case Constantes.RESULT_CREAR:
                findViewById(R.id.content_editor_notas).setVisibility(View.VISIBLE);
                findViewById(R.id.content_detalle_notas).setVisibility(View.GONE);

                ((EditText) findViewById(R.id.et_editor_titulo)).setText("");
                ((Spinner) findViewById(R.id.spn_editor_tipo)).setSelection(0);
                ((TextInputEditText) findViewById(R.id.tiet_editor_detalle)).setText("");
                break;
            case Constantes.RESULT_DETALLE:
                findViewById(R.id.content_editor_notas).setVisibility(View.GONE);
                findViewById(R.id.content_detalle_notas).setVisibility(View.VISIBLE);

                mIdNota = extra.getLong(Constantes.PARAM_NOTA_ID);
                final Nota nota = mPresenter.encontrarNota(mIdNota);
                final String htmlText = getResources().getString(R.string.msg_detalle_nota,
                        nota.getTitulo(), nota.getTipo(), nota.getDescripcion());
                ((TextView) findViewById(R.id.tv_detalle_nota)).setText(Html.fromHtml(htmlText));
                break;
        }
    }

    /**
     * Obtener listado tipos nota
     *
     * @return listado tipos nota
     */
    String[] obtenerTiposNota() {
        return getResources().getStringArray(R.array.spn_editor_tipos);
    }

    /**
     * Dado un tipo de nota, obtener la posicion en la lista de tipos.
     *
     * @param tipoNota texto a consultar
     * @return posicion del tipo de nota.
     */
    private int obtenerPosicionTipoNota(String tipoNota) {
        final String[] tipos = obtenerTiposNota();
        for (int pos = 0; pos < tipos.length; pos++) {
            final String item = tipos[pos];
            if (item.equalsIgnoreCase(tipoNota)) {
                return pos;
            }
        }
        return 0;
    }

    /**
     * Obtener datos pasados por las pantallas.
     *
     * @return datos encontrados
     */
    private Bundle getBundleExtras() {
        Intent intent = getIntent();
        return intent.getExtras();
    }

    /**
     * Preparar toolbar de la pantalla.
     */
    private void prepararToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * Preparar boton de aplicacion de guardado de notas.
     */
    private void prepararBotonEdicion() {
        ((Button) findViewById(R.id.btn_editor_guardar)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prepararGuardadoNota();
            }
        });
    }

    @Override
    public void habilitarEdicion() {
        Bundle extra = getBundleExtras();
        if (extra == null) return;

        findViewById(R.id.content_editor_notas).setVisibility(View.VISIBLE);
        findViewById(R.id.content_detalle_notas).setVisibility(View.GONE);

        final Nota nota = mPresenter.encontrarNota(mIdNota);
        ((EditText) findViewById(R.id.et_editor_titulo)).setText(nota.getTitulo());
        ((Spinner) findViewById(R.id.spn_editor_tipo)).setSelection(obtenerPosicionTipoNota(nota.getTipo()));
        ((TextInputEditText) findViewById(R.id.tiet_editor_detalle)).setText(nota.getDescripcion());
        mAccion = Constantes.RESULT_EDITAR;
    }

    @Override
    public void prepararGuardadoNota() {
        final String titulo = ((EditText) findViewById(R.id.et_editor_titulo)).getText().toString();
        final String tipo = (String) ((Spinner) findViewById(R.id.spn_editor_tipo)).getSelectedItem();
        final String detalle = ((TextInputEditText) findViewById(R.id.tiet_editor_detalle)).getText().toString();

        boolean datosCompletos = !titulo.isEmpty() && !tipo.isEmpty() && !detalle.isEmpty();

        if (datosCompletos) {
            final Nota notaGuardar = new Nota();
            notaGuardar.setTitulo(titulo);
            notaGuardar.setTipo(tipo);
            notaGuardar.setDescripcion(detalle);
            if (mAccion == Constantes.RESULT_EDITAR) {
                notaGuardar.setId(mIdNota);
            }
            mPresenter.aplicarGuardadoNota(notaGuardar, mAccion == Constantes.RESULT_CREAR);
        } else {
            Snackbar.make(findViewById(R.id.editor_content),
                    R.string.err_edicion_faltantes, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void mostrarMensajeExito(boolean crearNuevo) {
        int resId = crearNuevo ? R.string.msg_registro_exitoso : R.string.msg_edicion_exitosa;
        Snackbar.make(findViewById(R.id.editor_content), resId, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void mostrarMensajeError(boolean crearNuevo) {
        Snackbar.make(findViewById(R.id.editor_content), R.string.err_edicion_nota, Snackbar.LENGTH_SHORT).show();
    }

}
