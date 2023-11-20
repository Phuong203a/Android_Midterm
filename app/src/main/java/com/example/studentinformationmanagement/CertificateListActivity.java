package com.example.studentinformationmanagement;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.studentinformationmanagement.adapter.CertificateAdapter;
import com.example.studentinformationmanagement.adapter.StudentAdapter;
import com.example.studentinformationmanagement.dao.CertificateDAO;
import com.example.studentinformationmanagement.dao.UserDAO;
import com.example.studentinformationmanagement.model.Certificate;
import com.example.studentinformationmanagement.util.Const;

import java.util.List;

public class CertificateListActivity extends AppCompatActivity {
    private ImageView imageViewAddCertificate;
    private ImageView imgBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certificate_list);
        imageViewAddCertificate = findViewById(R.id.imgAddCertificate);
        imgBack = findViewById(R.id.imgBackCertificateList);

        Bundle extras = getIntent().getExtras();
        String studentCode = extras.getString(Const.FIELD.STUDENT_CODE);

        CertificateDAO certificateDAO = new CertificateDAO();
        UserDAO userDAO = new UserDAO();
        String role = userDAO.getCurrentUser(null).getRole();
        List<Certificate> certificates=certificateDAO.getListCertificate(studentCode);

        if (!certificates.isEmpty()) {
            RecyclerView recyclerView = findViewById(R.id.recycleViewListCertificate);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(new CertificateAdapter(getApplicationContext(), certificates,role));
        }
        if(role.equals(Const.ROLE.EMPLOYEE)){
            imageViewAddCertificate.setVisibility(View.GONE);
        }

        imageViewAddCertificate.setOnClickListener(v -> addNewCertificate( certificateDAO,studentCode));
        imgBack.setOnClickListener(v->finish());

    }
    private void addNewCertificate(CertificateDAO certificateDAO, String studentCode){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add new certificate");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("Create", (dialog, which) -> {
            Certificate certificate = new Certificate();
            certificate.setStudentCode(studentCode);
            certificate.setName(input.getText().toString().trim());
            certificate.setDelete(false);
            certificateDAO.createCertificate(certificate);
            finish();
            startActivity(getIntent());
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }
}