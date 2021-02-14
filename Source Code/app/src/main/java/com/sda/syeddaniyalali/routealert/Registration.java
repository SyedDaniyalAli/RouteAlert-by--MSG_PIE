package com.sda.syeddaniyalali.routealert;

public class Registration {

    String msg_User_ID;
    String msg_First_Name, msg_Last_Name, msg_Email_ID, msg_Contact, msg_ID_Proof, msg_Password, msg_Approval;

    public Registration(int msg_User_ID, String msg_First_Name, String msg_Last_Name, String mag_Email_ID, String msg_Contact, String msg_ID_Proof, String msg_Password) {

        this.msg_First_Name = msg_First_Name;
        this.msg_Last_Name = msg_Last_Name;
        this.msg_Email_ID = mag_Email_ID;
        this.msg_Contact = msg_Contact;
        this.msg_ID_Proof = msg_ID_Proof;
        this.msg_Password = msg_Password;
    }

    public Registration(String userid,String msg_First_Name, String msg_Last_Name, String mag_Email_ID, String msg_Contact, String msg_ID_Proof, String msg_Password, String msg_Approval) {

        this.msg_User_ID=userid;
        this.msg_First_Name = msg_First_Name;
        this.msg_Last_Name = msg_Last_Name;
        this.msg_Email_ID = mag_Email_ID;
        this.msg_Contact = msg_Contact;
        this.msg_ID_Proof = msg_ID_Proof;
        this.msg_Password = msg_Password;
        this.msg_Approval=msg_Approval;
    }




}
