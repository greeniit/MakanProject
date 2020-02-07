package com.makan.app.activity;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.makan.R;
import com.makan.app.fragment.BookPropertyFragment;
import com.makan.app.fragment.PostYourRequirementsFragment;

public class PostYourRequirementsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_fragment_holder);
        setFragment();
    }

    private void setFragment(){

        PostYourRequirementsFragment postYourRequirementsFragment = new PostYourRequirementsFragment();
        postYourRequirementsFragment.setArguments(getIntent().getExtras());
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frame, postYourRequirementsFragment);
        transaction.commit();
    }
}
