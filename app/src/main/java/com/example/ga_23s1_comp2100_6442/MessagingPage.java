package com.example.ga_23s1_comp2100_6442;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.ga_23s1_comp2100_6442.adapter.MessageAdapter;
import com.example.ga_23s1_comp2100_6442.model.User;
import com.example.ga_23s1_comp2100_6442.model.UserMessage;
import com.example.ga_23s1_comp2100_6442.ultilities.Constant;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.ktx.Firebase;

public class MessagingPage extends AppCompatActivity {
    MessageAdapter adapter;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging_page);
        Bundle bundle = getIntent().getExtras();
        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference reference = db.getReference().child(Constant.MESSAGE_COLLECTION).child(bundle.getString("messageId"));
        FirebaseRecyclerOptions<UserMessage> data = new FirebaseRecyclerOptions
                .Builder<UserMessage>()
                .setQuery(reference, UserMessage.class)
                .build();
        adapter = new MessageAdapter(data);
        RecyclerView recyclerView = findViewById(R.id.message_recycler_view);
        recyclerView.setAdapter(adapter);
        // textChangelistener, send button is disabled when there is no text in the field
        ImageView sendingMessageButton = findViewById(R.id.sendButton);
        sendingMessageButton.setOnClickListener(
                event -> {
                    EditText messageEdittext = findViewById(R.id.messageEditText);
                    String message = messageEdittext.getText().toString();
                    reference.push().setValue(new UserMessage(message, mAuth.getCurrentUser().getEmail(), null));
                    messageEdittext.setText("");
                }
        );
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onPause() {
        super.onPause();
        adapter.stopListening();
    }
}