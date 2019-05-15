package com.example.atypicaltrails.session;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.atypicaltrails.R;


public class ProfileFragment extends Fragment {
    private TextView textUsername;
    private TextView textCreated;
    private TextView textExecuted;
    private TextView textTotalHours;
    private TextView textFirstLast;
    private TextView textPhone;
    private TextView textAddress;
    private TextView textEmail;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        textUsername = v.findViewById(R.id.profileUsernameTextView);
        textEmail = v.findViewById(R.id.profileEmailTextView);
        textAddress = v.findViewById(R.id.profileAddressTextView);
        textFirstLast = v.findViewById(R.id.profileFirstLastTextView);
        textPhone = v.findViewById(R.id.profilePhoneTextView);
        textCreated = v.findViewById(R.id.profileCreatedTrailsTextView);
        textExecuted = v.findViewById(R.id.profileExecutedTextView);
        textTotalHours = v.findViewById(R.id.profileTotalHoursTextView);


        return v;
    }
    /*
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        View profileView = v.findViewById(R.id.nav_profile);
        textUsername = profileView.findViewById(R.id.profileUsernameTextView);
        textCreated = profileView.findViewById(R.id.profileCreatedTrailsTextView);
        textExecuted = profileView.findViewById(R.id.profileExecutedTextView);
        textTotalHours = profileView.findViewById(R.id.profileTotalHoursTextView);
        textFirstLast = profileView.findViewById(R.id.profileFirstLastTextView);
        textAddress = profileView.findViewById(R.id.profileAddressTextView);
        textPhone = profileView.findViewById(R.id.profilePhoneTextView);
        textEmail = profileView.findViewById(R.id.profileEmailTextView);


        return v;
    }
    */
}
