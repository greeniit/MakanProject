package com.makan.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import com.makan.R;
import com.makan.app.fragment.RattingFragment;

public class RattingActivity extends BaseActivity{

    String proId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_fragment_holder);
        Intent intent = getIntent();
        proId = intent.getStringExtra("PROID");
        setFragment();
    }

    private void setFragment(){

        RattingFragment rattingFragment = new RattingFragment();
        Bundle arguments = new Bundle();
        arguments.putString( "PROID" , proId);
        rattingFragment.setArguments(arguments);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frame, rattingFragment);
        transaction.commit();
    }

}
