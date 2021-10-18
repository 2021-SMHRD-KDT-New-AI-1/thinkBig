package com.cjt.weather;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.URI;


public class F1AddBoard extends Fragment {

//    int REQUEST_IMAGE_CODE = 1001;
//    int REQUEST_EXTERNAL_STORAGE_PERMISSION = 1002;
//
//    Button btn_edit1, btn_edit2, btn_edit3;
//    TextView tv_hashtag, tv_top, tv_bottom, tv_shoes, tv_acc, tv_weather, tv_post;
//    ImageView img_weather, img_pic;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // 에러 나서 일단 주석 처리!!!!!!! 선생님께 여쭤볼게욥
        // 앨범 접근 권한 설정 코드
//        if (ContextCompat.checkSelfPermission(getActivity(),
//                Manifest.permission.READ_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED) {
//            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
//                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
//            } else {
//                ActivityCompat.requestPermissions(getActivity(),
//                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
//                        REQUEST_EXTERNAL_STORAGE_PERMISSION);
//            }
//        } else {
//        }
//
//        // 앨범 접근 코드
//        // 뷰 찾고 이미지 뷰에 이벤트 주기
//        View view = inflater.inflate(R.layout.fragment_1, container, false);
//        img_pic = view.findViewById(R.id.img_pic);
//        img_pic.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent in = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(in, REQUEST_IMAGE_CODE);
//            }
//        });
        return inflater.inflate(R.layout.fragment_1, container, false);
    }

    // 접근한 결과를 나타내는 코드
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == REQUEST_IMAGE_CODE){
//            Uri image = data.getData();
//            try {
//                Bitmap bitmap  = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), image);
//                img_pic.setImageBitmap(bitmap);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//    }
}





