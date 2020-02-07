package com.makan.app.professional;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.ornolfr.ratingview.RatingView;
import com.makan.R;
import com.makan.app.app.WebConstant;
import com.makan.app.model.Proffesional;
import com.makan.app.web.pojo.ProfesionalServiceRespose;
import com.makan.app.web.pojo.ProfessionalSerachResponse;

import java.util.ArrayList;
import java.util.List;


public class ProfessionalSearchResultAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context mContext;
    private ArrayList<Proffesional> profesionalServiceList;
    private ClickListener clickListener;

    public ProfessionalSearchResultAdapter(ClickListener clickListener,Context mContext, ArrayList<Proffesional> profesionalServiceList) {
        this.mContext = mContext;
        this.profesionalServiceList = profesionalServiceList;
        this.clickListener = clickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivThumbnail;
        private TextView tvTitle;
        private TextView tvAddress;
        private RatingView rvratingBar;



        public ViewHolder(final View view) {
            super(view);

            ivThumbnail = (ImageView)view.findViewById(R.id.ivThumbnail);
            tvTitle = (TextView)view.findViewById(R.id.tvTitle);
            tvAddress = (TextView)view.findViewById(R.id.tvAddress);
            rvratingBar = (RatingView) view.findViewById(R.id.rvratingBar);
            rvratingBar.setEnabled(false);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (clickListener!=null){
                        Proffesional profesionalService = profesionalServiceList.get(getAdapterPosition());
                        clickListener.serviceResultItemClick(profesionalService.getAddId());
                    }
                }
            });



        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowitem_professionalsearchserviceresult, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ProfessionalSearchResultAdapter.ViewHolder viewHolder = (ProfessionalSearchResultAdapter.ViewHolder) holder;
        Proffesional profesionalService = profesionalServiceList.get(position);
        viewHolder.tvTitle.setText(profesionalService.getCompanyName());
        viewHolder.tvAddress.setText(profesionalService.getAddsAddress());
        if (profesionalService.getAvgRate() == null || profesionalService.getAvgRate().equals(null)){
            viewHolder.rvratingBar.setRating(0);
        }else {
            viewHolder.rvratingBar.setRating(Float.parseFloat(profesionalService.getAvgRate()));
        }



        Glide.with(mContext)
                .load(WebConstant.BASE_IMAGE_URL + profesionalServiceList.get(position).getAddsPhoto())
                .into(viewHolder.ivThumbnail);


    }

    @Override
    public int getItemCount() {
        return profesionalServiceList.size();
    }
    public void addItems(List<Proffesional> serviceList){
        profesionalServiceList.addAll(serviceList); }

}
