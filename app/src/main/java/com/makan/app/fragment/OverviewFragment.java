package com.makan.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.makan.R;

public class OverviewFragment extends BaseFragment {

    private WebView wvMain;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_overview, container, false);
        initialiseComponents(rootView);
        loadWebPage();
        return rootView;
    }

    private void initialiseComponents(View rootView){

        wvMain=(WebView)rootView.findViewById(R.id.wvMain);
    }

    private void loadWebPage(){

        wvMain.setWebViewClient(new WebViewClient());
        wvMain.getSettings().setJavaScriptEnabled(true);
        wvMain.loadUrl("http://www.google.com");
    }

}