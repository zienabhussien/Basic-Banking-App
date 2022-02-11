package com.example.basicbankapp.ui;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.basicbankapp.R;
import com.example.basicbankapp.database.TransactionContract;
import com.example.basicbankapp.database.TransactionHelper;
import com.example.basicbankapp.databinding.FragmentStartBinding;

public class StartFragment extends Fragment {
    FragmentStartBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentStartBinding.inflate(inflater, container, false);
        binding.usersBtn.setOnClickListener(view -> {
            NavHostFragment.findNavController(StartFragment.this)
                    .navigate(StartFragmentDirections.actionStartFragmentToUserList());
        });
        binding.transactionBtn.setOnClickListener(view -> {
            NavHostFragment.findNavController(StartFragment.this)
                    .navigate(StartFragmentDirections.actionStartFragmentToTransactionHistoryFragment());
        });
        return binding.getRoot();
    }

}
