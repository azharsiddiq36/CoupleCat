package com.azhar.couplecat.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.azhar.couplecat.Adapter.PostinganAdapter;
import com.azhar.couplecat.Adapter.PostinganAdapter;
import com.azhar.couplecat.Model.Couple;
import com.azhar.couplecat.Model.Postingan;
import com.azhar.couplecat.Model.ResponsePostingan;
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

public class PostinganActivity extends AppCompatActivity {
    SessionManager sessionManager;
    CoupleCatInterface coupleCatInterface;
    @BindView(R.id.rvPostingan)
    RecyclerView rvPostingan;
    PostinganAdapter postinganAdapter;
    RecyclerView.LayoutManager layoutManager;
    HashMap<String, String> map;
    ArrayList<Postingan> postingans = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postingan);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(PostinganActivity.this);
        coupleCatInterface = CombineApi.getApiService();
        map = sessionManager.getDetailsLoggin();
        layoutManager = new LinearLayoutManager(PostinganActivity.this, LinearLayoutManager.VERTICAL, false);
        rvPostingan.setLayoutManager(layoutManager);
        loadPostingan();
    }
    @OnClick(R.id.lyBtnAdd)
    protected void lyBtnAdd(View view){
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = inflater.inflate(R.layout.detail_postingan_add, null);
        final PopupWindow popupWindow = new PopupWindow(popupView);
        popupWindow.setWidth(width);
        popupWindow.setAnimationStyle(android.R.style.Animation_Translucent);
        popupWindow.setHeight(height-200);
        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        ImageView close = (ImageView)popupView.findViewById(R.id.btnClose);
        Button btnSubmit = (Button) popupView.findViewById(R.id.btnSubmit);
        final EditText etDescription = (EditText) popupView.findViewById(R.id.etDeskripsi);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<ResponsePostingan> responsePostinganCall = coupleCatInterface.addPostingan(
                        map.get(sessionManager.KEY_PENGGUNA_ID),etDescription.getText().toString());
                responsePostinganCall.enqueue(new Callback<ResponsePostingan>() {
                    @Override
                    public void onResponse(Call<ResponsePostingan> call, Response<ResponsePostingan> response) {
                        if (response.body().getStatus()==200){
                            Toast.makeText(PostinganActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            popupWindow.dismiss();
                            loadPostingan();
                        }
                        else{
                            Toast.makeText(PostinganActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponsePostingan> call, Throwable t) {
                        Toast.makeText(PostinganActivity.this, ""+t.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
    private void loadPostingan() {
        Call<ResponsePostingan> postinganCall = coupleCatInterface.getPostingan();
        postinganCall.enqueue(new Callback<ResponsePostingan>() {
            @Override
            public void onResponse(Call<ResponsePostingan> call, Response<ResponsePostingan> response) {
               postingans = (ArrayList) response.body().getData();
                postinganAdapter = new PostinganAdapter(PostinganActivity.this,postingans);
                rvPostingan.setAdapter(postinganAdapter);
                postinganAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponsePostingan> call, Throwable t) {

            }
        });
    }
}
