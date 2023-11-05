package com.example.studentinformationmanagement;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.studentinformationmanagement.dao.UserDAO;
import com.example.studentinformationmanagement.model.User;
import com.example.studentinformationmanagement.util.Const;
import com.example.studentinformationmanagement.util.DataUtil;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {
    TextView txtUserName;
    TextView txtEmail;
    TextView txtDob;
    TextView txtAge;
    TextView txtPhoneNumber;
    TextView txtRole;
    ImageView circleImageView;
    ImageView imgBackProfile;

    Button btnEditProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_profile);
            UserDAO userDAO = new UserDAO();
            User currentUser = userDAO.getCurrentUser(null);

            txtUserName = findViewById(R.id.txtUserNameProfile);
            txtEmail = findViewById(R.id.txtEmailProfile);
            txtDob = findViewById(R.id.txtDobProfile);
            txtAge = findViewById(R.id.txtAgeProfile);
            txtPhoneNumber = findViewById(R.id.txtPhoneNumberProfile);
            txtRole = findViewById(R.id.txtRoleProfile);
            circleImageView = (ImageView) findViewById(R.id.imageAvatarProfile);
            imgBackProfile = findViewById(R.id.imgBackProfile);
            btnEditProfile = findViewById(R.id.btnEditProfile);

            if(!currentUser.getRole().equals(Const.ROLE.ADMIN)){
                btnEditProfile.setVisibility(View.GONE);
            }

            imgBackProfile.setOnClickListener(v -> {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            });

            btnEditProfile.setOnClickListener(v -> {
                Intent intent = new Intent(this, EditProfileActivity.class);
                startActivity(intent);
                finish();
            });


            txtUserName.setText(currentUser.getUserName());
            txtEmail.setText(currentUser.getEmail());
            txtDob.setText(currentUser.getDob());
            txtAge.setText(String.valueOf(DataUtil.calculateAge(currentUser.getDob())));
            txtPhoneNumber.setText(currentUser.getPhoneNumber());
            txtRole.setText(currentUser.getRole());
            DataUtil.setAvatar(currentUser.getAvatar(), circleImageView, R.drawable.default_avatar);

        } catch (Exception ex) {
            Log.d("ProfileActivity", ex.getMessage());

        }
    }
}