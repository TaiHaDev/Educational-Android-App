package com.example.ga_23s1_comp2100_6442;

import static com.example.ga_23s1_comp2100_6442.utilities.UploadingDataJob.readingDataFromCSV;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.ga_23s1_comp2100_6442.adapter.CourseAdapter;
import com.example.ga_23s1_comp2100_6442.model.Course;
import com.example.ga_23s1_comp2100_6442.utilities.Constant;
import com.example.ga_23s1_comp2100_6442.utilities.FirebaseUtil;
import com.example.ga_23s1_comp2100_6442.ultilities.Constant;
import com.example.ga_23s1_comp2100_6442.ultilities.FirebaseUtil;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class HomePage extends AppCompatActivity {
    CourseAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new CourseAdapter();
        setContentView(R.layout.activity_home_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        bottomNavigationHandler();
        fetchAndDisplayCourses();

    }

    private void bottomNavigationHandler() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.coursesMenu);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.profileMenu) {
                    Intent intent = new Intent(getApplicationContext(), ProfilePage.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
    }
    private void fetchAndDisplayCourses() {
        FirebaseFirestore fb = FirebaseFirestore.getInstance();
        fb.collection(Constant.COURSE_COLLECTION).limit(30).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<Course> fireBaseData = queryDocumentSnapshots.toObjects(Course.class);
                adapter.setData(fireBaseData);
                RecyclerView recyclerView = findViewById(R.id.courses_list);
                recyclerView.setAdapter(adapter);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search_icon);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search our Courses");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                FirebaseUtil.simpleQueryFireStore(query, adapter);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        // messaging icon behaviour
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.start_chatting) {
            Intent intent = new Intent(this, ChatListingPage.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
