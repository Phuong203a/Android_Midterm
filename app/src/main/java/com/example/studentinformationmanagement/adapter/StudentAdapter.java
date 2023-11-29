package com.example.studentinformationmanagement.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentinformationmanagement.EditStudentActivity;
import com.example.studentinformationmanagement.EditUserActivity;
import com.example.studentinformationmanagement.R;
import com.example.studentinformationmanagement.holder.MyUserAccountViewHolder;
import com.example.studentinformationmanagement.holder.StudentViewHolder;
import com.example.studentinformationmanagement.model.Student;
import com.example.studentinformationmanagement.model.User;
import com.example.studentinformationmanagement.util.Const;

import java.util.List;

public class StudentAdapter extends  RecyclerView.Adapter<StudentViewHolder>{
    Context context;
    List<Student> studentList;
    String currentUserRole;

    public StudentAdapter(Context context, List<Student> studentList, String currentUserRole) {
        this.context = context;
        this.studentList = studentList;
        this.currentUserRole = currentUserRole;
    }
    public StudentAdapter(){

    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    public String getCurrentUserRole() {
        return currentUserRole;
    }

    public void setCurrentUserRole(String currentUserRole) {
        this.currentUserRole = currentUserRole;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StudentViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view_student, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        holder.txtStudentName.setText(studentList.get(position).getName());
        holder.txtStudentCode.setText(studentList.get(position).getCode());
        holder.cardView.setOnClickListener(v -> {
            if (currentUserRole.equals(Const.ROLE.EMPLOYEE)) {
                return;
            }
            Intent intent = new Intent(context, EditStudentActivity.class);
            intent.putExtra(Const.FIELD.CODE, studentList.get(position).getCode());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }
}
