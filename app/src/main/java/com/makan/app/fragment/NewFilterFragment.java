package com.makan.app.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.github.guilhe.rangeseekbar.SeekBarRangedView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.makan.R;
import com.makan.app.activity.LoginActivity;
import com.makan.app.activity.MapActivity;
import com.makan.app.activity.PostYourRequirementsActivity;
import com.makan.app.activity.PropertyListActivity;
import com.makan.app.adapter.FilterCategoryAdapter;
import com.makan.app.adapter.FilterSubCategoryAdapter;
import com.makan.app.app.AppState;
import com.makan.app.callback.FilterCategoryCallback;
import com.makan.app.core.Codes;
import com.makan.app.model.Property;
import com.makan.app.preference.PrefKey;
import com.makan.app.preference.PreferenceManager;
import com.makan.app.util.Utility;
import com.makan.app.web.WebServiceManager;
import com.makan.app.web.pojo.FillterCategoryResponse;
import com.makan.app.web.pojo.FilterCategoryRequest;
import com.makan.app.web.pojo.FilterSearchRequest;
import com.makan.app.web.pojo.GetCategoryResponse;
import com.makan.app.web.pojo.NewFilterRequest;
import com.makan.app.web.pojo.NewFilterResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Response;

public class NewFilterFragment extends BaseFragment implements CompoundButton.OnCheckedChangeListener, View.OnClickListener, FilterCategoryCallback {


//

    Unbinder unbinder;
    View rootView;
    @BindView(R.id.llYearly)
    LinearLayout llYearly;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tvBuy)
    TextView tvBuy;
    @BindView(R.id.llBuy)
    LinearLayout llBuy;
    @BindView(R.id.tvRent)
    TextView tvRent;
    @BindView(R.id.llRent)
    LinearLayout llRent;
    @BindView(R.id.etLocationSearch)
    EditText etLocationSearch;
    @BindView(R.id.ivFilter)
    ImageView ivFilter;
    @BindView(R.id.rlSearch)
    RelativeLayout rlSearch;
    @BindView(R.id.tvSelectLocation)
    TextView tvSelectLocation;
    @BindView(R.id.spSelectLocation)
    Spinner spSelectLocation;
    @BindView(R.id.tvSelectArea)
    TextView tvSelectArea;
    @BindView(R.id.spSelectArea)
    Spinner spSelectArea;
    @BindView(R.id.tvResidential)
    TextView tvResidential;
    @BindView(R.id.llproperty)
    LinearLayout llproperty;
    @BindView(R.id.tvCommercial)
    TextView tvCommercial;
    @BindView(R.id.llCommercial)
    LinearLayout llCommercial;
    @BindView(R.id.rvPropertyType)
    RecyclerView rvPropertyType;
    @BindView(R.id.rvSubCat)
    RecyclerView rvSubCat;
    @BindView(R.id.tvYearly)
    TextView tvYearly;
    @BindView(R.id.tvMonthly)
    TextView tvMonthly;
    @BindView(R.id.llMonthly)
    LinearLayout llMonthly;
    @BindView(R.id.spPriceMin)
    Spinner spPriceMin;
    @BindView(R.id.spPriceMax)
    Spinner spPriceMax;
    @BindView(R.id.tvPriceMin)
    TextView tvPriceMin;
    @BindView(R.id.tvPriceMax)
    TextView tvPriceMax;
    @BindView(R.id.rsPrice)
    SeekBarRangedView rsPrice;
    @BindView(R.id.spAreaMin)
    Spinner spAreaMin;
    @BindView(R.id.spAreaMax)
    Spinner spAreaMax;
    @BindView(R.id.tvAreaMin)
    TextView tvAreaMin;
    @BindView(R.id.tvAreaMax)
    TextView tvAreaMax;
    @BindView(R.id.rsArea)
    SeekBarRangedView rsArea;
    @BindView(R.id.btBedStudio)
    Button btBedStudio;
    @BindView(R.id.btBed1)
    Button btBed1;
    @BindView(R.id.btBed2)
    Button btBed2;
    @BindView(R.id.btBed3)
    Button btBed3;
    @BindView(R.id.btBed4)
    Button btBed4;
    @BindView(R.id.btBed5)
    Button btBed5;
    @BindView(R.id.btBed6)
    Button btBed6;
    @BindView(R.id.llBedRoomCountHolder)
    LinearLayout llBedRoomCountHolder;
    @BindView(R.id.btBath1)
    Button btBath1;
    @BindView(R.id.btBath2)
    Button btBath2;
    @BindView(R.id.btBath3)
    Button btBath3;
    @BindView(R.id.btBath4)
    Button btBath4;
    @BindView(R.id.btBath5)
    Button btBath5;
    @BindView(R.id.btBath6)
    Button btBath6;
    @BindView(R.id.llPropertyTypeHolder)
    LinearLayout llPropertyTypeHolder;
    @BindView(R.id.btnSearch)
    Button btnSearch;
    @BindView(R.id.btnMapSearch)
    Button btnMapSearch;
    @BindView(R.id.rvPropertyTypeDetails)
    LinearLayout rvPropertyTypeDetails;
    @BindView(R.id.tvRFrentFreq)
    TextView tvRFrentFreq;
    @BindView(R.id.llRFrentFreq)
    LinearLayout llRFrentFreq;

    @BindView(R.id.tv_bedroomscounts)
    TextView tv_bedroomscounts;

    @BindView(R.id.tv_bathroomscounts)
    TextView tv_bathroomscounts;


    private List<GetCategoryResponse.MainCategory> mainCategories = new ArrayList<>();
    private Place selectedPlace = null;
    private FilterSearchRequest filterSearchRequest = new FilterSearchRequest();
    private int selectedType = 0;
    private int selectedPropertyType = 1;
    private String selectedRentFrequencyType = "";
    private String selectedBedCount = "0";
    private int selectedBathroomCount = 0;
    private FilterCategoryAdapter filterCategoryAdapter;
    private FilterSubCategoryAdapter filterSubCategoryAdapter;
    private PreferenceManager preferenceManager;
    public static int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    NewFilterRequest newFilterRequest = new NewFilterRequest();
    private List<FillterCategoryResponse.MainCategory> mainCategories1 = new ArrayList<>();
    private List<Property> mainCategories2 = new ArrayList<>();
    private ArrayList<String> data;
    private String LocationName;
    private String priceMin,priceMax;
    private String areaMin,areaMax;

    private String a;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_filter, container, false);
        ButterKnife.bind(this, rootView);
        Bundle arguments = getArguments();
        if (arguments != null) {
            setToolBar();
        } else {
            hideToolbar();
        }

        setData();
