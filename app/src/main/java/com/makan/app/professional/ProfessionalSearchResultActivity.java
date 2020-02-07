package com.makan.app.professional;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.makan.R;
import com.makan.app.activity.BaseActivity;
import com.makan.app.app.WebConstant;
import com.makan.app.core.Codes;
import com.makan.app.model.Proffesional;
import com.makan.app.model.Property;
import com.makan.app.preference.PrefKey;
import com.makan.app.preference.PreferenceManager;
import com.makan.app.util.Utility;
import com.makan.app.web.WebServiceManager;
import com.makan.app.web.pojo.HomeRequest;
import com.makan.app.web.pojo.HomeResponse;
import com.makan.app.web.pojo.ProfesionalServiceRequest;
import com.makan.app.web.pojo.ProfesionalServiceRespose;
import com.makan.app.web.pojo.ProfessionalDetailsResponse;
import com.makan.app.web.pojo.ProfessionalSearchRequest;
import com.makan.app.web.pojo.ProfessionalSerachResponse;
import com.makan.app.web.pojo.PropertyList;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Response;

public class ProfessionalSearchResultActivity extends BaseActivity implements ClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rvServiceList)
    RecyclerView rvServiceList;
    @BindView(R.id.rvServiceItemsList)
    RecyclerView rvServiceItemsList;
    @BindView(R.id.rlProgressHolder)
    RelativeLayout rlProgressHolder;

    String ServiceId;
    String Location;
    ArrayList<Proffesional> mProfesionalsList;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    Proffesional proffesional ;


    private ProfessionalsSearchServiceAdapter professionalsAdapter;
    private ProfessionalSearchResultAdapter professionalSearchResultAdapter;
    private ArrayList<ProfesionalServiceRespose.Service> services = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professional_search_result);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.professionalsearch);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ServiceId = getIntent().getStringExtra("SERVICE_ID");
        Location = getIntent().getStringExtra("LOCATION");
        mProfesionalsList = new ArrayList<>();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ProfessionalSearchResultActivity.this,ProfessionalMapActivity.class);
                ArrayList<Proffesional> proffesionals = new ArrayList<>();
                proffesionals.addAll( mProfesionalsList);
                intent.putParcelableArrayListExtra("DATA",proffesionals);
                startActivity(intent );

            }
        });
        setRecycleView();
        prepareData();
    }

    private void prepareData() {
        new ProfesionalDataFetchTask().execute();
        new getServiceByServiceTypeId().execute();
    }

    private void setRecycleView() {

        professionalsAdapter = new ProfessionalsSearchServiceAdapter(this, this, services);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        rvServiceList.setLayoutManager(linearLayoutManager);
        rvServiceList.setAdapter(professionalsAdapter);


        professionalSearchResultAdapter = new ProfessionalSearchResultAdapter(this, this, mProfesionalsList);
        LinearLayoutManager linearLayout = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        rvServiceItemsList.setLayoutManager(linearLayout);
        rvServiceItemsList.setAdapter(professionalSearchResultAdapter);


    }

    @Override
    public void serviceItemClick(String serviceId) {
//        Toast.makeText(this, "i", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void serviceResultItemClick(String addId) {

        Intent intent = new Intent(this, ProfessionalsDetailsActivity.class);
        intent.putExtra("ITEM_ID", addId);
        startActivity(intent);
//        Toast.makeText(this, addId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void itemClicked(String serviceId) {
        ServiceId = serviceId;
        new getServiceByServiceTypeId().execute();
    }

    private class ProfesionalDataFetchTask extends AsyncTask<Void, Void, Integer> {

        private String errorMessage;
        private ProfesionalServiceRespose profesionalServiceRespose;
        HomeResponse homeResponse;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            rlProgressHolder.setVisibility(View.VISIBLE);
        }

        @Override
        protected Integer doInBackground(Void... params) {


            int statusCode = 0;

            if (new Utility().isNetworkConnected(ProfessionalSearchResultActivity.this)) {

                HomeRequest homeRequest = new HomeRequest();
                ProfesionalServiceRequest profesionalServiceRequest = new ProfesionalServiceRequest();
                profesionalServiceRequest.setLanguage(new PreferenceManager().getValue(ProfessionalSearchResultActivity.this, PrefKey.CURRENT_DATA));

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

            rlProgressHolder.setVisibility(View.GONE);

            if (result == Codes.SUCCESS) {

                professionalsAdapter.addItems(services);
                professionalsAdapter.notifyDataSetChanged();

            }
            else {

                if (errorMessage != null && errorMessage.length() > 0) {
                    new Utility().showMessageAlertDialog(ProfessionalSearchResultActivity.this, errorMessage);
                } else {
                    new Utility().showMessageAlertDialog(ProfessionalSearchResultActivity.this, new Utility().getErrorMessage(ProfessionalSearchResultActivity.this, result));
                }

            }

        }

    }

    private class getServiceByServiceTypeId extends AsyncTask<Void, Void, Integer> {

        private String errorMessage;
        private ProfessionalSerachResponse professionalSerachResponse;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            rlProgressHolder.setVisibility(View.VISIBLE);
            mProfesionalsList.clear();
        }

        @Override
        protected Integer doInBackground(Void... params) {


            int statusCode = 0;

            if (new Utility().isNetworkConnected(ProfessionalSearchResultActivity.this)) {


                ProfessionalSearchRequest professionalSearchRequest = new ProfessionalSearchRequest();
                professionalSearchRequest.setLanguage(new PreferenceManager().getValue(ProfessionalSearchResultActivity.this, PrefKey.CURRENT_DATA));
                professionalSearchRequest.setServiceid(ServiceId);
                professionalSearchRequest.setServicelocation(Location);


                Response<ProfessionalSerachResponse> response = WebServiceManager.getInstance().getProfessionalServicesbyId(professionalSearchRequest);

                if (response != null && response.isSuccessful() && response.raw().code() == 200) {

                    professionalSerachResponse = response.body();

                    if (professionalSerachResponse != null) {

                        if (professionalSerachResponse.getIsSuccess() == 1 && professionalSerachResponse.getProfessional() != null && professionalSerachResponse.getProfessional().size() > 0) {

                            if (professionalSerachResponse.getProfessional() != null && professionalSerachResponse.getProfessional().size() > 0) {




                                for (ProfessionalSerachResponse.Professional professional : professionalSerachResponse.getProfessional()){

                                    Proffesional proffesional=new Proffesional();

                                    proffesional.setAddsLat(professional.getAddsLat());
                                    proffesional.setAddId(professional.getAddId());
                                    proffesional.setAddsAddress(professional.getAddsAddress());
                                    proffesional.setAddsDescription(professional.getAddsDescription());
                                    proffesional.setAddsLong(professional.getAddsLong());
                                    proffesional.setAddsPhoto(professional.getAddsPhoto());
                                    proffesional.setAddsWebsite(professional.getAddsWebsite());
                                    proffesional.setAvgRate(professional.getAvgRate());
                                    proffesional.setCompanyLogo(professional.getCompanyLogo());
                                    proffesional.setCompanyName(professional.getCompanyName());

                                    mProfesionalsList.addAll(Collections.singleton(proffesional));
                                }

//                                mProfesionalsList.addAll(professionalSerachResponse.getProfessional());
                            }

                            statusCode = Codes.SUCCESS;

                        } else if (professionalSerachResponse.getIsSuccess() == 1 && (professionalSerachResponse.getProfessional() == null || professionalSerachResponse.getProfessional().size() == 0)) {

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

            rlProgressHolder.setVisibility(View.GONE);

            if (result == Codes.SUCCESS) {

                professionalSearchResultAdapter.notifyDataSetChanged();

            } else {

                if (errorMessage != null && errorMessage.length() > 0) {
                    new Utility().showMessageAlertDialog(ProfessionalSearchResultActivity.this, errorMessage);
                } else {
                    new Utility().showMessageAlertDialog(ProfessionalSearchResultActivity.this, new Utility().getErrorMessage(ProfessionalSearchResultActivity.this, result));
                }

            }


        }
    }

}
