package com.example.ecommerce;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {

    private DatabaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(String name, String desc, String price, String brand, String category) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.item_name, name);
        contentValue.put(DatabaseHelper.item_description, desc);
        contentValue.put(DatabaseHelper.item_price, price);
        contentValue.put(DatabaseHelper.brand, brand);
        contentValue.put(DatabaseHelper.category, category);
        database.insert(DatabaseHelper.TABLE_NAME, null, contentValue);
    }

    public Cursor fetch() {
        String[] columns = new String[] { DatabaseHelper._ID, DatabaseHelper.item_name, DatabaseHelper.item_price, DatabaseHelper.brand, DatabaseHelper.item_description,  DatabaseHelper.category};
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(long _id, String name, String desc, String price, String brand, String category) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.item_name, name);
        contentValues.put(DatabaseHelper.item_description, desc);
        contentValues.put(DatabaseHelper.item_price, price);
        contentValues.put(DatabaseHelper.brand, brand);
        contentValues.put(DatabaseHelper.category, category);
        int i = database.update(DatabaseHelper.TABLE_NAME, contentValues, DatabaseHelper._ID + " = " + _id, null);
        return i;
    }

    public void delete(long _id) {
        database.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper._ID + "=" + _id, null);
    }

}