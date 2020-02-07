package com.makan.app.adapter;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makan.R;
import com.makan.app.app.WebConstant;
import com.makan.app.callback.FilterCategoryCallback;
import com.makan.app.web.pojo.FillterCategoryResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FilterSubCategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<FillterCategoryResponse.SubCategoryList> subCategoryLists;
    private List<String> selected;
    private FilterCategoryCallback filterCategoryCallback;

    public FilterSubCategoryAdapter(Context mContext, List<FillterCategoryResponse.SubCategoryList> subCategoryLists, FilterCategoryCallback filterCategoryCallback) {
        this.mContext = mContext;
        this.subCategoryLists = subCategoryLists;
        this.filterCategoryCallback = filterCategoryCallback;
        this.selected = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowitem_property_types, parent, false);
        return new FilterSubCategoryAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {

        FilterSubCategoryAdapter.ViewHolder viewHolder = (FilterSubCategoryAdapter.ViewHolder) holder;
        final FillterCategoryResponse.SubCategoryList  subCategoryList = subCategoryLists.get(position);
        viewHolder.SubName.setText(subCategoryList.getSubCategoryName());
        viewHolder .itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selected.contains(subCategoryList.getSubCategoryId())) {
                    selected.remove(subCategoryList.getSubCategoryId());
                    unhighlightView(holder);

                } else {
                    selected.clear();
                    selected.add(subCategoryList.getSubCategoryId());
                    highlightView(holder);
                    Log.d("wehgfkgwekfhgkfhgdfg",String.valueOf(selected.size()));
                    filterCategoryCallback.selectedData(selected);
                }
            }
        });

        if (selected.contains(subCategoryList))
            highlightView(holder);
        else

            unhighlightView(holder);
    }

    @Override
    public int getItemCount() {
        return subCategoryLists.size();
    }



    private void highlightView(RecyclerView.ViewHolder holder) {
        holder.itemView.setBackground(ContextCompat.getDrawable(mContext, R.drawable.new_home_button_background));
    }

    private void unhighlightView(RecyclerView.ViewHolder holder) {
        holder.itemView.setBackgroundColor(ContextCompat.getColor(mContext, android.R.color.transparent));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView SubName;
        private ImageView SubImage;
        public ViewHolder(View itemView) {
            super(itemView);

            SubName = (TextView)itemView.findViewById(R.id.ivProfessionalText);
            SubImage = (ImageView)itemView.findViewById(R.id.ivProfessionalImage);
        }
    }
}
