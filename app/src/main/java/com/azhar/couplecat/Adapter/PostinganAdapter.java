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
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.azhar.couplecat.Activity.KomentarActivity;
import com.azhar.couplecat.Activity.PostinganActivity;
import com.azhar.couplecat.Model.Postingan;
import com.azhar.couplecat.Model.ResponseKomentar;
import com.azhar.couplecat.Model.ResponsePostingan;
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

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static com.azhar.couplecat.Rest.CombineApi.img_url;

public class PostinganAdapter extends RecyclerView.Adapter<PostinganAdapter.ViewHolder> implements Filterable {
    private ArrayList<Postingan> rvData;
    Context context;
    HashMap<String, String> map;
    Dialog myDialog;
    SessionManager sessionManager;
    String komentar;
    CoupleCatInterface coupleCatInterface;
    String waktu = null;
    private ArrayList<Postingan> rvDataList;
    public PostinganAdapter(Context context, ArrayList<Postingan> inputData){
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
        public Button btnKomentar;
        public ImageView ivPicture,ivReport;
        public ViewHolder(View v){
            super(v);
            ivReport = (ImageView)v.findViewById(R.id.ivReport);
            btnKomentar = (Button)v.findViewById(R.id.btnKomentar);
            tvName = (TextView) v.findViewById(R.id.tvNama);
            ivPicture = (ImageView)v.findViewById(R.id.ivFoto);
            tvDescription = (TextView) v.findViewById(R.id.tvPostingan);
            lyContainer=(LinearLayout) v.findViewById(R.id.lyContainer);
            tvTime = (TextView)v.findViewById(R.id.tvTime);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_postingan, parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int pofgdion) {
        final Postingan Postingan = rvData.get(pofgdion);
        holder.tvName.setText(Postingan.getPenggunaNama());
        holder.tvDescription.setText(Postingan.getPostinganDeskripsi());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String today = dateFormat.format(date);
        String time = Postingan.getPostinganTanggal();
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
                .load(img_url+Postingan.getPenggunaFoto())
                .placeholder(android.R.drawable.sym_def_app_icon)
                .error(android.R.drawable.sym_def_app_icon)
                .into(holder.ivPicture);

        Call<ResponseKomentar> responseKomentarCall = coupleCatInterface.getKomentar(Postingan.getPostinganId());
        responseKomentarCall.enqueue(new Callback<ResponseKomentar>() {
            @Override
            public void onResponse(Call<ResponseKomentar> call, Response<ResponseKomentar> response) {
                if (response.body().getStatus() == 200){

                    holder.btnKomentar.setText(""+response.body().getTotal()+" Komentar");
                }
                else{
                    holder.btnKomentar.setText("0 Komentar");
                }
            }

            @Override
            public void onFailure(Call<ResponseKomentar> call, Throwable t) {
                holder.btnKomentar.setText("0 Komentar");
            }
        });
        holder.ivReport.setOnClickListener(new View.OnClickListener() {
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
                final View popupView = inflater.inflate(R.layout.detail_report, null);
                final PopupWindow popupWindow = new PopupWindow(popupView);
                popupWindow.setWidth(width);
                popupWindow.setAnimationStyle(android.R.style.Animation_Translucent);
                popupWindow.setHeight(height-200);
                popupWindow.setFocusable(true);
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
                ImageView close = (ImageView)popupView.findViewById(R.id.btnClose);
                Button btnSubmit = (Button) popupView.findViewById(R.id.btnSubmit);
                final EditText etDescription = (EditText) popupView.findViewById(R.id.etDeskripsi);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                btnSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Call<ResponsePostingan> responsePostinganCall = coupleCatInterface.addReport(
                                map.get(sessionManager.KEY_PENGGUNA_ID),etDescription.getText().toString(),Postingan.getPostinganId().toString());
                        responsePostinganCall.enqueue(new Callback<ResponsePostingan>() {
                            @Override
                            public void onResponse(Call<ResponsePostingan> call, Response<ResponsePostingan> response) {
                                if (response.body().getStatus()==200){
                                    Toast.makeText(context, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    popupWindow.dismiss();
                                }
                                else{
                                    Toast.makeText(context, "Gagal", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponsePostingan> call, Throwable t) {
                                Toast.makeText(context, ""+t.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });
        holder.lyContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context,KomentarActivity.class);
                i.putExtra("postingan_id",Postingan.getPostinganId());
                i.putExtra("postingan_nama",Postingan.getPenggunaNama());
                i.putExtra("postingan_foto",Postingan.getPenggunaFoto());
                i.putExtra("postingan_deskripsi",Postingan.getPostinganDeskripsi());
                i.putExtra("postingan_waktu",waktu);
                i.putExtra("total_koment",holder.btnKomentar.getText().toString());
                context.startActivity(i);
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
            ArrayList<Postingan> filteredList =new ArrayList<>();
            if (constraint==null||constraint.length()==0){
                filteredList.addAll(rvDataList);
            }
            else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Postingan item:rvDataList ){
                    if (item.getPenggunaNama().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                    else if (item.getPostinganTanggal().toLowerCase().contains(filterPattern)){
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