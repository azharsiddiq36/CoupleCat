package com.azhar.couplecat.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.azhar.couplecat.Model.Message;
import com.azhar.couplecat.Model.ResponseMessage;
import com.azhar.couplecat.R;
import com.azhar.couplecat.Utils.SessionManager;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.azhar.couplecat.Rest.CombineApi.img_url;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> implements Filterable {
   private ArrayList<Message> rvData;
    Context context;
    Dialog myDialog;
    SessionManager sessionManager;
    String waktu = null;
    private ArrayList<Message> rvDataList;
    HashMap<String,String> map;
    private List<ResponseMessage> data;

    public MessageAdapter(Context context, ArrayList<Message> inputData) {
        this.context = context;
        sessionManager = new SessionManager(context);
        map = sessionManager.getDetailsLoggin();
        rvData = inputData;

        rvDataList = new ArrayList<>(rvData);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTime, tvText;
        public RelativeLayout rlChat;
        public ImageView ivStatus;
        public ViewHolder(View v) {
            super(v);
            tvTime = (TextView) v.findViewById(R.id.tvTime);
            tvText = (TextView) v.findViewById(R.id.tvText);
            rlChat = (RelativeLayout) v.findViewById(R.id.rlChat);
            ivStatus = (ImageView) v.findViewById(R.id.ivStatus);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_message, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int pofgdion) {
        final Message Message = rvData.get(pofgdion);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String today = dateFormat.format(date);
        String time = Message.getChattingTanggal();
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
        }catch (Exception e){

        }
        holder.tvText.setText(Message.getChattingText());

        if (Message.getChattingPenggunaId().toString().equals(map.get(sessionManager.KEY_PENGGUNA_ID))){
            RelativeLayout.LayoutParams params= (RelativeLayout.LayoutParams)holder.rlChat.getLayoutParams();
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            holder.rlChat.setBackground(ContextCompat.getDrawable(context,R.drawable.bg_chat_send));
            holder.rlChat.setLayoutParams(params);
            if (Message.getChattingStatus().equals("read")){
                Picasso.get()
                        .load(R.drawable.check_read).placeholder(android.R.drawable.sym_def_app_icon)
                        .error(android.R.drawable.sym_def_app_icon)
                        .into(holder.ivStatus);
            }
            else{
                Picasso.get()
                        .load(R.drawable.check_delivered).placeholder(android.R.drawable.sym_def_app_icon)
                        .error(android.R.drawable.sym_def_app_icon)
                        .into(holder.ivStatus);
            }
        }
        else{
            RelativeLayout.LayoutParams params= (RelativeLayout.LayoutParams)holder.rlChat.getLayoutParams();
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            holder.rlChat.setBackground(ContextCompat.getDrawable(context,R.drawable.bg_chat_receiver));
            holder.rlChat.setLayoutParams(params);
            holder.ivStatus.setVisibility(View.GONE);
        }

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
            ArrayList<Message> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(rvDataList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Message item : rvDataList) {
                    if (item.getChattingText().toLowerCase().contains(filterPattern)) {
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
