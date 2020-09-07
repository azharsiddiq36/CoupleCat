package com.azhar.couplecat.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.azhar.couplecat.Model.ResponseContestDetails;
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

public class DetailContestActivity extends AppCompatActivity {
    SessionManager sessionManager;
    HashMap<String,String> map;
    CoupleCatInterface coupleCatInterface;
    @BindView(R.id.ivBg)
    ImageView ivBg;
    @BindView(R.id.tvJudul)
    TextView tvJudul;
    @BindView(R.id.tvDescription)
    TextView tvDescription;
    @BindView(R.id.tvDetails)
    TextView tvDetail;
    @BindView(R.id.tvTglMulai)
    TextView tvTglMulai;
    @BindView(R.id.tvSisa)
    TextView tvSisa;
    @BindView(R.id.tvNomor)
    TextView tvNomor;
    @BindView(R.id.tvHarga)
    TextView tvHarga;
    @BindView(R.id.tvLokasi)
    TextView tvLokasi;
    @BindView(R.id.tvNama)
    TextView tvPenyelenggara;
    int hrg = 0;
    int ss = 0;
    String ktgr,jdl,tgl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_contest);
        ButterKnife.bind(this);
        coupleCatInterface = CombineApi.getApiService();
        sessionManager = new SessionManager(this);
        map = sessionManager.getDetailsLoggin();
        loadDetails(getIntent().getStringExtra("id"));
    }

    private void loadDetails(String s) {
        Call<ResponseContestDetails> responseContestDetailsCall = coupleCatInterface.getDetailContest(s);
        responseContestDetailsCall.enqueue(new Callback<ResponseContestDetails>() {
            @Override
            public void onResponse(Call<ResponseContestDetails> call, Response<ResponseContestDetails> response) {
                if (response.body().getStatus() == 200){
                    String gambar = response.body().getData().getKontesFoto();
                    Picasso.get()
                            .load(img_url + gambar)
                            .placeholder(android.R.drawable.sym_def_app_icon)
                            .error(android.R.drawable.sym_def_app_icon)
                            .into(ivBg);
                    tvJudul.setText(response.body().getData().getKontesNama());
                    tvDescription.setText(response.body().getData().getKontesDescription());
                    tvDetail.setText(response.body().getData().getKontesDetails());
                    tvHarga.setText("Rp. "+response.body().getData().getKontesBiaya());
                    int pemesanan = Integer.parseInt(response.body().getData().getKontesJumlahPemesan());
                    int total = Integer.parseInt(response.body().getData().getKontesKuota());
                    int sisa = total-pemesanan;
                    hrg = Integer.parseInt(response.body().getData().getKontesBiaya());
                    ss = sisa;
                    tgl = response.body().getData().getKontesTanggalMulai();
                    ktgr = response.body().getData().getJenisKontesNama();
                    tvSisa.setText(String.valueOf(sisa)+" Tiket Tersisa");
                    tvNomor.setText(response.body().getData().getKontesNomor());
                    tvPenyelenggara.setText(response.body().getData().getPenggunaNama());
                    tvTglMulai.setText(dateindoconverter(response.body().getData().getKontesTanggalMulai()));
                    tvLokasi.setText("Kontes ini diadakan di "+response.body().getData().getKontesLokasi()+" \n("+
                    response.body().getData().getKontesDesa()+", "+response.body().getData().getKontesKecamatan()+", "
                    +response.body().getData().getKontesKabupaten()+", "+response.body().getData().getKontesProvinsi()+")");

                }
            }

            @Override
            public void onFailure(Call<ResponseContestDetails> call, Throwable t) {

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
    @OnClick(R.id.ivWhislist)
    protected void ivWhislist(View view) {
        Intent gotopesan = new Intent(DetailContestActivity.this,PemesananActivity.class);
        gotopesan.putExtra("id",getIntent().getStringExtra("id"));
        gotopesan.putExtra("sisa",""+ss);
        gotopesan.putExtra("harga",""+hrg);
        gotopesan.putExtra("kategori",ktgr);
        gotopesan.putExtra("judul",tvJudul.getText().toString());
        gotopesan.putExtra("tgl",tgl);
        startActivity(gotopesan);
    }

}
