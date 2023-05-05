package com.example.ga_23s1_comp2100_6442;

import static com.example.ga_23s1_comp2100_6442.utilities.UploadingDataJob.readingDataFromCSV;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.ga_23s1_comp2100_6442.adapter.CourseAdapter;
import com.example.ga_23s1_comp2100_6442.model.Course;
import com.example.ga_23s1_comp2100_6442.storage.AVLTree;
import com.example.ga_23s1_comp2100_6442.utilities.Constant;
import com.example.ga_23s1_comp2100_6442.utilities.FirebaseUtil;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import org.checkerframework.checker.units.qual.A;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

public class HomePage extends AppCompatActivity {
    private CourseAdapter adapter;
    public static AVLTree historySearchTree;
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        loadRecentlySearch();
        adapter = new CourseAdapter(sharedPref);

        if (FirebaseAuth.getInstance().getCurrentUser()==null){
            startActivity(new Intent(HomePage.this, LoginPage.class));
            finish();
        }
        adapter = new CourseAdapter();
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

    /**
     * load the search history tree from shared preferences
     * @author: Tai Ha
     */

    public void loadRecentlySearch() {
        if (historySearchTree != null) return;
        Gson gson = new Gson();
        sharedPref = getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
        String jsonString = sharedPref.getString("historySearchTree", null);
        historySearchTree = gson.fromJson(jsonString, AVLTree.class);
        if (historySearchTree == null) historySearchTree = new AVLTree();
        historySearchTree.inOrderTraversal();
    }







}
