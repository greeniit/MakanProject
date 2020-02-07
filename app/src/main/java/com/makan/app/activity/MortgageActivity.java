package com.makan.app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import com.makan.R;
import com.makan.app.fragment.NewMortgageFragment;


public class MortgageActivity extends BaseActivity{


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_fragment_holder);
        setFragment();
    }

    private void setFragment(){
        Bundle bundle=new Bundle();
        bundle.putFloat("PRICE", getIntent().getFloatExtra("PRICE",0));
        NewMortgageFragment mortgageFragment = new NewMortgageFragment();
        mortgageFragment.setArguments(bundle);
        mortgageFragment.setArguments(getIntent().getExtras());
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frame, mortgageFragment);
        transaction.commit();

    }

}
