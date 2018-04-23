package com.makan.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.makan.R;
import com.makan.app.activity.FilterActivity;
import com.makan.app.util.Utility;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchFragment extends BaseFragment{

    @BindView(R.id.btnFilter)
    Button btnFilter;

    @BindView(R.id.btnSearch)
    Button btnSearch;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this,rootView);
        return rootView;
    }

    @OnClick(R.id.btnFilter)
    void onFilterClicked() {

        new Utility().moveToActivity(getActivity(), FilterActivity.class, null);
    }

    @OnClick(R.id.btnSearch)
    void onSearchClicked() {

    }

}
