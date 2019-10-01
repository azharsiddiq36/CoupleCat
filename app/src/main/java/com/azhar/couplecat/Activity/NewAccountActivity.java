package com.azhar.couplecat.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.azhar.couplecat.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewAccountActivity extends AppCompatActivity {
    @BindView(R.id.etUsername)
    EditText etUsername;
    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);
        ButterKnife.bind(this);
    }
    @OnClick(R.id.lyBack)
    protected void lyBack(View view){
        Intent gotologin = new Intent(NewAccountActivity.this,LoginActivity.class);
        startActivity(gotologin);
        finish();
    }
    @OnClick(R.id.btnDaftar)
    protected void btnDaftar(View view){
//        Intent gotologin = new Intent(NewAccountActivity.this,LoginActivity.class);
//        startActivity(gotologin);
    }
}
