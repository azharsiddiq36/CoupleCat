package com.azhar.couplecat.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.azhar.couplecat.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddMyCatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_my_cat);
        ButterKnife.bind(this);
    }
    @OnClick(R.id.lyBack)
    public void lyBack(View view){
        onBackPressed();
    }
}
