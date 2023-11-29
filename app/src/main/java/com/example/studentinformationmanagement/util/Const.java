package com.example.studentinformationmanagement.util;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Const {
    public static class COLLECTION{
        public static String USER = "user";
        public static String LOGIN_HISTORY = "login_history";
        public static String STUDENT = "student";
        public static String CERTIFICATE = "certificate";
    }
    public static class DOCUMENT{
        public static String USER = "user";
    }
    public static class FIELD{
        public static String USER = "user";
        public static String EMAIL = "email";
        public static String ROLE = "role";
        public static String USER_NAME = "userName";
        public static String PHONE_NUMBER = "phoneNumber";
        public static String DOB = "dob";
        public static String AVATAR = "avatar";
        public static String STATUS = "status";
        public static String IS_DELETE = "delete";
        public static String LOGIN_TIME = "loginTime";
        public static String DEVICE_NAME = "deviceName";
        public static String NAME = "name";
        public static String CODE = "code";
        public static String STUDENT_CODE = "studentCode";

    }
    public static class REQUEST_CODE{
        public static int IMAGE = 100;

    }


    public static class ROLE{
        public static String ADMIN = "ADMIN";
        public static String MANAGER = "MANAGER";
        public static String EMPLOYEE = "EMPLOYEE";

    }
    public static class STATUS{
        public static String NORMAL = "NORMAL";
        public static String LOCKED = "LOCKED";

    }
    public static FirebaseFirestore DATABASE_REFERENCE ;
    public static FirebaseAuth FIREBASE_AUTH;

    public static StorageReference STORAGE_REFERENCE ;

    static {
        DATABASE_REFERENCE = FirebaseFirestore.getInstance();
        FIREBASE_AUTH = FirebaseAuth.getInstance();
        STORAGE_REFERENCE =  FirebaseStorage.getInstance().getReference();
    }
    public static class FIREBASE{
        public static String API_KEY = "AIzaSyBBMa4RfG9PP-GiGiABMqHiAIw0rVUFPkE";
        public static String PROJECT_ID = "student-information-mana-adba6";

    }

}
