package com.azhar.couplecat.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.azhar.couplecat.Model.ResponsePengguna;
import com.azhar.couplecat.R;
import com.azhar.couplecat.Rest.CombineApi;
import com.azhar.couplecat.Rest.CoupleCatInterface;
import com.azhar.couplecat.Utils.SessionManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPasswordActivity extends AppCompatActivity {
    String TAG = "SERANGGA";
    SessionManager sessionManager;
    CoupleCatInterface coupleCatInterface;
    @BindView(R.id.etEmail)
    EditText etEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManager = new SessionManager(getApplicationContext());
        coupleCatInterface = CombineApi.getApiService();
        setContentView(R.layout.activity_forget_password);
        ButterKnife.bind(this);
    }
    @OnClick(R.id.lyBack)
    protected void lyBack(View view){
        Intent gotologin = new Intent(ForgetPasswordActivity.this,LoginActivity.class);
        startActivity(gotologin);
        finish();
    }
    @OnClick(R.id.btnReset)
    protected void btnReset(View view){
        String email = etEmail.getText().toString();
        coupleCatInterface.forget(email).enqueue(new Callback<ResponsePengguna>() {
            @Override
            public void onResponse(Call<ResponsePengguna> call, Response<ResponsePengguna> response) {
                int status = response.body().getStatus();
                if (status ==200){
                    Toast.makeText(ForgetPasswordActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(ForgetPasswordActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponsePengguna> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.toString());
            }
        });
        /*
        Intent gotologin = new Intent(ForgetPasswordActivity.this,LoginActivity.class);
        startActivity(gotologin);
        finish();
        */
    }
}
