package com.example.rodrigosilva.shoppingapp.data;

import android.provider.BaseColumns;

final class Contract {

    private Contract() {}

    static class Customer implements BaseColumns {
        static final String TABLE_NAME = "customer";
        static final String COLUMN_NAME_USERNAME = "userName";
        static final String COLUMN_NAME_PASSWORD = "password";
        static final String COLUMN_NAME_FIRST_NAME = "firstName";
        static final String COLUMN_NAME_LAST_NAME = "lastName";
        static final String COLUMN_NAME_ADDRESS = "address";
        static final String COLUMN_NAME_CITY = "city";
        static final String COLUMN_NAME_POSTAL_CODE = "postalCode";
    }

    static class SalesRepresentative implements BaseColumns {
        static final String TABLE_NAME = "salesRepresentative";
        static final String COLUMN_NAME_USERNAME = "userName";
        static final String COLUMN_NAME_PASSWORD = "password";
        static final String COLUMN_NAME_FIRST_NAME = "firstName";
        static final String COLUMN_NAME_LAST_NAME = "lastName";
    }

    static class Shoe implements BaseColumns {
        static final String TABLE_NAME = "shoe";
        static final String COLUMN_NAME_NAME = "name";
        static final String COLUMN_NAME_CATEGORY = "category";
        static final String COLUMN_NAME_SIZE = "size";
        static final String COLUMN_NAME_PRICE = "price";
    }

    static class ShoeCategory implements BaseColumns {
        static final String TABLE_NAME = "shoeCategory";
        static final String COLUMN_NAME_DESCRIPTION = "description";
    }

    static class Order implements BaseColumns {
        static final String TABLE_NAME = "orderEntry";
        static final String COLUMN_NAME_CUSTOMER = "customer";
        static final String COLUMN_NAME_SHOE = "shoe";
        static final String COLUMN_NAME_DATE = "date";
        static final String COLUMN_NAME_QUANTITY = "quantity";
        static final String COLUMN_NAME_STATUS = "status";
    }
}
