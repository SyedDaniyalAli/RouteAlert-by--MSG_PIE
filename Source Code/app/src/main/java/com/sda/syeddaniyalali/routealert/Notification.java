package com.sda.syeddaniyalali.routealert;

public class Notification {

    int msg_Notification_ID;
    String msg_Date, msg_Time, msg_Location, msg_Description;

    public Notification(int msg_Notification_ID, String msg_Date, String msg_Time, String msg_Location, String msg_Description) {
        this.msg_Notification_ID = msg_Notification_ID;
        this.msg_Date = msg_Date;
        this.msg_Time = msg_Time;
        this.msg_Location = msg_Location;
        this.msg_Description = msg_Description;
    }
}
