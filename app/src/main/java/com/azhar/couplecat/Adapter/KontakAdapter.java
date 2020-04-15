package com.azhar.couplecat.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.azhar.couplecat.Activity.MessageActivity;
import com.azhar.couplecat.Model.Kontak;
import com.azhar.couplecat.Model.ResponseKontak;
import com.azhar.couplecat.Model.ResponseLastMessage;
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

public class KontakAdapter extends RecyclerView.Adapter<KontakAdapter.ViewHolder> implements Filterable {
    private ArrayList<Kontak> rvData;
    Context context;
    Dialog myDialog;
    CoupleCatInterface coupleCatInterface;
    private ArrayList<Kontak> rvDataList;
    SessionManager sessionManager;
    private List<ResponseKontak> data;
    HashMap<String, String> map;
    String kontak_id,nama,foto;
    String TAG = "Kambing";
    public KontakAdapter(Context context, ArrayList<Kontak> inputData) {
        this.context = context;
        rvData = inputData;
        rvDataList = new ArrayList<>(rvData);
        sessionManager = new SessionManager(context);
        map = sessionManager.getDetailsLoggin();
        coupleCatInterface = CombineApi.getApiService();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvNama, tvLastMessage, tvTime;
        public ImageView ivFoto, ivStatus;
        public LinearLayout lyContainer;

        public ViewHolder(View v) {
            super(v);
            lyContainer = (LinearLayout)v.findViewById(R.id.lyContainer);
            tvNama = (TextView) v.findViewById(R.id.tvNama);
            tvLastMessage = (TextView) v.findViewById(R.id.tvLastMessage);
            tvTime = (TextView) v.findViewById(R.id.tvTime);
            ivFoto = (ImageView) v.findViewById(R.id.ivFoto);
            ivStatus = (ImageView) v.findViewById(R.id.ivStatus);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_chat, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int pofgdion) {
        final Kontak Kontak = rvData.get(pofgdion);
        //get last message

        Call<ResponseLastMessage> getLastMessage = coupleCatInterface.getLastConversation(Kontak.getKontakId().toString());
        getLastMessage.enqueue(new Callback<ResponseLastMessage>() {
            @Override
            public void onResponse(Call<ResponseLastMessage> call, Response<ResponseLastMessage> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 200){
                        kontak_id = null;
                        if (Kontak.getKontakPenggunaId().equals(map.get(sessionManager.KEY_PENGGUNA_ID))) {
                            kontak_id = Kontak.getKontakPenggunaId2().toString();
                        } else {
                            kontak_id = Kontak.getKontakPenggunaId().toString();
                        }
                        //get info kontak
                        Call<ResponsePengguna> getInfoKontak = coupleCatInterface.detailAccount(kontak_id);
                        getInfoKontak.enqueue(new Callback<ResponsePengguna>() {
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
                        holder.tvLastMessage.setText(""+response.body().getData().getChattingText());
                        holder.tvTime.setText("" +response.body().getData().getChattingTanggal().substring(8,10)+"-"
                                +response.body().getData().getChattingTanggal().substring(5,7)+"-"
                                +response.body().getData().getChattingTanggal().substring(0,4)+" ("+response.body().getData().getChattingTanggal().substring(11,16)+")");
                        switch (response.body().getData().getChattingStatus()) {
                            case "delivered":
                                Picasso.get()
                                        .load(R.drawable.check_delivered)
                                        .error(android.R.drawable.sym_def_app_icon)
                                        .into(holder.ivStatus);
                                break;
                            case "send":
                                Picasso.get()
                                        .load(R.drawable.check_sent)
                                        .error(android.R.drawable.sym_def_app_icon)
                                        .into(holder.ivStatus);
                                break;
                            case "read":
                                Picasso.get()
                                        .load(R.drawable.check_read)
                                        .error(android.R.drawable.sym_def_app_icon)
                                        .into(holder.ivStatus);
                                break;
                            default:
                                Picasso.get()
                                        .load(R.drawable.check_delivered)
                                        .error(android.R.drawable.sym_def_app_icon)
                                        .into(holder.ivStatus);
                                break;

                        }
                    }
                    else{
                        holder.lyContainer.setVisibility(View.GONE);
                    }
                }
                holder.lyContainer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(context,MessageActivity.class);
                        i.putExtra("kontak_id",Kontak.getKontakId());
                        i.putExtra("pengguna_id",kontak_id);
                        i.putExtra("jenis","1");
                        i.putExtra("nama",nama);
                        i.putExtra("foto",foto);
                        context.startActivity(i);
                    }
                });
            }

            @Override
            public void onFailure(Call<ResponseLastMessage> call, Throwable t) {

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
            ArrayList<Kontak> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(rvDataList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Kontak item : rvDataList) {
                    if (item.getKontakId().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    } else if (item.getKontakId().toLowerCase().contains(filterPattern)) {
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
