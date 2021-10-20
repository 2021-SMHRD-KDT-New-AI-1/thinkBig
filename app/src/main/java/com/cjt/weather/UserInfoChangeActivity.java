package com.cjt.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class UserInfoChangeActivity<spf> extends AppCompatActivity {

    TextView tv_user_info_name, tv_user_info_birthdate;
    EditText edt_user_info_phone, edt_user_info_cur_pw, edt_user_info_new_pw;
    Button btn_user_info_phone_change, btn_user_info_pw_change;
    RequestQueue requestQueue;
    SharedPreferences spf_user_info;
    SharedPreferences.Editor editor_user_info;
    String TAG = "MAIN";

    String id = ""; // 디비에 키값 보내줘야함!
    // 보여줄 정보
    String name = "";
    String birthdate = "";
    // 수정할 정보
    String phone = "";
    String cur_pw = "";
    String new_pw = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_information_change);

        tv_user_info_name = findViewById(R.id.tv_user_info_name);
        tv_user_info_birthdate = findViewById(R.id.tv_user_info_birthdate);
        edt_user_info_phone = findViewById(R.id.edt_user_info_phone);
        edt_user_info_cur_pw = findViewById(R.id.edt_user_info_cur_pw);
        edt_user_info_new_pw = findViewById(R.id.edt_user_info_new_pw);
        btn_user_info_phone_change = findViewById(R.id.btn_user_info_phone_change);
        btn_user_info_pw_change = findViewById(R.id.btn_user_info_pw_change);

        spf_user_info = getSharedPreferences("user_info", Context.MODE_PRIVATE);
        editor_user_info = spf_user_info.edit();
        id = spf_user_info.getString("id", "default_id");
        name = spf_user_info.getString("name", "default_name");
        birthdate = spf_user_info.getString("birthdate", "default_birthdate");
        phone = spf_user_info.getString("phone", "default_phone");

        tv_user_info_name.setText(name);
        tv_user_info_birthdate.setText(birthdate);
        edt_user_info_phone.setText(phone);

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }


        String url = "http://172.30.1.29:3002/edit_user";

        // 폰번호 갱신 코드
        final StringRequest stringRequest_change_phone = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("user_info_change_phone 응답 확인", response);

                Toast toast = null;
                if (response.equals("success")) {
                    toast = Toast.makeText(getApplicationContext(), "휴대폰번호 수정 완료", Toast.LENGTH_SHORT);
                    editor_user_info.putString("phone", phone);
                    editor_user_info.commit();
                } else if (response.equals("fail")) {
                    toast = Toast.makeText(getApplicationContext(), "휴대폰번호 수정 실패", Toast.LENGTH_SHORT);
                }
                toast.show();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Writer writer = new StringWriter();
                        error.printStackTrace(new PrintWriter(writer));
                        String s = writer.toString();
                        Log.d("edit_user 확인", s);
                    }
                }
        ) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("btn", "phone");
                params.put("id", id);
                params.put("phone", phone);
                //params.put("birthdate", birthdate);

                return params;
            }
        };

        // 비밀번호 변경하기
        final StringRequest stringRequest_change_pw = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("user_info_change_pw 응답 확인", response);

                Toast toast = null;
                if (response.equals("success")) {
                    toast = Toast.makeText(getApplicationContext(), "비밀번호 수정 완료", Toast.LENGTH_SHORT);
                    editor_user_info.putString("pw", new_pw);
                    editor_user_info.commit();
                } else if (response.equals("fail")) {
                    toast = Toast.makeText(getApplicationContext(), "비밀번호 수정 완료", Toast.LENGTH_SHORT);
                }
                toast.show();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Writer writer = new StringWriter();
                        error.printStackTrace(new PrintWriter(writer));
                        String s = writer.toString();
                        Log.d("edit_user 확인", s);
                    }
                }
        ) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("btn", "pw");
                params.put("id", id);
                params.put("pw", new_pw);
                //params.put("birthdate", birthdate);

                return params;
            }
        };

        stringRequest_change_phone.setTag(TAG);
        stringRequest_change_pw.setTag(TAG);

        // 버튼:핸드폰 번호 변경하기
        btn_user_info_phone_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phone = edt_user_info_phone.getText().toString();
                if (phone.length() != 11) {
                    Toast.makeText(getApplicationContext(), "번호를 정확히 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    requestQueue.add(stringRequest_change_phone);
                }
            }
        });

        // 버튼:비밀번호 변경하기
        btn_user_info_pw_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cur_pw = edt_user_info_cur_pw.getText().toString();
                new_pw = edt_user_info_new_pw.getText().toString();

                if (cur_pw.equals(new_pw)) {
                    Toast.makeText(getApplicationContext(), "현재와 다른 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    requestQueue.add(stringRequest_change_pw);
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (requestQueue != null) {
            requestQueue.cancelAll(TAG);
        }
    }
}