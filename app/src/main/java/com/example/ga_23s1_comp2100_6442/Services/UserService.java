package com.example.ga_23s1_comp2100_6442.Services;

import static android.content.ContentValues.TAG;

import android.util.Log;
import android.widget.Adapter;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.example.ga_23s1_comp2100_6442.model.Request;
import com.example.ga_23s1_comp2100_6442.ultilities.Constant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserService {
    FirebaseFirestore db;

    public UserService() {
        db = FirebaseFirestore.getInstance();
    }

    /**
     * This method retrieves a document from a Firestore collection using the user ID and gets a
     * list of user IDs from a collectionName specified by the collectionName name. It then retrieves the name and
     * username of each user in the list of user IDs and adds them to the list of user names.
     * Finally, it notifies the ArrayAdapter that the data has changed.
     *
     * @param id current user id
     * @param adapter ArrayAdapter for current listView
     * @param users the list of users you want to update and display
     * @param collectionName the collection you want to search(followers, following)
     */
    public void fetchAndDisplayUsers(String id, ArrayAdapter adapter, List<String> users, String collectionName) {
        //get all the uid from current user's followers
        db.collection(Constant.STUDENTS_COLLECTION).document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        //store all the uid in a temp list
                        List<String> temp = (List<String>) document.get(collectionName);
                        //clear current list before updating to avoid duplicates
                        users.clear();
                        //check null
                        if (temp==null){
                            temp = new ArrayList<>();
                        }
                        //get username and names form the uid in temp list
                        for (int i = 0; i < Objects.requireNonNull(temp).size(); i++) {
                            getNameById(temp.get(i), adapter, users);
                        }
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    }
                }
            }
        });
    }

    /**
     * This method retrieves a document from a Firestore collection using the user ID and gets the name and
     * username of the user. It then adds the name and username to the list of user names and
     * notifies the ArrayAdapter that the data has changed.
     *
     * @param id the id of the user you want to display
     * @param adapter ArrayAdapter for current listView
     * @param users the list of users you want to update and display
     */
    public void getNameById(String id, ArrayAdapter adapter, List<String> users) {
        //find the student object from on id
        db.collection(Constant.STUDENTS_COLLECTION).document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        //get name field
                        String name = (String) document.get("name");
                        //get username field
                        String userName = (String) document.get("userName");
                        //add name and username in followersNames list for exhibition
                        users.add(name + ":   " + userName);
                        //update data in adapter
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    /**
     * This method queries the Firestore collection for documents that have a username field equal to the
     * receiver username. It then creates a Request object with the sender ID, receiver ID, request
     * type, and sender and receiver names, and adds it to a Firestore collection. This is used to
     * follow the user with the specified username.
     *
     * @param senderId id of current user who wants to send request
     * @param senderName name of current user who wants to send request
     * @param receiverUserName the email address of the user you want to follow
     */
    public void followUserByEmail(String senderId, String senderName, String receiverUserName) {
        //find receiver by email(userName)
        db.collection(Constant.STUDENTS_COLLECTION).whereEqualTo("userName", receiverUserName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String receiverId = document.getId();
                                String receiverName = (String) document.get("name");
                                Request r = new Request(senderId, receiverId, Request.RequestType.Follow, "", senderName, receiverName);
                                db.collection(Constant.REQUESTS_COLLECTION).add(r);
                            }
                        }
                    }
                });
    }
}
