package com.example.ga_23s1_comp2100_6442.utilities;

import android.net.Uri;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.RequestManager;
import com.example.ga_23s1_comp2100_6442.adapter.CourseAdapter;
import com.example.ga_23s1_comp2100_6442.model.Course;
import com.example.ga_23s1_comp2100_6442.model.Lecturer;
import com.example.ga_23s1_comp2100_6442.model.Student;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

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
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<Course> courses = new ArrayList<>();
                        CourseUtil.SetCoursesFromDocumentSnapshots(queryDocumentSnapshots, courses);
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

    public static void getMyCourses(CourseAdapter adapter) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        assert currentUser != null;
        DocumentReference docRefLec = db.collection("lecturers").document(currentUser.getUid());
        DocumentReference docRefStu = db.collection("students").document(currentUser.getUid());
        docRefLec.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                //if the current user is a lecturer
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Lecturer lecturer = document.toObject(Lecturer.class);
                        assert lecturer != null;
                        List<String> courses = lecturer.getCourses();
                        if (courses != null) {
                            for (String courseId : courses) {
                                getCourseFromId(courseId, adapter);
                            }
                        }
                    } else { //the current user is a student
                        docRefStu.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                Student student = documentSnapshot.toObject(Student.class);
                                assert student != null;
                                List<String> coursesEnrolled = student.getCoursesEnrolled();
                                assert coursesEnrolled != null;
                                for (String courseId : coursesEnrolled) {
                                    getCourseFromId(courseId, adapter);
                                }
                            }
                        });
                    }
                }
            }
        });
    }


    public static void getCourseFromId(String courseId, CourseAdapter adapter) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Constant.COURSE_COLLECTION).document(courseId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Course course = documentSnapshot.toObject(Course.class);
                if (course!=null){
                course.setCourseId(documentSnapshot.getId());
                adapter.updateCourse(course);}
            }
        });
    }


}
