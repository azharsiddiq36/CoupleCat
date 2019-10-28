package com.azhar.couplecat.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.azhar.couplecat.R;


import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.Picasso;

import static com.azhar.couplecat.Rest.CombineApi.img_url;

public class SliderAdapter extends
        SliderViewAdapter<SliderAdapter.SliderAdapterVH> {
    String path[] = {"https://cdn1-production-images-kly.akamaized.net/U1TunLDOB0rf-LeFCfhgkN0Yqhw=/640x360/smart/filters:quality(75):strip_icc():format(jpeg)/kly-media-production/medias/2756583/original/074850700_1553079501-cat-3593021_1920.jpg",
                        "https://media.beritagar.id/2018-10/5c1b3b3ecc19a2ea5cf1333bb3c5b55676b9e677.jpg",
                        "https://cdn2.tstatic.net/jakarta/foto/bank/images/ilustrasi-kucing_20180618_155243.jpg",
                        "https://muslimobsession.com/wp-content/uploads/2019/09/kucing.jpg",
                        "https://img.alinea.id/img/content/2019/04/27/34945/australia-akan-musnahkan-2-juta-kucing-liar-dengan-sosis-be-BynHcEdViQ.jpg"};
    private Context context;
    private int mCount;

    public SliderAdapter(Context context) {
        this.context = context;
    }

    public void setCount(int count) {
        this.mCount = count;
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item, null);
        return new SliderAdapterVH(inflate);
    }
    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return mCount;
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {


        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "This is item in position " + position, Toast.LENGTH_SHORT).show();
            }
        });
        for (int i = 0;i<position;i++){
            Picasso.get()
                    .load(path[i])
                    .placeholder(R.drawable.bg_kucing_1)
                    .error(R.drawable.check_delivered)
                    .into(viewHolder.imageViewBackground);
        }

    }



    public class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView imageViewBackground;
        ImageView imageGifContainer;
        TextView textViewDescription;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider);
            textViewDescription = itemView.findViewById(R.id.tv_auto_image_slider);
            this.itemView = itemView;
        }
    }


}