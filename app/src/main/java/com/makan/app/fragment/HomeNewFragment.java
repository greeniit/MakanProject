package com.makan.app.fragment;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.makan.R;
import com.makan.app.activity.HomeActivity;
import com.makan.app.activity.LoginActivity;
import com.makan.app.activity.NewSearchActivity;
import com.makan.app.activity.PostYourRequirementsActivity;
import com.makan.app.activity.PropertyListActivity;
import com.makan.app.adapter.HomeViewPagerAdapter;
import com.makan.app.adapter.RecentPropertyListAdapter;
import com.makan.app.app.AppState;
import com.makan.app.app.WebConstant;
import com.makan.app.core.Codes;
import com.makan.app.preference.PrefKey;
import com.makan.app.preference.PreferenceManager;
import com.makan.app.professional.ProfessionalsActivity;
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
public class HomeNewFragment extends BaseFragment
{

    @BindView(R.id.iv_homeImage)
    ImageView ivHomeImage;
    @BindView(R.id.iv_makanLogo)
    ImageView ivMakanLogo;
    @BindView(R.id.bt_search)
    Button btSearch;
    @BindView(R.id.bt_professional)
    Button btProfessional;
    @BindView(R.id.ll_buttons)
    LinearLayout llButtons;
    @BindView(R.id.bt_postYourRequirement)
    Button btPostYourRequirement;
    @BindView(R.id.tv_buy)
    TextView tvBuy;
    @BindView(R.id.tv_buyPropertyCount)
    TextView tvBuyPropertyCount;
    @BindView(R.id.ll_buy)
    LinearLayout llBuy;
    @BindView(R.id.tv_RentPropertyCount)
    TextView tvRentPropertyCount;
    @BindView(R.id.ll_rent)
    LinearLayout llRent;
    @BindView(R.id.tv_showAllRecentlyAddedProperty)
    TextView tvShowAllRecentlyAddedProperty;
    @BindView(R.id.rv_recentlyAddedProperty)
    RecyclerView rvRecentlyAddedProperty;
    @BindView(R.id.ll_recentlyAddedPropertyHolder)
    LinearLayout llRecentlyAddedPropertyHolder;
    @BindView(R.id.homeViewPager)
    ViewPager homeViewPager;
    @BindView(R.id.indicator)
    CircleIndicator indicator;
    @BindView(R.id.tv_featuredProperty)
    TextView tvFeaturedProperty;
    @BindView(R.id.tv_showAllFeaturedProperty)
    TextView tvShowAllFeaturedProperty;
    @BindView(R.id.rv_featuredProperty)
    RecyclerView rvFeaturedProperty;
    @BindView(R.id.ll_featuredPropertyHolder)
    LinearLayout llFeaturedPropertyHolder;
    @BindView(R.id.rlProgressHolder)
    RelativeLayout rlProgressHolder;
    @BindView(R.id.bt_saleProperty)
    Button btSaleProperty;
    @BindView(R.id.bt_rentProperty)
    Button btRentProperty;
    private PreferenceManager preferenceManager;
    private List<HomeResponse.RecentProperty> mFeaturedPropertyList, mRecentlyAddedPropertyList;
    private List<HomeResponse.RecentProperty> mFeaturedPropertyRentList;
    private RecentPropertyListAdapter mFeaturedPropertyListAdapter, mRecentlyAddedPropertyListAdapter;
    private HomeViewPagerAdapter homeViewPagerAdapter;
    Handler handler = new Handler();
    int currentPage = 0;
    private int selectedType = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFeaturedPropertyList = new ArrayList<>();
        mFeaturedPropertyRentList = new ArrayList<>();
        mRecentlyAddedPropertyList = new ArrayList<>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_home_new, container, false);
        ButterKnife.bind(this, rootView);
        Configuration config = getResources().getConfiguration();
        if (config.smallestScreenWidthDp >= 600)
        {
            ivHomeImage.setImageDrawable(getResources().getDrawable(R.drawable.homeimage));
        }
        else
        {
            ivHomeImage.setImageDrawable(getResources().getDrawable(R.drawable.homeimage));
        }
        setRecycleView();
        preferenceManager = new PreferenceManager();
        if (preferenceManager.getValue(getActivity(), PrefKey.CURRENT_DATA).equals("ar")) {
            setLocale("ar");
        } else {
            setLocale("en");
        }

            new HomeDataFetchTask().execute();
        return rootView;
    }

    private void setRecycleView() {

        mFeaturedPropertyListAdapter = new RecentPropertyListAdapter(getActivity(), mFeaturedPropertyList, RecentPropertyListAdapter.STYLE_HORIZONTAL_LIST_CELL);
        mFeaturedPropertyListAdapter.notifyDataSetChanged();
        mRecentlyAddedPropertyListAdapter = new RecentPropertyListAdapter(getActivity(), mRecentlyAddedPropertyList, RecentPropertyListAdapter.STYLE_HORIZONTAL_LIST_CELL);
        rvFeaturedProperty.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rvRecentlyAddedProperty.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rvFeaturedProperty.setAdapter(mFeaturedPropertyListAdapter);
        rvRecentlyAddedProperty.setAdapter(mRecentlyAddedPropertyListAdapter);

    }

    private void setLocale(String LAN) {
        if (!LAN.equals(preferenceManager.getValue(getActivity(), PrefKey.CURRENT_DATA))) {
            preferenceManager.setValue(getActivity(), PrefKey.CURRENT_DATA, LAN);
            Locale locale = new Locale(LAN);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getResources().updateConfiguration(config, getResources().getDisplayMetrics());
//            startActivity(getIntent());
        }
    }

    private void setFeaturedPropertyData() {
        mFeaturedPropertyListAdapter = new RecentPropertyListAdapter(getActivity(), mFeaturedPropertyRentList, RecentPropertyListAdapter.STYLE_HORIZONTAL_LIST_CELL);
        rvFeaturedProperty.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rvFeaturedProperty.setAdapter(mFeaturedPropertyListAdapter);
        mFeaturedPropertyListAdapter.notifyDataSetChanged();
    }


    @OnClick({R.id.bt_search, R.id.bt_professional, R.id.bt_postYourRequirement, R.id.tv_showAllRecentlyAddedProperty, R.id.tv_showAllFeaturedProperty, R.id.bt_saleProperty, R.id.bt_rentProperty,R.id.ll_buy, R.id.ll_rent})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_search:
                new Utility().moveToActivity(getActivity(), NewSearchActivity.class, null);
                break;
            case R.id.bt_professional:
                break;
            case R.id.bt_postYourRequirement:

                if (AppState.getInstance().getUserId() != null && AppState.getInstance().getUserId().length() > 0) {
                    Intent intent = new Intent(getContext(), PostYourRequirementsActivity.class);
                    startActivity(intent);

                } else {
                    new Utility().moveToActivity(getContext(), LoginActivity.class, null);
                }

                break;
            case R.id.tv_showAllRecentlyAddedProperty:

                if (mRecentlyAddedPropertyList != null) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("recent_properties", (ArrayList<? extends Parcelable>) mRecentlyAddedPropertyList);
                    new Utility().moveToActivity(getActivity(), PropertyListActivity.class, bundle);
                }

                break;
            case R.id.tv_showAllFeaturedProperty:


                if (mFeaturedPropertyList != null) {
                    Bundle bundle = new Bundle();
                    if (selectedType == 1)
                    {
                        bundle.putInt("type", 1);
                        new Utility().moveToActivity(getActivity(), PropertyListActivity.class, bundle);
//                        bundle.putParcelableArrayList("featured_properties", (ArrayList<? extends Parcelable>) mFeaturedPropertyRentList);
                    } else {
                        bundle.putInt("type", 0);
                        new Utility().moveToActivity(getActivity(), PropertyListActivity.class, bundle);
//                        bundle.putParcelableArrayList("featured_properties", (ArrayList<? extends Parcelable>) mFeaturedPropertyList);
                    }

                    new Utility().moveToActivity(getActivity(), PropertyListActivity.class, bundle);
                }

                break;

            case R.id.bt_rentProperty:

                selectedType = 1;

                setFeaturedPropertyData();

                btSaleProperty.setTextColor(Color.BLACK);
                btSaleProperty.setBackground(getResources().getDrawable(R.drawable.filiter_border));


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                {
                    btRentProperty.setTextColor(getResources().getColor(R.color.white, null));
                    btRentProperty.setBackground(getResources().getDrawable(R.drawable.home_button_background, null));
                } else {
                    btRentProperty.setTextColor(getResources().getColor(R.color.white));
                    btRentProperty.setBackground(getResources().getDrawable(R.drawable.home_button_background));
                }

                break;

            case R.id.bt_saleProperty:

                selectedType = 2;

                setRecycleView();

                btRentProperty.setTextColor(Color.BLACK);
                btRentProperty.setBackground(getResources().getDrawable(R.drawable.filiter_border));

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    btSaleProperty.setTextColor(getResources().getColor(R.color.white, null));
                    btSaleProperty.setBackground(getResources().getDrawable(R.drawable.home_button_background, null));
                } else {
                    btSaleProperty.setTextColor(getResources().getColor(R.color.white));
                    btSaleProperty.setBackground(getResources().getDrawable(R.drawable.home_button_background));
                }


                break;

            case R.id.ll_buy:

                Bundle bundle = new Bundle();
                bundle.putInt("type", 1);
                new Utility().moveToActivity(getActivity(), PropertyListActivity.class, bundle);
                break;

            case R.id.ll_rent:
                Bundle bundle1 = new Bundle();
                bundle1.putInt("type", 0);
                new Utility().moveToActivity(getActivity(), PropertyListActivity.class, bundle1);
                break;
        }
    }

    @OnClick(R.id.bt_professional)
    public void onViewClicked() {
        Intent intent = new Intent(getContext(), ProfessionalsActivity.class);
        startActivity(intent);
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

                if (AppState.getInstance().getUserId() != null) {
                    homeRequest.setUserId(Integer.parseInt(AppState.getInstance().getUserId()));

                }
                homeRequest.setLanguage(new PreferenceManager().getValue(getActivity(), PrefKey.CURRENT_DATA));
                Response<HomeResponse> response = WebServiceManager.getInstance().homeData(homeRequest);

                if (response != null && response.isSuccessful() && response.raw().code() == 200) {

                    homeResponse = response.body();

                    if (homeResponse != null) {

                        if (homeResponse.getRes() == 1 && homeResponse.getIsSuccess() == 1) {

                            mRecentlyAddedPropertyList = homeResponse.getRecentProperty();
                            mFeaturedPropertyList = homeResponse.getFeaturedProperty();
                            mFeaturedPropertyRentList = homeResponse.getFeaturedPropertyRent();

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

                homeViewPagerAdapter = new HomeViewPagerAdapter(getActivity(), homeResponse.getSliderImages());
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

                int rentCount = homeResponse.getTotalRentItemsCount();
                int buyCount = homeResponse.getTotalBuyItemsCount();

                if (rentCount == 1) {
                    tvRentPropertyCount.setText(getResources().getString(R.string.itemone));
                } else {
                    tvRentPropertyCount.setText(String.valueOf(rentCount +" "+ getResources().getString(R.string.items)));
                }

                if (buyCount == 1) {
                    tvBuyPropertyCount.setText(getResources().getString(R.string.itemone));
                } else {
                    tvBuyPropertyCount.setText(String.valueOf(buyCount +" "+getResources().getString(R.string.items)));
                }

                if (homeResponse.getFeaturedProperty() != null && homeResponse.getFeaturedProperty().size() > 0) {
                    llFeaturedPropertyHolder.setVisibility(View.VISIBLE);
                } else {
                    llFeaturedPropertyHolder.setVisibility(View.GONE);
                }

                if (homeResponse.getRecentProperty() != null && homeResponse.getRecentProperty().size() > 0) {
                    llRecentlyAddedPropertyHolder.setVisibility(View.VISIBLE);
                } else {
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

            if (homeViewPagerAdapter != null) {

                if (homeViewPager.getCurrentItem() == homeViewPagerAdapter.getCount() - 1) {
                    currentPage = 0;
                }

                homeViewPager.setCurrentItem(currentPage++, true);
            }

        }
    };

    @Override
    public void onStop() {
        super.onStop();

        if (handler != null) {
            handler.removeCallbacks(update);
        }

    }
}
