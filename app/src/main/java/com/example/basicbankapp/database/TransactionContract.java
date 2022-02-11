package com.example.basicbankapp.database;

import android.provider.BaseColumns;

public class TransactionContract {

    private TransactionContract(){
    }
    public static final class TransactionEntry implements BaseColumns{

        /**Name of database table */
        public static final String TABLE_NAME = "Transaction_table";

        /** Table fields */
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_FROM_NAME = "from_name";
        public static final String COLUMN_TO_NAME = "to_name";
        public static final String COLUMN_AMOUNT = "amount";
        public static final String COLUMN_STATUS = "status";

    }

}
