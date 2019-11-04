package com.azhar.couplecat.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import com.azhar.couplecat.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvOrigin)
    TextView tvOrigin;
    @BindView(R.id.tvLifeSpan)
    TextView tvLifeSpan;
    @BindView(R.id.tvWeight)
    TextView tvWeight;
    @BindView(R.id.tvDescription)
    TextView tvDescription;
    @BindView(R.id.tvWikipediaUrl)
    TextView tvWikipediaUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        tvDescription.setText(getIntent().getStringExtra("description"));
        tvLifeSpan.setText(getIntent().getStringExtra("lifespan"));
        tvWeight.setText(getIntent().getStringExtra("weight"));
        tvWikipediaUrl.setText(getIntent().getStringExtra("wikipedia"));
        tvName.setText(getIntent().getStringExtra("name"));
        tvOrigin.setText(getIntent().getStringExtra("origin"));
    }
}
