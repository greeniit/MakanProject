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
import com.makan.app.adapter.NewsListAdapter;
import com.makan.app.core.Codes;
import com.makan.app.util.Utility;
import com.makan.app.web.WebServiceManager;
import com.makan.app.web.pojo.NewsResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Response;

public class NewsFragment extends BaseFragment{

    @BindView(R.id.rvNews)
    RecyclerView rvNews;

    @BindView(R.id.rlProgressHolder)
    RelativeLayout rlProgressHolder;

    private List<NewsResponse.News> mNewsList;

    private NewsListAdapter mNewsAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNewsList = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_news, container, false);
        ButterKnife.bind(this,rootView);
        setRecycleView();
        prepareDealerList();
        return rootView;
    }

    private void setRecycleView() {

        mNewsAdapter = new NewsListAdapter(getActivity(), mNewsList);
        rvNews.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rvNews.setItemAnimator(new DefaultItemAnimator());
        rvNews.setAdapter(mNewsAdapter);
    }

    /**
     * Adding few properties for testing
     */
    private void prepareDealerList() {

     new NewsDataFetchTask().execute();
    }


    private class NewsDataFetchTask extends AsyncTask<Void, Void, Integer> {

        private String errorMessage;
        private NewsResponse newsResponse;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            rlProgressHolder.setVisibility(View.VISIBLE);
        }

        @Override
        protected Integer doInBackground(Void... params) {


            int statusCode = 0;

            if (new Utility().isNetworkConnected(getActivity())) {

                Response<NewsResponse> response = WebServiceManager.getInstance().getNews();

                if (response != null && response.isSuccessful() && response.raw().code() == 200) {

                    newsResponse = response.body();

                    if (newsResponse != null) {

                        if (newsResponse.getIsSuccess() == 1 && newsResponse.getNews()!=null&&newsResponse.getNews().size()>0) {

                            mNewsList= newsResponse.getNews();
                            statusCode = Codes.SUCCESS;

                        } else if (newsResponse.getIsSuccess() == 1 && (newsResponse.getNews()==null||newsResponse.getNews().size()==0)){

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

                mNewsAdapter.addItems(mNewsList);
                mNewsAdapter.notifyDataSetChanged();

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
