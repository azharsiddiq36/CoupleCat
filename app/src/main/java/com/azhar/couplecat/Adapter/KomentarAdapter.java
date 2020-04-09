package com.azhar.couplecat.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.Toast;

import com.azhar.couplecat.Activity.KomentarActivity;
import com.azhar.couplecat.Model.Komentar;
import com.azhar.couplecat.Model.ResponseKomentar;
import com.azhar.couplecat.R;
import com.azhar.couplecat.Rest.CombineApi;
import com.azhar.couplecat.Rest.CoupleCatInterface;
import com.azhar.couplecat.Utils.SessionManager;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.azhar.couplecat.Rest.CombineApi.img_url;

public class KomentarAdapter extends RecyclerView.Adapter<KomentarAdapter.ViewHolder> implements Filterable {
    private ArrayList<Komentar> rvData;
    Context context;
    HashMap<String, String> map;
    Dialog myDialog;
    SessionManager sessionManager;
    CoupleCatInterface coupleCatInterface;
    String waktu = null;
    private ArrayList<Komentar> rvDataList;
    public KomentarAdapter(Context context, ArrayList<Komentar> inputData){
        this.context = context;
        rvData=inputData;
        sessionManager = new SessionManager(context);
        map = sessionManager.getDetailsLoggin();
        coupleCatInterface = CombineApi.getApiService();
        rvDataList = new ArrayList<>(rvData);
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tvName,tvTime,tvDescription;
        public LinearLayout lyContainer;
        public ImageView ivPicture;
        public ViewHolder(View v){
            super(v);
            tvName = (TextView) v.findViewById(R.id.tvNama);
            ivPicture = (ImageView)v.findViewById(R.id.ivFoto);
            tvDescription = (TextView) v.findViewById(R.id.tvKoment);
            lyContainer=(LinearLayout) v.findViewById(R.id.lyContainer);
            tvTime = (TextView)v.findViewById(R.id.tvTime);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_komentar, parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int pofgdion) {
        final Komentar Komentar = rvData.get(pofgdion);
        holder.tvName.setText(Komentar.getPenggunaNama());
        holder.tvDescription.setText(Komentar.getKomentarDeskripsi());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String today = dateFormat.format(date);
        String time = Komentar.getKomentarTanggal();
        Date d1,d2;

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
            holder.tvTime.setText(waktu);
        }
        catch (Exception e){

        }
        Picasso.get()
                .load(img_url+Komentar.getPenggunaFoto())
                .placeholder(android.R.drawable.sym_def_app_icon)
                .error(android.R.drawable.sym_def_app_icon)
                .into(holder.ivPicture);
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
            ArrayList<Komentar> filteredList =new ArrayList<>();
            if (constraint==null||constraint.length()==0){
                filteredList.addAll(rvDataList);
            }
            else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Komentar item:rvDataList ){
                    if (item.getPenggunaNama().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                    else if (item.getKomentarTanggal().toLowerCase().contains(filterPattern)){
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
            rvData.addAll((ArrayList)results.values);
            notifyDataSetChanged();
        }

    };


}