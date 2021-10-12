package com.cjt.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class sign_up extends AppCompatActivity {

    EditText edit_id, edit_pw, edit_pw2, edit_name;
    Button btn_register;
    RadioButton radi_male, radi_female;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edit_id = findViewById(R.id.edit_id);
        edit_pw = findViewById(R.id.edit_pw);
        edit_pw2 = findViewById(R.id.edit_pw2);
        edit_name = findViewById(R.id.edit_name);
        btn_register = findViewById(R.id.btn_register);
        radi_male = findViewById(R.id.radi_male);
        radi_female = findViewById(R.id.radi_female);

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = edit_id.getText().toString();
                String pw = edit_pw.getText().toString();
                String pw2 = edit_pw2.getText().toString();
                String name = edit_name.getText().toString();
                String url = "http://172.30.1.29:3002/Gaip?id=";
                url += id;
                url += "&pw=" + pw;
                url += "&name=" + name;

                StringRequest request = new StringRequest(
                        Request.Method.GET,
                        url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }

                );
                requestQueue.add(request);
                if (!pw.equals(pw2)) {
                    Toast.makeText(getApplicationContext(),"비밀번호가 일치해야 합니다.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(),"회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(sign_up.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });


    }
}