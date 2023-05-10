package com.example.ga_23s1_comp2100_6442.adapter;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ga_23s1_comp2100_6442.MyDataActivity;
import com.example.ga_23s1_comp2100_6442.PostDetails;
import com.example.ga_23s1_comp2100_6442.R;
import com.example.ga_23s1_comp2100_6442.model.Post;
import com.example.ga_23s1_comp2100_6442.playVideo;
import com.google.firebase.Timestamp;

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
        CardView view = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.row_post_item, parent, false);
        return  new PostAdapter.ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull PostAdapter.ViewHolder holder, int position) {
        Post currentPost = data.get(position);
        TextView postTitleTextView = holder.rootView.findViewById(R.id.row_post_title);
        postTitleTextView.setText(currentPost.getTitle());
        TextView postAuthorTextView = holder.rootView.findViewById(R.id.row_post_author);
        postAuthorTextView.setText(currentPost.getUserName());
        TextView postDescriptionTextView = holder.rootView.findViewById(R.id.row_post_description);
        postDescriptionTextView.setText(currentPost.getDescription());
        TextView postTimeStampTextView = holder.rootView.findViewById(R.id.row_post_timestamp);
        postTimeStampTextView.setText((currentPost.getTimeStamp()).toString());
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

    /**
     *
     * @param holder
     * @param currentPost
     */
    private void setEventHandlerForHolder(ViewHolder holder, Post currentPost) {
        holder.rootView.setOnClickListener(event -> {
            Intent intent = new Intent(holder.rootView.getContext(), PostDetails.class);
            intent.putExtra("pid", currentPost.getPostId());
            holder.rootView.getContext().startActivity(intent);
        });
    }

}

