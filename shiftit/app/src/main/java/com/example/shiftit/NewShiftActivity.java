package com.example.shiftit;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class NewShiftActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private Repository repository;
    private Spinner hospital;
    private FirebaseAuth auth;
    private FirebaseUser currentUser;
    private User user;
    private Calendar date;
    private SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm");
    private EditText hours;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_shift);

        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();

        repository = Repository.getInstance();

        user = repository.getUser(currentUser.getUid());

        hospital = findViewById(R.id.hospital);
        hospital.setPrompt("Select a hospital");

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, 0, user.getHospitals());

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hospital.setAdapter(adapter);

        hours = findViewById(R.id.hours);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        date = Calendar.getInstance();
        date.set(Calendar.YEAR, year);
        date.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        date.set(Calendar.MONTH, month);

        int hour = date.get(Calendar.HOUR_OF_DAY);
        int minute = date.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        new TimePickerDialog(this, this, hour, minute,
                DateFormat.is24HourFormat(this)).show();
    }

    public void picker(View view) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        new DatePickerDialog(this, this, year, month, day).show();
    }

    public void create(View view) {
        //TODO add validation
        Shift shift = new Shift(currentUser.getUid(), date.getTime(),
                Integer.valueOf(hours.getText().toString()), hospital.getSelectedItem().toString());
        repository.save(shift);
    }

    public void cancel(View view) {
        startActivity(new Intent(this, HomeActivity.class));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        date.set(Calendar.HOUR_OF_DAY, hourOfDay);
        date.set(Calendar.MINUTE, minute);
        TextView dateView = findViewById(R.id.date);
        dateView.setText(format.format(date.getTime()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}