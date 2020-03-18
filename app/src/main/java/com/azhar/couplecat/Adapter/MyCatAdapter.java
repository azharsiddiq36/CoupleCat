package com.azhar.couplecat.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.media.Image;
import android.support.v7.widget.CardView;
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

import com.azhar.couplecat.Model.MyCat;
import com.azhar.couplecat.Model.ResponseMyCat;
import com.azhar.couplecat.R;
import com.azhar.couplecat.Utils.SessionManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static com.azhar.couplecat.Rest.CombineApi.img_url;

public class MyCatAdapter extends RecyclerView.Adapter<MyCatAdapter.ViewHolder> implements Filterable {
    //public class MyCatAdapter extends RecyclerView.Adapter<MyCatAdapter.ViewHolder>{
    private ArrayList<MyCat> rvData;
    Context context;
    Dialog myDialog;

    private ArrayList<MyCat> rvDataList;
    SessionManager sessionManager;
    private List<ResponseMyCat> data;

    public MyCatAdapter(Context context, ArrayList<MyCat> inputData) {
        this.context = context;

        rvData = inputData;

        rvDataList = new ArrayList<>(rvData);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvNama, tvJenis;
        public LinearLayout lyMyCat;
        public ImageView imgMyCat, imgGender;

        public ViewHolder(View v) {
            super(v);
            tvNama = (TextView) v.findViewById(R.id.tvNama);
            tvJenis = (TextView) v.findViewById(R.id.tvJenis);
            imgGender = (ImageView) v.findViewById(R.id.tvGender);
            imgMyCat = (ImageView) v.findViewById(R.id.ivFoto);
            lyMyCat = (LinearLayout) v.findViewById(R.id.lyMyCat);

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_my_cat, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int pofgdion) {
        final MyCat MyCat = rvData.get(pofgdion);
        holder.tvNama.setText(MyCat.getKucingNama());
        holder.tvJenis.setText(MyCat.getKucingJenis());
        String gambar = MyCat.getKucingFoto();
        if (gambar.equals("")) {
            gambar = "assets/images/user.png";
        }

        Picasso.get()
                .load(img_url + gambar)
                .placeholder(android.R.drawable.sym_def_app_icon)
                .error(android.R.drawable.sym_def_app_icon)
                .into(holder.imgMyCat);
        if (MyCat.getKucingJk().toLowerCase().equals("betina")) {
            Picasso.get()
                    .load(R.drawable.ic_female).placeholder(android.R.drawable.sym_def_app_icon)
                    .error(android.R.drawable.sym_def_app_icon)
                    .into(holder.imgGender);
        } else {
            Picasso.get().load(R.drawable.ic_male).placeholder(android.R.drawable.sym_def_app_icon)
                    .error(android.R.drawable.sym_def_app_icon)
                    .into(holder.imgGender);
        }
/*
        holder.lyMyCat.setOnClickListener(new View.OnClickListener() {
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
                final View popupView = inflater.inflate(R.layout.detail_MyCat, null);
                ImageView foto = (ImageView) popupView.findViewById(R.id.MyCatFoto);
                ImageView close = (ImageView) popupView.findViewById(R.id.btnClose);
                Button tutup;
                TextView tvMyCatType, tvMyCatNama, tvMyCatKeterangan;
                sessionManager = new SessionManager(context);
                tvMyCatNama = (TextView) popupView.findViewById(R.id.tvNamaMyCat);
                tvMyCatType = (TextView) popupView.findViewById(R.id.tvTypeMyCat);
                tvMyCatKeterangan = (TextView) popupView.findViewById(R.id.tvKeterangan);
                tvMyCatKeterangan.setText(MyCat.getMyCatRincian());
                HashMap<String, String> map = sessionManager.getDetailsLoggin();
                final PopupWindow popupWindow = new PopupWindow(popupView);
                popupWindow.setWidth(width);
                popupWindow.setAnimationStyle(android.R.style.Animation_Translucent);
                popupWindow.setHeight(height - 200);
                popupWindow.setFocusable(true);
                popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
                tvMyCatNama.setText(MyCat.getMyCatNama());
                tvMyCatType.setText(MyCat.getKategoriNama());
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
                LinearLayout lyInflater = (LinearLayout) popupView.findViewById(R.id.lyInflater);
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
                String gambar = "";
                if (MyCat.getMyCatFoto().equals("")) {
                    gambar = "assets/images/user.png";
                } else {
                    gambar = MyCat.getMyCatFoto();
                }

                Picasso.get()
                        .load(img_url + gambar)
                        .placeholder(android.R.drawable.sym_def_app_icon)
                        .error(android.R.drawable.sym_def_app_icon)
                        .into(foto);

            }
        });
        */
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
            ArrayList<MyCat> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(rvDataList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (MyCat item : rvDataList) {
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
