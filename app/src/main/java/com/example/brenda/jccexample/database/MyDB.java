package com.example.brenda.jccexample.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Brenda on 25/02/2017.
 */
public class MyDB extends SQLiteOpenHelper {

    public MyDB(Context context) {
        super(context, "BrendaDB", null, 1);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Pais( idPais text not null primary key, pais TEXT not null )");
        db.execSQL("create table DatoInteres( idDatoInteres text not null primary key, DatoInteres TEXT not null, Pais_idPais TEXT not null, foreign key(Pais_idPais) references Pais(idPais) on delete cascade on update cascade )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

}
