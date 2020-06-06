package com.example.shiftit;

import android.app.PendingIntent;
import android.content.Intent;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    private Map<String, User> users = new HashMap<>();
    private Map<String, Shift> shifts = new HashMap<>();
    private Map<String, Shift> history = new HashMap<>();
    private List<Adapter> adapters = new ArrayList<>();
    private Integer notificationId = 1;

    private Repository() {

        usersRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d("usersRef", "onChildAdded");
                User user = dataSnapshot.getValue(User.class);
                users.put(user.getUid(), user);
                fireAdapterChange();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                User user = dataSnapshot.getValue(User.class);
                users.put(user.getUid(), user);
                fireAdapterChange();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                users.remove(user.getUid());
                fireAdapterChange();
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

                User user = getCurrentUser();
                if (user != null
                    && !user.getUid().equals(shift.getUid())
                    && user.getHospitals().contains(shift.getHospital())
                    && shift.getProfession().equals(user.getProfession())) {

                    Intent intent = new Intent(AppInitializer.getContext(), OpenShiftsActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    PendingIntent pendingIntent = PendingIntent.getActivity(AppInitializer.getContext(), 0, intent, 0);

                    NotificationCompat.Builder builder = new NotificationCompat.Builder(AppInitializer.getContext(), AppInitializer.CHANNEL_ID)
                            .setSmallIcon(R.drawable.notification_icon)
                            .setContentTitle("New shift available")
                            .setContentText("At " + shift.getHospital() + " for " + shift.getHours() + " hours, starting " + shift.getDate())
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                            .setContentIntent(pendingIntent)
                            .setAutoCancel(true);

                    builder.build();

                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(AppInitializer.getContext());
                    notificationManager.notify(notificationId++, builder.build());
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Shift shift = dataSnapshot.getValue(Shift.class);
                shifts.put(shift.getId(), shift);
                fireAdapterChange();

                User user = getCurrentUser();
                if (user != null
                        && user.getUid().equals(shift.getUid())
                        && shift.getTakerUid() != null) {

                    Intent intent = new Intent(AppInitializer.getContext(), MyShiftsActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    PendingIntent pendingIntent = PendingIntent.getActivity(AppInitializer.getContext(), 0, intent, 0);

                    NotificationCompat.Builder builder = new NotificationCompat.Builder(AppInitializer.getContext(), AppInitializer.CHANNEL_ID)
                            .setSmallIcon(R.drawable.notification_icon)
                            .setContentTitle("Your shift got taken")
                            .setContentText("At " + shift.getHospital() + " by " + getUser(shift.getTakerUid()).getName() + " starting " + shift.getDate())
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                            .setContentIntent(pendingIntent)
                            .setAutoCancel(true);

                    builder.build();

                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(AppInitializer.getContext());
                    notificationManager.notify(notificationId++, builder.build());
                }

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

    public void markAsSettled(List<Shift> shifts) {
        for (Shift shift: shifts) {
            historyRef.child(shift.getId()).removeValue();
            history.remove(shift.getId());
        }
    }

    public User getUser(String uid) {
        return users.get(uid);
    }

    public List<Shift> getOpenShifts(User user) {
        List<Shift> r = new ArrayList<>();
        for (Shift shift : shifts.values()) {
            if (shift.getProfession().equals(user.getProfession()) && shift.getTakerUid() == null
                    && user.getHospitals().contains(shift.getHospital())
                    && !shift.getUid().equals(user.getUid())) {
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

    public void addAdapter(Adapter adapter) {
        this.adapters.add(adapter);
    }

    public void removeAdapter(ShiftsAdapter adapter) {
        this.adapters.remove(adapter);
    }

    public void fireAdapterChange() {
        for (Adapter adapter: adapters) {
            adapter.updateList();
        }
    }

    public void remove(Shift shift) {
        shiftsRef.child(shift.getId()).removeValue();
        shifts.remove(shift);
    }

    private User getCurrentUser() {
        FirebaseUser authUser = FirebaseAuth.getInstance().getCurrentUser();
        if (authUser != null && users.containsKey(authUser.getUid())) {
            return users.get(authUser.getUid());
        }

        return null;
    }

    public List<History> getHistory() {

        User currentUser = getCurrentUser();

        List<History> list = new ArrayList<>();

        Map<String, List<Shift>> map = new HashMap<>();

        for (Shift s : history.values()) {
            if (s.getUid().equals(currentUser.getUid())) {

                if (!map.containsKey(s.getTakerUid())) {
                    map.put(s.getTakerUid(), new ArrayList<Shift>());
                }

                map.get(s.getTakerUid()).add(s);
            }
        }

        for (String taker : map.keySet()) {

            History h = new History();
            h.setUid(taker);
            int hours = 0;

            for (Shift s: map.get(taker)) {
                hours += s.getHours();
            }

            h.setShifts(map.get(taker));
            h.setHours(hours);
            list.add(h);
        }

        return list;
    }
}
