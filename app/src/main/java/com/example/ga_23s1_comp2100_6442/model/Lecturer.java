package com.example.ga_23s1_comp2100_6442.model;

import android.view.inputmethod.CorrectionInfo;

import com.example.ga_23s1_comp2100_6442.ultilities.Constant;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.units.qual.C;

import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.security.auth.SubjectDomainCombiner;

public class Lecturer extends User {
    private List<String> courses;

    public Lecturer() {
    }

    public Lecturer(String userName, String name, String institution,String id) {
        super(userName, name, institution,id);
        isLecturer=true;
    }


//    public void addStudentToCourse(String studentId, String courseId) {
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        DocumentReference studentRef = db.collection(Constant.STUDENTS_COLLECTION).document(studentId);
//        DocumentReference courseRef = db.collection(Constant.COURSE_COLLECTION_TEST).document(courseId);
//        studentRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                studentRef.update("coursesEnrolled", FieldValue.arrayUnion(courseId));
//            }
//        });
//        courseRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                courseRef.update("studentEnrolled",FieldValue.arrayUnion(studentId));
//            }
//        });
//    }
//
//    public void deleteStudentFromCourse(String studentId, String courseId) {
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        DocumentReference studentRef = db.collection(Constant.STUDENTS_COLLECTION).document(studentId);
//        DocumentReference courseRef = db.collection(Constant.COURSE_COLLECTION_TEST).document(courseId);
//        studentRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                studentRef.update("coursesEnrolled", FieldValue.arrayRemove(courseId));
//            }
//        });
//        courseRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                courseRef.update("studentEnrolled",FieldValue.arrayRemove(studentId));
//            }
//        });
//    }

    public List<String> getCourses() {
        return courses;
    }

    public void setCourses(List<String> courses) {
        this.courses = courses;
    }
}
