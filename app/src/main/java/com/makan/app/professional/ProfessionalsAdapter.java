package com.makan.app.professional;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
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
import com.makan.app.web.pojo.ProfesionalServiceRespose;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ProfessionalsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ClickListener clickListener;
    private ArrayList<ProfesionalServiceRespose.Service> profesionalServiceList;

    public ProfessionalsAdapter(Context mContext,ClickListener clickListener, ArrayList<ProfesionalServiceRespose.Service> profesionalServiceList) {
        this.mContext = mContext;
        this.profesionalServiceList = profesionalServiceList;
        this.clickListener = clickListener;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvPName,tvCat;
        public ImageView ivPImage;
        public CardView cvPImageHolder;

        public ViewHolder(View view) {
            super(view);
            tvPName = (TextView) view.findViewById(R.id.ivProfessionalText);
            ivPImage = (ImageView) view.findViewById(R.id.ivProfessionalImage);
            cvPImageHolder = (CardView) view.findViewById(R.id.cv_ImageHolder);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (clickListener!=null){
                        ProfesionalServiceRespose.Service profesionalService = profesionalServiceList.get(getAdapterPosition());
                        clickListener.serviceItemClick(profesionalService.getServiceId());
                    }
                }
            });

        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowitem_profesionalsearch, parent, false);
        return new ProfessionalsAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ProfessionalsAdapter.ViewHolder viewHolder = (ProfessionalsAdapter.ViewHolder) holder;
        ProfesionalServiceRespose.Service profesionalService = profesionalServiceList.get(position);
        viewHolder.tvPName.setText(profesionalService.getServiceName());

        Glide.with(mContext)
                .load(WebConstant.BASE_IMAGE_URL + profesionalServiceList.get(position).getServiceIcon())
                .into(viewHolder.ivPImage);


        viewHolder.cvPImageHolder.setCardBackgroundColor(getRandomColorCode());


    }

    @Override
    public int getItemCount() {
        return profesionalServiceList.size();
    }

    public void addItems(List<ProfesionalServiceRespose.Service> serviceList){

        profesionalServiceList.addAll(serviceList);
    }

    public int getRandomColorCode(){

        Random random = new Random();

        return Color.argb(255, random.nextInt(256), random.nextInt(256),     random.nextInt(256));

    }
}
