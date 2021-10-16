package com.cjt.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class Edit_Profile extends AppCompatActivity {

    private static final String TAG = "MAIN";
    ImageButton btn_pr_edit;
    EditText ed_pr_nick, edt_pr_msg, edt_pr_hashtag;
    Button btn_pro_img;
    RequestQueue requestQueue;

    String pro_img_path = "";
    String nick = "";
    String state_msg = "";
    String pro_tag = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        btn_pr_edit = findViewById(R.id.btn_pr_edit);
        ed_pr_nick = findViewById(R.id.ed_pr_nick);
        edt_pr_msg = findViewById(R.id.edt_pr_msg);
        edt_pr_hashtag = findViewById(R.id.edt_pr_hashtag);
        btn_pro_img = findViewById(R.id.btn_pro_img);

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        String url = "http://172.30.1.29:3002/Profile";

        final StringRequest stringRequest_edit_profile = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("profile 응답 확인", response);
                // 응답 종류
                // 1. edit_pro_success
                // 2. edit_pro_fail

                Toast toast = null;
                if (response.equals("edit_pro_success")) {
                    toast = Toast.makeText(getApplicationContext(), "프로필 설정 완료.", Toast.LENGTH_SHORT);
                } else if (response.equals("edit_pro_fail")) {
                    toast = Toast.makeText(getApplicationContext(), "프로필 설정 실패", Toast.LENGTH_SHORT);
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
                        Log.d("profile 오류 확인", s);
                    }
                }
        ) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("pro_img_path", pro_img_path);
                params.put("nick", nick);
                params.put("state_msg", state_msg);
                params.put("pro_tag", pro_tag);

                return params;
            }
        };

        stringRequest_edit_profile.setTag(TAG);

        btn_pr_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pro_img_path = btn_pro_img.getText().toString();
                nick = ed_pr_nick.getText().toString();
                state_msg = edt_pr_msg.getText().toString();
                pro_tag = edt_pr_hashtag.getText().toString();

                requestQueue.add(stringRequest_edit_profile);

                Intent intent = new Intent(Edit_Profile.this, MainActivity.class);
                intent.putExtra("from", "edt_profile");
                // 다른 액티비티에 값을 이렇게 넘겨주는구나!
                startActivity(intent);
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