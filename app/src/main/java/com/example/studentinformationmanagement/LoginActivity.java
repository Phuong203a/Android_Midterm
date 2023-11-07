package com.example.studentinformationmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.studentinformationmanagement.dao.LoginHistoryDAO;
import com.example.studentinformationmanagement.dao.UserDAO;
import com.example.studentinformationmanagement.model.LoginHistory;
import com.example.studentinformationmanagement.model.User;
import com.example.studentinformationmanagement.util.Const;
import com.example.studentinformationmanagement.util.DataUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicReference;

public class LoginActivity extends AppCompatActivity {
    private Button btnLogin;
    private EditText txtEmail;
    private EditText txtPassword;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = findViewById(R.id.btnLogin);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);

        btnLogin.setOnClickListener(loginAction());
    }

    private View.OnClickListener loginAction() {
        return v -> {
            try {
                String email = txtEmail.getText().toString().trim();
                String password = txtPassword.getText().toString().trim();
                mAuthSignIn(email, password);
            } catch (Exception ex) {
                Log.d("Error loginAction LoginActivity", ex.getMessage());
            }
        };

    }

    private void mAuthSignIn(String email, String password) {
        LoadingAlert loadingAlert = new LoadingAlert(LoginActivity.this);

        if (email.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Email không được bỏ trống", Toast.LENGTH_SHORT).show();
            return;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(LoginActivity.this, "Email không đúng định dạng", Toast.LENGTH_SHORT).show();
            return;
        } else if (password.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Password không được bỏ trống", Toast.LENGTH_SHORT).show();
            return;
        }

        loadingAlert.setLoadingDialog();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                            boolean isSuccess = checkIfUserAvailable(email);
                            if (isSuccess) {
                                createLoginHistory(email);
                                Intent intent = new Intent(this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                )
                .addOnFailureListener(e -> Toast.makeText(LoginActivity.this, "User name or password is incorrect", Toast.LENGTH_SHORT).show());

        new Handler(Looper.getMainLooper()).postDelayed(() -> loadingAlert.closeLoadingDialog(), 500);

    }

    private void createLoginHistory(String email){
        LoginHistory loginHistory = new LoginHistory();
        loginHistory.setEmail(email);
        loginHistory.setDeviceName(Build.MODEL);
        loginHistory.setLoginTime(DataUtil.parseDateToString(new Date(),DataUtil.DATE_FORMAT_ddMMyyyyHHmmss));

        LoginHistoryDAO loginHistoryDAO = new LoginHistoryDAO();
        loginHistoryDAO.createLoginHistory(loginHistory);
    }

    private boolean checkIfUserAvailable(String email) {
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getCurrentUser(email);

        if (user.getStatus().equals(Const.STATUS.LOCKED)) {
            Toast.makeText(LoginActivity.this, "Tài khoản đang bị khoá", Toast.LENGTH_SHORT).show();
            return false;
        } else if (user.isDelete()) {
            Toast.makeText(LoginActivity.this, "Tài khoản đã bị xoá", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


}