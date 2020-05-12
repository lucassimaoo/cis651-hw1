package com.example.lab6;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class UpdateContact extends AppCompatActivity {


    private EditText name;
    private EditText lastname;
    private EditText phone;
    private Button button;
    private long id;
    private MyDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_contact);
        name = findViewById(R.id.contactName);
        lastname = findViewById(R.id.contactLastName);
        phone = findViewById(R.id.contactPhone);
        button = findViewById(R.id.addNewContact);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveContact();
            }
        });
        id = getIntent().getLongExtra("CONTACT_ID", 1);
        dbHelper = new MyDbHelper(this);
        Contact c = dbHelper.getContact(id);
        name.setText(c.getName());
        lastname.setText(c.getLastname());
        phone.setText(c.getPhone());

    }

    private void saveContact() {
        dbHelper.updateContact(id, this, new Contact(name.getText().toString(),
                lastname.getText().toString(), phone.getText().toString()));
        finish();
    }

}
