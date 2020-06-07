package com.azhar.couplecat.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;

import com.azhar.couplecat.Adapter.ContestAdapter;
import com.azhar.couplecat.Model.Contest;
import com.azhar.couplecat.Model.ResponseContest;
import com.azhar.couplecat.R;
import com.azhar.couplecat.Rest.CombineApi;
import com.azhar.couplecat.Rest.CoupleCatInterface;
import com.azhar.couplecat.Utils.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllContestActivity extends AppCompatActivity {
    @BindView(R.id.rvKontes)
    RecyclerView rvKontes;
    SessionManager sessionManager;
    HashMap<String,String> map;
    ArrayList<Contest> contests = new ArrayList<>();
    ContestAdapter contestAdapter;
    CoupleCatInterface coupleCatInterface;
    RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_contest);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(AllContestActivity.this);
        map = sessionManager.getDetailsLoggin();
        coupleCatInterface = CombineApi.getApiService();
        layoutManager = new LinearLayoutManager(AllContestActivity.this, LinearLayoutManager.VERTICAL, false);
        rvKontes.setLayoutManager(layoutManager);
        loadContest();
    }

    private void loadContest() {
        Call<ResponseContest> responseContestCall = coupleCatInterface.getListContest();
        responseContestCall.enqueue(new Callback<ResponseContest>() {
            @Override
            public void onResponse(Call<ResponseContest> call, Response<ResponseContest> response) {
                if (response.body().getStatus() == 200){
                    contests = (ArrayList) response.body().getData();
                    contestAdapter = new ContestAdapter(AllContestActivity.this,contests);
                    layoutManager = new LinearLayoutManager(AllContestActivity.this, LinearLayoutManager.VERTICAL, false);
                    rvKontes.setLayoutManager(layoutManager);
                    rvKontes.smoothScrollToPosition(contests.size());
                    rvKontes.setAdapter(contestAdapter);
                    contestAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ResponseContest> call, Throwable t) {

            }
        });
    }
}
