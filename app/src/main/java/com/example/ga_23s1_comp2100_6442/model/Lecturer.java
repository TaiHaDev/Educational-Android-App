package com.example.ga_23s1_comp2100_6442.model;

import android.view.inputmethod.CorrectionInfo;

import com.example.ga_23s1_comp2100_6442.ultilities.Constant;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.units.qual.C;

import java.util.ConcurrentModificationException;
import java.util.Map;
import java.util.Set;

import javax.security.auth.SubjectDomainCombiner;

public class Lecturer extends User {
    private Map<String, Course> courses;

    public Lecturer() {
    }

    public Lecturer(String userName, String name, String institution) {
        super(userName, name, institution);
    }


    public void addStudentToCourse(Student student, String courseId) {
        student.getCoursesEnrolled().remove(courseId);
        courses.remove(courseId);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference courseRef = db.collection(Constant.COURSE_COLLECTION_TEST).document(courseId);
        courseRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Course course = documentSnapshot.toObject(Course.class);
                assert course != null;
                courseRef.update("studentEnrolled",course.getStudentsEnrolled().add(student));
            }
        });
    }

    public void deleteStudentFromCourse(Student student, String courseId) {
        student.getCoursesEnrolled().remove(courseId);
        courses.remove(courseId);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference courseRef = db.collection(Constant.COURSE_COLLECTION_TEST).document(courseId);
        courseRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Course course = documentSnapshot.toObject(Course.class);
                assert course != null;
                courseRef.update("studentEnrolled",course.getStudentsEnrolled().remove(student));
            }
        });
    }

    public Map<String, Course> getCourses() {
        return courses;
    }

    public void setCourses(Map<String, Course> courses) {
        this.courses = courses;
    }
}
