package com.makan.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.makan.R;
import com.makan.app.fragment.PropertyListFragment;

public class PropertyListActivity extends BaseActivity{


    private Toolbar toolbar;
    private PropertyListFragment propertyListFragment;

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

            }else if(getIntent().getExtras()!=null&&(getIntent().getExtras().containsKey("search_properties")||getIntent().getExtras().containsKey("search_key"))){

                getSupportActionBar().setTitle("Search Property");

            }else if(getIntent().getExtras()!=null&&(getIntent().getExtras().containsKey("new_filter"))) {

                getSupportActionBar().setTitle("Search Result");

            }

        }

    }

    private void setFragment(){

        propertyListFragment = new PropertyListFragment();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (100) : {

                if (resultCode == Activity.RESULT_OK) {

                    if(data.getExtras()!=null&&data.getExtras().getBoolean("isRefreshRequired")){
                        propertyListFragment.preparePropertyList();
                    }

                }

                break;
            }
        }
    }

}
