package com.azhar.couplecat.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.azhar.couplecat.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }
    @OnClick(R.id.tvLupaPassword)
    protected void tvLupaPassword(View view){
        Intent gotoforget = new Intent(LoginActivity.this,ForgetPasswordActivity.class);
        startActivity(gotoforget);
        finish();
    }
    @OnClick(R.id.tvRegister)
    protected void tvRegister(View view){
        Intent gotoregister = new Intent(LoginActivity.this,NewAccountActivity.class);
        startActivity(gotoregister);
        finish();
    }
    @OnClick(R.id.btnLogin)
    protected void btnLogin(View view){
        Intent gotohome = new Intent(LoginActivity.this,MainActivity.class);
        startActivity(gotohome);
        finish();
    }
}
