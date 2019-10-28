package com.azhar.couplecat.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class NewAccountActivity extends AppCompatActivity {
    @BindView(R.id.etUsername)
    EditText etUsername;
    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.etNama)
    EditText etNama;
    @BindView(R.id.etPassword)
    EditText etPassword;
    SessionManager sessionManager;
    CoupleCatInterface coupleCatInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(getApplicationContext());
        coupleCatInterface = CombineApi.getApiService();
    }
    @OnClick(R.id.lyBack)
    protected void lyBack(View view){
        Intent gotologin = new Intent(NewAccountActivity.this,LoginActivity.class);
        startActivity(gotologin);
        finish();
    }
    @OnClick(R.id.btnDaftar)
    protected void btnDaftar(View view){
        String nama,username,email,password;
        nama = etNama.getText().toString();
        username = etUsername.getText().toString();
        password = etPassword.getText().toString();
        email = etEmail.getText().toString();
        coupleCatInterface.register(nama,username,email,password).enqueue(new Callback<ResponsePengguna>() {
            @Override
            public void onResponse(Call<ResponsePengguna> call, Response<ResponsePengguna> response) {
                int status = response.body().getStatus();
                if (status == 200){
                    Toast.makeText(NewAccountActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Intent gotologin = new Intent(NewAccountActivity.this,LoginActivity.class);
                    startActivity(gotologin);
                }
                else{
                    Toast.makeText(NewAccountActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponsePengguna> call, Throwable t) {

            }
        });

    }
}
