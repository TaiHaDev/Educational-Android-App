package com.example.ga_23s1_comp2100_6442;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

public class playVideo extends AppCompatActivity {
    private VideoView mainVideoView;
    private ImageView playBtn;
    private TextView currentTimer;
    private TextView durationTimer;
    private ProgressBar currentProgress;

    private Uri videoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);
        mainVideoView=(VideoView) findViewById(R.id.videoView);

        videoUri= Uri.parse("https://firebasestorage.googleapis.com/v0/b/comp2100-comp6442-assignment.appspot.com/o/mixkit-pack-of-husky-dogs-barking-in-the-snow-48856-medium.mp4?alt=media&token=c52d9d27-4809-4746-a40f-718c6ab9c388");
        mainVideoView.setVideoURI(videoUri);
        mainVideoView.requestFocus();
        mainVideoView.start();
    }
}