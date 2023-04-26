package com.example.ga_23s1_comp2100_6442;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ga_23s1_comp2100_6442.utilities.Constant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginPage extends AppCompatActivity {


    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        TextView userName=(TextView) findViewById(R.id.userName);
        TextView password=(TextView) findViewById(R.id.password);
        MaterialButton loginBtn=(MaterialButton) findViewById(R.id.loginBtn);
        mAuth = FirebaseAuth.getInstance();

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
                            Constant.setUserNameAfterLogin(user.getUid());


                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            Toast.makeText(LoginPage.this,"LOGIN SUCCESSFUL",Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(LoginPage.this, HomePage.class);
                            startActivity(intent);


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
}