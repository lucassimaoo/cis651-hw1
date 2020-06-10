package com.example.shiftit;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NewShiftActivity extends BasicActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private static SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm");
    private Spinner hospital;
    private User user;
    private Calendar date;
    private EditText hours;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();

        repository = Repository.getInstance();

        user = repository.getUser(currentUser.getUid());

        hospital = findViewById(R.id.hospital);
        hospital.setPrompt("Select a hospital");
        hospital.getPopupContext().setTheme(R.style.PopUp);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, 0, user.getHospitals());

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hospital.setAdapter(adapter);

        hours = findViewById(R.id.hours);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_new_shift;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        date = Calendar.getInstance();
        date.set(Calendar.YEAR, year);
        date.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        date.set(Calendar.MONTH, month);

        int hour = date.get(Calendar.HOUR_OF_DAY);

        new TimePickerDialog(this, R.style.TimePicker, this, hour, 0,
                DateFormat.is24HourFormat(this)).show();
    }

    public void picker(View view) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        
        new DatePickerDialog(this, R.style.DatePicker, this, year, month, day).show();
    }

    public void create(View view) {

        if (date == null || hours.getText().toString().trim().isEmpty()
                || hospital.getSelectedItem().toString().isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Shift shift = new Shift(currentUser.getUid(), date.getTime(),
                Integer.valueOf(hours.getText().toString()), hospital.getSelectedItem().toString(), user.getProfession());
        repository.save(shift);
        finish();
    }

    public void cancel(View view) {
        finish();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        date.set(Calendar.HOUR_OF_DAY, hourOfDay);
        date.set(Calendar.MINUTE, 0);
        TextView dateView = findViewById(R.id.date);
        dateView.setText(format.format(date.getTime()));
    }
}
