package com.example.eventor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.ItemTouchHelper;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.eventor.LoginActivity.ngrokID;

public class EventsActivity extends AppCompatActivity {

    Button toExplore, toCreate, logout, findfriends;
    private static String GET_FRIENDS = ngrokID + "/eventor/getfriends.php";
    private static String GET_EVENTS = ngrokID + "/eventor/getevents.php";

    private RecyclerView mRecyclerView;
//    private EventListAdapter mAdapter;
    private List<Eventt> mSportsData;
    private EventsAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
//        putinlist();

//        // Get a handle to the RecyclerView.
//        mRecyclerView = findViewById(R.id.recycle);
//// Create an adapter and supply the data to be displayed.
//        mAdapter = new EventListAdapter(getApplicationContext(), LoginActivity.user.getEvents());
//// Connect the adapter with the RecyclerView.
//        mRecyclerView.setAdapter(mAdapter);
//// Give the RecyclerView a default layout manager.
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        // Initialize the RecyclerView.
        mRecyclerView = findViewById(R.id.recycle);

        // Set the Layout Manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the ArrayList that will contain the data.
        mSportsData = new ArrayList<>();

        // Initialize the adapter and set it to the RecyclerView.
        mAdapter = new EventsAdapter(this, mSportsData);
        mRecyclerView.setAdapter(mAdapter);

        // Get the data.
        initializeData();

        // Helper class for creating swipe to dismiss and drag and drop
        // functionality.
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper
                .SimpleCallback(
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT |
                        ItemTouchHelper.DOWN | ItemTouchHelper.UP,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            /**
             * Defines the drag and drop functionality.
             *
             * @param recyclerView The RecyclerView that contains the list items
             * @param viewHolder The SportsViewHolder that is being moved
             * @param target The SportsViewHolder that you are switching the
             *               original one with.
             * @return true if the item was moved, false otherwise
             */
            @Override
            public boolean onMove(RecyclerView recyclerView,
                                  RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                // Get the from and to positions.
                int from = viewHolder.getAdapterPosition();
                int to = target.getAdapterPosition();

                // Swap the items and notify the adapter.
                Collections.swap(mSportsData, from, to);
                mAdapter.notifyItemMoved(from, to);
                return true;
            }

            /**
             * Defines the swipe to dismiss functionality.
             *
             * @param viewHolder The viewholder being swiped.
             * @param direction The direction it is swiped in.
             */
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder,
                                 int direction) {
                // Remove the item from the dataset.
                mSportsData.remove(viewHolder.getAdapterPosition());
                // Notify the adapter.
                mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        });

        // Attach the helper to the RecyclerView.
        helper.attachToRecyclerView(mRecyclerView);




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

    private void initializeData() {
        // Get the resources from the XML file.



        // Clear the existing data (to avoid duplication).
        mSportsData.clear();

        // Create the ArrayList of Sports objects with the titles and
        // information about each sport
        for (int i = 0; i < LoginActivity.user.getEventSize(); i++) {
            mSportsData.add(LoginActivity.user.getEvents().get(i));
        }

        // Notify the adapter of the change.
        mAdapter.notifyDataSetChanged();
    }
//    public void putinlist() {
//        for (int i = 0; i < LoginActivity.user.getEventSize(); i++) {
//            System.out.println(LoginActivity.user.getEvents().get(i).getEname().trim());
//        }
//
//        mRecyclerView = findViewById(R.id.recycle);
//// Create an adapter and supply the data to be displayed.
//        mAdapter = new EventListAdapter(getApplicationContext(), LoginActivity.user.getEvents());
//// Connect the adapter with the RecyclerView.
//        mRecyclerView.setAdapter(mAdapter);
//// Give the RecyclerView a default layout manager.
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//
//    }
}