package com.sda.syeddaniyalali.routealert;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
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

import static com.sda.syeddaniyalali.routealert.TemporaryAppData.ROUTE_ALERT_URL_SEND_EMAIL;
import static com.sda.syeddaniyalali.routealert.TemporaryAppData.ROUTE_ALERT_URL_UpdateNotification;

public class RecyclerViewAdminApprovals extends RecyclerView.Adapter<RecyclerViewAdminApprovals.ViewHolder> {

    private ArrayList<Registration> msg_array;
    private Context msg_mcontext;

    private static String UserEmail;

    private int numbers=1;

    RecyclerViewAdminApprovals(Context context, ArrayList<Registration> array)
    {
        msg_mcontext=context;
        msg_array=array;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(msg_mcontext).inflate(R.layout.view_admin_approvals_cardview, viewGroup,false);
        return new RecyclerViewAdminApprovals.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, @SuppressLint("RecyclerView") final int i) {

        viewHolder.msg_Admin_Approvals_Title.setText("Approval: "+numbers++);
        viewHolder.msg_Admin_UserID.setText(""+msg_array.get(i).msg_User_ID);
        viewHolder.msg_Admin_FirstName.setText(msg_array.get(i).msg_First_Name);
        viewHolder.msg_Admin_LastName.setText(msg_array.get(i).msg_Last_Name);
        viewHolder.msg_Admin_EmailID.setText(msg_array.get(i).msg_Email_ID);
        viewHolder.msg_Admin_Contact.setText(msg_array.get(i).msg_Contact);
        viewHolder.msg_Admin_IDProof.setText(msg_array.get(i).msg_ID_Proof);

        try
        {
            if(msg_array.get(i).msg_Approval.equalsIgnoreCase("Approved"))
            {
                viewHolder.msg_Admin_Switch.setText("Approved");
                viewHolder.msg_Admin_Switch.setChecked(true);
            }
            else if(msg_array.get(i).msg_Approval.equalsIgnoreCase("Denied"))
            {
                viewHolder.msg_Admin_Switch.setText("Denied");
                viewHolder.msg_Admin_Switch.setChecked(false);
            }
            else
            {
                viewHolder.msg_Admin_Switch.setText("Pending");
                viewHolder.msg_Admin_Switch.setChecked(false);
            }


            viewHolder.msg_Admin_Switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                    if(isChecked)
                    {
                        approveUser(""+msg_array.get(i).msg_Email_ID,"Approved", i);
                        viewHolder.msg_Admin_Switch.setText("Approved");
                        sendEmail(msg_array.get(i).msg_Email_ID,"Congratulations","You have been Activated Successfully Now you can login..");
                    }
                    else
                    {
                        approveUser(""+msg_array.get(i).msg_Email_ID,"Denied", i);
                        viewHolder.msg_Admin_Switch.setText("Denied");
                        sendEmail(msg_array.get(i).msg_Email_ID,"Confirmation","Your information is not correct as our needs Sorry... Try Again");

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
        TextView msg_Admin_Approvals_Title, msg_Admin_UserID,
                msg_Admin_FirstName, msg_Admin_LastName, msg_Admin_EmailID,
                msg_Admin_Contact, msg_Admin_IDProof;

        Switch msg_Admin_Switch;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            msg_Admin_Approvals_Title = itemView.findViewById(R.id.msg_Admin_Approvals_Title);
            msg_Admin_UserID = itemView.findViewById(R.id.msg_Admin_UserID);
            msg_Admin_FirstName = itemView.findViewById(R.id.msg_Admin_FirstName);
            msg_Admin_LastName = itemView.findViewById(R.id.msg_Admin_LastName);
            msg_Admin_EmailID = itemView.findViewById(R.id.msg_Admin_EmailID);
            msg_Admin_Contact = itemView.findViewById(R.id.msg_Admin_Contact);
            msg_Admin_IDProof = itemView.findViewById(R.id.msg_Admin_IDProof);
            msg_Admin_Switch = itemView.findViewById(R.id.msg_Admin_Switch);




        }

    }

    private void approveUser(final String email, final String Status, final int i)
    {
        try
        {
            //Save to API

            StringRequest request=new StringRequest(Request.Method.POST, ROUTE_ALERT_URL_UpdateNotification, new Response.Listener<String>() {
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
                    map.put("Email_ID",email.trim());
                    map.put("Status",Status);
                    UserEmail = msg_array.get(i).msg_Email_ID;

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

    //SendEmail-------------------------------------------------------------------------------------
    private void sendEmail(String To,String subject,String msg)
    {
        StringRequest request=new StringRequest(Request.Method.GET, ROUTE_ALERT_URL_SEND_EMAIL+"to="+To+"&subject="+subject+"&msg="+msg, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Toast.makeText(msg_mcontext, "Email Sent SuccessFully", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(msg_mcontext, "Email Not Sent", Toast.LENGTH_SHORT).show();
            }
        });


        RequestQueue queue= Volley.newRequestQueue(msg_mcontext);
        queue.add(request);

    }

}
