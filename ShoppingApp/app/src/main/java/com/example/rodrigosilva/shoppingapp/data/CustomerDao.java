package com.example.rodrigosilva.shoppingapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import com.example.rodrigosilva.shoppingapp.model.Customer;

public class CustomerDao extends BaseDao {

    public CustomerDao(Context context) {
        super(context);
    }

    public void remove() {
        this.openWritable();

        database.delete(Contract.Customer.TABLE_NAME, null, null);

        this.close();
    }

    public Customer findCustomer(String userName, String password) {
        this.openReadable();

        Customer result = null;

        String whereClause = Contract.Customer.COLUMN_NAME_USERNAME + " = ? AND " + Contract.Customer.COLUMN_NAME_PASSWORD + " = ?";

        try {
            Cursor cursor = database.query(Contract.Customer.TABLE_NAME, // The table to query
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

        String whereClause = Contract.Customer.COLUMN_NAME_USERNAME + " = ?";

        try {
            Cursor cursor = database.query(Contract.Customer.TABLE_NAME, // The table to query
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

    public void insert(Customer customer) {
        this.openWritable();
        try {

            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            values.put(Contract.Customer.COLUMN_NAME_USERNAME, customer.getUserName());
            values.put(Contract.Customer.COLUMN_NAME_PASSWORD, customer.getPassword());
            values.put(Contract.Customer.COLUMN_NAME_FIRST_NAME, customer.getFirstName());
            values.put(Contract.Customer.COLUMN_NAME_LAST_NAME, customer.getLastName());
            values.put(Contract.Customer.COLUMN_NAME_ADDRESS, customer.getAddress());
            values.put(Contract.Customer.COLUMN_NAME_CITY, customer.getCity());
            values.put(Contract.Customer.COLUMN_NAME_POSTAL_CODE, customer.getPostalCode());


            // Insert the new row, returning the primary key value of the
            // new row
            long rowId = 0;

            String selection = Contract.Customer._ID + " == ?";
            String[] selectionArgs = { String.valueOf(customer.getId()) };

            rowId = database.update(Contract.Customer.TABLE_NAME, values, selection,
                    selectionArgs);

            if (rowId == 0) {
                database.insert(Contract.Customer.TABLE_NAME, null, values);
            }

        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        } finally {
            close();
        }
    }

    private Customer bindSQLite(Cursor cursor) {
        Customer customer = new Customer();
        for (int i = 0; i < cursor.getColumnCount(); i++) {
            if (Contract.Customer._ID.equals(cursor.getColumnName(i)))
                customer.setId(cursor.getInt(i));
            else if (Contract.Customer.COLUMN_NAME_USERNAME.equals(cursor.getColumnName(i)))
                customer.setUserName(cursor.getString(i));
            else if (Contract.Customer.COLUMN_NAME_PASSWORD.equals(cursor.getColumnName(i)))
                customer.setPassword(cursor.getString(i));
            else if (Contract.Customer.COLUMN_NAME_FIRST_NAME.equals(cursor.getColumnName(i)))
                customer.setFirstName(cursor.getString(i));
            else if (Contract.Customer.COLUMN_NAME_LAST_NAME.equals(cursor.getColumnName(i)))
                customer.setLastName(cursor.getString(i));
            else if (Contract.Customer.COLUMN_NAME_ADDRESS.equals(cursor.getColumnName(i)))
                customer.setAddress(cursor.getString(i));
            else if (Contract.Customer.COLUMN_NAME_CITY.equals(cursor.getColumnName(i)))
                customer.setCity(cursor.getString(i));
            else if (Contract.Customer.COLUMN_NAME_POSTAL_CODE.equals(cursor.getColumnName(i)))
                customer.setPostalCode(cursor.getString(i));
        }
        return customer;
    }
}
