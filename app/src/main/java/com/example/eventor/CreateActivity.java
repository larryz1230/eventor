package com.example.eventor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CreateActivity extends AppCompatActivity  implements DatePickerDialog.OnDateSetListener {

    boolean startdate;
    private static String URL_REGIST = LoginActivity.ngrokID+"/eventor/addevent.php";
    boolean startime, pubb;
    EditText eventname, eventdesc;
    RadioGroup rgroup;
    RadioButton rpriv, rpub;
    TextView timedisplay, endtimedisplay, datedisplay, enddatedisplay;
    Button  submit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        Button opendate = findViewById(R.id.datepicker);
        Button enddate = findViewById(R.id.datepicker2);
        Button starttime = findViewById(R.id.button);
        Button endtime = findViewById(R.id.endtime);

        starttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startime = true;
                showTimePicker(view);
            }
        });

        endtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startime = false;
                showTimePicker(view);
            }
        });

        opendate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startdate = true;
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        enddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startdate = false;
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });


        eventname = findViewById(R.id.eventtitle);
        eventdesc = findViewById(R.id.eventdesc);
        rgroup = findViewById(R.id.radioGroup);
        rpriv = findViewById(R.id.private1);
        rpub = findViewById(R.id.public1);
        submit = findViewById(R.id.submit);

        pubb = false;
        if (rpub.isChecked()){
            pubb = true;
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addevent(eventname.getText().toString().trim(), eventdesc.getText().toString().trim(), timedisplay.getText().toString().trim(), endtimedisplay.getText().toString().trim(), pubb, datedisplay.getText().toString().trim(), enddatedisplay.getText().toString().trim());

            }
        });
    }

    public void showTimePicker(View view) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "hi"
        );
//        getString(R.string.timepicker)
    }

    public void processTimePickerResult(int hourOfDay, int minute) {
        // Convert time elements into strings.
        System.out.println(hourOfDay);
        String AM = "AM";
        if(hourOfDay == 0){
            hourOfDay = 12;
        } else if(hourOfDay == 12){
            AM = "PM";
        } else if(hourOfDay > 12){
            hourOfDay -= 12;
            if(hourOfDay < 12) {
                AM = "PM";
            }
        }

        String hour_string = Integer.toString(hourOfDay);
        String minute_string = Integer.toString(minute);

        if(minute < 10){
            minute_string = "0" + minute_string;
        }
        // Assign the concatenated strings to timeMessage.


        String timeMessage = (hour_string + ":" + minute_string + " " + AM);
//        getString(R.string.time_toast)


        timedisplay = findViewById(R.id.timedisplay);
        endtimedisplay = findViewById(R.id.endtimedisplay);
        if(startime){
            timedisplay.setText(hour_string + ":" + minute_string + " " + AM);
            Toast.makeText(this, "Event start time set to "
                    + timeMessage, Toast.LENGTH_SHORT).show();
        } else {
            endtimedisplay.setText(hour_string + ":" + minute_string + " " + AM);
            Toast.makeText(this, "Event end time set to "
                    + timeMessage, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        datedisplay = findViewById(R.id.datedisplay);
        enddatedisplay = findViewById(R.id.enddate);
        if(startdate){
            datedisplay.setText(currentDateString);
        } else {
            enddatedisplay.setText(currentDateString);
        }

    }

    private void addevent(final String ename, final String edesc, final String datestart, final String dateend, final boolean publicc, final String startday, final String endday){
//        mRegister.setVisibility(View.GONE);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");



                            if (success.equals("1")){
                                Toast.makeText(CreateActivity.this, "Creation Success", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), EventsActivity.class));

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("anyText",response);
                            System.out.println(e.toString());
                            Toast.makeText(CreateActivity.this, "Event Error" + e.toString(), Toast.LENGTH_SHORT).show();
//                            mRegister.setVisibility(View.VISIBLE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.toString());
                        Toast.makeText(CreateActivity.this, "Event Error" + error.toString(), Toast.LENGTH_SHORT).show();
//                        .setVisibility(View.VISIBLE);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map <String, String> params = new HashMap<>();
                params.put ("ename", ename);
                params.put ("edesc", edesc);
                params.put("estart", datestart);
                params.put("eend", dateend);
                params.put("startday", startday);
                params.put("endday", endday);
                if (publicc){
                    params.put("public", "true");
                } else{
                    params.put("public", "false");
                }
                params.put("email", LoginActivity.user.getEmail());


//                System.out.println(fname + " " + lname);
                return params;


            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}