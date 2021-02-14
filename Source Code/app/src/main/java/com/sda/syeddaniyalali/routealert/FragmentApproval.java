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

import static com.sda.syeddaniyalali.routealert.TemporaryAppData.ROUTE_ALERT_URL_RegistrationView;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentApproval extends Fragment {

    ArrayList<Registration> msg_reg_array = new ArrayList<Registration>();
    RecyclerViewAdminApprovals recyclerViewAdminApprovals;
    RecyclerView msg_Recycler_Approvals;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_fragment_approval, container, false);


        try
        {

            msg_Recycler_Approvals = view.findViewById(R.id.msg_Recycler_Approvals);
            msg_Recycler_Approvals.setLayoutManager(new LinearLayoutManager(getContext()));

            getApprovals();
        }
        catch (Exception e)
        {
            Toast.makeText(getActivity(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }



        return view;
    }

    private void getApprovals() {

        //Read from Api

        StringRequest request=new StringRequest(Request.Method.POST, ROUTE_ALERT_URL_RegistrationView, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try
                {
                    JSONObject object=new JSONObject(s);
                    JSONArray array=object.getJSONArray("registration");
                    for(int i=0; i<array.length(); i++)
                    {
                        JSONObject jsonObject=array.getJSONObject(i);
                        msg_reg_array.add(new Registration(jsonObject.getString("User_ID"),jsonObject.getString("First_Name"),
                                jsonObject.getString("Last_Name"), jsonObject.getString("Email_ID"),
                                jsonObject.getString("Contact"),jsonObject.getString("ID_Proof"),
                                jsonObject.getString("Password"),
                                jsonObject.getString("Approval")));

                        recyclerViewAdminApprovals=new RecyclerViewAdminApprovals(getActivity(), msg_reg_array);
                        msg_Recycler_Approvals.setAdapter(recyclerViewAdminApprovals);
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

                Toast.makeText(getContext(), "Please Connect to Internet", Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue queue= Volley.newRequestQueue(Objects.requireNonNull(getContext()).getApplicationContext());
        queue.add(request);

    }

}
