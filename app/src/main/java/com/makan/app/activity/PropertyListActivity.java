package com.makan.app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;

import com.makan.R;
import com.makan.app.fragment.PropertyListFragment;

public class PropertyListActivity extends BaseActivity{


    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_main);
        initialiseComponents();
        setToolBar();
        setFragment();
    }

    private void initialiseComponents(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    protected void setToolBar(){
        setSupportActionBar(toolbar);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            if(getIntent().getExtras()!=null&&getIntent().getExtras().get("title")!=null){
                getSupportActionBar().setTitle(getIntent().getExtras().getString("title"));
            }else if(getIntent().getExtras()!=null&&getIntent().getExtras().containsKey("featured_properties")){

                getSupportActionBar().setTitle("Featured Property");

            }else if(getIntent().getExtras()!=null&&getIntent().getExtras().containsKey("recent_properties")){

                getSupportActionBar().setTitle("Recently Added Property");

            }else if(getIntent().getExtras()!=null&&getIntent().getExtras().containsKey("dealer_properties")){

                getSupportActionBar().setTitle("Dealer Property");

            }else if(getIntent().getExtras()!=null&&getIntent().getExtras().containsKey("place_name")){

                getSupportActionBar().setTitle("City Properties");

            }else if(getIntent().getExtras()!=null&&getIntent().getExtras().containsKey("search_properties")){

                getSupportActionBar().setTitle("Search Property");

            }

        }

    }

    private void setFragment(){

        PropertyListFragment propertyListFragment = new PropertyListFragment();
        propertyListFragment.setArguments(getIntent().getExtras());
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frame, propertyListFragment);
        transaction.commit();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
