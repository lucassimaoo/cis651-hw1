package com.example.shiftit;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.core.Repo;

public class Repository {

    private static Repository INSTANCE;
    private FirebaseDatabase database;
    private DatabaseReference usersRef;

    private Repository() {
        database = FirebaseDatabase.getInstance();
        usersRef = database.getReference("Users");
    }

    public static Repository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Repository();
        }
        return INSTANCE;
    }

    public void save(User user) {
        usersRef.child(user.getUid()).setValue(user);
    }
}
