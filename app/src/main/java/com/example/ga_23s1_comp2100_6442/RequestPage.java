package com.example.ga_23s1_comp2100_6442;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.ga_23s1_comp2100_6442.model.Course;
import com.example.ga_23s1_comp2100_6442.model.Lecturer;
import com.example.ga_23s1_comp2100_6442.model.Request;
import com.example.ga_23s1_comp2100_6442.model.Student;
import com.example.ga_23s1_comp2100_6442.ultilities.Constant;
import com.example.ga_23s1_comp2100_6442.ultilities.FirebaseUtil;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RequestPage extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseUser user;
    RequestAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_page);

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivity(new Intent(RequestPage.this, LoginPage.class));
            finish();
        }
        adapter = new RequestAdapter();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        fetchAndDisplayRequests();
    }

    @Override
    protected void onStart() {
        super.onStart();
//        Request r = new Request("11","22", Request.RequestType.JoinCourse,"2fRes1tsJb1yvffHVnsx");
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        db.collection("requests").add(r);

    }

    private void fetchAndDisplayRequests() {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("requests").whereEqualTo("receiver", user.getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<Request> requests = queryDocumentSnapshots.toObjects(Request.class);
                        adapter.setData(requests);
                        RecyclerView recyclerView = findViewById(R.id.requests_list);
                        recyclerView.setAdapter(adapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        List<Request> requests = new ArrayList<>();
                        adapter.setData(requests);
                        RecyclerView recyclerView = findViewById(R.id.requests_list);
                        recyclerView.setAdapter(adapter);
                    }
                });

    }
}