package com.example.basicbankapp.ui;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



import com.example.basicbankapp.R;
import com.example.basicbankapp.adapters.TransactionHistoryAdapter;
import com.example.basicbankapp.data.Transaction;
import com.example.basicbankapp.database.TransactionContract.TransactionEntry;
import com.example.basicbankapp.database.TransactionContract;
import com.example.basicbankapp.database.TransactionHelper;
import com.example.basicbankapp.databinding.FragmentTransactionHistoryBinding;

import java.util.ArrayList;

public class TransactionHistoryFragment extends Fragment {
  FragmentTransactionHistoryBinding binding;
  ArrayList<Transaction> transactionList;
  TransactionHistoryAdapter adapter;
  TransactionHelper dbHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         transactionList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTransactionHistoryBinding.inflate(inflater, container, false);

       dbHelper = new TransactionHelper(getContext());
        displayDatabaseInfo();

         adapter = new TransactionHistoryAdapter(getContext(),transactionList);
         adapter.notifyDataSetChanged();
         binding.transactionHistoryRv.setHasFixedSize(true);
         binding.transactionHistoryRv.setAdapter(adapter);

        return binding.getRoot();
    }

    private void displayDatabaseInfo() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String [] projection = { TransactionEntry.COLUMN_FROM_NAME,
                                 TransactionEntry.COLUMN_TO_NAME,
                                 TransactionEntry.COLUMN_AMOUNT,
                                 TransactionEntry.COLUMN_STATUS };
        Cursor cursor = db.query(TransactionEntry.TABLE_NAME,projection,
                  null,null,null,null,null);
       try {
           int fromNameColumnIndex = cursor.getColumnIndex(TransactionEntry.COLUMN_FROM_NAME);
           int toNameColumnIndex = cursor.getColumnIndex(TransactionEntry.COLUMN_TO_NAME);
           int amountColumnIndex = cursor.getColumnIndex(TransactionEntry.COLUMN_AMOUNT);
           int statusColumnIndex = cursor.getColumnIndex(TransactionEntry.COLUMN_STATUS);

           while (cursor.moveToNext()){
             String fromName = cursor.getString(fromNameColumnIndex);
             String toName = cursor.getString(toNameColumnIndex);
             int accountBalance = cursor.getInt(amountColumnIndex);
             int status = cursor.getInt(statusColumnIndex);

             transactionList.add(new Transaction(fromName,toName,accountBalance,status));
           }
       }finally {
           cursor.close();
       }

      if(transactionList.isEmpty()){
          binding.emptyText.setVisibility(View.VISIBLE);
      }else{
          binding.emptyText.setVisibility(View.GONE);
      }
    }
}