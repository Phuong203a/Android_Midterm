package com.example.studentinformationmanagement.dao;

import android.util.Log;

import com.example.studentinformationmanagement.model.Certificate;
import com.example.studentinformationmanagement.model.LoginHistory;
import com.example.studentinformationmanagement.model.Student;
import com.example.studentinformationmanagement.model.User;
import com.example.studentinformationmanagement.util.Const;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.FutureTask;

public class CertificateDAO {
    public void createCertificate(Certificate certificate) {
        CollectionReference collection = Const.DATABASE_REFERENCE.collection(Const.COLLECTION.CERTIFICATE);
        collection.add(certificate).addOnSuccessListener(aVoid -> {
                    Log.d("createCertificate", "DocumentSnapshot successfully create!");
                })
                .addOnFailureListener(e -> {
                    Log.d("createCertificate", "Error updating document", e);
                });
    }

    public void updateCertificate(Certificate certificate) {
        DocumentReference docRef = Const.DATABASE_REFERENCE.collection(Const.COLLECTION.CERTIFICATE)
                .document(certificate.getId());
        docRef.update(Const.FIELD.NAME, certificate.getName(),
                        Const.FIELD.STUDENT_CODE, certificate.getStudentCode(),
                        Const.FIELD.IS_DELETE, certificate.isDelete()
                ).addOnSuccessListener(aVoid -> {
                    Log.d("updateStudent", "DocumentSnapshot successfully updated!");

                })
                .addOnFailureListener(e -> {
                    Log.d("updateStudent", "Error updating document", e);
                });
    }

    public List<Certificate> getListCertificate(String studentCode) {
        List<Certificate> certificates = new ArrayList<>();
        try {
            Task<QuerySnapshot> firebaseTask = Const.DATABASE_REFERENCE
                    .collection(Const.COLLECTION.CERTIFICATE)
                    .whereEqualTo(Const.FIELD.STUDENT_CODE, studentCode)
                    .whereEqualTo(Const.FIELD.IS_DELETE, false)
                    .get()
                    .addOnCompleteListener(task -> {
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
            QuerySnapshot result = (QuerySnapshot) futureTask.get();
            if (result != null) {
                Certificate certificate;
                for (QueryDocumentSnapshot document : result) {
                    certificate = new Certificate();
                    certificate.setId(document.getId());
                    certificate.setName(String.valueOf(document.get(Const.FIELD.NAME)));
                    certificate.setStudentCode(String.valueOf(document.get(Const.FIELD.STUDENT_CODE)));
                    certificate.setDelete(Boolean.parseBoolean(document.get(Const.FIELD.IS_DELETE).toString()));
                    certificates.add(certificate);
                }
            }
        } catch (Exception ex) {
            Log.d("Exception getLoginHistory", ex.getMessage());
        }
        return certificates;
    }

    public Certificate getCertificateById(String id) {
        try {
            DocumentReference docRef = Const.DATABASE_REFERENCE.collection(Const.COLLECTION.CERTIFICATE)
                    .document(id);
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
                Certificate certificate = new Certificate();
                certificate.setId(result.getId());
                certificate.setName(String.valueOf(result.get(Const.FIELD.NAME)));
                certificate.setStudentCode(String.valueOf(result.get(Const.FIELD.STUDENT_CODE)));
                certificate.setDelete(Boolean.parseBoolean(result.get(Const.FIELD.IS_DELETE).toString()));
                return certificate;
            } else {
                return null;
            }

        } catch (Exception ex) {
            Log.d("Exception getStudentByCode MainActivity", ex.getMessage());
        }
        return null;
    }
}
