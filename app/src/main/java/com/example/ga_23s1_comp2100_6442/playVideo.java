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

    private Uri videoUri;
    private String vn;//video name
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);
        mainVideoView = (VideoView) findViewById(R.id.videoView);

        Bundle bundle = getIntent().getExtras();//create bundle to get videoName;
        System.out.println(bundle.getString("vn"));
        if (bundle.getString("vn")!=null){
            vn=bundle.getString("vn");
        }
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference pathReference = storageRef.child(vn+".mp4");
        pathReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                mainVideoView.setVideoURI(uri);
                // Got the download URL for 'users/me/profile.png'
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                System.out.println("-----------------------------------------------------cannot find");
                // Handle any errors
            }
        });
        mainVideoView.requestFocus();
        mainVideoView.start();

    }
}