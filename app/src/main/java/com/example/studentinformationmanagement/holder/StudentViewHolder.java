package com.example.studentinformationmanagement.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentinformationmanagement.R;

public class StudentViewHolder extends RecyclerView.ViewHolder {
    public TextView txtStudentName,txtStudentCode;
    public CardView cardView;
    public StudentViewHolder(@NonNull View itemView) {
        super(itemView);
        txtStudentName=itemView.findViewById(R.id.txtStudentName);
        txtStudentCode=itemView.findViewById(R.id.txtStudentCode);
        cardView=itemView.findViewById(R.id.cardViewItemStudent);
    }
}
