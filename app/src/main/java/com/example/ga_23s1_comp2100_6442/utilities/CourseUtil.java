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
}
