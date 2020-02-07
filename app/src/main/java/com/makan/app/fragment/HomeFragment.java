package com.makan.app.fragment;

import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.makan.R;
import com.makan.app.activity.FilterActivity;
import com.makan.app.activity.HomeActivity;
import com.makan.app.activity.PropertyListActivity;
import com.makan.app.activity.SearchActivity;
import com.makan.app.adapter.HomeViewPagerAdapter;
import com.makan.app.adapter.RecentPropertyListAdapter;
import com.makan.app.app.AppState;
import com.makan.app.app.WebConstant;
import com.makan.app.core.Codes;
import com.makan.app.preference.PrefKey;
import com.makan.app.preference.PreferenceManager;
import com.makan.app.util.Utility;
import com.makan.app.web.WebServiceManager;
import com.makan.app.web.pojo.HomeRequest;
import com.makan.app.web.pojo.HomeResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.relex.circleindicator.CircleIndicator;
import retrofit2.Response;

public class HomeFragment extends BaseFragment {



    @BindView(R.id.tvShowAllFeaturedProperty)
    TextView tvShowAllFeaturedProperty;

    @BindView(R.id.tvShowAllRecentlyAddedProperty)
    TextView tvShowAllRecentlyAddedProperty;

    @BindView(R.id.tvRentPropertyCount)
    TextView tvRentPropertyCount;

    @BindView(R.id.tvBuyPropertyCount)
    TextView tvBuyPropertyCount;

    @BindView(R.id.rvFeaturedProperty)
    RecyclerView rvFeaturedProperty;

    @BindView(R.id.rvRecentlyAddedProperty)
    RecyclerView rvRecentlyAddedProperty;

    @BindView(R.id.llRecentlyAddedPropertyHolder)
    LinearLayout llRecentlyAddedPropertyHolder;

    @BindView(R.id.llFeaturedPropertyHolder)
    LinearLayout llFeaturedPropertyHolder;

    @BindView(R.id.ivFilter)
    ImageView ivFilter;

    @BindView(R.id.rlProgressHolder)
    RelativeLayout rlProgressHolder;

    @BindView(R.id.llBuy)
    LinearLayout llBuy;

    @BindView(R.id.llRent)
    LinearLayout llRent;

    @BindView(R.id.etSearch)
    EditText etSearch;

    @BindView(R.id.homeViewPager)
    ViewPager homeViewPager;

    @BindView(R.id.indicator)
    CircleIndicator indicator;

    private List<HomeResponse.RecentProperty> mFeaturedPropertyList, mRecentlyAddedPropertyList;
    private RecentPropertyListAdapter mFeaturedPropertyListAdapter, mRecentlyAddedPropertyListAdapter;
    private HomeViewPagerAdapter homeViewPagerAdapter;


