package com.azhar.couplecat.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class MyKontesFragment extends Fragment {
    @BindView(R.id.rvContest)
    RecyclerView rvContest;
    SessionManager sessionManager;
    CoupleCatInterface coupleCatInterface;
    HashMap<String,String> map;
    RecyclerView.LayoutManager layoutManager;
    ContestAdapter contestAdapter;
    ArrayList<Contest> contest;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mycontest, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sessionManager = new SessionManager(getContext());
        coupleCatInterface = CombineApi.getApiService();
        map = sessionManager.getDetailsLoggin();
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvContest.setLayoutManager(layoutManager);
        loadData();
    }

    private void loadData() {
        Call<ResponseContest> responseContestCall = coupleCatInterface.getMyContest(map.get(sessionManager.KEY_PENGGUNA_ID),null);
        responseContestCall.enqueue(new Callback<ResponseContest>() {
            @Override
            public void onResponse(Call<ResponseContest> call, Response<ResponseContest> response) {
                Log.d("kambing", "onResponse: "+response.body().getTotal());
                if (response.body().getTotal()>0){
                    contest = (ArrayList)response.body().getData();
                    contestAdapter = new ContestAdapter(getActivity(),contest);
                    rvContest.setAdapter(contestAdapter);
                    contestAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ResponseContest> call, Throwable t) {

            }
        });
    }

}