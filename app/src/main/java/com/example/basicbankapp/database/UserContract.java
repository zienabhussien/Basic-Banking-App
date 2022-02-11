package com.example.basicbankapp.database;

import android.provider.BaseColumns;

public class UserContract {
    private UserContract() {
    }

    public static final class UserEntry implements BaseColumns{
        /**  database Table name */
        public static final String TABLE_NAME = "user";

        /** Table fields */
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_USER_NAME = "name";
        public static final String COLUMN_USER_EMAIL = "email";
        public static final String COLUMN_USER_ACCOUNT_NUMBER = "accountNo";
        public static final String COLUMN_USER_PHONE_NO = "phoneNo";
        public static final String COLUMN_USER_IFSC_CODE = "ifscCode";
        public static final String COLUMN_USER_ACCOUNT_BALANCE = "balance";
    }
}
