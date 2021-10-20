package com.cjt.weather;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class F2Grid extends Fragment {

    // 뷰 선언
    ImageView img_grid_background, img_grid_weatherIcon;
    TextView tv_grid_temp, tv_grid_weather, tv_grid_senseTemp, tv_grid_wind, tv_grid_humid;
    RecyclerView postRecyclerView;
    PostAdapter postAdapter;

    RequestQueue rq;
    private static final String TAG = "MAIN";

    // 변수 선언
    String weather, temper = "", sense_temper, humid, wind;
    List<BoardVO> items;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // 뷰 초기화
        View view = inflater.inflate(R.layout.fragment_2, container, false);

        img_grid_background = view.findViewById(R.id.img_grid_background);
        img_grid_weatherIcon = view.findViewById(R.id.img_grid_weatherIcon);

        tv_grid_temp = view.findViewById(R.id.tv_grid_temp);
        tv_grid_weather = view.findViewById(R.id.tv_grid_weather);
        tv_grid_senseTemp = view.findViewById(R.id.tv_grid_senseTemp);
        tv_grid_wind = view.findViewById(R.id.tv_grid_wind);
        tv_grid_humid = view.findViewById(R.id.tv_grid_humid);

        postRecyclerView = view.findViewById(R.id.postsRecyclerView);

        // List<BoardVO> items 초기화
        items = new ArrayList<>();

        // 리싸이클러 뷰 시작
        postRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        postAdapter = new PostAdapter(items);
        postRecyclerView.setAdapter(postAdapter);

        items.add(new BoardVO("이도현", "애기야 가자!", R.drawable.amekaji1, "#차도남", "3", "청자켓", "치노팬츠", "구두", "크로스백"));
        items.add(new BoardVO("이진화", "고소할게요.", R.drawable.amekaji2, "#스트릿", "2", "조끼", "브라운팬츠", "닥터마틴", "오메가시계"));
        items.add(new BoardVO("김지윤", "무야호~!!", R.drawable.amekaji3, "#행복행복", "7", "무신사스탠다드", "무신사스탠다드", "옥스포드", "18k 반지"));
        items.add(new BoardVO("전진완", "그냥 되던데?", R.drawable.amekaji4, "#개발자룩", "12", "에잇세컨즈", "탑텐", "뉴발란스 327", "백팩"));
        items.add(new BoardVO("최진태", "여름이었다.", R.drawable.amekaji5, "#캐주얼", "10", "아디다스", "보세 와이드 팬츠", "뉴발", "미착용"));
        items.add(new BoardVO("배수지", "난 너무 예뻐요.", R.drawable.amekaji6, "#SoHot", "32", "커스텀멜로우", "체크바지", "닥스", "미착용"));

        if (rq == null) {
            rq = Volley.newRequestQueue(getActivity());
        }

        // 날씨값 받아오는 연결
        String url_weather = "https://api.openweathermap.org/data/2.5/weather?q=gwangju&appid=3c8a165efd4b9e13dc4f58b4b1056c34&lang=kr&units=metric";

        final StringRequest sr = new StringRequest(Request.Method.GET, url_weather, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("error", "onResponse: 날씨");
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray weatherArr = jsonObject.getJSONArray("weather");
                    JSONObject weatherOb = (JSONObject) weatherArr.get(0);
                    JSONObject main = jsonObject.getJSONObject("main");
                    JSONObject windOb = jsonObject.getJSONObject("wind");

                    weather = weatherOb.getString("main");

                    temper = main.getString("temp");
                    sense_temper = main.getString("feels_like");
                    humid = main.getString("humidity");

                    wind = windOb.getString("speed");

                    tv_grid_weather.setText(weather);
                    tv_grid_temp.setText(temper);
                    tv_grid_senseTemp.setText(sense_temper);
                    tv_grid_humid.setText(humid);
                    tv_grid_wind.setText(wind);

                    if (weather.equals("clear")) {
                        img_grid_weatherIcon.setImageResource(R.drawable.clear);
                        img_grid_background.setImageResource(R.drawable.clearbackground);
                    } else if (weather.equals("clouds")) {
                        img_grid_weatherIcon.setImageResource(R.drawable.clouds);
                        img_grid_background.setImageResource(R.drawable.cloudsbackground);
                    } else if (weather.equals("atmosphere")) {
                        img_grid_weatherIcon.setImageResource(R.drawable.atmosphere);
                    } else if (weather.equals("rain")) {
                        img_grid_weatherIcon.setImageResource(R.drawable.rain);
                        img_grid_background.setImageResource(R.drawable.rainbackground);
                    } else if (weather.equals("thunderstorm")) {
                        img_grid_weatherIcon.setImageResource(R.drawable.thunderstorm);
                    } else if (weather.equals("drizzle")) {
                        img_grid_weatherIcon.setImageResource(R.drawable.drizzle);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("error", "onErrorResponse2: ");
                        error.printStackTrace();
                    }
                });
        sr.setTag(TAG);

        // 날씨값 가져오는 연결 실행
        rq.add(sr);

        // 여기는 그리드에 뿌려줄 데이터 받아올 연결.
        String url = "http://172.30.1.29:3002/resboard";

        final StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Grid response", response);
                response = "{\"boradDataArray\":" + response + "}";
                Log.d("Grid response", response);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray boards = jsonObject.getJSONArray("boradDataArray");

                    for (int i = 0; i < boards.length(); i++) {
                        JSONObject board = (JSONObject) boards.get(i);

                        String nick = board.getString("nick");
                        String state_msg = board.getString("state_msg");
                        //String img = board.getString("img");
                        String content = board.getString("content");
                        String like_cnt = board.getString("like_cnt");
                        String top = board.getString("top");
                        String bottom = board.getString("bottom");
                        String shoes = board.getString("shoes");
                        String acc = board.getString("acc");

                        BoardVO boardVO = new BoardVO(nick, state_msg, R.drawable.image4, content, like_cnt, top, bottom, shoes, acc);
                        items.add(boardVO);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // 오.. 나 좀 똑똑한듯..?
                postRecyclerView.getAdapter().notifyDataSetChanged();

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("error", "onErrorResponse: ");
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                // 날씨값 기준으로 게시물 가져올거에요!
                params.put("temper", temper);
                return params;
            }
        };
        stringRequest.setTag(TAG);

        // 그리드 데이터 받아오는 연결 시작!
        rq.add(stringRequest);

        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        if (rq != null) {
            rq.cancelAll(TAG);
        }
    }
}