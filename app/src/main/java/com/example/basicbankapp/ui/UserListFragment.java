package com.example.basicbankapp.ui;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.basicbankapp.adapters.UserListAdapter;
import com.example.basicbankapp.database.UserContract;
import com.example.basicbankapp.database.UserHelper;
import com.example.basicbankapp.data.User;
import com.example.basicbankapp.databinding.FragmentUserListBinding;

import java.util.ArrayList;


public class UserListFragment extends Fragment {
     FragmentUserListBinding binding;
    private ArrayList<User> users;
     UserListAdapter adapter;
     private UserHelper userHelper;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        users = new ArrayList<>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUserListBinding.inflate(inflater,container,false);
         userHelper = new UserHelper(getContext());
         displayDatabaseInfo();
        adapter = new UserListAdapter(getContext(),users);
        binding.userListRv.setHasFixedSize(true);
         binding.userListRv.setAdapter(adapter);

        return binding.getRoot();
    }

    private void displayDatabaseInfo() {
        users.clear();
        Cursor cursor = userHelper.readAllUsers();
        int phoneColumnIndex = cursor.getColumnIndex(UserContract.UserEntry.COLUMN_USER_PHONE_NO);
        int nameColumnIndex = cursor.getColumnIndex(UserContract.UserEntry.COLUMN_USER_NAME);
        int emailColumnIndex = cursor.getColumnIndex(UserContract.UserEntry.COLUMN_USER_EMAIL);
        int ifscCodeColumnIndex = cursor.getColumnIndex(UserContract.UserEntry.COLUMN_USER_IFSC_CODE);
        int userAccountBalanceColumnIndex = cursor.getColumnIndex(UserContract.UserEntry.COLUMN_USER_ACCOUNT_BALANCE);
        int userAccountNumberColumnIndex = cursor.getColumnIndex(UserContract.UserEntry.COLUMN_USER_ACCOUNT_NUMBER);

      while(cursor.moveToNext()){
         String name = cursor.getString(nameColumnIndex);
         String email = cursor.getString(emailColumnIndex);
         String phoneNumber = cursor.getString(phoneColumnIndex);
         String ifscCode = cursor.getString(ifscCodeColumnIndex);
         int accountNumber = cursor.getInt(userAccountNumberColumnIndex);
         int accountBalance = cursor.getInt(userAccountBalanceColumnIndex);
         users.add(new User(name,accountBalance,accountNumber,phoneNumber,ifscCode,email));
      }

    }
}