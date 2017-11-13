package com.example.brenda.jccexample.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
        db.execSQL("create table Pais(" +
                "        idPais integer not null primary key," +
                "        pais TEXT not null" +
                ")");
        db.execSQL("create table DatoInteres(" +
                "        idDatoInteres integer not null primary key," +
                "        DatoInteres TEXT not null," +
                "        Pais_idPais integer not null," +
                "        foreign key(Pais_idPais) references Pais(idPais) on delete cascade on update cascade" +
                ")");
        db.execSQL("create table Modismo(" +
                "        idModismo integer not null primary key," +
                "        Expresion TEXT not null," +
                "        idPais integer not null," +
                "        foreign key(idPais) references Pais(idPais) on delete cascade on update cascade" +
                ")");
        db.execSQL("create table Significado(" +
                "        idSignificado integer not null primary key," +
                "        Significado text not null," +
                "        idModismo integer not null," +
                "        foreign key(idModismo) references Modismo(idModismo) on delete cascade on update cascade" +
                ")");
        db.execSQL("create table Ejemplo(" +
                "        idEjemplo integer not null primary key," +
                "        Ejemplo text not null," +
                "        idModismo integer not null," +
                "        foreign key(idModismo) references Modismo(idModismo) on delete cascade on update cascade" +
                ")");
        db.execSQL("create table ModismoRelacion(" +
                "        idModismo_1 integer not null," +
                "        idModismo_2 integer not null," +
                "        primary key(idModismo_1,idModismo_2)," +
                "        foreign key(idModismo_1) references Modismo(idModismo) on delete cascade on update cascade," +
                "        foreign key(idModismo_2) references Modismo(idModismo) on delete cascade on update cascade" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        Log.e("MyDB", "Fui llamado @onUpdate");
        db.execSQL("DROP TABLE IF EXISTS `ModismoRelacion`");
        db.execSQL("DROP TABLE IF EXISTS `Ejemplo`");
        db.execSQL("DROP TABLE IF EXISTS `Similar`");
        db.execSQL("DROP TABLE IF EXISTS `Significado`");
        db.execSQL("DROP TABLE IF EXISTS `DatoInteres`");
        db.execSQL("DROP TABLE IF EXISTS `Modismo`");
        db.execSQL("DROP TABLE IF EXISTS `Pais`");

        db.execSQL("create table Pais(" +
                "        idPais integer not null primary key," +
                "        pais TEXT not null" +
                ")");
        db.execSQL("create table DatoInteres(" +
                "        idDatoInteres integer not null primary key," +
                "        DatoInteres TEXT not null," +
                "        Pais_idPais integer not null," +
                "        foreign key(Pais_idPais) references Pais(idPais) on delete cascade on update cascade" +
                ")");
        db.execSQL("create table Modismo(" +
                "        idModismo integer not null primary key," +
                "        Expresion TEXT not null," +
                "        idPais integer not null," +
                "        foreign key(idPais) references Pais(idPais) on delete cascade on update cascade" +
                ")");
        db.execSQL("create table Significado(" +
                "        idSignificado integer not null primary key," +
                "        Significado text not null," +
                "        idModismo integer not null," +
                "        foreign key(idModismo) references Modismo(idModismo) on delete cascade on update cascade" +
                ")");
        db.execSQL("create table Ejemplo(" +
                "        idEjemplo integer not null primary key," +
                "        Ejemplo text not null," +
                "        idModismo integer not null," +
                "        foreign key(idModismo) references Modismo(idModismo) on delete cascade on update cascade" +
                ")");
        db.execSQL("create table ModismoRelacion(" +
                "        idModismo_1 integer not null," +
                "        idModismo_2 integer not null," +
                "        primary key(idModismo_1,idModismo_2)," +
                "        foreign key(idModismo_1) references Modismo(idModismo) on delete cascade on update cascade," +
                "        foreign key(idModismo_2) references Modismo(idModismo) on delete cascade on update cascade" +
                ")");
    }

}
