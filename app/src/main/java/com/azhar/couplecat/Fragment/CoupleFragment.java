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

import com.azhar.couplecat.Activity.AddCoupleActivity;
import com.azhar.couplecat.Activity.MainActivity;
import com.azhar.couplecat.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class CoupleFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        android.support.v7.app.ActionBar actionBar =
                ((MainActivity) getActivity()).getSupportActionBar();
        assert actionBar != null;
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_overlay_splash));
        actionBar.setTitle("Pasangan");
        View view = inflater.inflate(R.layout.fragment_couple, container, false);
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
    @OnClick(R.id.lyBtnCircle)
    public void lyBtnCircle(View view){
        Intent gotoaddcouple = new Intent(getActivity(),AddCoupleActivity.class);
        startActivity(gotoaddcouple);
    }
}
