package com.azhar.couplecat.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.azhar.couplecat.Activity.PaymentActivity;
import com.azhar.couplecat.Model.ResponseRowPemesanan;
import com.azhar.couplecat.R;
import com.azhar.couplecat.Rest.CombineApi;
import com.azhar.couplecat.Rest.CoupleCatInterface;
import com.azhar.couplecat.Utils.SessionManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.azhar.couplecat.Rest.CombineApi.img_url;

public class RPemesananAdapter extends RecyclerView.Adapter<RPemesananAdapter.ViewHolder> {

    Context context;
    HashMap<String, String> map;
    Dialog myDialog;
    SessionManager sessionManager;
    CoupleCatInterface coupleCatInterface;
    String waktu = null;
    private ArrayList<String> data_id;
    String judul = null;
    String total = null;
    String kontes = null;
    public RPemesananAdapter(Context context, ArrayList<String> inputData) {
        this.context = context;

        data_id = inputData;
        sessionManager = new SessionManager(context);
        map = sessionManager.getDetailsLoggin();
        coupleCatInterface = CombineApi.getApiService();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvJudul, tvTotal, tvTanggal;
        public ImageView ivPicture;
        protected Button btnBayar;

        public ViewHolder(View v) {
            super(v);
            tvJudul = (TextView) v.findViewById(R.id.tvJudul);
            ivPicture = (ImageView) v.findViewById(R.id.ivBackground);
            tvTotal = (TextView) v.findViewById(R.id.tvTotal);
            tvTanggal = (TextView) v.findViewById(R.id.tvTanggal);
            btnBayar = (Button) v.findViewById(R.id.btnBayar);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_pemesanan, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int pofgdion) {
        final String id_list = data_id.get(pofgdion);
        Call<ResponseRowPemesanan> responseRowPemesananCall = coupleCatInterface.getOneOfPemesanan(id_list);
        responseRowPemesananCall.enqueue(new Callback<ResponseRowPemesanan>() {
            @Override
            public void onResponse(Call<ResponseRowPemesanan> call, Response<ResponseRowPemesanan> response) {
                Log.d("kantau", "onResponse: " + response.body().getStatus());
                Log.d("kantau", "onResponse: " + response.body().getJudul());
                if (response.body().getStatus() == 200) {
                    kontes = response.body().getKontes();
                    holder.tvJudul.setText(response.body().getJudul());
                    holder.tvTotal.setText("Rp. " + response.body().getJumlah());
                    judul = response.body().getJudul();
                    total = response.body().getJumlah();
                    String time = response.body().getTanggal();
                    String bulan = null;
                    switch (time.substring(5, 7)) {
                        case "01":
                            bulan = "Januari";
                            break;
                        case "02":
                            bulan = "Februari";
                            break;
                        case "03":
                            bulan = "Maret";
                            break;
                        case "04":
                            bulan = "April";
                            break;
                        case "05":
                            bulan = "Mei";
                            break;
                        case "06":
                            bulan = "Juni";
                            break;
                        case "07":
                            bulan = "Juli";
                            break;
                        case "08":
                            bulan = "Agustus";
                            break;
                        case "09":
                            bulan = "September";
                            break;
                        case "10":
                            bulan = "Oktober";
                            break;
                        case "11":
                            bulan = "November";
                            break;
                        case "12":
                            bulan = "Desember";
                            break;
                    }
                    waktu = time.substring(8, 10) + " "
                            + bulan + " "
                            + time.substring(0, 4);
                    holder.tvTanggal.setText(waktu);

                    Picasso.get()
                            .load(img_url+response.body().getFoto())
                            .placeholder(android.R.drawable.sym_def_app_icon)
                            .error(android.R.drawable.sym_def_app_icon)
                            .into(holder.ivPicture);
                }

            }

            @Override
            public void onFailure(Call<ResponseRowPemesanan> call, Throwable t) {
                Log.d("kantau", "onFailure: " + t.toString());
            }
        });

        holder.btnBayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent i = new Intent(context,PaymentActivity.class);
                i.putExtra("id", id_list);
                i.putExtra("judul", judul);
                i.putExtra("total",total);
                i.putExtra("kontes",kontes);
                context.startActivity(i);
            }
        });
        /*
        holder.tvJudul.setText(Pemesanan.getKontesNama());
        holder.tvTotal.setText(Pemesanan.getPemesananTotal());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String today = dateFormat.format(date);
        String time = Pemesanan.getKontesTanggalMulai();
        Date d1,d2;
        holder.btnBayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotopembayaran = new Intent(context,PaymentActivity.class);
                gotopembayaran.putExtra("pemesanan",Pemesanan.getPemesananId());
                gotopembayaran.putExtra("total",Pemesanan.getPemesananTotal());
                context.startActivity(gotopembayaran);
            }
        });
        try {

            d1 = dateFormat.parse(time);
            d2 = dateFormat.parse(today);
            long diff = d2.getTime() - d1.getTime();
            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);
            if (diffDays == 0 && diffHours == 0 && diffMinutes == 0){
                waktu = String.valueOf(diffSeconds)+" detik yang lalu";
            }
            else if (diffDays == 0 && diffHours == 0){
                waktu = String.valueOf(diffMinutes)+" menit yang lalu";
            }
            else if (diffDays == 0){
                waktu = String.valueOf(diffHours)+" jam yang lalu";
            }
            else{
                if (diffDays % 30 > 0 && diffDays % 30 <= 12){
                    waktu = String.valueOf(diffDays)+" Bulan yang lalu";
                }
                else if(diffDays % 30 == 0){
                    waktu = String.valueOf(diffDays)+" Hari yang lalu";
                }
                else{
                    String bulan = null;
                    switch (time.substring(5,7)){
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
                    waktu = time.substring(8,10)+" "
                            +bulan+" "
                            +time.substring(0,4);
                }
            }
            holder.tvTanggal.setText(waktu);
        }
        catch (Exception e){

        }
        Picasso.get()
                .load(img_url+Pemesanan.getKontesFoto())
                .placeholder(android.R.drawable.sym_def_app_icon)
                .error(android.R.drawable.sym_def_app_icon)
                .into(holder.ivPicture);
                */
    }

    @Override
    public int getItemCount() {
        return data_id.size();
    }


}