package com.makan.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makan.R;
import com.makan.app.activity.DealerDetailActivity;
import com.makan.app.app.WebConstant;
import com.makan.app.model.Property;
import com.makan.app.util.Utility;
import com.makan.app.web.pojo.AdvertisementResponse;

import java.util.List;


public class AdvertisementAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<AdvertisementResponse.Advertisement> advertisements;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView ivAdvertisement;
        public TextView tvDealerWebsite;

        public ViewHolder(View view) {
            super(view);
            ivAdvertisement = (ImageView) view.findViewById(R.id.ivDealer);
            tvDealerWebsite=(TextView)view.findViewById(R.id.tvDealerWebsite);
        }
    }

    public AdvertisementAdapter(Context mContext, List<AdvertisementResponse.Advertisement> advertisements) {
        this.mContext = mContext;
        this.advertisements = advertisements;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_cell_best_deals_featured_dealer, parent, false);
        return new AdvertisementAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        AdvertisementAdapter.ViewHolder viewHolder = (AdvertisementAdapter.ViewHolder) holder;
        AdvertisementResponse.Advertisement advertisement = advertisements.get(position);
        viewHolder.tvDealerWebsite.setText(advertisement.getAddsWebsite());
        Glide.with(mContext).load(WebConstant.BASE_IMAGE_URL+advertisement.getAddsPhoto()).into(viewHolder.ivAdvertisement);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return advertisements.size();
    }
}
