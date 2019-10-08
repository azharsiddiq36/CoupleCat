package com.azhar.couplecat.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.azhar.couplecat.Activity.DetailActivity;
import com.azhar.couplecat.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class PersonalDataKtpFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_data_ktp, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        android.support.v7.app.ActionBar actionBar =
//                ((MainActivity) getActivity()).getSupportActionBar();
//        assert actionBar != null;actionBar.setTitle("Profile");

    }

}