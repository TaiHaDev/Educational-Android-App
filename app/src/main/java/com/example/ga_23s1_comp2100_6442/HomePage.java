package com.example.ga_23s1_comp2100_6442;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
                String vn = videoName.getText().toString();
                System.out.println("------------------------------------------------------");
                System.out.println("vn:"+vn);
                Intent intent = new Intent(HomePage.this, playVideo.class);
                intent.putExtra("vn",vn);//transfer the vn to next activity
                startActivity(intent);
            }
        });
    }
}