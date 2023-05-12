package com.example.ga_23s1_comp2100_6442.ultilities;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

import android.annotation.SuppressLint;

import com.example.ga_23s1_comp2100_6442.MyDataActivity;
import com.example.ga_23s1_comp2100_6442.model.Course;
import com.example.ga_23s1_comp2100_6442.model.Post;
import com.example.ga_23s1_comp2100_6442.model.User;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PostUtil {
    static MyDataActivity Send_data;

    @SuppressLint("RestrictedApi")
    public static void SetCoursesFromDocumentSnapshots(QuerySnapshot queryDocumentSnapshots, List<Post> fireBaseData) {
        Send_data = (MyDataActivity) getApplicationContext();
        for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
            if (documentSnapshot != null) {
                Post post = documentSnapshot.toObject(Post.class);
                User user = Send_data.getUser();
                if (Objects.requireNonNull(post).isVisibility()|| Send_data.getUser().getIsLecturer()  ) {
                    post.setPostId(documentSnapshot.getId());
                    fireBaseData.add(post);
                }
                if((post.isAnonymous()&&!Send_data.getUser().getIsLecturer())){
                    post.setUserName("Anonymous");
                }
            }
        }
    }
}
