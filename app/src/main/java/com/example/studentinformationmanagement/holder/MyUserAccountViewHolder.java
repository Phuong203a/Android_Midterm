package com.example.studentinformationmanagement.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentinformationmanagement.R;

public class MyUserAccountViewHolder extends RecyclerView.ViewHolder {
    public ImageView imageView;
    public TextView txtEmail,txtUserName;
    public CardView cardView;

    public MyUserAccountViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView=itemView.findViewById(R.id.imgAvatarItemUser);
        txtEmail=itemView.findViewById(R.id.txtEmailItemUser);
        txtUserName=itemView.findViewById(R.id.txtUsernameItemUser);
        cardView=itemView.findViewById(R.id.cardViewItemUser);
    }
}
