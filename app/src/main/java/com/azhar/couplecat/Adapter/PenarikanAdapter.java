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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.azhar.couplecat.Model.Contest;
import com.azhar.couplecat.Model.ResponseContest;
import com.azhar.couplecat.Model.ResponseSaldo;
import com.azhar.couplecat.R;
import com.azhar.couplecat.Rest.CombineApi;
import com.azhar.couplecat.Rest.CoupleCatInterface;
import com.azhar.couplecat.Utils.SessionManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.azhar.couplecat.Rest.CombineApi.img_url;

public class PenarikanAdapter extends RecyclerView.Adapter<PenarikanAdapter.ViewHolder> implements Filterable {
    private ArrayList<Contest> rvData;
    Context context;
    Dialog myDialog;
    CoupleCatInterface coupleCatInterface;
    private ArrayList<Contest> rvDataList;
    SessionManager sessionManager;
    private List<ResponseContest> data;
    HashMap<String, String> map;
    public PenarikanAdapter(Context context, ArrayList<Contest> inputData) {
        this.context = context;
        rvData = inputData;
        rvDataList = new ArrayList<>(rvData);
        sessionManager = new SessionManager(context);
        map = sessionManager.getDetailsLoggin();
        coupleCatInterface = CombineApi.getApiService();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTotal, tvJudul;
        public ImageView ivFoto;
        public RelativeLayout lyContainer;
        public Button btnTarik;
        public ViewHolder(View v) {
            super(v);
            btnTarik = (Button) v.findViewById(R.id.btnTarik);
            lyContainer = (RelativeLayout) v.findViewById(R.id.rlPemesanan);
            tvTotal = (TextView) v.findViewById(R.id.tvTotal);
            tvJudul = (TextView) v.findViewById(R.id.tvJudul);
            ivFoto = (ImageView) v.findViewById(R.id.ivBackground);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_penarikan, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int pofgdion) {
        final Contest Contest = rvData.get(pofgdion);
        holder.tvJudul.setText(Contest.getKontesNama());
        Call<ResponseSaldo> responseSaldoCall = coupleCatInterface.saldokontes(map.get(sessionManager.KEY_PENGGUNA_ID),
                Contest.getKontesId());
        responseSaldoCall.enqueue(new Callback<ResponseSaldo>() {
            @Override
            public void onResponse(Call<ResponseSaldo> call, Response<ResponseSaldo> response) {
                if (response.body().getStatus().equals("200")){
                  holder.tvTotal.setText(response.body().getTotal());
                }
            }

            @Override
            public void onFailure(Call<ResponseSaldo> call, Throwable t) {

            }
        });
        holder.btnTarik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Transfer uang kontes ke saldo?")
                        .setConfirmText("Ya")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                Call<ResponseSaldo> transfer = coupleCatInterface.transfer(map.get(sessionManager.KEY_PENGGUNA_ID),
                                        holder.tvTotal.getText().toString(),
                                        Contest.getKontesId());
                                transfer.enqueue(new Callback<ResponseSaldo>() {
                                    @Override
                                    public void onResponse(Call<ResponseSaldo> call, Response<ResponseSaldo> response) {
                                        if (response.body().getStatus().equals("200")){
                                            Toast.makeText(context, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                    @Override
                                    public void onFailure(Call<ResponseSaldo> call, Throwable t) {

                                    }
                                });
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .setCancelButton("Cancel", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .show();
            }
        });
        String gambar = Contest.getKontesFoto();
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
            ArrayList<Contest> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(rvDataList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Contest item : rvDataList) {
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
