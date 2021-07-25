package com.example.eventor;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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


import android.util.Log;


import static com.example.eventor.LoginActivity.ngrokID;

public class SplashScreenActivity extends Activity {

    private static final int SPLASH_SHOW_TIME = 500;
    private static String GET_FRIENDS = ngrokID + "/eventor/getfriends.php";
    private static String GET_EVENTS = ngrokID + "/eventor/getevents.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new BackgroundSplashTask().execute();

    }



    /**
     * Async Task: can be used to load DB, images during which the splash screen
     * is shown to user
     */
    private class BackgroundSplashTask extends AsyncTask{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            retrieveEvents();
            retrieveFriends();

            try {
                Thread.sleep(SPLASH_SHOW_TIME);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            Intent i = new Intent(SplashScreenActivity.this,
                    EventsActivity.class);
            // any info loaded can during splash_show
            // can be passed to main activity using
            // below
            i.putExtra("loaded_info", " ");
            startActivity(i);
            finish();
        }


    }


        public void retrieveFriends(){
        final ArrayList<User> tempList = new ArrayList<>();
//        System.out.println("got here");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_FRIENDS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
//                            Log.e("anyText",response);
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

//                                    System.out.println(fname + " " + lname);
//                                        TODO: kqwdkwqmld
                                    LoginActivity.user.addFriend((new User(id, fname, lname, email)));
                                }
                            }

//                            for (int i=0;i<tempList.size();i++){
//                                System.out.println(tempList.get(i));
//                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(SplashScreenActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                            System.out.println(e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SplashScreenActivity.this, "Error" + error.toString(), Toast.LENGTH_SHORT).show();
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

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
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
//                                    System.out.println(eday);

                                    LoginActivity.user.addEventt(new Eventt(ename, edesc, stime, etime, sday, eday, id, numjoined, ispublic));
                                }


                            }

//                            for (int i=0;i<tempList.size();i++){
//                                System.out.println(tempList.get(i));
//                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(SplashScreenActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                            System.out.println(e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SplashScreenActivity.this, "Error" + error.toString(), Toast.LENGTH_SHORT).show();
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
