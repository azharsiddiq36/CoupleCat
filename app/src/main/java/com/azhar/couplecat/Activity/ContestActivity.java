package com.azhar.couplecat.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.azhar.couplecat.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contest);
        ButterKnife.bind(this);
    }
    @OnClick(R.id.lyMyContest)
    public void lyMyContest (View view){
        Intent gotomycontest = new Intent(ContestActivity.this,MyContestActivity.class);
        startActivity(gotomycontest);
    }
    @OnClick(R.id.lyBack)
    public void lyBack (View view){
        onBackPressed();
    }
    @OnClick(R.id.lyBtnDaftar)
    public void lyBtnDaftar(View view){
        Intent gotoaddcontest = new Intent(ContestActivity.this,AddContestActivity.class);
        startActivity(gotoaddcontest);
    }
    @OnClick(R.id.lyBtnTicket)
    public void lyBtnTicket(View view){
        Intent gototicket = new Intent(ContestActivity.this,MyTicketActivity.class);
        startActivity(gototicket);
    }
    @OnClick(R.id.rlKontes3)
    public void rlKontes3(View view){
        Intent gotolistkontes = new Intent(ContestActivity.this,AllContestActivity.class);
        startActivity(gotolistkontes);
    }
}
