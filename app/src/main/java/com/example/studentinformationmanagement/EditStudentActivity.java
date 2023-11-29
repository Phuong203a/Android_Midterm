package com.example.studentinformationmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentinformationmanagement.dao.StudentDAO;
import com.example.studentinformationmanagement.model.Student;
import com.example.studentinformationmanagement.util.Const;

public class EditStudentActivity extends AppCompatActivity {
    ImageView imgBack, imgViewCertificate;
    EditText editTextName;
    TextView txtStudentCode;
    Button btnEdit, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student);
        imgBack = findViewById(R.id.imgBackEditStudent);
        imgViewCertificate = findViewById(R.id.imageViewCertificate);
        editTextName = findViewById(R.id.editTxtUserNameEditStudent);
        txtStudentCode = findViewById(R.id.txtStudentCodeEdit);
        btnEdit = findViewById(R.id.btnEditStudent);
        btnDelete = findViewById(R.id.btnDeleteStudent);

        Bundle extras = getIntent().getExtras();
        String code = extras.getString(Const.FIELD.CODE);
        String currentRole = extras.getString(Const.FIELD.ROLE);

        StudentDAO studentDAO = new StudentDAO();
        Student student = studentDAO.getStudentByCode(code);

        txtStudentCode.setText(student.getCode());
        editTextName.setText(student.getName());

        imgBack.setOnClickListener(v -> backToListStudent());
        imgViewCertificate.setOnClickListener(v -> viewCertificate(code));

        if(currentRole.equals(Const.ROLE.EMPLOYEE)){
            btnEdit.setVisibility(View.GONE);
            btnDelete.setVisibility(View.GONE);
        }
        btnEdit.setOnClickListener(v -> updateStudent(student, studentDAO));
        btnDelete.setOnClickListener(v -> deleteStudent(student, studentDAO));

    }

    private void viewCertificate(String code) {
        Intent intent = new Intent(this, CertificateListActivity.class);
        intent.putExtra(Const.FIELD.STUDENT_CODE, code);
        startActivity(intent);
    }

    private void backToListStudent() {
        Intent intent = new Intent(this, StudentListActivity.class);
        startActivity(intent);
        finish();
    }

    private void updateStudent(Student student, StudentDAO studentDAO) {
        String name = editTextName.getText().toString();
        if (name.isEmpty()) {
            Toast.makeText(this, "Hãy điền đầy đủ thông tin student", Toast.LENGTH_SHORT).show();
            return;
        }
        student.setName(name);
        studentDAO.updateStudent(student);
        backToListStudent();
    }

    private void deleteStudent(Student student, StudentDAO studentDAO) {
        student.setDelete(true);
        studentDAO.updateStudent(student);
        backToListStudent();
    }
}