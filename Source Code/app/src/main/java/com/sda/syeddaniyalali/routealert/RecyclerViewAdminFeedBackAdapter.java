package com.sda.syeddaniyalali.routealert;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerViewAdminFeedBackAdapter extends RecyclerView.Adapter<RecyclerViewAdminFeedBackAdapter.ViewHolder> {

    private ArrayList<Feedback> msg_array;
    private Context msg_mcontext;
    private int numbers=1;

    RecyclerViewAdminFeedBackAdapter(Context context, ArrayList<Feedback> array)
    {
        msg_mcontext=context;
        msg_array=array;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view= LayoutInflater.from(msg_mcontext).inflate(R.layout.admin_checkfeedback_cardview, viewGroup,false);

        return new RecyclerViewAdminFeedBackAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        viewHolder.msg_admin_Feedback_Title.setText("FeedBack ID:"+numbers++);
        viewHolder.msg_admin_Feedback_UserID.setText(""+msg_array.get(i).msg_User_ID);
        viewHolder.msg_admin_Feedback_FeedbackID.setText(""+msg_array.get(i).msg_Feedback_ID);
        viewHolder.msg_admin_Feedback_Desc.setText(msg_array.get(i).msg_Description);
        viewHolder.msg_admin_Feedback_Date.setText(msg_array.get(i).msg_Date);
    }

    @Override
    public int getItemCount() {
        return msg_array.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView msg_admin_Feedback_Title, msg_admin_Feedback_UserID,
                msg_admin_Feedback_FeedbackID, msg_admin_Feedback_Desc,
                msg_admin_Feedback_Date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            msg_admin_Feedback_Title = itemView.findViewById(R.id.msg_admin_Feedback_Title);
            msg_admin_Feedback_UserID = itemView.findViewById(R.id.msg_admin_Feedback_UserID);
            msg_admin_Feedback_FeedbackID = itemView.findViewById(R.id.msg_admin_Feedback_FeedbackID);
            msg_admin_Feedback_Desc = itemView.findViewById(R.id.msg_admin_Feedback_Desc);
            msg_admin_Feedback_Date = itemView.findViewById(R.id.msg_admin_Feedback_Date);

        }
    }
}
