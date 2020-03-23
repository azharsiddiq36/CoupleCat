package com.azhar.couplecat.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.azhar.couplecat.Adapter.MessageAdapter;
import com.azhar.couplecat.R;
import com.azhar.couplecat.Rest.CombineApi;
import com.azhar.couplecat.Rest.CoupleCatInterface;
import com.azhar.couplecat.Utils.SessionManager;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MessageActivity extends AppCompatActivity {
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.ivFoto)
    ImageView ivFoto;
    @BindView(R.id.rvChat)
    RecyclerView rvChat;
    @BindView(R.id.etText)
    EditText etText;
    SessionManager sessionManager;
    CoupleCatInterface coupleCatInterface;
    MessageAdapter messageAdapter;
    HashMap<String,String> map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(this);
        map = sessionManager.getDetailsLoggin();
        coupleCatInterface = CombineApi.getApiService();
        loadData();
        loadMessage();
    }

    private void loadData() {

    }

    private void loadMessage() {

    }

    @OnClick(R.id.btnSend)
    protected void btnSend(View view){

    }
}
