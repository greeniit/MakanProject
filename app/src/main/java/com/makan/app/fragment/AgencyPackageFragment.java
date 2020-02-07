package com.makan.app.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.makan.R;
import com.makan.app.adapter.AgencyPackageAdapter;
import com.makan.app.adapter.ProffesionalPackageAdapter;
import com.makan.app.core.Codes;
import com.makan.app.preference.PrefKey;
import com.makan.app.preference.PreferenceManager;
import com.makan.app.util.Utility;
import com.makan.app.web.WebServiceManager;
import com.makan.app.web.pojo.AgencyPackageRequest;
import com.makan.app.web.pojo.AgencyPackageResponse;
import com.makan.app.web.pojo.ProffesionalPackageRequest;
import com.makan.app.web.pojo.ProffesionalPackageResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Response;


public class AgencyPackageFragment extends Fragment {


    @BindView(R.id.rvDealer)
    RecyclerView rvDealer;

    @BindView(R.id.rlProgressHolder)
    RelativeLayout rlProgressHolder;

    private List<AgencyPackageResponse.AgencyPackage> mPackageList;
    private AgencyPackageAdapter agencyPackageAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPackageList = new ArrayList<>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_proffesional_package, container, false);
        ButterKnife.bind(this,rootView);
        new ProfessionalPackageFetch().execute();
//        setRecycleView();
        return rootView;
    }

    private void setRecycleView() {
        agencyPackageAdapter = new AgencyPackageAdapter(getActivity(), mPackageList);
        rvDealer.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rvDealer.setItemAnimator(new DefaultItemAnimator());
        rvDealer.setAdapter(agencyPackageAdapter);

    }

    private class ProfessionalPackageFetch extends AsyncTask<Void, Void, Integer> {

        private String errorMessage;
        private AgencyPackageResponse agencyPackageResponse;
        private AgencyPackageRequest agencyPackageRequest;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            rlProgressHolder.setVisibility(View.VISIBLE);
        }

        @Override
        protected Integer doInBackground(Void... params) {


            int statusCode = 0;

            if (new Utility().isNetworkConnected(getActivity())) {

                agencyPackageRequest = new AgencyPackageRequest();
                agencyPackageRequest.setLanguage(new PreferenceManager().getValue(getActivity(), PrefKey.CURRENT_DATA));
                Response<AgencyPackageResponse> response = WebServiceManager.getInstance().getAgencyData(agencyPackageRequest);


                if (response != null && response.isSuccessful() && response.raw().code() == 200) {

                    agencyPackageResponse = response.body();

                    if (agencyPackageResponse != null) {

                        if (agencyPackageResponse.getIsSuccess() == 1 && agencyPackageResponse.getAgencyPackage()!=null&&agencyPackageResponse.getAgencyPackage().size()>0) {

                            statusCode = Codes.SUCCESS;
                            mPackageList=agencyPackageResponse.getAgencyPackage();

                        } else if (agencyPackageResponse.getIsSuccess() == 1 && (agencyPackageResponse.getAgencyPackage()==null||agencyPackageResponse.getAgencyPackage().size()==0)){

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

//                proffesionalPackageAdapter.addItems(mPackageList);
//                proffesionalPackageAdapter.notifyDataSetChanged();
                setRecycleView();

            } else {

                if (errorMessage != null && errorMessage.length() > 0) {
                    new Utility().showMessageAlertDialog(getActivity(), errorMessage);
                } else {
                    new Utility().showMessageAlertDialog(getActivity(), new Utility().getErrorMessage(getActivity(), result));
                }

            }


        }
    }

}
