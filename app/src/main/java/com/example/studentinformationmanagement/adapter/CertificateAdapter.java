package com.example.studentinformationmanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentinformationmanagement.R;
import com.example.studentinformationmanagement.holder.CertificateViewHolder;
import com.example.studentinformationmanagement.holder.StudentViewHolder;
import com.example.studentinformationmanagement.model.Certificate;
import com.example.studentinformationmanagement.model.Student;
import com.example.studentinformationmanagement.util.Const;

import java.util.List;

public class CertificateAdapter extends  RecyclerView.Adapter<CertificateViewHolder>{
    Context context;
    List<Certificate> certificates;
    String currentUserRole;
    public CertificateAdapter(Context context, List<Certificate> certificates, String currentUserRole) {
        this.context = context;
        this.certificates = certificates;
        this.currentUserRole = currentUserRole;
    }
    @NonNull
    @Override
    public CertificateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CertificateViewHolder(LayoutInflater.from(context).inflate(R.layout.item_certificate_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CertificateViewHolder holder, int position) {
        holder.txtCertificateName.setText(certificates.get(position).getName());
        holder.cardView.setOnClickListener(v -> {
            if (currentUserRole.equals(Const.ROLE.EMPLOYEE)) {
                return;
            }
        });
    }

    @Override
    public int getItemCount() {
        return certificates.size();
    }
}
