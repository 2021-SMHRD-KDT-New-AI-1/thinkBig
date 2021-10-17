package com.cjt.weather;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class F4SetUp extends Fragment {

    View view;
    Button btn_edit_profile;

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

        tv_nick = view.findViewById(R.id.tv_nick);
        tv_state_msg = view.findViewById(R.id.tv_state_msg);
        tv_pro_tag = view.findViewById(R.id.tv_pro_tag);

        /*
        프로필 셋텍스트 하는 것은 좀 더 고민해봐야겠어
        하지만, 인텐트를 사용해서 메인으로 값을 넘겨주고,
        그 값을 프래그먼트로 이동할 때 넘겨줘서 설정하면 될 거같다.
        */

        /* 미완성! 다른 방법으로 해야할 듯
        SharedPreferences spf = getActivity().getSharedPreferences("pro_info", Context.MODE_PRIVATE);
        nick = spf.getString("nick", "default_nick");
        state_msg = spf.getString("state_msg", "default_state_msg");
        pro_tag = spf.getString("pro_tag", "default_pro_tag");
        // spf.getString("id", 만약 사용할 데이터가 없을 때 디폴트 값 넣어주기);

        tv_nick.setText(nick);
        tv_state_msg.setText(state_msg);
        tv_pro_tag.setText(pro_tag);

         */

        btn_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Edit_Profile.class);
                startActivity(intent);
            }
        });

        return view;
    }
}