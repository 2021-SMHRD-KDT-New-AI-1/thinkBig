package com.cjt.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Edit_Profile extends AppCompatActivity {

    Button btn_edit4,btn_edit5,btn_edit6;
    TextView edt_pr_main,edt_pr_text,textView13,textView11,textView15,textView16,
            textView22,textView20,textView19;
    ImageView img_pr_img;
    EditText edt_pr_name,edt_pr_id,edt_pr_message,edt_pr_nick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);


        btn_edit4=findViewById(R.id.btn_edit4);
        btn_edit5=findViewById(R.id.btn_edit5);
        btn_edit6=findViewById(R.id.btn_edit6);

        edt_pr_nick=findViewById(R.id.edt_pr_nick);
        edt_pr_message=findViewById(R.id.edt_pr_message);

        btn_edit4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                edt_pr_nick.setText(edt_pr_nick.getText());
            }
        });
    }
}