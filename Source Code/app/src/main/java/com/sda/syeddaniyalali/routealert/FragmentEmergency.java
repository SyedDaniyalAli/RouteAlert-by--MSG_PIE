package com.sda.syeddaniyalali.routealert;

import android.content.Context;
import android.net.Uri;
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

import static com.sda.syeddaniyalali.routealert.TemporaryAppData.ROUTE_ALERT_URL_EmergencyView;


public class FragmentEmergency extends Fragment {

    RecyclerView msg_Recycler_Emergency;
    ArrayList<EmergencyHelp> msg_array=new ArrayList<EmergencyHelp>();
    RecyclerViewAdminEmergencyAdapter msg_recyclerViewAdminEmergency;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_fragment_emergency,container,false);
        msg_Recycler_Emergency = view.findViewById(R.id.msg_Recycler_Emergency);
        msg_Recycler_Emergency.setLayoutManager(new LinearLayoutManager(getContext()));
        msg_recyclerViewAdminEmergency=new RecyclerViewAdminEmergencyAdapter(getContext(),msg_array);


        getEmergencyData();

        return view;
    }

    private void getEmergencyData() {

        //Read from Api

        StringRequest request=new StringRequest(Request.Method.POST, ROUTE_ALERT_URL_EmergencyView, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try
                {
                    JSONObject object=new JSONObject(s);
                    JSONArray array=object.getJSONArray("emergency");
                    for(int i=0; i<array.length(); i++)
                    {
                        JSONObject jsonObject=array.getJSONObject(i);
                        msg_array.add(new EmergencyHelp(jsonObject.getInt("User_ID"),
                                jsonObject.getInt("Help_ID"), jsonObject.getString("Location"),
                                jsonObject.getString("Detail_Name"),
                                jsonObject.getString("Detail_Address"), jsonObject.getString("Detail_Contact")));

                        msg_Recycler_Emergency.setAdapter(msg_recyclerViewAdminEmergency);
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
