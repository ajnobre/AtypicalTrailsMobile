package com.example.atypicaltrails.login;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.atypicaltrails.R;
import com.example.atypicaltrails.login.call.LoginUserData;
import com.example.atypicaltrails.serverApi.AtypicalServerApi;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class LoginActivity extends AppCompatActivity {
    private AtypicalServerApi atypicalServerApi;
    private String baseUrl = "https://androidservertester2.appspot.com/rest/";
    private TextInputLayout textInputEmail;
    private TextInputLayout textInputPassword;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TOKEN_ID = "TokenID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        atypicalServerApi = retrofit.create(AtypicalServerApi.class);

        textInputEmail = findViewById(R.id.text_input_email);
        textInputPassword = findViewById(R.id.text_input_password);

    }

    public boolean validateEmail() {
        String emailInput = textInputEmail.getEditText().getText().toString().trim();
        if (emailInput.isEmpty()) {
            textInputEmail.setError("Field can't be empty");
            return false;
        } else {
            textInputEmail.setError(null);
            return true;
        }
    }

    public boolean validatePassword() {
        String passwordInput = textInputPassword.getEditText().getText().toString().trim();
        if (passwordInput.isEmpty()) {
            textInputPassword.setError("Field can't be empty");
            return false;
        } else {
            textInputPassword.setError(null);
            return true;
        }
    }

    public void tryLogin() {
        LoginUserData userData = new LoginUserData(textInputEmail.getEditText().getText().toString().trim(), textInputPassword.getEditText().getText().toString().trim());
        atypicalServerApi.loginUser(userData);

        Call<String> loginCall = atypicalServerApi.loginUser(userData);
        loginCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    switch (response.code()) {
                        case 403:
                            textInputPassword.setError(response.errorBody().string());
                            break;
                        case 200:
                            saveData(response.body());
                            //TODO encaminha para o perfil
                            Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Login failed, could not connect to the server.", Toast.LENGTH_SHORT).show();

            }
        });

    }


    public void saveData(String token) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TOKEN_ID, token);
        editor.apply();
    }

    public void confirmInput(View v) {
        if (!validateEmail() | !validatePassword()) {
            return;
        }
        tryLogin();
    }


}

