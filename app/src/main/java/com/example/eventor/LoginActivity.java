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

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    Button toEvents, toReg, login;
    public static String ngrokID = "https://e16c423a5a63.ngrok.io";
    private static String URL_LOGIN = ngrokID + "/eventor/login.php";
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
                startActivity(new Intent(getApplicationContext(), EventsActivity.class));
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

                                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));

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
}