package com.cjt.weather;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class F2Grid extends Fragment {

    TextView tv_main_temp, tv_main_weather;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_2, container, false);

        // 리싸이클러 뷰 시작
        RecyclerView postRecyclerView = view.findViewById(R.id.postsRecyclerView);
        postRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));

        List<BoardVO> items = new ArrayList<>();

        items.add(new BoardVO(R.drawable.image2, "내용이지!", "#태크", "5",
                "나이키", "켈빈클라인", "뉴발", "티파니"));
        items.add(new BoardVO(R.drawable.image3, "내용이지!", "#태크", "5",
                "아디다스", "뱅뱅", "나이키", "MLB모자"));
        items.add(new BoardVO(R.drawable.image4, "내용이지!", "#태크", "5",
                "셔츠", "검정슬랙스", "구두", "갤럭시워치"));
        items.add(new BoardVO(R.drawable.image5, "내용이지!", "#태크", "5",
                "나이키", "켈빈클라인", "뉴발", "티파니"));

        postRecyclerView.setAdapter(new PostAdapter(items));
        Weather weather = new Weather();


        // toString(), ParseInt 하면 죽음 뿐...
//        tv_main_temp = view.findViewById(R.id.tv_main_temp);
//        tv_main_temp.setText(weather.temper);
        //tv_main_temp.setText(weather.temper);
//        tv_main_temp.setText(weather.temper);
//

//        tv_main_weather = view.findViewById(R.id.tv_main_weather);
//        tv_main_weather.setText(weather.weather);


        return view;
    }
}