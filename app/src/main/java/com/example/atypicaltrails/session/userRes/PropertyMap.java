package com.example.atypicaltrails.session.userRes;

import com.google.gson.annotations.SerializedName;

public class PropertyMap {
    @SerializedName("Email")
    private String email;

    @SerializedName("Mobile")
    private String mobile;

    @SerializedName("Address")
    private String address;

    @SerializedName("user_creation_time")
    private CreationTime creationTime;

    @SerializedName("Name")
    private String name;

    @SerializedName("Password")
    private String password;

    public PropertyMap(String email, String mobile, String address, CreationTime creationTime, String name, String password) {
        this.email = email;
        this.mobile = mobile;
        this.address = address;
        this.creationTime = creationTime;
        this.name = name;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }
}
