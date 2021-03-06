package com.manajemeninformasi.riska.findingtutor.setting;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Isja on 15/02/2017.
 */

public class Database extends SQLiteOpenHelper {
    private static final String DB_NAME = "dblocal.db";
    private static final String TABLE_NAME = "user";
    public static final String COLUMN_ID = "id_user";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_JENIS = "jenis_user";
    public static final String COLUMN_ALAMAT = "alamat";
    public static final String COLUMN_JK = "jeniskelamin";
    public static final String COLUMN_USIA = "usia";
    public static final String COLUMN_FLAG = "flag";
    public static final int VERSION = 2;
    public Database(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists "+TABLE_NAME+"("+COLUMN_ID+" TEXT, "+COLUMN_USERNAME+" TEXT, "+COLUMN_NAME+" TEXT, "
                +COLUMN_ALAMAT+" TEXT, "+COLUMN_JENIS+" TEXT, "+COLUMN_JK+" TEXT, "+COLUMN_USIA+" TEXT, "+COLUMN_FLAG+" TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS"+TABLE_NAME);
    }
    public void add(String id, String username, String name, String alamat, String jeniskelamin, String jenis, String usia)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, id);
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_ALAMAT, alamat);
        values.put(COLUMN_JK, jeniskelamin);
        values.put(COLUMN_JENIS, jenis);
        values.put(COLUMN_USIA,usia);
        db.insert(TABLE_NAME,null,values);
    }
    public void delete()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        Log.d("data terhapus", "cek");
    }
    public String cekLogin()
    {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        if (cursor.getCount()==0)
        {
            return "";
        }
        String jenis = cursor.getString(cursor.getColumnIndex("jenis_user"));
        cursor.close();
        return jenis;
    }
    public String getIduser()
    {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        if (cursor.getCount()==0)
        {
            return "";
        }
        String id_user = cursor.getString(cursor.getColumnIndex("id_user"));
        cursor.close();
        return id_user;
    }
    public String getJenis()
    {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        if (cursor.getCount()==0)
        {
            return "";
        }
        String jenis = cursor.getString(cursor.getColumnIndex("jenis_user"));
        cursor.close();
        return jenis;
    }
    public String getJenisKelamin()
    {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        if (cursor.getCount()==0)
        {
            return "";
        }
        String jeniskelamin = cursor.getString(cursor.getColumnIndex("jeniskelamin"));
        cursor.close();
        return jeniskelamin;
    }
    public String getUsia()
    {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        if (cursor.getCount()==0)
        {
            return "";
        }
        String usia = cursor.getString(cursor.getColumnIndex("usia"));
        cursor.close();
        return usia;
    }
    public String getUsername()
    {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        if(cursor.getCount()==0)
        {
            return "";
        }
        String username = cursor.getString(cursor.getColumnIndex("username"));
        cursor.close();
        return username;
    }
    public String getNameuser()
    {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        if(cursor.getCount()==0)
        {
            return "";
        }
        String nameuser = cursor.getString(cursor.getColumnIndex("name"));
        cursor.close();
        return nameuser;
    }
    public String getAlamatuser()
    {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        if(cursor.getCount()==0)
        {
            return "";
        }
        String alamat = cursor.getString(cursor.getColumnIndex("alamat"));
        cursor.close();
        return alamat;
    }
    public void updateAlamat(String username, String alamat)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("alamat",alamat);
        db.update(TABLE_NAME,values,"username='"+username+"'",null);
        Log.d("data masuk", alamat);
    }
    public void updateFlag(String punish)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("flag",punish);
        db.update(TABLE_NAME,values,null,null);
    }
    public String selectFlag()
    {
        String flag;
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        if(cursor.getCount()==0)
        {
            return "";
        }
        if (cursor.isNull(7))
        {
            flag = "kosong";
        }
        else
        flag = cursor.getString(cursor.getColumnIndex("flag"));
        cursor.close();
        return flag;
    }

}
