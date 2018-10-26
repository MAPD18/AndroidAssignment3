package com.example.rodrigosilva.shoppingapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "ShoppingApp.db";

    private static final String SQL_CREATE_CUSTOMER =
            "CREATE TABLE IF NOT EXISTS " + Contract.Customer.TABLE_NAME + " (" +
                    Contract.Customer._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    Contract.Customer.COLUMN_NAME_USERNAME + " TEXT UNIQUE," +
                    Contract.Customer.COLUMN_NAME_PASSWORD + " TEXT," +
                    Contract.Customer.COLUMN_NAME_FIRST_NAME + " TEXT," +
                    Contract.Customer.COLUMN_NAME_LAST_NAME + " TEXT," +
                    Contract.Customer.COLUMN_NAME_ADDRESS + " TEXT," +
                    Contract.Customer.COLUMN_NAME_CITY + " TEXT," +
                    Contract.Customer.COLUMN_NAME_POSTAL_CODE + " TEXT)";

    private static final String SQL_DELETE_CUSTOMER =
            "DROP TABLE IF EXISTS " + Contract.Customer.TABLE_NAME;

    private static final String SQL_CREATE_CUSTOMER_SALES_REPRESENTATIVE =
            "CREATE TABLE IF NOT EXISTS " + Contract.SalesRepresentative.TABLE_NAME + " (" +
                    Contract.SalesRepresentative._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    Contract.SalesRepresentative.COLUMN_NAME_USERNAME + " TEXT UNIQUE," +
                    Contract.SalesRepresentative.COLUMN_NAME_PASSWORD + " TEXT," +
                    Contract.SalesRepresentative.COLUMN_NAME_FIRST_NAME + " TEXT," +
                    Contract.SalesRepresentative.COLUMN_NAME_LAST_NAME + " TEXT)";

    private static final String SQL_DELETE_CUSTOMER_SALES_REPRESENTATIVE =
            "DROP TABLE IF EXISTS " + Contract.SalesRepresentative.TABLE_NAME;

    private static final String SQL_CREATE_SHOE =
            "CREATE TABLE IF NOT EXISTS " + Contract.Shoe.TABLE_NAME + " (" +
                    Contract.Shoe._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    Contract.Shoe.COLUMN_NAME_NAME + " TEXT," +
                    Contract.Shoe.COLUMN_NAME_CATEGORY + " INTEGER," +
                    Contract.Shoe.COLUMN_NAME_SIZE + " INTEGER," +
                    Contract.Shoe.COLUMN_NAME_PRICE + " REAL)";

    private static final String SQL_DELETE_SHOE =
            "DROP TABLE IF EXISTS " + Contract.Shoe.TABLE_NAME;

    private static final String SQL_CREATE_SHOE_CATEGORY =
            "CREATE TABLE IF NOT EXISTS " + Contract.ShoeCategory.TABLE_NAME + " (" +
                    Contract.ShoeCategory._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    Contract.ShoeCategory.COLUMN_NAME_DESCRIPTION + " TEXT)";

    private static final String SQL_DELETE_SHOE_CATEGORY =
            "DROP TABLE IF EXISTS " + Contract.ShoeCategory.TABLE_NAME;

    private static final String SQL_CREATE_ORDER =
            "CREATE TABLE IF NOT EXISTS " + Contract.Order.TABLE_NAME + " (" +
                    Contract.Order._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    Contract.Order.COLUMN_NAME_CUSTOMER + " INTEGER," +
                    Contract.Order.COLUMN_NAME_SHOE + " INTEGER," +
                    Contract.Order.COLUMN_NAME_DATE + " REAL," +
                    Contract.Order.COLUMN_NAME_QUANTITY + " INTEGER," +
                    Contract.Order.COLUMN_NAME_STATUS + " INTEGER)";

    private static final String SQL_DELETE_ORDER =
            "DROP TABLE IF EXISTS " + Contract.Order.TABLE_NAME;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_CUSTOMER);
        db.execSQL(SQL_CREATE_CUSTOMER_SALES_REPRESENTATIVE);
        db.execSQL(SQL_CREATE_SHOE);
        db.execSQL(SQL_CREATE_SHOE_CATEGORY);
        db.execSQL(SQL_CREATE_ORDER);
        db.execSQL(SQL_CREATE_CUSTOMER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_CUSTOMER);
        db.execSQL(SQL_DELETE_CUSTOMER_SALES_REPRESENTATIVE);
        db.execSQL(SQL_DELETE_SHOE);
        db.execSQL(SQL_DELETE_SHOE_CATEGORY);
        db.execSQL(SQL_DELETE_ORDER);
        db.execSQL(SQL_DELETE_CUSTOMER);
        onCreate(db);
    }
}
