package com.azhar.couplecat.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
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

import com.azhar.couplecat.Activity.DetailActivity;
import com.azhar.couplecat.Model.Information;
import com.azhar.couplecat.R;
import com.azhar.couplecat.Utils.SessionManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static com.azhar.couplecat.Rest.CombineApi.img_url;

public class TheCatApiAdapter extends RecyclerView.Adapter<TheCatApiAdapter.ViewHolder> implements Filterable {
//public class TheCatApiAdapter extends RecyclerView.Adapter<TheCatApiAdapter.ViewHolder>{
    private ArrayList<Information> rvData;
    Context context;
    Dialog myDialog;

    private ArrayList<Information> rvDataList;
    SessionManager sessionManager;
//    private List<ResponseInformation> data;
    public TheCatApiAdapter(Context context, ArrayList<Information> inputData){
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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_information, parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int pofgdion) {
        final Information Information = rvData.get(pofgdion);
        holder.tvName.setText(Information.getName());
        holder.tvDescription.setText(Information.getDescription().substring(0,Math.min(Information.getDescription().length(),115))+"...");
        holder.tvCountry.setText(Information.getOrigin());

        Picasso.get()
                .load(img_url+Information.getName().toLowerCase()+".jpg")
                .placeholder(android.R.drawable.sym_def_app_icon)
                .error(android.R.drawable.sym_def_app_icon)
                .into(holder.ivPicture);
        holder.lyContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotodescription = new Intent(context,DetailActivity.class);
                gotodescription.putExtra("origin",Information.getOrigin());
                gotodescription.putExtra("name",Information.getName());
                gotodescription.putExtra("description",Information.getDescription());
                gotodescription.putExtra("wikipedia",Information.getWikipediaUrl());
                gotodescription.putExtra("lifespan",Information.getLifeSpan());
                gotodescription.putExtra("weight",Information.getWeight().getImperial());
                context.startActivity(gotodescription);

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
            ArrayList<Information> filteredList =new ArrayList<>();
            if (constraint==null||constraint.length()==0){
                filteredList.addAll(rvDataList);
            }
            else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Information item:rvDataList ){
                    if (item.getName().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                    else if (item.getCountryCode().toLowerCase().contains(filterPattern)){
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