package com.example.lab6;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddNewContact extends AppCompatActivity {

    private EditText name;
    private EditText lastname;
    private EditText phone;
    private Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_contact);
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
    }

    private void saveContact() {
        new MyDbHelper(this).saveNewContact(new Contact(name.getText().toString(),
                lastname.getText().toString(), phone.getText().toString()), this);
        finish();
    }
}
