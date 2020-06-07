package com.azhar.couplecat.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.azhar.couplecat.Activity.LoginActivity;
import com.azhar.couplecat.Activity.MessageActivity;
import com.azhar.couplecat.Model.Jadwal;
import com.azhar.couplecat.Model.ResponseJadwal;
import com.azhar.couplecat.Model.ResponseLastMessage;
import com.azhar.couplecat.Model.ResponseOneCat;
import com.azhar.couplecat.Model.ResponsePengguna;
import com.azhar.couplecat.R;
import com.azhar.couplecat.Rest.CombineApi;
import com.azhar.couplecat.Rest.CoupleCatInterface;
import com.azhar.couplecat.Utils.SessionManager;
import com.azhar.couplecat.Utils.SweetAllert;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static com.azhar.couplecat.Rest.CombineApi.img_url;

public class WaitingScheduleAdapter extends RecyclerView.Adapter<WaitingScheduleAdapter.ViewHolder> implements Filterable {
    private ArrayList<Jadwal> rvData;
    Context context;
    Dialog myDialog;
    CoupleCatInterface coupleCatInterface;
    private ArrayList<Jadwal> rvDataList;
    SessionManager sessionManager;
    private List<ResponseJadwal> data;
    HashMap<String, String> map;
    String jadwal_id, nama, foto,jantan,betina,jenis_jantan,jenis_betina,imgJantan,imgBetina;
    String TAG = "Kambing";
    SweetAllert alert;
    public WaitingScheduleAdapter(Context context, ArrayList<Jadwal> inputData) {
        this.context = context;
        rvData = inputData;
        alert = new SweetAllert(context);
        rvDataList = new ArrayList<>(rvData);
        sessionManager = new SessionManager(context);
        map = sessionManager.getDetailsLoggin();
        coupleCatInterface = CombineApi.getApiService();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvNama, tvTanggal, tvHal;
        public ImageView ivFoto;
        public LinearLayout lyContainer;
        public Button btnSetuju,btnTolak;
        public ViewHolder(View v) {
            super(v);
            btnSetuju = (Button) v.findViewById(R.id.btnSetuju);
            btnTolak = (Button) v.findViewById(R.id.btnTolak);
            lyContainer = (LinearLayout) v.findViewById(R.id.lyContainer);
            tvNama = (TextView) v.findViewById(R.id.tvNama);
            tvTanggal = (TextView) v.findViewById(R.id.tvTanggal);
            tvHal = (TextView) v.findViewById(R.id.tvHal);
            ivFoto = (ImageView) v.findViewById(R.id.ivFoto);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_schedule_waiting, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int pofgdion) {
        final Jadwal Jadwal = rvData.get(pofgdion);
        //get last message
        String pengguna_id;
        if (Jadwal.getPenjadwalanIdKucingPengaju().equals(map.get(sessionManager.KEY_PENGGUNA_ID))){
            pengguna_id = Jadwal.getPenjadwalanIdPenerima();
        }
        else{
            pengguna_id = Jadwal.getPenjadwalanIdPengaju();
        }
        //detail kucing pengaju
        Call<ResponseOneCat> getKucingPengaju = coupleCatInterface.getCatDetail(Jadwal.getPenjadwalanIdKucingPengaju());
        getKucingPengaju.enqueue(new Callback<ResponseOneCat>() {
            @Override
            public void onResponse(Call<ResponseOneCat> call, Response<ResponseOneCat> response) {
                if (response.body().getData().getKucingJk().toLowerCase().equals("jantan")){
                    jantan = response.body().getData().getKucingNama();
                    jenis_jantan = response.body().getData().getKucingJenis();
                    imgJantan = response.body().getData().getKucingFoto();
                }
                else{
                    betina = response.body().getData().getKucingNama();
                    jenis_betina = response.body().getData().getKucingJenis();
                    imgBetina = response.body().getData().getKucingFoto();
                }
            }

            @Override
            public void onFailure(Call<ResponseOneCat> call, Throwable t) {

            }
        });
        //detail kucing penerima
        Call<ResponseOneCat> getKucingPenerima = coupleCatInterface.getCatDetail(Jadwal.getPenjadwalanIdKucingPenerima());
        getKucingPenerima.enqueue(new Callback<ResponseOneCat>() {
            @Override
            public void onResponse(Call<ResponseOneCat> call, Response<ResponseOneCat> response) {
                if (response.body().getData().getKucingJk().toLowerCase().equals("jantan")){
                    jantan = response.body().getData().getKucingNama();
                    jenis_jantan = response.body().getData().getKucingJenis();
                    imgJantan = response.body().getData().getKucingFoto();
                }
                else{
                    betina = response.body().getData().getKucingNama();
                    jenis_betina = response.body().getData().getKucingJenis();
                    imgBetina = response.body().getData().getKucingFoto();
                }
            }

            @Override
            public void onFailure(Call<ResponseOneCat> call, Throwable t) {

            }
        });

        //detail pengguna
        Call<ResponsePengguna> getInfoJadwal = coupleCatInterface.detailAccount(pengguna_id);
        getInfoJadwal.enqueue(new Callback<ResponsePengguna>() {
            @Override
            public void onResponse(Call<ResponsePengguna> call, Response<ResponsePengguna> response) {
                if (response.isSuccessful()) {
                    nama = response.body().getData().getPenggunaNama();
                    foto = response.body().getData().getPenggunaFoto();
                    holder.tvNama.setText("" + response.body().getData().getPenggunaNama());
                    String gambar = response.body().getData().getPenggunaFoto();
                    if (gambar.equals("")) {
                        gambar = "assets/images/user.png";
                    }
                    Picasso.get()
                            .load(img_url + gambar)
                            .placeholder(android.R.drawable.sym_def_app_icon)
                            .error(android.R.drawable.sym_def_app_icon)
                            .into(holder.ivFoto);
                }
            }
            @Override
            public void onFailure(Call<ResponsePengguna> call, Throwable t) {

            }
        });
        String bulan = null;

        if (Jadwal.getPenjadwalanStatus().equals("menunggu")) {
            switch (Jadwal.getPenjadwalanTglPengajuan().substring(5,7)){
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
            holder.tvTanggal.setText("Diajukan pada "+Jadwal.getPenjadwalanTglPengajuan().substring(8,10)+" "+bulan+" "+Jadwal.getPenjadwalanTglPengajuan().substring(0,4));
        } else if (Jadwal.getPenjadwalanStatus().equals("setuju")) {
            switch (Jadwal.getPenjadwalanTglTerima().substring(5,7)){
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
            holder.tvTanggal.setText("Disetujui pada "+Jadwal.getPenjadwalanTglTerima().substring(8,10)+" "+bulan+" "+Jadwal.getPenjadwalanTglTerima().substring(0,4));
        } else {
            holder.tvTanggal.setText("Ditolak");
        }
        holder.tvHal.setText(Jadwal.getPenjadwalanDeskripsi());
        holder.lyContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
                Display display = wm.getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);
                int width = size.x;
                int height = size.y;
                LayoutInflater inflater = (LayoutInflater)
                        context.getSystemService(LAYOUT_INFLATER_SERVICE);
                final View popupView = inflater.inflate(R.layout.detail_penjadwalan, null);
                //ImageView foto = (ImageView)popupView.findViewById(R.id.kunjunganFoto);
                ImageView close = (ImageView)popupView.findViewById(R.id.btnClose);
                ImageView ivJantan = (ImageView)popupView.findViewById(R.id.ivJantan);
                ImageView ivBentina = (ImageView)popupView.findViewById(R.id.ivBetina);
                TextView namajantan,namabetina,j_j,j_b,waktu,lokasi,detailwaktu;
                sessionManager = new SessionManager(context);
                namabetina = (TextView)popupView.findViewById(R.id.tvNamaBetina);
                namajantan = (TextView)popupView.findViewById(R.id.tvNamaJantan);
                j_j = (TextView)popupView.findViewById(R.id.tvJenisJantan);
                j_b = (TextView)popupView.findViewById(R.id.tvJenisBetina);
                waktu = (TextView)popupView.findViewById(R.id.tvTanggal);
                lokasi = (TextView)popupView.findViewById(R.id.tvLokasi);
                detailwaktu = (TextView)popupView.findViewById(R.id.tvDetailWaktu);
                final PopupWindow popupWindow = new PopupWindow(popupView);
                popupWindow.setWidth(width);
                popupWindow.setAnimationStyle(android.R.style.Animation_Translucent);
                popupWindow.setHeight(height);
                popupWindow.setFocusable(true);
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
                namabetina.setText(betina);
                namajantan.setText(jantan);
                j_b.setText(jenis_betina);
                j_j.setText(jenis_jantan);
                String bulan = null;
                switch (Jadwal.getPenjadwalanTanggal().substring(5,7)){
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
                detailwaktu.setText(holder.tvTanggal.getText());
                waktu.setText(Jadwal.getPenjadwalanTanggal().substring(8,10)+" "+bulan+" "+Jadwal.getPenjadwalanTanggal().substring(0,4));
                lokasi.setText(Jadwal.getPenjadwalanLokasi());
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });
                Picasso.get()
                        .load(img_url + imgJantan)
                        .placeholder(android.R.drawable.sym_def_app_icon)
                        .error(android.R.drawable.sym_def_app_icon)
                        .into(ivJantan);
                Picasso.get()
                        .load(img_url + imgBetina)
                        .placeholder(android.R.drawable.sym_def_app_icon)
                        .error(android.R.drawable.sym_def_app_icon)
                        .into(ivBentina);
            }
        });
        holder.btnTolak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.confirmAllert();
                if (alert.getConfirm() == true){
                    Call<ResponseJadwal> responseJadwalCall = coupleCatInterface.rejectJadwal(Jadwal.getPenjadwalanId());
                    responseJadwalCall.enqueue(new Callback<ResponseJadwal>() {
                        @Override
                        public void onResponse(Call<ResponseJadwal> call, Response<ResponseJadwal> response) {
                            alert.errorAllert("Membatalkan Jadwal");
                        }

                        @Override
                        public void onFailure(Call<ResponseJadwal> call, Throwable t) {

                        }
                    });
                }
            }
        });
        holder.btnSetuju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.confirmAllert();
                if (alert.getConfirm() == true){
                    alert.loadingAllert("Sedang menyetujui Data");
                    Call<ResponseJadwal> responseJadwalCall = coupleCatInterface.accJadwal(Jadwal.getPenjadwalanId());
                    responseJadwalCall.enqueue(new Callback<ResponseJadwal>() {
                        @Override
                        public void onResponse(Call<ResponseJadwal> call, Response<ResponseJadwal> response) {

                            if (response.body().getStatus() == 200){
                                alert.dismissloadingAllert();
                                alert.successAllert("Berhasil Menyetujui Jadwal");
                            }

                        }

                        @Override
                        public void onFailure(Call<ResponseJadwal> call, Throwable t) {

                        }
                    });
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return rvData.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    public Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Jadwal> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(rvDataList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Jadwal item : rvDataList) {
                    if (item.getPenjadwalanStatus().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    } else if (item.getPenjadwalanStatus().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            rvData.clear();
            rvData.addAll((ArrayList) results.values);
            notifyDataSetChanged();
        }
    };

}
