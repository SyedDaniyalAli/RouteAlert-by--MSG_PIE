package com.sda.syeddaniyalali.routealert;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONObject;

import static com.sda.syeddaniyalali.routealert.TemporaryAppData.ROUTE_ALERT_URL_SendNotification;
import static com.sda.syeddaniyalali.routealert.TemporaryAppData.ROUTE_ALERT_USER_CURRENT_CREDENTIALS;
import static com.sda.syeddaniyalali.routealert.TemporaryAppData.ROUTE_ALERT_USER_SharedPreference;

public class ActivityMainAdmin extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener  {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.getDrawerArrowDrawable().setColor(Color.WHITE);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Fragment fragment=new FragmentNotification();
        displaySelecteFragment(fragment);

        //Initialize FireBaseApp--------------------------------------------------------------------
            try {

                FirebaseApp.initializeApp(ActivityMainAdmin.this);
                FirebaseInstanceId.getInstance().getToken();
                FirebaseMessaging.getInstance().subscribeToTopic("Admin");
                preferences = getSharedPreferences(ROUTE_ALERT_USER_SharedPreference, Context.MODE_PRIVATE);


            }
            catch (Exception e)
            {
                Toast.makeText(ActivityMainAdmin.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        //------------------------------------------------------------------------------------------

        }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main_admin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        /*int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressLint({"CutPasteId", "SetTextI18n"})
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        if (id == R.id.msg_nav_Notification)
        {
            fragment=new FragmentNotification();
            displaySelecteFragment(fragment);

        } else if (id == R.id.msg_nav_Complaint) {

            fragment=new FragmentComplaint();
            displaySelecteFragment(fragment);

        } else if (id == R.id.msg_nav_Emergency) {

            fragment=new FragmentEmergency();
            displaySelecteFragment(fragment);

        } else if (id == R.id.msg_nav_Report) {

            fragment=new FragmentReport();
            displaySelecteFragment(fragment);

        }
        else if (id == R.id.msg_nav_Approval) {

            fragment=new FragmentApproval();
            displaySelecteFragment(fragment);

        }else if (id == R.id.msg_nav_Feedback) {

            fragment=new FragmentFeedback();
            displaySelecteFragment(fragment);

        } else if (id == R.id.msg_nav_ChangePass) {

            fragment=new FragmentChangePassword();
            displaySelecteFragment(fragment);

        }else if (id == R.id.msg_nav_Logout) {

            try {

               AlertDialog.Builder builder=new AlertDialog.Builder(this);
               builder.setTitle("Logout Confirmation");
               builder.setMessage("Are you sure??");

               builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       dialog.cancel();
                   }
               });

               builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       Intent intent=new Intent(ActivityMainAdmin.this,LoginUserActivity.class);
                       editor.putString(ROUTE_ALERT_USER_CURRENT_CREDENTIALS, "");
                       startActivity(intent);
                   }
               });
                builder.show();
            }
            catch (Exception e)
            {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void displaySelecteFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_content,fragment);
        fragmentTransaction.commit();
    }


}
