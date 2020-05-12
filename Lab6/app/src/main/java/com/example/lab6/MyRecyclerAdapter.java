package com.example.lab6;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Map;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {

    private List<Contact> md;
    private Context context;
    private RecyclerView recyclerView;

    public MyRecyclerAdapter(List<Contact> list, Context context, RecyclerView recyclerView) {
        md = list;
        this.context = context;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_layout, parent, false);
        final ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.name_v.setText(md.get(position).getName());
        holder.lastname_v.setText(md.get(position).getLastname());
        holder.phone_v.setText(md.get(position).getPhone());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Choose option");
                builder.setMessage("Update or delete user?");
                builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        goToUpdateActivity(md.get(position).getId());
                    }
                });
                builder.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new MyDbHelper(context).deleteContact(md.get(position).getId(), context);
                        md.remove(position);
                        recyclerView.removeViewAt(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, md.size());
                        notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });
    }

    private void goToUpdateActivity(long id) {
        Intent intent = new Intent(context, UpdateContact.class);
        intent.putExtra("CONTACT_ID", id);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return md.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name_v;
        public TextView lastname_v;
        public TextView phone_v;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name_v = (TextView) itemView.findViewById(R.id.name_view);
            lastname_v = (TextView) itemView.findViewById(R.id.lastname_view);
            phone_v = (TextView) itemView.findViewById(R.id.phone_number);
        }
    }

    public void updateList(List<Contact> contacts) {
        this.md = contacts;
        notifyDataSetChanged();
    }


}
