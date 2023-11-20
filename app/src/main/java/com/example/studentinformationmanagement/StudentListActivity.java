package com.example.studentinformationmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.studentinformationmanagement.adapter.MyUserAccountAdapter;
import com.example.studentinformationmanagement.adapter.StudentAdapter;
import com.example.studentinformationmanagement.dao.StudentDAO;
import com.example.studentinformationmanagement.dao.UserDAO;
import com.example.studentinformationmanagement.model.Student;
import com.example.studentinformationmanagement.util.Const;

import java.util.List;

public class StudentListActivity extends AppCompatActivity {
    private ImageView imageViewAddStudent;
    private ImageView imgBack;
    private Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        imageViewAddStudent = findViewById(R.id.imgAddStudent);
        imgBack = findViewById(R.id.imgBackStudentList);

        spinner=findViewById(R.id.spinnerOrderStudent);
        String[] arraySpinner = new String[]{
                Const.FIELD.NAME,Const.FIELD.CODE
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        UserDAO userDAO = new UserDAO();
        StudentDAO studentDAO = new StudentDAO();
        String role = userDAO.getCurrentUser(null).getRole();
        List<Student> studentList = studentDAO.getAllStudent(null);

        if (!studentList.isEmpty()) {
            RecyclerView recyclerView = findViewById(R.id.recycleViewListStudent);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(new StudentAdapter(getApplicationContext(), studentList,role));
        }
        if(role.equals(Const.ROLE.EMPLOYEE)){
            imageViewAddStudent.setVisibility(View.GONE);
        }

        imageViewAddStudent.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddStudentActivity.class);
            startActivity(intent);

        });
        imgBack.setOnClickListener(t -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });

    }
}