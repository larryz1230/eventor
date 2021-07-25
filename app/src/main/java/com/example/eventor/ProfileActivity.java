package com.example.eventor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
//import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.Friendor.FriendListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    Button addfriend;
    RecyclerView mRecyclerView, amRecyclerView;
    com.example.Friendor.FriendListAdapter mAdapter;
    EditText femail;
    private String URL_CREDITS = LoginActivity.ngrokID + "/eventor/findfriend.php";
    private String URL_ADD = LoginActivity.ngrokID + "/eventor/addfriend.php";
    SwipeRefreshLayout mySwipeRefreshLayout;
    private User tempuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        femail = findViewById(R.id.femail);
        addfriend = findViewById(R.id.addfriend);

        addfriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFriend(femail.getText().toString().trim());
            }
        });


        ArrayList<User> fff = new ArrayList<>();
        for (int i =0; i<13; i++){
            fff.add(LoginActivity.user.getFriends().get(i%LoginActivity.user.getFriends().size()));
        }

//        mySwipeRefreshLayout = findViewById(R.id.swiperefresh);
//
//        mySwipeRefreshLayout.setOnRefreshListener(
//                new SwipeRefreshLayout.OnRefreshListener() {
//                    @Override
//                    public void onRefresh() {
//                        Log.i("Profile Activity", "onRefresh called from SwipeRefreshLayout");
//
//                        // This method performs the actual data-refresh operation.
//                        // The method calls setRefreshing(false) when it's finished.
////                        myUpdateOperation();
//                    }
//                }
//        );





        // Get a handle to the RecyclerView.
        mRecyclerView = findViewById(R.id.recycle);
// Create an adapter and supply the data to be displayed.
        mAdapter = new FriendListAdapter(getApplicationContext(), LoginActivity.user.getFriends());
// Connect the adapter with the RecyclerView.
        mRecyclerView.setAdapter(mAdapter);
// Give the RecyclerView a default layout manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));



        // Get a handle to the RecyclerView.
        amRecyclerView = findViewById(R.id.recycle1);
// Create an adapter and supply the data to be displayed.
        mAdapter = new FriendListAdapter(getApplicationContext(), fff);
// Connect the adapter with the RecyclerView.
        amRecyclerView.setAdapter(mAdapter);
// Give the RecyclerView a default layout manager.
        amRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    private void addFriend (final String email){

//            loading.setVisibility(View.VISIBLE);
//            mLogin.setVisibility(View.GONE);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_CREDITS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("login");

                            if (success.equals("1")) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String fname = object.getString("fname").trim();
                                    String lname = object.getString("lname").trim();
                                    String email = object.getString("email").trim();
//                                        String age = object.getString("age").trim();
                                    int ID = object.getInt("id");

                                    for (int j=0; j<LoginActivity.user.getFriends().size(); j++){
                                        if (LoginActivity.user.getFriends().get(j).getEmail().equals(email)){
                                            Toast.makeText(ProfileActivity.this, "User Already Added", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                    }

                                    if (LoginActivity.user.getEmail().equals(email)){
                                        Toast.makeText(ProfileActivity.this, "Cannot Friend yourself", Toast.LENGTH_SHORT).show();
                                        return;
                                    }


//                                    tempuser = new User(ID, fname, lname, email);
                                    confirmFriend(LoginActivity.user.getEmail(), email, LoginActivity.user.getId(), ID, LoginActivity.user.getFname(), LoginActivity.user.getLname(),fname, lname);
//                                    confirmFriend(email, LoginActivity.user.getEmail(), ID, LoginActivity.user.getId(), fname, lname, LoginActivity.user.getFname(), LoginActivity.user.getLname());


//                                    Toast.makeText(ProfileActivity.this, "Success", Toast.LENGTH_SHORT).show();

//                                        loading.setVisibility(View.GONE);
                                }
                            } else{
                              Toast.makeText(ProfileActivity.this, "User Not Found", Toast.LENGTH_SHORT).show();
                                Log.e("anyText",response);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
//                                loading.setVisibility(View.GONE);
                            Toast.makeText(ProfileActivity.this, "User Not Found", Toast.LENGTH_SHORT).show();
//                                mLogin.setVisibility(View.VISIBLE);
                            Log.e("anyText",response);

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                            loading.setVisibility(View.GONE);
//                            mLogin.setVisibility(View.VISIBLE);

                        Toast.makeText(ProfileActivity.this, "User Not Found", Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void confirmFriend(final String email1, final String email2, final Integer id1, final Integer id2, final String fname1, final String lname1, final String fname2, final String lname2){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ADD,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            System.out.println("here");



                            if (success.equals("1")){
                                Toast.makeText(ProfileActivity.this, "Confirm Success", Toast.LENGTH_SHORT).show();
//                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
//                                LoginActivity.user.addFriend(tempuser);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("anyText",response);
                            System.out.println(e.toString());
                            Toast.makeText(ProfileActivity.this, "Register Error" + e.toString(), Toast.LENGTH_SHORT).show();
//                            mRegister.setVisibility(View.VISIBLE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.toString());
                        Toast.makeText(ProfileActivity.this, "Register Error" + error.toString(), Toast.LENGTH_SHORT).show();
//                        .setVisibility(View.VISIBLE);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map <String, String> params = new HashMap<>();
                params.put ("id1", id1.toString());
                params.put ("id2", id2.toString());
                params.put("email1", email1);
                params.put("email2", email2);
                params.put("fname1", fname1);
                params.put("lname1", lname1);
                params.put("fname2", fname2);
                params.put("lname2", lname2);

//                System.out.println(fname + " " + lname);
                return params;


            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}