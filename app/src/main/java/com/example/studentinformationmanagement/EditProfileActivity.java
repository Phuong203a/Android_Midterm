package com.example.studentinformationmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.studentinformationmanagement.dao.UserDAO;
import com.example.studentinformationmanagement.model.User;
import com.example.studentinformationmanagement.util.Const;
import com.example.studentinformationmanagement.util.DataUtil;

import java.util.Calendar;

public class EditProfileActivity extends AppCompatActivity {

    EditText editTextName;
    EditText editTextDob;
    EditText editTextPhone;
    RadioGroup radioGroup;
    RadioButton radioButtonNormal;
    RadioButton radioButtonLocked;
    Button btnSave;
    ImageView imageViewAvatar;
    ImageView imgBackEdit;

    DatePickerDialog.OnDateSetListener datePickerListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        editTextName = findViewById(R.id.editTxtUserNameProfile);
        editTextDob = findViewById(R.id.editTxtDobProfile);
        editTextPhone = findViewById(R.id.editTxtPhoneNumberProfile);
        radioGroup = findViewById(R.id.radioGroupEdit);
        radioButtonNormal = findViewById(R.id.radioButtonNormal);
        radioButtonLocked = findViewById(R.id.radioButtonLocked);
        btnSave = findViewById(R.id.btnSaveProfile);
        imageViewAvatar = findViewById(R.id.imgAvatarEdit);
        imgBackEdit = findViewById(R.id.imgBackEdit);

        UserDAO userDAO = new UserDAO();
        User currentUser = userDAO.getCurrentUser(null);

        editTextName.setText(currentUser.getUserName());
        editTextDob.setText(currentUser.getDob());
        editTextPhone.setText(currentUser.getPhoneNumber());
        DataUtil.setAvatar(currentUser.getAvatar(),imageViewAvatar,R.drawable.default_avatar);


        String status = currentUser.getStatus();

        if (status.equals(Const.STATUS.NORMAL)) {
            radioButtonNormal.setChecked(true);
            radioButtonLocked.setChecked(false);

        } else if (status.equals(Const.STATUS.LOCKED)) {
            radioButtonLocked.setChecked(true);
            radioButtonNormal.setChecked(false);

        }

        btnSave.setOnClickListener(v -> saveProfile(currentUser));

        editTextDob.setOnClickListener(v -> setDate());
        datePickerListener = (view, year, month, dayOfMonth) -> {
            month += 1;
            String date = dayOfMonth + "/" + month + "/" + year;
            editTextDob.setText(date);
        };

        imgBackEdit.setOnClickListener(v -> {
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void setDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DATE);

        DatePickerDialog dialog = new DatePickerDialog(
                EditProfileActivity.this,
                R.style.Theme_StudentInformationManagement,
                datePickerListener,
                year, month, day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private void saveProfile(User currentUser) {
        currentUser.setUserName(editTextName.getText().toString());
        currentUser.setDob(editTextDob.getText().toString());
        currentUser.setPhoneNumber(editTextPhone.getText().toString());
        String status = Const.STATUS.NORMAL;

        int radioId = radioGroup.getCheckedRadioButtonId();
        if (radioId == R.id.radioButtonNormal) {
            status = Const.STATUS.NORMAL;
        } else if (radioId == R.id.radioButtonLocked) {
            status = Const.STATUS.LOCKED;
        }
        currentUser.setStatus(status);
        UserDAO userDAO = new UserDAO();
        userDAO.updateUser(currentUser);

        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
        finish();
    }
}
