package com.example.studentinformationmanagement.dao;

import android.util.Log;

import com.example.studentinformationmanagement.model.LoginHistory;
import com.example.studentinformationmanagement.util.Const;
import com.google.firebase.firestore.CollectionReference;

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
}
