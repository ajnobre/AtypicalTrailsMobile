package com.example.atypicaltrails.register.call;

public class RegisterUserData {
    public String username;
    public String password;
    public String email;
    public String name;
    public String address;
    public String mobile;
    public String tokenID;

    public RegisterUserData(String name, String username, String email, String phone, String address, String password) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.address = address;
        this.mobile = phone;
        this.tokenID = "";
    }
}