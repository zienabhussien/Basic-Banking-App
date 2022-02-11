package com.example.basicbankapp.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.basicbankapp.R;
import com.example.basicbankapp.data.User;
import com.example.basicbankapp.databinding.FragmentUserDataBinding;


public class UserDataFragment extends Fragment {
    FragmentUserDataBinding binding;
    UserDataFragmentArgs args;
    AlertDialog dialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUserDataBinding.inflate(inflater,container,false);
         displayData();
        binding.transferMoneyBtn.setOnClickListener(view -> {
            enterAmount();
        });



        return binding.getRoot();
    }

    private void enterAmount() {
     final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
     View mView = getLayoutInflater().inflate(R.layout.dialog_box,null);
     builder.setTitle("Enter Amount").setView(mView).setCancelable(false);

     final EditText amount = mView.findViewById(R.id.enter_money);
     builder.setPositiveButton("SEND", new DialogInterface.OnClickListener() {
         @Override
         public void onClick(DialogInterface dialogInterface, int i) {

         }
     }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
         @Override
         public void onClick(DialogInterface dialogInterface, int i)
         {
           dialogInterface.cancel();
           transactionCancel();
         }
     });

     dialog = builder.create();
     dialog.show();
     dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(view -> {
        int currentBalance = Integer.parseInt(String.valueOf(binding.availBalanceTv.getText()));
        if(amount.getText().toString().isEmpty()){
            amount.setError("Amount can't be empty");
        }else if(Integer.parseInt(amount.getText().toString())> currentBalance){
            amount.setError("your account don't enough money");
        }else{
          int accountNo = Integer.parseInt(binding.accountNoTv.getText().toString());
          String fromUserName = binding.nameTv.getText().toString();
          int fromUserAccountBalance = Integer.parseInt(binding.availBalanceTv.getText().toString());
          User user = new User(fromUserName,fromUserAccountBalance,accountNo);
          String transferAmount = amount.getText().toString();

            NavHostFragment.findNavController(UserDataFragment.this)
                    .navigate(UserDataFragmentDirections
                            .actionUserDataFragmentToSendToUserListFragment(user,transferAmount));
                      dialog.dismiss();
        }
     });

    }

    private void transactionCancel() {
       AlertDialog.Builder builderExitBtn = new AlertDialog.Builder(getContext());
       builderExitBtn.setTitle("Do you want to exit ?").setCancelable(false)
               .setPositiveButton("YES", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialogInterface, int i) {
               Toast.makeText(getContext(), "Transaction cancelled!", Toast.LENGTH_LONG).show();
           }
       }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialogInterface, int i) {
             dialogInterface.dismiss();
             enterAmount();
           }
       });
       AlertDialog dialog = builderExitBtn.create();
       dialog.show();
    }

    private void displayData() {
        args = UserDataFragmentArgs.fromBundle(requireArguments());
        User user = args.getCurrentUser();
        binding.accountNoTv.setText(user.getAccountNumber()+"");
        binding.availBalanceTv.setText(user.getBalance()+"");
        binding.nameTv.setText(user.getName());
        binding.emailTv.setText(user.getEmail());
        binding.ifscTv.setText(user.getIfscCode());
        binding.phoneNoTv.setText(user.getPhoneNumber());
    }
}