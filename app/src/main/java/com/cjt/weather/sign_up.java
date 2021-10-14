package com.cjt.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class sign_up extends AppCompatActivity {

    private static final String TAG = "MAIN";
    EditText edit_id, edit_pw, edit_pw2, edit_birthdate, edit_name, edit_phone;
    RadioButton radi_male, radi_female;
    RadioGroup radi_Group;
    Button btn_id_identify, btn_register;
    RequestQueue requestQueue;

    // 이너클래스에서 써야해서 전역으로 선언!
    String id;
    String pw;
    String pw2;
    String birthdate;
    String name;
    String gender;
    String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edit_id = findViewById(R.id.edit_id);
        edit_pw = findViewById(R.id.edit_pw);
        edit_pw2 = findViewById(R.id.edit_pw2);
        edit_birthdate = findViewById(R.id.edit_birthdate);
        edit_name = findViewById(R.id.edit_name);
        radi_male = findViewById(R.id.radi_male);
        radi_female = findViewById(R.id.radi_female);
        radi_Group = findViewById(R.id.radi_Group);
        edit_phone = findViewById(R.id.edit_phone);
        btn_id_identify = findViewById(R.id.btn_id_identify);
        btn_register = findViewById(R.id.btn_register);

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        String url = "http://172.30.1.29:3002/SignUp";

        final StringRequest stringRequest_join = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // response 받을 때는 스트링이다!
                // 스트링이 제이슨 형식이라면, 제이슨 객체에 넣어서 파싱하면 됨!
                Log.d("회원가입 응답 확인", response);

                // 응답 종류
                // 1. join_success
                // 2. join_fail

                Toast toast = null;

                if (response.equals("join_success")) {
                    toast = Toast.makeText(getApplicationContext(), "회원가입 성공.", Toast.LENGTH_SHORT);
                    Intent intent = new Intent(sign_up.this, LoginActivity.class);
                    startActivity(intent);
                } else if (response.equals("join_fail")) {
                    toast = Toast.makeText(getApplicationContext(), "정보를 다시 확인해주세요.", Toast.LENGTH_SHORT);
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
                        Log.d("asdf", s);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("id", id);
                params.put("pw", pw);
                params.put("pw2", pw2);
                params.put("birthdate", birthdate);
                params.put("name", name);
                params.put("gender", gender);
                params.put("phone", phone);

                // params.put("json_data", 제이슨객체.toString());
                // 제이슨으로 보내는 경우는.. Array를 보낼 때 또는 VO(객체로 보낼때)

                return params;
            }
        };

        final StringRequest stringRequest_id_identify = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // 응답 종류
                // 1. can_use_id
                // 2. existed_id

                Toast toast = null;

                if (response.equals("can_use_id")) {
                    toast = Toast.makeText(getApplicationContext(), "사용할 수 있는 ID입니다.", Toast.LENGTH_SHORT);
                } else if (response.equals("existed_id")) {
                    toast = Toast.makeText(getApplicationContext(), "존재하는 ID입니다. 다른 ID를 입력해주세요.", Toast.LENGTH_SHORT);
                }
                toast.show();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("asdf", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", id);

                return params;
            }
        };

        stringRequest_join.setTag(TAG);
        stringRequest_id_identify.setTag(TAG);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id = edit_id.getText().toString();
                pw = edit_pw.getText().toString();
                pw2 = edit_pw2.getText().toString();
                phone = edit_phone.getText().toString();

                // 생년월일 받아서 계산하기!

                String temp_birthdate = edit_birthdate.getText().toString();
                String year = temp_birthdate.substring(0, 4);
                String month = temp_birthdate.substring(4, 6);
                String day = temp_birthdate.substring(6);
                // 실제 보낼 생년월일 값
                birthdate = year + "-" + month + "-" + day;

                name = edit_name.getText().toString();

                gender = radi_Group.getCheckedRadioButtonId() == R.id.radi_male ? "남" : "여";

                // 비밀번호 확인 -> 다를 경우
                if (!pw.equals(pw2)) {
                    Toast.makeText(getApplicationContext(), "비밀번호가 일치해야 합니다.", Toast.LENGTH_SHORT).show();
                } else {
                    requestQueue.add(stringRequest_join);
                    Log.d("asdf", "정보넘기기 시도");
                }
            }
        });


        btn_id_identify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestQueue.add(stringRequest_id_identify);
            }
        });
    }

    // 액티비티가 꺼지거나 사라졌을 때 큐삭제
    // 열었으니까 닫는 코드
    @Override
    protected void onStop() {
        super.onStop();
        if (requestQueue != null) {
            requestQueue.cancelAll(TAG);
        }
    }
}