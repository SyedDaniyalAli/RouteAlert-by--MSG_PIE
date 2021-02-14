package com.sda.syeddaniyalali.routealert;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerViewUserComplaintStatus extends RecyclerView.Adapter<RecyclerViewUserComplaintStatus.ViewHolder> {

    private ArrayList<Complaints> msg_array;
    private Context msg_mcontext;
    private  int numbers=1;

    RecyclerViewUserComplaintStatus(Context context, ArrayList<Complaints> array)
    {
        msg_mcontext=context;
        msg_array=array;
    }

    @NonNull
    @Override
    public RecyclerViewUserComplaintStatus.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {

        View view= LayoutInflater.from(msg_mcontext).inflate(R.layout.view_user_complain_status_cardview, parent,false);
        return new RecyclerViewUserComplaintStatus.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        viewHolder.msg_User_Complaint_title.setText("Complaint# "+numbers++);
        viewHolder.msg_User_User_id.setText(""+msg_array.get(i).msg_User_ID);
        viewHolder.msg_user_complain_id.setText(""+msg_array.get(i).msg_Complaint_ID);

        if(msg_array.get(i).msg_Status.equals("Approved"))
        {
            viewHolder.msg_user_complain_status.setBackgroundColor(Color.GREEN);
            viewHolder.msg_user_complain_status.setText("Approved");
        }
        else if(msg_array.get(i).msg_Status.equals("Pending"))
        {
            viewHolder.msg_user_complain_status.setBackgroundColor(Color.BLUE);
            viewHolder.msg_user_complain_status.setTextColor(Color.WHITE);
            viewHolder.msg_user_complain_status.setText("Pending");
        }
        else
        {
            viewHolder.msg_user_complain_status.setBackgroundColor(Color.RED);
            viewHolder.msg_user_complain_status.setText("Denied");
        }

        viewHolder.msg_user_Complaint_title.setText("Title: "+msg_array.get(i).msg_Title);
        viewHolder.msg_user_Complaint_Category.setText("Category: "+msg_array.get(i).msg_Complaint_Category);
        viewHolder.msg_user_Complaint_Desc.setText("Description: "+msg_array.get(i).msg_Description);
        viewHolder.msg_user_Complaint_Date.setText("Date: "+msg_array.get(i).msg_Date);

    }

    @Override
    public int getItemCount() {
        return msg_array.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView msg_User_User_id, msg_user_complain_id, msg_user_complain_status,
                msg_user_Complaint_title, msg_user_Complaint_Category, msg_user_Complaint_Desc,
                msg_user_Complaint_Date,msg_User_Complaint_title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            msg_User_User_id=itemView.findViewById(R.id.msg_User_User_id);
            msg_user_complain_id=itemView.findViewById(R.id.msg_user_complain_id);
            msg_user_complain_status=itemView.findViewById(R.id.msg_user_complain_status);
            msg_user_Complaint_title=itemView.findViewById(R.id.msg_user_Complaint_title);
            msg_user_Complaint_Category=itemView.findViewById(R.id.msg_user_Complaint_Category);
            msg_user_Complaint_Desc=itemView.findViewById(R.id.msg_user_Complaint_Desc);
            msg_user_Complaint_Date=itemView.findViewById(R.id.msg_user_Complaint_Date);
            msg_User_Complaint_title=itemView.findViewById(R.id.msg_User_Complaint_title);
        }
    }
}
