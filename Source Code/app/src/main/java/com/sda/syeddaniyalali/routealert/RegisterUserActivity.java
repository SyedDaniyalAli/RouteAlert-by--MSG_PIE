package com.sda.syeddaniyalali.routealert;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.sda.syeddaniyalali.routealert.TemporaryAppData.ROUTE_ALERT_URL_Registration;

public class RegisterUserActivity extends AppCompatActivity {

    //Static Fields---------------------------------------------------------------------------------

    private final static String FIRST_NAME = "First_Name";
    private final static String LAST_NAME = "Last_Name";
    private final static String EMAIL_ID = "Email_ID";
    private final static String CONTACT = "Contact";
    private final static String ID_PROOF = "ID_Proof";
    private final static String PASSWORD = "Password";
    private final static String APPROVAL = "Approval";

    private final static String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    Pattern msg_Cnic = Pattern.compile("\\d{5}-\\d{7}");
    Matcher matcher;

    //Declaring-------------------------------------------------------------------------------------
    EditText msg_RegisterUserName, msg_RegisterUserLastName, msg_RegisterUserEmail,
            msg_RegisterUserContact, msg_RegisterUserCnic, msg_RegisterUserpassword,
            msg_RegisterUserConfirmpassword;

    Button msg_RegisterUserSignUpBtn;
    TextView msg_RegisterUserLinkLogin;

    Registration registration;


    //Calling on Create-----------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        //Initializing------------------------------------------------------------------------------

        //EditTexts==================
        msg_RegisterUserName =findViewById(R.id.msg_RegisterUserName);
        msg_RegisterUserLastName =findViewById(R.id.msg_RegisterUserLastName);
        msg_RegisterUserEmail =findViewById(R.id.msg_RegisterUserEmail);
        msg_RegisterUserContact =findViewById(R.id.msg_RegisterUserContact);
        msg_RegisterUserCnic =findViewById(R.id.msg_RegisterUserCnic);
        msg_RegisterUserpassword =findViewById(R.id.msg_RegisterUserpassword);
        msg_RegisterUserConfirmpassword =findViewById(R.id.msg_RegisterUserConfirmpassword);

        //Buttons==================
        msg_RegisterUserSignUpBtn =findViewById(R.id.msg_RegisterUserSignUpBtn);

        //TextView==================
        msg_RegisterUserLinkLogin =findViewById(R.id.msg_RegisterUserLinkLogin);

        //Event setOnClickListener-----------------------------------------------------------------------------
        msg_RegisterUserSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String msg_Email=msg_RegisterUserEmail.getText().toString().trim();

                if(!msg_RegisterUserName.getText().toString().isEmpty() &&
                        !msg_RegisterUserLastName.getText().toString().isEmpty() &&
                        !msg_RegisterUserEmail.getText().toString().isEmpty() &&
                        !msg_RegisterUserContact.getText().toString().isEmpty() &&
                        !msg_RegisterUserpassword.getText().toString().isEmpty()
                )
                {
                    if(msg_Email.matches(emailPattern))
                    {
                        if(msg_RegisterUserpassword.length()>=6)
                        {
                            if(msg_RegisterUserpassword.getText().toString().equals(msg_RegisterUserConfirmpassword.getText().toString()))
                            {
                                registration=new Registration("12",msg_RegisterUserName.getText().toString(), msg_RegisterUserLastName.getText().toString(),
                                        msg_RegisterUserEmail.getText().toString(), msg_RegisterUserContact.getText().toString(),
                                        msg_RegisterUserCnic.getText().toString(),
                                        msg_RegisterUserpassword.getText().toString(),"Pending");

                                SaveRegistrationData();
                            }
                            else
                            {
                                Toast.makeText(RegisterUserActivity.this, "Confirm Password is Different", Toast.LENGTH_SHORT).show();
                            }

                        }
                        else
                        {
                            Toast.makeText(RegisterUserActivity.this, "Do type Password: Greater than 6 ", Toast.LENGTH_SHORT).show();
                        }

                    }
                    else
                    {
                        Toast.makeText(RegisterUserActivity.this, "Enter a Correct Email", Toast.LENGTH_SHORT).show();
                    }

                }
                else
                {
                    Toast.makeText(RegisterUserActivity.this, "Please Complete the Credentials", Toast.LENGTH_SHORT).show();
                }





            }
        });

        msg_RegisterUserLinkLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RegisterUserActivity.this, LoginUserActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }


    void SaveRegistrationData()
    {
        //SaveData to API

        StringRequest request=new StringRequest(StringRequest.Method.POST, ROUTE_ALERT_URL_Registration, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                Toast.makeText(RegisterUserActivity.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                Toast.makeText(RegisterUserActivity.this, "Waiting For Approval\nDo Check your Email", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                Toast.makeText(RegisterUserActivity.this, ""+volleyError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> map=new HashMap<>();
                map.put(FIRST_NAME,registration.msg_First_Name);
                map.put(LAST_NAME,registration.msg_Last_Name);
                map.put(EMAIL_ID,registration.msg_Email_ID);
                map.put(CONTACT,registration.msg_Contact);
                map.put(ID_PROOF,registration.msg_ID_Proof);
                map.put(PASSWORD,registration.msg_Password);
                map.put(APPROVAL,registration.msg_Approval);

                return  map;
            }
        };

        RequestQueue queue= Volley.newRequestQueue(RegisterUserActivity.this);
        queue.add(request);

    }
}
