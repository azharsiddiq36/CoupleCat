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

import com.azhar.couplecat.Adapter.TicketAdapter;
import com.azhar.couplecat.Model.Pembayaran;
import com.azhar.couplecat.Model.ResponsePembayaran;
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

public class MyHistoryTicketFragment extends Fragment {
    @BindView(R.id.rvTicket)
    RecyclerView rvContest;
    SessionManager sessionManager;
    CoupleCatInterface coupleCatInterface;
    HashMap<String,String> map;
    RecyclerView.LayoutManager layoutManager;
    TicketAdapter contestAdapter;
    ArrayList<Pembayaran> contest;
    String TAG = "Kambing";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_ticket, container, false);
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
        Log.d(TAG, "onViewCreated: ini Kontes");
        loadData();
    }

    private void loadData() {
        Call<ResponsePembayaran> responsePembayaranCall = coupleCatInterface.getMyPayment(map.get(sessionManager.KEY_PENGGUNA_ID));
        responsePembayaranCall.enqueue(new Callback<ResponsePembayaran>() {
            @Override
            public void onResponse(Call<ResponsePembayaran> call, Response<ResponsePembayaran> response) {
                Log.d(TAG, "onResponse: "+response.body().getStatus());
                if (response.body().getStatus()==200) {
                    contest = (ArrayList) response.body().getData();
                    contestAdapter = new TicketAdapter(getActivity(), contest);
                    rvContest.setAdapter(contestAdapter);
                    contestAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ResponsePembayaran> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.toString());
            }
        });
    }

}