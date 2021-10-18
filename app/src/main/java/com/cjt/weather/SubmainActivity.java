package com.cjt.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SubmainActivity extends AppCompatActivity {

    Button imageButtonLIke;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submain);


        imageButtonLIke.findViewById(R.id.imageButtonLIke);
        imageButtonLIke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {





            }
        });


    }
}