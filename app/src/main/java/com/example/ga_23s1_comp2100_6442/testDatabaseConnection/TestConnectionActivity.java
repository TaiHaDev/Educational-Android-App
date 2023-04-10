package com.example.ga_23s1_comp2100_6442.testDatabaseConnection;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.ga_23s1_comp2100_6442.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TestConnectionActivity extends AppCompatActivity {
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_connection);

        // Assume we have a Person class with name and age fields

        mDatabase = FirebaseDatabase.getInstance().getReference();

    }

    @Override
    protected void onStart() {
        super.onStart();
        writeNewPerson();
    }

    public void writeNewPerson() {
        Person person0 = new Person("A", 24);
        Person person1 = new Person("B", 24);
        Person person2= new Person("C", 24);
        Person person3 = new Person("D", 24);
        Person person4 = new Person("E", 24);
        mDatabase.child("Users").child("User1").setValue(person0);
        mDatabase.child("Users").child("User2").setValue(person1);
        mDatabase.child("Users").child("User3").setValue(person2);
        mDatabase.child("Users").child("User4").setValue(person3);
        mDatabase.child("Users").child("User5").setValue(person4);
    }
}