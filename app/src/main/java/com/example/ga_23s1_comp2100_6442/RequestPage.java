package com.example.ga_23s1_comp2100_6442;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.ga_23s1_comp2100_6442.model.Request;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class RequestPage extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseUser user;
    RequestAdapter adapter;
    RecyclerView recyclerView;
    TextView refresh;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_page);
        recyclerView = findViewById(R.id.requests_list);
        refresh = findViewById(R.id.myRequest);
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivity(new Intent(RequestPage.this, LoginPage.class));
            finish();
        }
        adapter = new RequestAdapter();
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        fetchAndDisplayRequests();
    }

    @Override
    protected void onStart() {
        super.onStart();
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchAndDisplayRequests();
            }
        });
    }

    private void fetchAndDisplayRequests() {
        db.collection("requests").whereEqualTo("receiver", user.getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<String> requestIds = new ArrayList<>();
                        for (QueryDocumentSnapshot q:queryDocumentSnapshots){
                            requestIds.add(q.getId());
                        }
                        List<Request> requests = queryDocumentSnapshots.toObjects(Request.class);
                        adapter.setRequests(requests,requestIds);
                        recyclerView.setAdapter(adapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        List<Request> requests = new ArrayList<>();
                        List<String> requestIds = new ArrayList<>();
                        adapter.setRequests(requests,requestIds);
                        recyclerView.setAdapter(adapter);
                    }
                });

    }


}