package com.makan.app.adapter;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.makan.R;
import com.makan.app.activity.SubCategoryActivity;
import com.makan.app.util.Utility;
import com.makan.app.web.pojo.GetCategoryResponse;

import java.util.List;

public class CategoryAdapter extends BaseAdapter {

    private Context mContext;

    private List<GetCategoryResponse.MainCategory> categories;

    public CategoryAdapter(Context c,List<GetCategoryResponse.MainCategory> categories) {
        this.mContext = c;
        this.categories=categories;
    }

    public int getCount() {
        return categories.size();
    }

    public Object getItem(int position) {
        return categories.get(position);
    }

    public long getItemId(int position) {
        return categories.get(0).hashCode();
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(final int position, View convertView, ViewGroup parent) {

        GetCategoryResponse.MainCategory mainCategory=categories.get(position);

        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            grid = new View(mContext);

            grid = inflater.inflate(R.layout.custom_cell_category, null);

            TextView title = (TextView) grid.findViewById(R.id.tvCategory);
            TextView count = (TextView)grid.findViewById(R.id.tvCategoryCount);

            LinearLayout llCategoryMainHolder=(LinearLayout)grid.findViewById(R.id.llCategoryMainHolder);

            llCategoryMainHolder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Bundle bundle=new Bundle();
                    bundle.putParcelable("selected_category",categories.get(position));

                    new Utility().moveToActivity(mContext,SubCategoryActivity.class,bundle);
                }
            });

            title.setText(mainCategory.getMainCategoryName());

            int itemCount = Integer.parseInt(mainCategory.getSubCategoryCount());


            count.setText(String.valueOf(itemCount)+(itemCount==0||itemCount==1? mContext.getResources().getString(R.string.item):mContext.getResources().getString(R.string.items)));

        } else {
            grid = (View) convertView;
        }

        return grid;
    }

}