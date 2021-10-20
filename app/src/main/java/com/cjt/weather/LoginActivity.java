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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    // 뷰 선언
    EditText et_id, et_pw;
    Button btn_login, btn_join;
    RequestQueue requestQueue;
    private static final String TAG = "MAIN";
    SharedPreferences spf_user_info;
    SharedPreferences.Editor editor_user_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 뷰 초기화
        et_id = findViewById(R.id.et_id);
        et_pw = findViewById(R.id.et_pw);
        btn_login = findViewById(R.id.btn_login);
        btn_join = findViewById(R.id.btn_join);

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        String url = "http://172.30.1.29:3002/Login";

        final StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String result = "";

                // 로그인 성공 했을 때 사용자 정보를 가져와서 SharedPreferences 에 저장.
                try {
                    JSONObject object = new JSONObject(response);

                    result = object.getString("result");
                    String id = object.getString("id");
                    String pw = object.getString("pw");
                    String name = object.getString("name");
                    String phone = object.getString("phone");
                    String gender = object.getString("gender");
                    String birthdate = object.getString("birth_date");
                    //String pro_img_path = object.getString("pro_img_path");
                    String nick = object.getString("nick");
                    String state_msg = object.getString("state_msg");
                    String pro_tag = object.getString("pro_tag");

                    // SharedPreferences 에 로그인한 사용자 정보 셋팅.
                    spf_user_info = getSharedPreferences("user_info", Context.MODE_PRIVATE);
                    editor_user_info = spf_user_info.edit();
                    editor_user_info.putString("id", id);
                    editor_user_info.putString("pw", pw);
                    editor_user_info.putString("name", name);
                    editor_user_info.putString("phone", phone);
                    editor_user_info.putString("gender", gender);
                    editor_user_info.putString("birthdate", birthdate);
                    editor_user_info.putString("nick", nick);
                    editor_user_info.putString("state_msg", state_msg);
                    editor_user_info.putString("pro_tag", pro_tag);
                    editor_user_info.commit();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // 응답 종류
                // 1. not_exit_id
                // 2. not_correct_pw
                // 3. login_success -> 제이슨으로 받아온다. "result":"login_success"

                Toast toast = null;

                if (response.equals("not_exit_id")) {
                    toast = Toast.makeText(getApplicationContext(), "ID가 존재하지 않습니다.", Toast.LENGTH_SHORT);

                } else if (response.equals("not_correct_pw")) {
                    toast = Toast.makeText(getApplicationContext(), "비밀번호를 확인해주세요.", Toast.LENGTH_SHORT);

                    //} else if (response.equals("login_success")) {
                } else if (result.equals("login_success")) {
                    toast = Toast.makeText(getApplicationContext(), "로그인 성공.", Toast.LENGTH_SHORT);

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("from", "Login");
                    startActivity(intent);
                    // 로그인 성공했을 경우 메인액티비티로 보내준 후 프래그먼트2로 보내줄 수 있도록
                    // key값과 value를 같이 준다.
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
                params.put("id", et_id.getText().toString());
                params.put("pw", et_pw.getText().toString());

                return params;
            }
        };

        stringRequest.setTag(TAG);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestQueue.add(stringRequest);
            }
        });

        btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
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
