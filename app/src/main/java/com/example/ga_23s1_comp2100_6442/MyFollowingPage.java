package com.example.ga_23s1_comp2100_6442;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ga_23s1_comp2100_6442.model.Request;
import com.example.ga_23s1_comp2100_6442.ultilities.Constant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MyFollowingPage extends AppCompatActivity {
    List<String> followingNames = new ArrayList<>();
    ListView listView;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    FirebaseFirestore db;
    ArrayAdapter adapter;
    TextView refresh;
    TextView followInput;
    String inputUserName;
    String receiverName;
    String receiverId;
    Button followButton;
    SwipeRefreshLayout mSwipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_following_page);
        db = FirebaseFirestore.getInstance();
        refresh = findViewById(R.id.myFollowing);
        listView = findViewById(R.id.listFollowing);
        followButton = findViewById(R.id.followButton);
        followInput = findViewById(R.id.followInput);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        mSwipeRefreshLayout = findViewById(R.id.swiperefresh);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, followingNames);
        listView.setAdapter(adapter);
        fetchFollowing();
        followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputUserName = followInput.getText().toString();
                //find receiver by email(userName)
                db.collection(Constant.STUDENTS_COLLECTION).whereEqualTo("userName", inputUserName)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        receiverId =document.getId();
                                        receiverName = (String) document.get("name");
                                    }
                                }
                            }
                        });

                db.collection(Constant.STUDENTS_COLLECTION).document(currentUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                followUser(currentUser.getUid(), receiverId, (String) document.get("name"), receiverName);
                            }
                        }
                    }
                });
            }
        });
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchFollowing();
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchFollowing();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void fetchFollowing() {
        //get all the uid from current user's followers
        db.collection(Constant.STUDENTS_COLLECTION).document(currentUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        //store all the uid in a temp list
                        List<String> temp = (List<String>) document.get("following");
                        //clear current list before updating to avoid duplicates
                        followingNames.clear();
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
                        followingNames.add(name + ":   " + userName);
                        //update data in adapter
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    public void followUser(String sender, String receiver, String senderName, String receiverName) {
        Request r = new Request(sender, receiver, Request.RequestType.Follow, "", senderName, receiverName);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Constant.REQUESTS_COLLECTION).add(r);
    }
}