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
import android.support.v4.app.ActivityCompat;
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

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import static com.sda.syeddaniyalali.routealert.TemporaryAppData.ROUTE_ALERT_CONTACT_NUMBER_KEY;
import static com.sda.syeddaniyalali.routealert.TemporaryAppData.ROUTE_ALERT_URL_Emergency;
import static com.sda.syeddaniyalali.routealert.TemporaryAppData.ROUTE_ALERT_URL_EmergencyView;
import static com.sda.syeddaniyalali.routealert.TemporaryAppData.ROUTE_ALERT_USER_DEFAULT_VALUE;
import static com.sda.syeddaniyalali.routealert.TemporaryAppData.ROUTE_ALERT_USER_FIRST_NAME_KEY;
import static com.sda.syeddaniyalali.routealert.TemporaryAppData.ROUTE_ALERT_USER_ID_KEY;
import static com.sda.syeddaniyalali.routealert.TemporaryAppData.ROUTE_ALERT_USER_SharedPreference;

public class ActivityUserEmergencyHelp extends AppCompatActivity {

    TextView msg_User_emergency_id, msg_User_emergency_location;
    EditText msg_User_emergency_address, msg_User_emergency_Contact;
    Button msg_btn_user_emergency_Help;

    private static final String USER_ID="User_ID";
    private static final String USER_LOCATION="Location";
    private static final String USER_DETAIL_NAME="Detail_Name";
    private static final String USER_DETAIL_ADDRESS="Detail_Address";
    private static final String USER_DETAIL_CONTACT="Detail_Contact";
    private static final int REQUEST_CODE = 10;
    private static final int MAX_RESULT = 1;
    private static final String TOPIC="Admin";

    SharedPreferences get_sharedPreferences;

    LocationManager locationManager;
    Address msg_Address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_emergency_help);

        try {
            msg_User_emergency_id = findViewById(R.id.msg_User_emergency_id);
            msg_User_emergency_location = findViewById(R.id.msg_User_emergency_location);
            msg_User_emergency_address = findViewById(R.id.msg_User_emergency_address);
            msg_User_emergency_Contact = findViewById(R.id.msg_User_emergency_Contact);
            msg_btn_user_emergency_Help = findViewById(R.id.msg_btn_user_emergency_Help);

            get_sharedPreferences = getSharedPreferences(ROUTE_ALERT_USER_SharedPreference, Context.MODE_PRIVATE);

            msg_User_emergency_id.setText(get_sharedPreferences.getString(ROUTE_ALERT_USER_ID_KEY, ROUTE_ALERT_USER_DEFAULT_VALUE));
            msg_User_emergency_Contact.setText(get_sharedPreferences.getString(ROUTE_ALERT_CONTACT_NUMBER_KEY, ROUTE_ALERT_USER_DEFAULT_VALUE));

            showLocation();

            msg_btn_user_emergency_Help.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {
                        sendNotification(TOPIC, "Emergency Help", msg_User_emergency_Contact.getText()
                                .toString().trim());

                    }catch (Exception e)
                    {
                        Toast.makeText(ActivityUserEmergencyHelp.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    getHelp();

                }
            });
        }
        catch (Exception e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }



    private void getHelp()
    {
        //Save Data to API

        StringRequest request=new StringRequest(Request.Method.POST, ROUTE_ALERT_URL_Emergency, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Toast.makeText(ActivityUserEmergencyHelp.this, ""+s, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(ActivityUserEmergencyHelp.this, ""+volleyError, Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map=new HashMap<>();
                map.put(USER_ID, msg_User_emergency_id.getText().toString().trim());
                map.put(USER_LOCATION, msg_User_emergency_location.getText().toString().trim());
                map.put(USER_DETAIL_NAME, Objects.requireNonNull(get_sharedPreferences.getString(ROUTE_ALERT_USER_FIRST_NAME_KEY, ROUTE_ALERT_USER_DEFAULT_VALUE)));
                map.put(USER_DETAIL_ADDRESS, msg_User_emergency_address.getText().toString().trim());
                map.put(USER_DETAIL_CONTACT, msg_User_emergency_Contact.getText().toString().trim());


                return map;
            }
        };

        RequestQueue queue= Volley.newRequestQueue(this);
        queue.add(request);
    }



    //Getting Current Location----------------------------------------------------------------------

    @SuppressLint("SetTextI18n")
    public void showLocation(){

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if(ActivityCompat.checkSelfPermission(ActivityUserEmergencyHelp.this,android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            if(Build.VERSION.SDK_INT >= 23 &&
                    ActivityCompat.checkSelfPermission(ActivityUserEmergencyHelp.this,android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(ActivityUserEmergencyHelp.this,android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},REQUEST_CODE);

                return;
            }
        }

        try{

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,1,listener);
        }
        catch(Exception e){

            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        double msg_Longitude = location.getLongitude();
        double msg_Latitude = location.getLatitude();

        try
        {
            msg_Address=new Address(Locale.getDefault());
            msg_Address = getAddress(msg_Latitude,msg_Longitude);
            msg_User_emergency_location.setText(" Longitude: "+msg_Longitude+"\n Latitude: "+msg_Latitude);
            msg_User_emergency_address.setText(msg_Address.getAddressLine(0).toString());

        }catch (Exception e)
        {
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    LocationListener listener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
//            String result = "";
//            result += "Longitude: " + location.getLongitude() + "\n Latitude:  " + location.getLatitude();
//            Toast.makeText(ActivityUserEmergencyHelp.this, result, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            //Toast.makeText(ActivityUserEmergencyHelp.this, "something changed", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderEnabled(String provider) {
            //Toast.makeText(ActivityUserEmergencyHelp.this, ""+provider, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderDisabled(String provider) {
            //Toast.makeText(ActivityUserEmergencyHelp.this, ""+provider, Toast.LENGTH_SHORT).show();
        }
    };
    //----------------------------------------------------------------------------------------------

    //Getting Address Line----------------------------------------------------------------------------------------------
    public Address getAddress(double latitude, double longitude)
    {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude,longitude, MAX_RESULT);
            return addresses.get(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    //----------------------------------------------------------------------------------------------

    //calling notification Api

    public void sendNotification(String Topic,String Title,String Message) {

        String url ="http://msgtesting.000webhostapp.com/notification.php?Topic="+Topic+"&title="+Title+"&message="+Message;
        StringRequest requests = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if(s!=null)
                {
                    Toast.makeText(ActivityUserEmergencyHelp.this,"Notification Sent Successfully!", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(ActivityUserEmergencyHelp.this, volleyError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(requests);
    }
}
