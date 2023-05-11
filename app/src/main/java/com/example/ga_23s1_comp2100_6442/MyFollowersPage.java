package com.example.ga_23s1_comp2100_6442;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ga_23s1_comp2100_6442.Services.UserService;
import com.example.ga_23s1_comp2100_6442.ultilities.Constant;
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

    List<String> followersNames;
    ListView listView;
    FirebaseFirestore db;
    FirebaseUser currentUser;
    ArrayAdapter adapter;
    TextView titleRefresh;
    SwipeRefreshLayout mSwipeRefreshLayout;
    UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_followers_page);

        userService = new UserService();
        //prepare firebase
        db = FirebaseFirestore.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        prepareLayout();
        //fetch data
        userService.fetchAndDisplayUsers(currentUser.getUid(),adapter,followersNames,"followers");
    }
    private void prepareLayout() {
        followersNames = new ArrayList<>();
        titleRefresh = findViewById(R.id.myFollowers);
        listView = findViewById(R.id.listFollowers);
        mSwipeRefreshLayout = findViewById(R.id.swiperefresh);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, followersNames);
        listView.setAdapter(adapter);

        //set refresh page
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                userService.fetchAndDisplayUsers(currentUser.getUid(),adapter,followersNames,"followers");
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        titleRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userService.fetchAndDisplayUsers(currentUser.getUid(),adapter,followersNames,"followers");
            }
        });
    }
}