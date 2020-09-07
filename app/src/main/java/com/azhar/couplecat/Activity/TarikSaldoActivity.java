package com.azhar.couplecat.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.azhar.couplecat.Model.ResponsePengguna;
import com.azhar.couplecat.Model.ResponseSaldo;
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

public class TarikSaldoActivity extends AppCompatActivity {
    @BindView(R.id.tvTotal)
    TextView tvTotal;
    @BindView(R.id.etNoRekening)
    EditText etNoRekening;
    @BindView(R.id.etBank)
    EditText etBank;
    @BindView(R.id.etJumlah)
    EditText etJumlah;
    SessionManager sessionManager;
    HashMap<String,String> map;
    CoupleCatInterface coupleCatInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarik_saldo);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(TarikSaldoActivity.this);
        map = sessionManager.getDetailsLoggin();
        coupleCatInterface = CombineApi.getApiService();
        loadTotal();
    }

    private void loadTotal() {
        Call<ResponsePengguna> responsePenggunaCall = coupleCatInterface.detailAccount(map.get(sessionManager.KEY_PENGGUNA_ID));
        responsePenggunaCall.enqueue(new Callback<ResponsePengguna>() {
            @Override
            public void onResponse(Call<ResponsePengguna> call, Response<ResponsePengguna> response) {
                int status = response.body().getStatus();
                if (status == 200) {
                    tvTotal.setText("Rp. " + response.body().getData().getPenggunaSaldo().toString());
                }
                else{
                    Log.d("kambing", "onResponse: "+response.body().getMessage()+","+response.body().getStatus());
                }
//                if (response.body().getStatus().equals("200")){
//                    tvTotal.setText("Rp. "+response.body().getData().getPenggunaSaldo());
//                }
            }

            @Override
            public void onFailure(Call<ResponsePengguna> call, Throwable t) {

            }
        });
    }

    @OnClick(R.id.btnSubmit)
    protected void btnSubmit(View view){
        Call<ResponseSaldo> responseSaldoCall = coupleCatInterface.tarikSaldo(map.get(sessionManager.KEY_PENGGUNA_ID),
                etNoRekening.getText().toString(),
                etBank.getText().toString(),
                etJumlah.getText().toString());
        responseSaldoCall.enqueue(new Callback<ResponseSaldo>() {
            @Override
            public void onResponse(Call<ResponseSaldo> call, Response<ResponseSaldo> response) {
                if (response.body().getStatus().equals("200")){
                    Toast.makeText(TarikSaldoActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseSaldo> call, Throwable t) {

            }
        });
    }
}
