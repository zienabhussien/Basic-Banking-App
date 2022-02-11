package com.example.basicbankapp.ui;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.basicbankapp.adapters.SendToUserAdapter;
import com.example.basicbankapp.database.TransactionHelper;
import com.example.basicbankapp.database.UserHelper;
import com.example.basicbankapp.data.User;
import com.example.basicbankapp.databinding.FragmentSendToUserListBinding;
import com.example.basicbankapp.database.TransactionContract.TransactionEntry;
import java.util.ArrayList;
import com.example.basicbankapp.database.UserContract.UserEntry;


public class SendToUserListFragment extends Fragment implements SendToUserAdapter.OnUserListener{
  FragmentSendToUserListBinding binding;
  SendToUserListFragmentArgs args;
  SendToUserAdapter adapter;
  UserHelper userHelper;
  ArrayList<User> users;
    int fromUserAccountNo, toUserAccountNo, toUserAccountBalance;
    String fromUserAccountName, fromUserAccountBalance, transferAmount, toUserAccountName;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         args = SendToUserListFragmentArgs.fromBundle(requireArguments());
         users = new ArrayList<>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSendToUserListBinding.inflate(inflater,container,false);

        User user = args.getSelectedUser();
        fromUserAccountName = user.getName();
        fromUserAccountNo = user.getAccountNumber();
        fromUserAccountBalance = String.valueOf(user.getBalance());
         transferAmount = args.getAmount();

         userHelper = new UserHelper(getContext());

         adapter = new SendToUserAdapter(users,this);
         adapter.notifyDataSetChanged();

         binding.sendToUserList.setHasFixedSize(true);
         binding.sendToUserList.setAdapter(adapter);

        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }



    @Override
    public void onUserClick(int position) {
        // display info in transaction list
        toUserAccountNo = users.get(position).getAccountNumber();
        toUserAccountName = users.get(position).getName();
        toUserAccountBalance = users.get(position).getBalance();

        calculateAmount();

      new TransactionHelper(getContext())
                  .insertTransferData(fromUserAccountName,toUserAccountName,transferAmount,1);

        Toast.makeText(getContext(), "Transaction successful!", Toast.LENGTH_LONG).show();
        NavHostFragment.findNavController(SendToUserListFragment.this)
                .navigate(SendToUserListFragmentDirections.actionSendToUserListFragmentToStartFragment());

    }

    private void calculateAmount() {
        Integer currentAmount = Integer.parseInt(fromUserAccountBalance);
        Integer transferAmountInt = Integer.parseInt(transferAmount);
        Integer remainingAmount = currentAmount - transferAmountInt;
        Integer increasedAmount = transferAmountInt + toUserAccountBalance;

        // Update amount in the dataBase
        new UserHelper(getContext()).updateAmount(fromUserAccountNo, remainingAmount);
        new UserHelper(getContext()).updateAmount(toUserAccountNo, increasedAmount);
    }
    // to handle click onBackPress btn
    @Override
    public void onAttach( Context context) {
        super.onAttach(context);

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                AlertDialog.Builder builder_exitBtn  = new AlertDialog.Builder(getContext());
                builder_exitBtn.setTitle("Do you want to cancel transaction ?").setCancelable(false)
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                TransactionHelper transactionHelper = new TransactionHelper(getContext());
                                SQLiteDatabase db = transactionHelper.getWritableDatabase();
                                ContentValues values = new ContentValues();
                                values.put(TransactionEntry.COLUMN_FROM_NAME,fromUserAccountName);
                                values.put(TransactionEntry.COLUMN_TO_NAME,toUserAccountName);
                                values.put(TransactionEntry.COLUMN_AMOUNT,transferAmount);
                                values.put(TransactionEntry.COLUMN_STATUS,0);

                                db.insert(TransactionEntry.TABLE_NAME,null,values);

                                Toast.makeText(getContext(), "Transaction Cancelled!", Toast.LENGTH_LONG).show();
                                NavHostFragment.findNavController(SendToUserListFragment.this)
                                        .navigate(SendToUserListFragmentDirections.actionSendToUserListFragmentToStartFragment());
                               getActivity().finish();

                            }
                        }).setNegativeButton("NO",null);

                AlertDialog dialog = builder_exitBtn.create();
                dialog.show();
            }
        };

        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    private void displayDatabaseInfo() {

     SQLiteDatabase db = userHelper.getReadableDatabase();
     String [] projections = {
                       UserEntry.COLUMN_USER_NAME,
                       UserEntry.COLUMN_USER_EMAIL,
                       UserEntry.COLUMN_USER_ACCOUNT_NUMBER,
                       UserEntry.COLUMN_USER_ACCOUNT_BALANCE,
                       UserEntry.COLUMN_USER_PHONE_NO,
                       UserEntry.COLUMN_USER_IFSC_CODE,};
        Cursor cursor = db.query(UserEntry.TABLE_NAME, projections, null, null,
                                                      null, null, null, null );

      try{
       int phoneNoColumnIndex = cursor.getColumnIndex(UserEntry.COLUMN_USER_PHONE_NO);
       int accountNoColumnIndex = cursor.getColumnIndex(UserEntry.COLUMN_USER_ACCOUNT_NUMBER);
       int accountBalanceColumnIndex = cursor.getColumnIndex(UserEntry.COLUMN_USER_ACCOUNT_BALANCE);
       int ifscCodeColumnIndex = cursor.getColumnIndex(UserEntry.COLUMN_USER_IFSC_CODE);
       int nameColumnIndex = cursor.getColumnIndex(UserEntry.COLUMN_USER_NAME);
       int emailColumnIndex = cursor.getColumnIndex(UserEntry.COLUMN_USER_EMAIL);

          while (cursor.moveToNext()){
              String currentName = cursor.getString(nameColumnIndex);
              String currentEmail = cursor.getString(emailColumnIndex);
              String phoneNumber = cursor.getString(phoneNoColumnIndex);
              String ifscCode = cursor.getString(ifscCodeColumnIndex);
              int accountNumber = cursor.getInt(accountNoColumnIndex);
              int accountBalance = cursor.getInt(accountBalanceColumnIndex);

              users.add(new User(currentName,accountBalance,
                           accountNumber,phoneNumber,ifscCode,currentEmail));
              adapter.setUsers(users);

          }
      }finally {
       cursor.close();
      }

    }
}