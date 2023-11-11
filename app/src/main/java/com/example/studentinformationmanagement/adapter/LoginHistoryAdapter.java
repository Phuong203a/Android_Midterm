package com.example.studentinformationmanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentinformationmanagement.R;
import com.example.studentinformationmanagement.holder.LoginHistoryViewHolder;
import com.example.studentinformationmanagement.holder.MyUserAccountViewHolder;
import com.example.studentinformationmanagement.model.LoginHistory;
import com.example.studentinformationmanagement.model.User;

import java.util.List;

public class LoginHistoryAdapter extends RecyclerView.Adapter<LoginHistoryViewHolder>{
    Context context;
    List<LoginHistory> loginHistoryList;
    public LoginHistoryAdapter(Context context, List<LoginHistory> loginHistoryList) {
        this.context = context;
        this.loginHistoryList = loginHistoryList;
    }
    @NonNull
    @Override
    public LoginHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LoginHistoryViewHolder(LayoutInflater.from(context).inflate(R.layout.item_login_history,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull LoginHistoryViewHolder holder, int position) {
        holder.txtTime.setText(loginHistoryList.get(position).getLoginTime());
        holder.txtDeviceName.setText(loginHistoryList.get(position).getDeviceName());
    }

    @Override
    public int getItemCount() {
        return loginHistoryList.size();
    }
}
