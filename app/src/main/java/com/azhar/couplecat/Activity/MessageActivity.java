package com.azhar.couplecat.Activity;

import android.os.Handler;
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

import com.azhar.couplecat.Adapter.KomentarAdapter;
import com.azhar.couplecat.Adapter.MessageAdapter;
import com.azhar.couplecat.Model.Komentar;
import com.azhar.couplecat.Model.Message;
import com.azhar.couplecat.Model.ResponseCheckMessage;
import com.azhar.couplecat.Model.ResponseContact;
import com.azhar.couplecat.Model.ResponseKontak;
import com.azhar.couplecat.Model.ResponseMessage;
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

public class MessageActivity extends AppCompatActivity {
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.ivFoto)
    ImageView ivFoto;
    @BindView(R.id.rvChat)
    RecyclerView rvChat;
    @BindView(R.id.etText)
    EditText etText;
    ArrayList<Message> messages = new ArrayList<>();
    SessionManager sessionManager;
    CoupleCatInterface coupleCatInterface;
    MessageAdapter messageAdapter;
    HashMap<String, String> map;
    RecyclerView.LayoutManager layoutManager;
    String kontak, foto, nama;
    Handler handler;
    int total,num_delivered,num_read;
    String TAG = "kumbang";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ButterKnife.bind(this);
        total = 0;
        num_delivered = 0;
        num_read = 0;
        sessionManager = new SessionManager(this);
        map = sessionManager.getDetailsLoggin();
        layoutManager = new LinearLayoutManager(MessageActivity.this, LinearLayoutManager.VERTICAL, false);
        rvChat.setLayoutManager(layoutManager);
        coupleCatInterface = CombineApi.getApiService();
        handler = new Handler();
        m_Runnable.run();
        loadData();
        if (getIntent().getStringExtra("jenis").equals("1")) {
            kontak = getIntent().getStringExtra("kontak_id");
            loadMessage();
        } else {
            checkData();
        }

    }
    private final Runnable m_Runnable = new Runnable() {
        public void run() {
            if (kontak!=null){
                checkChangerMessage();
            }
            MessageActivity.this.handler.postDelayed(m_Runnable, 300);
        }

    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        handler.removeCallbacks(m_Runnable);
    }

    private void checkChangerMessage() {
        Call<ResponseCheckMessage> responseMessageCall = coupleCatInterface.checkMessageChange(kontak);
        responseMessageCall.enqueue(new Callback<ResponseCheckMessage>() {
            @Override
            public void onResponse(Call<ResponseCheckMessage> call, Response<ResponseCheckMessage> response) {

                if (response.body().getStatus() == 200){

                    if (total != response.body().getTotal() || num_delivered != response.body().getDelivered() || num_read != response.body().getRead()){
                        loadMessage();
                    }
                    total = response.body().getTotal();
                    num_delivered = response.body().getDelivered();
                    num_read = response.body().getRead();
                }
            }

            @Override
            public void onFailure(Call<ResponseCheckMessage> call, Throwable t) {

            }
        });
    }

    private void checkData() {
        Call<ResponseKontak> responseKontakCall = coupleCatInterface.checkKontak(map.get(sessionManager.KEY_PENGGUNA_ID),
                getIntent().getStringExtra("pengguna_id"));
        responseKontakCall.enqueue(new Callback<ResponseKontak>() {
            @Override
            public void onResponse(Call<ResponseKontak> call, Response<ResponseKontak> response) {
                if (response.body().getStatus() == 200){
                    kontak = response.body().getData().get(0).getKontakId();
                    loadMessage();
                }
                else{
                    kontak = null;
                }
            }

            @Override
            public void onFailure(Call<ResponseKontak> call, Throwable t) {

            }
        });
    }

    private void loadData() {
        foto = getIntent().getStringExtra("foto");
        nama = getIntent().getStringExtra("nama");
        Picasso.get()
                .load(img_url + foto)
                .placeholder(android.R.drawable.sym_def_app_icon)
                .error(android.R.drawable.sym_def_app_icon)
                .into(ivFoto);
        tvName.setText(nama);
    }

    private void loadMessage() {
        Call<ResponseMessage> responseMessageCall = coupleCatInterface.getConversation(kontak, map.get(sessionManager.KEY_PENGGUNA_ID));
        responseMessageCall.enqueue(new Callback<ResponseMessage>() {
            @Override
            public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                if (response.body().getStatus() == 200) {
                    messages = (ArrayList) response.body().getData();
                    messageAdapter = new MessageAdapter(MessageActivity.this, messages);
                    layoutManager = new LinearLayoutManager(MessageActivity.this, LinearLayoutManager.VERTICAL, false);
                    rvChat.setLayoutManager(layoutManager);
                    rvChat.smoothScrollToPosition(messages.size());
                    rvChat.setAdapter(messageAdapter);
                    messageAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ResponseMessage> call, Throwable t) {

            }
        });
    }
    @OnClick(R.id.btnSend)
    protected void btnSend(View view) {
        if (kontak == null) {
            Call<ResponseContact> ResponseContactCall = coupleCatInterface.addKontak(map.get(sessionManager.KEY_PENGGUNA_ID),
                    getIntent().getStringExtra("pengguna_id"));
            ResponseContactCall.enqueue(new Callback<ResponseContact>() {
                @Override
                public void onResponse(Call<ResponseContact> call, Response<ResponseContact> response) {
                    if (response.body().getStatus() == 200){
                        kontak = response.body().getData().getKontakId();
                       addMessage();
                    }
                }

                @Override
                public void onFailure(Call<ResponseContact> call, Throwable t) {

                }
            });
        }
        else{
            addMessage();
        }
    }
    protected void addMessage(){
        String pesan = etText.getText().toString();
        if (pesan.equals("")){

        }
        else{
        Call<ResponseMessage> responseMessageCall = coupleCatInterface.addMessage(kontak,
                map.get(sessionManager.KEY_PENGGUNA_ID),
                getIntent().getStringExtra("pengguna_id"),
                pesan);
        responseMessageCall.enqueue(new Callback<ResponseMessage>() {
            @Override
            public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                if (response.body().getStatus() == 200) {
                    etText.setText("");
                }
            }

            @Override
            public void onFailure(Call<ResponseMessage> call, Throwable t) {

            }
        });
        }
    }
}
