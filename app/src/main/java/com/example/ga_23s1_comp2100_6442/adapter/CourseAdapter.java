package com.example.ga_23s1_comp2100_6442.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.ga_23s1_comp2100_6442.R;
import com.example.ga_23s1_comp2100_6442.model.Course;
import com.example.ga_23s1_comp2100_6442.playVideo;
import com.example.ga_23s1_comp2100_6442.utilities.FirebaseUtil;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {
    List<Course> data;
    SharedPreferences sharedPref;

    String searchingCourseName;

    public CourseAdapter(SharedPreferences sharedPref) {
        this.sharedPref = sharedPref;
    }

    public CourseAdapter() {
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<Course> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateCourse(Course course) {
        if (this.data == null) {
            data = new ArrayList<>();
        }
        data.add(course);
        notifyDataSetChanged();
    }
    @SuppressLint("NotifyDataSetChanged")
    public void updateCourses(List<Course> courses) {
        if (this.data == null) {
            data = new ArrayList<>();
        }
        System.out.println(data);
        courses.addAll(data);
        data=courses;
        System.out.println(data);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView view = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.course_view, parent, false);
        return new ViewHolder(view);
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
        setEventHandlerForHolder(holder, currentCourse);


        // mark the recently searched course
//        if (HomePage.historySearchTree.search(currentCourse)) {
//            courseNameTextView.setTextColor(Color.MAGENTA);
//        } else {
//            courseNameTextView.setTextColor(Color.BLACK);
//        }
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

    private void setEventHandlerForHolder(ViewHolder holder, Course currentCourse) {
        holder.rootView.setOnClickListener(event -> {
            Intent intent = new Intent(holder.rootView.getContext(), playVideo.class);
            intent.putExtra("vn", currentCourse.getCourseId());
            holder.rootView.getContext().startActivity(intent);
        });
    }
}
