package com.example.ga_23s1_comp2100_6442;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.ga_23s1_comp2100_6442.model.Course;
import com.example.ga_23s1_comp2100_6442.ultilities.Constant;
import com.example.ga_23s1_comp2100_6442.ultilities.FirebaseUtil;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class playVideo extends AppCompatActivity {
    private VideoView mainVideoView;
    private ImageView playBtn;
    private TextView currentTimer;
    private TextView durationTimer;
    private MediaController mediaController;
    private ProgressBar currentProgress;

    private String videoName;//video name for searching
    private int playBackPosition = 0;
    Course currentCourse;
    List<Course> course = new ArrayList<>();
    boolean courseCreated = false;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);

        mainVideoView = (VideoView) findViewById(R.id.videoView);
        currentProgress = (ProgressBar) findViewById(R.id.progressBar);
        //get current auth
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        //set mediaController
        mediaController = new MediaController(this);
        mainVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mediaController.setAnchorView(findViewById(R.id.videoContainer));
                mainVideoView.setMediaController(mediaController);
                mainVideoView.seekTo(playBackPosition);
                mainVideoView.start();
            }
        });
        mainVideoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {

                if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
                    currentProgress.setVisibility(View.INVISIBLE);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        //create bundle to get videoName from last activity(HomePage)
        Intent i = getIntent();
        //check if videoName is passed by bundle
        String id = i.getStringExtra("vn");
        assert id != null;
        db.collection(Constant.COURSE_COLLECTION_TEST).document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                currentCourse = documentSnapshot.toObject(Course.class);
                //create firebase object
                FirebaseStorage storage = FirebaseStorage.getInstance();
                // Create a storage reference from our app
                String link;
                if (currentCourse.getIsPublic()) {
                    link = currentCourse.getLink();
                } else if (currentCourse.getStudentsEnrolled().contains(currentUser.getUid())) {
                    link = currentCourse.getLink();
                } else {
                    link = "gs://comp2100-comp6442-assignment.appspot.com/ocean.mp4";
                }
                StorageReference storageRef = storage.getReferenceFromUrl(link);
                storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        // Got the download URL for 'videoName.mp4' and set to videoView
                        mainVideoView.setVideoURI(uri);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(playVideo.this, "Video not found!", Toast.LENGTH_LONG).show();
                        // Handle any errors
                    }
                });

            }
        });

        currentProgress.setVisibility(View.VISIBLE);


    }

    @Override
    protected void onPause() {
        super.onPause();
        mainVideoView.pause();
        playBackPosition = mainVideoView.getCurrentPosition();
    }

    @Override
    protected void onStop() {
        mainVideoView.stopPlayback();
        super.onStop();
    }
}
