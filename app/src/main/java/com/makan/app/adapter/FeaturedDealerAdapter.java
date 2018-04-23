package com.makan.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.makan.R;
import com.makan.app.activity.DealerDetailActivity;
import com.makan.app.model.Property;
import com.makan.app.util.Utility;

import java.util.List;

public class FeaturedDealerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<Property> propertyList;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView ivDealer;

        public ViewHolder(View view) {
            super(view);
            ivDealer = (ImageView) view.findViewById(R.id.ivDealer);
        }
    }

    public FeaturedDealerAdapter(Context mContext, List<Property> propertList) {
        this.mContext = mContext;
        this.propertyList = propertList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_cell_best_deals_featured_dealer, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        ViewHolder viewHolder = (ViewHolder) holder;
        Property property = propertyList.get(position);
        Glide.with(mContext).load(property.getThumbnail()).into(viewHolder.ivDealer);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Utility().moveToActivity(mContext, DealerDetailActivity.class, null);
            }
        });

    }

    @Override
    public int getItemCount() {
        return propertyList.size();
    }

}
