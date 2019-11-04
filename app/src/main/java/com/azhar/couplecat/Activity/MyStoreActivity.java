package com.azhar.couplecat.Activity;

import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.azhar.couplecat.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyStoreActivity extends AppCompatActivity {
    @BindView(R.id.lyBtnBottom)
    LinearLayout lyBtnBottom;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    @BindView(R.id.ivBgStore)
    ImageView ivBgStore;
    @BindView(R.id.tvChangeStorePhoto)
    TextView tvChangeStorePhoto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_store);
        ButterKnife.bind(MyStoreActivity.this);
    }
    @OnClick(R.id.lyBtnAjukan)
    public void lyBtnAjukan(View view){
        lyBtnBottom.setVisibility(View.VISIBLE);
        ivBgStore.setVisibility(View.VISIBLE);
        linearLayout.setVisibility(View.VISIBLE);
        tvChangeStorePhoto.setVisibility(View.VISIBLE);

    }
}
