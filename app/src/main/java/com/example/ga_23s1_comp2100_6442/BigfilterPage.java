package com.example.ga_23s1_comp2100_6442;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.ga_23s1_comp2100_6442.adapter.CourseAdapter;
import com.example.ga_23s1_comp2100_6442.model.Course;
import com.example.ga_23s1_comp2100_6442.storage.AVLTree;
import com.example.ga_23s1_comp2100_6442.utilities.Constant;
import com.example.ga_23s1_comp2100_6442.utilities.CourseUtil;
import com.example.ga_23s1_comp2100_6442.utilities.FirebaseUtil;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class BigfilterPage extends AppCompatActivity {
    private CourseAdapter adapter;
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bigfilter_page);
        adapter = new CourseAdapter(sharedPref);

        if (FirebaseAuth.getInstance().getCurrentUser()==null){
            startActivity(new Intent(BigfilterPage.this, LoginPage.class));
            finish();
        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        bottomNavigationHandler();

        Button btnComputerScience = findViewById(R.id.btn_computer_science);
        Button btnInformationTechnology = findViewById(R.id.btn_information_technology);
        Button btnDataAnalysis = findViewById(R.id.btn_data_analysis);

        btnComputerScience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BigfilterPage.this, HomePage.class);
                intent.putExtra(Constant.BIG_FILTER_KEY, Constant.COMPUTER_SCIENCE);
                startActivity(intent);
            }
        });
        btnInformationTechnology.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BigfilterPage.this, HomePage.class);
                intent.putExtra(Constant.BIG_FILTER_KEY, Constant.INFORMATION_TECHNOLOGY);
                startActivity(intent);
            }
        });
        btnDataAnalysis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BigfilterPage.this, HomePage.class);
                intent.putExtra(Constant.BIG_FILTER_KEY, Constant.DATA_SCIENCE);
                startActivity(intent);
            }
        });
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
                else if (item.getItemId() == R.id.forumsMenu) {
                    Intent intent = new Intent(getApplicationContext(), ForumPage.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.start_chatting) {
            Intent intent = new Intent(this, ChatListingPage.class);
            startActivity(intent);
        }
        if (item.getItemId() == R.id.search_icon) {
            //do not expand search icon in bigFilterPage
            item.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
                @Override
                public boolean onMenuItemActionExpand(@NonNull MenuItem item) {
                    return false;
                }
                @Override
                public boolean onMenuItemActionCollapse(@NonNull MenuItem item) {
                    return false;
                }
            });
            Intent intent = new Intent(this, HomePage.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}