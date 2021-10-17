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

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = "MAIN";
    EditText edit_id, edit_pw, edit_pw2, edit_birthdate, edit_name, edit_phone;
    RadioButton radio_male, radio_female;
    RadioGroup radioGroup;
    Button btn_id_identify, btn_register;
    RequestQueue requestQueue;

    // 이너클래스에서 써야해서 전역으로 선언!
    String id = "";
    String pw = "";
    String birthdate = "";
    String name = "";
    String gender = "";
    String phone = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // 초기화
        edit_id = findViewById(R.id.edit_id);
        edit_pw = findViewById(R.id.edit_pw);
        edit_pw2 = findViewById(R.id.edit_pw2);
        edit_birthdate = findViewById(R.id.edit_birthdate);
        edit_name = findViewById(R.id.edit_name);
        radio_male = findViewById(R.id.radi_male);
        radio_female = findViewById(R.id.radi_female);
        radioGroup = findViewById(R.id.radi_Group);
        edit_phone = findViewById(R.id.edit_phone);
        btn_id_identify = findViewById(R.id.btn_id_identify);
        btn_register = findViewById(R.id.btn_register);

        // 큐
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        String url = "http://172.30.1.29:3002/SignUp";

        // 아이디 확인을 위한 값전달.
        final StringRequest stringRequest_id_identify = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("ID 확인 응답 확인", response);
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
                        Writer writer = new StringWriter();
                        error.printStackTrace(new PrintWriter(writer));
                        String s = writer.toString();
                        Log.d("ID 확인 응답 오류", s);
                    }
                }
        ) {
            // ID값 전달.
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("btn", "btnid");
                params.put("id", id);

                return params;
            }
        };

        final StringRequest stringRequest_join = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("회원가입 응답 확인", response);
                // 응답 종류
                // 1. join_success
                // 2. join_fail

                Toast toast = null;
                if (response.equals("join_success")) {
                    toast = Toast.makeText(getApplicationContext(), "회원가입 성공.", Toast.LENGTH_SHORT);
                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
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
                        Log.d("회원가입 오류", s);
                    }
                }
        ) {
            // 회원가입 값 전달.
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("btn", "btnjoin");
                params.put("id", id);
                params.put("pw", pw);
                params.put("birthdate", birthdate);
                params.put("name", name);
                params.put("gender", gender);
                params.put("phone", phone);

                return params;
            }
        };

        stringRequest_id_identify.setTag(TAG);
        stringRequest_join.setTag(TAG);


        btn_id_identify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("test", "onClick: 아이디 확인");
                id = edit_id.getText().toString();
                edit_id.setText(id);
                requestQueue.add(stringRequest_id_identify);
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Log.d("test", "onClick: 회원가입 확인");
                id = edit_id.getText().toString();
                pw = edit_pw.getText().toString();
                String pw2 = edit_pw2.getText().toString();
                phone = edit_phone.getText().toString();

                String temp_birthdate = edit_birthdate.getText().toString();
                // 실제 보낼 생년월일 값

                name = edit_name.getText().toString();
                gender = radioGroup.getCheckedRadioButtonId() == R.id.radi_male ? "남" : "여";

                // 값을 모두 입력했을 경우에만!!
                if (id.length() != 0 && pw.length() != 0 && pw2.length() != 0 && phone.length() != 0 && temp_birthdate.length() != 0 && name.length() != 0 && gender.length() != 0) {
                    // 비밀번호 확인 -> 다를 경우
                    if (!pw.equals(pw2)) {
                        Toast.makeText(getApplicationContext(), "비밀번호가 일치해야 합니다.", Toast.LENGTH_SHORT).show();
                    } else if (temp_birthdate.length() != 8) {
                        Toast.makeText(getApplicationContext(), "생년월일을 확인해주세요.", Toast.LENGTH_SHORT).show();
                    } else if (phone.length() != 11) {
                        Toast.makeText(getApplicationContext(), "전화번호를 확인해주세요.", Toast.LENGTH_SHORT).show();
                    } else {
                        // 생년월일 받아서 계산하기!
                        String year = temp_birthdate.substring(0, 4);
                        String month = temp_birthdate.substring(4, 6);
                        String day = temp_birthdate.substring(6);
                        birthdate = year + "-" + month + "-" + day;
                        requestQueue.add(stringRequest_join);
                    }
                } else {
                    Log.d("test", "회원정보 입력안됨.");
                    Toast.makeText(getApplicationContext(), "회원정보를 모두 입력해주세요.", Toast.LENGTH_SHORT).show();
                }

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