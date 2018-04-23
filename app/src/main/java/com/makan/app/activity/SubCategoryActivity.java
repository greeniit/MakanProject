package com.makan.app.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;

import com.makan.R;
import com.makan.app.fragment.CategoryFragment;
import com.makan.app.fragment.DealerDetailFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SubCategoryActivity extends BaseActivity{

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_fragment_holder_with_toolbar);
        ButterKnife.bind(this);
        setToolBar();
        setFragment();
    }

    protected void setToolBar() {
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            toolbar.setTitleTextColor(Color.WHITE);
            toolbar.setSubtitleTextColor(Color.WHITE);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Subcategory");

        }

    }

    private void setFragment(){

        CategoryFragment categoryFragment = new CategoryFragment();
        categoryFragment.setArguments(getIntent().getExtras());
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frame, categoryFragment);
        transaction.commit();
    }

}
