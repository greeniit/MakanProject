package com.makan.app.professional;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makan.R;
import com.makan.app.activity.PropertyListActivity;
import com.makan.app.app.WebConstant;
import com.makan.app.util.Utility;
import com.makan.app.web.pojo.HomeResponse;
import com.makan.app.web.pojo.ProfesionalServiceRespose;

import java.util.List;

public class ProfessionalSearchViewPager extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<ProfesionalServiceRespose.GalleryImage> imagesToLoad;

    public ProfessionalSearchViewPager(Context context,List<ProfesionalServiceRespose.GalleryImage> imagesToLoad) {
        this.context = context;
        this.imagesToLoad=imagesToLoad;

    }
    @Override
    public int getCount() {
        return imagesToLoad.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.custom_cell_home_main_image, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.ivHomeMain);
        TextView tvPlaceName=(TextView)view.findViewById(R.id.tvPlaceName);
//        tvPlaceName.setText(imagesToLoad.get(position).getImageTitle());
        tvPlaceName.setVisibility(View.GONE);

        Glide.with(context).load(WebConstant.BASE_IMAGE_URL+imagesToLoad.get(position).getAddsImg()).into(imageView);

//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Bundle bundle=new Bundle();
//                bundle.putString("place_name",imagesToLoad.get(position).getCitieName());
//                new Utility().moveToActivity(context, PropertyListActivity.class, bundle);
//            }
//        });


        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);

    }
}
