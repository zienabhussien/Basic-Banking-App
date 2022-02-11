package com.example.basicbankapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.basicbankapp.R;
import com.example.basicbankapp.data.User;
import com.example.basicbankapp.ui.UserListFragmentDirections;

import java.util.ArrayList;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder>{

    private ArrayList<User> userList;



    public UserListAdapter(Context context, ArrayList<User> userList) {
        this.userList = userList;

    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                              .inflate(R.layout.user_list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
         holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
       TextView userName;
       TextView userAccountBalance;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.username);
            userAccountBalance =  itemView.findViewById(R.id.amount);
        }

        public void onBind(int position) {
            userName.setText(userList.get(position).getName());
            userAccountBalance.setText(String.format("%d",userList.get(position).getBalance()));

            itemView.setOnClickListener(view -> {
              int accountNumber = userList.get(position).getAccountNumber();
              String name = userList.get(position).getName();
              String email = userList.get(position).getEmail();
              String ifscCode = userList.get(position).getIfscCode();
              String phoneNumber = userList.get(position).getPhoneNumber();
              int balance = userList.get(position).getBalance();

              User user = new User(name,balance,accountNumber,phoneNumber,ifscCode,email);
               Navigation.findNavController(view)
                       .navigate(UserListFragmentDirections.actionUserListToUserDataFragment(user));
            });
        }
    }



}
