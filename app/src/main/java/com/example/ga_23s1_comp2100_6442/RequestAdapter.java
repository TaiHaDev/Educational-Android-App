package com.example.ga_23s1_comp2100_6442;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ga_23s1_comp2100_6442.model.Course;
import com.example.ga_23s1_comp2100_6442.model.Request;
import com.example.ga_23s1_comp2100_6442.ultilities.Constant;
import com.example.ga_23s1_comp2100_6442.ultilities.FirebaseUtil;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.ViewHolder> {
    List<Request> data;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void setData(List<Request> data) {
        this.data = data;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView view = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.request_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Request currentRequest = data.get(position);
        System.out.println(currentRequest.getInformation());
        TextView courseNameTextView = holder.rootView.findViewById(R.id.sender_name);
        courseNameTextView.setText(currentRequest.getSenderName());
        TextView courseAuthorTextView = holder.rootView.findViewById(R.id.request_info);
        String[] information = currentRequest.getInformation().split("/");
        String info;
        if (Objects.equals(currentRequest.getRequestType(), Request.RequestType.Follow)) {
            info = currentRequest.getSenderName()+" wants to follow you";
        } else {
            info = currentRequest.getSenderName()+" wants to join "+information[0];
        }
        courseAuthorTextView.setText(info);

        //set accept and deny buttons
        Button accept = holder.rootView.findViewById(R.id.accept);
        Button deny = holder.rootView.findViewById(R.id.deny);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Objects.equals(currentRequest.getRequestType(), Request.RequestType.Follow)) {
                    db.collection("students").document(currentRequest.getReceiver()).update("followers", FieldValue.arrayUnion(currentRequest.getSender()));
                    db.collection("students").document(currentRequest.getSender()).update("following", FieldValue.arrayUnion(currentRequest.getReceiver()));
                } else {
                    db.collection(Constant.COURSE_COLLECTION_TEST).document(information[1]).update("studentsEnrolled", FieldValue.arrayUnion(currentRequest.getSender()));
                }
                accept.setEnabled(false);
                deny.setEnabled(false);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        CardView rootView;

        public ViewHolder(CardView rootView) {
            super(rootView);
            this.rootView = rootView;
        }
    }
//    private void setEventHandlerForHolder(ViewHolder holder, Course currentCourse) {
//        holder.rootView.setOnClickListener(event -> {
//            Intent intent = new Intent(holder.rootView.getContext(), playVideo.class);
//            intent.putExtra("vn", currentCourse.getCourseId());
//            holder.rootView.getContext().startActivity(intent);
//        });
//    }
}
