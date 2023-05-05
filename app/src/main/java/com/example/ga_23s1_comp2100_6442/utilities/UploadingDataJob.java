package com.example.ga_23s1_comp2100_6442.utilities;

import androidx.annotation.NonNull;

import com.example.ga_23s1_comp2100_6442.model.Course;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UploadingDataJob {
    public static void main(String[] args) {

    }
//        try {
//        InputStream inputStreamCrawl = getAssets().open("crawl.csv");
//        InputStream inputStreamDescription = getAssets().open("description.csv");
//        InputStream inputStreamSearchTerm = getAssets().open("searchTerm.csv");
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        CollectionReference ref = db.collection(Constant.COURSE_COLLECTION);
//        List<Course> courses = readingDataFromCSV(inputStreamCrawl, inputStreamDescription, inputStreamSearchTerm);
//        for (Course course : courses) {
//            ref.add(course);
//        }
//    } catch (
//    IOException e) {
//        throw new RuntimeException(e);
//    }
    public static List<Course> readingDataFromCSV(InputStream inputStreamCrawl, InputStream inputStreamDescription, InputStream inputStreamSearchTerm) {
        List<Course> courses = new ArrayList<>();
        try {

            BufferedReader readCrawl = new BufferedReader(new InputStreamReader(inputStreamCrawl));
            BufferedReader readDescription = new BufferedReader(new InputStreamReader(inputStreamDescription));
            BufferedReader readSearchTerm = new BufferedReader(new InputStreamReader(inputStreamSearchTerm));
            String crawlLine;
            String filtersLine;
            String searchTermLine;
            while ((crawlLine = readCrawl.readLine()) != null
                    && (filtersLine = readDescription.readLine()) != null
                    && (searchTermLine = readSearchTerm.readLine()) != null ) {
                crawlLine = crawlLine.replace(",,,,,,,,,,,", "");
                String[] courseInfo = crawlLine.split(",");
                String author = courseInfo[0].replace("\"", "");
                String title = courseInfo[1].replace("\"", "");
                String description = String.join(",", Arrays.copyOfRange(courseInfo, 2, courseInfo.length));
                description = description.substring(1, description.length() - 1);
                filtersLine = filtersLine.replace("[", "");
                filtersLine = filtersLine.replace("]", "");
                String[] filters = filtersLine.split(", ");
                searchTermLine = searchTermLine.replace("[", "");
                searchTermLine = searchTermLine.replace("]","");
                String[] searchTerms = searchTermLine.split(",");
                List<String> link = new ArrayList<>();
                link.add("gs://comp2100-comp6442-assignment.appspot.com/10min.mp4");
//                courses.add(new Course(author, title, "gs://comp2100-comp6442-assignment.appspot.com/3683.webp"
//                        , link
//                        , description, Arrays.stream(filters).collect(Collectors.toList())
//                        , Arrays.stream(searchTerms).collect(Collectors.toList())));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return courses;
    }


}
