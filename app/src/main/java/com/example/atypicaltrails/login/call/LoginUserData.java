package com.example.atypicaltrails.login.call;

public class LoginUserData {
    public String username;
    public String password;
    public String email;
    public String name;
    public String address;
    public String mobile;
    public String tokenID;

    public LoginUserData(String username, String password) {
        this.username = username;
        this.password = password;
        this.name = "";
        this.email = "";
        this.address = "";
        this.mobile = "";
        this.tokenID = "";
    }
}