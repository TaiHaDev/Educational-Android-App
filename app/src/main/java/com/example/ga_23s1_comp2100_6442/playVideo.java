package com.example.ga_23s1_comp2100_6442;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class playVideo extends AppCompatActivity {
    private VideoView mainVideoView;
    private ImageView playBtn;
    private TextView currentTimer;
    private TextView durationTimer;
    private ProgressBar currentProgress;

    private String videoName;//video name for searching

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);
        mainVideoView = (VideoView) findViewById(R.id.videoView);

        //create bundle to get videoName from last activity(HomePage)
        Bundle bundle = getIntent().getExtras();
        //check if videoName is passed by bundle
        if (bundle.getString("vn") != null) {
            videoName = bundle.getString("vn");
        }
        //create firebase object
        FirebaseStorage storage = FirebaseStorage.getInstance();
        // Create a storage reference from our app
        StorageReference storageRef = storage.getReference();
        // Create a reference to "videoName.mp4"
        StorageReference pathReference = storageRef.child(videoName + ".mp4");
        pathReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'videoName.mp4' and set to videoView
                mainVideoView.setVideoURI(uri);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(playVideo.this,"Video not found!",Toast.LENGTH_LONG).show();
                // Handle any errors
            }
        });
        mainVideoView.requestFocus();
        mainVideoView.start();

    }
}