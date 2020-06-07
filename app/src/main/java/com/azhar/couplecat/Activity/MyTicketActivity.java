package com.azhar.couplecat.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.azhar.couplecat.Fragment.MyHistoryTicketFragment;
import com.azhar.couplecat.Fragment.MyTicketFragment;
import com.azhar.couplecat.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyTicketActivity extends AppCompatActivity {
    MyTicketFragment myTicketFragment;
    MyHistoryTicketFragment myHistoryTicketFragment;
    @BindView(R.id.tvRiwayat)
    TextView tvRiwayat;
    @BindView(R.id.tvBerlaku)
    TextView tvBerlaku;
    FragmentTransaction mFragmentTransaction;
    FragmentManager mFragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ticket);
        ButterKnife.bind(this);
        myTicketFragment = new MyTicketFragment();
        myHistoryTicketFragment = new MyHistoryTicketFragment();
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        Log.d("kambing", "onCreate: ticket");
        loadFragment(myTicketFragment);

    }

    @OnClick(R.id.tvBerlaku)
    protected void tvBerlaku(View view){
        tvRiwayat.setBackgroundColor(Color.TRANSPARENT);
        tvBerlaku.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.bg_button));
        loadFragment(myTicketFragment);
    }
    @OnClick(R.id.tvRiwayat)
    protected void tvRiwayat(View view){
        tvBerlaku.setBackgroundColor(Color.TRANSPARENT);
        tvRiwayat.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.bg_button));
        loadFragment(myHistoryTicketFragment);
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
