package com.example.ga_23s1_comp2100_6442.model;

import com.google.firebase.firestore.FirebaseFirestore;

public abstract class UserFactory {
    public final User signUp(String userName, String name, String institution, String userId, FirebaseFirestore db) {
        User user = createUser(userName, name, institution, userId);
        registerUser(user,db);
        return user;
    }

    protected abstract User createUser(String userName, String name, String institution, String userId);

    protected abstract void registerUser(User user,FirebaseFirestore db);
}
