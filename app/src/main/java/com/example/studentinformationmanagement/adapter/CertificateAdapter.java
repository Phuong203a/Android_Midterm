package com.example.studentinformationmanagement.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentinformationmanagement.R;
import com.example.studentinformationmanagement.dao.CertificateDAO;
import com.example.studentinformationmanagement.holder.CertificateViewHolder;
import com.example.studentinformationmanagement.holder.StudentViewHolder;
import com.example.studentinformationmanagement.model.Certificate;
import com.example.studentinformationmanagement.model.Student;
import com.example.studentinformationmanagement.util.Const;

import java.util.List;

public class CertificateAdapter extends RecyclerView.Adapter<CertificateViewHolder> {
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
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getRootView().getContext());
            View dialogView = LayoutInflater.from(v.getRootView().getContext()).inflate(R.layout.certificate_dialog, null);
            dialogView.setBackgroundColor(Color.TRANSPARENT);

            EditText txtCertificateEdit = dialogView.findViewById(R.id.editTextCertificateEdit);
            Button btnEditCertificate = dialogView.findViewById(R.id.btnEditCertificate);
            Button btnDeleteCertificate = dialogView.findViewById(R.id.btnDeleteCertificate);

            txtCertificateEdit.setText(certificates.get(position).getName());

            btnEditCertificate.setOnClickListener(t->{
                certificates.get(position).setName(txtCertificateEdit.getText().toString());
                updateCertificate(position);
            });
            btnDeleteCertificate.setOnClickListener(t->{
                certificates.get(position).setDelete(true);
                updateCertificate(position);
            });
            builder.setView(dialogView);
            builder.setCancelable(true);

            builder.show();
        });
    }

    @Override
    public int getItemCount() {
        return certificates.size();
    }

    private void updateCertificate(int position){
        Certificate certificate = certificates.get(position);
        CertificateDAO certificateDAO = new CertificateDAO();
        certificateDAO.updateCertificate(certificate);
        if(certificate.isDelete()){
            certificates.remove(position);
        }
        this.notifyDataSetChanged(); //notify for change

    }
}
