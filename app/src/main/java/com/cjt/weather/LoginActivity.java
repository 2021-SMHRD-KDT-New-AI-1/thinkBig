package com.cjt.weather;


import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    EditText et_id, et_pw;
    Button btn_login, btn_findid, btn_findpw, btn_join;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_id = findViewById(R.id.et_id);
        et_pw = findViewById(R.id.et_pw);
        btn_login = findViewById(R.id.btn_login);
        btn_findid = findViewById(R.id.btn_findid);
        btn_findpw = findViewById(R.id.btn_findpw);
        btn_join = findViewById(R.id.btn_join);

        SharedPreferences pref = getSharedPreferences("checkFirst", Activity.MODE_PRIVATE);
        boolean checkFirst = pref.getBoolean("checkFirst", false);
        if (checkFirst == false) {
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("checkFirst", true);
            editor.commit();

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {

        }

        btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, sign_up.class);
                startActivity(intent);
            }
        });


        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user_id = et_id.getText().toString();
                String user_pw = et_pw.getText().toString();

                String url = "http://172.30.1.29:3002/AllSelect";
                StringRequest request = new StringRequest(
                        Request.Method.GET,
                        url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {

                                    JSONArray jsonArray = new JSONArray(response);
                                    boolean found = false;
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject info = (JSONObject) jsonArray.get(i);

                                        String resultId = info.getString("id");
                                        String resultPassword = info.getString("pw");


                                        if (user_id.equals(resultId) && user_pw.equals(resultPassword)) {
                                            found = true;
                                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                            startActivity(intent);
                                        }
                                    }
                                    if (found == false) {
                                        Toast.makeText(getApplicationContext(), "ID와 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                error.printStackTrace();
                                Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
                requestQueue.add(request);
            }
        });
    }
}
