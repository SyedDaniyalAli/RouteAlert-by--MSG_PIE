package com.sda.syeddaniyalali.routealert;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerViewAdminEmergencyAdapter extends RecyclerView.Adapter<RecyclerViewAdminEmergencyAdapter.ViewHolder> {

    private ArrayList<EmergencyHelp> msg_array;
    private Context msg_mcontext;

    RecyclerViewAdminEmergencyAdapter(Context context, ArrayList<EmergencyHelp> array)
    {
        msg_mcontext=context;
        msg_array=array;
    }

    private int numbers=1;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(msg_mcontext).inflate(R.layout.fragment_admin_emergency_cardview, viewGroup,false);
        return new RecyclerViewAdminEmergencyAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        viewHolder.msg_admin_Emergency_text_id.setText("Alert# "+numbers++);
        viewHolder.msg_admin_Emergency_UserID.setText(""+msg_array.get(i).msg_User_ID);
        viewHolder.msg_admin_Emergency_Name.setText(msg_array.get(i).msg_User_Details_Name);
        viewHolder.msg_admin_Emergency_Location.setText(msg_array.get(i).msg_Location);
        viewHolder.msg_admin_Emergency_Address.setText(msg_array.get(i).msg_User_Details_Address);
        viewHolder.msg_admin_Emergency_Contact.setText(msg_array.get(i).msg_User_Details_Contact_No);

    }

    @Override
    public int getItemCount() {
        return msg_array.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView msg_admin_Emergency_text_id, msg_admin_Emergency_UserID, msg_admin_Emergency_Location,
                msg_admin_Emergency_Address, msg_admin_Emergency_Contact, msg_admin_Emergency_Name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            msg_admin_Emergency_text_id = itemView.findViewById(R.id.msg_admin_Emergency_text_id);
            msg_admin_Emergency_UserID = itemView.findViewById(R.id.msg_admin_Emergency_UserID);
            msg_admin_Emergency_Name = itemView.findViewById(R.id.msg_admin_Emergency_Name);
            msg_admin_Emergency_Location = itemView.findViewById(R.id.msg_admin_Emergency_Location);
            msg_admin_Emergency_Address = itemView.findViewById(R.id.msg_admin_Emergency_Address);
            msg_admin_Emergency_Contact = itemView.findViewById(R.id.msg_admin_Emergency_Contact);
        }
    }

}
