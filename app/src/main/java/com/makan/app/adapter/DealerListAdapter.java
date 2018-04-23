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
import com.makan.app.util.Utility;
import com.makan.app.web.pojo.Dealer;

import java.util.List;

public class DealerListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int HEADER=100;
    private static final int CELL=101;

    private Context mContext;
    private List<Dealer> dealerList;

    public class CellViewHolder extends RecyclerView.ViewHolder {

        public TextView tvTitle, tvAddress,tvWebsite;
        public ImageView ivThumbnail;

        public CellViewHolder(View view) {

            super(view);
            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            tvAddress = (TextView) view.findViewById(R.id.tvAddress);
            tvWebsite = (TextView) view.findViewById(R.id.tvDealerWebsite);
            ivThumbnail = (ImageView) view.findViewById(R.id.ivThumbnail);

        }
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {

        public HeaderViewHolder(View view) {
            super(view);
        }
    }


    public DealerListAdapter(Context mContext, List<Dealer> dealerList) {
        this.mContext = mContext;
        this.dealerList = dealerList;
        this.dealerList.add(0,null);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView;

        switch (viewType){

            case HEADER:

                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.custom_cell_dealer_header, parent, false);

                return new HeaderViewHolder(itemView);

            case CELL:

                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.custom_cell_dealer, parent, false);

                return new CellViewHolder(itemView);
        }

        return null;

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        Dealer dealer;

        switch (holder.getItemViewType()) {

            case CELL:

                CellViewHolder cellViewHolder=(CellViewHolder)holder;
                dealer = dealerList.get(position);
                cellViewHolder.tvTitle.setText(dealer.getAgencyName());
                cellViewHolder.tvAddress.setText(dealer.getAgencyAddress());
                cellViewHolder.tvWebsite.setText(dealer.getAgencyWebsite());

                Glide.with(mContext).load(WebConstant.BASE_IMAGE_URL+dealer.getAgencyLogo()).into(cellViewHolder.ivThumbnail);

                cellViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        new Utility().moveToActivity(mContext, DealerDetailActivity.class,null);
                    }
                });

                break;
        }


    }

    @Override
    public int getItemCount() {
        return dealerList.size();
    }

    @Override
    public int getItemViewType(int position) {

        if(position==0){
            return HEADER;
        }else{
            return CELL;
        }
    }

    public void addItems(List<Dealer> dealers){

        dealerList.addAll(dealers);

    }

}