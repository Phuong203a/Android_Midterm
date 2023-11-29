package com.example.studentinformationmanagement.dao;

import android.util.Log;

import com.example.studentinformationmanagement.model.Student;
import com.example.studentinformationmanagement.model.User;
import com.example.studentinformationmanagement.util.Const;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.FutureTask;

public class StudentDAO {
    public void createStudent(Student student) {
        DocumentReference docRef = Const.DATABASE_REFERENCE.collection(Const.COLLECTION.STUDENT).document(student.getCode());
        docRef.set(student).addOnSuccessListener(aVoid -> {
                    Log.d("createStudent", "DocumentSnapshot successfully create!");
                })
                .addOnFailureListener(e -> {
                    Log.d("createStudent", "Error updating document", e);
                });
    }

    public Student getStudentByCode(String code) {
        try {

            DocumentReference docRef = Const.DATABASE_REFERENCE.collection(Const.COLLECTION.STUDENT).document(code);
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
                Student student = new Student();
                student.setName(result.get(Const.FIELD.NAME).toString());
                student.setCode(result.get(Const.FIELD.CODE).toString());
                return student;
            } else {
                return null;
            }

        } catch (Exception ex) {
            Log.d("Exception getStudentByCode MainActivity", ex.getMessage());
        }
        return null;
    }

    public List<Student> getAllStudent(String orderByField) {
        List<Student> allStudent = new ArrayList<>();
        try {
            Task<QuerySnapshot> firebaseTask = (orderByField == null || ("").equals(orderByField)) ?
                    Const.DATABASE_REFERENCE
                            .collection(Const.COLLECTION.STUDENT)
                            .whereEqualTo(Const.FIELD.IS_DELETE, false)
                            .get()
                            .addOnCompleteListener(task -> {
                            })
                    :
                    Const.DATABASE_REFERENCE
                            .collection(Const.COLLECTION.STUDENT)
                            .whereEqualTo(Const.FIELD.IS_DELETE, false)
                            .orderBy(orderByField, Query.Direction.ASCENDING)
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
//            if (result != null) {
            for (QueryDocumentSnapshot r : result) {
                Student student = new Student();
                student.setName(r.get(Const.FIELD.NAME).toString());
                student.setCode(r.get(Const.FIELD.CODE).toString());
                student.setDelete(Boolean.parseBoolean(r.get(Const.FIELD.IS_DELETE).toString()));
                allStudent.add(student);
            }


        } catch (Exception ex) {
            Log.d("Exception getAllStudent MainActivity", ex.getMessage());
        }
        return allStudent;
    }

    public void updateStudent(Student student) {
        DocumentReference docRef = Const.DATABASE_REFERENCE.collection(Const.COLLECTION.STUDENT).document(student.getCode());
        docRef.update(Const.FIELD.NAME, student.getName(),
                        Const.FIELD.CODE, student.getCode(),
                        Const.FIELD.IS_DELETE, student.isDelete()
                ).addOnSuccessListener(aVoid -> {
                    Log.d("updateStudent", "DocumentSnapshot successfully updated!");

                })
                .addOnFailureListener(e -> {
                    Log.d("updateStudent", "Error updating document", e);
                });
    }
}
