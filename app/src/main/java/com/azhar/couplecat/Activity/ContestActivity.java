package com.azhar.couplecat.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.azhar.couplecat.Model.ResponseContest;
import com.azhar.couplecat.R;
import com.azhar.couplecat.Rest.CombineApi;
import com.azhar.couplecat.Rest.CoupleCatInterface;
import com.azhar.couplecat.Utils.SessionManager;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.azhar.couplecat.Rest.CombineApi.img_url;

public class ContestActivity extends AppCompatActivity {
    SessionManager sessionManager;
    HashMap<String,String> map;
    CoupleCatInterface coupleCatInterface;
    @BindView(R.id.tvJud2)
    TextView tvJud2;
    @BindView(R.id.tvjud1)
    TextView tvJud1;
    @BindView(R.id.ivBg1)
    ImageView ivBg1;
    @BindView(R.id.ivBg2)
    ImageView ivBg2;
    @BindView(R.id.tvTgl1)
    TextView tvTgl1;
    @BindView(R.id.tvTgl2)
    TextView tvTgl2;
    @BindView(R.id.rlKontes1)
    RelativeLayout rlKontes1;
    @BindView(R.id.rlKontes2)
    RelativeLayout rlKontes2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contest);
        ButterKnife.bind(this);
        coupleCatInterface = CombineApi.getApiService();
        sessionManager = new SessionManager(this);
        map = sessionManager.getDetailsLoggin();
        loadContes();
    }

    private void loadContes() {
        Call<ResponseContest> responseContestCall = coupleCatInterface.getListContest();
        responseContestCall.enqueue(new Callback<ResponseContest>() {
            @Override
            public void onResponse(Call<ResponseContest> call, final Response<ResponseContest> response) {
                if (response.body().getStatus() == 200){
                    if (response.body().getTotal() == 1){
                        rlKontes2.setVisibility(View.GONE);
                        String gambar = response.body().getData().get(0).getKontesFoto();
                        Picasso.get()
                                .load(img_url + gambar)
                                .placeholder(android.R.drawable.sym_def_app_icon)
                                .error(android.R.drawable.sym_def_app_icon)
                                .into(ivBg1);
                        tvJud1.setText(response.body().getData().get(0).getKontesNama());
                        tvTgl1.setText(dateindoconverter(response.body().getData().get(0).getKontesTanggalMulai()));
                        rlKontes1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent gotodescription = new Intent(getApplicationContext(),DetailContestActivity.class);
                                gotodescription.putExtra("id",response.body().getData().get(0).getKontesId());
                                startActivity(gotodescription);
                            }
                        });
                    }
                    else{
                        String gambar = response.body().getData().get(0).getKontesFoto();
                        Picasso.get()
                                .load(img_url + gambar)
                                .placeholder(android.R.drawable.sym_def_app_icon)
                                .error(android.R.drawable.sym_def_app_icon)
                                .into(ivBg1);
                        tvJud1.setText(response.body().getData().get(0).getKontesNama());
                        tvTgl1.setText(dateindoconverter(response.body().getData().get(0).getKontesTanggalMulai()));
                        rlKontes1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent gotodescription = new Intent(getApplicationContext(),DetailContestActivity.class);
                                gotodescription.putExtra("id",response.body().getData().get(0).getKontesId());
                                startActivity(gotodescription);
                            }
                        });
                        String gambar2 = response.body().getData().get(1).getKontesFoto();
                        Picasso.get()
                                .load(img_url + gambar2)
                                .placeholder(android.R.drawable.sym_def_app_icon)
                                .error(android.R.drawable.sym_def_app_icon)
                                .into(ivBg2);
                        tvJud2.setText(response.body().getData().get(1).getKontesNama());
                        tvTgl2.setText(dateindoconverter(response.body().getData().get(1).getKontesTanggalMulai()));
                        rlKontes2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent gotodescription = new Intent(getApplicationContext(),DetailContestActivity.class);
                                gotodescription.putExtra("id",response.body().getData().get(1).getKontesId());
                                startActivity(gotodescription);
                            }
                        });
                    }
                }
            }
            @Override
            public void onFailure(Call<ResponseContest> call, Throwable t) {

            }
        });

    }

    private String dateindoconverter(String kontesTanggalMulai) {
        String tgl = kontesTanggalMulai.substring(8,10);
        String bulan = kontesTanggalMulai.substring(5,7);
        String tahun = kontesTanggalMulai.substring(0,4);
        switch (bulan){
            case "01" : bulan = "Januari";break;
            case "02" : bulan = "Februari";break;
            case "03" : bulan = "Maret";break;
            case "04" : bulan = "April";break;
            case "05": bulan = "Mei";break;
            case "06": bulan = "Juni";break;
            case "07": bulan = "Juli";break;
            case "08": bulan = "Agustus";break;
            case "09": bulan = "September";break;
            case "10" : bulan = "Oktober";break;
            case "11" : bulan = "November";break;
            case "12" : bulan = "Desember";break;
        }
        String tanggal = tgl+" "+bulan+" "+tahun;
        return tanggal;
    }
    @OnClick(R.id.lyBtnPemesanan)
    protected void lyBtnPemesanan(View view){
        Intent gotoriwayatpemesanan = new Intent(ContestActivity.this,RiwayatPemesananActivity.class);
        startActivity(gotoriwayatpemesanan);
    }
    @OnClick(R.id.lyMyContest)
    public void lyMyContest (View view){
        Intent gotomycontest = new Intent(ContestActivity.this,MyContestActivity.class);
        startActivity(gotomycontest);
    }
    @OnClick(R.id.lyBack)
    public void lyBack (View view){
        onBackPressed();
    }
    @OnClick(R.id.lyBtnDaftar)
    public void lyBtnDaftar(View view){
        Intent gotoaddcontest = new Intent(ContestActivity.this,AddContestActivity.class);
        startActivity(gotoaddcontest);
    }
    @OnClick(R.id.lyBtnTicket)
    public void lyBtnTicket(View view){
        Intent gototicket = new Intent(ContestActivity.this,MyTicketActivity.class);
        startActivity(gototicket);
    }
    @OnClick(R.id.rlKontes3)
    public void rlKontes3(View view){
        Intent gotolistkontes = new Intent(ContestActivity.this,AllContestActivity.class);
        startActivity(gotolistkontes);
    }
}
