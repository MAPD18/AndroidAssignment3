package com.example.rodrigosilva.shoppingapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import com.example.rodrigosilva.shoppingapp.model.Customer;
import com.example.rodrigosilva.shoppingapp.model.Order;
import com.example.rodrigosilva.shoppingapp.model.Shoe;
import com.example.rodrigosilva.shoppingapp.model.Status;

import java.util.ArrayList;
import java.util.Date;

public class OrderDao extends BaseDao {

    public OrderDao(Context context) {
        super(context);
    }

    public void remove() {
        this.openWritable();

        database.delete(Contract.Order.TABLE_NAME, null, null);

        this.close();
    }

    public Order findOrderById(int id) {
        this.openReadable();

        Order order = null;

        String whereClause = Contract.Order._ID + " = ?";

        try {
            Cursor cursor = database.query(Contract.Order.TABLE_NAME, // The table to query
                    null, // The columns to return
                    whereClause, // The columns for the WHERE clause
                    new String[]{String.valueOf(id)}, // The values for the WHERE clause
                    null, // don't group the rows
                    null, // don't filter by row groups
                    null // The sort order
            );

            cursor.moveToFirst();
            if (!cursor.isAfterLast()) {
                order = this.bindSQLite(cursor);
            }
            cursor.close();
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        } finally {
            this.close();
        }

        return order;
    }

    public ArrayList<Order> findAllOrders() {
        this.openReadable();

        ArrayList<Order> orders = new ArrayList<>();

        try {
            Cursor cursor = database.query(Contract.Order.TABLE_NAME, // The table to query
                    null, // The columns to return
                    null, // The columns for the WHERE clause
                    null, // The values for the WHERE clause
                    null, // don't group the rows
                    null, // don't filter by row groups
                    null // The sort order
            );

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Order order = this.bindSQLite(cursor);
                orders.add(order);
                cursor.moveToNext();
            }
            cursor.close();
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        } finally {
            this.close();
        }

        return orders;

    }

    public ArrayList<Order> findAllOrdersByCustomer(int customerId) {
        this.openReadable();

        ArrayList<Order> orders = new ArrayList<>();

        String whereClause = Contract.Order.COLUMN_NAME_CUSTOMER + " = ?";
        try {
            Cursor cursor = database.query(Contract.Order.TABLE_NAME, // The table to query
                    null, // The columns to return
                    whereClause, // The columns for the WHERE clause
                    new String[]{String.valueOf(customerId)}, // The values for the WHERE clause
                    null, // don't group the rows
                    null, // don't filter by row groups
                    null // The sort order
            );

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Order order = this.bindSQLite(cursor);
                orders.add(order);
                cursor.moveToNext();
            }
            cursor.close();
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        } finally {
            this.close();
        }

        return orders;

    }

    public void insert(Order order) {
        this.openWritable();
        try {

            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            values.put(Contract.Order.COLUMN_NAME_CUSTOMER, order.getCustomer().getId());
            values.put(Contract.Order.COLUMN_NAME_SHOE, order.getShoe().getId());
            values.put(Contract.Order.COLUMN_NAME_QUANTITY, order.getQuantity());
            values.put(Contract.Order.COLUMN_NAME_STATUS, order.getStatus().name());
            values.put(Contract.Order.COLUMN_NAME_DATE, new Date().getTime());


            // Insert the new row, returning the primary key value of the
            // new row
            long rowId = 0;

            String selection = Contract.Order._ID + " == ?";
            String[] selectionArgs = { String.valueOf(order.getId()) };

            rowId = database.update(Contract.Order.TABLE_NAME, values, selection,
                    selectionArgs);

            if (rowId == 0) {
                database.insert(Contract.Order.TABLE_NAME, null, values);
            }

        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        } finally {
            close();
        }
    }

    private Order bindSQLite(Cursor cursor) {
        Order order = new Order();
        for (int i = 0; i < cursor.getColumnCount(); i++) {
            if (Contract.Order._ID.equals(cursor.getColumnName(i)))
                order.setId(cursor.getInt(i));
            else if (Contract.Order.COLUMN_NAME_CUSTOMER.equals(cursor.getColumnName(i))) {
                order.setCustomer(new Customer(cursor.getInt(i)));
            } else if (Contract.Order.COLUMN_NAME_SHOE.equals(cursor.getColumnName(i))) {
                ShoeDao shoeDao = new ShoeDao(getContext());
                order.setShoe(shoeDao.findShoeById(cursor.getInt(i)));
            } else if (Contract.Order.COLUMN_NAME_QUANTITY.equals(cursor.getColumnName(i)))
                order.setQuantity(cursor.getInt(i));
            else if (Contract.Order.COLUMN_NAME_STATUS.equals(cursor.getColumnName(i)))
                order.setStatus(cursor.getString(i));
        }
        return order;
    }
}
