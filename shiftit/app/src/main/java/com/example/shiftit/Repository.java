package com.example.shiftit;

import android.util.Log;
import android.widget.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Repository {

    private static Repository INSTANCE;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference usersRef = database.getReference("Users");
    private DatabaseReference shiftsRef = database.getReference("Shifts");
    private DatabaseReference historyRef = database.getReference("History");
    private ObjectMapper mapper = new ObjectMapper();
    private Map<String, User> users = new HashMap<>();
    private Map<String, Shift> shifts = new HashMap<>();
    private Map<String, Shift> history = new HashMap<>();
    private List<ShiftsAdapter> adapters = new ArrayList<>();

    private Repository() {

        usersRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d("usersRef", "onChildAdded");
                User user = dataSnapshot.getValue(User.class);
                users.put(user.getUid(), user);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                User user = dataSnapshot.getValue(User.class);
                users.put(user.getUid(), user);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                users.remove(user.getUid());
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        shiftsRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d("shiftsRef", "onChildAdded");
                Shift shift = dataSnapshot.getValue(Shift.class);
                shifts.put(shift.getId(), shift);
                fireAdapterChange();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Shift shift = dataSnapshot.getValue(Shift.class);
                shifts.put(shift.getId(), shift);
                fireAdapterChange();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Shift shift = dataSnapshot.getValue(Shift.class);
                shifts.remove(shift.getId());
                fireAdapterChange();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        historyRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d("historyRef", "onChildAdded");
                Shift shift = dataSnapshot.getValue(Shift.class);
                history.put(shift.getId(), shift);
                fireAdapterChange();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Shift shift = dataSnapshot.getValue(Shift.class);
                history.put(shift.getId(), shift);
                fireAdapterChange();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Shift shift = dataSnapshot.getValue(Shift.class);
                history.remove(shift.getId());
                fireAdapterChange();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
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
        users.put(user.getUid(), user);
    }

    public void save(Shift shift) {
        if (shift.getId() == null) {
            String key = shiftsRef.push().getKey();
            shift.setId(key);
        }
        shiftsRef.child(shift.getId()).setValue(shift);
        shifts.put(shift.getId(), shift);
    }

    public void markAsDone(Shift shift) {
        historyRef.child(shift.getId()).setValue(shift);
        history.put(shift.getId(), shift);
        shiftsRef.child(shift.getId()).removeValue();
        shifts.remove(shift.getId());
    }

    public User getUser(String uid) {
        return users.get(uid);
    }

    public List<Shift> getOpenShiftsByProfession(String profession) {
        //TODO only list shift from other users
        List<Shift> r = new ArrayList<>();
        Log.d("getOpenShifts", shifts.size() + " " + profession);
        for (Shift shift : shifts.values()) {
            Log.d("shift", shift.getProfession());
            if (shift.getProfession().equals(profession) && shift.getTakerUid() == null) {
                r.add(shift);
            }
        }
        return r;
    }

    public List<Shift> getMyShifts(String uid) {
        List<Shift> r = new ArrayList<>();
        for (Shift shift : shifts.values()) {
            if (shift.getUid().equals(uid)) {
                r.add(shift);
            }
        }
        return r;
    }

    public List<Shift> getShiftsAssigned(String uid) {
        List<Shift> r = new ArrayList<>();
        for (Shift shift : shifts.values()) {
            if (shift.getTakerUid() != null && shift.getTakerUid().equals(uid)) {
                r.add(shift);
            }
        }
        return r;
    }

    public void addAdapter(ShiftsAdapter adapter) {
        this.adapters.add(adapter);
    }

    public void removeAdapter(ShiftsAdapter adapter) {
        this.adapters.remove(adapter);
    }

    public void fireAdapterChange() {
        for (ShiftsAdapter adapter: adapters) {
            adapter.updateShiftList();
        }
    }

    public void remove(Shift shift) {
        shiftsRef.child(shift.getId()).removeValue();
        shifts.remove(shift);
    }
}
