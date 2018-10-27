package com.example.rodrigosilva.shoppingapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import com.example.rodrigosilva.shoppingapp.model.Shoe;

import java.util.ArrayList;

public class ShoeDao extends BaseDao {

    public ShoeDao(Context context) {
        super(context);
    }

    public void remove() {
        this.openWritable();

        database.delete(Contract.Shoe.TABLE_NAME, null, null);

        this.close();
    }

    public Shoe findShoeById(int id) {
        this.openReadable();

        Shoe shoe = null;

        String whereClause = Contract.Shoe._ID + " = ?";

        try {
            Cursor cursor = database.query(Contract.Shoe.TABLE_NAME, // The table to query
                    null, // The columns to return
                    whereClause, // The columns for the WHERE clause
                    new String[]{String.valueOf(id)}, // The values for the WHERE clause
                    null, // don't group the rows
                    null, // don't filter by row groups
                    null // The sort order
            );

            cursor.moveToFirst();
            if (!cursor.isAfterLast()) {
                shoe = this.bindSQLite(cursor);
            }
            cursor.close();
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        } finally {
            this.close();
        }

        return shoe;
    }

    public ArrayList<Shoe> findAllShoes() {
        this.openReadable();

        ArrayList<Shoe> shoes = new ArrayList<>();

        try {
            Cursor cursor = database.query(Contract.Shoe.TABLE_NAME, // The table to query
                    null, // The columns to return
                    null, // The columns for the WHERE clause
                    null, // The values for the WHERE clause
                    null, // don't group the rows
                    null, // don't filter by row groups
                    null // The sort order
            );

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Shoe shoe = this.bindSQLite(cursor);
                shoes.add(shoe);
                cursor.moveToNext();
            }
            cursor.close();
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        } finally {
            this.close();
        }

        return shoes;

    }

    public ArrayList<Shoe> findAllShoes(int categoryId) {
        this.openReadable();

        ArrayList<Shoe> shoes = new ArrayList<>();

        String whereClause = Contract.Shoe.COLUMN_NAME_CATEGORY + " = ?";
        try {
            Cursor cursor = database.query(Contract.Shoe.TABLE_NAME, // The table to query
                    null, // The columns to return
                    whereClause, // The columns for the WHERE clause
                    new String[]{String.valueOf(categoryId)}, // The values for the WHERE clause
                    null, // don't group the rows
                    null, // don't filter by row groups
                    null // The sort order
            );

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Shoe shoe = this.bindSQLite(cursor);
                shoes.add(shoe);
                cursor.moveToNext();
            }
            cursor.close();
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        } finally {
            this.close();
        }

        return shoes;

    }

    public void insert(Shoe shoe) {
        this.openWritable();
        try {

            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            values.put(Contract.Shoe.COLUMN_NAME_NAME, shoe.getName());
            values.put(Contract.Shoe.COLUMN_NAME_CATEGORY, shoe.getCategory());
            values.put(Contract.Shoe.COLUMN_NAME_SIZE, shoe.getSize());
            values.put(Contract.Shoe.COLUMN_NAME_PRICE, shoe.getPrice());


            // Insert the new row, returning the primary key value of the
            // new row
            long rowId = 0;

            String selection = Contract.Shoe._ID + " == ?";
            String[] selectionArgs = { String.valueOf(shoe.getId()) };

            rowId = database.update(Contract.Shoe.TABLE_NAME, values, selection,
                    selectionArgs);

            if (rowId == 0) {
                database.insert(Contract.Shoe.TABLE_NAME, null, values);
            }

        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        } finally {
            close();
        }
    }

    private Shoe bindSQLite(Cursor cursor) {
        Shoe shoe = new Shoe();
        for (int i = 0; i < cursor.getColumnCount(); i++) {
            if (Contract.Shoe._ID.equals(cursor.getColumnName(i)))
                shoe.setId(cursor.getInt(i));
            else if (Contract.Shoe.COLUMN_NAME_NAME.equals(cursor.getColumnName(i)))
                shoe.setName(cursor.getString(i));
            else if (Contract.Shoe.COLUMN_NAME_CATEGORY.equals(cursor.getColumnName(i)))
                shoe.setCategory(cursor.getInt(i));
            else if (Contract.Shoe.COLUMN_NAME_SIZE.equals(cursor.getColumnName(i)))
                shoe.setSize(cursor.getInt(i));
            else if (Contract.Shoe.COLUMN_NAME_PRICE.equals(cursor.getColumnName(i)))
                shoe.setPrice(cursor.getFloat(i));
        }
        return shoe;
    }
}
