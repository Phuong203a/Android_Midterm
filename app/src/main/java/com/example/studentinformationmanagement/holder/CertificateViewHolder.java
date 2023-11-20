package com.example.studentinformationmanagement.holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentinformationmanagement.R;

public class CertificateViewHolder extends RecyclerView.ViewHolder{
    public TextView txtCertificateName;
    public CardView cardView;
    public CertificateViewHolder(@NonNull View itemView) {
        super(itemView);
        txtCertificateName=itemView.findViewById(R.id.txtCertificate);
        cardView=itemView.findViewById(R.id.cardViewItemCertificate);
    }
}
