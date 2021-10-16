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

public class Edit_Profile extends AppCompatActivity {

    ImageButton btn_pr_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        btn_pr_edit = findViewById(R.id.btn_pr_edit);

        btn_pr_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Edit_Profile.this, MainActivity.class);
                intent.putExtra("from", "edt_profile");
                // 다른 액티비티에 값을 이렇게 넘겨주는구나!
                startActivity(intent);
            }
        });

    }
}