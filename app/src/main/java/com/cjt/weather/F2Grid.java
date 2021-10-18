package com.cjt.weather;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class F2Grid extends Fragment {

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
                "나이키", "켈빈클라인", "뉴발", "티파니"));
        items.add(new BoardVO(R.drawable.image4, "내용이지!", "#태크", "5",
                "나이키", "켈빈클라인", "뉴발", "티파니"));
        items.add(new BoardVO(R.drawable.image5, "내용이지!", "#태크", "5",
                "나이키", "켈빈클라인", "뉴발", "티파니"));

        postRecyclerView.setAdapter(new PostAdapter(items));

        return view;
    }
}