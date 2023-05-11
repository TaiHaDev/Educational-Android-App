package com.example.ga_23s1_comp2100_6442.utilities;

import android.net.Uri;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.RequestManager;
import com.example.ga_23s1_comp2100_6442.adapter.CourseAdapter;
import com.example.ga_23s1_comp2100_6442.model.Course;
import com.example.ga_23s1_comp2100_6442.model.Student;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class FirebaseUtil {
    public static void downloadAndSetImageFromStorage(RequestManager requestManager, ImageView imageView, String url) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference gsReference = storage.getReferenceFromUrl(url);

        gsReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                requestManager.load(uri).into(imageView);
            }
        });
    }
    public static void simpleQueryFireStore(String term, CourseAdapter adapter) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Constant.COURSE_COLLECTION_TEST)
                .whereArrayContains("searchTerm", term)
                .limit(30).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<Course> courses = new ArrayList<>();
                        CourseUtil.SetCoursesFromDocumentSnapshots(queryDocumentSnapshots,courses);
                        System.out.println(":" + courses);
                        adapter.setData(courses);
                    }

                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    public static List<String> tokenize(String input) {
        List<String> tokens = new ArrayList<>();
        String[] rawTokens = input.split("\\|\\|");
        for (String rawToken : rawTokens) {
            String[] keyValue = rawToken.split(":");
            String key = keyValue[0].trim();
            String value = keyValue[1].trim();
            tokens.add(key + ":" + value);
        }
        return tokens;
    }

    public static List<String> parse(List<String> tokens) {
        List<String> parsedValues = new ArrayList<>();
        for (String token : tokens) {
            String[] keyValue = token.split(":");
            String key = keyValue[0].trim();
            String value = keyValue[1].trim();
            if (key.equals("#title")) {
                parsedValues.add(value.toLowerCase());
            }
            if (key.equals("#author")) {
                parsedValues.add(value.toLowerCase());
            }
        }
        return parsedValues;
    }

    public static void QueryFireStore(String term, CourseAdapter adapter, String bigFilter, String descriptFilter){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Query dbc = db.collection(Constant.COURSE_COLLECTION_TEST);
        if (descriptFilter != null){
            dbc = dbc.whereEqualTo("descriptFilter", descriptFilter);
        }
        if(bigFilter != null){
            dbc = dbc.whereEqualTo("bigFilter", bigFilter);
        }
        List<String> tokens = tokenize(term);
        System.out.println(tokens);
        List<String> terms = parse(tokens);
        System.out.println(terms);
        dbc.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<Course> courses = new ArrayList<>();
                CourseUtil.setSearch(queryDocumentSnapshots,courses, terms);
                System.out.println(":" + courses);
                adapter.setData(courses);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("error");
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        });
    }

    public static void getMyCourses(CourseAdapter adapter) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        assert currentUser != null;
        DocumentReference docRef = db.collection("students").document(currentUser.getUid());
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Student student = documentSnapshot.toObject(Student.class);
                assert student != null;
                List<String> coursesEnrolled = student.getCoursesEnrolled();
                for (String courseId : coursesEnrolled){
                    getCourseFromId(courseId,adapter);
                }
            }
        });
    }

    public static void getCourseFromId(String courseId,CourseAdapter adapter) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Constant.COURSE_COLLECTION).document(courseId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Course course= documentSnapshot.toObject(Course.class);
                course.setCourseId(documentSnapshot.getId());
                adapter.updateData(course);
            }
        });
    }



}
