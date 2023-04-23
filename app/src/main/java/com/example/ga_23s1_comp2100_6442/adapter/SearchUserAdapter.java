package com.example.ga_23s1_comp2100_6442.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ga_23s1_comp2100_6442.MessagingPage;
import com.example.ga_23s1_comp2100_6442.R;
import com.example.ga_23s1_comp2100_6442.model.ChatMetaData;
import com.example.ga_23s1_comp2100_6442.model.DatabaseUser;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SearchUserAdapter extends RecyclerView.Adapter<SearchUserAdapter.SearchedUserViewHolder> {
    List<DatabaseUser> data;
    public SearchUserAdapter(List<DatabaseUser> data) {
        this.data = data;
    }
    @NonNull
    @Override
    public SearchUserAdapter.SearchedUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_user, parent, false);
        return new SearchedUserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchUserAdapter.SearchedUserViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    class SearchedUserViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        public SearchedUserViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
        }
        public void bind(DatabaseUser model) {
            TextView nameTextView = itemView.findViewById(R.id.search_user_name);
            nameTextView.setText(model.getName());
            TextView emailTextView = itemView.findViewById(R.id.search_user_email);
            emailTextView.setText(model.getEmail());
            itemView.setOnClickListener(event -> {
                Intent intent = new Intent(itemView.getContext(), MessagingPage.class);
                intent.putExtra("receiverId", model.getUid());
                intent.putExtra("receiverName", model.getName());
                itemView.getContext().startActivity(intent);
            });
        }
    }
}
