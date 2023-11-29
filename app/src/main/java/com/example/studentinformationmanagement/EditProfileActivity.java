package com.example.studentinformationmanagement;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.studentinformationmanagement.dao.ImageDao;
import com.example.studentinformationmanagement.dao.UserDAO;
import com.example.studentinformationmanagement.model.User;
import com.example.studentinformationmanagement.util.Const;
import com.example.studentinformationmanagement.util.DataUtil;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URI;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.FutureTask;

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

    Uri imageUri;
    Bitmap bitmap;
    DatePickerDialog.OnDateSetListener datePickerListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        editTextName = findViewById(R.id.editTxtUserNameEdit);
        editTextDob = findViewById(R.id.editTxtDobProfile);
        editTextPhone = findViewById(R.id.editTxtPhoneEdit);
        radioGroup = findViewById(R.id.radioGroupEdit);
        radioButtonNormal = findViewById(R.id.radioButtonNormal);
        radioButtonLocked = findViewById(R.id.radioButtonLocked);
        btnSave = findViewById(R.id.btnSaveProfile);
        imageViewAvatar = findViewById(R.id.imgAvatarEdit);
        imgBackEdit = findViewById(R.id.imgBackEdit);

        Bundle extras = getIntent().getExtras();
        String email = "";
        if (extras != null) {
            email = extras.getString(Const.FIELD.EMAIL);
        }
        UserDAO userDAO = new UserDAO();
        User currentUser = userDAO.getCurrentUser(email);

        editTextName.setText(currentUser.getUserName());
        editTextDob.setText(currentUser.getDob());
        editTextPhone.setText(currentUser.getPhoneNumber());
        DataUtil.setAvatar(currentUser.getAvatar(), imageViewAvatar, R.drawable.default_avatar);


        String status = currentUser.getStatus();

        if (status.equals(Const.STATUS.NORMAL)) {
            radioButtonNormal.setChecked(true);
            radioButtonLocked.setChecked(false);

        } else if (status.equals(Const.STATUS.LOCKED)) {
            radioButtonLocked.setChecked(true);
            radioButtonNormal.setChecked(false);

        }

        btnSave.setOnClickListener(v -> saveProfile(currentUser));

        editTextDob.setOnClickListener(v -> DataUtil.setDate(this, datePickerListener));
        datePickerListener = (view, year, month, dayOfMonth) -> {
            month += 1;
            String date = dayOfMonth + "/" + month + "/" + year;
            editTextDob.setText(date);
        };

        imgBackEdit.setOnClickListener(v -> finish());
        imageViewAvatar.setOnClickListener(v -> selectImage());
    }


    private void saveProfile(User currentUser) {
        currentUser.setUserName(editTextName.getText().toString());
        currentUser.setDob(editTextDob.getText().toString());
        currentUser.setPhoneNumber(editTextPhone.getText().toString());
        if(imageUri != null){
            ImageDao imageDao = new ImageDao();
            currentUser.setAvatar(imageDao.uploadImage(imageUri));
        }
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
            if(data!= null){
                imageUri = data.getData();
                imageViewAvatar.setImageURI(imageUri);
            }
        }
    }


}
