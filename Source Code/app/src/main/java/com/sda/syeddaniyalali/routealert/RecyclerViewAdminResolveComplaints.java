package com.sda.syeddaniyalali.routealert;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.sda.syeddaniyalali.routealert.TemporaryAppData.ROUTE_ALERT_URL_UPDATE_NOTIFICATION;
import static com.sda.syeddaniyalali.routealert.TemporaryAppData.ROUTE_ALERT_URL_UpdateNotification;

public class RecyclerViewAdminResolveComplaints extends RecyclerView.Adapter<RecyclerViewAdminResolveComplaints.ViewHolder> {


    private ArrayList<Complaints> msg_array;
    private ArrayList arrayListStatus=new ArrayList();
    private Context msg_mcontext;

    private int numbers=1;

    RecyclerViewAdminResolveComplaints(Context context, ArrayList<Complaints> array)
    {
        msg_mcontext=context;
        msg_array=array;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view= LayoutInflater.from(msg_mcontext).inflate(R.layout.view_admin_complain_cardview, viewGroup,false);
        return new RecyclerViewAdminResolveComplaints.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {

        viewHolder.msg_admin_Complaint_title.setText("Complaint# "+numbers++);
        viewHolder.msg_admin_Complaint_UserId.setText(msg_array.get(i).msg_User_ID+"");
        viewHolder.msg_admin_Complaint_ID.setText(msg_array.get(i).msg_Complaint_ID+"");
        viewHolder.msg_admin_Complaint_Title.setText(msg_array.get(i).msg_Title);
        viewHolder.msg_admin_Complaint_Desc.setText(msg_array.get(i).msg_Description);
        viewHolder.msg_admin_Complaint_Date.setText(msg_array.get(i).msg_Date);

        try
        {
            if(msg_array.get(i).msg_Status.equalsIgnoreCase("Approved"))
            {
                viewHolder.msg_admin_Complaint_ChangeStatus.setText("Approved");
                viewHolder.msg_admin_Complaint_ChangeStatus.setChecked(true);
            }
            else if(msg_array.get(i).msg_Status.equalsIgnoreCase("Denied"))
            {
                viewHolder.msg_admin_Complaint_ChangeStatus.setText("Denied");
                viewHolder.msg_admin_Complaint_ChangeStatus.setChecked(false);
            }
            else
            {
                viewHolder.msg_admin_Complaint_ChangeStatus.setText("Pending");
                viewHolder.msg_admin_Complaint_ChangeStatus.setChecked(false);
            }

            viewHolder.msg_admin_Complaint_ChangeStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if(isChecked)
                    {
                        approveUser(""+msg_array.get(i).msg_User_ID,"Approved");
                        viewHolder.msg_admin_Complaint_ChangeStatus.setText("Approved");

                    }
                    else
                    {
                        approveUser(""+msg_array.get(i).msg_User_ID,"Denied");
                        viewHolder.msg_admin_Complaint_ChangeStatus.setText("Denied");
                    }
                }
            });

        }
        catch (Exception e)
        {
            Toast.makeText(msg_mcontext, ""+e, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return msg_array.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView msg_admin_Complaint_UserId, msg_admin_Complaint_ID, msg_admin_Complaint_Title,
                msg_admin_Complaint_Desc, msg_admin_Complaint_Date,msg_admin_Complaint_title;

        Switch msg_admin_Complaint_ChangeStatus;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            msg_admin_Complaint_title = itemView.findViewById(R.id.msg_admin_Complaint_title);
            msg_admin_Complaint_UserId = itemView.findViewById(R.id.msg_admin_Complaint_UserId);
            msg_admin_Complaint_ID = itemView.findViewById(R.id.msg_admin_Complaint_ID);
            msg_admin_Complaint_Title = itemView.findViewById(R.id.msg_admin_Complaint_Title);
            msg_admin_Complaint_Desc = itemView.findViewById(R.id.msg_admin_Complaint_Desc);
            msg_admin_Complaint_Date = itemView.findViewById(R.id.msg_admin_Complaint_Date);
            msg_admin_Complaint_ChangeStatus = itemView.findViewById(R.id.msg_admin_Complaint_ChangeStatus);

            msg_admin_Complaint_ChangeStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                }
            });

        }
    }

    private void approveUser(final String ID, final String Status)
    {
        try
        {
            //Save to API

            StringRequest request=new StringRequest(Request.Method.POST, ROUTE_ALERT_URL_UPDATE_NOTIFICATION, new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    Toast.makeText(msg_mcontext, ""+s, Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Toast.makeText(msg_mcontext, ""+volleyError, Toast.LENGTH_SHORT).show();
                }
            }){

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> map=new HashMap<>();
                    map.put("User_ID",ID);
                    map.put("Status",Status);

                    return map;
                }
            };

            RequestQueue queue= Volley.newRequestQueue(msg_mcontext);
            queue.add(request);

        }
        catch (Exception e)
        {
            Toast.makeText(msg_mcontext, ""+e, Toast.LENGTH_SHORT).show();
        }


    }

}
