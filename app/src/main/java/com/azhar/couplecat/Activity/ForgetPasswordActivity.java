package com.azhar.couplecat.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.azhar.couplecat.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgetPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        ButterKnife.bind(this);
    }
    @OnClick(R.id.lyBack)
    protected void lyBack(View view){
        Intent gotologin = new Intent(ForgetPasswordActivity.this,LoginActivity.class);
        startActivity(gotologin);
        finish();
    }
    @OnClick(R.id.btnReset)
    protected void btnReset(View view){
        Intent gotologin = new Intent(ForgetPasswordActivity.this,LoginActivity.class);
        startActivity(gotologin);
        finish();
    }
}
