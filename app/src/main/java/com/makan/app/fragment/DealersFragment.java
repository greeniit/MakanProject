package com.makan.app.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.makan.R;
import com.makan.app.adapter.DealerListAdapter;
import com.makan.app.core.Codes;
import com.makan.app.util.Utility;
import com.makan.app.web.WebServiceManager;
import com.makan.app.web.pojo.Dealer;
import com.makan.app.web.pojo.DealerResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Response;

public class DealersFragment extends BaseFragment{

    @BindView(R.id.rvDealer)
    RecyclerView rvDealer;

    @BindView(R.id.rlProgressHolder)
    RelativeLayout rlProgressHolder;

    private List<Dealer> mDealerList;
    private DealerListAdapter mDealerAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDealerList = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_dealer, container, false);
        ButterKnife.bind(this,rootView);
        setRecycleView();
        new DealerDataFetchTask().execute();
        return rootView;
    }

    private void setRecycleView() {

        mDealerAdapter = new DealerListAdapter(getActivity(), mDealerList);
        rvDealer.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rvDealer.setItemAnimator(new DefaultItemAnimator());
        rvDealer.setAdapter(mDealerAdapter);
    }

    private class DealerDataFetchTask extends AsyncTask<Void, Void, Integer> {

        private String errorMessage;
        private DealerResponse dealerResponse;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            rlProgressHolder.setVisibility(View.VISIBLE);
        }

        @Override
        protected Integer doInBackground(Void... params) {


            int statusCode = 0;

            if (new Utility().isNetworkConnected(getActivity())) {

                Response<DealerResponse> response = WebServiceManager.getInstance().getDealers();

                if (response != null && response.isSuccessful() && response.raw().code() == 200) {

                    dealerResponse = response.body();

                    if (dealerResponse != null) {

                        if (dealerResponse.getIsSuccess() == 1 && dealerResponse.getDealerList().size()>0) {

                            statusCode = Codes.SUCCESS;
                            mDealerList=dealerResponse.getDealerList();

                        } else if (dealerResponse.getIsSuccess() == 1 && dealerResponse.getDealerList().size()==0){

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

                mDealerAdapter.addItems(mDealerList);
                mDealerAdapter.notifyDataSetChanged();

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
