package com.example.studentinformationmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.studentinformationmanagement.adapter.MyUserAccountAdapter;
import com.example.studentinformationmanagement.dao.UserDAO;
import com.example.studentinformationmanagement.model.User;

import java.util.List;

public class UserAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);
        UserDAO userDAO = new UserDAO();
        List<User> allUserActive = userDAO.getAllUserActive();

        if(!allUserActive.isEmpty()){
            RecyclerView recyclerView = findViewById(R.id.recycleViewListUser);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(new MyUserAccountAdapter(getApplicationContext(),allUserActive));
        }

    }
}