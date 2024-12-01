package com.example.todoltd.managerObject_Singleton;

import com.google.firebase.auth.FirebaseAuth;

public class FirebaseAuthManager {
    private static FirebaseAuth mAuth;

    // Singleton pattern to ensure only one instance is created
    private static FirebaseAuthManager instance;

    private FirebaseAuthManager() {
        mAuth = FirebaseAuth.getInstance();
    }

    public static synchronized FirebaseAuthManager getInstance() {
        if (instance == null) {
            instance = new FirebaseAuthManager();
        }
        return instance;
    }

    public FirebaseAuth getFirebaseAuth() {
        return mAuth;
    }

}
