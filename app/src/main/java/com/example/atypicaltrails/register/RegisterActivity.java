package com.example.atypicaltrails.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.atypicaltrails.login.LoginActivity;
import com.example.atypicaltrails.serverApi.AtypicalServerApi;
import com.example.atypicaltrails.R;
import com.example.atypicaltrails.register.call.RegisterUserData;
import com.example.atypicaltrails.serverApi.ServerMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RegisterActivity extends AppCompatActivity {
    private AtypicalServerApi atypicalServerApi;
    private String baseUrl = "https://androidservertester2.appspot.com/rest/";
    private TextInputLayout textInputFirstLast;
    private TextInputLayout textInputUsername;
    private TextInputLayout textInputEmail;
    private TextInputLayout textInputPhone;
    private TextInputLayout textInputAddress;
    private TextInputLayout textInputPassword;
    private TextInputLayout textInputConfirmPassword;
//TODO POR MENSAGEM DE ERRO A FINAL

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        atypicalServerApi = retrofit.create(AtypicalServerApi.class);

        textInputFirstLast = findViewById(R.id.text_input_firstLast);
        textInputUsername = findViewById(R.id.text_input_username);
        textInputEmail = findViewById(R.id.text_input_email);
        textInputPhone = findViewById(R.id.text_input_phone);
        textInputAddress = findViewById(R.id.text_input_address);
        textInputPassword = findViewById(R.id.text_input_password);
        textInputConfirmPassword = findViewById(R.id.text_input_confirmPassword);


    }

    public boolean validateFirstLast() {
        String firstLastInput = textInputFirstLast.getEditText().getText().toString().trim();
        if (firstLastInput.isEmpty()) {
            textInputFirstLast.setError("Field can't be empty");
            return false;
        } else {
            textInputFirstLast.setError(null);
            return true;
        }
    }

    public boolean validateUsername() {
        String usernameInput = textInputUsername.getEditText().getText().toString().trim();
        if (usernameInput.isEmpty()) {
            textInputFirstLast.setError("Field can't be empty");
            return false;
        } else {
            textInputFirstLast.setError(null);
            return true;
        }
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

    public boolean validatePhone() {
        String phoneInput = textInputPhone.getEditText().getText().toString().trim();
        if (phoneInput.isEmpty()) {
            textInputEmail.setError("Field can't be empty");
            return false;
        } else {
            textInputEmail.setError(null);
            return true;
        }
    }

    public boolean validateAddress() {
        String addressInput = textInputPhone.getEditText().getText().toString().trim();
        if (addressInput.isEmpty()) {
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
            textInputEmail.setError("Field can't be empty");
            return false;
        } else {
            textInputEmail.setError(null);
            return true;
        }
    }

    public boolean validateConfimrPassword() {
        String confirmPasswordInput = textInputConfirmPassword.getEditText().getText().toString().trim();
        if (confirmPasswordInput.isEmpty()) {
            textInputEmail.setError("Field can't be empty");
            return false;
        } else {
            String passwordInput = textInputPassword.getEditText().getText().toString().trim();
            if (!confirmPasswordInput.equals(passwordInput)) {
                textInputConfirmPassword.setError("Passwords don't match");
                return false;
            } else {
                textInputEmail.setError(null);
                return true;
            }
        }
    }

    public void tryRegister() {
        RegisterUserData userData = new RegisterUserData(textInputFirstLast.getEditText().getText().toString().trim(), textInputUsername.getEditText().getText().toString().trim(),
                textInputEmail.getEditText().getText().toString().trim(), textInputPhone.getEditText().getText().toString().trim(), textInputAddress.getEditText().getText().toString().trim()
                , textInputPassword.getEditText().getText().toString().trim());

        Call<ServerMessage> registerCall = atypicalServerApi.registerUser(userData);
        registerCall.enqueue(new Callback<ServerMessage>() {
            @Override
            public void onResponse(Call<ServerMessage> call, Response<ServerMessage> response) {
                try {
                    //TODO ver se res é necessario para mais alguma cisa
                    ServerMessage msg;
                    JSONObject errorResponse;
                    switch (response.code()) {
                        case 200:
                            msg = response.body();
                            Toast.makeText(RegisterActivity.this, msg.getMsg(), Toast.LENGTH_SHORT).show();
                            openLoginActivity();
                            break;
                        case 400:
                            errorResponse = new JSONObject(response.errorBody().string());
                            textInputUsername.setError(errorResponse.getString("msg"));
                            break;
                        case 403:
                            errorResponse = new JSONObject(response.errorBody().string());
                            textInputEmail.setError(errorResponse.getString("msg"));
                            break;
                        default:
                            break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return;
            }

            @Override
            public void onFailure(Call<ServerMessage> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Registration failed, could not connect to the server.", Toast.LENGTH_SHORT).show();

            }
        });
        return;
    }

    public void openLoginActivity() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

    public void confirmInput(View v) {
        if (!validateFirstLast() | !validateUsername() | !validateEmail() | !validatePhone() | !validateAddress() | !validatePassword() | !validateConfimrPassword()) {
            return;

        }
        tryRegister();

    }


}
