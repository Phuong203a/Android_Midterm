package com.example.studentinformationmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.studentinformationmanagement.adapter.LoginHistoryAdapter;
import com.example.studentinformationmanagement.adapter.MyUserAccountAdapter;
import com.example.studentinformationmanagement.dao.LoginHistoryDAO;
import com.example.studentinformationmanagement.dao.UserDAO;
import com.example.studentinformationmanagement.model.LoginHistory;
import com.example.studentinformationmanagement.model.User;
import com.example.studentinformationmanagement.util.Const;

import java.util.List;

public class LoginHistoryActivity extends AppCompatActivity {
    private ImageView imageBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_history);
        imageBack = findViewById(R.id.imgBackLoginHistory);
        String email = getIntent().getExtras() != null ? getIntent().getExtras().getString(Const.FIELD.EMAIL): "";

        LoginHistoryDAO loginHistoryDAO = new LoginHistoryDAO();
        List<LoginHistory> loginHistoryList = loginHistoryDAO.getLoginHistory(email);
        if(!loginHistoryList.isEmpty()){
            RecyclerView recyclerView = findViewById(R.id.recycleViewLoginHistory);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(new LoginHistoryAdapter(getApplicationContext(),loginHistoryList));
        }
        imageBack.setOnClickListener(v->backToUserDetail(email));
    }
    private void backToUserDetail(String email) {
        Intent intent = new Intent(this, EditUserActivity.class);
        intent.putExtra(Const.FIELD.EMAIL,email);
        startActivity(intent);
        finish();
    }
}