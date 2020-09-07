package com.azhar.couplecat.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.azhar.couplecat.Adapter.KomentarAdapter;
import com.azhar.couplecat.Adapter.PostinganAdapter;
import com.azhar.couplecat.Model.Komentar;
import com.azhar.couplecat.Model.ResponseKomentar;
import com.azhar.couplecat.R;
import com.azhar.couplecat.Rest.CombineApi;
import com.azhar.couplecat.Rest.CoupleCatInterface;
import com.azhar.couplecat.Utils.SessionManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.azhar.couplecat.Rest.CombineApi.img_url;

public class KomentarActivity extends AppCompatActivity {
    String postingan_id;
    @BindView(R.id.tvPostingan)
    TextView tvPostingan;
    @BindView(R.id.tvTime)
    TextView tvTime;
    @BindView(R.id.ivFoto)
    ImageView ivFoto;
    @BindView(R.id.tvNama)
    TextView tvNama;
    @BindView(R.id.ivFotoProfile)
    ImageView ivFotoProfile;
    @BindView(R.id.etKomentar)
    EditText etKomentar;
    @BindView(R.id.rvKomentar)
    RecyclerView rvKomentar;
    SessionManager sessionManager;
    @BindView(R.id.tvNotif)
            TextView tvNotif;
    HashMap<String,String> map;
    ArrayList<Komentar> komentars = new ArrayList<>();
    KomentarAdapter komentarAdapter;
    CoupleCatInterface coupleCatInterface;
    RecyclerView.LayoutManager layoutManager;
    String total_koment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_komentar);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(KomentarActivity.this);
        map = sessionManager.getDetailsLoggin();
        coupleCatInterface = CombineApi.getApiService();
        layoutManager = new LinearLayoutManager(KomentarActivity.this, LinearLayoutManager.VERTICAL, false);
        rvKomentar.setLayoutManager(layoutManager);
        loadPostingan();
        if (total_koment.equals("0 Komentar")){
            rvKomentar.setVisibility(View.GONE);

        }
        else{

            loadKomentar();
        }


    }
    @OnClick(R.id.btnRight)
    protected void btnRight(View view){
        addKomentar();
    }

    private void addKomentar() {
        if (etKomentar.getText().toString().equals("") || etKomentar.getText().toString() == null){
            Toast.makeText(this, "Tidak ada komentar yang ditambahkan", Toast.LENGTH_SHORT).show();
        }
        else{
            Call<ResponseKomentar> responseKomentarCall = coupleCatInterface.addKomentar(map.get(sessionManager.KEY_PENGGUNA_ID),
                    postingan_id,
                    etKomentar.getText().toString());
            responseKomentarCall.enqueue(new Callback<ResponseKomentar>() {
                @Override
                public void onResponse(Call<ResponseKomentar> call, Response<ResponseKomentar> response) {
                    if (response.body().getStatus() == 200){
                        Toast.makeText(KomentarActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        etKomentar.setText("");
                        loadKomentar();

                    }
                    else{
                        Toast.makeText(KomentarActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseKomentar> call, Throwable t) {
                    Toast.makeText(KomentarActivity.this, "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void loadKomentar() {
        rvKomentar.setVisibility(View.VISIBLE);
        tvNotif.setVisibility(View.GONE);
        Call<ResponseKomentar> responseKomentarCall = coupleCatInterface.getKomentar(postingan_id);
        responseKomentarCall.enqueue(new Callback<ResponseKomentar>() {
            @Override
            public void onResponse(Call<ResponseKomentar> call, Response<ResponseKomentar> response) {
                komentars = (ArrayList) response.body().getData();
                komentarAdapter = new KomentarAdapter(KomentarActivity.this,komentars);
                layoutManager = new LinearLayoutManager(KomentarActivity.this, LinearLayoutManager.VERTICAL, false);
                rvKomentar.setLayoutManager(layoutManager);
                rvKomentar.smoothScrollToPosition(komentars.size());
                rvKomentar.setAdapter(komentarAdapter);
                komentarAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseKomentar> call, Throwable t) {

            }
        });
    }

    private void loadPostingan() {
        total_koment = getIntent().getStringExtra("total_koment");
        postingan_id = getIntent().getStringExtra("postingan_id");
        tvNama.setText(getIntent().getStringExtra("postingan_nama"));
        tvTime.setText(getIntent().getStringExtra("postingan_waktu"));
        tvPostingan.setText(getIntent().getStringExtra("postingan_deskripsi"));
        String foto = getIntent().getStringExtra("postingan_foto");
        String foto_profil = map.get(sessionManager.KEY_PENGGUNA_FOTO);
        Picasso.get()
                .load(img_url+foto)
                .placeholder(android.R.drawable.sym_def_app_icon)
                .error(android.R.drawable.sym_def_app_icon)
                .into(ivFoto);
        Picasso.get()
                .load(img_url+foto_profil)
                .placeholder(android.R.drawable.sym_def_app_icon)
                .error(android.R.drawable.sym_def_app_icon)
                .into(ivFotoProfile);
    }

}
