package com.example.eventor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class EventDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        TextView textView = findViewById(R.id.textView);
//        textView.setText();
        textView.setText(getIntent().getStringExtra("title"));
    }
}