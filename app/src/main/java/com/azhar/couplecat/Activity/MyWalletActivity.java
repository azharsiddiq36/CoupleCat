package com.azhar.couplecat.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.azhar.couplecat.Adapter.ContestAdapter;
import com.azhar.couplecat.Adapter.PenarikanAdapter;
import com.azhar.couplecat.Model.Contest;
import com.azhar.couplecat.Model.ResponseContest;
import com.azhar.couplecat.Model.ResponsePengguna;
import com.azhar.couplecat.R;
import com.azhar.couplecat.Rest.CombineApi;
import com.azhar.couplecat.Rest.CoupleCatInterface;
import com.azhar.couplecat.Utils.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyWalletActivity extends AppCompatActivity {
    @BindView(R.id.tvTotal)
    TextView tvTotal;
    @BindView(R.id.rvKontes)
    RecyclerView rvKontes;
    SessionManager sessionManager;
    HashMap<String,String> map;
    RecyclerView.LayoutManager layoutManager;
    PenarikanAdapter penarikanAdapter;
    ArrayList<Contest> contests = new ArrayList<>();
    CoupleCatInterface coupleCatInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wallet);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(MyWalletActivity.this);
        map = sessionManager.getDetailsLoggin();
        coupleCatInterface = CombineApi.getApiService();
        layoutManager = new LinearLayoutManager(MyWalletActivity.this, LinearLayoutManager.VERTICAL, false);
        rvKontes.setLayoutManager(layoutManager);
        loadContest();
        loadTotal();
    }

    private void loadTotal() {
        Call<ResponsePengguna> responsePenggunaCall = coupleCatInterface.detailAccount(map.get(sessionManager.KEY_PENGGUNA_ID));
        responsePenggunaCall.enqueue(new Callback<ResponsePengguna>() {
            @Override
            public void onResponse(Call<ResponsePengguna> call, Response<ResponsePengguna> response) {
                if (response.body().getStatus().equals("200")){
                    tvTotal.setText("Rp. "+response.body().getData().getPenggunaSaldo());
                }
            }

            @Override
            public void onFailure(Call<ResponsePengguna> call, Throwable t) {

            }
        });
    }

    private void loadContest() {
        Call<ResponseContest> responseContestCall = coupleCatInterface.getlistsaldokontes(map.get(sessionManager.KEY_PENGGUNA_ID));
        responseContestCall.enqueue(new Callback<ResponseContest>() {
            @Override
            public void onResponse(Call<ResponseContest> call, Response<ResponseContest> response) {
                if (response.body().getStatus() == 200){
                    contests = (ArrayList) response.body().getData();
                    penarikanAdapter = new PenarikanAdapter(MyWalletActivity.this,contests);
                    layoutManager = new LinearLayoutManager(MyWalletActivity.this, LinearLayoutManager.VERTICAL, false);
                    rvKontes.setLayoutManager(layoutManager);
                    rvKontes.smoothScrollToPosition(contests.size());
                    rvKontes.setAdapter(penarikanAdapter);
                    penarikanAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ResponseContest> call, Throwable t) {

            }
        });
    }

    @OnClick(R.id.tvAddSaldo)
    protected void tvAddSaldo(View view){
        Intent i = new Intent(getApplicationContext(),AddSaldoActivity.class);
        startActivity(i);
    }
    @OnClick(R.id.tvTarikSaldo)
    protected void tvTarikSaldo(View view){
        Intent i = new Intent(getApplicationContext(),TarikSaldoActivity.class);
        startActivity(i);
    }


}
