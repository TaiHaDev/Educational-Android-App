package com.example.ga_23s1_comp2100_6442;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;

import com.example.ga_23s1_comp2100_6442.adapter.ChatListingAdapter;
import com.example.ga_23s1_comp2100_6442.model.ChatMetaData;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ChatListingPage extends AppCompatActivity {
    FirebaseAuth mAuth;
    ChatListingAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_listing_page);
        setUpToolbar();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        adapter = new ChatListingAdapter();
        RecyclerView chatListingRecyclerView = findViewById(R.id.chat_listing);
        chatListingRecyclerView.setAdapter(adapter);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("metadata").child(mAuth.getCurrentUser().getUid());
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ChatMetaData chatMetaData = snapshot.getValue(ChatMetaData.class);
                adapter.addData(chatMetaData);
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                ChatMetaData chatMetaData = snapshot.getValue(ChatMetaData.class);
                adapter.removeData(chatMetaData);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void setUpToolbar() {
        MaterialToolbar toolbar = findViewById(R.id.chat_listing_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.chat_listing_page_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Intent intent = new Intent(this, HomePage.class);
            Intent intent = new Intent(this, BigfilterPage.class);
            startActivity(intent);
            finish();
        } else if (item.getItemId() == R.id.user_search) {
            Intent intent = new Intent(this, UserSearchingPage.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

}