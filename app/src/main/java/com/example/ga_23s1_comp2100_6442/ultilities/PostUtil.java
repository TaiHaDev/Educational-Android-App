package com.example.ga_23s1_comp2100_6442.ultilities;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

import android.annotation.SuppressLint;

import com.example.ga_23s1_comp2100_6442.MyDataActivity;
import com.example.ga_23s1_comp2100_6442.model.Course;
import com.example.ga_23s1_comp2100_6442.model.Post;
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
                if (Send_data.getUser().getIsLecturer() || Objects.requireNonNull(post).isVisibility() ) {
                    assert post != null;
                    post.setPostId(documentSnapshot.getId());
                    fireBaseData.add(post);
                }
                if((!Send_data.getUser().getIsLecturer())&&post.isAnonymous()){
                    post.setUserName("Anonymous");
                }
            }
        }
    }
}
