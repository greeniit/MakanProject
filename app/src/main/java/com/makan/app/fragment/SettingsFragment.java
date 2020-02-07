package com.makan.app.fragment;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.google.gson.Gson;
import com.makan.R;
import com.makan.app.app.WebConstant;
import com.makan.app.model.User;
import com.makan.app.preference.PrefKey;
import com.makan.app.preference.PreferenceManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsFragment extends BaseFragment{

    private ImageView ivProfileImage;

    @BindView(R.id.tvName)
    TextView tvName;

    @BindView(R.id.tvEmail)
    TextView tvEmail;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this,rootView);
        initialiseComponents(rootView);
        setData();
        return rootView;
    }

    private void initialiseComponents(View view){

        ivProfileImage=(ImageView)view.findViewById(R.id.ivProfile);
    }

    private void setData(){

        Gson gson=new Gson();

        String userData=new PreferenceManager().getValue(getActivity(), PrefKey.USER_DATA);

        User user=gson.fromJson(userData, User.class);


        tvName.setText(user.getName());
        tvEmail.setText(user.getEmail());


//        Glide.with(getActivity()).load(WebConstant.BASE_IMAGE_URL+user.getProfileImage()).asBitmap().centerCrop().into(new BitmapImageViewTarget(ivProfileImage) {
//            @Override
//            protected void setResource(Bitmap resource) {
//
//                if(isAdded()&&isVisible()){
//                    RoundedBitmapDrawable circularBitmapDrawable =
//                            RoundedBitmapDrawableFactory.create(getActivity().getResources(), resource);
//                    circularBitmapDrawable.setCircular(true);
//                    ivProfileImage.setImageDrawable(circularBitmapDrawable);
//                }
//
//            }
//        });

                    Glide.with(this)
                    .load(WebConstant.BASE_IMAGE_URL + user.getProfileImage())
                    .centerCrop()
                    .apply(RequestOptions.circleCropTransform())
                    .placeholder(R.drawable.placeholder_error)
                    .error(R.drawable.noimage)
                    .into(ivProfileImage);

//        Glide.with(getContext())
//                .load(WebConstant.BASE_IMAGE_URL+user.getProfileImage())
//                .apply(RequestOptions.circleCropTransform())
//                .error(R.drawable.noimage)
//                .into(ivProfileImage);
    }

}
