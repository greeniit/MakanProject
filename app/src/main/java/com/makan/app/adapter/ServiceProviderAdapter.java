package com.makan.app.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makan.R;
import com.makan.app.app.WebConstant;
import com.makan.app.professional.ClickListener;
import com.makan.app.web.pojo.ProfesionalServiceRespose;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ServiceProviderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<ProfesionalServiceRespose.Service> profesionalServiceList;

    public ServiceProviderAdapter(Context mContext, ArrayList<ProfesionalServiceRespose.Service> profesionalServiceList) {
        this.mContext = mContext;
        this.profesionalServiceList = profesionalServiceList;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvPName;
        public ImageView ivPImage;
        public CardView cvPImageHolder;

        public ViewHolder(final View view) {
            super(view);
            tvPName = (TextView) view.findViewById(R.id.ivProfessionalText);
            ivPImage = (ImageView) view.findViewById(R.id.ivProfessionalImage);
            cvPImageHolder = (CardView) view.findViewById(R.id.cv_ImageHolder);


        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowitem_professionalsearchresult, parent, false);
        return new ServiceProviderAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {

        ServiceProviderAdapter.ViewHolder viewHolder = (ServiceProviderAdapter.ViewHolder) holder;
        final ProfesionalServiceRespose.Service profesionalService = profesionalServiceList.get(position);
        viewHolder.tvPName.setText(profesionalService.getServiceName());

        Glide.with(mContext)
                .load(WebConstant.BASE_IMAGE_URL + profesionalServiceList.get(position).getServiceIcon())
                .into(viewHolder.ivPImage);


        viewHolder.cvPImageHolder.setCardBackgroundColor(getRandomColorCode());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                highlightView(holder);
            }
        });



    }

    @Override
    public int getItemCount() {
        return profesionalServiceList.size();
    }

    public void addItems(List<ProfesionalServiceRespose.Service> serviceList){
        profesionalServiceList.addAll(serviceList); }

    public int getRandomColorCode(){
        Random random = new Random();
        return Color.argb(255, random.nextInt(256), random.nextInt(256),     random.nextInt(256));
    }

    private void highlightView(RecyclerView.ViewHolder holder) {
        holder.itemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorAccent));
    }

    private void unhighlightView(RecyclerView.ViewHolder holder) {
        holder.itemView.setBackgroundColor(ContextCompat.getColor(mContext, android.R.color.transparent));
    }
}
