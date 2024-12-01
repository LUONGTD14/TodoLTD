package com.example.todoltd.managerObject_Singleton;

import com.google.firebase.database.FirebaseDatabase;

public class FireBaseDbManager {
    private static FirebaseDatabase mFirebaseDb;

    // Singleton pattern to ensure only one instance is created
    private static FireBaseDbManager instance;

    private FireBaseDbManager() {
        mFirebaseDb = FirebaseDatabase.getInstance();
    }

    public static synchronized FireBaseDbManager getInstance() {
        if (instance == null) {
            instance = new FireBaseDbManager();
        }
        return instance;
    }

    public FirebaseDatabase getFirebaseAuth() {
        return mFirebaseDb;
    }

}