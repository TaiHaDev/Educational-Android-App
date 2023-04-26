package com.example.ga_23s1_comp2100_6442;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import com.example.ga_23s1_comp2100_6442.model.Student;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Locale;
import java.util.Objects;

public class SignUpPage extends AppCompatActivity implements View.OnClickListener {
    TextView userName;
    TextView name;
    TextView institution;
    TextView password;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch sw;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        userName = (TextView) findViewById(R.id.newUserName);
        name = (TextView) findViewById(R.id.name);
        institution = (TextView) findViewById(R.id.institution);
        password = (TextView) findViewById(R.id.newPassword);
        sw = findViewById(R.id.switch1);
        findViewById(R.id.signUpBtn).setOnClickListener(this);
        auth = FirebaseAuth.getInstance();
    }


    //    @Override
//    protected void onStart() {
//        super.onStart();
//
//        if (mAuth.getCurrentUser() != null) {
//            //handle the already login user
//        }
//    }
    private void registerUser() {
        final String userNameS = userName.getText().toString().trim();
        final String nameS = name.getText().toString().trim();
        final String institutionS = institution.getText().toString().trim();
        String passwordS = password.getText().toString().trim();

        System.out.println(userNameS);
        System.out.println(passwordS);
        auth.createUserWithEmailAndPassword(userNameS, passwordS).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success");
//                    FirebaseUser user = mAuth.getCurrentUser();
                    Student student = new Student(
                            userNameS,
                            nameS,
                            institutionS,
                            Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()
                    );
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    System.out.println("am i running");
                    db.collection("students").document("1111").set(student);


                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                }
            }
        });


    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.signUpBtn) {
            System.out.println("i am signB");
            registerUser();
            Intent intent = new Intent(SignUpPage.this, LoginPage.class);
            startActivity(intent);
        }
    }
}