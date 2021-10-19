package com.cjt.weather;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private List<BoardVO> items;

    public PostAdapter(List<BoardVO> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PostViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.activity_submain, parent, false));
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

        /* // 얘들 선언해주기
               content; // xml에 없음
               board_tag; // xml에 없음
         */

        // 뷰 선언
        ImageView imgView;

        ImageButton btn_like;
        TextView tv_like_cnt;
        TextView tv_board_content;
        
        TextView tv_board_top;
        TextView tv_board_shoes;
        TextView tv_board_bottom;
        TextView tv_board_acc;

        PostViewHolder(@NonNull View itemView) {
            super(itemView);
            // 뷰 초기화
            imgView = itemView.findViewById(R.id.imgView);
            btn_like = itemView.findViewById(R.id.imageButtonLIke);
            tv_like_cnt = itemView.findViewById(R.id.tv_like_cnt);
            tv_board_content = itemView.findViewById(R.id.tv_board_content);
            
            tv_board_top = itemView.findViewById(R.id.tv_board_top);
            tv_board_shoes = itemView.findViewById(R.id.tv_profile_nick);
            tv_board_bottom = itemView.findViewById(R.id.tv_board_bottom);
            tv_board_acc = itemView.findViewById(R.id.tv_profile_state_msg);
        }

        void setPosImageView(BoardVO items) {
            // 각 아이템들 셋팅
            imgView.setImageResource(items.getImg());
            tv_like_cnt.setText(items.getLike_cnt());
            // tv_board_content.setText(items.get);

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
                    tv_like_cnt.setText("" + cnt);

                }
            });
        }
    }
}
