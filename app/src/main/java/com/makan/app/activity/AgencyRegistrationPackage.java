package com.makan.app.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.makan.R;
import com.makan.app.fragment.AgencyPackageFragment;
import com.makan.app.fragment.AgencyRegistrationFragment;

/**
 * Created by Sarath U S, sarath.us44@gmail.com on 2019-09-19.
 */

public class AgencyRegistrationPackage extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_fragment_holder);
        setFragment();
    }

    private void setFragment(){

        AgencyRegistrationFragment agencyRegistrationFragment = new AgencyRegistrationFragment();
        agencyRegistrationFragment.setArguments(getIntent().getExtras());
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frame, agencyRegistrationFragment);
        transaction.commit();
    }
}

