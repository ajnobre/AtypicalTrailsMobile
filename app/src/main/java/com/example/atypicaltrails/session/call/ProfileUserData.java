package com.example.atypicaltrails.session.call;

public class ProfileUserData {
    public String username;
    public String password;
    public String email;
    public String name;
    public String address;
    public String mobile;
    public String tokenID;

    public ProfileUserData(String username, String tokenID) {
        this.username = username;
        this.password = "";
        this.name = "";
        this.email = "";
        this.address = "";
        this.mobile = "";
        this.tokenID = tokenID;
    }
}
