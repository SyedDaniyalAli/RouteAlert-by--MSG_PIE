package com.sda.syeddaniyalali.routealert;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentChangePassword extends Fragment {

    EditText msg_Admin_CurrentPassword, msg_Admin_NewPassword, msg_Admin_ConfirmPassword;
    Button msg_Btn_Admin_ChangePassword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_fragment_change_password, container, false);

        msg_Admin_CurrentPassword = view.findViewById(R.id.msg_Admin_CurrentPassword);
        msg_Admin_NewPassword = view.findViewById(R.id.msg_Admin_NewPassword);
        msg_Admin_ConfirmPassword = view.findViewById(R.id.msg_Admin_ConfirmPassword);
        msg_Btn_Admin_ChangePassword = view.findViewById(R.id.msg_Btn_Admin_ChangePassword);

        msg_Btn_Admin_ChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Password Has been Changed", Toast.LENGTH_SHORT).show();
            }
        });

        return view;

    }

}
