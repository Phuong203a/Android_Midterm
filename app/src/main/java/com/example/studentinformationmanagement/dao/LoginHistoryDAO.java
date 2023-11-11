package com.example.studentinformationmanagement.dao;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.studentinformationmanagement.model.LoginHistory;
import com.example.studentinformationmanagement.model.User;
import com.example.studentinformationmanagement.util.Const;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.FutureTask;

public class LoginHistoryDAO {
    public void createLoginHistory(LoginHistory loginHistory) {
        CollectionReference collection = Const.DATABASE_REFERENCE.collection(Const.COLLECTION.LOGIN_HISTORY);
        collection.add(loginHistory).addOnSuccessListener(aVoid -> {
                    Log.d("createLoginHistory", "DocumentSnapshot successfully createLoginHistory!");
                })
                .addOnFailureListener(e -> {
                    Log.d("createLoginHistory", "Error createLoginHistory", e);
                });
    }

    public List<LoginHistory> getLoginHistory(String email) {
        List<LoginHistory> loginHistoryList = new ArrayList<>();
        try {
            Task<QuerySnapshot> firebaseTask = Const.DATABASE_REFERENCE
                    .collection(Const.COLLECTION.LOGIN_HISTORY)
                    .whereEqualTo(Const.FIELD.EMAIL, email)
                    .get()
                    .addOnCompleteListener(task -> {});
            FutureTask<Object> futureTask = new FutureTask<>(() -> {
                try {
                    return Tasks.await(firebaseTask);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            });
            new Thread(futureTask).start();
            QuerySnapshot result = (QuerySnapshot) futureTask.get();
            if (result != null) {
                LoginHistory loginHistory;
                for (QueryDocumentSnapshot document : result) {
                    loginHistory = new LoginHistory();
                    loginHistory.setLoginTime(String.valueOf(document.get(Const.FIELD.LOGIN_TIME)));
                    loginHistory.setEmail(String.valueOf(document.get(Const.FIELD.EMAIL)));
                    loginHistory.setDeviceName(String.valueOf(document.get(Const.FIELD.DEVICE_NAME)));
                    loginHistoryList.add(loginHistory);
                }
            }
        } catch (Exception ex) {
            Log.d("Exception getLoginHistory", ex.getMessage());
        }

        return loginHistoryList;

    }
}
