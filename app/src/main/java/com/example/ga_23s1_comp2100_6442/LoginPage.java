package com.example.ga_23s1_comp2100_6442;

import static android.content.ContentValues.TAG;

import static com.example.ga_23s1_comp2100_6442.utilities.UploadingDataJob.readingDataFromCSV;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ga_23s1_comp2100_6442.model.Course;
import com.example.ga_23s1_comp2100_6442.model.Lecturer;
import com.example.ga_23s1_comp2100_6442.model.Student;
import com.example.ga_23s1_comp2100_6442.model.StudentFactory;
import com.example.ga_23s1_comp2100_6442.model.User;
import com.example.ga_23s1_comp2100_6442.model.UserFactory;
import com.example.ga_23s1_comp2100_6442.utilities.Constant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class LoginPage extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch sw;
    boolean isLecture;
    FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    MyDataActivity Send_data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        if (FirebaseAuth.getInstance().getCurrentUser()!=null){
            startActivity(new Intent(LoginPage.this, HomePage.class));
            finish();
        }

        TextView userName=(TextView) findViewById(R.id.userName);
        TextView password=(TextView) findViewById(R.id.password);
        sw = findViewById(R.id.switch2);
        sw.setOnCheckedChangeListener(this);
        MaterialButton loginBtn=(MaterialButton) findViewById(R.id.loginBtn);
        mAuth = FirebaseAuth.getInstance();
        Send_data= (MyDataActivity)getApplicationContext();
//        admin
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String userNameS =userName.getText().toString();
                final String passwordS  =password.getText().toString();
                checkLogin(userNameS,passwordS);

            }
        });
    }

    private void checkLogin(String email,String password){

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // fetch the user profile using the uid from the sign in user
                            FirebaseUser user = mAuth.getCurrentUser();

                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            Toast.makeText(LoginPage.this,"LOGIN SUCCESSFUL",Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(LoginPage.this, HomePage.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                            while (user == null) {
                                user = mAuth.getCurrentUser();
                            }
                            if (isLecture){
                                db.collection("lecturers").document(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot document = task.getResult();
                                            if (document.exists()) {
                                                User user = document.toObject(Lecturer.class);
                                                Send_data.setUser(user);
                                                startActivity(intent);
                                            }
                                        }
                                    }
                                });

                            }else {
                                //maybe move to home page
                                Constant.setUserNameAfterLogin(user.getUid());
                                db.collection("students").document(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot document = task.getResult();
                                            if (document.exists()) {
                                                User user = document.toObject(Student.class);
                                                Send_data.setUser(user);
                                                startActivity(intent);
                                            }
                                        }
                                    }
                                });

                            }


//                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginPage.this,"LOGIN FAIL!",Toast.LENGTH_LONG).show();
//                            updateUI(null);
                        }
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            isLecture = true;
            System.out.println(true);
        } else {
            isLecture = false;
            System.out.println(false);
        }
    }
}
