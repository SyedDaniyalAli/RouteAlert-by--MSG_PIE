package com.sda.syeddaniyalali.routealert;

public class Feedback {

    int msg_User_ID, msg_Feedback_ID;
    String msg_Description, msg_Date;

    public Feedback(int msg_User_ID, int msg_Feedback_ID, String msg_Description, String msg_Date) {
        this.msg_User_ID = msg_User_ID;
        this.msg_Feedback_ID = msg_Feedback_ID;
        this.msg_Description = msg_Description;
        this.msg_Date = msg_Date;
    }
}
