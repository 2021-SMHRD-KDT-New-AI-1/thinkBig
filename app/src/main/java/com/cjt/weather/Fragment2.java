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


public class Fragment2 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment2() {
        // Required empty public constructor
    }



    public static Fragment2 newInstance(String param1, String param2) {
        Fragment2 fragment = new Fragment2();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_2, container, false);

        //리싸이클시작
        RecyclerView postRecyclerView =view.findViewById(R.id.postsRecyclerView);
        postRecyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        );
        //Drawable폴더에서 이미지 파일 가져온다
        List<PostTitem> postTitems = new ArrayList<>();

        postTitems.add(new PostTitem(R.drawable.image2, "감자",));
        postTitems.add(new PostTitem(R.drawable.image3, "옥수수"));
        postTitems.add(new PostTitem(R.drawable.image4, "가지"));
        postTitems.add(new PostTitem(R.drawable.image5, "양파"));
        postTitems.add(new PostTitem(R.drawable.image1, "고구마"));
        postTitems.add(new PostTitem(R.drawable.image6, "최진태"));
        postTitems.add(new PostTitem(R.drawable.image7, "김지윤"));
        postTitems.add(new PostTitem(R.drawable.image8, "이진화"));
        postTitems.add(new PostTitem(R.drawable.image9, "전진완"));
        postTitems.add(new PostTitem(R.drawable.image10, "이도현"));

        postRecyclerView.setAdapter(new PostAdapter(postTitems));

        return view;
    }
}