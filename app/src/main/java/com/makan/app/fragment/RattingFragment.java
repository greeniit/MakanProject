package com.makan.app.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.github.ornolfr.ratingview.RatingView;
import com.makan.R;
import com.makan.app.app.AppState;
import com.makan.app.core.Codes;
import com.makan.app.preference.PrefKey;
import com.makan.app.preference.PreferenceManager;
import com.makan.app.professional.ProfessionalsActivity;
import com.makan.app.professional.ProfessionalsDetailsActivity;
import com.makan.app.util.Utility;
import com.makan.app.web.WebServiceManager;
import com.makan.app.web.pojo.ProfessionalRatingRequest;
import com.makan.app.web.pojo.ProfessionalRatingResponse;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;

public class RattingFragment extends BaseFragment {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.imgToolbar)
    ImageView imgToolbar;
    @BindView(R.id.rvratingBar)
    RatingView rvratingBar;
    @BindView(R.id.etDescription)
    EditText etDescription;
    @BindView(R.id.btnSubmitFeedback)
    Button btnSubmitFeedback;


    private String proId, proComment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ratting, container, false);
        ButterKnife.bind(this, rootView);
        initialiseComponents(rootView);
        return rootView;
    }

    private void initialiseComponents(View rootView) {

    }

    @OnClick(R.id.btnSubmitFeedback)
    void onSubmitRatings() {

        proComment = etDescription.getText().toString().trim();
        proId = getArguments().getString("PROID");
        new sendRating().execute();
    }



    private class sendRating extends AsyncTask<Void, Void, Integer> {

        private String errorMessage;
        private ProfessionalRatingResponse professionalRatingResponse;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog();
        }

        @Override
        protected Integer doInBackground(Void... params) {


            int statusCode = 0;

            if (new Utility().isNetworkConnected(getContext())) {

                ProfessionalRatingRequest professionalRatingRequest = new ProfessionalRatingRequest();
                professionalRatingRequest.setAddsId(proId);
                professionalRatingRequest.setComment(proComment);
                if (AppState.getInstance().getUserId() != null) {
                    professionalRatingRequest.setUserId(AppState.getInstance().getUserId());
                }
                professionalRatingRequest.setRate(String.valueOf(rvratingBar.getRating()));
                professionalRatingRequest.setLanguage(new PreferenceManager().getValue(getContext(), PrefKey.CURRENT_DATA));

                Response<ProfessionalRatingResponse> response = WebServiceManager.getInstance().sendProfessionalRating(professionalRatingRequest);

                if (response != null && response.isSuccessful() && response.raw().code() == 200) {

                    professionalRatingResponse = response.body();

                    if (professionalRatingResponse != null) {

                        if (professionalRatingResponse.getIsSuccess() == 1) {


                            statusCode = Codes.SUCCESS;

                        } else {
                            statusCode = Codes.ERROR_NO_RECORDS;
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

                etDescription.setText("");
                new Utility().showMessageAlertDialog(getContext(), getResources().getString(R.string.thankyoufoyourrating));
                new Utility().moveToActivity(getContext(), ProfessionalsActivity.class,null);


            } else {

                if (errorMessage != null && errorMessage.length() > 0) {
                    new Utility().showMessageAlertDialog(getContext(), errorMessage);
                } else {
                    new Utility().showMessageAlertDialog(getContext(), new Utility().getErrorMessage(getContext(), result));
                }

            }


        }


    }


}
