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
import com.makan.app.app.WebConstant;
import com.makan.app.util.Utility;
import com.makan.app.web.pojo.HomeResponse;
import com.makan.app.web.pojo.NewsResponse;

import java.util.List;

public class NewsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int HEADER=100;
    private static final int CELL=101;

    private Context mContext;
    private List<NewsResponse.News> newsList;

    public class CellViewHolder extends RecyclerView.ViewHolder {

        public TextView tvTitle, tvDesc,tvDay,tvMonth;
        public ImageView ivThumbnail;

        public CellViewHolder(View view) {

            super(view);
            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            tvDesc = (TextView) view.findViewById(R.id.tvDesc);
            tvDay = (TextView) view.findViewById(R.id.tvDay);
            tvMonth = (TextView) view.findViewById(R.id.tvMonth);
            ivThumbnail = (ImageView) view.findViewById(R.id.ivThumbnail);

        }
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {

        public HeaderViewHolder(View view) {
            super(view);
        }
    }


    public NewsListAdapter(Context mContext, List<NewsResponse.News> newsList) {
        this.mContext = mContext;
        this.newsList = newsList;
        this.newsList.add(0,null);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView;

        switch (viewType){

            case HEADER:

                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.custom_cell_news_header, parent, false);

                return new HeaderViewHolder(itemView);

            case CELL:

                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.custom_cell_news, parent, false);

                return new CellViewHolder(itemView);
        }

        return null;

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        NewsResponse.News news;

        switch (holder.getItemViewType()) {

            case CELL:

                CellViewHolder cellViewHolder=(CellViewHolder)holder;
                news = newsList.get(position);
                cellViewHolder.tvTitle.setText(news.getNewsTitle());
                cellViewHolder.tvDesc.setText(news.getNewsDescription());

                String date=news.getNewsDate();
                String day=date.split("-")[2];
                int month=Integer.parseInt(date.split("-")[1]);

                cellViewHolder.tvDay.setText(day);
                cellViewHolder.tvMonth.setText(Utility.getMonth(month));

                Glide.with(mContext).load(WebConstant.BASE_IMAGE_URL+news.getNewsImageName()).into(cellViewHolder.ivThumbnail);

                cellViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

                break;
        }


    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    @Override
    public int getItemViewType(int position) {

        if(position==0){
            return HEADER;
        }else{
            return CELL;
        }
    }


    public void addItems(List<NewsResponse.News> news){

        newsList.addAll(news);
    }
}