package com.example.basicbankapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.basicbankapp.R;
import com.example.basicbankapp.data.Transaction;

import java.util.ArrayList;

public class TransactionHistoryAdapter extends RecyclerView.Adapter<TransactionHistoryAdapter.ViewHolder>{
 ArrayList<Transaction> transactionList;
 Context context;
    public TransactionHistoryAdapter(Context context,ArrayList<Transaction> transactionList) {
        this.transactionList = transactionList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                          .inflate(R.layout.transaction_history_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
         holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
       TextView fromUserName, toUserName, transferAmount,status;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fromUserName = itemView.findViewById(R.id.senderName_tv);
            toUserName = itemView.findViewById(R.id.receiverName_tv);
            status = itemView.findViewById(R.id.status_tv);
            transferAmount = itemView.findViewById(R.id.transfer_amount);
        }

        public void onBind(int position) {
            fromUserName.setText(transactionList.get(position).getFromUser());
            toUserName.setText(transactionList.get(position).getToUser());
            transferAmount.setText(String.format("%d",transactionList.get(position).getAmountTransferred()));
            if(transactionList.get(position).getStatus() == 1){
                 status.setText("Successful");
                status.setTextColor(ContextCompat.getColor(context,R.color.green));
            }else{
                status.setText("Failed");
              //  status.setTextColor(Color.red(R.color.red));
                toUserName.setText("No one selected!");
                status.setTextColor(ContextCompat.getColor(context,R.color.red));

            }
        }
    }
}
