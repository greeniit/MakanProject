package com.makan.app.fragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.makan.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class AgencyRegistrationFragment extends BaseFragment {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_heading)
    TextView tvHeading;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_contactnumber)
    EditText etContactnumber;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_crno)
    EditText etCrno;
    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.et_website)
    EditText etWebsite;
    @BindView(R.id.et_meesage)
    EditText etMeesage;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.tv_backtopackages)
    TextView tvBacktopackages;
    @BindView(R.id.footer)
    LinearLayout footer;
    Unbinder unbinder;

    public AgencyRegistrationFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_agency_registration, container, false);
        ButterKnife.bind(this, rootView);
        setToolBar(rootView);
        return rootView;
    }

    protected void setToolBar(View rootView) {

        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {

            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.agencyregistration));

        }

        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(etName.length()==0)

                {
                    etName.requestFocus();
                    etName.setError("FIELD CANNOT BE EMPTY");
                }

                else if(etContactnumber.length()==0)
                {
                    etContactnumber.requestFocus();
                    etContactnumber.setError("FIELD CANNOT BE EMPTY");
                }

                else if(etEmail.length()==0)
                {
                    etEmail.requestFocus();
                    etEmail.setError("FIELD CANNOT BE EMPTY");
                }
                else if(etCrno.length()==0)
                {
                    etCrno.requestFocus();
                    etCrno.setError("FIELD CANNOT BE EMPTY");
                }

                else if(etAddress.length()==0)
                {
                    etAddress.requestFocus();
                    etAddress.setError("FIELD CANNOT BE EMPTY");
                }

                else if(etWebsite.length()==0)
                {
                    etWebsite.requestFocus();
                    etWebsite.setError("FIELD CANNOT BE EMPTY");
                }

                else if(etMeesage.length()==0)
                {
                    etMeesage.requestFocus();
                    etMeesage.setError("FIELD CANNOT BE EMPTY");
                }
                else {
                    Toast.makeText(getContext(),getResources().getText(R.string.registration_success),Toast.LENGTH_LONG).show();
                    getActivity().finish();
                }

            }
        });

        tvBacktopackages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });


    }


}
