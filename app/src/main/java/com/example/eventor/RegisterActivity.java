package com.example.eventor;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private static String URL_REGIST = LoginActivity.ngrokID+"/eventor/register.php";
    Button toLog, Register;
    private EditText fname, lname, email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        toLog = findViewById(R.id.tolog);
        Register = findViewById(R.id.register);
        toLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });

        fname = findViewById(R.id.fname);
        lname = findViewById(R.id.lname);
        password = findViewById(R.id.password);
        email = findViewById(R.id.email);

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Regist();
            }
        });
    }

    private void Regist(){
//        mRegister.setVisibility(View.GONE);

        final String fname = this.fname.getText().toString().trim();
        final String lname = this.lname.getText().toString().trim();
        final String email = this.email.getText().toString().trim();
        final String password = this.password.getText().toString().trim();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");



                            if (success.equals("1")){
                                Toast.makeText(RegisterActivity.this, "Register Success", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("anyText",response);
                            System.out.println(e.toString());
                            Toast.makeText(RegisterActivity.this, "Register Error" + e.toString(), Toast.LENGTH_SHORT).show();
//                            mRegister.setVisibility(View.VISIBLE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.toString());
                        Toast.makeText(RegisterActivity.this, "Register Error" + error.toString(), Toast.LENGTH_SHORT).show();
//                        .setVisibility(View.VISIBLE);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map <String, String> params = new HashMap<>();
                params.put ("fname", fname);
                params.put ("lname", lname);
                params.put("email", email);
                params.put("password", password);

                System.out.println(fname + " " + lname);
                return params;


            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}