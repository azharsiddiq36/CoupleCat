package com.azhar.couplecat.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.azhar.couplecat.Adapter.ScheduleAdapter;
import com.azhar.couplecat.Model.Jadwal;
import com.azhar.couplecat.Model.ResponseJadwal;
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

public class MyAcceptScheduleFragment extends Fragment {
    @BindView(R.id.rvSchedule)
    RecyclerView rvSchedule;
    SessionManager sessionManager;
    CoupleCatInterface coupleCatInterface;
    HashMap<String,String> map;
    RecyclerView.LayoutManager layoutManager;
    ScheduleAdapter scheduleAdapter;
    ArrayList<Jadwal> schedule;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myacceptschedule, container, false);
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
        rvSchedule.setLayoutManager(layoutManager);
        loadData();
    }

    private void loadData() {
        Call<ResponseJadwal> responseJadwalCall = coupleCatInterface.getJadwal(map.get(sessionManager.KEY_PENGGUNA_ID),"setuju");
        responseJadwalCall.enqueue(new Callback<ResponseJadwal>() {
            @Override
            public void onResponse(Call<ResponseJadwal> call, Response<ResponseJadwal> response) {
                if (response.body().getTotal()>0) {
                    schedule = (ArrayList) response.body().getData();
                    scheduleAdapter = new ScheduleAdapter(getActivity(), schedule);
                    rvSchedule.setAdapter(scheduleAdapter);
                    scheduleAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ResponseJadwal> call, Throwable t) {

            }
        });
    }

}