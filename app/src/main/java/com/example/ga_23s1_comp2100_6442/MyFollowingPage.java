package com.example.ga_23s1_comp2100_6442;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ga_23s1_comp2100_6442.Services.UserService;
import com.example.ga_23s1_comp2100_6442.model.Request;
import com.example.ga_23s1_comp2100_6442.model.User;
import com.example.ga_23s1_comp2100_6442.ultilities.Constant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MyFollowingPage extends AppCompatActivity {
    List<String> followingNames;
    ListView listView;
    FirebaseUser currentUser;
    FirebaseFirestore db;
    ArrayAdapter adapter;
    TextView titleRefresh;
    TextView followInput;
    String inputUserName;
    Button followButton;
    SwipeRefreshLayout swipeRefreshLayout;
    MyDataActivity myDataActivity;
    UserService userService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_following_page);

        userService = new UserService();
        myDataActivity = (MyDataActivity) getApplicationContext();
        //prepare firebase
        db = FirebaseFirestore.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        prepareLayout();
        //fetch data
        userService.fetchAndDisplayUsers(currentUser.getUid(), adapter, followingNames, "following");
    }

    private void prepareLayout() {
        followingNames = new ArrayList<>();
        titleRefresh = findViewById(R.id.myFollowing);
        listView = findViewById(R.id.listFollowing);
        followButton = findViewById(R.id.followButton);
        followInput = findViewById(R.id.followInput);
        swipeRefreshLayout = findViewById(R.id.swiperefresh);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, followingNames);
        listView.setAdapter(adapter);
        followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputUserName = followInput.getText().toString();
                //send request
                userService.followUserByEmail(currentUser.getUid(), myDataActivity.getUser().getName(), inputUserName);
                Toast.makeText(MyFollowingPage.this, "Request sent", Toast.LENGTH_LONG).show();
                //clean input
                followInput.setText("");
            }
        });
        //set refresh page
        titleRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userService.fetchAndDisplayUsers(currentUser.getUid(), adapter, followingNames, "following");
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                userService.fetchAndDisplayUsers(currentUser.getUid(), adapter, followingNames, "following");
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}