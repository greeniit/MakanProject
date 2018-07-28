package com.makan.app.fragment;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.makan.R;
import com.makan.app.util.Utility;

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

        Dexter.withActivity(getActivity())
                .withPermission(Manifest.permission.CALL_PHONE)
                .withListener(new PermissionListener() {
                    @Override public void onPermissionGranted(PermissionGrantedResponse response) {

                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + getString(R.string.phone_number)));
                        startActivity(intent);
                    }
                    @Override public void onPermissionDenied(PermissionDeniedResponse response) {

                        Log.e("Makan","Call permission denied");
                    }
                    @Override public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                        Log.e("Makan","Call permission denied. Rationale should be shown");
                        new Utility().showMessageAlertDialog(getActivity(),getResources().getString(R.string.permission_phone_not_granted));
                    }

                }).check();


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
