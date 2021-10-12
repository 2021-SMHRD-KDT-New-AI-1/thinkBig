package com.cjt.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class PostActivity extends AppCompatActivity {

    ImageButton imgbtn;
    Button btn_edit1, btn_edit2, btn_edit3;
    TextView tv_hashtag,tv_top, tv_bottom, tv_shoes, tv_acc, tv_weather, tv_post;
    ImageView img_weather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgbtn = findViewById(R.id.imgbtn);
        btn_edit1 = findViewById(R.id.btn_edit1);
        btn_edit2 = findViewById(R.id.btn_edit2);
        btn_edit3 = findViewById(R.id.btn_edit3);
        tv_hashtag = findViewById(R.id.tv_hashtag);
        tv_top = findViewById(R.id.tv_top);
        tv_bottom = findViewById(R.id.tv_bottom);
        tv_shoes = findViewById(R.id.tv_shoes);
        tv_acc = findViewById(R.id.tv_acc);
        tv_weather = findViewById(R.id.tv_weather);
        tv_post = findViewById(R.id.tv_post);

    }
}