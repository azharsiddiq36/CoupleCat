package com.azhar.couplecat.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.azhar.couplecat.R;

import butterknife.ButterKnife;

public class MyFriendsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_friends);
        ButterKnife.bind(this);
    }
}
