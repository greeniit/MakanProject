package com.makan.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makan.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsDetailFragment extends BaseFragment{

    @BindView(R.id.tvTitle)
    TextView tvTitle;

    @BindView(R.id.tvDescription)
    TextView tvDescription;

    @BindView(R.id.ivMain)
    ImageView ivMain;

    @BindView(R.id.toolbar)
    Toolbar toolbar;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_news_detail, container, false);
        ButterKnife.bind(this,rootView);

        setToolBar();

        if(getArguments().containsKey("title")){
            tvTitle.setText(getArguments().getString("title"));
        }

        if(getArguments().containsKey("description")){
            tvDescription.setText(getArguments().getString("description"));
        }


        return rootView;
    }

    protected void setToolBar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("");
        }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(getArguments().containsKey("imageUrl")){
            
            Glide.with(getActivity()).load(getArguments().getString("imageUrl")).placeholder(R.color.colorPrimary).into(ivMain);

        }
    }
}
