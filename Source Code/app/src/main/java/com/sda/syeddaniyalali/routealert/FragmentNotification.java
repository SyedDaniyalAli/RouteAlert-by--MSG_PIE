package com.sda.syeddaniyalali.routealert;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.SimpleTimeZone;

import static com.sda.syeddaniyalali.routealert.TemporaryAppData.ROUTE_ALERT_URL_Notification;
import static com.sda.syeddaniyalali.routealert.TemporaryAppData.ROUTE_ALERT_URL_SendNotification;
import static com.sda.syeddaniyalali.routealert.TemporaryAppData.ROUTE_ALERT_USER_CURRENT_LOCATION_LAT;
import static com.sda.syeddaniyalali.routealert.TemporaryAppData.ROUTE_ALERT_USER_CURRENT_LOCATION_LNG;
import static com.sda.syeddaniyalali.routealert.TemporaryAppData.ROUTE_ALERT_USER_DEFAULT_VALUE;
import static com.sda.syeddaniyalali.routealert.TemporaryAppData.ROUTE_ALERT_USER_SharedPreference;

public class FragmentNotification extends Fragment {

    EditText msg_admin_send_notifications;
    Button msg_btn_Admin_emergency_Help;
    TextView msg_user_Notification_Date, msg_user_Notification_Time, msg_user_Complaint_Location;

    private static final String DATE="Date";
    private static final String TIME="Time";
    private static final String LOCATION="Location";
    private static final String DESCRIPTION="Description";
    private static final String TOPIC="User";



    SharedPreferences get_sharedPreferences;

    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String date = sdf.format(System.currentTimeMillis());

    private static String time;
    private static String Lat;
    private static String Lng;


    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_notifications,container,false);

        msg_admin_send_notifications = view.findViewById(R.id.msg_admin_send_notifications);
        msg_btn_Admin_emergency_Help = view.findViewById(R.id.msg_btn_notify);
        msg_user_Notification_Date = view.findViewById(R.id.msg_user_Notification_Date);
        msg_user_Notification_Time = view.findViewById(R.id.msg_user_Notification_Time);
        msg_user_Complaint_Location = view.findViewById(R.id.msg_user_Complaint_Location);

        get_sharedPreferences = this.getActivity().getSharedPreferences(ROUTE_ALERT_USER_SharedPreference,Context.MODE_PRIVATE);


        Lat = get_sharedPreferences.getString(ROUTE_ALERT_USER_CURRENT_LOCATION_LAT,ROUTE_ALERT_USER_DEFAULT_VALUE);
        Lng = get_sharedPreferences.getString(ROUTE_ALERT_USER_CURRENT_LOCATION_LNG,ROUTE_ALERT_USER_DEFAULT_VALUE);

        msg_user_Complaint_Location.setText(" Lat: "+Lat+" \n Lng: "+Lng);

        //Get Current Time--------------------------------------------------------------------------
        Calendar cal = Calendar.getInstance();
        Date date1=cal.getTime();
        @SuppressLint("SimpleDateFormat")
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        time=dateFormat.format(date1);


        msg_user_Notification_Date.setText(" "+date);
        msg_user_Notification_Time.setText(" "+time);


        msg_btn_Admin_emergency_Help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                generateNotification();

                try {
                    sendNotification(TOPIC, "NEWS ALERT", msg_admin_send_notifications.getText()
                    .toString().trim());

                }catch (Exception e)
                {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }



            }
        });

        return view;
    }

    private void generateNotification() {

        //Save to API

        StringRequest request=new StringRequest(Request.Method.POST, ROUTE_ALERT_URL_Notification, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Toast.makeText(getContext(), ""+s, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getContext(), ""+volleyError, Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map=new HashMap<>();
                map.put(DATE,date);
                map.put(TIME,time);
                map.put(LOCATION," Lat: "+Lat+" \n Lng: "+Lng);
                map.put(DESCRIPTION,msg_admin_send_notifications.getText().toString().trim());


                return map;
            }
        };

        RequestQueue queue= Volley.newRequestQueue(Objects.requireNonNull(getContext()).getApplicationContext());
        queue.add(request);

    }


    //calling notification Api

    public void sendNotification(String Topic,String Title,String Message) {

        String url ="http://msgtesting.000webhostapp.com/notification.php?Topic="+Topic+"&title="+Title+"&message="+Message;
        StringRequest requests = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if(s!=null)
                {
                    Toast.makeText(getActivity(),"Notification Sent Successfully!", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getActivity(), volleyError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(Objects.requireNonNull(getContext()));
        requestQueue.add(requests);
    }

}
