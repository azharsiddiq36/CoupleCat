package com.azhar.couplecat.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.azhar.couplecat.Model.ResponsePemesanan;
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

public class PemesananActivity extends AppCompatActivity {
    @BindView(R.id.tvJumlah)
    TextView tvJumlah;
    @BindView(R.id.tvKategori)
    TextView tvKategori;
    @BindView(R.id.tvHarga)
    TextView tvHarga;
    @BindView(R.id.tvJudul)
    TextView tvJudul;
    @BindView(R.id.tvTotal)
    TextView tvTotal;
    @BindView(R.id.roundedImageView)
    ImageView ivUpload;
    int jmlh = 0;
    int ttl = 0;
    int harga;
    int max;
    CoupleCatInterface coupleCatInterface;
    SessionManager sessionManager;
    HashMap<String,String> map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemesanan);
        ButterKnife.bind(this);
        coupleCatInterface = CombineApi.getApiService();
        sessionManager = new SessionManager(PemesananActivity.this);
        map = sessionManager.getDetailsLoggin();
        max = Integer.parseInt(getIntent().getStringExtra("sisa"));
        harga = Integer.parseInt(getIntent().getStringExtra("harga"));
        tvHarga.setText("Rp. "+harga);
        tvJudul.setText(getIntent().getStringExtra("judul"));
        tvKategori.setText(getIntent().getStringExtra("kategori"));
    }
    @OnClick(R.id.btnTambah)
    protected void btnTambah(View view){
        if (jmlh == max){
            jmlh = max;
        }
        else{
            jmlh++;
        }
        ttl = jmlh * harga;
        tvJumlah.setText(""+jmlh);
        tvTotal.setText("Total : Rp. "+ttl);
    }
    @OnClick(R.id.btnKurang)
    protected void btnKurang(View view){
        if (jmlh == 0){
            jmlh = 0;
        }
        else{
            jmlh--;
        }
        ttl = jmlh * harga;
        tvJumlah.setText(""+jmlh);
        tvTotal.setText("Total : Rp. "+ttl);
    }
    @OnClick(R.id.btnPesan)
    protected void btnPesan(View view){
        Call<ResponsePemesanan> responsePemesananCall = coupleCatInterface.addPemesanan(map.get(sessionManager.KEY_PENGGUNA_ID),
                String.valueOf(jmlh),
                String.valueOf(ttl),
                getIntent().getStringExtra("id"));
        responsePemesananCall.enqueue(new Callback<ResponsePemesanan>() {
            @Override
            public void onResponse(Call<ResponsePemesanan> call, Response<ResponsePemesanan> response) {
                if (response.body().getStatus().equals("200")){
                    Toast.makeText(PemesananActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Intent gotoriwayat = new Intent(getApplicationContext(),RiwayatPemesananActivity.class);
                    startActivity(gotoriwayat);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ResponsePemesanan> call, Throwable t) {

            }
        });
    }
}
