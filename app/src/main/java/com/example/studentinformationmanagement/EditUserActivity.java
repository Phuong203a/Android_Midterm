package com.example.studentinformationmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentinformationmanagement.dao.ImageDao;
import com.example.studentinformationmanagement.dao.LoginHistoryDAO;
import com.example.studentinformationmanagement.dao.UserDAO;
import com.example.studentinformationmanagement.model.User;
import com.example.studentinformationmanagement.util.Const;
import com.example.studentinformationmanagement.util.DataUtil;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EditUserActivity extends AppCompatActivity {
    EditText editTextName, editTextDob, editTextPhone, editTextEmail;
    Button btnEdit, btnDelete;
    ImageView imageViewAvatar;
    ImageView imgBack;
    Spinner spinner;
    DatePickerDialog.OnDateSetListener datePickerListener;
    RadioGroup radioGroup;
    RadioButton radioButtonNormal, radioButtonLocked;
    Uri imageUri;
    TextView txtLoginHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        editTextName = findViewById(R.id.editTxtUserNameEditUser);
        editTextDob = findViewById(R.id.editTxtDobEditUser);
        editTextEmail = findViewById(R.id.editTxtEmailEditUser);
        editTextPhone = findViewById(R.id.editTxtPhoneNumberEditUser);
        btnEdit = findViewById(R.id.btnEditUser);
        btnDelete = findViewById(R.id.btnDeleteUser);
        imageViewAvatar = findViewById(R.id.imgAvatarEditUser);
        imgBack = findViewById(R.id.imgBackEditUser);
        spinner = findViewById(R.id.spinnerEditUser);
        radioGroup = findViewById(R.id.radioGroupEditUser);
        radioButtonNormal = findViewById(R.id.radioButtonNormalEditUser);
        radioButtonLocked = findViewById(R.id.radioButtonLockedEditUser);
        txtLoginHistory = findViewById(R.id.txtLoginHistory);

        String[] arraySpinner = new String[]{
                Const.ROLE.ADMIN, Const.ROLE.EMPLOYEE, Const.ROLE.MANAGER
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        Bundle extras = getIntent().getExtras();
        String email = extras != null ? extras.getString(Const.FIELD.EMAIL): "";


        UserDAO userDAO = new UserDAO();
        User editUser = userDAO.getCurrentUser(email);

        editTextName.setText(editUser.getUserName());
        editTextEmail.setText(editUser.getEmail());
        editTextDob.setText(editUser.getDob());
        editTextPhone.setText(editUser.getPhoneNumber());
        spinner.setSelection(findIndexSpinner(arraySpinner, editUser.getRole()));
        DataUtil.setAvatar(editUser.getAvatar(), imageViewAvatar, R.drawable.default_avatar);

        editTextDob.setOnClickListener(v -> DataUtil.setDate(this, datePickerListener));
        datePickerListener = (view, year, month, dayOfMonth) -> {
            month += 1;
            String date = dayOfMonth + "/" + month + "/" + year;
            editTextDob.setText(date);
        };
        String status = editUser.getStatus();

        if (status.equals(Const.STATUS.NORMAL)) {
            radioButtonNormal.setChecked(true);
            radioButtonLocked.setChecked(false);

        } else if (status.equals(Const.STATUS.LOCKED)) {
            radioButtonLocked.setChecked(true);
            radioButtonNormal.setChecked(false);

        }

        imgBack.setOnClickListener(v -> backToListUser());
        btnEdit.setOnClickListener(v -> editUser(editUser,userDAO));
        imageViewAvatar.setOnClickListener(v -> selectImage());
        btnDelete.setOnClickListener(v -> deleteUser(editUser, userDAO));
        txtLoginHistory.setOnClickListener(v ->viewLoginHistory(email));

    }

    private void viewLoginHistory(String email) {
        Intent intent = new Intent(this, LoginHistoryActivity.class);
        intent.putExtra(Const.FIELD.EMAIL,email);
        startActivity(intent);
    }

    private void editUser(User user,UserDAO userDAO) {

        user.setEmail(editTextEmail.getText().toString());
        user.setUserName(editTextName.getText().toString());
        user.setDob(editTextDob.getText().toString());
        user.setPhoneNumber(editTextPhone.getText().toString());
        user.setRole(spinner.getSelectedItem().toString());
        if (imageUri != null) {
            ImageDao imageDao = new ImageDao();
            user.setAvatar(imageDao.uploadImage(imageUri));
        }
        String status = Const.STATUS.NORMAL;
        int radioId = radioGroup.getCheckedRadioButtonId();
        if (radioId == R.id.radioButtonNormalEditUser) {
            status = Const.STATUS.NORMAL;
        } else if (radioId == R.id.radioButtonLockedEditUser) {
            status = Const.STATUS.LOCKED;
        }
        user.setStatus(status);

        userDAO.updateUser(user);
        Toast.makeText(this, "Update account thành công", Toast.LENGTH_SHORT).show();
        backToListUser();
    }

    private int findIndexSpinner(String[] arraySpinner, String role) {
        for (int i = 0; i < arraySpinner.length; i++) {
            if (arraySpinner[i].equals(role))
                return i;
        }
        return 0;
    }

    private void backToListUser() {
        Intent intent = new Intent(this, UserAccountActivity.class);
        startActivity(intent);
        finish();
    }

    private void deleteUser(User user, UserDAO userDAO) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (user.getEmail().equals(currentUser.getEmail())) {
            Toast.makeText(this, "Không thể tự xoá account chính chủ", Toast.LENGTH_SHORT).show();
            return;
        }
        user.setDelete(true);
        userDAO.updateUser(user);
        Toast.makeText(this, "Xoá account thành công", Toast.LENGTH_SHORT).show();
        backToListUser();
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), Const.REQUEST_CODE.IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Const.REQUEST_CODE.IMAGE) {
            imageUri = data.getData();
            imageViewAvatar.setImageURI(imageUri);
        }
    }
}