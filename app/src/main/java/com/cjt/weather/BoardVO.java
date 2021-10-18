package com.cjt.weather;

import android.widget.ImageView;

public class BoardVO {

    // 밑에 두개는 게시물의 아이디와 조인해서 같이 보내주세요.
    //private String nick;
    //private String state_msg;

    //private String board_img_path;
    private int img;

    private String content;
    //private String board_tag;

    private String like_cnt;
    // private String comment; // 댓글

    // 옷정보
    private String top;
    private String bottom;
    private String shoes;
    private String acc;

    public BoardVO(int img, String content, String board_tag, String like_cnt, String top, String bottom, String shoes, String acc) {
        this.img = img;
        this.content = content;
        //this.board_tag = board_tag;
        this.like_cnt = like_cnt;
        this.top = top;
        this.bottom = bottom;
        this.shoes = shoes;
        this.acc = acc;
    }

    public int getImg() {
        return img;
    }

    public String getAcc() {
        return acc;
    }

    public void setAcc(String acc) {
        this.acc = acc;
    }

    public void setImg(int img) {
        this.img = img;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
/*
    public String getBoard_tag() {
        return board_tag;
    }

    public void setBoard_tag(String board_tag) {
        this.board_tag = board_tag;
    }
*/
    public String getLike_cnt() {
        return like_cnt;
    }

    public void setLike_cnt(String like_cnt) {
        this.like_cnt = like_cnt;
    }

    public String getTop() {
        return top;
    }

    public void setTop(String top) {
        this.top = top;
    }

    public String getBottom() {
        return bottom;
    }

    public void setBottom(String bottom) {
        this.bottom = bottom;
    }

    public String getShoes() {
        return shoes;
    }

    public void setShoes(String shoes) {
        this.shoes = shoes;
    }
}
