package com.example.atypicaltrails.serverApi;

import android.service.autofill.UserData;

import com.example.atypicaltrails.login.call.LoginUserData;
import com.example.atypicaltrails.register.call.RegisterUserData;
import com.example.atypicaltrails.session.call.ProfileUserData;
import com.example.atypicaltrails.session.userRes.GetUserInfoReponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AtypicalServerApi {
    @POST("register/v2")
    Call<ServerMessage> registerUser(@Body RegisterUserData userData);

    @POST("login/user")
    Call<ServerMessage> loginUser(@Body LoginUserData userData);

    @POST("login/getInfo")
    Call<GetUserInfoReponse> getInfoUser(@Body ProfileUserData userData);

    

}
