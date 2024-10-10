package com.example.ecommerce;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Database Information
    private Context context;
    private static final String DB_NAME = "SHOPPHILE.DB";

    // Table Name
    public static final String TABLE_NAME = "PRODUCTS";

    // Table columns
    public static final String _ID = "_id";
    public static final String item_name = "item_name";
    public static final String item_price = "item_price";
    public static final String item_description = "item_description";
    public static final String brand = "brand";
    public static final String category = "category";

    // database version
    static final int DB_VERSION = 1;

    // Creating table query
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +
            _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            item_name + " TEXT NOT NULL, " +
            item_price + " REAL NOT NULL, " +
            brand + " TEXT, " +
            category + " TEXT, " +
            item_description + " TEXT);";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}