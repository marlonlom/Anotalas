package com.example.demo.anotalas.model.datos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.demo.anotalas.model.items.Nota;
import com.example.demo.anotalas.model.repositorio.NotasRepositorio;

import java.util.ArrayList;
import java.util.List;

/**
 * SQL helper for Notas database
 *
 * @author demo
 */
public final class NotasSQLiteHelper extends SQLiteOpenHelper implements NotasRepositorio {
    private static final String BD_NAME = "demo_notas_bd";
    private static final int BD_VERSION = 1;
    private static final String SQL_CREATE = "CREATE TABLE Notas " +
            "(codigo INTEGER PRIMARY KEY AUTOINCREMENT, titulo TEXT, " +
            "tipo TEXT, descripcion TEXT)";

    public NotasSQLiteHelper(Context context) {
        super(context, BD_NAME, null, BD_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Notas");
        onCreate(db);
    }

    @Override
    public boolean guardarNota(Nota nota, boolean crearNuevo) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            final ContentValues valores = new ContentValues();
            valores.put("titulo", nota.getTitulo());
            valores.put("tipo", nota.getTipo());
            valores.put("descripcion", nota.getDescripcion());
            if (!crearNuevo) {
                db.update("Usuarios", valores, "codigo=".concat(String.valueOf(nota.getId())), null);
            } else {
                db.insert("Notas", null, valores);
            }
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            db.close();
        }
    }

    @Override
    public boolean eliminarNota(Long idNota) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.delete("Notas", "codigo=".concat(String.valueOf(idNota)), null);
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            db.close();
        }
    }

    @Override
    public List<Nota> listarTodos() {
        final List<Nota> notas = new ArrayList<Nota>();
        SQLiteDatabase db = getReadableDatabase();
        final Cursor cursor = db.rawQuery("SELECT codigo, titulo, tipo, descripcion FROM Notas LIMIT 50", null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (cursor.isAfterLast()) {
                final Nota item = new Nota();
                item.setId(cursor.getLong(0));
                item.setTitulo(cursor.getString(1));
                item.setTipo(cursor.getString(2));
                item.setDescripcion(cursor.getString(3));

                cursor.moveToNext();
                notas.add(item);
            }
            cursor.close();
        }
        db.close();

        return notas;
    }

    @Override
    public Nota encontrarPorId(Long idNota) {
        Nota item = null;
        SQLiteDatabase db = getReadableDatabase();
        final String txtQuery = "SELECT codigo, titulo, tipo, descripcion " +
                "FROM Notas WHERE codigo = %s";
        final Cursor cursor = db.rawQuery(String.format(txtQuery, String.valueOf(idNota)), null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();

            item = new Nota();
            item.setId(cursor.getLong(0));
            item.setTitulo(cursor.getString(1));
            item.setTipo(cursor.getString(2));
            item.setDescripcion(cursor.getString(3));

            cursor.close();
        }
        db.close();

        return item;
    }
}
