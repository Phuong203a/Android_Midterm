package com.example.studentinformationmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.studentinformationmanagement.dao.CertificateDAO;
import com.example.studentinformationmanagement.dao.StudentDAO;
import com.example.studentinformationmanagement.model.Certificate;
import com.example.studentinformationmanagement.model.Student;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.annotation.Nullable;

public class ImportFileActivity extends AppCompatActivity {

    private ImageView imgBack;
    private CardView cardViewImportStudent, cardViewImportCertificate;
    private static final int READ_REQUEST_CODE_STUDENT = 666666;
    private static final int READ_REQUEST_CODE_CERTIFICATE = 777777;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_file);
        imgBack = findViewById(R.id.imgBackImport);
        cardViewImportStudent = findViewById(R.id.cardViewImportStudent);
        cardViewImportCertificate = findViewById(R.id.cardViewImportCertificate);

        imgBack.setOnClickListener(t -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });
        cardViewImportStudent.setOnClickListener(v -> openFile(READ_REQUEST_CODE_STUDENT));
        cardViewImportCertificate.setOnClickListener(v -> openFile(READ_REQUEST_CODE_CERTIFICATE));

    }


    private void openFile(int type) {
        Intent intent = new Intent();
        intent.setType("*/*"); // You can set the MIME type according to your needs
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra(Intent.EXTRA_AUTO_LAUNCH_SINGLE_CHOICE, true);
        startActivityForResult(intent, type);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (data != null) {
                Uri uri = data.getData();
                if (requestCode == READ_REQUEST_CODE_STUDENT) {
                    importStudent(uri);
                } else if (requestCode == READ_REQUEST_CODE_CERTIFICATE) {
                    importCertificate(uri);
                }
            }
        }
    }

    private void importStudent(Uri uri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            if (inputStream != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                StudentDAO studentDAO = new StudentDAO();
                while ((line = reader.readLine()) != null) {
                    String[] values = line.split(",");
                    try {
                        Student student = new Student();
                        student.setDelete(false);
                        student.setCode(values[0].trim());
                        student.setName(values[1].trim());
                        studentDAO.createStudent(student);
                    } catch (Exception e) {
                        Log.d("Error importStudent", values[0].trim());
                    }
                }
                inputStream.close();
                Toast.makeText(this, "importStudent ok", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error reading CSV file", Toast.LENGTH_SHORT).show();
        }

    }

    private void importCertificate(Uri uri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            if (inputStream != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                CertificateDAO certificateDAO = new CertificateDAO();
                while ((line = reader.readLine()) != null) {
                    String[] values = line.split(",");
                    try {
                        Certificate certificate = new Certificate();
                        certificate.setDelete(false);
                        certificate.setName(values[0].trim());
                        certificate.setStudentCode(values[1].trim());
                        certificateDAO.createCertificate(certificate);
                    } catch (Exception e) {
                        Log.d("Error importCertificate", values[1].trim());
                    }
                }
                inputStream.close();
                Toast.makeText(this, "importCertificate ok", Toast.LENGTH_SHORT).show();

            }
        } catch (Exception e) {
            Toast.makeText(this, "Error reading CSV file", Toast.LENGTH_SHORT).show();
        }
    }
}