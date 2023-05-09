package com.example.ga_23s1_comp2100_6442.model;

import com.google.firebase.firestore.FirebaseFirestore;

public class StudentFactory extends UserFactory {
    @Override
    protected User createUser(String userName, String name, String institution, String userId) {
        return new Student(userName, name, institution, userId);
    }

    @Override
    protected void registerUser(User user, FirebaseFirestore db) {
        db.collection("students").document(user.getId()).set(user);
    }
}
