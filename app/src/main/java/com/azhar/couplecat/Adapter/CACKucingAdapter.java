package com.azhar.couplecat.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.azhar.couplecat.Model.MyCat;
import com.azhar.couplecat.R;

import java.util.ArrayList;
import java.util.List;

public class CACKucingAdapter extends ArrayAdapter<MyCat> {
    private LayoutInflater layoutInflater;
    List<MyCat> mMyCats;


    private Filter mFilter = new Filter() {
        @Override
        public String convertResultToString(Object resultValue) {
            return ((MyCat) resultValue).getKucingNama();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null) {
                ArrayList<MyCat> suggestions = new ArrayList<MyCat>();
                for (MyCat MyCat : mMyCats) {
                    // Note: change the "contains" to "startsWith" if you only want starting matches
                    if (MyCat.getKucingNama().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        suggestions.add(MyCat);
                    }
                }

                results.values = suggestions;
                results.count = suggestions.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            if (results != null && results.count > 0) {
                // we have filtered results
                addAll((ArrayList<MyCat>) results.values);
            } else {
                // no filter, add entire original list back in
                addAll(mMyCats);
            }
            notifyDataSetChanged();
        }
    };

    public CACKucingAdapter(Context context, int textViewResourceId, List<MyCat> MyCats) {
        super(context, textViewResourceId, MyCats);
        // copy all the MyCats into a master list
        mMyCats = new ArrayList<MyCat>(MyCats.size());
        mMyCats.addAll(MyCats);
        layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = layoutInflater.inflate(R.layout.custome_auto_complete, null);
        }

        final MyCat MyCat = getItem(position);

        TextView name = (TextView) view.findViewById(R.id.kucing);
        name.setText(MyCat.getKucingNama());
        return view;
    }

    private static String getId(String id) {

        return id;
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }
}



