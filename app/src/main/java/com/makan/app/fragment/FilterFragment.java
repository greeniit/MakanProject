package com.makan.app.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.github.guilhe.rangeseekbar.SeekBarRangedView;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.makan.R;
import com.makan.app.activity.MapActivity;
import com.makan.app.activity.PropertyListActivity;
import com.makan.app.core.Codes;
import com.makan.app.util.Utility;
import com.makan.app.web.WebServiceManager;
import com.makan.app.web.pojo.FilterSearchRequest;
import com.makan.app.web.pojo.GetCategoryResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

import static com.makan.app.activity.FilterActivity.PLACE_AUTOCOMPLETE_REQUEST_CODE;

public class FilterFragment extends BaseFragment implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    private View rootView;
    private Toolbar toolbar;
    private SeekBarRangedView rsPrice, rsArea;
    private TextView tvPriceMin, tvPriceMax, tvAreaMin, tvAreaMax;
    private LinearLayout llBuy, llRent;
    private TextView tvBuy, tvRent;
    private View viewBorderBuy, viewBorderRent;
    private Button btnSearch, btnMapSearch;
    private EditText etLocationSearch;
    private Place selectedPlace = null;
    private LinearLayout llPropertyTypeHolder;
    private RelativeLayout rlProgressHolder;
    private List<GetCategoryResponse.MainCategory> mainCategories = new ArrayList<>();
    private int priceMinBuy, priceMaxBuy, areaMinBuy, areaMaxBuy;

    private FilterSearchRequest filterSearchRequest = new FilterSearchRequest();
    private int selectedType = 1;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_filter, container, false);
        initialiseComponents(rootView);
        setToolBar();
        setListeners();

        new CategoryDataFetchTask().execute();

        return rootView;
    }


    private void initialiseComponents(View view) {
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        rsPrice = (SeekBarRangedView) view.findViewById(R.id.rsPrice);
        rsArea = (SeekBarRangedView) view.findViewById(R.id.rsArea);
        tvPriceMin = (TextView) view.findViewById(R.id.tvPriceMin);
        tvPriceMax = (TextView) view.findViewById(R.id.tvPriceMax);
        tvAreaMin = (TextView) view.findViewById(R.id.tvAreaMin);
        tvAreaMax = (TextView) view.findViewById(R.id.tvAreaMax);
        llBuy = (LinearLayout) view.findViewById(R.id.llBuy);
        llRent = (LinearLayout) view.findViewById(R.id.llRent);
        tvBuy = (TextView) view.findViewById(R.id.tvBuy);
        tvRent = (TextView) view.findViewById(R.id.tvRent);
        viewBorderBuy = (View) view.findViewById(R.id.viewBorderBuy);
        viewBorderRent = (View) view.findViewById(R.id.viewBorderRent);
        btnSearch = (Button) view.findViewById(R.id.btnSearch);
        btnMapSearch = (Button) view.findViewById(R.id.btnMapSearch);
        etLocationSearch = (EditText) view.findViewById(R.id.etLocationSearch);
        llPropertyTypeHolder = (LinearLayout) view.findViewById(R.id.llPropertyTypeHolder);
        rlProgressHolder = (RelativeLayout) view.findViewById(R.id.rlProgressHolder);
    }

    protected void setToolBar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {

            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Filter");


        }

    }

    private void setListeners() {
        llBuy.setOnClickListener(this);
        llRent.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        btnMapSearch.setOnClickListener(this);
        /*rsPrice.setNotifyWhileDragging(true);
        rsArea.setNotifyWhileDragging(true);

        rsPrice.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Object minValue, Object maxValue) {

                tvPriceMin.setText("OMR " + minValue.toString());
                tvPriceMax.setText("OMR " + maxValue.toString());
            }
        });

        rsArea.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Object minValue, Object maxValue) {

                tvAreaMin.setText(minValue.toString() + " Sqft");
                tvAreaMax.setText(maxValue.toString() + " Sqft");
            }
        });*/

        rsPrice.setOnSeekBarRangedChangeListener(

                new SeekBarRangedView.OnSeekBarRangedChangeListener() {
                    @Override
                    public void onChanged(SeekBarRangedView view, float minValue, float maxValue) {

                    }

                    @Override
                    public void onChanging(SeekBarRangedView view, float minValue, float maxValue) {

                        priceMinBuy = (int) minValue;
                        priceMaxBuy = (int) maxValue;

                        tvPriceMin.setText("OMR " + String.valueOf(priceMinBuy));
                        tvPriceMax.setText("OMR " + String.valueOf(priceMaxBuy));
                    }
                }
        );

        rsArea.setOnSeekBarRangedChangeListener(

                new SeekBarRangedView.OnSeekBarRangedChangeListener() {
                    @Override
                    public void onChanged(SeekBarRangedView view, float minValue, float maxValue) {

                    }

                    @Override
                    public void onChanging(SeekBarRangedView view, float minValue, float maxValue) {

                        areaMinBuy = (int) minValue;
                        areaMaxBuy = (int) maxValue;

                        tvAreaMin.setText(String.valueOf(areaMinBuy) + " Sqft");
                        tvAreaMax.setText(String.valueOf(areaMaxBuy) + " Sqft");
                    }
                }
        );

        etLocationSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    Intent intent =
                            new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                                    .build(getActivity());
                    startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
                } catch (GooglePlayServicesRepairableException e) {
                    // TODO: Handle the error.
                } catch (GooglePlayServicesNotAvailableException e) {
                    // TODO: Handle the error.
                }

            }
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        boolean isMainTag = !buttonView.getTag().toString().contains("#");

        if (isMainTag) {

            String categoryId = buttonView.getTag().toString().replace("main-", "");

            LinearLayout linearLayout = rootView.findViewWithTag(categoryId);

            if (linearLayout != null) {

                linearLayout.setVisibility(isChecked ? View.VISIBLE : View.GONE);

                if (isChecked) {

                    Switch allPropertiesSwitch = rootView.findViewWithTag("main-0");
                    allPropertiesSwitch.setOnCheckedChangeListener(null);
                    allPropertiesSwitch.setChecked(false);
                    allPropertiesSwitch.setOnCheckedChangeListener(this);


                    LinearLayout subLinearLayout = rootView.findViewWithTag("sublayout-" + categoryId);

                    if (subLinearLayout != null) {
                        subLinearLayout.setVisibility(View.VISIBLE);
                    }

                    for (GetCategoryResponse.MainCategory mainCategory : mainCategories) {

                        if (mainCategory.getMainCategoryId().equals(categoryId)) {

                            if (mainCategory.getSubCategoryList() != null && mainCategory.getSubCategoryList().size() > 0) {

                                for (GetCategoryResponse.SubCategoryList subCategory : mainCategory.getSubCategoryList()) {

                                    Switch subPropertyTypeSwitch = rootView.findViewWithTag("main-" + mainCategory.getMainCategoryId() + "#" + "sub-" + subCategory.getSubCategoryId());

                                    if (subPropertyTypeSwitch != null) {
                                        subPropertyTypeSwitch.setOnCheckedChangeListener(null);
                                        subPropertyTypeSwitch.setChecked(false);
                                        subPropertyTypeSwitch.setOnCheckedChangeListener(this);
                                    }
                                }
                            }

                            break;
                        }
                    }

                } else {

                    LinearLayout subLinearLayout = rootView.findViewWithTag("sublayout-" + categoryId);

                    if (subLinearLayout != null) {
                        subLinearLayout.setVisibility(View.GONE);
                    }
                }

            } else {

                if (categoryId.equals("0")) {

                    for (GetCategoryResponse.MainCategory mainCategory : mainCategories) {

                        Switch mainPropertyTypeSwitch = rootView.findViewWithTag("main-" + mainCategory.getMainCategoryId());

                        if (mainPropertyTypeSwitch != null) {
                            mainPropertyTypeSwitch.setOnCheckedChangeListener(null);
                            mainPropertyTypeSwitch.setChecked(false);
                            mainPropertyTypeSwitch.setOnCheckedChangeListener(this);

                            LinearLayout subLinearLayout = rootView.findViewWithTag("sublayout-" + mainCategory.getMainCategoryId());

                            if (subLinearLayout != null) {
                                subLinearLayout.setVisibility(View.GONE);
                            }

                            if (mainCategory.getSubCategoryList() != null && mainCategory.getSubCategoryList().size() > 0) {

                                for (GetCategoryResponse.SubCategoryList subCategory : mainCategory.getSubCategoryList()) {

                                    Switch subPropertyTypeSwitch = rootView.findViewWithTag("main-" + mainCategory.getMainCategoryId() + "#" + "sub-" + subCategory.getSubCategoryId());

                                    if (subPropertyTypeSwitch != null) {
                                        subPropertyTypeSwitch.setOnCheckedChangeListener(null);
                                        subPropertyTypeSwitch.setChecked(false);
                                        subPropertyTypeSwitch.setOnCheckedChangeListener(this);
                                    }
                                }
                            }


                        }
                    }

                }

            }


        } else {

            if (isChecked) {

                Switch mainCategorySwitch = rootView.findViewWithTag(buttonView.getTag().toString().split("#")[0]);
                mainCategorySwitch.setOnCheckedChangeListener(null);
                mainCategorySwitch.setChecked(false);
                mainCategorySwitch.setOnCheckedChangeListener(this);
            }


        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.llBuy:

                selectedType = 1;
                viewBorderRent.setBackgroundColor(Color.TRANSPARENT);
                tvRent.setTextColor(Color.BLACK);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    tvBuy.setTextColor(getResources().getColor(R.color.colorPrimaryDark, null));
                    viewBorderBuy.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark, null));
                } else {
                    tvBuy.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    viewBorderBuy.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                }

                tvPriceMin.setText("OMR " + String.valueOf(priceMinBuy));
                tvPriceMax.setText("OMR " + String.valueOf(priceMaxBuy));
                tvAreaMin.setText(String.valueOf(areaMinBuy) + " Sqft");
                tvAreaMax.setText(String.valueOf(areaMaxBuy) + " Sqft");

                break;

            case R.id.llRent:

                selectedType = 2;
                tvBuy.setTextColor(Color.BLACK);
                viewBorderBuy.setBackgroundColor(Color.TRANSPARENT);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    tvRent.setTextColor(getResources().getColor(R.color.colorPrimaryDark, null));
                    viewBorderRent.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark, null));
                } else {
                    tvRent.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    viewBorderRent.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                }


                break;

            case R.id.btnSearch:

                new RequestCreatorTask(true).execute();
                break;

            case R.id.btnMapSearch:
                new RequestCreatorTask(false).execute();
                break;
        }

    }

    public void setSelectedLocation(Place place) {

        if (place != null) {

            selectedPlace = place;
            etLocationSearch.setText(" " + selectedPlace.getName());
            filterSearchRequest.setLat(String.valueOf(place.getLatLng().latitude));
            filterSearchRequest.setLong(String.valueOf(place.getLatLng().longitude));
        }

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

                if (categoryResponse.getMainCategory() != null) {

                    mainCategories.clear();

                    GetCategoryResponse.MainCategory allPropertyCategory = new GetCategoryResponse.MainCategory();
                    allPropertyCategory.setMainCategoryName("All properties");
                    allPropertyCategory.setMainCategoryId("0");
                    addCategoryAndSubCategory(allPropertyCategory);

                    for (GetCategoryResponse.MainCategory mainCategory : categoryResponse.getMainCategory()) {

                        mainCategories.add(mainCategory);
                        addCategoryAndSubCategory(mainCategory);
                    }
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

    private void addCategoryAndSubCategory(GetCategoryResponse.MainCategory mainCategory) {

        CardView cardView = new CardView(getActivity());
        LinearLayout.LayoutParams cardViewParams = new LinearLayout.LayoutParams(
                RelativeLayout.LayoutParams.FILL_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        cardView.setLayoutParams(cardViewParams);

        LinearLayout linearLayout = new LinearLayout(getActivity());
        linearLayout.setBackgroundColor(Color.WHITE);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        CardView.LayoutParams linearLayoutParams = new CardView.LayoutParams(
                RelativeLayout.LayoutParams.FILL_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        linearLayout.setLayoutParams(linearLayoutParams);

        RelativeLayout relativeLayout = new RelativeLayout(getActivity());
        relativeLayout.setBackgroundColor(Color.WHITE);
        relativeLayout.setPadding(16, 16, 16, 16);
        LinearLayout.LayoutParams relativeLayoutParams = new LinearLayout.LayoutParams(
                RelativeLayout.LayoutParams.FILL_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        relativeLayout.setLayoutParams(relativeLayoutParams);

        TextView textView = new TextView(getActivity());
        textView.setText(mainCategory.getMainCategoryName());
        textView.setTextColor(Color.BLACK);
        textView.setTypeface(null, Typeface.BOLD);
        RelativeLayout.LayoutParams textViewLayoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.FILL_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        textViewLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        textView.setLayoutParams(relativeLayoutParams);


        Switch statusSwitch = new Switch(getActivity());

        if (mainCategory.getMainCategoryId().equals("0")) {
            statusSwitch.setChecked(true);
        } else {
            statusSwitch.setChecked(false);
        }

        statusSwitch.setTag("main-" + mainCategory.getMainCategoryId());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            statusSwitch.setShowText(false);
        }

        RelativeLayout.LayoutParams statusSwitchLayout = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.FILL_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        statusSwitchLayout.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        statusSwitchLayout.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        statusSwitch.setLayoutParams(statusSwitchLayout);
        statusSwitch.setOnCheckedChangeListener(this);


        relativeLayout.addView(textView);
        relativeLayout.addView(statusSwitch);
        linearLayout.addView(relativeLayout);

        if (mainCategory.getSubCategoryList() != null && mainCategory.getSubCategoryList().size() > 0) {

            LinearLayout subCategoryHolder = new LinearLayout(getActivity());
            subCategoryHolder.setBackgroundColor(Color.WHITE);
            subCategoryHolder.setTag(mainCategory.getMainCategoryId());
            subCategoryHolder.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams subCategoryHolderLinearLayoutParams = new LinearLayout.LayoutParams(
                    RelativeLayout.LayoutParams.FILL_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            subCategoryHolder.setLayoutParams(subCategoryHolderLinearLayoutParams);

            for (GetCategoryResponse.SubCategoryList subcategory : mainCategory.getSubCategoryList()) {

                LinearLayout subLinearLayout = new LinearLayout(getActivity());
                subLinearLayout.setBackgroundColor(Color.WHITE);
                subLinearLayout.setOrientation(LinearLayout.VERTICAL);
                subLinearLayout.setTag("sublayout-" + mainCategory.getMainCategoryId());
                LinearLayout.LayoutParams subLinearLayoutParams = new LinearLayout.LayoutParams(
                        RelativeLayout.LayoutParams.FILL_PARENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
                linearLayout.setLayoutParams(linearLayoutParams);

                RelativeLayout subRelativeLayout = new RelativeLayout(getActivity());
                subRelativeLayout.setBackgroundColor(Color.WHITE);
                subRelativeLayout.setPadding(16, 16, 16, 16);
                LinearLayout.LayoutParams subRelativeLayoutParams = new LinearLayout.LayoutParams(
                        RelativeLayout.LayoutParams.FILL_PARENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
                subRelativeLayout.setLayoutParams(subRelativeLayoutParams);

                TextView subTextView = new TextView(getActivity());
                subTextView.setText("-" + subcategory.getSubCategoryName());
                subTextView.setTextColor(Color.BLACK);
                subTextView.setPadding(5, 0, 0, 0);
                RelativeLayout.LayoutParams subTextViewLayoutParams = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.FILL_PARENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
                subRelativeLayout.setPadding(16, 16, 16, 16);
                subTextViewLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
                subTextView.setLayoutParams(subTextViewLayoutParams);


                Switch subStatusSwitch = new Switch(getActivity());
                subStatusSwitch.setChecked(false);
                subStatusSwitch.setTag("main-" + mainCategory.getMainCategoryId() + "#" + "sub-" + subcategory.getSubCategoryId());
                subStatusSwitch.setOnCheckedChangeListener(this);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    subStatusSwitch.setShowText(false);
                }

                RelativeLayout.LayoutParams subStatusSwitchLayoutParams = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.FILL_PARENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
                subStatusSwitchLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
                subStatusSwitchLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
                subStatusSwitch.setLayoutParams(subStatusSwitchLayoutParams);


                subRelativeLayout.addView(subTextView);
                subRelativeLayout.addView(subStatusSwitch);

                subLinearLayout.addView(subRelativeLayout);

                subLinearLayout.setVisibility(View.GONE);
                subCategoryHolder.addView(subLinearLayout);

            }

            linearLayout.addView(subCategoryHolder);
        }

        cardView.addView(linearLayout);
        llPropertyTypeHolder.addView(cardView);


        LinearLayout seperator = new LinearLayout(getActivity());
        seperator.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        seperator.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams seperatorLinearLayoutParams = new LinearLayout.LayoutParams(
                RelativeLayout.LayoutParams.FILL_PARENT,
                1);
        seperator.setLayoutParams(seperatorLinearLayoutParams);

        llPropertyTypeHolder.addView(seperator);

    }

    private class RequestCreatorTask extends AsyncTask<Void,Void,Bundle>{

        private boolean moveToPropertyList=false;

        RequestCreatorTask(boolean moveToPropertyList){

            this.moveToPropertyList=moveToPropertyList;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog();
        }

        @Override
        protected Bundle doInBackground(Void... voids) {

            return getBundle();
        }

        @Override
        protected void onPostExecute(Bundle bundle) {
            super.onPostExecute(bundle);
            dismissProgressDialog();

            if(moveToPropertyList){
                new Utility().moveToActivity(getActivity(), PropertyListActivity.class, bundle);
            }else{
                new Utility().moveToActivity(getActivity(), MapActivity.class, bundle);
            }

        }
    }

    private Bundle getBundle() {

        Bundle bundle = new Bundle();

        filterSearchRequest.setSelectedType(selectedType);
        filterSearchRequest.setMinprice((int) rsPrice.getSelectedMinValue());
        filterSearchRequest.setMaxprice((int) rsPrice.getSelectedMaxValue());
        filterSearchRequest.setMinarea((int) rsArea.getSelectedMinValue());
        filterSearchRequest.setMaxarea((int) rsArea.getSelectedMaxValue());

        ArrayList<Integer> subCategoryIds = new ArrayList<>();

        Switch allPropertySwitch = rootView.findViewWithTag("main-0");

        if (allPropertySwitch.isChecked()) {

            for (GetCategoryResponse.MainCategory mainCategory : mainCategories) {

                if (mainCategory.getSubCategoryList() != null && mainCategory.getSubCategoryList().size() > 0) {

                    for (GetCategoryResponse.SubCategoryList subCategory : mainCategory.getSubCategoryList()) {

                        if (!subCategoryIds.contains(Integer.parseInt(subCategory.getSubCategoryId()))) {

                            subCategoryIds.add(Integer.parseInt(subCategory.getSubCategoryId()));
                        }

                    }
                }
            }

        } else {

            for (GetCategoryResponse.MainCategory mainCategory : mainCategories) {

                Switch mainPropertyTypeSwitch = rootView.findViewWithTag("main-" + mainCategory.getMainCategoryId());

                if (mainPropertyTypeSwitch != null) {

                    if (!mainPropertyTypeSwitch.isChecked()) {

                        if (mainCategory.getSubCategoryList() != null && mainCategory.getSubCategoryList().size() > 0) {

                            for (GetCategoryResponse.SubCategoryList subCategory : mainCategory.getSubCategoryList()) {

                                Switch subPropertyTypeSwitch = rootView.findViewWithTag("main-" + mainCategory.getMainCategoryId() + "#" + "sub-" + subCategory.getSubCategoryId());

                                if (subPropertyTypeSwitch.isChecked()) {

                                    if (!subCategoryIds.contains(Integer.parseInt(subCategory.getSubCategoryId()))) {

                                        subCategoryIds.add(Integer.parseInt(subCategory.getSubCategoryId()));
                                    }

                                }
                            }
                        }

                    } else {

                        if (mainCategory.getSubCategoryList() != null && mainCategory.getSubCategoryList().size() > 0) {

                            for (GetCategoryResponse.SubCategoryList subCategory : mainCategory.getSubCategoryList()) {

                                if (!subCategoryIds.contains(Integer.parseInt(subCategory.getSubCategoryId()))) {

                                    subCategoryIds.add(Integer.parseInt(subCategory.getSubCategoryId()));
                                }


                            }
                        }
                    }


                }
            }
        }

        filterSearchRequest.setSubCategoryId(subCategoryIds);
        bundle.putString("search_properties","");
        bundle.putParcelable("filter_request",filterSearchRequest);

        return bundle;
    }

}
