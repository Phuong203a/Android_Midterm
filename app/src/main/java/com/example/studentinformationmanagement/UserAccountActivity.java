package com.example.studentinformationmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.studentinformationmanagement.adapter.MyUserAccountAdapter;
import com.example.studentinformationmanagement.dao.UserDAO;
import com.example.studentinformationmanagement.model.User;
import com.example.studentinformationmanagement.util.Const;

import java.util.List;

public class UserAccountActivity extends AppCompatActivity {

    private ImageView imageViewAddUser;
    private ImageView imgBackUserAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);
        imageViewAddUser = findViewById(R.id.imgAddUser);
        imgBackUserAccount = findViewById(R.id.imgBackUserAccount);
        UserDAO userDAO = new UserDAO();
        List<User> allUserActive = userDAO.getAllUserActive();
        String role = userDAO.getCurrentUser(null).getRole();

        if (!allUserActive.isEmpty()) {
            RecyclerView recyclerView = findViewById(R.id.recycleViewListUser);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(new MyUserAccountAdapter(getApplicationContext(), allUserActive,role));
        }
        if (!role.equals(Const.ROLE.ADMIN)) {
            imageViewAddUser.setVisibility(View.GONE);
        }
        imageViewAddUser.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddUserActivity.class);
            startActivity(intent);

        });
        imgBackUserAccount.setOnClickListener(t -> {

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });


    }
}