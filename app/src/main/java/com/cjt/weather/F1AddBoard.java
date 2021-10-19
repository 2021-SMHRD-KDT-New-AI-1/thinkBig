package com.cjt.weather;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class F1AddBoard extends Fragment {

    int REQUEST_IMAGE_CODE = 1001;
    int REQUEST_EXTERNAL_STORAGE_PERMISSION = 1002;

    ImageView img_post, img_select;
    EditText edt_post_content, edt_post_board_tag, edt_post_top, edt_post_bottom, edt_post_shoes, edt_post_acc;
    TextView tv_post_weather, tv_post_temper, tv_post_sense_temper, tv_post_wind, tv_post_humid;

    RequestQueue rq;
    private static final String TAG = "MAIN";

    SharedPreferences spf;

    String id = "";
    String weather, temper, sense_temper, humid, wind;
    String content="", board_tag="", top="", bottom="", shoes="", acc="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //앨범 접근 권한 설정 코드
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_EXTERNAL_STORAGE_PERMISSION);
            }
        }

        // 뷰 초기화
        View view = inflater.inflate(R.layout.fragment_1, container, false);

        img_post = view.findViewById(R.id.img_post);
        img_select = view.findViewById(R.id.img_select);

        tv_post_temper = view.findViewById(R.id.tv_post_temper);
        tv_post_weather = view.findViewById(R.id.tv_post_weather);
        tv_post_sense_temper = view.findViewById(R.id.tv_post_sense_temper);
        tv_post_wind = view.findViewById(R.id.tv_post_wind);
        tv_post_humid = view.findViewById(R.id.tv_post_humid);

        edt_post_content = view.findViewById(R.id.edt_post_content);
        edt_post_board_tag = view.findViewById(R.id.edt_post_board_tag);
        edt_post_top = view.findViewById(R.id.edt_post_top);
        edt_post_bottom = view.findViewById(R.id.edt_post_bottom);
        edt_post_shoes = view.findViewById(R.id.edt_post_shoes);
        edt_post_acc = view.findViewById(R.id.edt_post_acc);

        // 액티비티가 아니라서 Context가 없는 프래그먼트에는 사욯할 수 없다.
        spf = this.getActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);
        id = spf.getString("id", "default_id");

        if (rq == null) {
            rq = Volley.newRequestQueue(getActivity());
        }

        String url_weather = "https://api.openweathermap.org/data/2.5/weather?q=gwangju&appid=3c8a165efd4b9e13dc4f58b4b1056c34&lang=kr&units=metric";

        final StringRequest sr = new StringRequest(Request.Method.GET, url_weather, new Response.Listener<String>() {
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
                    temper = main.getString("temp");
                    sense_temper = main.getString("feels_like");
                    humid = main.getString("humidity");

                    wind = windOb.getString("speed");

                    tv_post_weather.setText(weather);
                    tv_post_temper.setText(temper);
                    tv_post_sense_temper.setText(sense_temper);
                    tv_post_humid.setText(humid);
                    tv_post_wind.setText(wind);

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


        // 앨범 접근 코드
        img_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(in, REQUEST_IMAGE_CODE);
            }
        });

        String url_post = "http://172.30.1.29:3002/board";

        final StringRequest stringRequest_posting = new StringRequest(Request.Method.POST, url_post, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Post response 확인", response);

                Toast toast = null;
                if (response.equals("board_fail")) {
                    toast = Toast.makeText(getActivity(), "포스팅 실패", Toast.LENGTH_SHORT);

                } else if (response.equals("board_success")) {
                    toast = Toast.makeText(getActivity(), "포스팅 성공.", Toast.LENGTH_SHORT);
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.putExtra("from", "Post");
                    startActivity(intent);
                }
                toast.show();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("id", id);
                params.put("weather", weather);
                params.put("temper", temper);
                params.put("sense_temper", sense_temper);
                params.put("humid", humid);
                params.put("wind", wind);

                params.put("content", content);
                params.put("board_tag", board_tag);
                params.put("top", top);
                params.put("bottom", bottom);
                params.put("shoes", shoes);
                params.put("acc", acc);

                return params;
            }
        };
        stringRequest_posting.setTag(TAG);

        // 게시물 게시 버튼
        img_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                content = edt_post_content.getText().toString();
                board_tag = edt_post_board_tag.getText().toString();
                top = edt_post_top.getText().toString();
                bottom = edt_post_bottom.getText().toString();
                shoes = edt_post_shoes.getText().toString();
                acc = edt_post_acc.getText().toString();

                rq.add(stringRequest_posting);
            }
        });

        return view;
    }

    //접근한 결과를 나타내는 코드
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CODE) {
            Uri image = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), image);
                img_select.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (rq != null) {
            rq.cancelAll(TAG);
        }
    }

}




