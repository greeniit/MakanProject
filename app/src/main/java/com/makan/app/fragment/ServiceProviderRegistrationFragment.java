package com.makan.app.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.makan.R;
import com.makan.app.adapter.ServiceProviderAdapter;
import com.makan.app.core.Codes;
import com.makan.app.preference.PrefKey;
import com.makan.app.preference.PreferenceManager;
import com.makan.app.professional.ClickListener;
import com.makan.app.professional.ProfessionalSearchResultActivity;
import com.makan.app.professional.ProfessionalSearchResultAdapter;
import com.makan.app.professional.ProfessionalsActivity;
import com.makan.app.professional.ProfessionalsSearchServiceAdapter;
import com.makan.app.util.Utility;
import com.makan.app.web.WebServiceManager;
import com.makan.app.web.pojo.HomeRequest;
import com.makan.app.web.pojo.HomeResponse;
import com.makan.app.web.pojo.ProfesionalServiceRequest;
import com.makan.app.web.pojo.ProfesionalServiceRespose;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Response;


public class ServiceProviderRegistrationFragment extends BaseFragment {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_companyname)
    EditText etCompanyname;
    @BindView(R.id.et_website)
    EditText etWebsite;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_contactnumber)
    EditText etContactnumber;
    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.et_des)
    EditText etDes;
    @BindView(R.id.tv_heading)
    TextView tvHeading;
    @BindView(R.id.rv_service)
    RecyclerView rvService;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.tv_backtopackages)
    TextView tvBacktopackages;
    @BindView(R.id.footer)
    LinearLayout footer;

    private ServiceProviderAdapter professionalsAdapter;
    private ProfessionalSearchResultAdapter professionalSearchResultAdapter;
    private ArrayList<ProfesionalServiceRespose.Service> services = new ArrayList<>();

    public ServiceProviderRegistrationFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_service_provider_registration, container, false);
        ButterKnife.bind(this,rootView);
        new ProfesionalDataFetchTask().execute();
        setToolBar(rootView);
        setRecycleView();
        return rootView;
    }

    private void setRecycleView() {
        professionalsAdapter = new  ServiceProviderAdapter(getContext(), services);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvService.setLayoutManager(linearLayoutManager);
        rvService.setAdapter(professionalsAdapter);
    }

    protected void setToolBar(View rootView) {

        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {

            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.servicepro));

        }



        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(etCompanyname.length()==0)

                {
                    etCompanyname.requestFocus();
                    etCompanyname.setError("FIELD CANNOT BE EMPTY");
                }

                else if(etWebsite.length()==0)
                {
                    etWebsite.requestFocus();
                    etWebsite.setError("FIELD CANNOT BE EMPTY");
                }

                else if(etEmail.length()==0)
                {
                    etEmail.requestFocus();
                    etEmail.setError("FIELD CANNOT BE EMPTY");
                }

                else if(etContactnumber.length()==0)
                {
                    etContactnumber.requestFocus();
                    etContactnumber.setError("FIELD CANNOT BE EMPTY");
                }

                else if(etAddress.length()==0)
                {
                    etAddress.requestFocus();
                    etAddress.setError("FIELD CANNOT BE EMPTY");
                }

                else if(etDes.length()==0)
                {
                    etDes.requestFocus();
                    etDes.setError("FIELD CANNOT BE EMPTY");
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

    private class ProfesionalDataFetchTask extends AsyncTask<Void, Void, Integer> {

        private String errorMessage;
        private ProfesionalServiceRespose profesionalServiceRespose;
        HomeResponse homeResponse;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Integer doInBackground(Void... params) {


            int statusCode = 0;

            if (new Utility().isNetworkConnected(getContext())) {

                HomeRequest homeRequest = new HomeRequest();
                ProfesionalServiceRequest profesionalServiceRequest = new ProfesionalServiceRequest();
                profesionalServiceRequest.setLanguage(new PreferenceManager().getValue(getContext(), PrefKey.CURRENT_DATA));

                Response<ProfesionalServiceRespose> response = WebServiceManager.getInstance().getProfessionalServiceList(profesionalServiceRequest);
                Response<HomeResponse> qresponse = WebServiceManager.getInstance().homeData(homeRequest);


                if (response != null && response.isSuccessful() && response.raw().code() == 200) {

                    profesionalServiceRespose = response.body();
                    homeResponse = qresponse.body();

                    if (profesionalServiceRespose != null) {

                        if (profesionalServiceRespose.getIsSuccess() == 1 && profesionalServiceRespose.getService() != null && profesionalServiceRespose.getService().size() > 0) {

                            services = profesionalServiceRespose.getService();
                            statusCode = Codes.SUCCESS;

                        } else if (profesionalServiceRespose.getIsSuccess() == 1 && (profesionalServiceRespose.getService() == null || profesionalServiceRespose.getService().size() == 0)) {

                            statusCode = Codes.ERROR_NO_RECORDS;


                        } else {
                            statusCode = Codes.ERROR_UNEXPECTED;
                        }

                    } else {
                        statusCode = Codes.ERROR_UNEXPECTED;
                    }


                } else {
                    statusCode = Codes.ERROR_UNABLE_CONNECT_TO_SERVER;
                }

            } else {
                statusCode = Codes.ERROR_NETWORK;
            }


            return statusCode;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);



            if (result == Codes.SUCCESS) {

                professionalsAdapter.addItems(services);
                professionalsAdapter.notifyDataSetChanged();

            }
            else {

                if (errorMessage != null && errorMessage.length() > 0) {
                    new Utility().showMessageAlertDialog(getContext(), errorMessage);
                } else {
                    new Utility().showMessageAlertDialog(getContext(), new Utility().getErrorMessage(getContext(), result));
                }

            }

        }

    }

}
