package com.example.ga_23s1_comp2100_6442;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomePage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        TextView videoName = (TextView) findViewById(R.id.vName);
        Button button = (Button) findViewById(R.id.buttonPlay);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get videoName from textView
                String vn = videoName.getText().toString();
                Intent intent = new Intent(HomePage.this, playVideo.class);
                //pass the videoName to next activity(playVideo)
                intent.putExtra("vn",vn);
                startActivity(intent);
            }
        });

    }
}