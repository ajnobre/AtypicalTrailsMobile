package com.example.atypicaltrails.session;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.atypicaltrails.R;
import com.example.atypicaltrails.login.LoginActivity;
import com.example.atypicaltrails.serverApi.AtypicalServerApi;
import com.example.atypicaltrails.session.call.ProfileUserData;
import com.example.atypicaltrails.session.userRes.GetUserInfoReponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class ProfileFragment extends Fragment {
    private AtypicalServerApi atypicalServerApi;
    private String baseUrl = "https://androidservertester2.appspot.com/rest/";

    private Context mContext;

    private TextView textUsername;
    private TextView textCreated;
    private TextView textExecuted;
    private TextView textTotalHours;
    private TextView textFirstLast;
    private TextView textPhone;
    private TextView textAddress;
    private TextView textEmail;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TOKEN_ID = "TokenID";
    public static final String USER_ID = "UserID";

    private String tokenID = "";
    private String userID = "";

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        atypicalServerApi = retrofit.create(AtypicalServerApi.class);

        textUsername = v.findViewById(R.id.profileUsernameTextView);
        textEmail = v.findViewById(R.id.profileEmailTextView);
        textAddress = v.findViewById(R.id.profileAddressTextView);
        textFirstLast = v.findViewById(R.id.profileFirstLastTextView);
        textPhone = v.findViewById(R.id.profilePhoneTextView);
        textCreated = v.findViewById(R.id.profileCreatedTrailsTextView);
        textExecuted = v.findViewById(R.id.profileExecutedTextView);
        textTotalHours = v.findViewById(R.id.profileTotalHoursTextView);
        loadData();
        if (!tokenID.equals("")) {
            getUserInfo();
        }

        return v;
    }

    public void getUserInfo() {
        ProfileUserData userData = new ProfileUserData(userID, tokenID);

        Call<GetUserInfoReponse> getUserInfoCall = atypicalServerApi.getInfoUser(userData);

        getUserInfoCall.enqueue(new Callback<GetUserInfoReponse>() {
            @Override
            public void onResponse(Call<GetUserInfoReponse> call, Response<GetUserInfoReponse> response) {
                try {
                    GetUserInfoReponse res;
                    JSONObject errorResponse;
                    switch (response.code()) {
                        case 200:
                            res = response.body();
                            textUsername.setText(res.getKey().getName());
                            textAddress.setText(res.getPropertyMap().getAddress());
                            textEmail.setText(res.getPropertyMap().getEmail());
                            textFirstLast.setText(res.getPropertyMap().getName());
                            textPhone.setText(res.getPropertyMap().getMobile());
                            break;
                        case 403:
                            //TODO tratar token invalido, reeicaminhar para login
                            errorResponse = new JSONObject(response.errorBody().string());
                            openLoginActivity();
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
            public void onFailure(Call<GetUserInfoReponse> call, Throwable t) {

            }
        });

    }

    public void loadData() {
        this.mContext = getContext();
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        tokenID = sharedPreferences.getString(TOKEN_ID, "");
        userID = sharedPreferences.getString(USER_ID, "");
    }

    public void openLoginActivity() {
        this.mContext = getContext();
        Intent intent = new Intent(mContext, LoginActivity.class);
        startActivity(intent);
    }

}
