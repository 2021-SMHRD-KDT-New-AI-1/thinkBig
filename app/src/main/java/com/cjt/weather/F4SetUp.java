package com.cjt.weather;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class F4SetUp extends Fragment {

    View view;
    Button btn_edit_profile, btn_edit_member_info;

    ImageView img_profile_pro_img_path;

    TextView tv_profile_name;
    TextView tv_profile_nick;
    TextView tv_profile_state_msg;
    TextView tv_profile_pro_tag;

    SharedPreferences spf;

    String name = "";
    String nick = "";
    String state_msg = "";
    String pro_tag = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_4, container, false);
        btn_edit_profile = view.findViewById(R.id.btn_edit_profile);
        btn_edit_member_info = view.findViewById(R.id.btn_edit_member_info);

        img_profile_pro_img_path = view.findViewById(R.id.img_profile_pro_img_path);

        tv_profile_name = view.findViewById(R.id.tv_profile_name);
        tv_profile_nick = view.findViewById(R.id.tv_board_shoes);
        tv_profile_state_msg = view.findViewById(R.id.tv_board_acc);
        tv_profile_pro_tag = view.findViewById(R.id.tv_profile_pro_tag);

        // getSharedPreferences는 Context 객체 메소드라서
        // 액티비티가 아니라서 Context가 없는 프래그먼트에는 사욯할 수 없다.
        spf = this.getActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);
        name = spf.getString("name", "default_name");
        nick = spf.getString("nick", "default_nick");
        state_msg = spf.getString("state_msg", "default_state_msg");
        pro_tag = spf.getString("pro_tag", "default_pro_tag");
        // spf.getString("id", 만약 사용할 데이터가 없을 때 디폴트 값 넣어주기);

        img_profile_pro_img_path.setImageResource(R.drawable.like_change);
        tv_profile_name.setText(name);
        tv_profile_nick.setText(nick);
        tv_profile_state_msg.setText(state_msg);
        tv_profile_pro_tag.setText(pro_tag);


        // Bundle : 메인 액티비티 -> F4로 값전달받기.
        Bundle bundle = getArguments();

        if (bundle != null) {
            nick = bundle.getString("nick");
            state_msg = bundle.getString("state_msg");
            pro_tag = bundle.getString("pro_tag");

            tv_profile_nick.setText(nick);
            tv_profile_state_msg.setText(state_msg);
            tv_profile_pro_tag.setText(pro_tag);
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