package com.makan.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.makan.R;
import com.makan.app.util.Utility;

public class ImageFragment extends BaseFragment{

    private ImageView ivFullMode;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_image_full_mode, container, false);
        initialiseComponents(rootView);
        loadImage();
        return rootView;
    }

    private void initialiseComponents(View view){

        ivFullMode=(ImageView)view.findViewById(R.id.ivFullMode);
    }

    private void loadImage(){

        new Utility().loadImage(getActivity(),getArguments().getString("url"),ivFullMode);
    }
}