    Handler handler = new Handler();
    int currentPage=0;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFeaturedPropertyList = new ArrayList<>();
        mRecentlyAddedPropertyList = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, rootView);
        setRecycleView();

        new HomeDataFetchTask().execute();

        return rootView;
    }

    private void setRecycleView() {

        mFeaturedPropertyListAdapter = new RecentPropertyListAdapter(getActivity(), mFeaturedPropertyList, RecentPropertyListAdapter.STYLE_HORIZONTAL_LIST_CELL);
        mRecentlyAddedPropertyListAdapter = new RecentPropertyListAdapter(getActivity(), mRecentlyAddedPropertyList, RecentPropertyListAdapter.STYLE_HORIZONTAL_LIST_CELL);
        rvFeaturedProperty.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rvRecentlyAddedProperty.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rvFeaturedProperty.setAdapter(mFeaturedPropertyListAdapter);
        rvRecentlyAddedProperty.setAdapter(mRecentlyAddedPropertyListAdapter);
    }

    /**
     * Adding few properties for testing
     */
    /*private void preparePropertyList() {

        int[] featuredProperty = new int[]{
                R.drawable.image_1,
                R.drawable.image_2,
                R.drawable.image_3,
                R.drawable.image_4};

        Property property = new Property();

        property.setThumbnail(featuredProperty[0]);
        mFeaturedPropertyList.add(property);
        mRecentlyAddedPropertyList.add(property);

        property = new Property();
        property.setThumbnail(featuredProperty[1]);
        mFeaturedPropertyList.add(property);
        mRecentlyAddedPropertyList.add(property);

        property = new Property();
        property.setThumbnail(featuredProperty[2]);
        mFeaturedPropertyList.add(property);
        mRecentlyAddedPropertyList.add(property);

        property = new Property();
        property.setThumbnail(featuredProperty[3]);
        mFeaturedPropertyList.add(property);
        mRecentlyAddedPropertyList.add(property);

        mFeaturedPropertyListAdapter.notifyDataSetChanged();
        mRecentlyAddedPropertyListAdapter.notifyDataSetChanged();

        rvFeaturedProperty.smoothScrollToPosition(0);
        rvRecentlyAddedProperty.smoothScrollToPosition(0);
    }*/

    @OnClick(R.id.tvShowAllFeaturedProperty)
    void onTvShowAllFeaturedPropertyClicked() {

        if(mFeaturedPropertyList!=null){

            Bundle bundle=new Bundle();
            bundle.putParcelableArrayList("featured_properties", (ArrayList<? extends Parcelable>) mFeaturedPropertyList);

            new Utility().moveToActivity(getActivity(), PropertyListActivity.class, bundle);
        }


    }

    @OnClick(R.id.tvShowAllRecentlyAddedProperty)
    void onTvShowAllRecentlyAddedPropertyClicked() {

        if(mRecentlyAddedPropertyList!=null) {

            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("recent_properties", (ArrayList<? extends Parcelable>) mRecentlyAddedPropertyList);

            new Utility().moveToActivity(getActivity(), PropertyListActivity.class, bundle);
        }


    }

    @OnClick(R.id.etSearch)
    void onSearchClicked() {

        new Utility().moveToActivity(getActivity(), SearchActivity.class, null);
    }

    @OnClick(R.id.llBuy)
    void onBuyClicked() {

        Bundle bundle=new Bundle();
        bundle.putInt("type",1);

        new Utility().moveToActivity(getActivity(), PropertyListActivity.class, bundle);
    }

    @OnClick(R.id.llRent)
    void onRentClicked() {

        Bundle bundle=new Bundle();
        bundle.putInt("type",2);
        new Utility().moveToActivity(getActivity(), PropertyListActivity.class, bundle);
    }

    @OnClick(R.id.ivFilter)
    void onIvFilterClicked() {

        new Utility().moveToActivity(getActivity(), FilterActivity.class, null);
    }

    private class HomeDataFetchTask extends AsyncTask<Void, Void, Integer> {

        String errorMessage;
        HomeResponse homeResponse;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            rlProgressHolder.setVisibility(View.VISIBLE);
        }

        @Override
        protected Integer doInBackground(Void... params) {


            int statusCode = 0;

            if (new Utility().isNetworkConnected(getActivity())) {

                HomeRequest homeRequest = new HomeRequest();

                if(AppState.getInstance().getUserId()!=null){
                    homeRequest.setUserId(Integer.parseInt(AppState.getInstance().getUserId()));

                }
                homeRequest.setLanguage(new PreferenceManager().getValue(getActivity(), PrefKey.CURRENT_DATA));

                Response<HomeResponse> response = WebServiceManager.getInstance().homeData(homeRequest);

                if (response != null && response.isSuccessful() && response.raw().code() == 200) {

                    homeResponse = response.body();

                    if (homeResponse != null) {

                        if (homeResponse.getRes() == 1 && homeResponse.getIsSuccess() == 1) {

                            mRecentlyAddedPropertyList=homeResponse.getRecentProperty();
                            mFeaturedPropertyList=homeResponse.getFeaturedProperty();

                            mRecentlyAddedPropertyListAdapter.addItems(mRecentlyAddedPropertyList);
                            mFeaturedPropertyListAdapter.addItems(mFeaturedPropertyList);
                            statusCode = Codes.SUCCESS;


                        } else if (homeResponse.getRes() == 0) {
                            errorMessage = homeResponse.getMsg();
                            statusCode = Codes.ERROR_USER_ALREADY_EXISTS;
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

                homeViewPagerAdapter=new HomeViewPagerAdapter(getActivity(),homeResponse.getSliderImages());
                homeViewPager.setAdapter(homeViewPagerAdapter);
                indicator.setViewPager(homeViewPager);



                new Timer().schedule(new TimerTask() {

                    @Override
                    public void run() {
                        handler.post(update);
                    }
                }, 100, 3000);

                mFeaturedPropertyListAdapter.notifyDataSetChanged();
                mRecentlyAddedPropertyListAdapter.notifyDataSetChanged();

                int rentCount=homeResponse.getTotalRentItemsCount();
                int buyCount=homeResponse.getTotalBuyItemsCount();

                if(rentCount==1){
                    tvRentPropertyCount.setText("1 ITEM");
                }else{
                    tvRentPropertyCount.setText(String.valueOf(rentCount+" ITEMS"));
                }

                if(buyCount==1){
                    tvBuyPropertyCount.setText("1 ITEM");
                }else{
                    tvBuyPropertyCount.setText(String.valueOf(buyCount+" ITEMS"));
                }

                if(homeResponse.getFeaturedProperty()!=null&&homeResponse.getFeaturedProperty().size()>0){
                    llFeaturedPropertyHolder.setVisibility(View.VISIBLE);
                }else{
                    llFeaturedPropertyHolder.setVisibility(View.GONE);
                }

                if(homeResponse.getRecentProperty()!=null&&homeResponse.getRecentProperty().size()>0){
                    llRecentlyAddedPropertyHolder.setVisibility(View.VISIBLE);
                }else{
                    llRecentlyAddedPropertyHolder.setVisibility(View.GONE);
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

    final Runnable update = new Runnable() {
        public void run() {

            if(homeViewPagerAdapter!=null){

                if (homeViewPager.getCurrentItem() == homeViewPagerAdapter.getCount()-1) {
                    currentPage = 0;
                }

                homeViewPager.setCurrentItem(currentPage++, true);
            }

        }
    };


    @Override
    public void onStop() {
        super.onStop();

        if(handler!=null){
            handler.removeCallbacks( update );
        }

    }
}