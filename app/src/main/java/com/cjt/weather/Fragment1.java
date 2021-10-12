package com.cjt.weather;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


public class Fragment1 extends Fragment {

    ImageButton imgbtn;
    Button btn_edit1, btn_edit2, btn_edit3;
    TextView tv_hashtag,tv_top, tv_bottom, tv_shoes, tv_acc, tv_weather, tv_post;
    ImageView img_weather;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_1, container, false);


    }
}