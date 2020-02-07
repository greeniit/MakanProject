package com.makan.app.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.makan.R;
import com.makan.app.callback.FilterCategoryCallback;
import com.makan.app.professional.ProfessionalsAdapter;
import com.makan.app.web.pojo.FillterCategoryResponse;
import com.makan.app.web.pojo.GetCategoryResponse;
import com.makan.app.web.pojo.NewsResponse;
import com.makan.app.web.pojo.ProfesionalServiceRespose;

import java.util.List;

public class FilterCategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<FillterCategoryResponse.MainCategory> mainCategories;
    private FilterCategoryCallback filterCategoryCallback;

    public FilterCategoryAdapter(Context context,List<FillterCategoryResponse.MainCategory> mainCategories,FilterCategoryCallback filterCategoryCallback) {
        this.mContext = context;
        this.mainCategories=mainCategories;
        this.filterCategoryCallback = filterCategoryCallback;
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowitem_filter_propertytype, parent, false);
        return new FilterCategoryAdapter.ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        FilterCategoryAdapter.ViewHolder viewHolder = (FilterCategoryAdapter.ViewHolder) holder;
        final FillterCategoryResponse.MainCategory profesionalService = mainCategories.get(position);
        viewHolder.tvCat.setText(profesionalService.getMainCategoryName());
        viewHolder.tvCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterCategoryCallback.filterCategorySelect(profesionalService.getSubCategoryList());

            }
        });

    }

    @Override
    public int getItemCount() {
        return mainCategories.size();
    }

    public void addItems(List<FillterCategoryResponse.MainCategory>mainCategories){

        mainCategories.addAll(mainCategories);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvCat;
        public ViewHolder(View itemView) {
            super(itemView);
            tvCat = (TextView)itemView.findViewById(R.id.tvCategory);
        }
    }
}
