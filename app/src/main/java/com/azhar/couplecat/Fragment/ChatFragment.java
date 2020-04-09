package com.azhar.couplecat.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.azhar.couplecat.Activity.MainActivity;
import com.azhar.couplecat.Adapter.KontakAdapter;
import com.azhar.couplecat.Model.Kontak;
import com.azhar.couplecat.Model.ResponseKontak;
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


public class ChatFragment extends Fragment {
    SessionManager sessionManager;
    HashMap<String, String> map;
    CoupleCatInterface coupleCatInterface;
    @BindView(R.id.rvKontak)
    RecyclerView rvKontak;
    RecyclerView.LayoutManager layoutManager;
    KontakAdapter kontakAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        android.support.v7.app.ActionBar actionBar =
                ((MainActivity) getActivity()).getSupportActionBar();
        assert actionBar != null;
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_overlay_splash));
        actionBar.setTitle("Chatting");
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
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
        rvKontak.setLayoutManager(layoutManager);
        loadKontak();
//        android.support.v7.app.ActionBar actionBar =
//                ((MainActivity) getActivity()).getSupportActionBar();
//        assert actionBar != null;actionBar.setTitle("Profile");

    }

    private void loadKontak() {
        Call<ResponseKontak> responseKontakCall = coupleCatInterface.getKontak(map.get(sessionManager.KEY_PENGGUNA_ID));
        responseKontakCall.enqueue(new Callback<ResponseKontak>() {
            @Override
            public void onResponse(Call<ResponseKontak> call, Response<ResponseKontak> response) {
                kontakAdapter = new KontakAdapter(getContext(), (ArrayList<Kontak>) response.body().getData());
                rvKontak.setAdapter(kontakAdapter);
                kontakAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseKontak> call, Throwable t) {

            }
        });
    }
}
