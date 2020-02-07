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
import android.widget.ListAdapter;
import android.widget.RelativeLayout;

import com.makan.R;
import com.makan.app.adapter.DealerListAdapter;
import com.makan.app.adapter.ProffesionalPackageAdapter;
import com.makan.app.core.Codes;
import com.makan.app.preference.PrefKey;
import com.makan.app.preference.PreferenceManager;
import com.makan.app.util.Utility;
import com.makan.app.web.WebServiceManager;
import com.makan.app.web.pojo.ProffesionalPackageRequest;
import com.makan.app.web.pojo.ProffesionalPackageResponse;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Response;


public class ProffesionalPackageFragment extends Fragment {


    @BindView(R.id.rvDealer)
    RecyclerView rvDealer;

    @BindView(R.id.rlProgressHolder)
    RelativeLayout rlProgressHolder;

    private List<ProffesionalPackageResponse.ProfessionalPackage> mPackageList;
    private ProffesionalPackageAdapter proffesionalPackageAdapter;


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
        proffesionalPackageAdapter = new ProffesionalPackageAdapter(getActivity(), mPackageList);
        rvDealer.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rvDealer.setItemAnimator(new DefaultItemAnimator());
        rvDealer.setAdapter(proffesionalPackageAdapter);

    }

    private class ProfessionalPackageFetch extends AsyncTask<Void, Void, Integer> {

        private String errorMessage;
        private ProffesionalPackageResponse proffesionalPackageResponse;
        private ProffesionalPackageRequest proffesionalPackageRequest;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            rlProgressHolder.setVisibility(View.VISIBLE);
        }

        @Override
        protected Integer doInBackground(Void... params) {


            int statusCode = 0;

            if (new Utility().isNetworkConnected(getActivity())) {

                proffesionalPackageRequest = new ProffesionalPackageRequest();
                proffesionalPackageRequest.setLanguage(new PreferenceManager().getValue(getActivity(), PrefKey.CURRENT_DATA));
                Response<ProffesionalPackageResponse> response = WebServiceManager.getInstance().getPackageData(proffesionalPackageRequest);


                if (response != null && response.isSuccessful() && response.raw().code() == 200) {

                    proffesionalPackageResponse = response.body();

                    if (proffesionalPackageResponse != null) {

                        if (proffesionalPackageResponse.getIsSuccess() == 1 && proffesionalPackageResponse.getProfessionalPackage()!=null&&proffesionalPackageResponse.getProfessionalPackage().size()>0) {

                            statusCode = Codes.SUCCESS;
                            mPackageList=proffesionalPackageResponse.getProfessionalPackage();

                        } else if (proffesionalPackageResponse.getIsSuccess() == 1 && (proffesionalPackageResponse.getProfessionalPackage()==null||proffesionalPackageResponse.getProfessionalPackage().size()==0)){

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
