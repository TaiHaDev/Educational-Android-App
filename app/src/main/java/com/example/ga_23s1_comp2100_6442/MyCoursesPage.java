package com.example.ga_23s1_comp2100_6442;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.ga_23s1_comp2100_6442.adapter.CourseAdapter;
import com.example.ga_23s1_comp2100_6442.model.Course;
import com.example.ga_23s1_comp2100_6442.ultilities.Constant;
import com.example.ga_23s1_comp2100_6442.utilities.FirebaseUtil;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MyCoursesPage extends AppCompatActivity {
    CourseAdapter adapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_courses_page);
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivity(new Intent(MyCoursesPage.this, LoginPage.class));
            finish();
        }
        adapter = new CourseAdapter();
        adapter.setData(new ArrayList<>());
        FirebaseUtil.getMyCourses(adapter);
        recyclerView = findViewById(R.id.courses_list);
        recyclerView.setAdapter(adapter);
    }

    private void fetchAndDisplayCourses() {
        FirebaseFirestore fb = FirebaseFirestore.getInstance();
        fb.collection(Constant.COURSE_COLLECTION_TEST).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<Course> fireBaseData = queryDocumentSnapshots.toObjects(Course.class);
                adapter.setData(fireBaseData);
                RecyclerView recyclerView = findViewById(R.id.courses_list);
                recyclerView.setAdapter(adapter);
            }
        });
    }
}