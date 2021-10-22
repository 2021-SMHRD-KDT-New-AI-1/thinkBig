package com.cjt.weather;

public class BoardVO {

    // 밑에 두개는 게시물의 아이디와 조인해서 같이 보내주세요.
    private String nick;

    private String state_msg;

    //private String board_img_path;
    // 일단은 드로어블 폴더에서 가져올수 있도록 임시 변수!
    // 사진 받아오면 위에꺼로 갈아끼우기!
    private int img;

    private String content;

    // xml 아직 안만들었네!
    //private String board_tag;

    private String like_cnt;

    // 댓글 기능은 할 수 있을 지 모르겠음!
    // private String comment;

    // 옷정보
    private String top;
    private String bottom;
    private String shoes;
    private String acc;

    public BoardVO(String nick, String state_msg, int img, String content, String like_cnt, String top, String bottom, String shoes, String acc) {
        this.nick = nick;
        this.state_msg = state_msg;
        //this.board_img_path = board_img_path;
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
    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getState_msg() {
        return state_msg;
    }

    public void setState_msg(String state_msg) {
        this.state_msg = state_msg;
    }

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
