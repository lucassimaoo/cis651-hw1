package com.example.lab3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Summary extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        PersonInfo pi = (PersonInfo) getIntent().getParcelableExtra("pi");
        TextView nl = findViewById(R.id.name_lastname);
        TextView cz=findViewById(R.id.city_zip);
        TextView lang=findViewById(R.id.lang);
        nl.setText("Name : "+pi.getName()+" LastName : "+pi.getLastname());
        cz.setText("City : "+pi.getCity()+" Zip : "+pi.getZip());
        lang.setText("Language : "+pi.getLanguage());
    }
}
