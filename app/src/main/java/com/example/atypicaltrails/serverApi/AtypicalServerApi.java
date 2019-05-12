package com.example.atypicaltrails.serverApi;

import android.service.autofill.UserData;

import com.example.atypicaltrails.login.call.LoginUserData;
import com.example.atypicaltrails.register.call.RegisterUserData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AtypicalServerApi {
    @POST("register/v2")
    Call<String> registerUser(@Body RegisterUserData userData);

    @POST("login/user")
    Call<String> loginUser(@Body LoginUserData userData);

}
