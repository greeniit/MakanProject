package com.makan.app.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.makan.R;
import com.makan.app.callback.DialogCallback;
import com.makan.app.core.Codes;
import com.makan.app.util.Utility;
import com.makan.app.web.WebServiceManager;
import com.makan.app.web.pojo.ForgotPasswordRequest;
import com.makan.app.web.pojo.ForgotPasswordResponse;

import retrofit2.Response;

public class ForgotPasswordFragment extends BaseFragment {

    private Toolbar toolbar;
    private EditText etEmail;
    private Button btnLogin;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_forgot_password, container, false);
        initialiseComponents(rootView);
        setToolBar();
        setListeners();
        return rootView;
    }


    private void initialiseComponents(View view) {
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        etEmail = (EditText) view.findViewById(R.id.etEmail);
        btnLogin = (Button) view.findViewById(R.id.btnLogin);
    }

    protected void setToolBar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {

            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("");

        }

    }

    private void setListeners() {

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (etEmail.getText().toString().trim().length() > 0) {

                    new PasswordResetRequestSubmissionTask().execute();
                }
            }
        });
    }


    public class PasswordResetRequestSubmissionTask extends AsyncTask<Void, Void, Integer> {

        private ForgotPasswordResponse forgotPasswordResponse;
        private String errorMessage;

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

                ForgotPasswordRequest forgotPasswordRequest = new ForgotPasswordRequest();
                forgotPasswordRequest.setEmail(etEmail.getText().toString().trim());

                Response<ForgotPasswordResponse> response = WebServiceManager.getInstance().forgotPassword(forgotPasswordRequest);

                if (response != null && response.isSuccessful() && response.raw().code() == 200) {

                    forgotPasswordResponse = response.body();

                    if (forgotPasswordResponse != null) {

                        if (forgotPasswordResponse.getRes() == 1) {

                            statusCode = Codes.SUCCESS;

                        } else {

                            errorMessage = forgotPasswordResponse.getMsg();
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

                new Utility().showMessageAlertDialog(getActivity(), forgotPasswordResponse.getMsg().trim(), 1, new DialogCallback() {
                    @Override
                    public void onDialogDismissed(int dialogId) {

                        if (getActivity() != null) {
                            getActivity().finish();
                        }

                    }
                });

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



