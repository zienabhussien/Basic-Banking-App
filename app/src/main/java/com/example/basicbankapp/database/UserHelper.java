package com.example.basicbankapp.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.basicbankapp.database.UserContract.UserEntry;
public class UserHelper extends SQLiteOpenHelper {

   String TABLE_NAME = UserContract.UserEntry.TABLE_NAME;

    /** name of tha database file*/
    private static final String DATABASE_NAME = "User.db";

    private static final int DATABASE_VERSION = 1;
    public UserHelper( Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the pets table
        String SQL_CREATE_USER_TABLE =  "CREATE TABLE " + UserEntry.TABLE_NAME + " ("
                + UserEntry.COLUMN_USER_ACCOUNT_NUMBER + " INTEGER, "
                + UserEntry.COLUMN_USER_NAME + " VARCHAR, "
                + UserEntry.COLUMN_USER_EMAIL + " VARCHAR, "
                + UserEntry.COLUMN_USER_IFSC_CODE + " VARCHAR, "
                + UserEntry.COLUMN_USER_PHONE_NO + " VARCHAR, "
                + UserEntry.COLUMN_USER_ACCOUNT_BALANCE + " INTEGER NOT NULL);";

             db.execSQL(SQL_CREATE_USER_TABLE);

             // insert to table
        db.execSQL("insert into "+ TABLE_NAME + " values(7860,'Amr Ali', 'amr@gmail.com','7584','01078956412', 15000)");
        db.execSQL("insert into "+ TABLE_NAME + " values(4356, 'Mazen Ahmed','mazen@gmail.com','4536','01098074587',48000)");
        db.execSQL("insert into "+ TABLE_NAME + " values(1023, 'Moataz Monier','moataz@gmail.com','8765','01098074587',86000)");
        db.execSQL("insert into "+ TABLE_NAME + " values(8096, 'Omar Kattab','omar@gmail.com','2231','01111326547',700)");
        db.execSQL("insert into "+ TABLE_NAME + " values(3214, 'Mohamed Wael','mohamed@gmail.com','9087','01155342198',20000)");
        db.execSQL("insert into "+ TABLE_NAME + " values(6054, 'Khaled Ahmed','khaled@gmail.com','6554','01596970587',55000)");
        db.execSQL("insert into "+ TABLE_NAME + " values(9876, 'Hassan Nour','hassan@gmail.com','4576','01098074587',48000)");
        db.execSQL("insert into "+ TABLE_NAME + " values(4432, 'Anas Mohamed','anas@gmail.com','3322','01066074087',9000)");
        db.execSQL("insert into "+ TABLE_NAME + " values(1002, 'Fatima Khaled','fatima@gmail.com','1332','01255074587',5000)");
        db.execSQL("insert into "+ TABLE_NAME + " values(5789, 'Nagwa Moataz','nagwa@gmail.com','2008','01098074587',48000)");
        db.execSQL("insert into "+ TABLE_NAME + " values(6098, 'Rayaan Munier','rayan@gmail.com','5004','01098074587',48000)");
        db.execSQL("insert into "+ TABLE_NAME + " values(3698,'Jhon Pratap', 'jhon@gmail.com','1207','01295640215', 4500)");
        db.execSQL("insert into "+ TABLE_NAME + " values(7853,'Morries Jain', 'morries@gmail.com','4522','0155021539', 2500)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
         if(i != i1){
             db.execSQL("DROP TABLE IF EXISTS " + UserEntry.TABLE_NAME );
             onCreate(db);
         }
    }

    public Cursor readAllUsers(){
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM "+UserEntry.TABLE_NAME,null);
        return cursor;
    }
   public void updateAmount(int accountNo, int amount){
       SQLiteDatabase database = this.getWritableDatabase();
       database.execSQL(" update "+ UserEntry.TABLE_NAME+" set "+
                         UserEntry.COLUMN_USER_ACCOUNT_BALANCE + " = "+ amount + " where "+
                         UserEntry.COLUMN_USER_ACCOUNT_NUMBER + " = "+ accountNo );
   }
}
