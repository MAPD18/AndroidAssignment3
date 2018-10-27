package com.example.rodrigosilva.shoppingapp.data;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class BaseDao {

    private Context context;
    SQLiteDatabase database;
    private DBHelper dbHelper;

    BaseDao(Context context) {
        this.context = context;
    }

    BaseDao openWritable() throws SQLException {
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    BaseDao openReadable() throws SQLException {
        dbHelper = new DBHelper(context);
        database = dbHelper.getReadableDatabase();
        return this;
    }

    protected void close() {
        dbHelper.close();
    }

    Context getContext() {
        return this.context;
    }

    protected SQLiteDatabase getDatabase() {
        return database;
    }
}
