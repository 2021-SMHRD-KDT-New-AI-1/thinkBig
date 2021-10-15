package com.cjt.weather;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {


    //여기에 선언??

    private List<PostTitem> postTitems;

    public PostAdapter(List<PostTitem> postTitems) {
        this.postTitems = postTitems;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PostViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.post_item_container,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        holder.setPosImageView(postTitems.get(position));

    }

    @Override
    public int getItemCount() {
        return postTitems.size();
    }
    //크기

    class PostViewHolder extends RecyclerView.ViewHolder{

        RoundedImageView posImageView;
        TextView textView;

        PostViewHolder(@NonNull View itemView) {
            super(itemView);
            posImageView = itemView.findViewById(R.id.imagePost);
            textView = itemView.findViewById(R.id.post_item_text);
        }
        void setPosImageView(PostTitem postTitem){

            //여기에 코드 입력해서
            posImageView.setImageResource(postTitem.getImage());
            textView.setText(postTitem.getText());
        }
    }
}
