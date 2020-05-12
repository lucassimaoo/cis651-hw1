package com.example.lab6;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MyDbHelper extends SQLiteOpenHelper {

    private static final String database_name = "contacts.db";
    private static final int database_version = 1;

    public MyDbHelper(@Nullable Context context) {
        super(context, database_name, null, database_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" CREATE TABLE CONTACTS ( _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " name TEXT NOT NULL, lastname TEXT NOT NULL, phone TEXT NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS CONTACTS");
        this.onCreate(db);
    }

    public void saveNewContact(Contact contact, Context context) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", contact.getName());
        values.put("lastname", contact.getLastname());
        values.put("phone", contact.getPhone());
        db.insert("CONTACTS", null, values);
        db.close();
        Toast.makeText(context, "Added", Toast.LENGTH_SHORT).show();
    }

    public List<Contact> contactList() {

        List<Contact> contacts = new ArrayList<>();
        Cursor c = this.getWritableDatabase().rawQuery("select * from CONTACTS", null);
        if (c.moveToFirst()) {
            do {
                contacts.add(new Contact(c.getLong(0), c.getString(1),
                        c.getString(2), c.getString(3)));
            } while(c.moveToNext());
        }
        c.close();
        return contacts;
    }

    public Contact getContact(long id) {
        Cursor c = this.getWritableDatabase().rawQuery("select * from CONTACTS where _id = " + id, null);
        Contact contact = null;
        if (c.getCount() > 0) {
            c.moveToFirst();
            contact = new Contact(c.getLong(0), c.getString(1),
                        c.getString(2), c.getString(3));

        }
        c.close();
        return contact;
    }

    public void deleteContact(long id, Context context) {
        this.getWritableDatabase().execSQL("delete from CONTACTS where _id = " + id);
        Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
    }

    public void updateContact(long id, Context context, Contact c) {
        this.getWritableDatabase().execSQL("update CONTACTS set name = '" + c.getName() + "'," +
                " lastname = '" + c.getLastname() + "', phone = '" + c.getPhone() + "' where _id = " + id);
        Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show();
    }


}
