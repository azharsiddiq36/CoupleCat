package com.azhar.couplecat.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import com.azhar.couplecat.Model.UniqueCode;
import com.azhar.couplecat.R;
import com.azhar.couplecat.Rest.ApiClient;
import com.azhar.couplecat.Rest.LocationInterface;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyWalletActivity extends AppCompatActivity {
    LocationInterface apiService;
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wallet);
        ButterKnife.bind(this);
        loadUniqueCode();
        Log.d("kambing", "onCreate: "+token);
    }
    public void loadUniqueCode() {
        apiService = ApiClient.getClient().create(LocationInterface.class);
        Call<UniqueCode> call = apiService.getUniqueCode();

        call.enqueue(new Callback<UniqueCode>() {
            @Override
            public void onResponse(Call<UniqueCode> call, Response<UniqueCode> response) {
                token = "MeP7c5ne" + response.body().getUniqueCode();
                Log.d("Kambiong", "onResponse: ");
            }

            @Override
            public void onFailure(Call<UniqueCode> call, Throwable t) {

            }
        });
    }
}
