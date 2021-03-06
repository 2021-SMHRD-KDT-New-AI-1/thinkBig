package com.cjt.weather;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity {

    int REQUEST_IMAGE_CODE = 1003;
    int REQUEST_EXTERNAL_STORAGE_PERMISSION = 1004;

    String url = "http://172.30.1.28:3002/Profile";
    RequestQueue requestQueue;
    SharedPreferences spf_user_info;
    SharedPreferences.Editor editor_user_info;
    ImageView pro_img;

    private static final String TAG = "MAIN";
    ImageButton btn_pr_edit;
    EditText ed_pro_nick, edt_pro_msg, edt_pro_hashtag;
    Button btn_pro_img;

    String id = "";
    String nick = "";
    String state_msg = "";
    String pro_tag = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        pro_img = findViewById(R.id.pro_img);
        btn_pr_edit = findViewById(R.id.btn_pr_edit);
        ed_pro_nick = findViewById(R.id.ed_pr_nick);
        edt_pro_msg = findViewById(R.id.edt_pr_msg);
        edt_pro_hashtag = findViewById(R.id.edt_pr_hashtag);
        btn_pro_img = findViewById(R.id.btn_pr_img);

        spf_user_info = getSharedPreferences("user_info", Context.MODE_PRIVATE);
        id = spf_user_info.getString("id", "default_id");
        // spf.getString("id", ?????? ????????? ???????????? ?????? ??? ????????? ??? ????????????);

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        final StringRequest stringRequest_edit_profile = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("profile ?????? ??????", response);
                // ?????? ??????
                // 1. edit_pro_success
                // 2. edit_pro_fail

                Toast toast = null;
                if (response.equals("edit_pro_success")) {
                    toast = Toast.makeText(getApplicationContext(), "????????? ?????? ??????.", Toast.LENGTH_SHORT);

                    // ????????? ??????????????? ???????????? ????????? ?????? ??????.
                    editor_user_info = spf_user_info.edit();
                    editor_user_info.putString("nick", nick);
                    editor_user_info.putString("state_msg", state_msg);
                    editor_user_info.putString("pro_tag", pro_tag);
                    editor_user_info.commit();

                    // ???????????? ???????????? ?????? ??????????????? ????????????.
                    Intent intent = new Intent(EditProfileActivity.this, MainActivity.class);
                    intent.putExtra("from", "edt_profile");
                    intent.putExtra("nick", nick);
                    intent.putExtra("state_msg", state_msg);
                    intent.putExtra("pro_tag", pro_tag);

                    startActivity(intent);

                } else if (response.equals("edit_pro_fail")) {
                    toast = Toast.makeText(getApplicationContext(), "????????? ?????? ??????", Toast.LENGTH_SHORT);
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
                        Log.d("profile ?????? ??????", s);
                    }
                }
        ) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("id", id);
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
                nick = ed_pro_nick.getText().toString();
                state_msg = edt_pro_msg.getText().toString();
                pro_tag = edt_pro_hashtag.getText().toString();

                requestQueue.add(stringRequest_edit_profile);
            }
        });

        //?????? ?????? ?????? ?????? ??????
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_EXTERNAL_STORAGE_PERMISSION);
            }
        } else {
        }

        // ????????? ?????? ????????? ????????? ??? ??????
        btn_pro_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(in, REQUEST_IMAGE_CODE);
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

    // ?????? ????????? ????????? ???????????? ??????
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CODE) {
            Uri image = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), image);
                pro_img.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


}