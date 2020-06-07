package com.azhar.couplecat.Activity;

import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.view.Window;
import android.widget.TextView;

import com.azhar.couplecat.Fragment.MyAccKontesFragment;
import com.azhar.couplecat.Fragment.MyKontesFragment;
import com.azhar.couplecat.Fragment.MyWaitingKontesFragment;
import com.azhar.couplecat.Model.ResponseContest;
import com.azhar.couplecat.Model.ResponseJadwal;
import com.azhar.couplecat.R;
import com.azhar.couplecat.Rest.CombineApi;
import com.azhar.couplecat.Rest.CoupleCatInterface;
import com.azhar.couplecat.Utils.SessionManager;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyContestActivity extends AppCompatActivity {

    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    MyKontesFragment myKontesFragment;
    MyWaitingKontesFragment myWaitingKontesFragment;
    MyAccKontesFragment myAccKontesFragment;
    CoupleCatInterface coupleCatInterface;
    SessionManager sessionManager;
    HashMap<String,String> map;
    @BindView(R.id.tvAccept)
    TextView tvAccept;
    @BindView(R.id.tvAll)
    TextView tvAll;
    @BindView(R.id.tvWaiting)
    TextView tvWaiting;
    int total;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_contest);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(getApplicationContext());
        coupleCatInterface = CombineApi.getApiService();
        map = sessionManager.getDetailsLoggin();
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        myKontesFragment = new MyKontesFragment();
        myAccKontesFragment = new MyAccKontesFragment();
        myWaitingKontesFragment = new MyWaitingKontesFragment();
        coupleCatInterface.getMyContest(map.get(sessionManager.KEY_PENGGUNA_ID),"menunggu").enqueue(new Callback<ResponseContest>() {
            @Override
            public void onResponse(Call<ResponseContest> call, Response<ResponseContest> response) {
                if (response.body().getTotal()>0){
                    total = response.body().getTotal();
                    tvWaiting.setText("Menunggu("+total+")");
                }
                else{
                    tvWaiting.setText("Menunggu");
                }

            }

            @Override
            public void onFailure(Call<ResponseContest> call, Throwable t) {

            }
        });

        loadFragment(myKontesFragment);
    }

    @OnClick(R.id.tvAccept)
    protected void tvAccept(View view){
        tvAll.setBackgroundColor(Color.TRANSPARENT);
        tvWaiting.setBackgroundColor(Color.TRANSPARENT);
        tvAccept.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.bg_button));
        loadFragment(myAccKontesFragment);
    }
    @OnClick(R.id.tvWaiting)
    protected void tvWaiting(View view){
        tvAccept.setBackgroundColor(Color.TRANSPARENT);
        tvAll.setBackgroundColor(Color.TRANSPARENT);
        tvWaiting.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.bg_button));
        loadFragment(myWaitingKontesFragment);
    }
    @OnClick(R.id.tvAll)
    protected void tvAll(View view){
        tvAccept.setBackgroundColor(Color.TRANSPARENT);
        tvWaiting.setBackgroundColor(Color.TRANSPARENT);
        tvAll.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.bg_button));
        loadFragment(myKontesFragment);
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
