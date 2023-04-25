package com.example.ga_23s1_comp2100_6442;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.ga_23s1_comp2100_6442.model.Course;
import com.example.ga_23s1_comp2100_6442.ultilities.FirebaseUtil;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {
    List<Course> data ;

    public void setData(List<Course> data) {
        this.data = data;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView view = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.course_view, parent, false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Course currentCourse = data.get(position);
        TextView courseNameTextView = holder.rootView.findViewById(R.id.course_name);
        courseNameTextView.setText(currentCourse.getTitle());
        TextView courseAuthorTextView = holder.rootView.findViewById(R.id.course_author);
        courseAuthorTextView.setText(currentCourse.getAuthor());
        ImageView courseThumbnailImageView = holder.rootView.findViewById(R.id.course_image);
        StorageReference gsReference = FirebaseStorage
                .getInstance()
                .getReferenceFromUrl(currentCourse.getThumbnail());
        FirebaseUtil.downloadAndSetImageFromStorage(Glide.with(holder.rootView.getContext()), courseThumbnailImageView, currentCourse.getThumbnail());
        setEventHandlerForHolder(holder, currentCourse.getLink().get(0));
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
    private void setEventHandlerForHolder(ViewHolder holder, String link) {
        holder.rootView.setOnClickListener(event -> {
            Intent intent = new Intent(holder.rootView.getContext(), playVideo.class);
            intent.putExtra("vn", link);
            holder.rootView.getContext().startActivity(intent);
        });
    }
}
