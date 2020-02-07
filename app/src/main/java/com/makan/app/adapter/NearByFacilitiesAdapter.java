package com.makan.app.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.makan.R;
import com.makan.app.app.WebConstant;
import com.makan.app.draweritems.DrawerModelClass;
import com.makan.app.model.Property;
import com.makan.app.web.pojo.ProfesionalServiceRespose;
import com.makan.app.web.pojo.PropertyDetailResponse;

import java.util.List;

public class NearByFacilitiesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    private List<PropertyDetailResponse.TotalAmenity> totalAmenities;
    private int image;

    public NearByFacilitiesAdapter(Context mContext, List<PropertyDetailResponse.TotalAmenity> totalAmenities) {
        this.mContext = mContext;
        this.totalAmenities = totalAmenities;
    }

    public void clear() {
        totalAmenities.clear();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvPName;
        public ImageView ivPImage;
        public CardView cvPImageHolder;



        public ViewHolder(View view) {
            super(view);
            tvPName = (TextView) view.findViewById(R.id.ivProfessionalText);
            ivPImage = (ImageView) view.findViewById(R.id.ivProfessionalImage);
            cvPImageHolder = (CardView) view.findViewById(R.id.cv_ImageHolder);


        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowitem_nearbyfacilities, parent, false);
        return new NearByFacilitiesAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ViewHolder viewHolder = (ViewHolder) holder;
        PropertyDetailResponse.TotalAmenity totalAmenity = totalAmenities.get(position);
        viewHolder.tvPName.setText(totalAmenity.getNearByName());

        if (totalAmenity.getAmimg().equals("School")){

            viewHolder.ivPImage.setImageResource(R.drawable.schoolnear);

        }else if (totalAmenity.getAmimg().equals("College")){

            viewHolder.ivPImage.setImageResource(R.drawable.graduationnear);

        }else if (totalAmenity.getAmimg().equals("Hospital")){

            viewHolder.ivPImage.setImageResource(R.drawable.hospital);

        }else if (totalAmenity.getAmimg().equals("Airport")){

            viewHolder.ivPImage.setImageResource(R.drawable.planenear);

        }else if (totalAmenity.getAmimg().equals("Railway Station")){

            viewHolder.ivPImage.setImageResource(R.drawable.trainnear);

        }else if (totalAmenity.getAmimg().equals("Mosque")){

            viewHolder.ivPImage.setImageResource(R.drawable.mosquenear);

        }

        else {
            viewHolder.ivPImage.setImageDrawable(null);
            viewHolder.ivPImage.setImageResource(R.drawable.pictures);
        }









    }

    @Override
    public int getItemCount() {
        return totalAmenities.size();
    }

    public void addItems( List<PropertyDetailResponse.TotalAmenity> totalAmenities){

        totalAmenities.addAll(totalAmenities);
    }
}
