package com.makan.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.makan.R;
import com.makan.app.model.Notification;
import com.makan.app.web.pojo.ProffesionalPackageResponse;

import java.util.List;

public class CoastDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<ProffesionalPackageResponse.CostDetail> costDetails;
    private int checkedPosition = -1;


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_title;

        public ViewHolder(View view) {
            super(view);
            tv_title = (TextView)view.findViewById(R.id.tv_title);
        }
    }


    public CoastDetailsAdapter(Context mContext, List<ProffesionalPackageResponse.CostDetail> costDetails) {
        this.mContext = mContext;
        this.costDetails = costDetails;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowitem_costdetails, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        final ProffesionalPackageResponse.CostDetail property=costDetails.get(position);

        final CoastDetailsAdapter.ViewHolder viewHolder = (CoastDetailsAdapter.ViewHolder) holder;
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

        viewHolder.tv_title.setText(property.amount + " " + "OMR / "+property.duration+" "+property.durationType);

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


    public ProffesionalPackageResponse.CostDetail getSelected() {
        if (checkedPosition != -1) {
            return costDetails.get(checkedPosition);
        }
        return null;
    }

}

