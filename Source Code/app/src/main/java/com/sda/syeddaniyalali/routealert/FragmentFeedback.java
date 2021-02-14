package com.sda.syeddaniyalali.routealert;


import android.os.Bundle;
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

import static com.sda.syeddaniyalali.routealert.TemporaryAppData.ROUTE_ALERT_URL_FeedBackView;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentFeedback extends Fragment {

    RecyclerView msg_rv_admin_view_FeedBack;
    ArrayList<Feedback> msg_array=new ArrayList<Feedback>();
    RecyclerViewAdminFeedBackAdapter msg_recyclerViewAdminFeedBack;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_fragment_feedback, container, false);
        msg_rv_admin_view_FeedBack=view.findViewById(R.id.msg_Recycler_Feedback);

        msg_rv_admin_view_FeedBack.setLayoutManager(new LinearLayoutManager(getContext()));
        msg_recyclerViewAdminFeedBack=new RecyclerViewAdminFeedBackAdapter(getContext(),msg_array);


        getFeedbackData();

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
                        msg_array.add(new Feedback(jsonObject.getInt("User_ID"),
                                jsonObject.getInt("Feedback_ID"), jsonObject.getString("Descripion"),
                                jsonObject.getString("Date")));

                        msg_rv_admin_view_FeedBack.setAdapter(msg_recyclerViewAdminFeedBack);
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

}

