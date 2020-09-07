package com.azhar.couplecat.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.azhar.couplecat.Model.ResponsePengguna;
import com.azhar.couplecat.R;
import com.azhar.couplecat.Rest.CombineApi;
import com.azhar.couplecat.Rest.CoupleCatInterface;
import com.azhar.couplecat.Utils.SessionManager;
import com.azhar.couplecat.Utils.SweetAllert;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    SessionManager sessionManager;
    CoupleCatInterface coupleCatInterface;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.etUsername)
    EditText etUsername;
    SweetAllert alert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(LoginActivity.this);
        alert = new SweetAllert(LoginActivity.this);
        coupleCatInterface = CombineApi.getApiService();
        if(sessionManager.isLogin()){
            startActivity(new Intent(LoginActivity.this, MainActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }
        else{

        }
    }
    @OnClick(R.id.tvLupaPassword)
    protected void tvLupaPassword(View view){
        Intent gotoforget = new Intent(LoginActivity.this,ForgetPasswordActivity.class);
        startActivity(gotoforget);
        finish();
    }
    @OnClick(R.id.tvRegister)
    protected void tvRegister(View view){
        Intent gotoregister = new Intent(LoginActivity.this,NewAccountActivity.class);
        startActivity(gotoregister);
        finish();
    }
    @OnClick(R.id.btnLogin)
    protected void btnLogin(View view){
        alert.loadingAllert("Sedang Memeriksa Data");
        authLogin();
    }

    private void authLogin() {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        coupleCatInterface.loginRequest(username,password).enqueue(new Callback<ResponsePengguna>() {
            @Override
            public void onResponse(Call<ResponsePengguna> call, Response<ResponsePengguna> response) {
                int status = response.body().getStatus();
                if (status == 200){
                    Toast.makeText(LoginActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    String id = response.body().getData().getPenggunaId().toString();
                    String username = response.body().getData().getPenggunaUsername().toString();
                    String hak_akses = response.body().getData().getPenggunaHakAkses().toString();
                    String saldo = response.body().getData().getPenggunaSaldo();
                    String jk = response.body().getData().getPenggunaJenisKelamin();
                    String nama = response.body().getData().getPenggunaNama();
                    String foto = response.body().getData().getPenggunaFoto();
                    String nomor = response.body().getData().getPenggunaNomor().toString();
                    String email = response.body().getData().getPenggunaEmail();
                    sessionManager.saveLogin(id,username,email,foto,jk,saldo,nama,hak_akses,nomor);
                    coupleCatInterface.checkValidasi(id).enqueue(new Callback<ResponsePengguna>() {
                        @Override
                        public void onResponse(Call<ResponsePengguna> call, Response<ResponsePengguna> response) {
                            String validasi = null;
                            int statusvalidasi = response.body().getStatus();
                            if (statusvalidasi == 200){
                                validasi = "sudah";
                            }
                            else{
                                validasi="belum";
                            }
                            sessionManager.saveValidasi(validasi);
                        }

                        @Override
                        public void onFailure(Call<ResponsePengguna> call, Throwable t) {

                        }
                    });
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
                else{
                    alert.dismissloadingAllert();
                    Toast.makeText(LoginActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponsePengguna> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Harap Periksa Jaringan Anda", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
