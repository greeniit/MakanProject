package com.makan.app.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.makan.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContactUsFragment extends BaseFragment{

    @BindView(R.id.cvPhone)
    CardView cvPhone;

    @BindView(R.id.cvEmail)
    CardView cvEmail;

    @BindView(R.id.cvWebSite)
    CardView cvWebsite;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_contact_us, container, false);
        ButterKnife.bind(this,rootView);
        return rootView;
    }

    @OnClick(R.id.cvPhone)
    void onPhoneOptionClicked(){

        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + getString(R.string.phone_number)));
        startActivity(intent);
    }

    @OnClick(R.id.cvEmail)
    void onEmailOptionClicked(){

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto",getString(R.string.email), null));
        startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }

    @OnClick(R.id.cvWebSite)
    void onWebSiteOptionClicked(){

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.makanrealty.com/"));
        startActivity(browserIntent);
    }
}
