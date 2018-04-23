package com.makan.app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.makan.R;
import com.makan.app.adapter.GalleryAdapter;
import com.makan.app.util.ZoomOutPageTransformer;

import java.util.ArrayList;
import java.util.List;

public class GalleryActivity extends BaseActivity implements View.OnClickListener,ViewPager.OnPageChangeListener{

    private ViewPager viewPager;
    private GalleryAdapter galleryAdapter;
    private ImageView ivClose;
    private TextView tvTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        initialiseComponents();
        setViewPager();
        setListeners();
    }

    private void initialiseComponents() {

        viewPager = (ViewPager) findViewById(R.id.vpImageSlider);
        ivClose=(ImageView)findViewById(R.id.ivClose);
        tvTitle=(TextView)findViewById(R.id.tvTitle);
    }

    private void setViewPager() {

        galleryAdapter = new GalleryAdapter(getSupportFragmentManager(), getImageList());
        viewPager.setAdapter(galleryAdapter);
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        tvTitle.setText(getString(R.string.image_count_message).replace("#","1").replace("$", String.valueOf(getImageList().size())));
    }

    private void setListeners(){
        ivClose.setOnClickListener(this);
        viewPager.addOnPageChangeListener(this);
    }

    private List<String> getImageList() {

       /* List<String> imageUrls = new ArrayList<>();
        imageUrls.add("https://static.pexels.com/photos/186077/pexels-photo-186077.jpeg");
        imageUrls.add("https://i.ytimg.com/vi/Xx6t0gmQ_Tw/maxresdefault.jpg");
        imageUrls.add("http://www.pampangahouses.com/wp-content/uploads/2012/08/leighton-front-view.jpg");
*/

        if(getIntent().getExtras()!=null){

            return getIntent().getExtras().getStringArrayList("images");
        }else{
            return null;
        }


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.ivClose:
                finish();
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


    }

    @Override
    public void onPageSelected(int position) {

        tvTitle.setText(getString(R.string.image_count_message).replace("#",String.valueOf(position+1)).replace("$", String.valueOf(getImageList().size())));

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
