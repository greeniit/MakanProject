package com.makan.app.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makan.R;
import com.makan.app.activity.PropertyDetailActivity;
import com.makan.app.app.WebConstant;
import com.makan.app.callback.PropertyAdapterWishListOperationCallback;
import com.makan.app.model.Property;
import com.makan.app.util.Utility;

import java.util.ArrayList;
import java.util.List;

public class PropertyListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int STYLE_VERTICAL_LIST_CELL=100;
    public static final int STYLE_HORIZONTAL_LIST_CELL=101;


    private Activity activity;
    private List<Property> propertyList;
    private PropertyAdapterWishListOperationCallback propertyAdapterWishListOperationCallback;
    private int mStyle;

    public class HorizontalCellViewHolder extends RecyclerView.ViewHolder {

        public TextView tvTitle, tvAddress,tvBedCount,tvArea,tvPrice,tvDescription;
        public ImageView ivThumbnail,ivWishList;
        private CardView cardView;

        public HorizontalCellViewHolder(View view) {
            super(view);
            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            tvAddress = (TextView) view.findViewById(R.id.tvAddress);
            tvBedCount = (TextView) view.findViewById(R.id.tvBed);
            tvArea = (TextView) view.findViewById(R.id.tvArea);
            tvPrice = (TextView) view.findViewById(R.id.tvPrice);
            tvDescription=(TextView) view.findViewById(R.id.tvDescription);
            ivThumbnail = (ImageView) view.findViewById(R.id.ivThumbnail);
            ivWishList=(ImageView)view.findViewById(R.id.ivWishList);
            cardView=(CardView)view.findViewById(R.id.card_view);
        }
    }

    public class VerticalCellViewHolder extends RecyclerView.ViewHolder {

        public TextView tvTitle, tvAddress,tvBedCount,tvArea,tvPrice,tvDescription;
        public ImageView ivThumbnail,ivWishList;

        public VerticalCellViewHolder(View view) {
            super(view);
            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            tvAddress = (TextView) view.findViewById(R.id.tvAddress);
            tvBedCount = (TextView) view.findViewById(R.id.tvBed);
            tvArea = (TextView) view.findViewById(R.id.tvArea);
            tvPrice = (TextView) view.findViewById(R.id.tvPrice);
            tvDescription=(TextView) view.findViewById(R.id.tvDescription);
            ivThumbnail = (ImageView) view.findViewById(R.id.ivThumbnail);
            ivWishList=(ImageView)view.findViewById(R.id.ivWishList);
        }
    }


    public PropertyListAdapter(Activity activity, List<Property> propertList,int style,PropertyAdapterWishListOperationCallback propertyAdapterWishListOperationCallback) {
        this.activity = activity;
        this.propertyList = propertList;
        this.mStyle=style;
        this.propertyAdapterWishListOperationCallback = propertyAdapterWishListOperationCallback;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView;

        switch (viewType){

            case STYLE_VERTICAL_LIST_CELL:

                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.custom_cell_property, parent, false);

                return new VerticalCellViewHolder(itemView);

            case STYLE_HORIZONTAL_LIST_CELL:

                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.custom_cell_home, parent, false);

                return new HorizontalCellViewHolder(itemView);
        }

        return null;

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        final Property property;

        switch (holder.getItemViewType()) {

            case STYLE_HORIZONTAL_LIST_CELL:
                HorizontalCellViewHolder horizontalCellViewHolder=(HorizontalCellViewHolder)holder;
                property = propertyList.get(position);

                horizontalCellViewHolder.tvTitle.setText(property.getTitle());
                horizontalCellViewHolder.tvPrice.setText(" "+property.getPrice()+" OMR");
                horizontalCellViewHolder.tvAddress.setText(property.getAddress());
                horizontalCellViewHolder.tvArea.setText(property.getArea()+" Sqft");
                horizontalCellViewHolder.tvBedCount.setText(property.getBedCount()+" Bedrooms");

                if(propertyList!=null&&propertyList.size()==1){

                    DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
                    int height = displayMetrics.heightPixels;
                    int width = displayMetrics.widthPixels;

                    horizontalCellViewHolder.cardView.setLayoutParams(new LinearLayout.LayoutParams((int)width, LinearLayout.LayoutParams.MATCH_PARENT));
                }

                Glide.with(activity).load(WebConstant.BASE_IMAGE_URL+property.getImage()).into(horizontalCellViewHolder.ivThumbnail);

                horizontalCellViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle=new Bundle();
                        bundle.putInt("property_id",property.getId());
                        new Utility().moveToActivityForResult(activity, PropertyDetailActivity.class,bundle);
                    }
                });

                break;

            case STYLE_VERTICAL_LIST_CELL:

                VerticalCellViewHolder verticalCellViewHolder=(VerticalCellViewHolder)holder;
                property = propertyList.get(position);

                verticalCellViewHolder.tvTitle.setText(property.getTitle());
                verticalCellViewHolder.tvPrice.setText(" "+property.getPrice()+" OMR");
                verticalCellViewHolder.tvAddress.setText(property.getAddress());
                verticalCellViewHolder.tvArea.setText(property.getArea()+" Sqft");
                //verticalCellViewHolder.tvDescription.setText(property.getDescription());

                if(property.getFavourite().equalsIgnoreCase("1")){
                    verticalCellViewHolder.ivWishList.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_fav_filled));
                }else{
                    verticalCellViewHolder.ivWishList.setImageDrawable(activity.getResources().getDrawable(R.drawable.favourite));
                }


                Glide.with(activity).load(WebConstant.BASE_IMAGE_URL+property.getImage()).into(verticalCellViewHolder.ivThumbnail);

                verticalCellViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Bundle bundle=new Bundle();
                        bundle.putInt("property_id",property.getId());
                        new Utility().moveToActivityForResult(activity, PropertyDetailActivity.class,bundle);
                    }
                });

                verticalCellViewHolder.ivWishList.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(propertyAdapterWishListOperationCallback!=null){
                            propertyAdapterWishListOperationCallback.onWishListClicked(property,position);
                        }

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

    public void addItems(List<Property> properties){

        propertyList=properties;
    }

    public void updateItem(Property property,int pos){

        propertyList.set(pos,property);
        notifyDataSetChanged();
    }

    public ArrayList<Property> getAddedItems(){

        return (ArrayList<Property>) propertyList;
    }
}