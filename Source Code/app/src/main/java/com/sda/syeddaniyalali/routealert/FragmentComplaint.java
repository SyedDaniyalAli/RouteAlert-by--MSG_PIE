package com.sda.syeddaniyalali.routealert;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import static com.sda.syeddaniyalali.routealert.TemporaryAppData.ROUTE_ALERT_URL_Complaint;
import static com.sda.syeddaniyalali.routealert.TemporaryAppData.ROUTE_ALERT_URL_ComplaintView;

public class FragmentComplaint extends Fragment {

    RecyclerView msg_rv_admin_view_complaints;
    ArrayList<Complaints> msg_array=new ArrayList<Complaints>();
    RecyclerViewAdminResolveComplaints msg_recyclerViewAdminResolveComplaints;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_fragment_complaint,container,false);

            msg_rv_admin_view_complaints=view.findViewById(R.id.msg_rv_admin_view_complaints);
            msg_rv_admin_view_complaints.setLayoutManager(new LinearLayoutManager(getContext()));

            viewUserComplaintStatus();



        return view;
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

                        msg_recyclerViewAdminResolveComplaints = new RecyclerViewAdminResolveComplaints(getActivity(),msg_array);
                        msg_rv_admin_view_complaints.setAdapter(msg_recyclerViewAdminResolveComplaints);
                    }

                }
                catch (Exception e)
                {
                    //Toast.makeText(getActivity(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                Toast.makeText(getActivity(), "No Internet", Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue queue= Volley.newRequestQueue(Objects.requireNonNull(getActivity()).getApplicationContext());
        queue.add(request);
    }
}
