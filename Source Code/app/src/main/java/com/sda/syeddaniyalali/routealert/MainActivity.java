package com.sda.syeddaniyalali.routealert;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.sda.syeddaniyalali.routealert.R;

import static com.sda.syeddaniyalali.routealert.TemporaryAppData.ROUTE_ALERT_USER_CURRENT_CREDENTIALS;
import static com.sda.syeddaniyalali.routealert.TemporaryAppData.ROUTE_ALERT_USER_ID_KEY;
import static com.sda.syeddaniyalali.routealert.TemporaryAppData.ROUTE_ALERT_USER_SharedPreference;

public class MainActivity extends AppCompatActivity {

    LinearLayout view_user_notificaion, view_user_Submit_Complain, view_user_Check_Status, view_user_Emergency, view_user_Feedback,
            view_user_Logout;
//
//    SharedPreferences Set_sharedPreferences;
//    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {

            FirebaseApp.initializeApp(MainActivity.this);
            FirebaseInstanceId.getInstance().getToken();
            FirebaseMessaging.getInstance().subscribeToTopic("User");

            //Set_sharedPreferences = getSharedPreferences(ROUTE_ALERT_USER_SharedPreference, Context.MODE_PRIVATE);

        }
        catch (Exception e)
        {
            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        view_user_notificaion =findViewById(R.id.view_user_notificaion);
        view_user_Submit_Complain =findViewById(R.id.view_user_Submit_Complain);
        view_user_Check_Status = findViewById(R.id.view_user_Check_Status);
        view_user_Emergency =findViewById(R.id.view_user_Emergency);
        view_user_Feedback =findViewById(R.id.view_user_Feedback);
        view_user_Logout =findViewById(R.id.view_user_Logout);


        view_user_notificaion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,ViewUserNotificationActivity.class);
                startActivity(intent);
            }
        });

        view_user_Submit_Complain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,ActivityUserSubmitComplains.class);
                startActivity(intent);
            }
        });

        view_user_Check_Status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,ActivityUserViewComplain.class);
                startActivity(intent);
            }
        });

        view_user_Emergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,ActivityUserEmergencyHelp.class);
                startActivity(intent);
            }
        });

        view_user_Feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,ActivityUserFeedback.class);
                startActivity(intent);
            }
        });

        view_user_Logout.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("SetTextI18n")
            public void onClick(View v) {
                try {
                   // editor.putString(ROUTE_ALERT_USER_CURRENT_CREDENTIALS, "");
                    Intent intent=new Intent(MainActivity.this, LoginUserActivity.class);
                    Toast.makeText(MainActivity.this, "Logout Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(intent);

                }
                catch (Exception e)
                {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}