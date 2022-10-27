package com.example.w7_q34;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class AddActivity extends AppCompatActivity {

    Button add;
    CalendarView calendarView;
    EditText text, time;
    String date;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            date = LocalDate.now().toString();
        } else {
            @SuppressLint("SimpleDateFormat") DateFormat dateFormat
                    = new SimpleDateFormat("yyyy-MM-dd");
            Date dateObj = new Date();
            date = dateFormat.format(dateObj);
        }
        text = findViewById(R.id.inputThing);
        time = findViewById(R.id.inputTime);
        add = findViewById(R.id.add);
        calendarView = findViewById(R.id.calendar);
        calendarView.setOnDateChangeListener((calendarView, i, i1, i2) ->
                date = i + "-" + (i1 + 1) + "-" + i2);
        add.setOnClickListener(view -> {
            if ((text.getText().toString().trim()).equals("")) {
                Toast.makeText(this, "Please type what you need to do!",
                        Toast.LENGTH_SHORT).show();
            } else if (!time.getText().toString().trim().
                    matches("(?:[0-1][0-9]|2[0-4]):[0-5]\\d")) {
                Toast.makeText(this, "Please type correct time in 24-hour format",
                        Toast.LENGTH_SHORT).show();
            } else {
                DataBaseHelper dataBaseHelper = new DataBaseHelper(AddActivity.this);
                if (time.getText().toString().equals("")) {
                    time.setText("00:00");
                }
                dataBaseHelper.addNewTODO(text.getText().toString().trim(), date.trim()
                        + " " + time.getText().toString().trim());
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}