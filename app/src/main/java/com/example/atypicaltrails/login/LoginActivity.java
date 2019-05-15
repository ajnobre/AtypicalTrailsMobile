package com.example.atypicaltrails.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.atypicaltrails.R;
import com.example.atypicaltrails.login.call.LoginUserData;
import com.example.atypicaltrails.serverApi.AtypicalServerApi;
import com.example.atypicaltrails.serverApi.ServerMessage;
import com.example.atypicaltrails.session.SessionActivity;

import org.json.JSONException;
import org.json.JSONObject;

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
    private TextInputLayout textInputUsername;
    private TextInputLayout textInputPassword;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TOKEN_ID = "TokenID";
    public static final String USER_ID = "UserID";


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

        textInputUsername = findViewById(R.id.text_input_username);
        textInputPassword = findViewById(R.id.text_input_password);

    }

    public boolean validateEmail() {
        String emailInput = textInputUsername.getEditText().getText().toString().trim();
        if (emailInput.isEmpty()) {
            textInputUsername.setError("Field can't be empty");
            return false;
        } else {
            textInputUsername.setError(null);
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
        LoginUserData userData = new LoginUserData(textInputUsername.getEditText().getText().toString().trim(), textInputPassword.getEditText().getText().toString().trim());

        Call<ServerMessage> loginCall = atypicalServerApi.loginUser(userData);

        loginCall.enqueue(new Callback<ServerMessage>() {
            @Override
            public void onResponse(Call<ServerMessage> call, Response<ServerMessage> response) {
                try {
                    ServerMessage msg;
                    JSONObject errorResponse;
                    switch (response.code()) {
                        case 200:
                            msg = response.body();
                            saveData(msg.getMsg(), textInputUsername.getEditText().getText().toString().trim());
                            Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                            openSessionActivity();
                            break;
                        case 403:
                            errorResponse = new JSONObject(response.errorBody().string());
                            textInputPassword.setError(errorResponse.getString("msg"));
                            break;
                        default:
                            break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ServerMessage> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Login failed, could not connect to the server.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void openSessionActivity() {
        Intent intent = new Intent(getApplicationContext(), SessionActivity.class);
        startActivity(intent);
    }

    public void saveData(String token, String username) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TOKEN_ID, token);
        editor.putString(USER_ID, username);
        editor.apply();
    }

    public void confirmInput(View v) {
        if (!validateEmail() | !validatePassword()) {
            return;
        }
        tryLogin();
    }


}

