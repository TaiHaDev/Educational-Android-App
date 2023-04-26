package com.example.ga_23s1_comp2100_6442;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ga_23s1_comp2100_6442.model.Lecturer;
import com.example.ga_23s1_comp2100_6442.model.Student;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Locale;
import java.util.Objects;

public class SignUpPage extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    TextView userName;
    TextView name;
    TextView institution;
    TextView password;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch sw;
    private FirebaseAuth auth;
    boolean isLecture;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        userName = (TextView) findViewById(R.id.newUserName);
        name = (TextView) findViewById(R.id.name);
        institution = (TextView) findViewById(R.id.institution);
        password = (TextView) findViewById(R.id.newPassword);
        sw = findViewById(R.id.switch1);
        sw.setOnCheckedChangeListener(this);
        findViewById(R.id.signUpBtn).setOnClickListener(this);
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            auth.signOut();
        }
    }


    //    @Override
//    protected void onStart() {
//        super.onStart();
//
//        if (mAuth.getCurrentUser() != null) {
//            //handle the already login user
//        }
//    }
    private void registerUser(String userNameS, String passwordS, String nameS, String institutionS) {

        System.out.println(userNameS);
        System.out.println(passwordS);
        auth.createUserWithEmailAndPassword(userNameS, passwordS).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    addUserToDatabase(userNameS, nameS, institutionS);
                    Log.d(TAG, "createUserWithEmail:success");
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                }
            }
        });
    }

    public void addUserToDatabase(String userNameS, String nameS, String institutionS) {
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        //wait for current user to be initialize
        while (user == null) {
            user = auth.getCurrentUser();
        }
        if (isLecture) {
            Lecturer lecturer = new Lecturer(
                    userNameS,
                    nameS,
                    institutionS,
                    user.getUid()
            );
            db.collection("lecturers").document(lecturer.getId()).set(lecturer);
        } else {
            Student student = new Student(
                    userNameS,
                    nameS,
                    institutionS,
                    user.getUid()
            );
            db.collection("students").document(student.getId()).set(student);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.signUpBtn) {
            final String userNameS = userName.getText().toString().trim();
            final String nameS = name.getText().toString().trim();
            final String institutionS = institution.getText().toString().trim();
            String passwordS = password.getText().toString().trim();
            registerUser(userNameS, passwordS, nameS, institutionS);
            addUserToDatabase(userNameS, nameS, institutionS);
            Intent intent = new Intent(SignUpPage.this, LoginPage.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            isLecture = true;
            System.out.println(isLecture);
        } else {
            isLecture = false;
            System.out.println(isLecture);
        }
    }
}