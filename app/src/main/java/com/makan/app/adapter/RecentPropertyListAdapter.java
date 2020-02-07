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
import com.makan.app.util.Utility;
import com.makan.app.web.pojo.HomeResponse;

import java.util.List;

public class RecentPropertyListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int STYLE_HORIZONTAL_LIST_CELL=100;


    private Context mContext;
    private List<HomeResponse.RecentProperty> propertyList;
    private int mStyle;

    public class HorizontalCellViewHolder extends RecyclerView.ViewHolder {

        public TextView tvTitle, tvAddress,tvBedCount,tvArea,tvPrice,tvBathroom;
        public ImageView ivThumbnail;

        public HorizontalCellViewHolder(View view) {
            super(view);
            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            tvAddress = (TextView) view.findViewById(R.id.tvAddress);
            tvBedCount = (TextView) view.findViewById(R.id.tvBed);
            tvArea = (TextView) view.findViewById(R.id.tvArea);
            tvPrice = (TextView) view.findViewById(R.id.tvPrice);
            tvBathroom = (TextView) view.findViewById(R.id.tvBathrrom);
            ivThumbnail = (ImageView) view.findViewById(R.id.ivThumbnail);
        }
    }

    public class VerticalCellViewHolder extends RecyclerView.ViewHolder {

        public TextView tvTitle, tvAddress,tvBedCount,tvArea,tvPrice;
        public ImageView ivThumbnail;

        public VerticalCellViewHolder(View view) {
            super(view);
            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            tvAddress = (TextView) view.findViewById(R.id.tvAddress);
            tvBedCount = (TextView) view.findViewById(R.id.tvBed);
            tvArea = (TextView) view.findViewById(R.id.tvArea);
            tvPrice = (TextView) view.findViewById(R.id.tvPrice);
            ivThumbnail = (ImageView) view.findViewById(R.id.ivThumbnail);

        }
    }


    public RecentPropertyListAdapter(Context mContext, List<HomeResponse.RecentProperty> propertList, int style) {
        this.mContext = mContext;
        this.propertyList = propertList;
        this.mStyle=style;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView;

        switch (viewType){

            case STYLE_HORIZONTAL_LIST_CELL:

                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.custom_cell_home, parent, false);

                return new RecentPropertyListAdapter.HorizontalCellViewHolder(itemView);

        }

        return null;

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        final HomeResponse.RecentProperty property;

        switch (holder.getItemViewType()) {

            case STYLE_HORIZONTAL_LIST_CELL:
                RecentPropertyListAdapter.HorizontalCellViewHolder horizontalCellViewHolder=(RecentPropertyListAdapter.HorizontalCellViewHolder)holder;
                property = propertyList.get(position);
                Glide.with(mContext).load(WebConstant.BASE_IMAGE_URL+property.getImage()).error(R.drawable.dummyimage).into(horizontalCellViewHolder.ivThumbnail);

                horizontalCellViewHolder.tvTitle.setText(property.getPropertyName());
                horizontalCellViewHolder.tvAddress.setText(property.getLocation());
                horizontalCellViewHolder.tvArea.setText(String.valueOf(property.getPlotArea()));

                if (String.valueOf(property.getBed_count()).equals("null") ||String.valueOf(property.getBed_count()).equals("")){
                    horizontalCellViewHolder.tvBedCount.setText("0");
                }else {
                    horizontalCellViewHolder.tvBedCount.setText(String.valueOf(property.getBed_count()));
                }

                horizontalCellViewHolder.tvPrice.setText(String.valueOf(property.getPrice()+" OMR"));

                if (String.valueOf(property.getBathroom_count()).equals("null") ||String.valueOf(property.getBathroom_count()).equals("")){
                    horizontalCellViewHolder.tvBathroom.setText("0");
                }else {
                    horizontalCellViewHolder.tvBathroom.setText(String.valueOf(property.getBathroom_count()));
                }

                horizontalCellViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Bundle bundle=new Bundle();
                        bundle.putInt("property_id",Integer.parseInt(property.getPropertyId()));

                        new Utility().moveToActivity(mContext, PropertyDetailActivity.class,bundle);
                    }
                });

                break;

        }


    }

    @Override
    public int getItemCount() {
        return propertyList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mStyle;
    }

    public void addItems(List<HomeResponse.RecentProperty> recentProperties){
        propertyList.addAll(recentProperties);
    }
}