package com.example.ga_23s1_comp2100_6442;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.ga_23s1_comp2100_6442.adapter.SearchUserAdapter;
import com.example.ga_23s1_comp2100_6442.model.Course;
import com.example.ga_23s1_comp2100_6442.model.DatabaseUser;
import com.example.ga_23s1_comp2100_6442.utilities.Constant;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class UserSearchingPage extends AppCompatActivity {
    SearchUserAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_searching_page);
        fetchAndDisplayUser();



    }
    private void fetchAndDisplayUser() {
        FirebaseFirestore fb = FirebaseFirestore.getInstance();
        fb.collection(Constant.USER_COLLECTION).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DatabaseUser> fireBaseData = queryDocumentSnapshots.toObjects(DatabaseUser.class);
                adapter = new SearchUserAdapter(fireBaseData);
                RecyclerView recyclerView = findViewById(R.id.searching_page_recycler_view);
                recyclerView.setAdapter(adapter);
            }
        });
    }
}