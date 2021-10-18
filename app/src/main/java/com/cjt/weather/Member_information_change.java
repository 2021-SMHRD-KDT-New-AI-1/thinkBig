package com.cjt.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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

public class Member_information_change<spf> extends AppCompatActivity {
    private static final String TAG = "MAIN";

    TextView tv_name1, tv_name2, tv_birthdate, tv_phone;
    EditText et_pw1, et_pw2;
    Button btn_et_phone, btn_et_pw;
    ImageButton btn_send;
    RequestQueue requestQueue;

    String id = "";
    String pw = "";
    String phone = "";
    String name = "";
    String birthdate = "";
    private SharedPreferences spf;
    private SharedPreferences spf1;
    private SharedPreferences spf2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_information_change);

        tv_name1 = findViewById(R.id.tv_name1);
        tv_name2 = findViewById(R.id.tv_name2);
        tv_birthdate = findViewById(R.id.tv_name2);
        tv_phone = findViewById(R.id.tv_phone);
        et_pw1 = findViewById(R.id.et_pw1);
        et_pw2 = findViewById(R.id.et_pw2);
        btn_et_phone = findViewById(R.id.btn_et_phone);
        btn_et_pw = findViewById(R.id.btn_et_pw);
        btn_send = findViewById(R.id.btn_send);

        spf = getSharedPreferences("id", Context.MODE_PRIVATE);
        spf1 = getSharedPreferences("name", Context.MODE_PRIVATE);
        spf2 = getSharedPreferences("birthdate", Context.MODE_PRIVATE);
        id = spf.getString("id", "default_id");
        name = spf.getString("name", "default_name");
        birthdate = spf.getString("birthdate", "default_birthdate");

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        String url = "http://172.30.1.29:3002/edit_user";

        final StringRequest stringRequest_edit_user = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("edit_user 응답 확인", response);
                // 응답 종류
                // 1. edit_pro_success
                // 2. edit_pro_fail

                Toast toast = null;
                if (response.equals("success")) {
                    toast = Toast.makeText(getApplicationContext(), "프로필 설정 완료.", Toast.LENGTH_SHORT);
                    Intent intent = new Intent(Member_information_change.this, MainActivity.class);
                    intent.putExtra("from", "Member_information_change");
                    intent.putExtra("pw", pw);
                    intent.putExtra("phone", phone);
                    startActivity(intent);
                } else if (response.equals("fail")) {
                    toast = Toast.makeText(getApplicationContext(), "회원정보 수정 실패", Toast.LENGTH_SHORT);
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
                params.put("id", id);
                params.put("name", name);
                params.put("birthdate", birthdate);
                params.put("pw", pw);
                params.put("phone", phone);

                return params;
            }
        };

        stringRequest_edit_user.setTag(TAG);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pw = et_pw2.getText().toString();
                phone = tv_phone.getText().toString();
                requestQueue.add(stringRequest_edit_user);
            }
        });

        if (!et_pw1.equals(et_pw2)) {
            Toast.makeText(getApplicationContext(), "비밀번호가 일치해야 합니다.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (requestQueue != null) {
            requestQueue.cancelAll(TAG);
        }
    }
}