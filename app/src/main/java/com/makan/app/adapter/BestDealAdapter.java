package com.makan.app.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makan.R;
import com.makan.app.activity.PropertyDetailActivity;
import com.makan.app.app.WebConstant;
import com.makan.app.model.Property;
import com.makan.app.util.Utility;

import java.util.List;

public class BestDealAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<Property> propertyList;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvTitle,tvAddress,tvPrevPrice,tvPrice,tvOfferPercentage;
        public ImageView ivProperty;

        public ViewHolder(View view) {
            super(view);
            ivProperty = (ImageView) view.findViewById(R.id.ivProperty);
            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            tvAddress = (TextView) view.findViewById(R.id.tvAddress);
            tvPrevPrice  = (TextView) view.findViewById(R.id.tvPrevPrice);
            tvPrice= (TextView) view.findViewById(R.id.tvPrice);
            tvOfferPercentage= (TextView) view.findViewById(R.id.tvOfferPercentage);
        }
    }

    public BestDealAdapter(Context mContext, List<Property> propertList) {
        this.mContext = mContext;
        this.propertyList = propertList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_cell_best_deal_offer, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        final Property property=propertyList.get(position);

        ViewHolder viewHolder = (ViewHolder) holder;

        viewHolder.tvTitle.setText(property.getTitle());
        viewHolder.tvAddress.setText(property.getAddress());
        viewHolder.tvPrevPrice.setText(property.getPrice());
        viewHolder.tvPrice.setText(property.getOfferPrice());
        viewHolder.tvOfferPercentage.setText(property.getOfferPercentage()+"%");

        Glide.with(mContext).load(WebConstant.BASE_IMAGE_URL+property.getImage()).into(viewHolder.ivProperty);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putInt("property_id",property.getId());
                new Utility().moveToActivity(mContext, PropertyDetailActivity.class, bundle);
            }
        });

    }

    @Override
    public int getItemCount() {
        return propertyList.size();
    }

}
