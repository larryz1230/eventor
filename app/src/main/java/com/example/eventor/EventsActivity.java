package com.example.eventor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.example.eventor.LoginActivity.ngrokID;

public class EventsActivity extends AppCompatActivity {

    Button toExplore, toCreate, logout, findfriends;
    private static String GET_FRIENDS = ngrokID + "/eventor/getfriends.php";
    private static String GET_EVENTS = ngrokID + "/eventor/getevents.php";

    private RecyclerView mRecyclerView;
    private EventListAdapter mAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        putinlist();

        // Get a handle to the RecyclerView.
        mRecyclerView = findViewById(R.id.recycle);
// Create an adapter and supply the data to be displayed.
        mAdapter = new EventListAdapter(getApplicationContext(), LoginActivity.user.getEvents());
// Connect the adapter with the RecyclerView.
        mRecyclerView.setAdapter(mAdapter);
// Give the RecyclerView a default layout manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));





//        TextView title = findViewById(R.id.textView);
//        System.out.println(user.getEventSize());
//        title.setText(user.getEvents().get(0).getEname());


        toCreate = findViewById(R.id.create);
        toExplore = findViewById(R.id.explore);
        logout = findViewById(R.id.logout);
        findfriends = findViewById(R.id.findfriends);

        findfriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
            }
        });



        toExplore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ExploreActivity.class));
//                retrieveFriends();
//                retrieveEvents();
            }
        });

        toCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CreateActivity.class));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
//                putinlist();
            }
        });


//        System.out.println(LoginActivity.user.getEventSize());


    }




    public void putinlist() {
        for (int i = 0; i < LoginActivity.user.getEventSize(); i++) {
            System.out.println(LoginActivity.user.getEvents().get(i).getEname().trim());
        }

        mRecyclerView = findViewById(R.id.recycle);
// Create an adapter and supply the data to be displayed.
        mAdapter = new EventListAdapter(getApplicationContext(), LoginActivity.user.getEvents());
// Connect the adapter with the RecyclerView.
        mRecyclerView.setAdapter(mAdapter);
// Give the RecyclerView a default layout manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

    }
}