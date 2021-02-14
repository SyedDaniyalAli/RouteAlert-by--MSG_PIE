package com.sda.syeddaniyalali.routealert;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import static com.sda.syeddaniyalali.routealert.TemporaryAppData.ROUTE_ALERT_ADMIN_DEFAULT_EMAIL;
import static com.sda.syeddaniyalali.routealert.TemporaryAppData.ROUTE_ALERT_ADMIN_DEFAULT_PASSWORD;
import static com.sda.syeddaniyalali.routealert.TemporaryAppData.ROUTE_ALERT_CONTACT_NUMBER_KEY;
import static com.sda.syeddaniyalali.routealert.TemporaryAppData.ROUTE_ALERT_EMAIL_ID_KEY;
import static com.sda.syeddaniyalali.routealert.TemporaryAppData.ROUTE_ALERT_ID_PROOF_KEY;
import static com.sda.syeddaniyalali.routealert.TemporaryAppData.ROUTE_ALERT_PASSWORD_KEY;
import static com.sda.syeddaniyalali.routealert.TemporaryAppData.ROUTE_ALERT_URL_sign_inView;
import static com.sda.syeddaniyalali.routealert.TemporaryAppData.ROUTE_ALERT_USER_CURRENT_CREDENTIALS;
import static com.sda.syeddaniyalali.routealert.TemporaryAppData.ROUTE_ALERT_USER_CURRENT_LOCATION_LAT;
import static com.sda.syeddaniyalali.routealert.TemporaryAppData.ROUTE_ALERT_USER_CURRENT_LOCATION_LNG;
import static com.sda.syeddaniyalali.routealert.TemporaryAppData.ROUTE_ALERT_USER_DEFAULT_VALUE;
import static com.sda.syeddaniyalali.routealert.TemporaryAppData.ROUTE_ALERT_USER_FIRST_NAME_KEY;
import static com.sda.syeddaniyalali.routealert.TemporaryAppData.ROUTE_ALERT_USER_ID_KEY;
import static com.sda.syeddaniyalali.routealert.TemporaryAppData.ROUTE_ALERT_USER_LAST_NAME_KEY;
import static com.sda.syeddaniyalali.routealert.TemporaryAppData.ROUTE_ALERT_USER_SharedPreference;

public class LoginUserActivity extends AppCompatActivity {

    //Static Fields---------------------------------------------------------------------------------

    private final static String EMAIL_ID = "User_Email";
    private final static String PASSWORD = "User_Pass";

    private static final int REQUEST_CODE = 10;
    private static final int MIN_DISTANCE = 5;
    private static final int MIN_TIME = 7;
    private static final int MAX_RESULT = 1;


    LocationManager locationManager;
    //Address msg_Address;

    //Declaring-------------------------------------------------------------------------------------
    EditText msg_LoginUserEmail, msg_LoginUserpassword;
    Switch msg_user_switch;
    Button msg_LoginUserSignUpBtn;
    TextView msg_LoginUserLinkLogin;

    SharedPreferences Set_sharedPreferences;
    SharedPreferences.Editor editor;
    Intent checkIntent;
    private static String checkID = "User";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);

        //Initializing------------------------------------------------------------------------------
        //EditTexts==================
        msg_LoginUserEmail = findViewById(R.id.msg_LoginUserEmail);
        msg_LoginUserpassword = findViewById(R.id.msg_LoginUserpassword);
        //Switch==================
        msg_user_switch = findViewById(R.id.msg_user_switch);
        //Buttons==================
        msg_LoginUserSignUpBtn = findViewById(R.id.msg_LoginUserSignUpBtn);
        msg_user_switch.setChecked(false);
        //TextView==================
        msg_LoginUserLinkLogin = findViewById(R.id.msg_LoginUserLinkLogin);

        //Set_sharedPreferences==================
        Set_sharedPreferences = getSharedPreferences(ROUTE_ALERT_USER_SharedPreference, Context.MODE_PRIVATE);
        checkIntent = new Intent(LoginUserActivity.this, MainActivity.class);

        String msg_ID=Set_sharedPreferences.getString(ROUTE_ALERT_USER_CURRENT_CREDENTIALS,ROUTE_ALERT_USER_DEFAULT_VALUE);

        if(Objects.equals(msg_ID, "USER"))
        {
            Intent intent=new Intent(LoginUserActivity.this, MainActivity.class);
            startActivity(intent);
        }
        else if(Objects.equals(msg_ID, "ADMIN"))
        {
            Intent intent=new Intent(LoginUserActivity.this, ActivityMainAdmin.class);
            startActivity(intent);
        }


        //Switch==================
        msg_user_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        try {
            if (msg_user_switch.isChecked()) {
                Toast.makeText(LoginUserActivity.this, "Login set for CTCD Department", Toast.LENGTH_SHORT).show();
                checkID = "Admin";
                checkIntent = new Intent(LoginUserActivity.this, ActivityMainAdmin.class);
            } else {
               Toast.makeText(LoginUserActivity.this, "Login set for User", Toast.LENGTH_SHORT).show();
                checkID = "User";
                checkIntent = new Intent(LoginUserActivity.this, MainActivity.class);
            }
        }
        catch (Exception e)
        {
            //Toast.makeText(LoginUserActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
            }
        });

        //Buttons==================
        msg_LoginUserSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    try {

                        //create a condition here if user equals admin then create this
                        if (checkID.equals("Admin")) {

                            adminLogin();
                            showLocation();

                        } else if(checkID.equals("User"))
                        {
                            checkUserCredentials();
                    }




            }
