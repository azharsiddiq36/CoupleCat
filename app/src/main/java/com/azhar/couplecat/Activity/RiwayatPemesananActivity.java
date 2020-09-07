package com.azhar.couplecat.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Window;

import com.azhar.couplecat.Adapter.RPemesananAdapter;
import com.azhar.couplecat.Model.ResponsePemesanan;
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

public class RiwayatPemesananActivity extends AppCompatActivity {
    @BindView(R.id.rvPemesanan)
    RecyclerView rvPemesanan;
    SessionManager sessionManager;
    HashMap<String,String> map;
    ArrayList pemesanans = new ArrayList<>();
    RPemesananAdapter pemesananAdapter;
    CoupleCatInterface coupleCatInterface;
    RecyclerView.LayoutManager layoutManager;
    String TAG = "kambing";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_pemesanan);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(RiwayatPemesananActivity.this);
        map = sessionManager.getDetailsLoggin();
        coupleCatInterface = CombineApi.getApiService();
        layoutManager = new LinearLayoutManager(RiwayatPemesananActivity.this, LinearLayoutManager.VERTICAL, false);
        rvPemesanan.setLayoutManager(layoutManager);
        loadPemesanan();
    }



    private void loadPemesanan() {
        Call<ResponsePemesanan> responsePemesananCall = coupleCatInterface.getPemesanan(map.get(sessionManager.KEY_PENGGUNA_ID));
        responsePemesananCall.enqueue(new Callback<ResponsePemesanan>() {
            @Override
            public void onResponse(Call<ResponsePemesanan> call, Response<ResponsePemesanan> response) {
                Log.d(TAG, "onResponse: "+response.body().getStatus());

                if (response.body().getStatus().equals("200")){
                    Log.d(TAG, "onResponse: "+response.body().getId_list());
                    String[] test = response.body().getId_list().split(",");
                    ArrayList<String> tes = new ArrayList<>();
                    for (int i = 0;i<test.length;i++){
                        tes.add(i,test[i]);
                    }

//                    pemesanans = (ArrayList) response.body().getData();
                    pemesananAdapter = new RPemesananAdapter(RiwayatPemesananActivity.this,tes);
                    layoutManager = new LinearLayoutManager(RiwayatPemesananActivity.this, LinearLayoutManager.VERTICAL, false);
                    rvPemesanan.setLayoutManager(layoutManager);
                    rvPemesanan.setAdapter(pemesananAdapter);
                    pemesananAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ResponsePemesanan> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.toString());
            }
        });
    }

}
