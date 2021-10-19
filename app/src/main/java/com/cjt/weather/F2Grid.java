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
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class F2Grid extends Fragment {
    // 뷰 선언
    ImageView img_grid_weatherIcon;
    TextView tv_grid_temp, tv_grid_weather, tv_grid_senseTemp, tv_grid_wind, tv_grid_humid;

    RequestQueue rq;
    private static final String TAG = "MAIN";
    String weather, temper, sense_temper, humid, wind;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_2, container, false);

        // 뷰 초기화
        tv_grid_temp = view.findViewById(R.id.tv_grid_temp);
        tv_grid_weather = view.findViewById(R.id.tv_grid_weather);
        tv_grid_senseTemp = view.findViewById(R.id.tv_grid_senseTemp);
        tv_grid_wind = view.findViewById(R.id.tv_grid_wind);
        tv_grid_humid = view.findViewById(R.id.tv_grid_humid);

        if (rq == null) {
            rq = Volley.newRequestQueue(getActivity());
        }

        String url = "https://api.openweathermap.org/data/2.5/weather?q=gwangju&appid=3c8a165efd4b9e13dc4f58b4b1056c34&lang=kr&units=metric";

        final StringRequest sr = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("날씨 response 확인", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray weatherArr = jsonObject.getJSONArray("weather");
                    JSONObject weatherOb = (JSONObject) weatherArr.get(0);
                    JSONObject main = jsonObject.getJSONObject("main");
                    JSONObject windOb = jsonObject.getJSONObject("wind");

                    weather = weatherOb.getString("main");
                    Log.d("날씨", weather);

                    temper = main.getString("temp");
                    Log.d("날씨", temper);
                    sense_temper = main.getString("feels_like");
                    Log.d("날씨", sense_temper);
                    humid = main.getString("humidity");
                    Log.d("날씨", humid);

                    wind = windOb.getString("speed");
                    Log.d("날씨", wind);

                    tv_grid_weather.setText(weather);
                    tv_grid_temp.setText(temper);
                    tv_grid_senseTemp.setText(sense_temper);
                    tv_grid_humid.setText(humid);
                    tv_grid_wind.setText(wind);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        sr.setTag(TAG);
        rq.add(sr);

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