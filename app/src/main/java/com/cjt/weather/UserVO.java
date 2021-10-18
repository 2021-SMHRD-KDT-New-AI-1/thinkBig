package com.cjt.weather;

public class UserVO {

    String id;
    String pw;
    String name;
    String phone;
    String gender;
    String birth_date;

    //String pro_img_path;
    String nick;
    String state_msg;
    String pro_tag;

    public UserVO(String id, String pw, String name, String phone, String gender, String birth_date, String nick, String state_msg, String pro_tag) {
        this.id = id;
        this.pw = pw;
        this.name = name;
        this.phone = phone;
        this.gender = gender;
        this.birth_date = birth_date;
        this.nick = nick;
        this.state_msg = state_msg;
        this.pro_tag = pro_tag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

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

    public String getPro_tag() {
        return pro_tag;
    }

    public void setPro_tag(String pro_tag) {
        this.pro_tag = pro_tag;
    }
}
