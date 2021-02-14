package com.sda.syeddaniyalali.routealert;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.sda.syeddaniyalali.routealert.TemporaryAppData.ROUTE_ALERT_URL_NotificationView;

public class ViewUserNotificationActivity extends AppCompatActivity {

    ArrayList<Notification> msg_notifications_array;
    RecyclerViewUserNotificationAdapter msg_adapter;
    RecyclerView msg_recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user_notification);
        try {
            msg_notifications_array=new ArrayList<Notification>();
            msg_recyclerView=findViewById(R.id.recycler_view_user_notification);
            msg_recyclerView.setLayoutManager(new LinearLayoutManager(ViewUserNotificationActivity.this));

            getNotifications();

        }
        catch (Exception e)
        {
            Toast.makeText(this, ""+e.toString(), Toast.LENGTH_LONG).show();
        }

    }

    void getNotifications()
    {
        //Read from Api

        StringRequest request=new StringRequest(Request.Method.POST, ROUTE_ALERT_URL_NotificationView, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try
                {
                    JSONObject object=new JSONObject(s);
                    JSONArray array=object.getJSONArray("notification");
                    for(int i=0; i<array.length(); i++)
                    {
                        JSONObject jsonObject=array.getJSONObject(i);
                        msg_notifications_array.add(new Notification(jsonObject.getInt("Notification_ID"),
                                "Date: "+jsonObject.getString("Date"),
                                "Time: "+jsonObject.getString("Time"),
                                "Location: "+jsonObject.getString("Location"),
                                "Description: "+jsonObject.getString("Description")));

                        msg_adapter=new RecyclerViewUserNotificationAdapter(ViewUserNotificationActivity.this,msg_notifications_array);
                        msg_recyclerView.setAdapter(msg_adapter);

                    }

                }
                catch (Exception e)
                {
                    Toast.makeText(ViewUserNotificationActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                Toast.makeText(ViewUserNotificationActivity.this, ""+volleyError, Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue mRequestQueue= Volley.newRequestQueue(ViewUserNotificationActivity.this);
        mRequestQueue.add(request);
    }
}
