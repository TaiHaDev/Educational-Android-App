package com.example.ga_23s1_comp2100_6442;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MyFollowersPage extends AppCompatActivity {

    List<String> followersNames = new ArrayList<>();
    ListView listView;
    FirebaseAuth mAuth;
    FirebaseUser user;
    ArrayAdapter adapter;
    TextView refresh;
    SwipeRefreshLayout mSwipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_followers_page);
        refresh = findViewById(R.id.myFollowers);
        listView = findViewById(R.id.listFollowers);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        mSwipeRefreshLayout = findViewById(R.id.swiperefresh);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, followersNames);
        listView.setAdapter(adapter);
        fetchFollowers();
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchFollowers();
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchFollowers();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void fetchFollowers() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        //get all the uid from current user's followers
        db.collection("students").document(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        //store all the uid in a temp list
                        List<String> temp = (List<String>) document.get("followers");
                        //clear current list before updating to avoid duplicates
                        followersNames.clear();
                        //get every username and names form the uid in temp list
                        for (int i = 0; i < Objects.requireNonNull(temp).size(); i++) {
                            getNameFormId(temp.get(i));
                        }
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    public void getNameFormId(String id) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        //find the student object from on id
        db.collection("students").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
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
                        followersNames.add(name + ":   " + userName);
                        //update data in adapter
                        adapter.notifyDataSetChanged();
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

    }
}