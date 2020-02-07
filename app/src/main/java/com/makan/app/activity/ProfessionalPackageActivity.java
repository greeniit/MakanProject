package com.makan.app.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import com.makan.R;
import com.makan.app.fragment.ProffesionalPackageFragment;

/**
 * Created by Sarath U S, sarath.us44@gmail.com on 2019-09-13.
 */
public class ProfessionalPackageActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_fragment_holder);
        setFragment();
    }

    private void setFragment(){

        ProffesionalPackageFragment  proffesionalPackageFragment = new ProffesionalPackageFragment();
        proffesionalPackageFragment.setArguments(getIntent().getExtras());
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frame, proffesionalPackageFragment);
        transaction.commit();
    }
}

