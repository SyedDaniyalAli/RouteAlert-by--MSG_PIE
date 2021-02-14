package com.sda.syeddaniyalali.routealert;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
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
import java.util.Objects;

import static com.sda.syeddaniyalali.routealert.TemporaryAppData.ROUTE_ALERT_URL_ComplaintView;
import static com.sda.syeddaniyalali.routealert.TemporaryAppData.ROUTE_ALERT_URL_FeedBackView;
import static com.sda.syeddaniyalali.routealert.TemporaryAppData.ROUTE_ALERT_URL_NotificationView;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentReport extends Fragment {

    RecyclerView msg_Recycler_Admin_GenerateReport;
    Spinner msg_Report_Categories;

    ArrayList<Feedback> msg_array_Feedback=new ArrayList<Feedback>();
    RecyclerViewAdminFeedBackAdapter msg_recyclerViewAdminFeedBack;

    ArrayList<Complaints> msg_array_Complaints=new ArrayList<Complaints>();
    RecyclerViewAdminResolveComplaints msg_recyclerViewAdminResolveComplaints;

    ArrayList<Notification> msg_notifications_array=new ArrayList<Notification>();
    RecyclerViewUserNotificationAdapter msg_adapter;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_fragment_report, container, false);
        msg_Recycler_Admin_GenerateReport=view.findViewById(R.id.msg_Recycler_Admin_GenerateReport);
        msg_Report_Categories = view.findViewById(R.id.msg_Report_Categories);
        msg_Recycler_Admin_GenerateReport.setLayoutManager(new LinearLayoutManager(getContext()));


        msg_Report_Categories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(msg_Report_Categories.getSelectedItem().equals("Complaint Summary"))
                {
                    msg_recyclerViewAdminResolveComplaints = new RecyclerViewAdminResolveComplaints(getActivity(),msg_array_Complaints);
                    viewUserComplaintStatus();
                }
                else if(msg_Report_Categories.getSelectedItem().equals("Daily FeedBacks"))
                {
                    msg_recyclerViewAdminFeedBack=new RecyclerViewAdminFeedBackAdapter(getActivity(),msg_array_Feedback);
                    getFeedbackData();
                }
                else if(msg_Report_Categories.getSelectedItem().equals("Notification Summary"))
                {
                    msg_adapter=new RecyclerViewUserNotificationAdapter(getActivity(),msg_notifications_array);
                    getNotifications();
                    Toast.makeText(getActivity(), ""+msg_Report_Categories.getSelectedItem(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getContext(), "Select Category", Toast.LENGTH_SHORT).show();
            }
        });




        return view;
    }

    private void getFeedbackData() {

        //Read from Api

        StringRequest request=new StringRequest(Request.Method.POST, ROUTE_ALERT_URL_FeedBackView, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try
                {
                    JSONObject object=new JSONObject(s);
                    JSONArray array=object.getJSONArray("feedback");
                    for(int i=0; i<array.length(); i++)
                    {
                        JSONObject jsonObject=array.getJSONObject(i);
                        msg_array_Feedback.add(new Feedback(jsonObject.getInt("User_ID"),
                                jsonObject.getInt("Feedback_ID"), jsonObject.getString("Descripion"),
                                jsonObject.getString("Date")));

                        msg_Recycler_Admin_GenerateReport.setAdapter(msg_recyclerViewAdminFeedBack);
                    }

                }
                catch (Exception e)
                {
                    Toast.makeText(getContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                Toast.makeText(getContext(), ""+volleyError, Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue queue= Volley.newRequestQueue(Objects.requireNonNull(getContext()).getApplicationContext());
        queue.add(request);

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
                        msg_array_Complaints.add(new Complaints(jsonObject.getInt("User_ID"),jsonObject.getInt("Complaint_ID"),
                                jsonObject.getString("Title"),jsonObject.getString("Complaint_category"),
                                jsonObject.getString("Description"),jsonObject.getString("Date"),
                                jsonObject.getString("Status")));


                        msg_Recycler_Admin_GenerateReport.setAdapter(msg_recyclerViewAdminResolveComplaints);
                    }

                }
                catch (Exception e)
                {
                    Toast.makeText(getActivity(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                Toast.makeText(getActivity(), ""+volleyError, Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue queue= Volley.newRequestQueue(Objects.requireNonNull(getActivity()).getApplicationContext());
        queue.add(request);
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

                        msg_Recycler_Admin_GenerateReport.setAdapter(msg_adapter);

                    }

                }
                catch (Exception e)
                {
                    Toast.makeText(getActivity(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                Toast.makeText(getActivity(), ""+volleyError, Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue mRequestQueue= Volley.newRequestQueue(Objects.requireNonNull(getActivity()).getApplicationContext());
        mRequestQueue.add(request);
    }

}
