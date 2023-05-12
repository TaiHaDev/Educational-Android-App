package com.example.ga_23s1_comp2100_6442;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class ProfilePage extends AppCompatActivity {
    MyDataActivity Send_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Send_data = (MyDataActivity) getApplicationContext();
        if (!Send_data.getUser().getIsLecturer()) {
            setContentView(R.layout.activity_profile_page);
        } else {
            setContentView(R.layout.activity_lecturer_profile_page);
        }

        bottomNavigationHandler();
        MaterialButton logOutBtn = (MaterialButton) findViewById(R.id.logOutBtn);
        TextView myFollowers = findViewById(R.id.followers);
        TextView myFollowing = findViewById(R.id.following);
        TextView requests = findViewById(R.id.requests);
        TextView myCourses = findViewById(R.id.enrolledCourse);
        TextView myUsername = findViewById(R.id.my_username);
        myUsername.setText(Send_data.getUser().getUserName());

        if (myFollowers != null) {
            myFollowers.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(ProfilePage.this, MyFollowersPage.class));
                }
            });
        }
        if (myFollowing != null) {
            myFollowing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(ProfilePage.this, MyFollowingPage.class));
                }
            });
        }
        requests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfilePage.this, RequestPage.class));
            }
        });
        myCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfilePage.this, MyCoursesPage.class));
            }
        });
        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(ProfilePage.this, LoginPage.class));
                    Toast.makeText(ProfilePage.this, user.getEmail() + " Sign out!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ProfilePage.this, "You aren't login Yet!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();
            System.out.println(name + " " + email + "  " + photoUrl + "  " + uid);
        }
    }

    private void bottomNavigationHandler() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.profileMenu);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.forumsMenu) {
                    Intent intent = new Intent(getApplicationContext(), ForumPage.class);
                    startActivity(intent);
                    return true;
                }
                if (item.getItemId() == R.id.coursesMenu) {
                    // Intent intent = new Intent(getApplicationContext(), HomePage.class);
                    Intent intent = new Intent(getApplicationContext(), BigfilterPage.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
    }
}
