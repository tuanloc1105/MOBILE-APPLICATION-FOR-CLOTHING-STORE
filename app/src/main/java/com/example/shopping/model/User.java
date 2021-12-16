package com.example.shopping.model;

public class User {

    private String fullname = "", username = "", password = "", postion, phonenum = "", email = "";

    public User(String fullname, String username, String password, String postion, String phonenum, String email) {
        this.fullname = fullname;
        this.username = username;
        this.password = password;
        this.postion = postion;
        this.phonenum = phonenum;
        this.email = email;
    }

    public User(){}

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPostion(String postion) {
        this.postion = postion;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getPostion() {
        return postion;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "User{" +
                "fullname='" + fullname + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", postion='" + postion + '\'' +
                ", phonenum='" + phonenum + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
