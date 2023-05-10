package com.example.ga_23s1_comp2100_6442;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.ga_23s1_comp2100_6442.model.Course;

import com.example.ga_23s1_comp2100_6442.model.Request;
import com.example.ga_23s1_comp2100_6442.model.Student;
import com.example.ga_23s1_comp2100_6442.ultilities.Constant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.grpc.internal.JsonUtil;

public class playVideo extends AppCompatActivity {
    private VideoView mainVideoView;
    private TextView courseTitle;
    private TextView courseDescription;
    private Button enrollButton;
    private MediaController mediaController;
    private ProgressBar currentProgress;
    private TextView title;
    String id;

    private String videoName;//video name for searching
    private int playBackPosition = 0;
    Course currentCourse;
    List<Course> course = new ArrayList<>();
    boolean courseCreated = false;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);

        mainVideoView = (VideoView) findViewById(R.id.videoView);
        currentProgress = (ProgressBar) findViewById(R.id.progressBar);
        courseTitle = findViewById(R.id.courseTitle);
        courseDescription = findViewById(R.id.courseDescription);
        enrollButton = findViewById(R.id.enrollButton);
        title = findViewById(R.id.courseTitle);
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
        id = i.getStringExtra("vn");
        assert id != null;
        db.collection(Constant.COURSE_COLLECTION).document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                currentCourse = documentSnapshot.toObject(Course.class);
                assert currentCourse != null;
                courseTitle.setText(currentCourse.getTitle());
                courseDescription.setText(currentCourse.getDescription());
                //create firebase object
                FirebaseStorage storage = FirebaseStorage.getInstance();
                // Create a storage reference from our app
                //check enrolled student list
                String link;
                if (currentCourse.getIsPublic()) {
                    link = currentCourse.getLink();
                    if (currentCourse.getStudentsEnrolled() != null && currentCourse.getStudentsEnrolled().contains(currentUser.getUid())) {
                        enrollButton.setText("enrolled");
                        enrollButton.setEnabled(false);
                    } else {
                        enrollButton.setText("enroll");
                        enrollButton.setEnabled(true);
                    }
                } else if (currentCourse.getStudentsEnrolled() != null && currentCourse.getStudentsEnrolled().contains(currentUser.getUid())) {
                    link = currentCourse.getLink();
                    enrollButton.setText("enrolled");
                    enrollButton.setEnabled(false);
                } else {
                    enrollButton.setText("enroll");
                    enrollButton.setEnabled(true);
                    link = "gs://comp2100-comp6442-assignment.appspot.com/ocean.mp4";
                }
                if (currentCourse.getStudentsApplied() != null && currentCourse.getStudentsApplied().contains(currentUser.getUid())) {
                    enrollButton.setText("pending");
                    enrollButton.setEnabled(false);
                }
                //set links to the reference
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

        //click title to refresh page
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onStart();
            }
        });
        //set enroll button
        enrollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentReference docRef = db.collection("students").document(currentUser.getUid());
                docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Student student = documentSnapshot.toObject(Student.class);
                        if (student != null) {
                            if (currentCourse.getIsPublic()) {
                                //add student studentsEnrolled in current course
                                db.collection(Constant.COURSE_COLLECTION).document(id).update("studentsEnrolled", FieldValue.arrayUnion(currentUser.getUid()));
                                //add current course to this student
                                db.collection(Constant.STUDENTS_COLLECTION).document(currentUser.getUid()).update("coursesEnrolled", FieldValue.arrayUnion(id));
                                enrollButton.setText("enrolled");
                            } else {
                                //create new request
                                Request r = new Request(currentUser.getUid(), currentCourse.getAuthorId(), Request.RequestType.JoinCourse, currentCourse.getTitle() + "/" + id, student.getName(), currentCourse.getAuthor());
                                //add new request to request collection
                                db.collection(Constant.REQUESTS_COLLECTION).add(r);
                                //add current student to pending list
                                db.collection(Constant.COURSE_COLLECTION).document(id).update("studentsApplied", FieldValue.arrayUnion(currentUser.getUid()));
                                enrollButton.setText("pending");
                            }
                            enrollButton.setEnabled(false);
                        }
                    }
                });
            }
        });
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
