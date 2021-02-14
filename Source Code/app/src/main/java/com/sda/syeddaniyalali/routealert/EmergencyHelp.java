package com.sda.syeddaniyalali.routealert;

public class EmergencyHelp {

    int msg_User_ID, msg_Help_ID;
    String msg_Location, msg_User_Details_Name, msg_User_Details_Address, msg_User_Details_Contact_No;

    public EmergencyHelp(int msg_User_ID, int msg_Help_ID, String msg_Location, String msg_User_Details_Name, String msg_User_Details_Address, String msg_User_Details_Contact_No) {
        this.msg_User_ID = msg_User_ID;
        this.msg_Help_ID = msg_Help_ID;
        this.msg_Location = msg_Location;
        this.msg_User_Details_Name = msg_User_Details_Name;
        this.msg_User_Details_Address = msg_User_Details_Address;
        this.msg_User_Details_Contact_No = msg_User_Details_Contact_No;
    }
}
