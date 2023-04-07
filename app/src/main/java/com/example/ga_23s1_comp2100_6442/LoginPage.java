package com.example.ga_23s1_comp2100_6442;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class LoginPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        TextView userName=(TextView) findViewById(R.id.userName);
        TextView password=(TextView) findViewById(R.id.password);


        MaterialButton loginBtn=(MaterialButton) findViewById(R.id.loginBtn);

//        admin
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userName.getText().toString().equals("comp2100@anu.au")&&password.getText().toString().equals("comp2100")||
                        userName.getText().toString().equals("comp6442@anu.au")&&password.getText().toString().equals("comp6442")){
//                    correct
                    Toast.makeText(LoginPage.this,"LOGIN SUCCESSFUL",Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(LoginPage.this, HomePage.class);
                    startActivity(intent);

                }else{
//                    incorrect
                    Toast.makeText(LoginPage.this,"LOGIN FAIL!",Toast.LENGTH_LONG).show();


                }
            }
        });
    }
}