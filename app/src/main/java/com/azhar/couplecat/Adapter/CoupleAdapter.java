package com.azhar.couplecat.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

import com.azhar.couplecat.Activity.AddScheduleActivity;
import com.azhar.couplecat.Activity.MessageActivity;
import com.azhar.couplecat.Model.Couple;
import com.azhar.couplecat.Model.ResponseCouple;
import com.azhar.couplecat.R;
import com.azhar.couplecat.Utils.SessionManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static com.azhar.couplecat.Rest.CombineApi.img_url;

public class CoupleAdapter extends RecyclerView.Adapter<CoupleAdapter.ViewHolder> implements Filterable {
    private ArrayList<Couple> rvData;
    Context context;
    Dialog myDialog;

    private ArrayList<Couple> rvDataList;
    SessionManager sessionManager;
    private List<ResponseCouple> data;

    public CoupleAdapter(Context context, ArrayList<Couple> inputData) {
        this.context = context;
        rvData = inputData;
        rvDataList = new ArrayList<>(rvData);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvNama,tvJenis,tvWaktuPosting;
        public LinearLayout lyCouple;
        public ImageView imgCouple, imgGender;

        public ViewHolder(View v) {
            super(v);

            tvWaktuPosting = (TextView) v.findViewById(R.id.tvWaktuPosting);
            tvNama = (TextView) v.findViewById(R.id.tvNama);
            tvJenis = (TextView) v.findViewById(R.id.tvJenis);
            imgGender = (ImageView) v.findViewById(R.id.tvGender);
            imgCouple = (ImageView) v.findViewById(R.id.ivFoto);
            lyCouple = (LinearLayout) v.findViewById(R.id.lyMyCat);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_couple_cat, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int pofgdion) {
        final Couple Couple = rvData.get(pofgdion);
        holder.tvNama.setText(Couple.getKucingNama());
        holder.tvJenis.setText(Couple.getKucingJenis());
        String tgl = Couple.getPasanganTanggalPost().substring(0,10);

        holder.tvWaktuPosting.setText(Couple.getPasanganTanggalPost().substring(8,10)+"-"
                +Couple.getPasanganTanggalPost().substring(5,7)+"-"
                +Couple.getPasanganTanggalPost().substring(0,4));
        String gambar = Couple.getKucingFoto();
        if (gambar.equals("")) {
            gambar = "assets/images/user.png";
        }
        Picasso.get()
                .load(img_url + gambar)
                .placeholder(android.R.drawable.sym_def_app_icon)
                .error(android.R.drawable.sym_def_app_icon)
                .into(holder.imgCouple);
        if (Couple.getKucingJk().toLowerCase().equals("betina")) {
            Picasso.get()
                    .load(R.drawable.ic_female).placeholder(android.R.drawable.sym_def_app_icon)
                    .error(android.R.drawable.sym_def_app_icon)
                    .into(holder.imgGender);
        } else {
            Picasso.get().load(R.drawable.ic_male).placeholder(android.R.drawable.sym_def_app_icon)
                    .error(android.R.drawable.sym_def_app_icon)
                    .into(holder.imgGender);
        }
        holder.lyCouple.setOnClickListener(new View.OnClickListener() {
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
                final View popupView = inflater.inflate(R.layout.detail_couple, null);
                final PopupWindow popupWindow = new PopupWindow(popupView);
                popupWindow.setWidth(width);
                popupWindow.setAnimationStyle(android.R.style.Animation_Translucent);
                popupWindow.setHeight(height-200);
                popupWindow.setFocusable(true);
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
                ImageView kucing_foto = (ImageView)popupView.findViewById(R.id.kucingFoto);
                ImageView pemilik_foto = (ImageView)popupView.findViewById(R.id.ivPemilik);
                ImageView close = (ImageView)popupView.findViewById(R.id.btnClose);
                Button tutup;
                final TextView tvLocation,tvNamaKucing,tvJenisKucing,tvHari,tvNamaPemilik;
                sessionManager = new SessionManager(context);
                tvNamaKucing = (TextView)popupView.findViewById(R.id.tvNamaKucing);
                tvLocation = (TextView)popupView.findViewById(R.id.tvLocation);
                tvJenisKucing = (TextView)popupView.findViewById(R.id.tvRas);
                tvNamaPemilik = (TextView)popupView.findViewById(R.id.tvPemilik);
                tvHari = (TextView)popupView.findViewById(R.id.tvHari);
                tvNamaKucing.setText(Couple.getKucingNama());
                tvLocation.setText(Couple.getPenggunaDesa()+","+Couple.getPenggunaKecamatan()+","+Couple.getPenggunaKabupaten()+","+Couple.getPenggunaProvinsi());
                tvNamaPemilik.setText(Couple.getPenggunaNama());
                tvJenisKucing.setText("("+Couple.getKucingJenis()+")");
                tvHari.setText("Tersedia pada har : "+Couple.getPasanganHari());
                String gambar = Couple.getKucingFoto();
                if (gambar.equals("")) {
                    gambar = "assets/images/user.png";
                }
                Picasso.get()
                        .load(img_url + gambar)
                        .placeholder(android.R.drawable.sym_def_app_icon)
                        .error(android.R.drawable.sym_def_app_icon)
                        .into(kucing_foto);
                gambar = Couple.getPenggunaFoto();
                if (gambar.equals("")) {
                    gambar = "assets/images/user.png";
                }
                Picasso.get()
                        .load(img_url + gambar)
                        .placeholder(android.R.drawable.sym_def_app_icon)
                        .error(android.R.drawable.sym_def_app_icon)
                        .into(pemilik_foto);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
                LinearLayout lyInflater = (LinearLayout)popupView.findViewById(R.id.lyInflater);
                lyInflater.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });
                tutup = (Button) popupView.findViewById(R.id.btnTutup);
                tutup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });
                ImageView ivMap = (ImageView)popupView.findViewById(R.id.ivMap);
                ImageView ivDirection = (ImageView)popupView.findViewById(R.id.ivDirection);
                ImageView ivMessage = (ImageView)popupView.findViewById(R.id.ivMessage);
                ImageView ivSchedule = (ImageView)popupView.findViewById(R.id.ivSchedule);
                ivMap.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = null;
                        String maplLabel = "Alamat "+tvNamaKucing.getText().toString();
                        String geo = "geo:"+Couple.getPenggunaLatitude()+","+Couple.getPenggunaLongitude()+"?q="+Couple.getPenggunaLatitude()+","+Couple.getPenggunaLongitude()+"("+maplLabel+")"+"?z=17";
                        i = new Intent(Intent.ACTION_VIEW, Uri.parse(geo));
                        i.setPackage("com.google.android.apps.maps");
                        context.startActivity(i);
                    }
                });
                ivDirection.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = null;
                        String geo = "google.navigation:q="+Couple.getPenggunaLatitude()+","+Couple.getPenggunaLongitude();
                        i = new Intent(Intent.ACTION_VIEW, Uri.parse(geo));
                        context.startActivity(i);
                    }
                });
                ivMessage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(context,MessageActivity.class);
                        i.putExtra("jenis","0");
                        i.putExtra("nama",Couple.getPenggunaNama());
                        i.putExtra("foto",Couple.getPenggunaFoto());
                        i.putExtra("pengguna_id",Couple.getPenggunaId());
                        i.putExtra("kontak_id","");
                        context.startActivity(i);
                    }
                });
                ivSchedule.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(context,AddScheduleActivity.class);
                        i.putExtra("pengguna_id",Couple.getPenggunaId());
                        i.putExtra("jk",Couple.getKucingJk());
                        i.putExtra("hari",Couple.getPasanganHari());
                        i.putExtra("kucing",Couple.getKucingNama());
                        i.putExtra("kucing_id",Couple.getKucingId());
                        context.startActivity(i);
                    }
                });
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
            ArrayList<Couple> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(rvDataList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Couple item : rvDataList) {
                    if (item.getKucingNama().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    } else if (item.getKucingJenis().toLowerCase().contains(filterPattern)) {
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

