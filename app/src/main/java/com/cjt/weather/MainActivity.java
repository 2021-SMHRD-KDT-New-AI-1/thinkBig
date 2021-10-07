package com.cjt.weather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 님들 주석 잘 달아주시고
        // 이 프로젝트 파일로 시작해봅시다!!

        BottomNavigationView nav_view;

        nav_view =findViewById(R.id.nav_view);

        nav_view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.menu1){
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new Fragment1()).commit();

                }else if(item.getItemId()==R.id.menu2){
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new Fragment2()).commit();

                }else if(item.getItemId()==R.id.menu3){
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new Fragment3()).commit();

                }else if(item.getItemId()==R.id.menu4)
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new Fragment4()).commit();

                return false;
            }
        });

    }
}