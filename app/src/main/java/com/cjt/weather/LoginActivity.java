package com.cjt.weather;


import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
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


        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://172.30.1.28:3002/LoginTest";

                //JSON형식으로 데이터 통신을 진행합니다!
                JSONObject testjson = new JSONObject();
                try {
                    //입력해둔 edittext의 id와 pw값을 받아와 put해줍니다 : 데이터를 json형식으로 바꿔 넣어주었습니다.
                    testjson.put("id", et_id.getText().toString());
                    testjson.put("pw", et_pw.getText().toString());
                    String jsonString = testjson.toString(); //완성된 json 포맷

                    //이제 전송해볼까요?
                    final RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
                    final JsonObjectRequest jsonObjectRequest
                            = new JsonObjectRequest(
                                    Request.Method.POST,
                                    url,
                                    testjson,
                                    new Response.Listener<JSONObject>() {

                        //데이터 전달을 끝내고 이제 그 응답을 받을 차례입니다.
                        @Override
                        public void onResponse(JSONObject response) {
                            try {

                                //받은 json형식의 응답을 받아
                                JSONObject jsonObject = new JSONObject(response.toString());

                                //key값에 따라 value값을 쪼개 받아옵니다.
                                String resultId = jsonObject.getString("approve_id");
                                String resultPassword = jsonObject.getString("approve_pw");

                                //만약 그 값이 같다면 로그인에 성공한 것입니다.
                                if(resultId.equals("OK") & resultPassword.equals("OK")){

                                    //이 곳에 성공 시 화면이동을 하는 등의 코드를 입력하시면 됩니다.
                                }else{
                                    //로그인에 실패했을 경우 실행할 코드를 입력하시면 됩니다.
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        //서버로 데이터 전달 및 응답 받기에 실패한 경우 아래 코드가 실행됩니다.
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                            //Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    requestQueue.add(jsonObjectRequest);
                    //
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
