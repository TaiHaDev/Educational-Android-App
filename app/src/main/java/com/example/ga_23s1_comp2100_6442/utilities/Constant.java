package com.example.ga_23s1_comp2100_6442.utilities;


import android.util.Log;
import static android.content.ContentValues.TAG;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ga_23s1_comp2100_6442.R;
import com.example.ga_23s1_comp2100_6442.model.Course;
import com.example.ga_23s1_comp2100_6442.model.DatabaseUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.core.Tag;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class Constant {
    public static String COURSE_COLLECTION = "courses";
    public static String MESSAGE_COLLECTION = "messages";
    public static String USER_COLLECTION = "user";
    public static String METADATA_COLLECTION = "metadata";
    public static DatabaseUser USER_PROFILE;

    public static void setUserNameAfterLogin(String uid) {
        FirebaseFirestore fb = FirebaseFirestore.getInstance();
        fb.collection(Constant.USER_COLLECTION).document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    USER_PROFILE = task.getResult().toObject(DatabaseUser.class);
                    USER_PROFILE.setUid(uid);
                }
            }
        });
    }
}
