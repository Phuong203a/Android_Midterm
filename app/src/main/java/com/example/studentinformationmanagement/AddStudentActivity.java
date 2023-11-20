package com.example.studentinformationmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.studentinformationmanagement.dao.StudentDAO;
import com.example.studentinformationmanagement.model.Student;

public class AddStudentActivity extends AppCompatActivity {
    EditText editTextName, editTextCode;
    ImageView imgBack;
    Button btnSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        imgBack = findViewById(R.id.imgBackAddStudent);
        btnSave = findViewById(R.id.btnAddStudent);
        editTextName = findViewById(R.id.editTxtUserNameAddStudent);
        editTextCode = findViewById(R.id.editTxtCodeAddStudent);
        imgBack.setOnClickListener(v -> backToListStudent());
        btnSave.setOnClickListener(v ->addStudent());

    }
    private void addStudent(){
        String name = editTextName.getText().toString();
        String code = editTextCode.getText().toString();
        StudentDAO studentDAO = new StudentDAO();
        if(!validate(name, code,studentDAO)){
            return;
        }
        studentDAO.createStudent(new Student(name,code,false));
        backToListStudent();
    }

    private boolean validate(String name, String code, StudentDAO studentDAO) {
        if (name.isEmpty() || code.isEmpty()) {
            Toast.makeText(this, "Không được bỏ trống các trường", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(studentDAO.getStudentByCode(code) != null){
            Toast.makeText(this, "Code đã tồn tại", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private void backToListStudent(){
        Intent intent = new Intent(this, StudentListActivity.class);
        startActivity(intent);
        finish();
    }
}