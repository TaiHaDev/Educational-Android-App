package com.example.ga_23s1_comp2100_6442;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.os.Bundle;

import com.example.ga_23s1_comp2100_6442.model.Lecturer;
import com.example.ga_23s1_comp2100_6442.model.Student;
import com.example.ga_23s1_comp2100_6442.model.User;
import com.example.ga_23s1_comp2100_6442.ultilities.Constant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MyDataActivity extends Application {
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    FirebaseFirestore db;
    private static User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        MyDataActivity.user = user;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            db.collection(Constant.STUDENTS_COLLECTION).document(currentUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            user = document.toObject(Student.class);
                            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                            System.out.println(user.getIsLecturer());
                        }
                    }
                }
            });
            db.collection("lecturers").document(currentUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            user = document.toObject(Lecturer.class);
                            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                            System.out.println(user.getIsLecturer());
                        }
                    }
                }
            });
        }
    }

}