catch(Exception e)
{
    //Toast.makeText(LoginUserActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
}
            }
        });

        //TextView==================
        msg_LoginUserLinkLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginUserActivity.this, RegisterUserActivity.class);
                startActivity(intent);
                finish();

            }
        });


    }

    //Admin PostCheck==================
    private void adminLogin() {
try {
    if (!(msg_LoginUserEmail.getText().toString().isEmpty()) && !(msg_LoginUserpassword.getText().toString().isEmpty())) {
        if (msg_LoginUserEmail.getText().toString().trim().equals(ROUTE_ALERT_ADMIN_DEFAULT_EMAIL) &&
                msg_LoginUserpassword.getText().toString().equals(ROUTE_ALERT_ADMIN_DEFAULT_PASSWORD)) {
                    startActivity(checkIntent);
                    finish();
                    editor.putString(ROUTE_ALERT_USER_CURRENT_CREDENTIALS, "ADMIN");

        } else {
            Toast.makeText(this, "Incorrect Credentials", Toast.LENGTH_SHORT).show();
        }
    } else {
        Toast.makeText(this, "Please Complete the Credentials", Toast.LENGTH_SHORT).show();
    }
}
catch (Exception e)
{
    //Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();

}

    }

    private void checkUserCredentials() {

        //ReadData from Api

        StringRequest request = new StringRequest(Request.Method.POST, ROUTE_ALERT_URL_sign_inView, new Response.Listener<String>() {
            @Override
            public void onResponse(final String s) {
                try {
                    if (!(msg_LoginUserEmail.getText().toString().trim().isEmpty()) && (!msg_LoginUserpassword.getText().toString().trim().isEmpty()))
                    {
//                        if (!(s.isEmpty()))
//                        {

                            final ProgressDialog progressDialog = new ProgressDialog(LoginUserActivity.this);


                            progressDialog.setTitle("Login in");
                            progressDialog.setMessage("Please Wait for a While...");
                            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                            progressDialog.show();
                            // notify user you are online
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    try
                                    {
                                        JSONObject object = new JSONObject(s);
                                        startActivity(checkIntent);
                                        //Set_sharedPreferences==================
                                        editor = Set_sharedPreferences.edit();
                                        editor.putString(ROUTE_ALERT_USER_ID_KEY, object.getString("User_ID")).apply();
                                        editor.putString(ROUTE_ALERT_USER_FIRST_NAME_KEY, object.getString("First_Name")).apply();
                                        editor.putString(ROUTE_ALERT_USER_LAST_NAME_KEY, object.getString("Last_Name")).apply();
                                        editor.putString(ROUTE_ALERT_EMAIL_ID_KEY, object.getString("Email_ID")).apply();
                                        editor.putString(ROUTE_ALERT_CONTACT_NUMBER_KEY, object.getString("Contact")).apply();
                                        editor.putString(ROUTE_ALERT_ID_PROOF_KEY, object.getString("ID_Proof")).apply();
                                        editor.putString(ROUTE_ALERT_PASSWORD_KEY, object.getString("Password")).apply();
                                        //Current Credentials---------------------------------------
                                        editor.putString(ROUTE_ALERT_USER_CURRENT_CREDENTIALS, "USER");

                                        progressDialog.dismiss();

                                    }
                                    catch (Exception e)
                                    {
                                        Toast.makeText(LoginUserActivity.this, "Wait to be Approved!", Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                    }
                                }
                            },4000);


//                        } else {
//                            Toast.makeText(LoginUserActivity.this, "Incorrect Credentials", Toast.LENGTH_SHORT).show();
//                        }
                    } else {
                        Toast.makeText(LoginUserActivity.this, "Please Complete the Credentials", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    Toast.makeText(LoginUserActivity.this, "Failed to login", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                //Toast.makeText(LoginUserActivity.this, "Invalid Credentials"+volleyError, Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<>();
                map.put(EMAIL_ID, msg_LoginUserEmail.getText().toString().trim());
                map.put(PASSWORD, msg_LoginUserpassword.getText().toString().trim());

                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(LoginUserActivity.this);
        queue.add(request);

    }

    //Getting Current Location----------------------------------------------------------------------

    @SuppressLint("SetTextI18n")
    public void showLocation(){

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if(ActivityCompat.checkSelfPermission(LoginUserActivity.this,android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            if(Build.VERSION.SDK_INT >= 23 &&
                    ActivityCompat.checkSelfPermission(LoginUserActivity.this,android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(LoginUserActivity.this,android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},REQUEST_CODE);

                return;
            }
        }

        try{

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,MIN_TIME,MIN_DISTANCE,listener);
        }
        catch(Exception e){

            //Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        double msg_Longitude = location.getLongitude();
        double msg_Latitude = location.getLatitude();

        try
        {
            editor=Set_sharedPreferences.edit();
            String lati = ""+msg_Latitude;
            String longi = ""+msg_Longitude;
            editor.putString(ROUTE_ALERT_USER_CURRENT_LOCATION_LAT, lati).apply();
            editor.putString(ROUTE_ALERT_USER_CURRENT_LOCATION_LNG, longi).apply();


        }catch (Exception e)
        {
            //Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    LocationListener listener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
//           //Toast.makeText(ActivityUserEmergencyHelp.this, "something changed", Toast.LENGTH_SHORT).show();
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

}
