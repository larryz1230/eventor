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
//                startActivity(new Intent(getApplicationContext(), EventsActivity.class));
                putinlist();
            }
        });


//        System.out.println(LoginActivity.user.getEventSize());


    }


//    public void retrieveFriends(){
//        final ArrayList<User> tempList = new ArrayList<>();
////        System.out.println("got here");
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_FRIENDS,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
////                            Log.e("anyText",response);
//                            JSONObject jsonObject = new JSONObject(response);
//                            String success = jsonObject.getString("success");
//                            JSONArray jsonArray = jsonObject.optJSONArray("getusers");
//
//
//                            if (success.equals("1")) {
//                                for (int i = 0; i < jsonArray.length(); i++) {
//                                    JSONObject object = jsonArray.optJSONObject(i);
//                                    String email = object.getString("email").trim();
//                                    int id = object.getInt("id");
//                                    String fname = object.getString("fname");
//                                    String lname  = object.getString("lname");
//
////                                    System.out.println(fname + " " + lname);
////                                        TODO: kqwdkwqmld
//                                    LoginActivity.user.addFriend((new User(id, fname, lname, email)));
//                                }
//                            }
//
////                            for (int i=0;i<tempList.size();i++){
////                                System.out.println(tempList.get(i));
////                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            Toast.makeText(EventsActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
//                            System.out.println(e.toString());
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(EventsActivity.this, "Error" + error.toString(), Toast.LENGTH_SHORT).show();
//                        System.out.println(error.toString());
//                    }
//                }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("email", LoginActivity.user.getEmail());
//                return params;
//            }
//        };
//
//        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//        requestQueue.add(stringRequest);
//
//
//
//
//    }
//
//
//
//    public void retrieveEvents(){
//        final ArrayList<User> tempList = new ArrayList<>();
////        System.out.println("got here");
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_EVENTS,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            Log.e("anyText",response);
//                            JSONObject jsonObject = new JSONObject(response);
//                            String success = jsonObject.getString("success");
//                            JSONArray jsonArray = jsonObject.optJSONArray("getusers");
//
//
//                            if (success.equals("1")) {
//                                for (int i = 0; i < jsonArray.length(); i++) {
//                                    JSONObject object = jsonArray.optJSONObject(i);
//                                    String ename = object.getString("ename").trim();
//                                    String edesc = object.getString("edesc").trim();
//                                    String stime = object.getString("estart").trim();
//                                    String etime = object.getString("eend").trim();
//                                    String sday = object.getString("startday").trim();
//                                    String eday = object.getString("endday").trim();
//                                    int id = object.getInt("id");
//                                    int numjoined = object.getInt("numjoined");
//                                    String pub = object.getString("public").trim();
//                                    boolean ispublic = false;
//                                    if (pub.equals("true")){
//                                        ispublic = true;
//                                    }
////                                    System.out.println(eday);
//
//                                    LoginActivity.user.addEventt(new Eventt(ename, edesc, stime, etime, sday, eday, id, numjoined, ispublic));
//                                }
//
//
//                            }
//
////                            for (int i=0;i<tempList.size();i++){
////                                System.out.println(tempList.get(i));
////                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            Toast.makeText(EventsActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
//                            System.out.println(e.toString());
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(EventsActivity.this, "Error" + error.toString(), Toast.LENGTH_SHORT).show();
//                        System.out.println(error.toString());
//                    }
//                }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("email", LoginActivity.user.getEmail());
//                return params;
//            }
//        };
//
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(stringRequest);
//    }

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