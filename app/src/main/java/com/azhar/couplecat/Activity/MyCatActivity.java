package com.azhar.couplecat.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.azhar.couplecat.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyCatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cat);
        ButterKnife.bind(this);
    }
    @OnClick(R.id.lyBtnAdd)
    public void lyBtnAdd(View view){
        Intent gotoaddmycat = new Intent (MyCatActivity.this,AddMyCatActivity.class);
        startActivity(gotoaddmycat);
    }
}
