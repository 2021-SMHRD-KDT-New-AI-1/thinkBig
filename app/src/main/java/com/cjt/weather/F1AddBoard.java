package com.cjt.weather;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;


public class F1AddBoard extends Fragment {

    int REQUEST_IMAGE_CODE = 1001;
    int REQUEST_EXTERNAL_STORAGE_PERMISSION = 1002;

    EditText edt_top, edt_bottom, edt_shoes, edt_acc, edt_content, edt_hashtag;
    ImageView img_weathericon, img_pic, img_post;

    String content, hashtag, top, bottom, shoes, acc;
    TextView tv_weather, tv_temper, tv_sense_temper, tv_wind, tv_humid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //앨범 접근 권한 설정 코드
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_EXTERNAL_STORAGE_PERMISSION);
            }
        } else {
        }

        // 앨범 접근 코드
        // 뷰 찾고 이미지 뷰에 이벤트 주기
        View view = inflater.inflate(R.layout.fragment_1, container, false);

        img_pic = view.findViewById(R.id.img_pic);
        img_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(in, REQUEST_IMAGE_CODE);
            }
        });

        // 변하는 날씨 정보 가져오는 코드
        img_weathericon = view.findViewById(R.id.img_grid_weatherIcon);
        tv_humid = view.findViewById(R.id.tv_humid);
        tv_sense_temper = view.findViewById(R.id.textview12);
        tv_temper = view.findViewById(R.id.tv_temper);
        tv_wind = view.findViewById(R.id.tv_wind);
        tv_weather = view.findViewById(R.id.tv_weather);
        // DB에서 값을 가져와서 변수에 날씨 값 저장하는 코드를 이 아래에 짜면 될 거 같아요.


        // 게시물 추가하는 버튼(값을 다 보내는 거)


        return view;
    }

    //접근한 결과를 나타내는 코드
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_IMAGE_CODE){
            Uri image = data.getData();
            try {
                Bitmap bitmap  = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), image);
                img_pic.setImageBitmap(bitmap);
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




