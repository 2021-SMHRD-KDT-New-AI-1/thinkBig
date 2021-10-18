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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "MAIN";
    EditText et_id, et_pw;
    Button btn_login, btn_join;
    RequestQueue requestQueue;
    SharedPreferences spf_user_info;
    SharedPreferences.Editor editor_user_info;
    List<UserVo> user_info_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_id = findViewById(R.id.et_id);
        et_pw = findViewById(R.id.et_pw);
        btn_login = findViewById(R.id.btn_login);
        btn_join = findViewById(R.id.btn_join);

        user_info_list = new ArrayList<UserVo>();

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        String url = "http://172.30.1.29:3002/Login";

        final StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // 받을 때는 스트링이다! 이거를 제이슨 객체에 넣던지 하면 돼!
                Log.d("로그인 응답 확인", response);
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject json_user_info = jsonObject.getJSONObject("jsonData");
                    JSONArray users_info = json_user_info.getJSONArray("id");

                    for (int i = 0; i < users_info.length(); i++) {
                        JSONObject user_info = (JSONObject) users_info.get(i);
                        // get을 쓰면, object를 리턴한다.
                        // json object로 다운 캐스팅

                        String id = user_info.getString("id");
                        String pw = user_info.getString("pw");
                        String name = user_info.getString("name");
                        String phone = user_info.getString("phone");
                        String gender = user_info.getString("gender");
                        String birth_date = user_info.getString("birth_date");
                        //String pro_img_path;
                        String nick = user_info.getString("nick");
                        String state_msg = user_info.getString("state_msg");
                        String pro_tag = user_info.getString("pro_tag");
                        Log.d("로그인 제이슨 확인", id);

                        UserVo vo = new UserVo(id, pw, name, phone, gender, birth_date,nick,state_msg, pro_tag);
                        user_info_list.add(vo);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // 응답 종류
                // 1. not_exit_id
                // 2. not_correct_pw
                // 3. login_success

                Toast toast = null;

                if (response.equals("not_exit_id")) {
                    toast = Toast.makeText(getApplicationContext(), "ID가 존재하지 않습니다.", Toast.LENGTH_SHORT);

                } else if (response.equals("not_correct_pw")) {
                    toast = Toast.makeText(getApplicationContext(), "비밀번호를 확인해주세요.", Toast.LENGTH_SHORT);

                } else if (response.equals("login_success")) {
                    toast = Toast.makeText(getApplicationContext(), "로그인 성공.", Toast.LENGTH_SHORT);

                    spf_user_info = getSharedPreferences("user_info", Context.MODE_PRIVATE);
                    editor_user_info = spf_user_info.edit();
                    editor_user_info.putString("id", et_id.getText().toString());
                    editor_user_info.commit();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("from", "Login");
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
                params.put("id", et_id.getText().toString());
                params.put("pw", et_pw.getText().toString());
                // params.put("json_data", 제이슨객체.toString());
                // 제이슨으로 보내는 경우는.. 어레이 보낼 때,, VO (객체로 보낼때)

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
