package com.sda.syeddaniyalali.routealert;

public class Complaints {

    int msg_User_ID, msg_Complaint_ID;
    String msg_Title, msg_Complaint_Category, msg_Description, msg_Date, msg_Status;

    public Complaints(int msg_User_ID, int msg_Complaint_ID, String msg_Title, String msg_Complaint_Category, String msg_Description, String msg_Date, String msg_Status) {
        this.msg_User_ID = msg_User_ID;
        this.msg_Complaint_ID = msg_Complaint_ID;
        this.msg_Title = msg_Title;
        this.msg_Complaint_Category = msg_Complaint_Category;
        this.msg_Description = msg_Description;
        this.msg_Date = msg_Date;
        this.msg_Status = msg_Status;
    }
}
