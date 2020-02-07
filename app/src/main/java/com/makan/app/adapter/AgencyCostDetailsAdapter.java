package com.makan.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.makan.R;
import com.makan.app.web.pojo.AgencyPackageResponse;
import com.makan.app.web.pojo.ProffesionalPackageResponse;

import java.util.List;

public class AgencyCostDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<AgencyPackageResponse.CostDetail> costDetails;
    private int checkedPosition = -1;


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_title;

        public ViewHolder(View view) {
            super(view);
            tv_title = (TextView)view.findViewById(R.id.tv_title);
        }
    }


    public AgencyCostDetailsAdapter(Context mContext, List<AgencyPackageResponse.CostDetail> costDetails) {
        this.mContext = mContext;
        this.costDetails = costDetails;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowitem_costdetails, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        final AgencyPackageResponse.CostDetail property=costDetails.get(position);

        final AgencyCostDetailsAdapter.ViewHolder viewHolder = (AgencyCostDetailsAdapter.ViewHolder) holder;
        final int positions = holder.getAdapterPosition();
        if (checkedPosition == -1) {
            viewHolder.tv_title.setTextColor(mContext.getResources().getColor(R.color.gold));
        } else {
            if (checkedPosition == positions) {
                viewHolder.tv_title.setTextColor(mContext.getResources().getColor(R.color.black));
            } else {
                viewHolder.tv_title.setTextColor(mContext.getResources().getColor(R.color.gold));
            }
        }

        viewHolder.tv_title.setText(property.getAmount() + " " + "OMR / "+property.getDuration()+" "+property.getDurationType());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyDataSetChanged();
                notifyItemChanged(positions);
                viewHolder.tv_title.setTextColor(mContext.getResources().getColor(R.color.black));

                if (checkedPosition != positions) {
                    notifyItemChanged(checkedPosition);
                    checkedPosition = positions;
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return costDetails.size();
    }

    public AgencyPackageResponse.CostDetail getSelected() {
        if (checkedPosition != -1) {
            return costDetails.get(checkedPosition);
        }
        return null;
    }



}