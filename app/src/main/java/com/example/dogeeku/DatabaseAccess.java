package com.example.dogeeku;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;

    /**
     * Private constructor to aboid object creation from outside classes.
     *
     * @param context
     */
    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DabaseAccess
     */
    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    /**
     * Open the database connection.
     */
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    /**
     * Close the database connection.
     */
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    /**
     * Read all quotes from the database.
     *
     * @return a List of quotes
     */

    public List<String> getQuotes() {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM pengguna WHERE id_pengguna = '3'", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(2));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public String cekLogin(String emailDB, String passwordDB){
        String hasilLogin="";
        Cursor cursor = database.rawQuery("SELECT nama_lengkap FROM pengguna WHERE email = ? AND password = ?", new String[] {emailDB, passwordDB},
                null);
        if(cursor.getCount() != 0){
            cursor.moveToFirst();
            hasilLogin = cursor.getString(0);
            return hasilLogin;
        }
        else{
            return "kosong";
        }
    }

    public void insert(String namaDB, String emailDB, String passwordDB) {
        ContentValues contentValue = new ContentValues();
        contentValue.put("nama_lengkap", namaDB);
        contentValue.put("email", emailDB);
        contentValue.put("password", passwordDB);
        database.insert("pengguna", null, contentValue);
    }

    public List<String> getNama_Barang() {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM barang", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(1));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public List<String> getHarga_Barang() {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM barang", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(2));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }


    public void insertReservasi(String anjingDB, String layananDB, String tanggalDB) {
        ContentValues contentValue = new ContentValues();
        contentValue.put("nama_anjing", anjingDB);
        contentValue.put("nama_layanan", layananDB);
        contentValue.put("tanggal", tanggalDB);
        database.insert("reservasi", null, contentValue);
    }

    public List<String> getNama_Anjing() {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM reservasi", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(1));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public List<String> getNama_Layanan() {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM reservasi", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(2));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }
}
