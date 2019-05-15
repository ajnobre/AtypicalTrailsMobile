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

import com.example.atypicaltrails.MainActivity;
import com.example.atypicaltrails.R;

public class EndSessionFragment extends Fragment {

    private Context mContext;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TOKEN_ID = "TokenID";
    public static final String USER_ID = "UserID";

    private String tokenID = "";
    private String userID = "";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ///View v = inflater.inflate(R.layout.fragment_endsession, container, false);
        saveData();
        openSessionActivity();
        return null;
    }

    public void saveData() {
        this.mContext = getContext();

        SharedPreferences sharedPreferences = mContext.getSharedPreferences(TOKEN_ID,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TOKEN_ID, "");
        editor.putString(USER_ID, "");
        editor.apply();
    }

    public void openSessionActivity() {
        Intent intent = new Intent(mContext, MainActivity.class);
        startActivity(intent);
    }
}
