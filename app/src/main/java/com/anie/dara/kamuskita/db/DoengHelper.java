package com.anie.dara.kamuskita.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.anie.dara.kamuskita.kamus;

import java.util.ArrayList;

import static android.os.Build.ID;

public class DoengHelper {

    private Context context;
    private DatabaseHelper dbhelp;

    private SQLiteDatabase db;

    public DoengHelper (Context context){
        this.context = context;
    }

    public DoengHelper open() throws SQLException {
        dbhelp = new DatabaseHelper(context);
        db = dbhelp.getWritableDatabase();
        return this;
    }

    public void close(){
        dbhelp.close();
    }

    public ArrayList<kamus> getAllData(){
        String query = "select * from " + DatabaseContract.DoEng.TABLE_NAME + " ORDER BY " + DatabaseContract.DoEng._ID +" ASC limit 1000";
        Log.d("qe",query);

        Cursor cursor = db.rawQuery(query,new String[] { });
        Log.d("cursor", String.valueOf(cursor.getPosition()));
        cursor.moveToFirst();

        ArrayList<kamus> arrayList = new ArrayList<>();
        kamus kamusModel;

        if (cursor.getCount()>0) {
            do {
                kamusModel = new kamus();
                kamusModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.DoEng._ID)));
                kamusModel.setKeyword(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.DoEng.KEY)));
                kamusModel.setArti(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.DoEng.ARTI)));
                arrayList.add(kamusModel);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        Log.d("array", String.valueOf(arrayList.size()));
        cursor.close();
        return arrayList;
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
        contentValues.put(DatabaseContract.DoEng.KEY, keyword);
        contentValues.put(DatabaseContract.DoEng.ARTI, arti);
        long hasil = db.insert(DatabaseContract.DoEng.TABLE_NAME, null, contentValues);
        if(hasil == -1){
            return false;
        }else{
            return true;
        }

    }

}
