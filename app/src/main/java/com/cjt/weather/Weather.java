package com.cjt.weather;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Weather extends AppCompatActivity {

    RequestQueue rq;
    StringRequest sr;
    String humid, weather, sense_temper, wind, temper;

    // 1. 우리 디비 컬럼 필드값(변수) 정의.


    public void receiveWeather () {
        // 2. api를 통해 위의 컬럼에 값을 넣어준다.
        if (rq == null) {
            rq = Volley.newRequestQueue(getApplicationContext());
        }


        sr = new StringRequest(Request.Method.GET, "https://api.openweathermap.org/data/2.5/weather?q=gwangju&appid=3c8a165efd4b9e13dc4f58b4b1056c34&lang=kr&units=metric",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject main = jsonObject.getJSONObject("main");
                            temper = main.getString("temp");
                            humid = main.getString("humidity");
                            weather = main.getString("weather");
                            sense_temper = main.getString("feels_like");
                            wind = main.getString("speed");

                            Log.d("날씨", temper);
                            // Log.d("날씨", temper.toString());

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

        rq.add(sr);
    }
}

