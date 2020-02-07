package com.makan.app.activity;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.makan.R;
import com.makan.app.fragment.AgencyPackageFragment;
import com.makan.app.fragment.ServiceProviderRegistrationFragment;

public class ServiceProviderRegistrationActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_fragment_holder);
        setFragment();
    }

    private void setFragment(){

        ServiceProviderRegistrationFragment serviceProviderRegistrationFragment = new ServiceProviderRegistrationFragment();
        serviceProviderRegistrationFragment.setArguments(getIntent().getExtras());
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frame, serviceProviderRegistrationFragment);
        transaction.commit();
    }
}

