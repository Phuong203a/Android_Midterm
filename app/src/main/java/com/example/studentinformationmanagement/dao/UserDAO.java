package com.example.studentinformationmanagement.dao;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.studentinformationmanagement.model.User;
import com.example.studentinformationmanagement.util.Const;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicBoolean;

public class UserDAO {
    public User getCurrentUser(String email) {
        try {
            if (email == null || email == "") {
                email = Const.FIREBASE_AUTH.getCurrentUser().getEmail();
            }

            DocumentReference docRef = Const.DATABASE_REFERENCE.collection(Const.COLLECTION.USER).document(email);
            Task<DocumentSnapshot> firebaseTask = docRef.get().addOnCompleteListener(t -> {
            });
            FutureTask<Object> futureTask = new FutureTask<>(() -> {
                try {
                    return Tasks.await(firebaseTask);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            });

            new Thread(futureTask).start();

            DocumentSnapshot result = (DocumentSnapshot) futureTask.get();
            if (result != null) {
                User user = new User();
                user.setUserName(result.get(Const.FIELD.USER_NAME).toString());
                user.setEmail(result.get(Const.FIELD.EMAIL).toString());
                user.setRole(result.get(Const.FIELD.ROLE).toString());
                user.setStatus(result.get(Const.FIELD.STATUS).toString());
                user.setAvatar(result.get(Const.FIELD.AVATAR).toString());
                user.setPhoneNumber(result.get(Const.FIELD.PHONE_NUMBER).toString());
                user.setDob(result.get(Const.FIELD.DOB).toString());
                user.setDelete(Boolean.parseBoolean(result.get(Const.FIELD.IS_DELETE).toString()));

                return user;
            } else {
                return null;
            }

        } catch (Exception ex) {
            Log.d("Exception getCurrentUser MainActivity", ex.getMessage());
        }
        return null;
    }

    public List<User> getAllUserActive() {
        List<User> allUser = new ArrayList<>();
        try {
            Task<QuerySnapshot> firebaseTask = Const.DATABASE_REFERENCE.collection(Const.COLLECTION.USER)
                    .whereEqualTo(Const.FIELD.IS_DELETE, true)
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
//            if (result != null) {
                for (QueryDocumentSnapshot r : result) {
                    User user = new User();
                    user.setUserName(r.get(Const.FIELD.USER_NAME).toString());
                    user.setEmail(r.get(Const.FIELD.EMAIL).toString());
                    user.setRole(r.get(Const.FIELD.ROLE).toString());
                    user.setStatus(r.get(Const.FIELD.STATUS).toString());
                    user.setAvatar(r.get(Const.FIELD.AVATAR).toString());
                    user.setPhoneNumber(r.get(Const.FIELD.PHONE_NUMBER).toString());
                    user.setDob(r.get(Const.FIELD.DOB).toString());
                    user.setDelete(Boolean.parseBoolean(r.get(Const.FIELD.IS_DELETE).toString()));
                    allUser.add(user);
                }


        } catch (Exception ex) {
            Log.d("Exception getCurrentUser MainActivity", ex.getMessage());
        }
        return allUser;
    }

    public void updateUser(User user) {
        DocumentReference docRef = Const.DATABASE_REFERENCE.collection(Const.COLLECTION.USER).document(user.getEmail());
        docRef.update(Const.FIELD.AVATAR, user.getAvatar(),
                        Const.FIELD.DOB, user.getDob(),
                        Const.FIELD.PHONE_NUMBER, user.getPhoneNumber(),
                        Const.FIELD.ROLE, user.getRole(),
                        Const.FIELD.STATUS, user.getStatus(),
                        Const.FIELD.IS_DELETE, user.isDelete(),
                        Const.FIELD.USER_NAME, user.getUserName()).addOnSuccessListener(aVoid -> {
                    Log.d("updateUser", "DocumentSnapshot successfully updated!");

                })
                .addOnFailureListener(e -> {
                    Log.d("updateUser", "Error updating document", e);
                });
    }

}
