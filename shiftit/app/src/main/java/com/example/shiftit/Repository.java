package com.example.shiftit;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Repo;

import java.util.HashMap;
import java.util.Map;

public class Repository {

    private static Repository INSTANCE;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference usersRef = database.getReference("Users");
    private ObjectMapper mapper = new ObjectMapper();
    private Map<String, User> users = new HashMap<>();

    private Repository() {

        usersRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                User user = dataSnapshot.getValue(User.class);
                users.put(user.getUid(), user);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d("repository", "changed");
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Log.d("repository", "removed");
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d("repository", "moded");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("repository", "cancelled");
            }
        });
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

    public User getUser(String uid) {
        return users.get(uid);
//        usersRef.child(uid).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                User snap = dataSnapshot.getValue(User.class);
//                user.setEmail(snap.getEmail());
//                user.setHospitals(snap.getHospitals());
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//            }
//        });


//        Log.d("user", usersRef.child(uid).child("email").toString());
//        user.setEmail(usersRef.child(uid).child("email").toString());
//       //mapper.convertValue(usersRef.child(uid).toString(), User.class);
//
//        return user;
    }
}
