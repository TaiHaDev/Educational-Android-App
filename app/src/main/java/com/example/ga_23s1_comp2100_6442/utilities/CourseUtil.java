package com.example.ga_23s1_comp2100_6442.utilities;

import com.example.ga_23s1_comp2100_6442.model.Course;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class CourseUtil {
    public static void SetCoursesFromDocumentSnapshots(QuerySnapshot queryDocumentSnapshots,  List<Course> fireBaseData) {
        for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
            Course course = documentSnapshot.toObject(Course.class);
            course.setCourseId(documentSnapshot.getId());
            System.out.println(documentSnapshot.getId());
            fireBaseData.add(course);
        }
    }

    public static void setSearch(QuerySnapshot queryDocumentSnapshots,  List<Course> fireBaseData, List<String> terms){
        for(DocumentSnapshot documentSnapshot : queryDocumentSnapshots){
            List<String> st = (List<String>) documentSnapshot.get("searchTerm");
            boolean flag = true;
            for(String t : terms){
                flag = false;
                String t1 = t.trim();
                for(String s : st){
                    if(flag) break;
                    String s1 = s.trim();
                    if(s1.compareTo(t1) >= 0 && s1.compareTo(t1 + "\uf8ff") < 0){
                        flag = true;
                    }
                }
                if(!flag) break;
            }
            if(!flag) continue;
            Course course = documentSnapshot.toObject(Course.class);
            course.setCourseId(documentSnapshot.getId());
            fireBaseData.add(course);
        }
    }
}
