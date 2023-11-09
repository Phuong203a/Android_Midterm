package com.example.studentinformationmanagement.dao;

import android.net.Uri;

import com.example.studentinformationmanagement.util.Const;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Date;
import java.util.concurrent.FutureTask;

public class ImageDao {
    public String uploadImage(Uri imageUri) {
        String url = "";
        if (imageUri != null) {
            try{
                Long id = new Date().getTime();
                StorageReference ref = Const.STORAGE_REFERENCE.child("avatar/" + imageUri.getLastPathSegment()+"_"+id);
                UploadTask uploadTask = ref.putFile(imageUri);
                uploadTask.addOnSuccessListener(taskSnapshot -> {
                });
                FutureTask<Object> futureTask = new FutureTask<>(() -> {
                    try {
                        return Tasks.await(uploadTask);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                });
                new Thread(futureTask).start();
                futureTask.get();

                Task<Uri> urlTask  =  FirebaseStorage.getInstance().getReference("avatar/"+imageUri.getLastPathSegment()+"_"+id).getDownloadUrl();
                urlTask.addOnSuccessListener(uri -> {
                });

                FutureTask<Object> futureTaskDownloadImage = new FutureTask<>(() -> {
                    try {
                        return Tasks.await(urlTask);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                });

                new Thread(futureTaskDownloadImage).start();

                Uri uri = (Uri)futureTaskDownloadImage.get();
                url = uri.toString();
            }catch (Exception ex){

            }
        }
        return url;
    }
}
