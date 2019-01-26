package com.anie.dara.kamuskita.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "dbkamus";

    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_ENTRIES_ENGDO =
            "CREATE TABLE " + DatabaseContract.EngDo.TABLE_NAME + " (" +
                    DatabaseContract.EngDo._ID + " INTEGER PRIMARY KEY," +
                    DatabaseContract.EngDo.COLUMN_NAME_KEYWORD + " TEXT," +
                    DatabaseContract.EngDo.COLUMN_NAME_ARTI + " TEXT)";

    private static final String SQL_CREATE_ENTRIES_DOENG =
            "CREATE TABLE " + DatabaseContract.DoEng.TABLE_NAME + " (" +
                    DatabaseContract.DoEng._ID + " INTEGER PRIMARY KEY," +
                    DatabaseContract.DoEng.KEY + " TEXT," +
                    DatabaseContract.DoEng.ARTI + " TEXT)";



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES_ENGDO);
        db.execSQL(SQL_CREATE_ENTRIES_DOENG);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ DatabaseContract.EngDo.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ DatabaseContract.DoEng.TABLE_NAME);
        onCreate(db);
    }


}
