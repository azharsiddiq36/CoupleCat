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

import com.azhar.couplecat.Activity.DetailActivity;
import com.azhar.couplecat.Model.Toko;
import com.azhar.couplecat.R;
import com.azhar.couplecat.Utils.SessionManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static com.azhar.couplecat.Rest.CombineApi.img_url;

public class TokoAdapter extends RecyclerView.Adapter<TokoAdapter.ViewHolder> implements Filterable {
//public class TheCatApiAdapter extends RecyclerView.Adapter<TheCatApiAdapter.ViewHolder>{
    private ArrayList<Toko> rvData;
    Context context;
    Dialog myDialog;

    private ArrayList<Toko> rvDataList;
    SessionManager sessionManager;
//    private List<ResponseToko> data;
    public TokoAdapter(Context context, ArrayList<Toko> inputData){
//            public TheCatApiAdapter(Context context){
        this.context = context;
        rvData=inputData;
        rvDataList = new ArrayList<>(rvData);
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tvName,tvCountry,tvDescription;
        public LinearLayout lyContainer;

        public ImageView ivPicture;
        public ViewHolder(View v){
            super(v);
            tvName = (TextView) v.findViewById(R.id.tvName);
            ivPicture = (ImageView)v.findViewById(R.id.ivPicture);
            tvDescription = (TextView) v.findViewById(R.id.tvDescription);
            lyContainer=(LinearLayout) v.findViewById(R.id.lyContainer);
            tvCountry = (TextView)v.findViewById(R.id.tvCountry);

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_toko, parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int pofgdion) {
        final Toko Toko = rvData.get(pofgdion);
        holder.tvName.setText(Toko.getTokoNama());
        holder.tvDescription.setText(Toko.getTokoDeskripsi().substring(0,Math.min(Toko.getTokoDeskripsi().length(),115))+"...");
        holder.tvCountry.setText(Toko.getTokoKabupaten());

        Picasso.get()
                .load(img_url+Toko.getTokoFoto().toLowerCase())
                .placeholder(android.R.drawable.sym_def_app_icon)
                .error(android.R.drawable.sym_def_app_icon)
                .into(holder.ivPicture);
        holder.lyContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
                Display display = wm.getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);
                int width = size.x;
                int height = size.y;
                LayoutInflater inflater = (LayoutInflater)
                        context.getSystemService(LAYOUT_INFLATER_SERVICE);
                final View popupView = inflater.inflate(R.layout.detail_toko, null);
                final PopupWindow popupWindow = new PopupWindow(popupView);
                popupWindow.setWidth(width);
                popupWindow.setAnimationStyle(android.R.style.Animation_Translucent);
                popupWindow.setHeight(height-200);
                popupWindow.setFocusable(true);
                popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
                ImageView kucing_foto = (ImageView)popupView.findViewById(R.id.tokoFoto);
                ImageView close = (ImageView)popupView.findViewById(R.id.btnClose);
                Button tutup;
                final TextView tvNama,tvNomor,tvAlamat,tvDeskripsi;
                sessionManager = new SessionManager(context);
                tvNama = (TextView)popupView.findViewById(R.id.tvNama);
                tvNomor = (TextView)popupView.findViewById(R.id.tvNomor);
                tvDeskripsi = (TextView)popupView.findViewById(R.id.tvDescription);
                tvAlamat = (TextView)popupView.findViewById(R.id.tvalamat);
                tvNama.setText(Toko.getTokoNama());
                tvDeskripsi.setText(Toko.getTokoDeskripsi());
                tvNomor.setText(Toko.getTokoNomor());
                tvAlamat.setText(Toko.getTokoAlamat());
                String gambar = Toko.getTokoFoto();
                if (gambar.equals("")) {
                    gambar = "assets/images/user.png";
                }
                tutup = (Button)popupView.findViewById(R.id.btnTutup);
                tutup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });
                Picasso.get()
                        .load(img_url + gambar)
                        .placeholder(android.R.drawable.sym_def_app_icon)
                        .error(android.R.drawable.sym_def_app_icon)
                        .into(kucing_foto);
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
            ArrayList<Toko> filteredList =new ArrayList<>();
            if (constraint==null||constraint.length()==0){
                filteredList.addAll(rvDataList);
            }
            else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Toko item:rvDataList ){
                    if (item.getTokoNama().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                    else if (item.getTokoKabupaten().toLowerCase().contains(filterPattern)){
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