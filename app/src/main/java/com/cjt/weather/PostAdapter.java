package com.cjt.weather;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private List<BoardVO> items;

    public PostAdapter(List<BoardVO> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PostViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.activity_submain,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        holder.setPosImageView(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class PostViewHolder extends RecyclerView.ViewHolder {

        /* // 얘네 선언해주기
                private String content; // xml에 없음
                //private String board_tag; // xml에 없음
                private String like_cnt;

                private String top;
                private String bottom;
                private String shoes;
         */

        //원래 도현이 코드 포스트_아이템_컨테이너에 있음.
        //RoundedImageView posImageView; //이미지
        ImageView imgView;

        ImageButton btn_like;
        TextView tv_like_cnt;

        TextView tv_board_top;
        TextView tv_board_shoes;
        TextView tv_board_bottom;
        TextView tv_board_acc;


        PostViewHolder(@NonNull View itemView) {
            super(itemView);
            //도현이코드
            //posImageView = itemView.findViewById(R.id.imagePost);
            imgView = itemView.findViewById(R.id.imgView);
            btn_like = itemView.findViewById(R.id.imageButtonLIke);
            tv_like_cnt = itemView.findViewById(R.id.tv_like_cnt);

            tv_board_top = itemView.findViewById(R.id.tv_board_top);
            tv_board_shoes = itemView.findViewById(R.id.tv_board_shoes);
            tv_board_bottom = itemView.findViewById(R.id.tv_board_bottom);
            tv_board_acc = itemView.findViewById(R.id.tv_board_acc);


        }

        void setPosImageView(BoardVO items) {

            //여기에 코드 입력해서
            //posImageView.setImageResource(postTitem.getImage());
            imgView.setImageResource(items.getImg());
            tv_like_cnt.setText(items.getLike_cnt());

            tv_board_top.setText(items.getTop());
            tv_board_shoes.setText(items.getShoes());
            tv_board_bottom.setText(items.getBottom());
            tv_board_acc.setText(items.getAcc());



            btn_like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    btn_like.setImageResource(R.drawable.like_change);
                    int cnt = Integer.parseInt(items.getLike_cnt().toString());
                    cnt++;
                    tv_like_cnt.setText(""+cnt);

                }
            });
        }
    }
}
