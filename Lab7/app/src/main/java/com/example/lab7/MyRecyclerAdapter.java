package com.example.lab7;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {

    SimpleDateFormat localDateFormat= new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference usersRef = database.getReference("Users");
    private List<User> usersList;
    private RecyclerView r;

    public MyRecyclerAdapter(RecyclerView recyclerView) {
        usersList=new ArrayList<>();
        r = recyclerView;

        usersRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                User userModel = new
                        User(dataSnapshot.child("displayname").getValue().toString(),
                        dataSnapshot.child("email").getValue().toString(),
                        dataSnapshot.child("phone").getValue().toString(),
                        localDateFormat.format(new
                                Date(Long.parseLong(dataSnapshot.child("timestamp").getValue().toString()))));
                usersList.add(userModel);
                MyRecyclerAdapter.this.notifyItemInserted(usersList.size()-1);
                r.scrollToPosition(usersList.size()-1);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent,false);
        final ViewHolder vh = new ViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final User u =usersList.get(position);
        holder.fname_v.setText("First Name: " +u.displayname);
        holder.email_v.setText("Email: " + u.email);
        holder.phone_v.setText("Phone Num: " + u.phone);
        holder.date_v.setText("Date Created: "+u.timestamp);

    }
    @Override
    public int getItemCount() {
        return usersList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView fname_v;
        public TextView email_v;
        public TextView phone_v;
        public TextView date_v;
        public ViewHolder(View v){
            super(v);
            fname_v = (TextView) v.findViewById(R.id.fname_view);
            email_v = (TextView) v.findViewById(R.id.email_view);
            phone_v = (TextView) v.findViewById(R.id.phone_view);
            date_v = (TextView) v.findViewById(R.id.date_view);
        }
    }

}
