package com.azhar.couplecat.Activity;

import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.azhar.couplecat.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyStoreActivity extends AppCompatActivity {
    @BindView(R.id.clStoreForm)
    CoordinatorLayout clStoreForm;
//    @BindView(R.id.tvAjukan)
//    TextView tvAjukan;
//    @BindView(R.id.textView10)
//    TextView textView10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_store);
        ButterKnife.bind(MyStoreActivity.this);
    }
//    @OnClick(R.id.tvAjukan)
//    public void tvAjukan(View view){
//        clStoreForm.setVisibility(View.VISIBLE);
//        tvAjukan.setVisibility(View.GONE);
//        textView10.setVisibility(View.GONE);
//    }
}
