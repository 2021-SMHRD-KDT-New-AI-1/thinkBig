package com.cjt.weather;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class F4SetUp extends Fragment {

    View view;
    Button btn_edit_profile, btn_edit_member_info;

    TextView tv_nick;
    TextView tv_state_msg;
    TextView tv_pro_tag;

    String nick = "";
    String state_msg = "";
    String pro_tag = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_4, container, false);
        btn_edit_profile = view.findViewById(R.id.btn_edit_profile);
        btn_edit_member_info = view.findViewById(R.id.btn_edit_member_info);

        tv_nick = view.findViewById(R.id.tv_nick);
        tv_state_msg = view.findViewById(R.id.tv_state_msg);
        tv_pro_tag = view.findViewById(R.id.tv_tag);

        // Bundle : 메인 액티비티 -> F4로 값전달받기.
        Bundle bundle = getArguments();

        if (bundle != null) {
            nick = bundle.getString("nick");
            state_msg = bundle.getString("state_msg");
            pro_tag = bundle.getString("pro_tag");

            tv_nick.setText(nick);
            tv_state_msg.setText(state_msg);
            tv_pro_tag.setText(pro_tag);
        }

        btn_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Edit_Profile.class);
                startActivity(intent);
            }
        });

        btn_edit_member_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Member_information_change.class);
                startActivity(intent);
            }
        });

        return view;
    }
}