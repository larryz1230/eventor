package com.example.eventor;

import androidx.appcompat.app.AppCompatActivity;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    Button toEvents, toReg, login;
  
    public static String ngrokID = "https://c5863d7eff9f.ngrok.io";

    private static String URL_LOGIN = ngrokID + "/eventor/login.php";
    private static String GET_FRIENDS = ngrokID + "/eventor/getfriends.php";
    private static String GET_EVENTS = ngrokID + "/eventor/getevents.php";
    EditText memail, pass;
    public static User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        memail = findViewById(R.id.email);
        pass = findViewById(R.id.password);

        toEvents = findViewById(R.id.toevents);
        toEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login("larry@gmail.com", "larry123");
            }
        });

        toReg = findViewById(R.id.toreg);
        toReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });

        login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login(memail.getText().toString().trim(), pass.getText().toString().trim());
            }
        });

    }

    private void Login (final String email, final String password){

//            loading.setVisibility(View.VISIBLE);
//            mLogin.setVisibility(View.GONE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
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

                                    user = new User(ID, fname, lname, email);

//                                    retrieveFriends();
//                                    retrieveEvents();

                                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                                    startActivity(new Intent(getApplicationContext(), SplashScreenActivity.class));

//                                        loading.setVisibility(View.GONE);
                                }
                            } else{
//                                    loading.setVisibility(View.GONE);
//                                    login.setVisibility(View.VISIBLE);
                                memail.setText("");
                                pass.setText("");
                                Toast.makeText(LoginActivity.this, "Username or Password incorrect \n Please Try Again", Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
//                                loading.setVisibility(View.GONE);
//                                mLogin.setVisibility(View.VISIBLE);
                            memail.setText("");
                            pass.setText("");
                            Toast.makeText(LoginActivity.this, "Username or Password incorrect \n Please Try Again", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                            loading.setVisibility(View.GONE);
//                            mLogin.setVisibility(View.VISIBLE);
                        Toast.makeText(LoginActivity.this, "Error" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void retrieveFriends(){
        final ArrayList<User> tempList = new ArrayList<>();
//        System.out.println("got here");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_FRIENDS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("anyText",response);
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.optJSONArray("getusers");


                            if (success.equals("1")) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.optJSONObject(i);
                                    String email = object.getString("email").trim();
                                    int id = object.getInt("id");
                                    String fname = object.getString("fname");
                                    String lname  = object.getString("lname");

                                    System.out.println(fname + " " + lname);
//                                        TODO: kqwdkwqmld
                                    user.addFriend((new User(id, fname, lname, email)));
                                }
                            }

//                            for (int i=0;i<tempList.size();i++){
//                                System.out.println(tempList.get(i));
//                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(LoginActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                            System.out.println(e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, "Error" + error.toString(), Toast.LENGTH_SHORT).show();
                        System.out.println(error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", LoginActivity.user.getEmail());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }



    public void retrieveEvents(){
        final ArrayList<User> tempList = new ArrayList<>();
//        System.out.println("got here");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_EVENTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("anyText",response);
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.optJSONArray("getusers");


                            if (success.equals("1")) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.optJSONObject(i);
                                    String ename = object.getString("ename").trim();
                                    String edesc = object.getString("edesc").trim();
                                    String stime = object.getString("estart").trim();
                                    String etime = object.getString("eend").trim();
                                    String sday = object.getString("startday").trim();
                                    String eday = object.getString("endday").trim();
                                    int id = object.getInt("id");
                                    int numjoined = object.getInt("numjoined");
                                    String pub = object.getString("public").trim();
                                    boolean ispublic = false;
                                    if (pub.equals("true")){
                                        ispublic = true;
                                    }

//                                    System.out.println(ename + " "  + edesc + "  " + stime + " " + etime);

                                    Eventt eventttt = new Eventt(ename, edesc, stime, etime, sday, eday, id, numjoined, ispublic);
                                    System.out.println(eventttt.getEname());
                                    user.addEventt(eventttt);

                                }
                            }

//                            for (int i=0;i<tempList.size();i++){
//                                System.out.println(tempList.get(i));
//                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(LoginActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                            System.out.println(e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, "Error" + error.toString(), Toast.LENGTH_SHORT).show();
                        System.out.println(error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", LoginActivity.user.getEmail());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}