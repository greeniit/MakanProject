package com.makan.app.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.makan.R;
import com.makan.app.adapter.CategoryAdapter;
import com.makan.app.adapter.SubCategoryAdapter;
import com.makan.app.core.Codes;
import com.makan.app.util.Utility;
import com.makan.app.web.WebServiceManager;
import com.makan.app.web.pojo.GetCategoryResponse;
import com.makan.app.web.pojo.NewsResponse;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Response;

public class CategoryFragment extends BaseFragment {

    @BindView(R.id.rlProgressHolder)
    RelativeLayout rlProgressHolder;

    @BindView(R.id.gridview)
    GridView gridview;


    @BindView(R.id.tvMainTitle)
    TextView tvMainTitle;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_categories, container, false);
        ButterKnife.bind(this, rootView);

        if(getArguments()==null||getArguments().get("selected_category")==null){

            new CategoryDataFetchTask().execute();
        }else{

            tvMainTitle.setText("Subcategory");

            GetCategoryResponse.MainCategory mainCategory=(GetCategoryResponse.MainCategory)getArguments().getParcelable("selected_category");

            gridview.setAdapter(new SubCategoryAdapter(getActivity(),mainCategory.getSubCategoryList()));

            rlProgressHolder.setVisibility(View.GONE);
        }

        return rootView;
    }


    private class CategoryDataFetchTask extends AsyncTask<Void, Void, Integer> {

        private String errorMessage;
        private GetCategoryResponse categoryResponse;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            rlProgressHolder.setVisibility(View.VISIBLE);
        }

        @Override
        protected Integer doInBackground(Void... params) {


            int statusCode = 0;

            if (new Utility().isNetworkConnected(getActivity())) {

                Response<GetCategoryResponse> response = WebServiceManager.getInstance().getCategories();

                if (response != null && response.isSuccessful() && response.raw().code() == 200) {

                    categoryResponse = response.body();

                    if (categoryResponse != null) {

                        if (categoryResponse.getIsSuccess() == 1) {

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

            rlProgressHolder.setVisibility(View.GONE);

            if (result == Codes.SUCCESS) {

                if(categoryResponse.getMainCategory()!=null){

                    gridview.setAdapter(new CategoryAdapter(getActivity(),categoryResponse.getMainCategory()));
                }


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
