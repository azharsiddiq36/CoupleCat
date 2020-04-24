package com.azhar.couplecat.Activity;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.azhar.couplecat.Fragment.MyAcceptScheduleFragment;
import com.azhar.couplecat.Fragment.MyScheduleFragment;
import com.azhar.couplecat.Fragment.MyWaitingScheduleFragment;
import com.azhar.couplecat.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyScheduleActivity extends AppCompatActivity {

    int step = 0;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    MyScheduleFragment myScheduleFragment;
    MyWaitingScheduleFragment myWaitingScheduleFragment;
    MyAcceptScheduleFragment myAcceptScheduleFragment;
    @BindView(R.id.tvAccept)
    TextView tvAccept;
    @BindView(R.id.tvAll)
    TextView tvAll;
    @BindView(R.id.tvWaiting)
    TextView tvWaiting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_schedule);
        ButterKnife.bind(this);
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        myScheduleFragment = new MyScheduleFragment();
        myWaitingScheduleFragment = new MyWaitingScheduleFragment();
        myAcceptScheduleFragment = new MyAcceptScheduleFragment();
           loadFragment(myScheduleFragment);
    }

    @OnClick(R.id.tvAccept)
    protected void tvAccept(View view){
        tvAll.setBackgroundColor(Color.TRANSPARENT);
        tvWaiting.setBackgroundColor(Color.TRANSPARENT);
        tvAccept.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.bg_button));
        loadFragment(myScheduleFragment);
    }
    @OnClick(R.id.tvWaiting)
    protected void tvWaiting(View view){
        tvAccept.setBackgroundColor(Color.TRANSPARENT);
        tvAll.setBackgroundColor(Color.TRANSPARENT);
        tvWaiting.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.bg_button));
        loadFragment(myWaitingScheduleFragment);
    }
    @OnClick(R.id.tvAll)
    protected void tvAll(View view){
        tvAccept.setBackgroundColor(Color.TRANSPARENT);
        tvWaiting.setBackgroundColor(Color.TRANSPARENT);
        tvAll.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.bg_button));
        loadFragment(myAcceptScheduleFragment);
    }

    public boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}

