package com.azhar.couplecat.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.azhar.couplecat.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
}
