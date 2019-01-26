package com.anie.dara.kamuskita.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;


import com.anie.dara.kamuskita.kamus;

import java.util.ArrayList;

import static android.os.Build.ID;

public class KamusHelper {
    private Context context;
    private DatabaseHelper dbhelp;

    private SQLiteDatabase db;

    public KamusHelper(Context context){
        this.context = context;
    }

    public KamusHelper open() throws SQLException {
        dbhelp = new DatabaseHelper(context);
        db = dbhelp.getWritableDatabase();
        return this;
    }

    public void close(){
        dbhelp.close();
    }

   public ArrayList<kamus> cariKeyword(String key){
        String query = "select * from " + DatabaseContract.EngDo.TABLE_NAME + " WHERE " + DatabaseContract.EngDo.COLUMN_NAME_KEYWORD + " LIKE '" + key +"%' LIMIT 1000";
        Cursor cursor = db.rawQuery(query,new String[] { });
        cursor.moveToFirst();
        ArrayList<kamus> arrayList = new ArrayList<>();
        kamus kamusModel;

        if (cursor.getCount()>0) {
            do {
                kamusModel = new kamus();
                kamusModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.EngDo._ID)));
                kamusModel.setKeyword(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.EngDo.COLUMN_NAME_KEYWORD)));
                kamusModel.setArti(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.EngDo.COLUMN_NAME_ARTI)));
                arrayList.add(kamusModel);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }


    public ArrayList<kamus> getAllData(){
        String query = "select * from " + DatabaseContract.EngDo.TABLE_NAME + " ORDER BY " + DatabaseContract.EngDo._ID +" ASC limit 1000";
        Cursor cursor = db.rawQuery(query,new String[] { });
        cursor.moveToFirst();

        ArrayList<kamus> arrayList = new ArrayList<>();
        kamus kamusModel;

        if (cursor.getCount()>0) {
            do {
                kamusModel = new kamus();
                kamusModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.EngDo._ID)));
                kamusModel.setKeyword(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.EngDo.COLUMN_NAME_KEYWORD)));
                kamusModel.setArti(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.EngDo.COLUMN_NAME_ARTI)));
                arrayList.add(kamusModel);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insert(kamus kamusModel){
        ContentValues initialValues =  new ContentValues();
        initialValues.put(DatabaseContract.EngDo.COLUMN_NAME_KEYWORD, kamusModel.getKeyword());
        initialValues.put(DatabaseContract.EngDo.COLUMN_NAME_ARTI, kamusModel.getArti());
        return db.insert(DatabaseContract.EngDo.TABLE_NAME, null, initialValues);
    }

    public void beginTransaction(){
        db.beginTransaction();
    }

    public void setTransactionSuccess(){
        db.setTransactionSuccessful();
    }

    public void endTransaction(){
        db.endTransaction();
    }

    public boolean insertdata(String keyword, String arti) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseContract.EngDo.COLUMN_NAME_KEYWORD, keyword);
        contentValues.put(DatabaseContract.EngDo.COLUMN_NAME_ARTI, arti);
        long result = db.insert(DatabaseContract.EngDo.TABLE_NAME, null, contentValues);

        if (result == -1) {
            return false;
        } else {

            return true;
        }
    }

}
