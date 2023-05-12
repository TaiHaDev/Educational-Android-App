package com.example.ga_23s1_comp2100_6442;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.ga_23s1_comp2100_6442.model.Course;
import com.example.ga_23s1_comp2100_6442.model.Post;
import com.example.ga_23s1_comp2100_6442.ultilities.Constant;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.util.HashMap;
import java.util.Map;

public class PostDetails extends AppCompatActivity {
    Post currentPost;

    FirebaseAuth mAuth;
    FirebaseUser currentUser;

    TextView postTitle;
    TextView postDescription;
    TextView author;
    Button blockButton;
    MyDataActivity Send_data;

    String id;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //get current auth
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        Send_data = (MyDataActivity) getApplicationContext();
        if (Send_data.getUser().getIsLecturer()) {
            setContentView(R.layout.post_view_for_lecturer);
            blockButton=findViewById(R.id.block_post_button);
        } else {
            setContentView(R.layout.post_view);
        }
        setBlockButton();

        postTitle = findViewById(R.id.post_detail_title);
        postDescription = findViewById(R.id.post_detail_desc);
        author = findViewById(R.id.post_detail_date_name);

    }

    @Override
    protected void onStart() {
        super.onStart();
        //create bundle to get videoName from last activity(HomePage)
        Intent i = getIntent();
        //check if videoName is passed by bundle
        id = i.getStringExtra("pid");
        db.collection("posts").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                currentPost = documentSnapshot.toObject(Post.class);
                if (currentPost.isVisibility() || Send_data.getUser().getIsLecturer()) {
                    assert currentPost != null;
                    postTitle.setText(currentPost.getTitle());
                    postDescription.setText(currentPost.getDescription());
                    if (!currentPost.isAnonymous() || Send_data.getUser().getIsLecturer()) {
                        author.setText(currentPost.getUserName());
                    } else {
                        System.out.println(currentPost.isAnonymous());
                        author.setText("Anonymous");
                    }

                    //create firebase object
                    FirebaseStorage storage = FirebaseStorage.getInstance();
                    // Create a storage reference from our app
                    //check enrolled student list
                    String link;
                }
            }
        });

    }

    public void setBlockButton() {
        if (Send_data.getUser().getIsLecturer()) {
            blockButton.setVisibility(View.VISIBLE);
            blockButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    db.collection("posts").document(id).update("visibility", false);
                    blockButton.setEnabled(false);
                    Toast.makeText(PostDetails.this,"Block Successfully!Student won't see this post.",Toast.LENGTH_LONG).show();
                }
            });
        }
//        else {
////            blockButton.setVisibility(View.INVISIBLE);
//
//        }
    }
}




