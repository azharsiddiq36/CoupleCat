package com.azhar.couplecat.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.azhar.couplecat.Activity.ChangePasswordActivity;
import com.azhar.couplecat.Activity.LoginActivity;
import com.azhar.couplecat.Activity.MainActivity;
import com.azhar.couplecat.Activity.MyAccountActivity;
import com.azhar.couplecat.Activity.MyCatActivity;
import com.azhar.couplecat.Activity.ContestActivity;
import com.azhar.couplecat.Activity.MyFriendsActivity;
import com.azhar.couplecat.Activity.MyScheduleActivity;
import com.azhar.couplecat.Activity.MyStoreActivity;
import com.azhar.couplecat.Activity.MyWalletActivity;
import com.azhar.couplecat.Activity.PersonalDataActivity;
import com.azhar.couplecat.Model.ResponsePengguna;
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


public class AccountFragment extends Fragment {
    SessionManager sessionManager;
    CoupleCatInterface coupleCatInterface;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvNumber)
    TextView tvNumber;
    @BindView(R.id.tvSaldo)
    TextView tvSaldo;
    @BindView(R.id.tvPersonal)
    TextView tvPersonal;
    @BindView(R.id.tvNotif)
    TextView tvNotif;
    @BindView(R.id.ivFotoProfile)
    ImageView ivFotoProfile;
    @BindView(R.id.lyNotif)
    LinearLayout lyNotif;
    HashMap<String,String> map;
    String TAG = "FIX";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        android.support.v7.app.ActionBar actionBar =
                ((MainActivity) getActivity()).getSupportActionBar();
        assert actionBar != null;
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_overlay_splash));
        actionBar.setTitle("Profile");
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sessionManager = new SessionManager(getContext());
        map = sessionManager.getDetailsLoggin();
        coupleCatInterface = CombineApi.getApiService();
        getDetailAccount();
        if (map.get(sessionManager.KEY_PENGGUNA_VALIDASI).equals("sudah")){
            tvPersonal.setVisibility(View.INVISIBLE);
        }
        tvName.setText(map.get(sessionManager.KEY_PENGGUNA_NAMA));
        tvNumber.setText(map.get(sessionManager.KEY_PENGGUNA_NOMOR));
        getFoto(map.get(sessionManager.KEY_PENGGUNA_FOTO));
    }

    private void getDetailAccount() {
        coupleCatInterface.detailAccount(map.get(sessionManager.KEY_PENGGUNA_ID)).enqueue(new Callback<ResponsePengguna>() {
            @Override
            public void onResponse(Call<ResponsePengguna> call, Response<ResponsePengguna> response) {
                int status = response.body().getStatus();
                if (status == 200){
                    tvSaldo.setText("Rp. "+response.body().getData().getPenggunaSaldo().toString());
                    if (Integer.parseInt(response.body().getMessage())>0){
                        tvNotif.setText(response.body().getMessage().toString());
                    }
                    else{
                        lyNotif.setVisibility(View.INVISIBLE);
                    }
                }
                else{
                    Toast.makeText(getContext(), ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    lyNotif.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ResponsePengguna> call, Throwable t) {

            }
        });
    }

    @OnClick(R.id.tvPersonal)
    public void tvPersonal(View view){
        Intent gotopersonal = new Intent(getActivity(),PersonalDataActivity.class);
        startActivity(gotopersonal);
    }
    @OnClick(R.id.lyBtnPassword)
    public void tvBtnPassword(View view){
        Intent gotopassword = new Intent(getActivity(),ChangePasswordActivity.class);
        startActivity(gotopassword);
    }

    @OnClick(R.id.lyBtnMyWallet)
    public void lyBtnMyWallet(View view){
        Intent gotowallet = new Intent(getActivity(),MyWalletActivity.class);
        startActivity(gotowallet);
    }
    @OnClick(R.id.lyBtnMyAccount)
    public void lyBtnMyAccount(View view){
        Intent gotoaccount = new Intent(getActivity(),MyAccountActivity.class);
        startActivity(gotoaccount);
    }
    @OnClick(R.id.lyBtnMySchedule)
    public void lyBtnMySchedule(View view){
        Intent gotoschedule = new Intent(getActivity(),MyScheduleActivity.class);
        gotoschedule.putExtra("jumlah",tvNotif.getText().toString());
        startActivity(gotoschedule);
    }
    @OnClick(R.id.lyBtnMyEvent)
    public void lyBtnMyEvent(View view){
        Intent gotoevent = new Intent(getActivity(),ContestActivity.class);
        startActivity(gotoevent);
    }
    @OnClick(R.id.lyBtnMyCat)
    public void lyBtnMyCat(View view){
        Intent gotocat = new Intent(getActivity(),MyCatActivity.class);
        startActivity(gotocat);
    }
    @OnClick(R.id.lyBtnMyStore)
    public void lyBtnMyStore(View view){
        Intent gotostore = new Intent(getActivity(),MyStoreActivity.class);
        startActivity(gotostore);
    }
    @OnClick(R.id.lyBtnLogout)
    public void lyBtnLogout(View view){
        sessionManager.logout();
        Intent gotologin = new Intent(getActivity(),LoginActivity.class);
        startActivity(gotologin);
        getActivity().finish();
    }
    private void getFoto(String foto) {
            Picasso.get()
                    .load(img_url+foto)
                    .placeholder(android.R.drawable.sym_def_app_icon)
                    .error(android.R.drawable.sym_def_app_icon)
                    .into(ivFotoProfile);


    }
}
