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

public class PostUtil {
    static MyDataActivity Send_data;

    @SuppressLint("RestrictedApi")
    public static void SetCoursesFromDocumentSnapshots(QuerySnapshot queryDocumentSnapshots, List<Post> fireBaseData) {
        Send_data = (MyDataActivity) getApplicationContext();
        for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
            if (Send_data.getUser().getIsLecturer() || (boolean) documentSnapshot.get("visibility")) {
                Post post = new Post();
                post.setPostId(documentSnapshot.getId());
                System.out.println(documentSnapshot.getId());
                post.setTitle((String) documentSnapshot.get("title"));
                post.setUserName((String) documentSnapshot.get("userName"));
                post.setDescription((String) documentSnapshot.get("description"));
                post.setTimeStamp(documentSnapshot.get("timeStamp"));
//                post.setStudentsEnrolled((List<String>) documentSnapshot.get("studentsEnrolled"));
//                post.setPublic((boolean) documentSnapshot.get("isPublic"));
//                post.setFilters((List<String>) documentSnapshot.get("filters"));
//                post.setSearchTerm((List<String>) documentSnapshot.get("searchTerm"));
                fireBaseData.add(post);
            }
        }
    }
}
