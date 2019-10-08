package com.azhar.couplecat.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.azhar.couplecat.Activity.ChangePasswordActivity;
import com.azhar.couplecat.Activity.LoginActivity;
import com.azhar.couplecat.Activity.MyAccountActivity;
import com.azhar.couplecat.Activity.MyCatActivity;
import com.azhar.couplecat.Activity.ContestActivity;
import com.azhar.couplecat.Activity.MyFriendsActivity;
import com.azhar.couplecat.Activity.MyScheduleActivity;
import com.azhar.couplecat.Activity.MyStoreActivity;
import com.azhar.couplecat.Activity.MyWalletActivity;
import com.azhar.couplecat.Activity.PersonalDataActivity;
import com.azhar.couplecat.R;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class AccountFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
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
    @OnClick(R.id.tvPersonal)
    public void tvPersonal(View view){
        Intent gotopersonal = new Intent(getActivity(),PersonalDataActivity.class);
        startActivity(gotopersonal);
    }
    @OnClick(R.id.lyBtnPassword)
    public void tvBtnPassword(View view){
        Intent gotopassword = new Intent(getActivity(),ChangePasswordActivity.class);
        startActivity(gotopassword);
    }

    @OnClick(R.id.lyBtnMyWallet)
    public void lyBtnMyWallet(View view){
        Intent gotowallet = new Intent(getActivity(),MyWalletActivity.class);
        startActivity(gotowallet);
    }
    @OnClick(R.id.lyBtnMyFriend)
    public void lyBtnMyFriend(View view){
        Intent gotofriend = new Intent(getActivity(),MyFriendsActivity.class);
        startActivity(gotofriend);
    }
    @OnClick(R.id.lyBtnMyAccount)
    public void lyBtnMyAccount(View view){
        Intent gotoaccount = new Intent(getActivity(),MyAccountActivity.class);
        startActivity(gotoaccount);
    }
    @OnClick(R.id.lyBtnMySchedule)
    public void lyBtnMySchedule(View view){
        Intent gotoschedule = new Intent(getActivity(),MyScheduleActivity.class);
        startActivity(gotoschedule);
    }
    @OnClick(R.id.lyBtnMyEvent)
    public void lyBtnMyEvent(View view){
        Intent gotoevent = new Intent(getActivity(),ContestActivity.class);
        startActivity(gotoevent);
    }
    @OnClick(R.id.lyBtnMyCat)
    public void lyBtnMyCat(View view){
        Intent gotocat = new Intent(getActivity(),MyCatActivity.class);
        startActivity(gotocat);
    }
    @OnClick(R.id.lyBtnMyStore)
    public void lyBtnMyStore(View view){
        Intent gotostore = new Intent(getActivity(),MyStoreActivity.class);
        startActivity(gotostore);
    }
    @OnClick(R.id.lyBtnLogout)
    public void lyBtnLogout(View view){
        Intent gotologin = new Intent(getActivity(),LoginActivity.class);
        startActivity(gotologin);
        getActivity().finish();
    }
}
