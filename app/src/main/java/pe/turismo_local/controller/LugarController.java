package pe.turismo_local.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import pe.turismo_local.model.DatabaseHelper;
import pe.turismo_local.model.LugarTuristico;

public class LugarController {
    private final DatabaseHelper dbHelper;

    public LugarController(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void insertLugar(LugarTuristico lugar) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("nombre", lugar.getNombre());
        values.put("descripcion", lugar.getDescripcion());
        values.put("cuidad", lugar.getCuidad());
        values.put("latitud", lugar.getLatitud());
        values.put("longitud", lugar.getLongitud());
        values.put("imagen", lugar.getImagen());
        values.put("favorito", lugar.isFavorito() ? 1 : 0);

        db.insert("lugares", null, values);
        db.close();
    }

    public void updateLugar(LugarTuristico lugar) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("nombre", lugar.getNombre());
        values.put("descripcion", lugar.getDescripcion());
        values.put("cuidad", lugar.getCuidad());
        values.put("latitud", lugar.getLatitud());
        values.put("longitud", lugar.getLongitud());
        values.put("imagen", lugar.getImagen());
        values.put("favorito", lugar.isFavorito() ? 1 : 0);

        db.update("lugares", values, "id = ?", new String[]{String.valueOf(lugar.getId())});
        db.close();
    }

    public void deleteLugar(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("lugares",  "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void toggleFavorito(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("UPDATE lugares SET favorito = NOT favorito WHERE id = " + id);
        db.close();
    }

    public List<LugarTuristico> obtenerLugares() {
        List<LugarTuristico> lugares = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("lugares", null,null, null, null, null, "nombre");

        if (cursor.moveToFirst()) {
            do {
                LugarTuristico lugar = new LugarTuristico(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getDouble(4),
                        cursor.getDouble(5),
                        cursor.getString(6),
                        cursor.getInt(7) == 1
                );

                lugares.add(lugar);
            } while (cursor.moveToNext());
            cursor.close();
        }

        db.close();
        return lugares;
    }

    public LugarTuristico obtenerLugar(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("lugares", null, "id = ?", new String[]{String.valueOf(id)}, null, null, null);

        LugarTuristico lugar = null;
        if (cursor.moveToFirst()) {
            lugar = new LugarTuristico(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getDouble(4),
                    cursor.getDouble(5),
                    cursor.getString(6),
                    cursor.getInt(7) == 1
            );
            cursor.close();
        }

        db.close();
        return lugar;
    }

    public List<LugarTuristico> obtenerFavoritos() {
        List<LugarTuristico> lugares = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("lugares", null, "favorito = 1", null, null, null, "nombre");

        if (cursor.moveToFirst()) {
            do {
                LugarTuristico lugar = new LugarTuristico(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getDouble(4),
                        cursor.getDouble(5),
                        cursor.getString(6),
                        cursor.getInt(7) == 1
                );

                lugares.add(lugar);
            } while (cursor.moveToNext());
            cursor.close();
        }

        db.close();
        return lugares;
    }

    public List<LugarTuristico> obtenerLugaresPorCiudad(String ciudad) {
        List<LugarTuristico> lugares = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("lugares", null, "ciudad = ?", new String[]{ciudad}, null, null, "nombre");

        if (cursor.moveToFirst()) {
            do {
                LugarTuristico lugar = new LugarTuristico(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getDouble(4),
                        cursor.getDouble(5),
                        cursor.getString(6),
                        cursor.getInt(7) == 1
                );

                lugares.add(lugar);
            } while (cursor.moveToNext());
            cursor.close();
        }

        db.close();
        return lugares;
    }
}
