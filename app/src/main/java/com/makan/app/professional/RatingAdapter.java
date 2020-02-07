package com.makan.app.professional;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.github.ornolfr.ratingview.RatingView;
import com.makan.R;
import com.makan.app.web.pojo.ProfessionalDetailsResponse;
import com.makan.app.web.pojo.ProfessionalSerachResponse;

import java.util.ArrayList;

public class RatingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private Context mContext;
    private ArrayList<ProfessionalDetailsResponse.Rating> ratings;

    public RatingAdapter(Context mContext, ArrayList<ProfessionalDetailsResponse.Rating> ratings) {
        this.mContext = mContext;
        this.ratings = ratings;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private RatingView rbRatingBar;
        private TextView tvName;
        private TextView tvDescription;




        public ViewHolder(final View view) {
            super(view);

            rbRatingBar = (RatingView) view.findViewById(R.id.rbRatingBar);
            rbRatingBar.setEnabled(false);
            tvName = (TextView) view.findViewById(R.id.tvName);
            tvDescription = (TextView) view.findViewById(R.id.tvDescription);

//            view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (clickListener != null) {
//                        ProfessionalSerachResponse.Professional profesionalService = profesionalServiceList.get(getAdapterPosition());
//                        clickListener.serviceResultItemClick(profesionalService.getAddId());
//                    }
//                }
//            });


        }
        }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowitm_rattingbar, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        RatingAdapter.ViewHolder viewHolder = (RatingAdapter.ViewHolder) holder;
        ProfessionalDetailsResponse.Rating rating = ratings.get(position);
        viewHolder.tvName.setText(rating.getDisplayName());
        viewHolder.tvDescription.setText(rating.getComment());
        viewHolder.rbRatingBar.setRating(Integer.parseInt(rating.getRate()));

    }

    @Override
    public int getItemCount() {
        return ratings.size();
    }
}
