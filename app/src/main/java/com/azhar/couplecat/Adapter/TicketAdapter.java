package com.azhar.couplecat.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.azhar.couplecat.Activity.BarcodeTicketActivity;
import com.azhar.couplecat.Model.Pembayaran;
import com.azhar.couplecat.Model.ResponsePembayaran;
import com.azhar.couplecat.R;
import com.azhar.couplecat.Rest.CombineApi;
import com.azhar.couplecat.Rest.CoupleCatInterface;
import com.azhar.couplecat.Utils.SessionManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.azhar.couplecat.Rest.CombineApi.img_url;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.ViewHolder> implements Filterable {
    private ArrayList<Pembayaran> rvData;
    Context context;
    Dialog myDialog;
    CoupleCatInterface coupleCatInterface;
    private ArrayList<Pembayaran> rvDataList;
    SessionManager sessionManager;
    private List<ResponsePembayaran> data;
    HashMap<String, String> map;
    public TicketAdapter(Context context, ArrayList<Pembayaran> inputData) {
        this.context = context;
        rvData = inputData;
        rvDataList = new ArrayList<>(rvData);
        sessionManager = new SessionManager(context);
        map = sessionManager.getDetailsLoggin();
        coupleCatInterface = CombineApi.getApiService();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvNama, tvTanggal,tvLocation, tvStatus;
        public ImageView ivFoto;
        public RelativeLayout lyContainer;

        public ViewHolder(View v) {
            super(v);
            tvStatus = (TextView)v.findViewById(R.id.tvStatus);
            tvLocation = (TextView)v.findViewById(R.id.tvLokasi);
            lyContainer = (RelativeLayout) v.findViewById(R.id.rlKontes);
            tvNama = (TextView) v.findViewById(R.id.tvjud);
            tvTanggal = (TextView) v.findViewById(R.id.tvTgl);
            ivFoto = (ImageView) v.findViewById(R.id.ivBg);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_ticket, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int pofgdion) {
        final Pembayaran Pembayaran = rvData.get(pofgdion);
        holder.tvLocation.setText(Pembayaran.getKontesKabupaten()+", "+Pembayaran.getKontesProvinsi());
        holder.tvNama.setText(Pembayaran.getKontesNama());
        holder.tvTanggal.setText(dateindoconverter(Pembayaran.getKontesTanggalMulai()));
        holder.lyContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context,BarcodeTicketActivity.class);
                i.putExtra("id",Pembayaran.getKontesId());
                context.startActivity(i);
            }
        });
        if (Pembayaran.getPembayaranStatus().equals("setuju")){
            holder.tvStatus.setText("(Telah disetujui)");
        }
        else if (Pembayaran.getPembayaranStatus().equals("menunggu")){
            holder.tvStatus.setText("(Menunggu Persetujuan)");
            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.red_btn_bg_color));
        }
        else{
            holder.tvStatus.setVisibility(View.GONE);
        }
        String gambar = Pembayaran.getKontesFoto();
        Picasso.get()
                .load(img_url + gambar)
                .placeholder(android.R.drawable.sym_def_app_icon)
                .error(android.R.drawable.sym_def_app_icon)
                .into(holder.ivFoto);

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
            ArrayList<Pembayaran> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(rvDataList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Pembayaran item : rvDataList) {
                    if (item.getKontesNama().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    } else if (item.getKontesLokasi().toLowerCase().contains(filterPattern)) {
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
