package com.example.studentinformationmanagement.holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentinformationmanagement.R;

public class LoginHistoryViewHolder extends RecyclerView.ViewHolder{
    public TextView txtTime,txtDeviceName;

    public LoginHistoryViewHolder(@NonNull View itemView) {
        super(itemView);
        txtTime=itemView.findViewById(R.id.txtLoginTime);
        txtDeviceName=itemView.findViewById(R.id.txtDevice);
    }
}
