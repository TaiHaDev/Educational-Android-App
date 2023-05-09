package com.example.ga_23s1_comp2100_6442;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ga_23s1_comp2100_6442.model.Post;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder>{



    List<Post> data ;

    public void setData(List<Post> data) {
        this.data = data;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView view = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.course_view, parent, false);
        return  new PostAdapter.ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull PostAdapter.ViewHolder holder, int position) {
        Post currentPost = data.get(position);
        TextView courseNameTextView = holder.rootView.findViewById(R.id.course_name);
        courseNameTextView.setText(currentPost.getTitle());
        TextView courseAuthorTextView = holder.rootView.findViewById(R.id.course_author);
        courseAuthorTextView.setText(currentPost.getDescription());
        ImageView courseThumbnailImageView = holder.rootView.findViewById(R.id.course_image);
        setEventHandlerForHolder(holder, currentPost);
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
    private void setEventHandlerForHolder(ViewHolder holder, Post currentPost) {
        holder.rootView.setOnClickListener(event -> {
            Intent intent = new Intent(holder.rootView.getContext(), playVideo.class);
            intent.putExtra("vn", currentPost.getPostId());
            holder.rootView.getContext().startActivity(intent);
        });
    }

}

