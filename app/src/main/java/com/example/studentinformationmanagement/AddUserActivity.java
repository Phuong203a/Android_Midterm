package com.example.studentinformationmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.studentinformationmanagement.dao.AuthDAO;
import com.example.studentinformationmanagement.dao.UserDAO;
import com.example.studentinformationmanagement.model.User;
import com.example.studentinformationmanagement.util.Const;
import com.example.studentinformationmanagement.util.DataUtil;

public class AddUserActivity extends AppCompatActivity {
    EditText editTextName, editTextDob, editTextPhone, editTextEmail, editTextPass, editTextRepass;
    Button btnSave;
    ImageView imageViewAvatar;
    ImageView imgBack;
    Spinner spinner;
    DatePickerDialog.OnDateSetListener datePickerListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        editTextName = findViewById(R.id.editTxtUserNameAddUser);
        editTextDob = findViewById(R.id.editTxtDobAddUser);
        editTextEmail = findViewById(R.id.editTxtEmailAddUser);
        editTextPass = findViewById(R.id.editTextPassAddUser);
        editTextRepass = findViewById(R.id.editTxtRepassAddUser);
        editTextPhone = findViewById(R.id.editTxtPhoneNumberAddUser);
        btnSave = findViewById(R.id.btnAddUser);
        imageViewAvatar = findViewById(R.id.imgAvatarAddUser);
        imgBack = findViewById(R.id.imgBackAddUser);
        spinner = findViewById(R.id.spinnerAddUser);
        String[] arraySpinner = new String[]{
                Const.ROLE.ADMIN, Const.ROLE.EMPLOYEE, Const.ROLE.MANAGER
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        UserDAO userDAO = new UserDAO();

        btnSave.setOnClickListener(v -> {
            createNewUser(userDAO);
        });

        editTextDob.setOnClickListener(v -> DataUtil.setDate(this,datePickerListener));
        datePickerListener = (view, year, month, dayOfMonth) -> {
            month += 1;
            String date = dayOfMonth + "/" + month + "/" + year;
            editTextDob.setText(date);
        };
        imgBack.setOnClickListener(v -> backToListUser());
    }

    private void createNewUser(UserDAO userDAO) {
        try{
            String userName = editTextName.getText().toString();
            String dob = editTextDob.getText().toString();
            String phoneNumber = editTextPhone.getText().toString();
            String email = editTextEmail.getText().toString();
            String pass = editTextPass.getText().toString();
            String rePass = editTextRepass.getText().toString();
            if(!validate(userName, dob, phoneNumber, email, pass, rePass, userDAO)){
                return;
            }
            AuthDAO authDAO = new AuthDAO();
            authDAO.mAuthSignUp(email,pass,this);

            User newUser = new User();
            newUser.setUserName(userName);
            newUser.setDob(dob);
            newUser.setPhoneNumber(phoneNumber);
            newUser.setEmail(email);
            newUser.setAvatar("");
            newUser.setRole(spinner.getSelectedItem().toString());
            newUser.setStatus(Const.STATUS.NORMAL);
            newUser.setDelete(false);

            userDAO.createUser(newUser);
            Toast.makeText(this, "Tạo tài khoản thành công", Toast.LENGTH_SHORT).show();
            backToListUser();
        }catch (Exception ex){

        }

    }


    private void backToListUser(){
        Intent intent = new Intent(this,UserAccountActivity.class);
        startActivity(intent);
        finish();
    }

    private boolean validate(String userName,
                             String dob,
                             String phoneNumber,
                             String email,
                             String pass,
                             String rePass,
                             UserDAO userDAO) {

        if (userName.isEmpty() ||
                dob.isEmpty() ||
                email.isEmpty() ||
                pass.isEmpty() ||
                rePass.isEmpty() ||
                phoneNumber.isEmpty()
        ) {
            Toast.makeText(this, "Không được bỏ trống các trường", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Email không đúng định dạng", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!pass.equals(rePass)) {
            Toast.makeText(this, "Mật khẩu nhập không đúng", Toast.LENGTH_SHORT).show();
            return false;
        } else if (userDAO.getCurrentUser(email) != null) {
            Toast.makeText(this, "Email đã tồn tại", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}