package com.makan.app.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.makan.R;
import com.makan.app.app.AppState;
import com.makan.app.callback.DialogCallback;
import com.makan.app.core.Codes;
import com.makan.app.util.Utility;
import com.makan.app.web.WebServiceManager;
import com.makan.app.web.pojo.BookPropertyRequest;
import com.makan.app.web.pojo.BookPropertyResponse;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;

public class BookPropertyFragment extends BaseFragment {

    @BindView(R.id.swUserType)
    Switch swUserType;

    @BindView(R.id.tvIndividual)
    TextView tvIndividual;

    @BindView(R.id.tvDealer)
    TextView tvDealer;

    @BindView(R.id.etName)
    EditText etName;

    @BindView(R.id.etEmail)
    EditText etEmail;

    @BindView(R.id.etMob)
    EditText etMob;

    @BindView(R.id.etPhone)
    EditText etPhone;

    @BindView(R.id.etMessage)
    EditText etMessage;

    @BindView(R.id.etPayment)
    EditText etPayment;

    @BindView(R.id.etLoan)
    EditText etLoan;

    @BindView(R.id.cbAgree)
    CheckBox cbAgree;

    @BindView(R.id.cbInterestedInLoan)
    CheckBox cbInterestedInLoan;

    private int propertyId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_book_property, container, false);
        ButterKnife.bind(this, rootView);
        init();
        setListeners();
        return rootView;
    }

    private void init() {

        propertyId = getActivity().getIntent().getIntExtra("property_id",0);

        tvIndividual.setTextColor(getResources().getColor(R.color.colorAccent));
        tvDealer.setTextColor(getResources().getColor(R.color.black));
    }

    private void setListeners() {

        swUserType.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b) {

                    tvIndividual.setTextColor(getResources().getColor(R.color.black));
                    tvDealer.setTextColor(getResources().getColor(R.color.colorAccent));

                } else {

                    tvIndividual.setTextColor(getResources().getColor(R.color.colorAccent));
                    tvDealer.setTextColor(getResources().getColor(R.color.black));

                }

            }
        });
    }

    @OnClick(R.id.btnSubmit)
    void onSubmitClicked() {

        if (etName.getText().length() != 0 && etEmail.getText().length() != 0 && etMob.getText().length() != 0 && etPhone.getText().length() != 0 && etLoan.getText().length() != 0 && etLoan.getText().length() != 0  && etMessage.getText().length() != 0) {

            if (cbAgree.isChecked()) {

                new BookPropertyTask().execute();

            } else {

                new Utility().showMessageAlertDialog(getActivity(), getResources().getString(R.string.agreetermsandconditions));
            }


        } else {

            new Utility().showMessageAlertDialog(getActivity(), getResources().getString(R.string.enterRequiredDetails));
        }


    }

    @OnClick(R.id.btnCancel)
    void onCancelClicked() {

        getActivity().finish();
    }


    public class BookPropertyTask extends AsyncTask<Void, Void, Integer> {

        private BookPropertyResponse bookPropertyResponse;
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

                BookPropertyRequest bookPropertyRequest = new BookPropertyRequest();
                bookPropertyRequest.setId(propertyId);
                bookPropertyRequest.setAgree(cbAgree.isChecked()?1:0);
                bookPropertyRequest.setEmail(etEmail.getText().toString());
                bookPropertyRequest.setId(Integer.parseInt(AppState.getInstance().getUserId()));
                bookPropertyRequest.setMessage(etMessage.getText().toString());
                bookPropertyRequest.setName(etName.getText().toString());
                bookPropertyRequest.setNumber(etMob.getText().toString());
                bookPropertyRequest.setLoan(Integer.parseInt(etLoan.getText().toString()));
                bookPropertyRequest.setPayment(etPayment.getText().toString());

                Response<BookPropertyResponse> response = WebServiceManager.getInstance().bookProperty(bookPropertyRequest);

                if (response != null && response.isSuccessful() && response.raw().code() == 200) {

                    bookPropertyResponse = response.body();

                    if (bookPropertyResponse != null) {

                        if (bookPropertyResponse.getIsSuccess() == 1) {

                            statusCode = Codes.SUCCESS;

                        } else {

                            errorMessage = bookPropertyResponse.getMsg();
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

                new Utility().showMessageAlertDialog(getActivity(), getResources().getString(R.string.sucessfullyScribed), 1, new DialogCallback() {
                    @Override
                    public void onDialogDismissed(int dialogId) {

                        if(getActivity()!=null){
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

