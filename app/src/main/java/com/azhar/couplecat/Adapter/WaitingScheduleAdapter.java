package com.azhar.couplecat.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.azhar.couplecat.Model.Jadwal;
import com.azhar.couplecat.Model.ResponseJadwal;
import com.azhar.couplecat.Model.ResponsePengguna;
import com.azhar.couplecat.R;
import com.azhar.couplecat.Rest.CombineApi;
import com.azhar.couplecat.Rest.CoupleCatInterface;
import com.azhar.couplecat.Utils.SessionManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    String jadwal_id, nama, foto;
    String TAG = "Kambing";

    public WaitingScheduleAdapter(Context context, ArrayList<Jadwal> inputData) {
        this.context = context;
        rvData = inputData;
        rvDataList = new ArrayList<>(rvData);
        sessionManager = new SessionManager(context);
        map = sessionManager.getDetailsLoggin();
        coupleCatInterface = CombineApi.getApiService();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvNama, tvTanggal, tvHal;
        public ImageView ivFoto;
        public LinearLayout lyContainer;
        Button btnTolak,btnSetuju;

        public ViewHolder(View v) {
            super(v);
            btnTolak = (Button) v.findViewById(R.id.btnTolak);
            btnSetuju = (Button)v.findViewById(R.id.btnSetuju);
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
