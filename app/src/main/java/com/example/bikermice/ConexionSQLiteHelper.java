package com.example.BikerMice;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

import com.example.BikerMice.utilidades.Utilidades;

public class ConexionSQLiteHelper extends SQLiteOpenHelper {


    public ConexionSQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(Utilidades.CREAR_TABLA_CONDUCTORES);
        db.execSQL(Utilidades.CREAR_TABLA_USUARIOS);
        db.execSQL(Utilidades.CREAR_TABLA_MOTOS);
        db.execSQL(Utilidades.CREAR_TABLA_COMENTARIOS);
        db.execSQL(Utilidades.CREAR_TABLA_PUNTAJE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAntigua, int versionNueva) {
        db.execSQL("DROP TABLE IF EXISTS conductores");
        db.execSQL("DROP TABLE IF EXISTS pasajeros");
        db.execSQL("DROP TABLE IF EXISTS motos");
        db.execSQL("DROP TABLE IF EXISTS comentarios");
        db.execSQL("DROP TABLE IF EXISTS puntaje");

        onCreate(db);
    }
}
