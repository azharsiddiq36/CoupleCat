package com.azhar.couplecat.Activity;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.azhar.couplecat.Fragment.AccountFragment;
import com.azhar.couplecat.Fragment.ChatFragment;
import com.azhar.couplecat.Fragment.CoupleFragment;
import com.azhar.couplecat.Fragment.HomeFragment;
import com.azhar.couplecat.Fragment.InformationFragment;
import com.azhar.couplecat.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.frameFragment)
    FrameLayout frameFragment;
    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragment = new HomeFragment();
                    break;
                case R.id.navigation_information:
                    fragment = new InformationFragment();
                    break;
                case R.id.navigation_account:
                    fragment = new AccountFragment();
                    break;
                case R.id.navigation_chat:
                    fragment = new ChatFragment();
                    break;
                case R.id.navigation_couple:
                    fragment = new CoupleFragment();
                    break;
            }
            return loadFragment(fragment);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        mTextMessage = (TextView) findViewById(R.id.message);
        loadFragment(new HomeFragment());
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
       // navigation.setItemIconTintList(Color.parseColor("#151b47"));
    }
    public boolean loadFragment(Fragment fragment) {
        if (fragment != null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameFragment, fragment)
                    .commit();
            return true;
        }
        return false;
    }

}
