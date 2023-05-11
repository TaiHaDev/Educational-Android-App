package com.example.ga_23s1_comp2100_6442.Services;

import static android.content.ContentValues.TAG;

import android.util.Log;
import android.widget.Adapter;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.example.ga_23s1_comp2100_6442.ultilities.Constant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.Objects;

public class UserService {
    FirebaseFirestore db;
    public UserService(){
        db = FirebaseFirestore.getInstance();
    }

    /**
     *
     * @param id
     * @param adapter
     * @param users
     * @param field
     */
    public void fetchAndDisplayUsers(String id, ArrayAdapter adapter, List<String> users, String field) {
        //get all the uid from current user's followers
        db.collection(Constant.STUDENTS_COLLECTION).document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        //store all the uid in a temp list
                        List<String> temp = (List<String>) document.get(field);
                        //clear current list before updating to avoid duplicates
                        users.clear();
                        //get every username and names form the uid in temp list
                        for (int i = 0; i < Objects.requireNonNull(temp).size(); i++) {
                            getNameById(temp.get(i), adapter, users);
                        }
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    }
                }
            }
        });
    }

    /**
     *
     * @param id
     * @param adapter
     * @param users
     */
    public void getNameById(String id, ArrayAdapter adapter, List<String> users) {
        //find the student object from on id
        db.collection(Constant.STUDENTS_COLLECTION).document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        //get name field
                        String name = (String) document.get("name");
                        //get username field
                        String userName = (String) document.get("userName");
                        //add name and username in followersNames list for exhibition
                        users.add(name + ":   " + userName);
                        //update data in adapter
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }
}
