package com.azhar.couplecat.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;
import com.azhar.couplecat.Model.ResponseMyCat;
import com.azhar.couplecat.Model.ResponsePasangan;
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

public class AddCoupleActivity extends AppCompatActivity {
    SessionManager sessionManager;
    HashMap<String, String> map;
    CoupleCatInterface coupleCatInterface;
    @BindView(R.id.spCat)
    Spinner spCat;
    @BindView(R.id.tgSenin)
    ToggleButton tgSenin;
    @BindView(R.id.tgMinggu)
    ToggleButton tgMinggu;
    @BindView(R.id.tgSabtu)
    ToggleButton tgSabtu;
    @BindView(R.id.tgJumat)
    ToggleButton tgJumat;
    @BindView(R.id.tgKamis)
    ToggleButton tgKamis;
    @BindView(R.id.tgRabu)
    ToggleButton tgRabu;
    @BindView(R.id.tgSelasa)
    ToggleButton tgSelasa;
    String kucing [];
    String hari[] = new String[7];
    String TAG = "Kambing";
    HashMap<String,String> tes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_couple);
        ButterKnife.bind(this);
        coupleCatInterface = CombineApi.getApiService();
        sessionManager = new SessionManager(AddCoupleActivity.this);
        map = sessionManager.getDetailsLoggin();
        loadKucing();
    }

    private void loadKucing() {
        Call<ResponseMyCat> responseMyCatCall = coupleCatInterface.myListCat(map.get(sessionManager.KEY_PENGGUNA_ID));
        responseMyCatCall.enqueue(new Callback<ResponseMyCat>() {
            @Override
            public void onResponse(Call<ResponseMyCat> call, Response<ResponseMyCat> response) {
                kucing = new String[response.body().getData().size()+1];
                tes = new HashMap<>();
                for (int i = 0;i<kucing.length;i++){
                    if (i == 0){
                        kucing[i] = "--- Pilih Kucing Anda ---";
                    }
                    else{
                        map.put(response.body().getData().get(i-1).getKucingNama(),response.body().getData().get(i-1).getKucingId());
                        kucing[i] = response.body().getData().get(i-1).getKucingNama();
                    }
                }
                final ArrayAdapter<String> adapter = new ArrayAdapter<>(AddCoupleActivity.this,
                        android.R.layout.simple_spinner_dropdown_item,kucing);
                spCat.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ResponseMyCat> call, Throwable t) {

            }
        });
    }

    @OnClick(R.id.btnAdd)
    public void btnAdd(View view) {
        String day = "";
        for (int i = 0;i<hari.length;i++){
            if (hari[i] == null){
                continue;
            }
            else{
                if (day.equals("")){
                    day = hari[i];
                }
                else{
                    day = day+","+hari[i];
                }
            }
        }
        String catId = map.get(spCat.getSelectedItem().toString());
        Call<ResponsePasangan> responsePasanganCall = coupleCatInterface.addCouple(catId,day,map.get(sessionManager.KEY_PENGGUNA_ID));
        responsePasanganCall.enqueue(new Callback<ResponsePasangan>() {
            @Override
            public void onResponse(Call<ResponsePasangan> call, Response<ResponsePasangan> response) {
                if (response.isSuccessful()){
                    Toast.makeText(AddCoupleActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(AddCoupleActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponsePasangan> call, Throwable t) {
                Toast.makeText(AddCoupleActivity.this, ""+t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        onBackPressed();
    }

    @OnClick(R.id.lyBack)
    public void lyBack(View view) {
        onBackPressed();
    }

    @OnClick(R.id.tgSenin)
    public void tgSenin(View view) {
        if (tgSenin.isChecked()) {
            hari[0] = "senin";
        } else {
            hari[0] = "";
        }
    }

    @OnClick(R.id.tgSelasa)
    public void tgSelasa(View view) {
        if (tgSelasa.isChecked()) {
            hari[1] = "selasa";
        } else {
            hari[1] = "";
        }
    }

    @OnClick(R.id.tgRabu)
    public void tgRabu(View view) {
        if (tgRabu.isChecked()) {
            hari[2] = "rabu";
        } else {
            hari[2] = "";
        }
    }

    @OnClick(R.id.tgKamis)
    public void tgKamis(View view) {
        if (tgKamis.isChecked()) {
            hari[3] = "kamis";
        } else {
            hari[3] = "";
        }
    }

    @OnClick(R.id.tgJumat)
    public void tgJumat(View view) {
        if (tgJumat.isChecked()) {
            hari[4] = "jumat";
        } else {
            hari[4] = "";
        }
    }

    @OnClick(R.id.tgSabtu)
    public void tgSabtu(View view) {
        if (tgSabtu.isChecked()) {
            hari[5] = "sabtu";
        } else {
            hari[5] = "";
        }

    }

    @OnClick(R.id.tgMinggu)
    public void tgMinggu(View view) {
        if (tgMinggu.isChecked()) {
            hari[6] = "minggu";
        } else {
            hari[6] = "";
        }
    }
}
