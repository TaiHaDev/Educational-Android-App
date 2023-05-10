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
import java.util.Random;
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
        String[] authorIds = {"1E88a81YzcVswD43yqT8V2Dfqqm1", "TpStXGca55ZOpCI8SB2KO2DDtgu2", "fedy9hIONwZbVa4gLVMunvc3pNg2", "ovVIgC2wJBYFpR45VJ3QlOimtWH3", "sN8c0ILusHQ4XnHlcaaoUbBwo0u2", "sZVNxulpsIXwTEHxUhXe8Wj7gDc2", "zqsQrwLnfWOVNYxojrv81IPN6o53"};
        List<Course> courses = new ArrayList<>();
        Random random = new Random();
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
                // assign author id or each course
                String authorId;
                if (author.equals("Google")) {
                    authorId = authorIds[0];
                } else if (author.equals("IBM")) {
                    authorId = authorIds[1];
                } else if (author.equals("Google Cloud")) {
                    authorId = authorIds[3];
                } else if (author.contains("Stanford University")) {
                    authorId = authorIds[4];
                } else {
                    int randomIndex = random.nextInt(7);
                    authorId = authorIds[randomIndex];
                }
                // assign boolean value randomly to isPublic field
                int randomNumber = random.nextInt(10);
                boolean isPublic;
                isPublic = randomNumber <= 6;

                courses.add(new Course(title, author, authorId,"gs://comp2100-comp6442-assignment.appspot.com/10min.mp4"
                        , "gs://comp2100-comp6442-assignment.appspot.com/3683.webp"
                        , isPublic
                        , description, Arrays.stream(filters).collect(Collectors.toList())
                        , Arrays.stream(searchTerms).collect(Collectors.toList())));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return courses;
    }


}
