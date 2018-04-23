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
import com.makan.app.activity.PropertyListActivity;
import com.makan.app.activity.SubCategoryActivity;
import com.makan.app.util.Utility;
import com.makan.app.web.pojo.GetCategoryResponse;

import java.util.List;

public class SubCategoryAdapter extends BaseAdapter {

    private Context mContext;

    private List<GetCategoryResponse.SubCategoryList> categories;

    public SubCategoryAdapter(Context c,List<GetCategoryResponse.SubCategoryList> categories) {
        this.mContext = c;
        this.categories=categories;
    }

    public int getCount() {
        return categories.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(final int position, View convertView, ViewGroup parent) {

        final GetCategoryResponse.SubCategoryList subCategory=categories.get(position);

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
                    bundle.putString("title",subCategory.getSubCategoryName());
                    bundle.putString("subcategory_id",subCategory.getSubCategoryId());
                    new Utility().moveToActivity(mContext,PropertyListActivity.class,bundle);
                }
            });

            title.setText(subCategory.getSubCategoryName());

            int itemCount = Integer.parseInt(subCategory.getSubCategoryPropertyCount());

            count.setText(String.valueOf(itemCount)+(itemCount==0||itemCount==1?" ITEM ":" ITEMS"));

        } else {
            grid = (View) convertView;
        }

        return grid;
    }

}