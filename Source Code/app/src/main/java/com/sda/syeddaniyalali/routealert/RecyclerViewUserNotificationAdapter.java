package com.sda.syeddaniyalali.routealert;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerViewUserNotificationAdapter extends RecyclerView.Adapter<RecyclerViewUserNotificationAdapter.ViewHolder> {

    private ArrayList<Notification> msg_array;
    private Context msg_mcontext;

    RecyclerViewUserNotificationAdapter(Context context, ArrayList<Notification> array)
    {
        msg_mcontext=context;
        msg_array=array;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {

        View view= LayoutInflater.from(msg_mcontext).inflate(R.layout.view_notification_by_user_cardview, parent,false);
        return new RecyclerViewUserNotificationAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {

        viewHolder.msg_view_notfication_by_user_id.setText(""+msg_array.get(i).msg_Notification_ID);
        viewHolder.msg_view_notfication_by_location.setText(""+msg_array.get(i).msg_Location);
        viewHolder.msg_view_notfication_by_date.setText(""+msg_array.get(i).msg_Date);
        viewHolder.msg_view_notfication_by_time.setText(""+msg_array.get(i).msg_Time);
        viewHolder.msg_view_notfication_by_description.setText(""+msg_array.get(i).msg_Description);
    }

    @Override
    public int getItemCount() {
        return msg_array.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView msg_view_notfication_by_user_id, msg_view_notfication_by_location,
                msg_view_notfication_by_date, msg_view_notfication_by_time,
                msg_view_notfication_by_description;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            msg_view_notfication_by_user_id=itemView.findViewById(R.id.msg_view_notfication_by_user_id);
            msg_view_notfication_by_location=itemView.findViewById(R.id.msg_view_notfication_by_location);
            msg_view_notfication_by_date=itemView.findViewById(R.id.msg_view_notfication_by_date);
            msg_view_notfication_by_time=itemView.findViewById(R.id.msg_view_notfication_by_time);
            msg_view_notfication_by_description=itemView.findViewById(R.id.msg_view_notfication_by_description);
        }
    }
}