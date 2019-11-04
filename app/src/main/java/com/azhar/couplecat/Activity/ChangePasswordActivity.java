package com.azhar.couplecat.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.azhar.couplecat.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChangePasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);
    }
    @OnClick(R.id.lyBack)
    public void lyBack(View view){
        onBackPressed();
    }
    @OnClick(R.id.btnUpdate)
    public void lyUpdate(View view){
        Intent gotomain = new Intent(ChangePasswordActivity.this,MainActivity.class);
        startActivity(gotomain);
    }
}
