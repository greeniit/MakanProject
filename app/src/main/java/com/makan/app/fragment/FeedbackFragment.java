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
import android.widget.TextView;

import com.github.ornolfr.ratingview.RatingView;
import com.makan.R;
import com.makan.app.app.AppState;
import com.makan.app.core.Codes;
import com.makan.app.util.Utility;
import com.makan.app.web.WebServiceManager;
import com.makan.app.web.pojo.FeedbackRequest;
import com.makan.app.web.pojo.FeedbackResponse;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;

public class FeedbackFragment extends BaseFragment {

    private Toolbar toolbar;

    @BindView(R.id.btnSubmitFeedback)
    Button btnSubmitFeedback;

    @BindView(R.id.rvOne)
    RatingView rvOne;

    @BindView(R.id.rvTwo)
    RatingView rvTwo;

    @BindView(R.id.rvThree)
    RatingView rvThree;

    @BindView(R.id.tvOne)
    TextView tvOne;

    @BindView(R.id.tvTwo)
    TextView tvTwo;

    @BindView(R.id.tvThree)
    TextView tvThree;

    @BindView(R.id.etDescription)
    EditText etDescription;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_feedback, container, false);
        ButterKnife.bind(this,rootView);
        initialiseComponents(rootView);
        return rootView;
    }


    private void initialiseComponents(View view) {

        rvOne.setOnRatingChangedListener(new RatingView.OnRatingChangedListener() {
            @Override
            public void onRatingChange(float oldRating, float newRating) {
                tvOne.setText(getRatings((int)newRating));
            }
        });

        rvTwo.setOnRatingChangedListener(new RatingView.OnRatingChangedListener() {
            @Override
            public void onRatingChange(float oldRating, float newRating) {

                tvTwo.setText(getRatings((int)newRating));
            }
        });

        rvThree.setOnRatingChangedListener(new RatingView.OnRatingChangedListener() {
            @Override
            public void onRatingChange(float oldRating, float newRating) {

                tvThree.setText(getRatings((int)newRating));
            }
        });
    }

    protected void setToolBar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {

            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.feedback));

        }

    }

    @OnClick(R.id.btnSubmitFeedback)
    void onSubmitFeedbackClicked(){

        FeedbackRequest feedbackRequest=new FeedbackRequest();
        feedbackRequest.setReviewService(getRatings((int)rvOne.getRating()));
        feedbackRequest.setReviewOffer(getRatings((int)rvTwo.getRating()));
        feedbackRequest.setReviewPrice(getRatings((int)rvThree.getRating()));
        feedbackRequest.setUserId(AppState.getInstance().getUserId());
        feedbackRequest.setDescription(etDescription.getText().toString());

        new SubmitFeedbackTask(feedbackRequest).execute();
    }

    private String getRatings(int rating){

        switch (rating){

            case 5:

                return getResources().getString(R.string.VeryGood);

            case 4:

                return getResources().getString(R.string.good);

            case 3:

                return getResources().getString(R.string.fair);


            case 2:

                return getResources().getString(R.string.average);


            case 1:

                return getResources().getString(R.string.Bad);



        }

        return null;
    }

    private class SubmitFeedbackTask extends AsyncTask<Void, Void, Integer> {

        private String errorMessage;
        private FeedbackRequest feedbackRequest;
        private FeedbackResponse feedbackResponse;

        public SubmitFeedbackTask(FeedbackRequest feedbackRequest){

            this.feedbackRequest=feedbackRequest;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog();
        }

        @Override
        protected Integer doInBackground(Void... params) {


            int statusCode = 0;

            if (new Utility().isNetworkConnected(getActivity())) {

                Response<FeedbackResponse> response = WebServiceManager.getInstance().sendFeedback(feedbackRequest);

                if (response != null && response.isSuccessful() && response.raw().code() == 200) {

                    feedbackResponse = response.body();

                    if (feedbackResponse != null) {

                        if (feedbackResponse.getIsSuccess() == 1) {

                            statusCode = Codes.SUCCESS;

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

            dismissProgressDialog();
            if (result == Codes.SUCCESS) {
                new Utility().showMessageAlertDialog(getActivity(), getResources().getString(R.string.sucessfully));
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