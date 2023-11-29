package com.example.studentinformationmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.studentinformationmanagement.adapter.MyUserAccountAdapter;
import com.example.studentinformationmanagement.adapter.StudentAdapter;
import com.example.studentinformationmanagement.dao.DataExportImportDAO;
import com.example.studentinformationmanagement.dao.StudentDAO;
import com.example.studentinformationmanagement.dao.UserDAO;
import com.example.studentinformationmanagement.model.Student;
import com.example.studentinformationmanagement.util.Const;

import java.util.List;

public class StudentListActivity extends AppCompatActivity {
    private ImageView imageViewAddStudent, imageExport;
    private ImageView imgBack;
    private Spinner spinner;
    private List<Student> studentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        imageViewAddStudent = findViewById(R.id.imgAddStudent);
        imgBack = findViewById(R.id.imgBackStudentList);
        imageExport = findViewById(R.id.imgExportStudent);
        spinner = findViewById(R.id.spinnerOrderStudent);
        RecyclerView recyclerView = findViewById(R.id.recycleViewListStudent);
        String[] arraySpinner = new String[]{
                Const.FIELD.NAME, Const.FIELD.CODE
        };

        ArrayAdapter<String> adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        UserDAO userDAO = new UserDAO();
        StudentDAO studentDAO = new StudentDAO();
        String role = userDAO.getCurrentUser(null).getRole();
        studentList = studentDAO.getAllStudent(null);

        StudentAdapter studentAdapter = new StudentAdapter();
        studentAdapter.setContext(getApplicationContext());
        studentAdapter.setCurrentUserRole(role);
        studentAdapter.setStudentList(studentList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(studentAdapter);

        if (role.equals(Const.ROLE.EMPLOYEE)) {
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
        imageExport.setOnClickListener(v -> exportStudent(studentList));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                studentList.clear();
                studentList = studentDAO.getAllStudent(arraySpinner[position]);
                studentAdapter.setStudentList(studentList);
                studentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void exportStudent(List<Student> studentList) {
        DataExportImportDAO dao = new DataExportImportDAO();
        String fileName = "students.csv";
        boolean isExportSuccess = dao.exportCSVStudent(studentList, fileName);
        Toast.makeText(this, "Export file " + (isExportSuccess ? "ok" : "thất bại"), Toast.LENGTH_SHORT).show();
    }
}