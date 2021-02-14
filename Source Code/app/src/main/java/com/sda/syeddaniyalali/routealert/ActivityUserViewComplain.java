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

import static com.sda.syeddaniyalali.routealert.TemporaryAppData.ROUTE_ALERT_URL_ComplaintView;

public class ActivityUserViewComplain extends AppCompatActivity {

    RecyclerView msg_Recycler_Complain_Status;
    RecyclerViewUserComplaintStatus msg_viewUserComplaintStatus;
    private ArrayList<Complaints> msg_array=new ArrayList<Complaints>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_complain);
    try {
        msg_Recycler_Complain_Status = findViewById(R.id.msg_Recycler_Complain_Status);
        msg_Recycler_Complain_Status.setLayoutManager(new LinearLayoutManager(this));

        viewUserComplaintStatus();
    }
    catch (Exception e)
    {
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
    }
    }

    private void viewUserComplaintStatus()
    {
        //Read from Api
        StringRequest request=new StringRequest(Request.Method.POST, ROUTE_ALERT_URL_ComplaintView, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try
                {
                    JSONObject object=new JSONObject(s);
                    JSONArray array=object.getJSONArray("complaint");
                    for(int i=0; i<array.length(); i++)
                    {
                        JSONObject jsonObject=array.getJSONObject(i);
                        msg_array.add(new Complaints(jsonObject.getInt("User_ID"),jsonObject.getInt("Complaint_ID"),
                                jsonObject.getString("Title"),jsonObject.getString("Complaint_category"),
                                jsonObject.getString("Description"),jsonObject.getString("Date"),
                                jsonObject.getString("Status")));

                        msg_viewUserComplaintStatus=new RecyclerViewUserComplaintStatus(ActivityUserViewComplain.this,msg_array);
                        msg_Recycler_Complain_Status.setAdapter(msg_viewUserComplaintStatus);
                    }

                }
                catch (Exception e)
                {
                    //Toast.makeText(ActivityUserViewComplain.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                Toast.makeText(ActivityUserViewComplain.this, "No Internet", Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue queue= Volley.newRequestQueue(this);
        queue.add(request);
    }
}
