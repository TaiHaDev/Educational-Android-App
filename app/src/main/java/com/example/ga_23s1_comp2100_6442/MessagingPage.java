package com.example.ga_23s1_comp2100_6442;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ga_23s1_comp2100_6442.adapter.MessageAdapter;
import com.example.ga_23s1_comp2100_6442.model.ChatMetaData;
import com.example.ga_23s1_comp2100_6442.model.UserMessage;
import com.example.ga_23s1_comp2100_6442.ultilities.Constant;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MessagingPage extends AppCompatActivity {
    MessageAdapter adapter;
    FirebaseAuth mAuth;
    RecyclerView recyclerView;
    String receiverId;
    String receiverName;
    String currentUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging_page);
        setUpToolbar();
        mAuth = FirebaseAuth.getInstance();
        currentUid = mAuth.getCurrentUser().getUid();
        Bundle bundle = getIntent().getExtras();
        receiverId = bundle.getString("receiverId");
        receiverName = bundle.getString("receiverName");
        String messageId = generateMessageId(currentUid, receiverId);
        setChatBlock();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference reference = db.getReference().child(Constant.MESSAGE_COLLECTION).child(messageId);
        FirebaseRecyclerOptions<UserMessage> data = new FirebaseRecyclerOptions
                .Builder<UserMessage>()
                .setQuery(reference, UserMessage.class)
                .build();
        adapter = new MessageAdapter(data);
        recyclerView = findViewById(R.id.message_recycler_view);
        recyclerView.setAdapter(adapter);
        // textChangelistener, send button is disabled when there is no text in the field
        ImageView sendingMessageButton = findViewById(R.id.sendButton);
        addScrollToBottomObserverToAdapter();
        sendingMessageButton.setOnClickListener(
                event -> {
                    EditText messageEdittext = findViewById(R.id.messageEditText);
                    String message = messageEdittext.getText().toString();
                    long currentTimestamp = System.currentTimeMillis();
                    reference.push().setValue(new UserMessage(message, Constant.USER_PROFILE.getName(), null));
                    updateChatListing(currentUid, new ChatMetaData(receiverId, receiverName, message, true, currentTimestamp));
                    updateChatListing(receiverId, new ChatMetaData(currentUid, Constant.USER_PROFILE.getName(), message, false, currentTimestamp));
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

    public String generateMessageId(String uid1, String uid2) {
        if (uid1.compareTo(uid2) >= 0) {
            return uid2 + uid1;
        } else {
            return uid1 + uid2;
        }
    }

    public void updateChatListing(String uid, ChatMetaData chatMetaData) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReference(Constant.METADATA_COLLECTION)
                .child(uid);
        Query query = databaseReference.orderByChild("id").equalTo(chatMetaData.getId()).limitToFirst(1);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // TODO make this asynchronous code cleaner.
                if (snapshot.getChildrenCount() == 0) {
                    databaseReference.push().setValue(chatMetaData);
                }
                for (DataSnapshot instance : snapshot.getChildren()) {

                    instance.getRef().removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            databaseReference.push().setValue(chatMetaData);

                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void addScrollToBottomObserverToAdapter() {
        RecyclerView.AdapterDataObserver dataObserver = new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                recyclerView.scrollToPosition(adapter.getItemCount() - 1);

            }
        };
        adapter.registerAdapterDataObserver(dataObserver);
    }

    public void setUpToolbar() {
        MaterialToolbar toolbar = findViewById(R.id.message_page_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.messaging_page_menu, menu);
        setIconBlock(menu.findItem(R.id.block_chat));
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(this, ChatListingPage.class);
            startActivity(intent);
            finish();
        } else if (item.getItemId() == R.id.block_chat) {
            setupBlockDialog(item);
        }
        return super.onOptionsItemSelected(item);
    }

    public void setupBlockDialog(MenuItem menuItem) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setCancelable(true);
        if (menuItem.getTitle().equals("block")) {
            dialogBuilder.setMessage("Do you want to block this user? Note that they will not be " +
                    "able to send you message anymore.");
            dialogBuilder.setTitle("Block " + receiverName);
            dialogBuilder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
                blockingThisUser();
                menuItem.setTitle("unblock");
                menuItem.setIcon(R.drawable.unblock_chat);
                dialog.cancel();
            });
        } else {
            dialogBuilder.setMessage("Do you want to unblock this user? They will be able to text you again.");
            dialogBuilder.setTitle("Unblock " + receiverName);
            dialogBuilder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
                unblockingThisUser();
                menuItem.setTitle("block");
                menuItem.setIcon(R.drawable.block_chat);
                dialog.cancel();
            });
        }
        dialogBuilder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
            dialog.cancel();
        });
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
    }

    public void blockingThisUser() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference().child("block");
        ref.child(receiverId).child(currentUid).setValue("");
    }

    public void setIconBlock(MenuItem menuItem) {
        DatabaseReference db = FirebaseDatabase.getInstance()
                .getReference("block")
                .child(receiverId);
        db.child(currentUid).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                menuItem.setIcon(R.drawable.unblock_chat);
                menuItem.setTitle("unblock");


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                menuItem.setIcon(R.drawable.block_chat);
                menuItem.setTitle("block");
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void setChatBlock() {
        DatabaseReference db = FirebaseDatabase.getInstance()
                .getReference("block")
                .child(currentUid);
        db.child(receiverId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    LinearLayout layout = findViewById(R.id.message_chat_layout);
                    layout.setVisibility(View.GONE);
                    TextView blockingInfoTextView = findViewById(R.id.blocking_info);
                    blockingInfoTextView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void unblockingThisUser() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("block").child(receiverId);
        ref.child(currentUid).removeValue();
    }


}