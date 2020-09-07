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

public class MyScheduleActivity extends AppCompatActivity {

    int step = 0;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    MyScheduleFragment myScheduleFragment;
    MyWaitingScheduleFragment myWaitingScheduleFragment;
    MyAcceptScheduleFragment myAcceptScheduleFragment;
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
        setContentView(R.layout.activity_my_schedule);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(getApplicationContext());
        coupleCatInterface = CombineApi.getApiService();
        map = sessionManager.getDetailsLoggin();
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        myScheduleFragment = new MyScheduleFragment();
        coupleCatInterface.getJadwal(map.get(sessionManager.KEY_PENGGUNA_ID),"menunggu").enqueue(new Callback<ResponseJadwal>() {
            @Override
            public void onResponse(Call<ResponseJadwal> call, Response<ResponseJadwal> response) {
                if (response.body().getTotal()>0){
                    total = response.body().getTotal();
                    tvWaiting.setText("Menunggu("+total+")");
                }
                else{
                    tvWaiting.setText("Menunggu");
                }
            }

            @Override
            public void onFailure(Call<ResponseJadwal> call, Throwable t) {

            }
        });
//
        myWaitingScheduleFragment = new MyWaitingScheduleFragment();
        myAcceptScheduleFragment = new MyAcceptScheduleFragment();
           loadFragment(myScheduleFragment);
    }

    @OnClick(R.id.tvAccept)
    protected void tvAccept(View view){
        tvAll.setBackgroundColor(Color.TRANSPARENT);
        tvWaiting.setBackgroundColor(Color.TRANSPARENT);
        tvAccept.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.bg_button));
        loadFragment(myAcceptScheduleFragment);
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
        loadFragment(myScheduleFragment);
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

