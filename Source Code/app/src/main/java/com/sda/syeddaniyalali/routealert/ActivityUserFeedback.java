package com.sda.syeddaniyalali.routealert;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.sda.syeddaniyalali.routealert.TemporaryAppData.ROUTE_ALERT_URL_FeedBack;
import static com.sda.syeddaniyalali.routealert.TemporaryAppData.ROUTE_ALERT_USER_DEFAULT_VALUE;
import static com.sda.syeddaniyalali.routealert.TemporaryAppData.ROUTE_ALERT_USER_ID_KEY;
import static com.sda.syeddaniyalali.routealert.TemporaryAppData.ROUTE_ALERT_USER_SharedPreference;

public class ActivityUserFeedback extends AppCompatActivity {

    TextView msg_FeedbackUserDate;
    EditText msg_FeedbackMessage;
    Button msg_FeedbackSubmitButton;

    SharedPreferences preferences;

    private static final String USER_ID = "User_ID";
    private static final String  DESCRIPTION = "Description";
    private static final String DATE = "Date";

    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    String date = DateFormat.getDateTimeInstance().format(new Date());

// textView is the TextView view that should display it

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_feedback);

        msg_FeedbackUserDate = findViewById(R.id.msg_FeedbackUserDate);
        msg_FeedbackMessage = findViewById(R.id.msg_FeedbackMessage);
        msg_FeedbackSubmitButton = findViewById(R.id.msg_FeedbackSubmitButton);
        msg_FeedbackUserDate.setText(date);

        preferences = getSharedPreferences(ROUTE_ALERT_USER_SharedPreference, Context.MODE_PRIVATE);
       // date= sdf.format(System.currentTimeMillis());
        msg_FeedbackSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!msg_FeedbackMessage.getText().toString().trim().isEmpty())
                {
                    sendFeedBack();
                }
                else
                {
                    Toast.makeText(ActivityUserFeedback.this, "Do Complete FeedBack", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void sendFeedBack() {

        //Save to API

        StringRequest request=new StringRequest(Request.Method.POST, ROUTE_ALERT_URL_FeedBack, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Toast.makeText(ActivityUserFeedback.this, ""+s, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(ActivityUserFeedback.this, "Please Check Internet and Try Again"+volleyError, Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map=new HashMap<>();

                map.put(USER_ID, Objects.requireNonNull(preferences.getString(ROUTE_ALERT_USER_ID_KEY,ROUTE_ALERT_USER_DEFAULT_VALUE)));
                map.put(DESCRIPTION, msg_FeedbackMessage.getText().toString());
                map.put(DATE, date);


                return map;
            }
        };

        RequestQueue queue= Volley.newRequestQueue(this);
        queue.add(request);
    }
}
