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
import com.azhar.couplecat.Activity.ForgetPasswordActivity;
import com.azhar.couplecat.Activity.LoginActivity;
import com.azhar.couplecat.R;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class InformationFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_information, container, false);
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
    @OnClick(R.id.lyInformation)
    public void lyInformation(View view){
        Intent gotodetail = new Intent(getActivity(),DetailActivity.class);
        startActivity(gotodetail);
    }
}
