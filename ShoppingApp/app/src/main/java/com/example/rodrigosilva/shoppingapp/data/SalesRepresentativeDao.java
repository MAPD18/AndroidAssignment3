package com.example.rodrigosilva.shoppingapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import com.example.rodrigosilva.shoppingapp.model.SalesRepresentative;

public class SalesRepresentativeDao extends BaseDao {

    public SalesRepresentativeDao(Context context) {
        super(context);
    }

    public void remove() {
        this.openWritable();

        database.delete(Contract.SalesRepresentative.TABLE_NAME, null, null);

        this.close();
    }

    public SalesRepresentative findSalesRepresentative(String userName, String password) {
        this.openReadable();

        SalesRepresentative result = null;

        String whereClause = Contract.SalesRepresentative.COLUMN_NAME_USERNAME + " = ? AND " + Contract.SalesRepresentative.COLUMN_NAME_PASSWORD + " = ?";

        try {
            Cursor cursor = database.query(Contract.SalesRepresentative.TABLE_NAME, // The table to query
                    null, // The columns to return
                    whereClause, // The columns for the WHERE clause
                    new String[]{userName, password}, // The values for the WHERE clause
                    null, // don't group the rows
                    null, // don't filter by row groups
                    null // The sort order
            );

            cursor.moveToFirst();
            if (!cursor.isAfterLast()) {
                result = this.bindSQLite(cursor);
            }
            cursor.close();
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        } finally {
            this.close();
        }

        return result;

    }

    public boolean checkUserNameAlreadyExists(String userName) {
        this.openReadable();

        boolean result = false;

        String whereClause = Contract.SalesRepresentative.COLUMN_NAME_USERNAME + " = ?";

        try {
            Cursor cursor = database.query(Contract.SalesRepresentative.TABLE_NAME, // The table to query
                    null, // The columns to return
                    whereClause, // The columns for the WHERE clause
                    new String[]{userName}, // The values for the WHERE clause
                    null, // don't group the rows
                    null, // don't filter by row groups
                    null // The sort order
            );

            if (cursor.getCount() > 0)
                result = true;
            cursor.close();
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        } finally {
            this.close();
        }
        return result;
    }

    public void insert(SalesRepresentative salesRepresentative) {
        this.openWritable();
        try {

            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            values.put(Contract.Customer.COLUMN_NAME_USERNAME, salesRepresentative.getUserName());
            values.put(Contract.Customer.COLUMN_NAME_PASSWORD, salesRepresentative.getPassword());
            values.put(Contract.Customer.COLUMN_NAME_FIRST_NAME, salesRepresentative.getFirstName());
            values.put(Contract.Customer.COLUMN_NAME_LAST_NAME, salesRepresentative.getLastName());


            // Insert the new row, returning the primary key value of the
            // new row
            long rowId = 0;

            String selection = Contract.SalesRepresentative._ID + " == ?";
            String[] selectionArgs = { String.valueOf(salesRepresentative.getId()) };

            rowId = database.update(Contract.SalesRepresentative.TABLE_NAME, values, selection,
                    selectionArgs);

            if (rowId == 0) {
                database.insert(Contract.SalesRepresentative.TABLE_NAME, null, values);
            }

        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        } finally {
            close();
        }
    }

    private SalesRepresentative bindSQLite(Cursor cursor) {
        SalesRepresentative salesRepresentative = new SalesRepresentative();
        for (int i = 0; i < cursor.getColumnCount(); i++) {
            if (Contract.Customer._ID.equals(cursor.getColumnName(i)))
                salesRepresentative.setId(cursor.getInt(i));
            else if (Contract.Customer.COLUMN_NAME_USERNAME.equals(cursor.getColumnName(i)))
                salesRepresentative.setUserName(cursor.getString(i));
            else if (Contract.Customer.COLUMN_NAME_PASSWORD.equals(cursor.getColumnName(i)))
                salesRepresentative.setPassword(cursor.getString(i));
            else if (Contract.Customer.COLUMN_NAME_FIRST_NAME.equals(cursor.getColumnName(i)))
                salesRepresentative.setFirstName(cursor.getString(i));
            else if (Contract.Customer.COLUMN_NAME_LAST_NAME.equals(cursor.getColumnName(i)))
                salesRepresentative.setLastName(cursor.getString(i));
        }
        return salesRepresentative;
    }
}
