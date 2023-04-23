package com.example.ga_23s1_comp2100_6442.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ga_23s1_comp2100_6442.MessagingPage;
import com.example.ga_23s1_comp2100_6442.R;
import com.example.ga_23s1_comp2100_6442.model.ChatMetaData;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatListingAdapter extends RecyclerView.Adapter<ChatListingAdapter.ChatMetaDataViewHolder> {

    private List<ChatMetaData> data = new ArrayList<>();


    @NonNull
    @Override
    public ChatMetaDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_metadata, parent, false);
        return new ChatMetaDataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatMetaDataViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ChatMetaDataViewHolder extends RecyclerView.ViewHolder {
        View chatView;
        public ChatMetaDataViewHolder(@NonNull View itemView) {
            super(itemView);
            this.chatView = itemView;
        }
        public void bind(ChatMetaData model) {
            TextView chatGroupName = chatView.findViewById(R.id.chat_group_name);
            chatGroupName.setText(model.getName());
            TextView metaDataMessage = chatView.findViewById(R.id.meta_data);
            Date date = new Date(model.getTimestamp());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String formattedDate = sdf.format(date);
            metaDataMessage.setText(model.getLast() + " . " + formattedDate);
            chatView.setOnClickListener(event -> {
                Intent intent = new Intent(chatView.getContext(), MessagingPage.class);
                intent.putExtra("receiverId", model.getId());
                intent.putExtra("receiverName", model.getName());
                chatView.getContext().startActivity(intent);
            });
            if (!model.isSeen()) {
                chatGroupName.setTextColor(Color.BLACK);
                metaDataMessage.setTextColor(Color.BLACK);
            }
        }
    }
    public void addData(ChatMetaData instance) {
        data.add(instance);
        notifyItemInserted(data.size() - 1);
    }
    public void removeData(ChatMetaData instance) {
        int index = data.indexOf(instance);
        data.remove(index);
        notifyItemRemoved(index);
    }

}
