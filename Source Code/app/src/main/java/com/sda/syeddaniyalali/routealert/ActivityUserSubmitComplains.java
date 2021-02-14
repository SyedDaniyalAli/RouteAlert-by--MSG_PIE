package com.sda.syeddaniyalali.routealert;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.sda.syeddaniyalali.routealert.TemporaryAppData.ROUTE_ALERT_URL_Complaint;
import static com.sda.syeddaniyalali.routealert.TemporaryAppData.ROUTE_ALERT_USER_DEFAULT_VALUE;
import static com.sda.syeddaniyalali.routealert.TemporaryAppData.ROUTE_ALERT_USER_ID_KEY;
import static com.sda.syeddaniyalali.routealert.TemporaryAppData.ROUTE_ALERT_USER_SharedPreference;


public class ActivityUserSubmitComplains extends AppCompatActivity {

    EditText msg_txt_user_complain_title, msg_txt_user_complain_desc;
    Button msg_btn_user_complain_submit;
    Spinner msg_spinner;

    private final static String USER_ID="User_ID";
    private final static String Complaint_ID="Complaint_ID";
    private final static String TITLE="Title";
    private final static String COMPLAINT_CATEGORY="Complaint_Category";
    private final static String DESCRIPTION="Description";
    private final static String DATE="Date";
    private final static String STATUS="Status";

    @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String date = sdf.format(System.currentTimeMillis());

    SharedPreferences preferences;

    //TODO User_ID, Complaint_ID, ==Title==, ==Complaint_Category==, ==Desription==, Date, Status=false

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_submit_complains);

        preferences = getSharedPreferences(ROUTE_ALERT_USER_SharedPreference, Context.MODE_PRIVATE);


        msg_txt_user_complain_title = findViewById(R.id.msg_txt_user_complain_title);
        msg_txt_user_complain_desc = findViewById(R.id.msg_txt_user_complain_desc);
        msg_btn_user_complain_submit = findViewById(R.id.msg_btn_user_complain_submit);
        msg_spinner = findViewById(R.id.msg_spinner);

        msg_btn_user_complain_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    submitComplaints();
                }
                catch (Exception e)
                {
                    Toast.makeText(ActivityUserSubmitComplains.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    private void submitComplaints() {

        //Save to API

        StringRequest request=new StringRequest(Request.Method.POST, ROUTE_ALERT_URL_Complaint, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                Toast.makeText(ActivityUserSubmitComplains.this, s, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(ActivityUserSubmitComplains.this, ""+volleyError, Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map=new HashMap<>();

                map.put(USER_ID, preferences.getString(ROUTE_ALERT_USER_ID_KEY, ROUTE_ALERT_USER_DEFAULT_VALUE));
                map.put(TITLE, msg_txt_user_complain_title.getText().toString());
                map.put(COMPLAINT_CATEGORY, msg_spinner.getSelectedItem().toString());
                map.put(DESCRIPTION, msg_txt_user_complain_desc.getText().toString());
                map.put(DATE, date);
                map.put(STATUS, "Pending");

                return map;
            }
        };

        RequestQueue queue= Volley.newRequestQueue(this);
        queue.add(request);

    }
}
