package com.cjt.weather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    // 구황작물님들 주석 잘 달아주시고
    // 이 프로젝트 파일로 시작해봅시다!!

    BottomNavigationView nav_view;
    F4SetUp f4SetUp;

    String from;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nav_view = findViewById(R.id.nav_view);

        nav_view.setSelectedItemId(R.id.menu2);

        f4SetUp = new F4SetUp();

        from = getIntent().getStringExtra("from");

        if (from.equals("Login")) {
            // 로그인하고 메인 액티비티 오자마자 그리드 화면 보여주기!
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new F2Grid()).commit();
        } else if (from.equals("edt_profile")) {
            String nick = getIntent().getStringExtra("nick");
            String state_msg = getIntent().getStringExtra("state_msg");
            String pro_tag = getIntent().getStringExtra("pro_tag");

            getSupportFragmentManager().beginTransaction().replace(R.id.container, f4SetUp).commit();

            Bundle bundle = new Bundle();
            bundle.putString("nick", nick);
            bundle.putString("state_msg", state_msg);
            bundle.putString("pro_tag", pro_tag);
            f4SetUp.setArguments(bundle);
        }

        nav_view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.menu1) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new F1AddBoard()).commit();

                } else if (item.getItemId() == R.id.menu2) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new F2Grid()).commit();

                } else if (item.getItemId() == R.id.menu3) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new F3List()).commit();

                } else if (item.getItemId() == R.id.menu4)
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, f4SetUp).commit();

                return true;
            }
        });

    }
}