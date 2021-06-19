package com.example.pm2e1201410010078.clases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.pm2e1201410010078.clases.dataBase;

public class SQLiteConexion extends SQLiteOpenHelper {

    public SQLiteConexion(Context context, String dbname, SQLiteDatabase.CursorFactory factory, int version){
        super(context, dbname, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /* Crear la tabla usando el método que está en la clase Transacciones */
        db.execSQL(dataBase.CREATE_TABLE_CONTACTOS);
        db.execSQL(dataBase.CREATE_TABLE_PAIS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(dataBase.DROP_TABLE_CONTACTOS); /* Borrar la base de datos */
        db.execSQL(dataBase.DROP_TABLE_PAISES); /* Borrar la base de datos */
        onCreate(db); /* Crear nuevamente la base de datos */
    }
}
