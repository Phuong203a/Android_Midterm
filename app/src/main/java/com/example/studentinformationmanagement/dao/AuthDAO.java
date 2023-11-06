package com.example.studentinformationmanagement.dao;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.studentinformationmanagement.util.Const;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;


public class AuthDAO {

    public void mAuthSignUp(String email, String password, Context context) {
        try {


            FirebaseOptions firebaseOptions = new FirebaseOptions.Builder()
                    .setApiKey(Const.FIREBASE.API_KEY)
                    .setApplicationId(Const.FIREBASE.PROJECT_ID).build();
            FirebaseApp myApp = FirebaseApp.initializeApp(context, firebaseOptions, "VirtualApp");

            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance(myApp);

            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(task ->
                            Toast.makeText(context, "Auth Ok", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e -> {
                        Log.d("Error mAuthSignUp LoginActivity", e.getMessage());
                        Toast.makeText(context, "Auth failed", Toast.LENGTH_SHORT).show();;
                    });
            firebaseAuth.signOut();
        } catch (Exception ex) {
            Log.d("Error mAuthSignUp LoginActivity", ex.getMessage());
        }
    }
}
