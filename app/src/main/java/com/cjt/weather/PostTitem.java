package com.cjt.weather;

public class PostTitem {

    //인터넷에서 이미지를 로드하려는 경우, 이미지 URL을 저장하기 위해 변수를 문자열링할 수 있다**
    //String url 형태와 같은 걸로

    private int image;
    private String text;

    public PostTitem(int image, String text) {
        this.image = image;
        this.text = text;

    }

    public String getText() {

        return text;
    }

    public int getImage() {

        return image;
    }
}
