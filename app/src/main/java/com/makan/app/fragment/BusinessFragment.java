package com.makan.app.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.makan.R;
import com.makan.app.core.Codes;
import com.makan.app.util.Utility;
import com.makan.app.web.WebServiceManager;
import com.makan.app.web.pojo.SubscribeBusinessRequest;
import com.makan.app.web.pojo.SubscribeBusinessResponse;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;

public class BusinessFragment extends BaseFragment{

    @BindView(R.id.etFirstName)
    EditText etFirstName;

    @BindView(R.id.etLastName)
    EditText etLastName;

    @BindView(R.id.etEmail)
    EditText etEmail;

    @BindView(R.id.btnSubscribe)
    TextView btnSubscribe;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_bussiness, container, false);
        ButterKnife.bind(this,rootView);

        return rootView;
    }


    @OnClick(R.id.btnSubscribe)
    void subscribeButtonClicked(){

        if(etFirstName.getText().length()>0&&etLastName.getText().length()>0&&etEmail.getText().length()>0){

            new SubmitSubscriptionDetails().execute();

        }else{

            new Utility().showMessageAlertDialog(getActivity(),"Enter all the details.");
        }

    }

    private class SubmitSubscriptionDetails extends AsyncTask<Void, Void, Integer> {

        private String errorMessage;
        private SubscribeBusinessResponse subscribeBusinessResponse;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            new Utility().hideSoftKeyBoard(getActivity());

            showProgressDialog();
        }

        @Override
        protected Integer doInBackground(Void... params) {


            int statusCode = 0;

            if (new Utility().isNetworkConnected(getActivity())) {

                SubscribeBusinessRequest subscribeBusinessRequest=new SubscribeBusinessRequest();
                subscribeBusinessRequest.setEmail(etEmail.getText().toString());
                subscribeBusinessRequest.setFname(etFirstName.getText().toString());
                subscribeBusinessRequest.setLname(etLastName.getText().toString());

                Response<SubscribeBusinessResponse> response = WebServiceManager.getInstance().subscribeBusiness(subscribeBusinessRequest);

                if (response != null && response.isSuccessful() && response.raw().code() == 200) {

                    subscribeBusinessResponse = response.body();

                    if (subscribeBusinessResponse != null) {

                        if (subscribeBusinessResponse.getIsSuccess() == 1) {

                            statusCode = Codes.SUCCESS;

                        } else {

                            errorMessage=subscribeBusinessResponse.getMsg();
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

            dismissProgressDialog();

            if (result == Codes.SUCCESS) {

                new Utility().showMessageAlertDialog(getActivity(), "You have successfully subscribed to makan.");

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
