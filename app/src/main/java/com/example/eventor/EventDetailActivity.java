package com.example.eventor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class EventDetailActivity extends AppCompatActivity {

    TextView  title, desc, sday, eday, etime, stime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        title = findViewById(R.id.textView);
        desc = findViewById(R.id.desc);
        sday = findViewById(R.id.startday);
        eday = findViewById(R.id.endday);
        etime = findViewById(R.id.endtime);
        stime = findViewById(R.id.starttime);

//        textView.setText();
        title.setText(getIntent().getStringExtra("title"));
        desc.setText(getIntent().getStringExtra("desc"));
        sday.setText(getIntent().getStringExtra("startday"));
        eday.setText(getIntent().getStringExtra("endday"));
        etime.setText(getIntent().getStringExtra("starttime"));
        stime.setText(getIntent().getStringExtra("endtime"));
    }
}