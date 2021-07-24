package com.example.eventor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;

public class CreateActivity extends AppCompatActivity  implements DatePickerDialog.OnDateSetListener {

    boolean startdate;
    boolean startime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        Button opendate = findViewById(R.id.datepicker);
        Button enddate = findViewById(R.id.datepicker2);
        Button starttime = findViewById(R.id.button);
        Button endtime = findViewById(R.id.endtime);

        starttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startime = true;
                showTimePicker(view);
            }
        });

        endtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startime = false;
                showTimePicker(view);
            }
        });

        opendate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startdate = true;
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        enddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startdate = false;
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });
    }

    public void showTimePicker(View view) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "hi"
        );
//        getString(R.string.timepicker)
    }

    public void processTimePickerResult(int hourOfDay, int minute) {
        // Convert time elements into strings.
        System.out.println(hourOfDay);
        String AM = "AM";
        if(hourOfDay == 0){
            hourOfDay = 12;
        } else if(hourOfDay == 12){
            AM = "PM";
        } else if(hourOfDay > 12){
            hourOfDay -= 12;
            if(hourOfDay < 12) {
                AM = "PM";
            }
        }

        String hour_string = Integer.toString(hourOfDay);
        String minute_string = Integer.toString(minute);

        if(minute < 10){
            minute_string = "0" + minute_string;
        }
        // Assign the concatenated strings to timeMessage.


        String timeMessage = (hour_string + ":" + minute_string + " " + AM);
//        getString(R.string.time_toast)


        TextView timedisplay = findViewById(R.id.timedisplay);
        TextView endtimedisplay = findViewById(R.id.endtimedisplay);
        if(startime){
            timedisplay.setText(hour_string + ":" + minute_string + " " + AM);
            Toast.makeText(this, "Event start time set to "
                    + timeMessage, Toast.LENGTH_SHORT).show();
        } else {
            endtimedisplay.setText(hour_string + ":" + minute_string + " " + AM);
            Toast.makeText(this, "Event end time set to "
                    + timeMessage, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        TextView datedisplay = findViewById(R.id.datedisplay);
        TextView enddate = findViewById(R.id.enddate);
        if(startdate){
            datedisplay.setText(currentDateString);
        } else {
            enddate.setText(currentDateString);
        }

    }
}