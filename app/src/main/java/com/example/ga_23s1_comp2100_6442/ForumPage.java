package com.example.ga_23s1_comp2100_6442;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ga_23s1_comp2100_6442.adapter.PostAdapter;
import com.example.ga_23s1_comp2100_6442.model.Post;
import com.example.ga_23s1_comp2100_6442.ultilities.PostUtil;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ForumPage extends AppCompatActivity {
    MyDataActivity Send_data;
    Post post;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    Dialog popAddPost;
    TextView popupTitle, popupDescription;

    private PostAdapter postAdapter;
    ImageView popupAddBtn;
    ImageView detailBtn;

    ProgressBar popupClickProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        bottomNavigationHandler();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        postAdapter = new PostAdapter();
        fetchAndDisplayPosts();

        iniPopup();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popAddPost.show();
            }
        });

        ImageView detailBtn=findViewById(R.id.row_arrow);


    }


    private void iniPopup() {

        popAddPost = new Dialog(this);
        popAddPost.setContentView(R.layout.popup_add_post);
        popAddPost.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popAddPost.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.WRAP_CONTENT);
        popAddPost.getWindow().getAttributes().gravity = Gravity.TOP;
        popupTitle = popAddPost.findViewById(R.id.popup_title);
        popupDescription = popAddPost.findViewById(R.id.popup_description);
        popupAddBtn = popAddPost.findViewById(R.id.popup_add);
        popupClickProgress = popAddPost.findViewById(R.id.popup_progressBar);

        popupAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                popupAddBtn.setVisibility(View.INVISIBLE);
                popupClickProgress.setVisibility(View.VISIBLE);
                if (!popupTitle.getText().toString().isEmpty()
                        && !popupDescription.getText().toString().isEmpty()) {
                    // create post Object
                    String title = popupTitle.getText().toString();
                    String description = popupDescription.getText().toString();
                    Send_data = (MyDataActivity) getApplicationContext();
                    String authorName = Send_data.getUser().getName();
                    String userId = Send_data.getUser().getId();
                    post = new Post(title, description, authorName, userId);
                    addPost(post);
                    popupTitle.setText("");
                    popupDescription.setText("");
                } else {
                    showMessage("Please verify all input fields");
                    popupAddBtn.setVisibility(View.VISIBLE);
                    popupClickProgress.setVisibility(View.INVISIBLE);

                }
            }
        });

    }

    private void addPost(Post post) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        db.collection("posts").add(post);
        db.collection("posts").add(post).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                showMessage("Post Added successfully");
                popupClickProgress.setVisibility(View.INVISIBLE);
                popupAddBtn.setVisibility(View.VISIBLE);
                popAddPost.dismiss();
            }
        });


    }




    private void showMessage(String message) {

        Toast.makeText(this, message, Toast.LENGTH_LONG).show();

    }

    public void fetchAndDisplayPosts(){
        FirebaseFirestore fb = FirebaseFirestore.getInstance();
        fb.collection("posts").limit(30).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<Post> fireBaseData = new ArrayList<>();
                PostUtil.SetCoursesFromDocumentSnapshots(queryDocumentSnapshots,fireBaseData);
                postAdapter.setData(fireBaseData);
                RecyclerView recyclerView = findViewById(R.id.posts_list);
                recyclerView.setAdapter(postAdapter);
            }
        });

    }


    private void bottomNavigationHandler() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.forumsMenu);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.coursesMenu) {
                    Intent intent = new Intent(getApplicationContext(), HomePage.class);
                    startActivity(intent);
                    return true;
                } else if (item.getItemId() == R.id.profileMenu) {
                    Intent intent = new Intent(getApplicationContext(), ProfilePage.class);
                    startActivity(intent);
                    return true;
                }
                return false;


            }
        });
    }


}
