package pe.turismo_local.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "lugares.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_LUGARES = "lugares";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NOMBRE = "nombre";
    private static final String COLUMN_CUIDAD = "ciudad";
    private static final String COLUMN_DESCRIPCION = "descripcion";
    private static final String COLUMN_LATITUD = "latitud";
    private static final String COLUMN_LONGITUD = "longitud";
    private static final String COLUMN_IMAGEN = "imagen";
    private static final String COLUMN_FAVORITO = "favorito";

    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_LUGARES + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NOMBRE + " TEXT, " +
                COLUMN_DESCRIPCION + " TEXT, " +
                COLUMN_CUIDAD + " TEXT, " +
                COLUMN_LATITUD + " REAL, " +
                COLUMN_LONGITUD + " REAL, " +
                COLUMN_IMAGEN + " TEXT, " +
                COLUMN_FAVORITO + " INTEGER DEFAULT 0)";

        db.execSQL(CREATE_TABLE);

        insertDatosIniciales(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LUGARES);
        onCreate(db);
    }

    public void insertDatosIniciales(SQLiteDatabase db) {
        String INSERT_LUGAR = "INSERT INTO " + TABLE_LUGARES + " (" +
                COLUMN_NOMBRE + ", " +
                COLUMN_DESCRIPCION + ", " +
                COLUMN_CUIDAD + ", " +
                COLUMN_LATITUD + ", " +
                COLUMN_LONGITUD + ", " +
                COLUMN_IMAGEN + ", " +
                COLUMN_FAVORITO + ") VALUES (?, ?, ?, ?, ?, ?, ?)";

        // Lima
        db.execSQL(INSERT_LUGAR, new String[]{
                "Plaza de Armas",
                "La Plaza de Armas de Lima, también conocida como Plaza Mayor, es el corazón del Centro Histórico de Lima y el lugar donde se fundó la ciudad en 1535 por Francisco Pizarro. Rodeada de edificios emblemáticos como el Palacio de Gobierno, la Catedral de Lima y el Palacio Municipal, conserva su arquitectura colonial y una fuente de bronce del siglo XVII en su centro. Declarada Patrimonio Cultural de la Humanidad por la UNESCO en 1991, es un punto clave para la historia, la cultura y el turismo en la capital peruana.",
                "Lima",
                "-12.0453",
                "-77.0311",
                "plaza_armas.jpg",
                "0"
        });

        db.execSQL(INSERT_LUGAR, new String[]{
                "Catedral de Lima",
                "La Catedral de Lima, ubicada en la Plaza de Armas, es el principal templo católico de la ciudad y un símbolo de la arquitectura colonial. Su construcción comenzó en 1535 por orden de Francisco Pizarro y ha sido reconstruida varias veces debido a terremotos. Su fachada neoclásica y sus altas torres contrastan con su interior barroco, donde se encuentran valiosas obras de arte religioso y la tumba del propio Pizarro. Es un importante destino turístico y religioso, reflejando la historia y el legado cultural del Perú.",
                "Lima",
                "-12.0432",
                "-77.0282",
                "plaza_armas.jpg",
                "0"
        });

        db.execSQL(INSERT_LUGAR, new String[]{
                "Palacio de Gobierno",
                "El Palacio de Gobierno del Perú, también conocido como Casa de Pizarro, es la sede del Poder Ejecutivo y residencia oficial del Presidente de la República. Ubicado en la Plaza de Armas de Lima, fue construido en 1535 por orden de Francisco Pizarro en el mismo lugar donde se encontraba el palacio del gobernante inca Taulichusco. A lo largo de los siglos, ha sido remodelado varias veces, destacando su actual fachada de estilo neobarroco. Es famoso por el Cambio de Guardia, un atractivo turístico que se realiza diariamente en su patio principal.",
                "Lima",
                "-12.0464",
                "-77.0300",
                "plaza_armas.jpg",
                "0"
        });

        db.execSQL(INSERT_LUGAR, new String[]{
                "Parque Kennedy",
                "El Parque Kennedy, ubicado en el distrito de Miraflores, es uno de los espacios públicos más conocidos y concurridos de Lima. Es famoso por su ambiente vibrante, rodeado de restaurantes, cafeterías y tiendas, además de ser un punto de encuentro para artistas callejeros y ferias de artesanía. Su característica más peculiar es la gran cantidad de gatos que habitan en el parque, convirtiéndolo en un lugar icónico para los amantes de los animales. Es un destino ideal para disfrutar de la cultura, la gastronomía y la vida nocturna de la ciudad.",
                "Lima",
                "-12.1210",
                "-77.0301",
                "plaza_armas.jpg",
                "0"
        });

        db.execSQL(INSERT_LUGAR, new String[]{
                "Museo Larco",
                "El Museo Larco, ubicado en el distrito de Pueblo Libre, es uno de los museos más importantes del Perú, conocido por su impresionante colección de arte precolombino. Fundado en 1926 por Rafael Larco Hoyle, alberga más de 45,000 piezas arqueológicas, incluyendo cerámicas, textiles y joyería de antiguas civilizaciones peruanas. Destaca por su famosa colección de cerámica erótica moche y sus exhibiciones accesibles al público en depósitos abiertos. Su sede es una casona virreinal del siglo XVIII, rodeada de hermosos jardines, lo que lo convierte en un destino imprescindible para quienes desean conocer la historia del Perú prehispánico.",
                "Lima",
                "-12.0855",
                "-77.0944",
                "plaza_armas.jpg",
                "0"
        });
    }

    public void insertLugar(LugarTuristico lugar) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NOMBRE, lugar.getNombre());
        values.put(COLUMN_DESCRIPCION, lugar.getDescripcion());
        values.put(COLUMN_CUIDAD, lugar.getCuidad());
        values.put(COLUMN_LATITUD, lugar.getLatitud());
        values.put(COLUMN_LONGITUD, lugar.getLongitud());
        values.put(COLUMN_IMAGEN, lugar.getImagen());
        values.put(COLUMN_FAVORITO, lugar.isFavorito() ? 1 : 0);

        db.insert(TABLE_LUGARES, null, values);
        db.close();
    }

    public void updateLugar(LugarTuristico lugar) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NOMBRE, lugar.getNombre());
        values.put(COLUMN_DESCRIPCION, lugar.getDescripcion());
        values.put(COLUMN_CUIDAD, lugar.getCuidad());
        values.put(COLUMN_LATITUD, lugar.getLatitud());
        values.put(COLUMN_LONGITUD, lugar.getLongitud());
        values.put(COLUMN_IMAGEN, lugar.getImagen());
        values.put(COLUMN_FAVORITO, lugar.isFavorito() ? 1 : 0);

        db.update(TABLE_LUGARES, values, COLUMN_ID + " = ?", new String[]{String.valueOf(lugar.getId())});
        db.close();
    }

    public void deleteLugar(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_LUGARES, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void toggleFavorito(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_LUGARES + " SET " + COLUMN_FAVORITO + " = NOT " + COLUMN_FAVORITO + " WHERE " + COLUMN_ID + " = " + id);
        db.close();
    }

    public List<LugarTuristico> obtenerLugares() {
        List<LugarTuristico> lugares = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_LUGARES, null,null, null, null, null, COLUMN_NOMBRE);

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
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_LUGARES, null, COLUMN_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);

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
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_LUGARES, null, COLUMN_FAVORITO + " = 1", null, null, null, COLUMN_NOMBRE);

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
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_LUGARES, null, COLUMN_CUIDAD + " = ?", new String[]{ciudad}, null, null, COLUMN_NOMBRE);

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