//        setRecycleview();
        initData();
        return rootView;
    }

    private void setData() {

        new getFillterCategory().execute();
    }

    private void setRecycleview() {

        filterCategoryAdapter = new FilterCategoryAdapter(getContext(), mainCategories1, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvPropertyType.setLayoutManager(linearLayoutManager);
        rvPropertyType.setAdapter(filterCategoryAdapter);


    }

    private void hideToolbar() {

        toolbar.setVisibility(View.GONE);

    }

    private void initData() {

        data = new ArrayList<>();
        preferenceManager = new PreferenceManager();

        Places.initialize(getContext(), "AIzaSyCqncUxKJZVSyVoGKAe3O781jBL9Lyi_sw");

        PlacesClient placesClient = Places.createClient(getContext());

        List<String> demoList = new ArrayList<String>();
        demoList.add("Select");
        demoList.add("Demo Data 1");
        demoList.add("Demo Data 2");
        demoList.add("Demo Data 3");
        demoList.add("Demo Data 4");
        demoList.add("Demo Data 5");
        demoList.add("Demo Data 6");
        demoList.add("Demo Data 7");
        demoList.add("Demo Data 8");
        demoList.add("Demo Data 9");
        demoList.add("Demo Data 10");
        demoList.add("Demo Data 11");
        demoList.add("Demo Data 12");
        demoList.add("Demo Data 13");
        demoList.add("Demo Data 14");
        demoList.add("Demo Data 15");

        String[] priceMinTypeList = new String[]{"Min", "Below 5000", "5000", "6000", "8000", "9000", "10000", "20000", "30000", "40000", "50000", "60000", "70000", "80000", "90000"};
        ArrayAdapter<String> dataAdapterPriceMin = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, priceMinTypeList);
        dataAdapterPriceMin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPriceMin.setAdapter(dataAdapterPriceMin);
        spPriceMin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                priceMin = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        String[] priceMaxTypeList = new String[]{"Max", "5000", "6000", "7000", "8000", "9000", "10000", "20000", "30000", "40000", "50000", "60000", "70000", "80000", "90000", "Above 90000"};
        ArrayAdapter<String> dataAdapterPriceMax = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, priceMaxTypeList);
        dataAdapterPriceMax.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPriceMax.setAdapter(dataAdapterPriceMax);
        spPriceMax.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                priceMax = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        String[] areaMinTypeList = new String[]{"Min", "Below 500", "600", "700", "800", "900", "1000", "1200", "1400", "1500", "2000", "3000", "4000", "5000", "6000", "7000"};
        ArrayAdapter<String> dataAdapterAreaMin = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, areaMinTypeList);
        dataAdapterAreaMin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spAreaMin.setAdapter(dataAdapterAreaMin);
        spAreaMin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                areaMin = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        String[] areaMaxTypeList = new String[]{"Max", "500", "600", "700", "800", "900", "1000", "1200", "1400", "1500", "2000", "3000", "4000", "5000", "6000", "7000", "Above 7000"};
        ArrayAdapter<String> dataAdapterAreaMax = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, areaMaxTypeList);
        dataAdapterAreaMax.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spAreaMax.setAdapter(dataAdapterAreaMax);
        spAreaMax.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                areaMax = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, demoList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSelectArea.setAdapter(dataAdapter);
        spSelectLocation.setAdapter(dataAdapter);


        etLocationSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                try {
//                    Intent intent =
//                            new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
//                                    .build(getActivity());
//                    startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
//                } catch (GooglePlayServicesRepairableException e) {
//                    // TODO: Handle the error.
//                } catch (GooglePlayServicesNotAvailableException e) {
//                    // TODO: Handle the error.
//                }

                // Set the fields to specify which types of place data to return.
                List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME);

                // Start the autocomplete intent.
                Intent intent = new Autocomplete.IntentBuilder(
                        AutocompleteActivityMode.OVERLAY, fields)
                        .build(getContext());
                startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);


            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setToolBar() {


        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {

            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.filter));


        }

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnSearch:
                break;

            case R.id.btnMapSearch:

                break;
        }

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

    }

    public void setSelectedLocation(Place place) {

        if (place != null) {

            selectedPlace = place;
            etLocationSearch.setText("" + selectedPlace.getName());
            LocationName = selectedPlace.getName()+"";
            newFilterRequest.setLat(String.valueOf(place.getLatLng().latitude));
            newFilterRequest.setLong(String.valueOf(place.getLatLng().longitude));
        }

    }


    @OnClick({R.id.llBuy, R.id.llRent, R.id.btnSearch, R.id.btnMapSearch, R.id.llproperty, R.id.llCommercial, R.id.llYearly, R.id.llMonthly, R.id.btBedStudio, R.id.btBed1, R.id.btBed2, R.id.btBed3, R.id.btBed4, R.id.btBed5, R.id.btBed6, R.id.btBath1, R.id.btBath2, R.id.btBath3, R.id.btBath4, R.id.btBath5, R.id.btBath6})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.llBuy:
                llRFrentFreq.setVisibility(View.GONE);
                tvRFrentFreq.setVisibility(View.GONE);
                selectedType = 0;
                tvRent.setTextColor(Color.BLACK);
                tvRent.setBackground(getResources().getDrawable(R.drawable.new_border));


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    tvBuy.setTextColor(getResources().getColor(R.color.black, null));
                    tvBuy.setBackground(getResources().getDrawable(R.drawable.new_home_button_background, null));
                } else {
                    tvBuy.setTextColor(getResources().getColor(R.color.black));
                    tvBuy.setBackground(getResources().getDrawable(R.drawable.new_home_button_background));
                }
                break;
            case R.id.llRent:

                selectedType = 1;
                tvBuy.setTextColor(Color.BLACK);
                tvBuy.setBackground(getResources().getDrawable(R.drawable.new_border));
                llRFrentFreq.setVisibility(View.VISIBLE);
                tvRFrentFreq.setVisibility(View.VISIBLE);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    tvRent.setTextColor(getResources().getColor(R.color.black, null));
                    tvRent.setBackground(getResources().getDrawable(R.drawable.new_home_button_background, null));
                } else {
                    tvRent.setTextColor(getResources().getColor(R.color.black));
                    tvBuy.setBackground(getResources().getDrawable(R.drawable.new_home_button_background));
                }

                break;
            case R.id.btnSearch:
                a = "search";
                new getFilterData().execute();

                break;

                case R.id.btnMapSearch:
                a  = "mapsearch";
                new getFilterData().execute();

                break;

            case R.id.llproperty:
                selectedPropertyType = 1;
                tvCommercial.setTextColor(Color.BLACK);
                tvCommercial.setBackground(getResources().getDrawable(R.drawable.new_border));


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    tvResidential.setTextColor(getResources().getColor(R.color.black, null));
                    tvResidential.setBackground(getResources().getDrawable(R.drawable.fillter_button_selection, null));
                } else {
                    tvResidential.setTextColor(getResources().getColor(R.color.black));
                    tvResidential.setBackground(getResources().getDrawable(R.drawable.fillter_button_selection));
                }
                break;
            case R.id.llCommercial:
                selectedPropertyType = 2;
                tvResidential.setTextColor(Color.BLACK);
                tvResidential.setBackground(getResources().getDrawable(R.drawable.new_border));


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    tvCommercial.setTextColor(getResources().getColor(R.color.black, null));
                    tvCommercial.setBackground(getResources().getDrawable(R.drawable.fillter_button_selection, null));
                } else {
                    tvCommercial.setTextColor(getResources().getColor(R.color.black));
                    tvCommercial.setBackground(getResources().getDrawable(R.drawable.fillter_button_selection));
                }
                break;

            case R.id.llYearly:
                selectedRentFrequencyType = "Yearly";
                tvMonthly.setTextColor(Color.BLACK);
                tvMonthly.setBackground(getResources().getDrawable(R.drawable.new_border));


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    tvYearly.setTextColor(getResources().getColor(R.color.black, null));
                    tvYearly.setBackground(getResources().getDrawable(R.drawable.fillter_button_selection, null));
                } else {
                    tvYearly.setTextColor(getResources().getColor(R.color.black));
                    tvYearly.setBackground(getResources().getDrawable(R.drawable.fillter_button_selection));
                }
                break;
            case R.id.llMonthly:

                selectedRentFrequencyType = "Monthly";
                tvYearly.setTextColor(Color.BLACK);
                tvYearly.setBackground(getResources().getDrawable(R.drawable.new_border));


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    tvMonthly.setTextColor(getResources().getColor(R.color.black, null));
                    tvMonthly.setBackground(getResources().getDrawable(R.drawable.fillter_button_selection, null));
                } else {
                    tvMonthly.setTextColor(getResources().getColor(R.color.black));
                    tvMonthly.setBackground(getResources().getDrawable(R.drawable.fillter_button_selection));
                }
                break;

            case R.id.btBedStudio:

                selectedBedCount = "studio";
                tv_bedroomscounts.setText(getResources().getString(R.string.studio) + " " + getResources().getString(R.string.bedroom));
                btBed1.setBackground(getResources().getDrawable(R.drawable.filiter_button_back));
                btBed2.setBackground(getResources().getDrawable(R.drawable.filiter_button_back));
                btBed3.setBackground(getResources().getDrawable(R.drawable.filiter_button_back));
                btBed4.setBackground(getResources().getDrawable(R.drawable.filiter_button_back));
                btBed5.setBackground(getResources().getDrawable(R.drawable.filiter_button_back));
                btBed6.setBackground(getResources().getDrawable(R.drawable.filiter_button_back));

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    btBedStudio.setBackground(getResources().getDrawable(R.drawable.filiter_button_back, null));
                } else {
                    btBedStudio.setBackground(getResources().getDrawable(R.drawable.filiter_button_back));
                }


                break;

            case R.id.btBed1:

                selectedBedCount = "1";
                tv_bedroomscounts.setText(selectedBedCount + " " + getResources().getString(R.string.bedroom));
                btBedStudio.setBackground(getResources().getDrawable(R.drawable.filiter_button_back));
                btBed2.setBackground(getResources().getDrawable(R.drawable.filiter_button_back));
                btBed3.setBackground(getResources().getDrawable(R.drawable.filiter_button_back));
                btBed4.setBackground(getResources().getDrawable(R.drawable.filiter_button_back));
                btBed5.setBackground(getResources().getDrawable(R.drawable.filiter_button_back));
                btBed6.setBackground(getResources().getDrawable(R.drawable.filiter_button_back));

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    btBed1.setBackground(getResources().getDrawable(R.drawable.filiter_button_back, null));
                } else {
                    btBed1.setBackground(getResources().getDrawable(R.drawable.filiter_button_back));
                }


                break;


            case R.id.btBed2:

                selectedBedCount = "2";
                tv_bedroomscounts.setText(selectedBedCount + " " + getResources().getString(R.string.bedroom));
                btBedStudio.setBackground(getResources().getDrawable(R.drawable.filiter_button_back));
                btBed1.setBackground(getResources().getDrawable(R.drawable.filiter_button_back));
                btBed3.setBackground(getResources().getDrawable(R.drawable.filiter_button_back));
                btBed4.setBackground(getResources().getDrawable(R.drawable.filiter_button_back));
                btBed5.setBackground(getResources().getDrawable(R.drawable.filiter_button_back));
                btBed6.setBackground(getResources().getDrawable(R.drawable.filiter_button_back));

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    btBed2.setBackground(getResources().getDrawable(R.drawable.filiter_button_back, null));
                } else {
                    btBed2.setBackground(getResources().getDrawable(R.drawable.filiter_button_back));
                }


                break;

            case R.id.btBed3:

                selectedBedCount = "3";
                tv_bedroomscounts.setText(selectedBedCount + " " + getResources().getString(R.string.bedroom));
                btBedStudio.setBackground(getResources().getDrawable(R.drawable.filiter_button_back));
                btBed1.setBackground(getResources().getDrawable(R.drawable.filiter_button_back));
                btBed2.setBackground(getResources().getDrawable(R.drawable.filiter_button_back));
                btBed4.setBackground(getResources().getDrawable(R.drawable.filiter_button_back));
                btBed5.setBackground(getResources().getDrawable(R.drawable.filiter_button_back));
                btBed6.setBackground(getResources().getDrawable(R.drawable.filiter_button_back));

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    btBed3.setBackground(getResources().getDrawable(R.drawable.filiter_button_back, null));
                } else {
                    btBed3.setBackground(getResources().getDrawable(R.drawable.filiter_button_back));
                }


                break;

            case R.id.btBed4:


                selectedBedCount = "4";
                tv_bedroomscounts.setText(selectedBedCount + " " + getResources().getString(R.string.bedroom));
                btBedStudio.setBackground(getResources().getDrawable(R.drawable.filiter_button_back));
                btBed1.setBackground(getResources().getDrawable(R.drawable.filiter_button_back));
                btBed2.setBackground(getResources().getDrawable(R.drawable.filiter_button_back));
                btBed3.setBackground(getResources().getDrawable(R.drawable.filiter_button_back));
                btBed5.setBackground(getResources().getDrawable(R.drawable.filiter_button_back));
                btBed6.setBackground(getResources().getDrawable(R.drawable.filiter_button_back));

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    btBed4.setBackground(getResources().getDrawable(R.drawable.filiter_button_back, null));
                } else {
                    btBed4.setBackground(getResources().getDrawable(R.drawable.filiter_button_back));
                }


                break;

            case R.id.btBed5:

                selectedBedCount = "5";
                tv_bedroomscounts.setText(selectedBedCount + " " + getResources().getString(R.string.bedroom));
                btBedStudio.setBackground(getResources().getDrawable(R.drawable.filiter_button_back));
                btBed1.setBackground(getResources().getDrawable(R.drawable.filiter_button_back));
                btBed2.setBackground(getResources().getDrawable(R.drawable.filiter_button_back));
                btBed3.setBackground(getResources().getDrawable(R.drawable.filiter_button_back));
                btBed4.setBackground(getResources().getDrawable(R.drawable.filiter_button_back));
                btBed6.setBackground(getResources().getDrawable(R.drawable.filiter_button_back));

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    btBed5.setBackground(getResources().getDrawable(R.drawable.filiter_button_back, null));
                } else {
                    btBed5.setBackground(getResources().getDrawable(R.drawable.filiter_button_back));
                }


                break;

            case R.id.btBed6:

                selectedBedCount = "6";
                tv_bedroomscounts.setText(selectedBedCount + " " + getResources().getString(R.string.bedroom));
                btBedStudio.setBackground(getResources().getDrawable(R.drawable.filiter_button_back));
                btBed1.setBackground(getResources().getDrawable(R.drawable.filiter_button_back));
                btBed2.setBackground(getResources().getDrawable(R.drawable.filiter_button_back));
                btBed3.setBackground(getResources().getDrawable(R.drawable.filiter_button_back));
                btBed4.setBackground(getResources().getDrawable(R.drawable.filiter_button_back));
                btBed5.setBackground(getResources().getDrawable(R.drawable.filiter_button_back));

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    btBed6.setBackground(getResources().getDrawable(R.drawable.filiter_button_back, null));
                } else {
                    btBed6.setBackground(getResources().getDrawable(R.drawable.filiter_button_back));
                }

                break;

            case R.id.btBath1:

                selectedBathroomCount = 1;
                tv_bathroomscounts.setText(selectedBathroomCount + " " + getResources().getString(R.string.bathroom));
                btBath2.setBackground(getResources().getDrawable(R.drawable.filiter_button_back));
                btBath3.setBackground(getResources().getDrawable(R.drawable.filiter_button_back));
                btBath4.setBackground(getResources().getDrawable(R.drawable.filiter_button_back));
                btBath5.setBackground(getResources().getDrawable(R.drawable.filiter_button_back));
                btBath6.setBackground(getResources().getDrawable(R.drawable.filiter_button_back));

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    btBath1.setBackground(getResources().getDrawable(R.drawable.filiter_button_back, null));
                } else {
                    btBath1.setBackground(getResources().getDrawable(R.drawable.filiter_button_back));
                }

                break;

            case R.id.btBath2:


                selectedBathroomCount = 2;
                tv_bathroomscounts.setText(selectedBathroomCount + " " + getResources().getString(R.string.bathroom));
                btBath1.setBackground(getResources().getDrawable(R.drawable.filiter_button_back));
                btBath3.setBackground(getResources().getDrawable(R.drawable.filiter_button_back));
                btBath4.setBackground(getResources().getDrawable(R.drawable.filiter_button_back));
                btBath5.setBackground(getResources().getDrawable(R.drawable.filiter_button_back));
                btBath6.setBackground(getResources().getDrawable(R.drawable.filiter_button_back));

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    btBath2.setBackground(getResources().getDrawable(R.drawable.filiter_button_back, null));
                } else {
                    btBath2.setBackground(getResources().getDrawable(R.drawable.filiter_button_back));
                }


                break;


            case R.id.btBath3:


                selectedBathroomCount = 3;
                tv_bathroomscounts.setText(selectedBathroomCount + " " + getResources().getString(R.string.bathroom));
                btBath1.setBackground(getResources().getDrawable(R.drawable.filiter_button_back));
                btBath2.setBackground(getResources().getDrawable(R.drawable.filiter_button_back));
                btBath4.setBackground(getResources().getDrawable(R.drawable.filiter_button_back));
                btBath5.setBackground(getResources().getDrawable(R.drawable.filiter_button_back));
                btBath6.setBackground(getResources().getDrawable(R.drawable.filiter_button_back));

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    btBath3.setBackground(getResources().getDrawable(R.drawable.filiter_button_back, null));
                } else {
                    btBath3.setBackground(getResources().getDrawable(R.drawable.filiter_button_back));
                }


                break;


            case R.id.btBath4:

                selectedBathroomCount = 4;
                tv_bathroomscounts.setText(selectedBathroomCount + " " + getResources().getString(R.string.bathroom));
                btBath1.setBackground(getResources().getDrawable(R.drawable.filiter_button_back));
                btBath2.setBackground(getResources().getDrawable(R.drawable.filiter_button_back));
                btBath3.setBackground(getResources().getDrawable(R.drawable.filiter_button_back));
                btBath5.setBackground(getResources().getDrawable(R.drawable.filiter_button_back));
                btBath6.setBackground(getResources().getDrawable(R.drawable.filiter_button_back));

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    btBath4.setBackground(getResources().getDrawable(R.drawable.filiter_button_back, null));
                } else {
                    btBath4.setBackground(getResources().getDrawable(R.drawable.filiter_button_back));
                }

                break;

            case R.id.btBath5:


                selectedBathroomCount = 5;
                tv_bathroomscounts.setText(selectedBathroomCount + " " + getResources().getString(R.string.bathroom));
                btBath1.setBackground(getResources().getDrawable(R.drawable.filiter_button_back));
                btBath2.setBackground(getResources().getDrawable(R.drawable.filiter_button_back));
                btBath3.setBackground(getResources().getDrawable(R.drawable.filiter_button_back));
                btBath4.setBackground(getResources().getDrawable(R.drawable.filiter_button_back));
                btBath6.setBackground(getResources().getDrawable(R.drawable.filiter_button_back));

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    btBath5.setBackground(getResources().getDrawable(R.drawable.filiter_button_back, null));
                } else {
                    btBath5.setBackground(getResources().getDrawable(R.drawable.filiter_button_back));
                }

                break;

            case R.id.btBath6:

                selectedBathroomCount = 6;
                tv_bathroomscounts.setText(selectedBathroomCount + " " + getResources().getString(R.string.bathroom));
                btBath1.setBackground(getResources().getDrawable(R.drawable.filiter_button_back));
                btBath2.setBackground(getResources().getDrawable(R.drawable.filiter_button_back));
                btBath3.setBackground(getResources().getDrawable(R.drawable.filiter_button_back));
                btBath4.setBackground(getResources().getDrawable(R.drawable.filiter_button_back));
                btBath5.setBackground(getResources().getDrawable(R.drawable.filiter_button_back));

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    btBath6.setBackground(getResources().getDrawable(R.drawable.filiter_button_back, null));
                } else {
                    btBath6.setBackground(getResources().getDrawable(R.drawable.filiter_button_back));
                }

                break;


        }
    }

    @Override
    public void filterCategorySelect(List<FillterCategoryResponse.SubCategoryList> subCategoryList) {

        if (subCategoryList == null) {
            Toast.makeText(getContext(), "No sub category found.", Toast.LENGTH_SHORT).show();
            rvSubCat.setVisibility(View.GONE);
        } else {
            rvSubCat.setVisibility(View.VISIBLE);
            filterSubCategoryAdapter = new FilterSubCategoryAdapter(getContext(), subCategoryList, this);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
            rvSubCat.setLayoutManager(linearLayoutManager);
            rvSubCat.setAdapter(filterSubCategoryAdapter);
            filterSubCategoryAdapter.notifyDataSetChanged();
        }


    }

    @Override
    public void selectedData(List<String> selected) {

        if (selected == null || selected.isEmpty()) {

        } else {

            for (String element : selected) {

                if (data.contains(element)) {

                } else {
                    data.addAll(selected);
                    preferenceManager.setValue(getContext(), "DATA", String.valueOf(data));

                    String s = preferenceManager.getValue(getContext(), "DATA");
                    Log.d("MuhammedNazil", s);
                }


            }


        }


    }



    private class getFillterCategory extends AsyncTask<Void, Void, Integer> {

        private String errorMessage;
        private FillterCategoryResponse fillterCategoryResponse;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog();
        }

        @Override
        protected Integer doInBackground(Void... params) {


            int statusCode = 0;

            if (new Utility().isNetworkConnected(getActivity())) {

                FilterCategoryRequest filterCategoryRequest = new FilterCategoryRequest();
                filterCategoryRequest.setLanguage(new PreferenceManager().getValue(getActivity(), PrefKey.CURRENT_DATA));

                Response<FillterCategoryResponse> response = WebServiceManager.getInstance().getFilterCategories(filterCategoryRequest);

                if (response != null && response.isSuccessful() && response.raw().code() == 200) {

                    fillterCategoryResponse = response.body();

                    if (fillterCategoryResponse != null) {

                        if (fillterCategoryResponse.getIsSuccess() == 1) {

                            mainCategories1 = fillterCategoryResponse.getMainCategory();

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
                setRecycleview();
                filterCategoryAdapter.notifyDataSetChanged();

            } else {

                if (errorMessage != null && errorMessage.length() > 0) {
                    new Utility().showMessageAlertDialog(getActivity(), errorMessage);
                } else {
                    new Utility().showMessageAlertDialog(getActivity(), new Utility().getErrorMessage(getActivity(), result));
                }

            }


        }
    }


    private class getFilterData extends AsyncTask<Void, Void, Integer> {

        private String errorMessage;
        private NewFilterResponse filterResponse;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog();
        }

        @Override
        protected Integer doInBackground(Void... params) {

            mainCategories2.clear();
            int statusCode = 0;

            if (new Utility().isNetworkConnected(getActivity())) {

                NewFilterRequest newFilterRequest = new NewFilterRequest();
                newFilterRequest.setLanguage(new PreferenceManager().getValue(getActivity(), PrefKey.CURRENT_DATA));
                newFilterRequest.setSubCategoryId(data);
                newFilterRequest.setLocation(LocationName);

                if (AppState.getInstance().getUserId() != null && AppState.getInstance().getUserId().length() > 0) {

                    newFilterRequest.setUserid(AppState.getInstance().getUserId());

                } else {
                    newFilterRequest.setUserid("");
                }


                if (priceMin.equals("Select Min Price")){
                    newFilterRequest.setMinprice("");
                }else {
                    newFilterRequest.setMinprice(priceMin);
                }

                if (areaMax.equals("Select Max Area")){
                    newFilterRequest.setMaxarea("");
                }else {
                    newFilterRequest.setMaxarea(areaMax);
                }

                if (priceMax.equals("Select Max Price")){
                    newFilterRequest.setMaxprice("");
                }else {
                    newFilterRequest.setMaxprice(priceMax);
                }

                if (areaMin.equals("Select Min Area")){
                    newFilterRequest.setMaxprice("");
                }else {
                    newFilterRequest.setMinarea(areaMin);
                }
                newFilterRequest.setSelectedType(selectedType+"");
                if (selectedType == 0){
                    newFilterRequest.setRentType("");
                }else {
                    newFilterRequest.setRentType(selectedRentFrequencyType);
                }

                if (selectedBedCount.equals("studio")){
                    newFilterRequest.setBedCount("");
                }else {
                    newFilterRequest.setBedCount(selectedBedCount);
                }



                newFilterRequest.setBathCount(selectedBathroomCount+"");


                newFilterRequest.setPropertyName("");



                Response<NewFilterResponse> response = WebServiceManager.getInstance().getFilterData(newFilterRequest);

                if (response != null && response.isSuccessful() && response.raw().code() == 200) {

                    filterResponse = response.body();

                    if (filterResponse != null) {

                        if (filterResponse.getIsSuccess() == 1) {


                                if(filterResponse.getPropertyList()!=null&& filterResponse.getPropertyList().size()>0){



                                    for (NewFilterResponse.PropertyList propertyList : filterResponse.getPropertyList()) {


                                    Property property = new Property();

                                    property.setId(Integer.parseInt(propertyList.getPropertyId()));
                                    property.setTitle(propertyList.getPropertyName());
                                    property.setAddress(propertyList.getLocation());
                                    property.setPropertyType(propertyList.getSubCategoryName());

                                    if (propertyList.getRooms() != null && propertyList.getRooms().length() > 0) {
                                        property.setBedCount(Integer.parseInt(propertyList.getRooms()));
                                    }

                                    if (propertyList.getBuildingArea() != null && propertyList.getBuildingArea().length() > 0) {
                                        property.setArea(Integer.parseInt(propertyList.getBuildingArea()));
                                    }
                                    property.setPrice(Float.valueOf(propertyList.getPrice()));
                                    property.setImage(propertyList.getImage());
                                    property.setFavourite(propertyList.getFavourite());
//                                property.setBedCount(Integer.parseInt(propertyList.getBed_count()));
                                    if (propertyList.getBathroomcount().equals(null) || propertyList.getBathroomcount() == null) {
                                        property.setBathCount(0);
                                    } else {

                                        property.setBathCount(Integer.parseInt(propertyList.getBathroomcount()));
                                    }

                                    if (propertyList.getBed_count().equals(null) || propertyList.getBed_count() == null) {
                                        property.setBedCount(0);
                                    } else {

                                        property.setBedCount(Integer.parseInt(propertyList.getBed_count()));
                                    }

                                    if (propertyList.getLat().isEmpty()|propertyList.getLat().matches("")&&propertyList.getLong().isEmpty()|propertyList.getLong().matches("")){
                                        property.setLatLng(new LatLng(Double.valueOf(0), Double.valueOf(0)));
                                    }else {
                                        property.setLatLng(new LatLng(Double.valueOf(propertyList.getLat()), Double.valueOf(propertyList.getLong())));
                                    }
//                                    property.setLatLng(new LatLng(Double.valueOf(propertyList.getLat()), Double.valueOf(propertyList.getLong())));


                                    mainCategories2.add(property);
                                }
                                    statusCode = Codes.SUCCESS;
                            }else {
                                    statusCode = Codes.ERROR_NO_RECORDS;
                                }




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



                goToListActivity();


            } else {

                if (errorMessage != null && errorMessage.length() > 0) {
                    new Utility().showMessageAlertDialog(getActivity(), errorMessage);
                } else {
                    new Utility().showMessageAlertDialog(getActivity(), new Utility().getErrorMessage(getActivity(), result));
                }

            }


        }

        private void goToListActivity() {
            Bundle bundle=new Bundle();

            if (a.equals("search")){
                if(mainCategories2!=null&&mainCategories2.size()>0){
                    bundle.putParcelableArrayList("new_filter", (ArrayList<? extends Parcelable>) mainCategories2);
                    new Utility().moveToActivity(getActivity(), PropertyListActivity.class, bundle);
                }
            }else {
                if(mainCategories2!=null&&mainCategories2.size()>0){
                    bundle.putParcelableArrayList("properties", (ArrayList<? extends Parcelable>) mainCategories2);
                    new Utility().moveToActivity(getActivity(), MapActivity.class, bundle);
                }
            }


        }
    }


}
