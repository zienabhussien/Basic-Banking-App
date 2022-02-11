package com.example.basicbankapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.basicbankapp.R;
import com.example.basicbankapp.data.User;

import java.util.ArrayList;

public class SendToUserAdapter extends RecyclerView.Adapter<SendToUserAdapter.ViewHolder>{
 ArrayList<User>userList;
 private OnUserListener onUserListener;
    public SendToUserAdapter(ArrayList<User> userList,OnUserListener onUserListener) {
        this.userList = userList;
        this.onUserListener = onUserListener;
    }

    public void setUsers(ArrayList<User>users){
        this.userList = users;
        notifyDataSetChanged();
    }
    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                         .inflate(R.layout.user_list_item,parent,false),onUserListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
          holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView userName, balance;
        OnUserListener onUserListener;
        public ViewHolder(@NonNull View itemView, OnUserListener onUserListener) {
            super(itemView);
            userName = itemView.findViewById(R.id.username);
            balance = itemView.findViewById(R.id.amount);
            this.onUserListener = onUserListener;
            itemView.setOnClickListener(this);
        }

        public void onBind(int position) {
            userName.setText(userList.get(position).getName());
            balance.setText(String.format("%d",userList.get(position).getBalance()));

        }

        @Override
        public void onClick(View view) {
              onUserListener.onUserClick(getAdapterPosition());
        }
    }
    public interface OnUserListener{
        void onUserClick(int position);
    }
}
