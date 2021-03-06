package com.cjt.weather;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class F1AddBoard extends Fragment {

    int REQUEST_IMAGE_CODE = 1001;
    int REQUEST_EXTERNAL_STORAGE_PERMISSION = 1002;

    RequestQueue rq;
    private static final String TAG = "MAIN";
    String url_weather = "https://api.openweathermap.org/data/2.5/weather?q=gwangju&appid=3c8a165efd4b9e13dc4f58b4b1056c34&lang=kr&units=metric";
    String url_post = "http://172.30.1.28:3002/board";

    ImageView img_post_background, img_post_weatherIcon, img_post, img_select;
    EditText edt_post_content, edt_post_board_tag, edt_post_top, edt_post_bottom, edt_post_shoes, edt_post_acc;
    TextView tv_post_weather, tv_post_temper, tv_post_sense_temper, tv_post_wind, tv_post_humid;
    Bitmap bmp_img;

    SharedPreferences spf;

    String id = "";
    String weather, temper, sense_temper, humid, wind;
    String content = "", board_tag = "", top = "", bottom = "", shoes = "", acc = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // ??? ?????????
        View view = inflater.inflate(R.layout.fragment_1, container, false);

        img_post_background = view.findViewById(R.id.img_post_background);
        img_post_weatherIcon = view.findViewById(R.id.img_grid_weatherIcon);
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

        // ??????????????? ???????????? Context??? ?????? ????????????????????? ????????? ??? ??????.
        spf = this.getActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);
        id = spf.getString("id", "default_id");

        //?????? ?????? ?????? ?????? ??????
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

        if (rq == null) {
            rq = Volley.newRequestQueue(getActivity());
        }

        // ?????? ???????????? ??????.

        final StringRequest sr = new StringRequest(Request.Method.GET, url_weather, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Log.d("?????? response ??????", response);
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

                    if (weather.equals("clear")) {
                        img_post_weatherIcon.setImageResource(R.drawable.clear);
                        img_post_background.setImageResource(R.drawable.clearbackground);
                    } else if (weather.equals("clouds")) {
                        img_post_weatherIcon.setImageResource(R.drawable.clouds);
                        img_post_background.setImageResource(R.drawable.rainbackground);
                    } else if (weather.equals("atmosphere")) {
                        img_post_weatherIcon.setImageResource(R.drawable.atmosphere);
                    } else if (weather.equals("rain")) {
                        img_post_weatherIcon.setImageResource(R.drawable.rain);
                        img_post_background.setImageResource(R.drawable.rainbackground);
                    } else if (weather.equals("thunderstorm")) {
                        img_post_weatherIcon.setImageResource(R.drawable.thunderstorm);
                    } else if (weather.equals("drizzle")) {
                        // drizzle:?????????
                        img_post_weatherIcon.setImageResource(R.drawable.drizzle);
                    }

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

        final StringRequest stringRequest_posting = new StringRequest(Request.Method.POST, url_post, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Log.d("Post response ??????", response);

                Toast toast = null;
                if (response.equals("board_fail")) {
                    toast = Toast.makeText(getActivity(), "????????? ??????", Toast.LENGTH_SHORT);

                } else if (response.equals("board_success")) {
                    toast = Toast.makeText(getActivity(), "????????? ??????.", Toast.LENGTH_SHORT);
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
                params.put("post_img", BitmapToString(bmp_img));
                Log.d("send_img", BitmapToString(bmp_img));
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

        // ?????? ?????? ??????
        img_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(in, REQUEST_IMAGE_CODE);

            }
        });

        // ????????? ?????? ??????
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

    // ?????? ?????? ??? ?????? ??????
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CODE) {
            Uri image = data.getData();
            try {
                // ???????????? ????????? ???????????? ??????????????????.(????????? ??????)
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), image);
                //img_select.setImageBitmap(bitmap);

                // res/drawable ??? ?????? ???????????? bitmap?????? ????????????
                //bmp_img = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.image2);

                // bmp_img ??? ???????????? ????????? ????????? ??????.
                //BitmapDrawable drawable = (BitmapDrawable) img_select.getDrawable();
                //bmp_img = drawable.getBitmap();
                //img_select.setImageBitmap(bitmap);

                String imgpath = "C:/Users/smhrd/Desktop/AndroidStudy/thinkBig/amekaji1.png";
                Bitmap bm = BitmapFactory.decodeFile(imgpath);
                //img_select.setImageBitmap(decodeSampledBitmapFromResource(getResources(), bm, 100, 100));


                Toast.makeText(getActivity(), "load ok", Toast.LENGTH_SHORT).show();

            } catch (IOException e) {
                Toast.makeText(getActivity(), "load error", Toast.LENGTH_SHORT).show();
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


    // bmp -> String
    public static String BitmapToString(Bitmap bitmap) {
        if (bitmap == null) {
            return "default image";
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, baos);
        byte[] bytes = baos.toByteArray();
        String bitString = Base64.encodeToString(bytes, Base64.DEFAULT);
        return bitString;
    }

    // String -> bmp
    public static Bitmap StringToBitmap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

}




