package com.azhar.couplecat.Activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;

import com.azhar.couplecat.Adapter.TokoAdapter;
import com.azhar.couplecat.Model.ResponseToko;
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

public class MyStoreActivity extends AppCompatActivity {
    @BindView(R.id.rvToko)
    RecyclerView rvToko;
    SessionManager sessionManager;
    CoupleCatInterface coupleCatInterface;
    RecyclerView.LayoutManager layoutManager;
    HashMap<String, String> map;
    TokoAdapter tokoAdapter;
    ArrayList toko = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_store);
        ButterKnife.bind(MyStoreActivity.this);
        sessionManager = new SessionManager(MyStoreActivity.this);
        coupleCatInterface = CombineApi.getApiService();
        map = sessionManager.getDetailsLoggin();
        layoutManager = new LinearLayoutManager(MyStoreActivity.this, LinearLayoutManager.VERTICAL, false);
        rvToko.setLayoutManager(layoutManager);
        loadToko();
    }
    @OnClick (R.id.lyMyStore)
    protected void lyMyStore(View view){
        Intent gotoaddstore = new Intent(MyStoreActivity.this,AddMyStore.class);
        startActivity(gotoaddstore);
    }
    private void loadToko() {
        Call<ResponseToko> responseTokoCall = coupleCatInterface.getListToko();
        responseTokoCall.enqueue(new Callback<ResponseToko>() {
            @Override
            public void onResponse(Call<ResponseToko> call, Response<ResponseToko> response) {
                if (response.isSuccessful()){
                    toko = (ArrayList) response.body().getData();
                    tokoAdapter = new TokoAdapter(getApplicationContext(),toko);
                    rvToko.setAdapter(tokoAdapter);
                    tokoAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ResponseToko> call, Throwable t) {

            }
        });
    }


}
