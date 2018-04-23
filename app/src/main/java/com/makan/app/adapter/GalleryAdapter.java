package com.makan.app.adapter;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.makan.app.app.WebConstant;
import com.makan.app.fragment.BaseFragment;
import com.makan.app.fragment.ImageFragment;

import java.util.List;


public class GalleryAdapter extends FragmentStatePagerAdapter {

    private List<String> mImageUrls;

    public GalleryAdapter(FragmentManager fm, List<String> imageUrls) {
        super(fm);
        mImageUrls=imageUrls;
    }

    @Override
    public BaseFragment getItem(int position) {
        Bundle bundle=new Bundle();
        bundle.putString("url", WebConstant.BASE_IMAGE_URL+mImageUrls.get(position));
        ImageFragment imageFragment=new ImageFragment();
        imageFragment.setArguments(bundle);
        return (imageFragment);
    }

    @Override
    public int getCount() {
        return mImageUrls.size();
    }

}
