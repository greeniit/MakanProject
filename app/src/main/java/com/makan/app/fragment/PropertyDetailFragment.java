package com.makan.app.fragment;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.model.LatLng;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.makan.R;
import com.makan.app.activity.BookPropertyActivity;
import com.makan.app.activity.DealerDetailActivity;
import com.makan.app.activity.GalleryActivity;
import com.makan.app.activity.LoginActivity;
import com.makan.app.activity.MapActivity;
import com.makan.app.activity.MortgageActivity;
import com.makan.app.activity.PropertyListActivity;
import com.makan.app.adapter.GalleryAdapter;
import com.makan.app.adapter.NearByFacilitiesAdapter;
import com.makan.app.adapter.PropertyListAdapter;
import com.makan.app.app.AppState;
import com.makan.app.app.WebConstant;
import com.makan.app.callback.WishListAddDeleteOperationCallback;
import com.makan.app.core.Codes;
import com.makan.app.model.Property;
import com.makan.app.preference.PrefKey;
import com.makan.app.preference.PreferenceManager;
import com.makan.app.task.WishListAddDeleteOperationTask;
import com.makan.app.util.Utility;
import com.makan.app.web.WebServiceManager;
import com.makan.app.web.pojo.PropertyDetailRequest;
import com.makan.app.web.pojo.PropertyDetailResponse;
import com.makan.app.web.pojo.PropertyList;
import com.makan.app.web.pojo.SimilarDataRequest;
import com.makan.app.web.pojo.SimilarDataResponse;
import com.makan.app.web.pojo.WishListRequest;
import com.makan.app.web.pojo.WishListResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class PropertyDetailFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.ivPrice)
    ImageView ivPrice;
    @BindView(R.id.collapsing_container)
    CollapsingToolbarLayout collapsingContainer;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.llGalleryView)
    LinearLayout llGalleryView;
    @BindView(R.id.ivBed)
    ImageView ivBed;
    @BindView(R.id.tvBed)
    TextView tvBed;
    @BindView(R.id.ivBathroom)
    ImageView ivBathroom;
    @BindView(R.id.tvBathroom)
    TextView tvBathroom;
    @BindView(R.id.ivArea)
    ImageView ivArea;
    @BindView(R.id.llLocationHolder)
    RelativeLayout llLocationHolder;
    @BindView(R.id.llNearbyItemsHolder)
    LinearLayout llNearbyItemsHolder;
    @BindView(R.id.ivLocation)
    ImageView ivLocation;
    @BindView(R.id.ivDealerEmail)
    ImageView ivDealerEmail;
    @BindView(R.id.ivDealerWebsite)
    ImageView ivDealerWebsite;
    @BindView(R.id.btCall)
    Button btCall;
    @BindView(R.id.ivWhatsapp)
    ImageView ivWhatsapp;
    @BindView(R.id.btEnquriy)
    Button btEnquriy;
    @BindView(R.id.tvTypeOfProperty)
    TextView tvTypeOfProperty;
    @BindView(R.id.tvToalBulidibgArea)
    TextView tvToalBulidibgArea;
    @BindView(R.id.tvTotalPlotArea)
    TextView tvTotalPlotArea;
    @BindView(R.id.tvBathroomCount)
    TextView tvBathroomCount;
    @BindView(R.id.tvBedroomCount)
    TextView tvBedroomCount;
    @BindView(R.id.tvLocation)
    TextView tvLocation;
    @BindView(R.id.tvKeyFacilities)
    TextView tvKeyFacilities;
    @BindView(R.id.tvDealerMobile)
    TextView tvDealerMobile;
    @BindView(R.id.tvFailities)
    TextView tvFailities;
    @BindView(R.id.btMortgage)
    Button btMortgage;
    @BindView(R.id.homeViewPager)
    ViewPager homeViewPager;


    private Toolbar toolbar;
    private RecyclerView rvSimilarProperty;
    private RecyclerView rvNearByDacilities;
    private TextView tvShowAllFeaturedProperty;
    private Button btnBookNow;
    private LinearLayout llDealerSummaryHolder;
    private ImageView ivGallery;
    private String mobileNumber;
    private String emailto;
    private String subId = "";
    private String agencyId = "";


    private List<Property> mSimilarPropertyList;
    private List<PropertyDetailResponse.TotalAmenity> nearByFacilitiesList;
    private PropertyListAdapter mSimilarPropertyListAdapter;
    private NearByFacilitiesAdapter nearByFacilitiesAdapter;
    private ArrayList<String> imagesUrls = new ArrayList<>();
    private List<String> imagesUrls1 = new ArrayList<>();
    private Property property;
    private boolean isLoadingCompleted = false;
    public static boolean isAddedToWishList = false;
    public static boolean wishListInitialStatus = false;

    private GalleryAdapter galleryAdapter;

    @BindView(R.id.rlProgressHolder)
    RelativeLayout rlProgressHolder;

    @BindView(R.id.tvTitle)
    TextView tvTitle;

    @BindView(R.id.tvAddress)
    TextView tvAddress;

    @BindView(R.id.tvPrice)
    TextView tvPrice;

    @BindView(R.id.tvArea)
    TextView tvArea;

    @BindView(R.id.tvDescription)
    TextView tvDescription;

    @BindView(R.id.tvDescriptionFull)
    TextView tvDescriptionFull;

    @BindView(R.id.tvPropertyType)
    TextView tvPropertyType;

    @BindView(R.id.tvDealerName)
    TextView tvDealerName;

    @BindView(R.id.tvDealerAddress)
    TextView tvDealerAddress;

    @BindView(R.id.tvDealerEmail)
    TextView tvDealerEmail;

    @BindView(R.id.tvDealerWebsite)
    TextView tvDealerWebsite;

    @BindView(R.id.ivMain)
    ImageView ivMain;

    @BindView(R.id.ivThumbnail)
    ImageView ivThumbnail;

    @BindView(R.id.ivAirport)
    ImageView ivAirport;

    @BindView(R.id.ivAtm)
    ImageView ivAtm;

    @BindView(R.id.ivBank)
    ImageView ivBank;

    @BindView(R.id.ivBusStation)
    ImageView ivBusStation;

    @BindView(R.id.ivHospital)
    ImageView ivHospital;

    @BindView(R.id.ivMall)
    ImageView ivMall;

    @BindView(R.id.ivMosque)
    ImageView ivMosque;

    @BindView(R.id.ivPark)
    ImageView ivPark;

    @BindView(R.id.ivPetrolPump)
    ImageView ivPetrolPump;

    @BindView(R.id.ivSchool)
    ImageView ivSchool;

    @BindView(R.id.ivStore)
    ImageView ivStore;

    @BindView(R.id.llMapView)
    LinearLayout llMapView;

    @BindView(R.id.btnMore)
    Button btnMore;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        mSimilarPropertyList = new ArrayList<>();
        nearByFacilitiesList = new ArrayList<>();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_property_detail, container, false);
        ButterKnife.bind(this, rootView);
        preparePropertyList();
        initialiseComponents(rootView);
        setToolBar();
        setHasOptionsMenu(true);
        setListeners();
        setRecycleView();

        return rootView;
    }


    private void initialiseComponents(View view) {
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        tvDescription = (TextView) view.findViewById(R.id.tvDescription);
        rvSimilarProperty = (RecyclerView) view.findViewById(R.id.rvSimilarProperty);
        rvNearByDacilities = (RecyclerView) view.findViewById(R.id.rvNearByDacilities);
        tvShowAllFeaturedProperty = (TextView) view.findViewById(R.id.tvShowAllFeaturedProperty);
        btnBookNow = (Button) view.findViewById(R.id.btnBookNow);
        llDealerSummaryHolder = (LinearLayout) view.findViewById(R.id.llDealerSummaryHolder);
        ivGallery = (ImageView) view.findViewById(R.id.ivGallery);
        ivGallery.setVisibility(View.GONE);
        llGalleryView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("images", imagesUrls);

                new Utility().moveToActivity(getActivity(), GalleryActivity.class, bundle);

            }
        });
        btCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dexter.withActivity(getActivity())
                        .withPermission(Manifest.permission.CALL_PHONE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {

                                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mobileNumber));
                                startActivity(intent);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {

                                Log.e("Makan", "Call permission denied");
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                                Log.e("Makan", "Call permission denied. Rationale should be shown");
                                new Utility().showMessageAlertDialog(getActivity(), getResources().getString(R.string.permission_phone_not_granted));
                            }

                        }).check();


            }
        });

        ivWhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://api.whatsapp.com/send?phone=" + mobileNumber;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        btEnquriy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{emailto});
                email.putExtra(Intent.EXTRA_SUBJECT, "");
                email.putExtra(Intent.EXTRA_TEXT, "");
                //need this to prompts email client only
                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email, "Choose an Email client :"));

            }
        });
        btMortgage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), MortgageActivity.class);
                Float a = property.getPrice();
                i.putExtra("PRICE", a);
                startActivity(i);
            }
        });
        tvDescription.setText(Html.fromHtml(getResources().getString(R.string.property_description)));
        tvDescriptionFull.setText(Html.fromHtml(getResources().getString(R.string.property_description)));
    }

    protected void setToolBar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("");
        }

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();

        if (isLoadingCompleted) {

            if (!isAddedToWishList) {
                inflater.inflate(R.menu.property_detail, menu);
            } else {
                inflater.inflate(R.menu.property_detail_added_to_wishlist, menu);
            }

        }

        super.onCreateOptionsMenu(menu, inflater);
    }


    @OnClick(R.id.btnMore)
    void onMoreClicked() {

        if (tvDescriptionFull.getVisibility() != View.VISIBLE) {
            tvDescriptionFull.setVisibility(View.VISIBLE);
            tvDescription.setVisibility(View.GONE);
            btnMore.setText("LESS");
        } else {

            tvDescriptionFull.setVisibility(View.GONE);
            tvDescription.setVisibility(View.VISIBLE);
            btnMore.setText("MORE");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_share:
                new Utility().openShareIntent(getActivity(), "");
                return true;

            case R.id.action_refresh:

                if (AppState.getInstance().getUserId() != null && AppState.getInstance().getUserId().length() > 0) {

                    showProgressDialog();
                    new WishListAddDeleteOperationTask(getActivity(), String.valueOf(property.getId()), !isAddedToWishList, new WishListAddDeleteOperationCallback() {
                        @Override
                        public void AddToWishListTaskSuccess() {

                            isAddedToWishList = !isAddedToWishList;
                            getActivity().invalidateOptionsMenu();
                            dismissProgressDialog();

                        }

                        @Override
                        public void AddToWishListTaskFailure(String errorMessage) {

                            if (errorMessage != null && errorMessage.length() > 0) {
                                new Utility().showMessageAlertDialog(getActivity(), errorMessage);
                            }

                            dismissProgressDialog();
                        }

                    }).execute();

                } else {
                    new Utility().moveToActivity(getActivity(), LoginActivity.class, null);
                }

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setRecycleView() {

        mSimilarPropertyListAdapter = new PropertyListAdapter(getActivity(), mSimilarPropertyList, PropertyListAdapter.STYLE_HORIZONTAL_LIST_CELL, null);
        rvSimilarProperty.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rvSimilarProperty.setAdapter(mSimilarPropertyListAdapter);

        SnapHelper snapHelper = new PagerSnapHelper();


        nearByFacilitiesAdapter = new NearByFacilitiesAdapter(getContext(), nearByFacilitiesList);
        rvNearByDacilities.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rvNearByDacilities.setAdapter(nearByFacilitiesAdapter);
        snapHelper.attachToRecyclerView(rvNearByDacilities);

        galleryAdapter = new GalleryAdapter(getFragmentManager(), imagesUrls);
        homeViewPager.setAdapter(galleryAdapter);


    }

    /**
     * Adding few properties for testing
     */
    private void preparePropertyList() {

        if (getArguments().containsKey("property_id")) {
            new GetPropertyDetail().execute();
            new getSimiliarProprety().execute();
        }

    }

    private void setListeners() {
        tvShowAllFeaturedProperty.setOnClickListener(this);
        btnBookNow.setOnClickListener(this);
        llDealerSummaryHolder.setOnClickListener(this);
        ivGallery.setOnClickListener(this);
        llMapView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.tvShowAllFeaturedProperty:
                Bundle bundle1 = new Bundle();
                bundle1.putInt("subcategory_id", Integer.parseInt(property.getSubCategory()));
                new Utility().moveToActivity(getActivity(), PropertyListActivity.class, bundle1);
                break;

            case R.id.btnBookNow:

                if (AppState.getInstance().isLoginStatus()) {

                    Bundle bundle = new Bundle();
                    bundle.putInt("property_id", property.getId());

                    new Utility().moveToActivity(getActivity(), BookPropertyActivity.class, bundle);
                } else {

                    new Utility().moveToActivity(getActivity(), LoginActivity.class, null);
                }

                break;

            /*case R.id.llDealerSummaryHolder:


                new Utility().moveToActivity(getActivity(), DealerDetailActivity.class, null);
                break;*/

            case R.id.ivGallery:

                Bundle bundle = new Bundle();
                bundle.putStringArrayList("images", imagesUrls);

                new Utility().moveToActivity(getActivity(), GalleryActivity.class, bundle);
                break;

            case R.id.llMapView:

                Bundle mapBundle = new Bundle();
                ArrayList<Property> properties = new ArrayList<>();
                properties.add(property);
                mapBundle.putString("source", "property_detail");
                mapBundle.putParcelableArrayList("properties", properties);
                new Utility().moveToActivity(getActivity(), MapActivity.class, mapBundle);
                break;




        }
    }


    @OnClick(R.id.ivThumbnail)
    public void onViewClicked() {
        Bundle bundle = new Bundle();
        bundle.putString("dealer_id",agencyId);
        new Utility().moveToActivity(getContext(), DealerDetailActivity.class,bundle);
    }

    private class GetPropertyDetail extends AsyncTask<Void, Void, Integer> {

        private String errorMessage;
        private PropertyDetailResponse propertyDetailResponse;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            isAddedToWishList = false;
            rlProgressHolder.setVisibility(View.VISIBLE);
        }

        @Override
        protected Integer doInBackground(Void... params) {


            int statusCode = 0;

            if (new Utility().isNetworkConnected(getActivity())) {

                PropertyDetailRequest propertyDetailRequest = new PropertyDetailRequest();
                propertyDetailRequest.setPropertyId(getArguments().getInt("property_id"));
                propertyDetailRequest.setLanguage(new PreferenceManager().getValue(getActivity(), PrefKey.CURRENT_DATA));

                Response<PropertyDetailResponse> response = WebServiceManager.getInstance().getPropertyDetail(propertyDetailRequest);

                if (response != null && response.isSuccessful() && response.raw().code() == 200) {

                    propertyDetailResponse = response.body();

                    if (propertyDetailResponse != null) {

                        if (propertyDetailResponse.getIsSuccess() == 1) {

                            imagesUrls.clear();

                            if (propertyDetailResponse.getTotalAmenities() != null) {

                                nearByFacilitiesList.clear();
                                nearByFacilitiesList.addAll(propertyDetailResponse.getTotalAmenities());

                            }

                            if (propertyDetailResponse.getPropertydetailImages() != null) {

                                for (PropertyDetailResponse.PropertydetailImage propertydetailImage : propertyDetailResponse.getPropertydetailImages()) {

                                    imagesUrls.add(propertydetailImage.getDetailImages());

                                }
                            }


                            PropertyList propertyList = propertyDetailResponse.getPropertyList().get(0);


                            property = new Property();

                            property.setSubCategory(propertyList.getSubCategoryId());

                            property.setId(Integer.parseInt(propertyList.getPropertyId()));
                            property.setTitle(propertyList.getPropertyName());
                            property.setAddress(propertyList.getLocation());
                            property.setPropertyType(propertyList.getSubCategoryName());

//                            if (propertyList.getRooms() != null && propertyList.getRooms().length() > 0) {
//                                property.setBedCount(Integer.parseInt(propertyList.getRooms()));
//                            }

                            if (propertyList.getPlotArea() != null && propertyList.getPlotArea().length() > 0) {
                                String sarea = propertyList.getPlotArea();
                                double d = Double.parseDouble(sarea);
                                property.setArea((int) d);
                            }


                            property.setPrice(Float.valueOf(propertyList.getPrice()));
                            property.setImage(propertyList.getImage());
                            property.setLatLng(new LatLng(Double.valueOf(propertyList.getLat()), Double.valueOf(propertyList.getLong())));
                            property.setDescription(propertyList.getDescription());
                            property.setFavourite(propertyList.getFavourite());
                            if (propertyList.getBedcount().equals(null) || propertyList.getBedcount() == null) {
                                property.setBedCount(0);
                            } else {
                                property.setBedCount(Integer.parseInt(propertyList.getBedcount()));
                            }

                            if (propertyList.getBathroom_count().equals(null) || propertyList.getBathroom_count() == null) {
                                property.setBathCount(0);

                            } else {
                                property.setBathCount(Integer.parseInt(propertyList.getBathroom_count()));

                            }


                            if (AppState.getInstance().isLoginStatus()) {

                                if (property.getFavourite() != null) {

                                    if (property.getFavourite().equalsIgnoreCase("1")) {
                                        isAddedToWishList = true;
                                    } else {
                                        isAddedToWishList = false;
                                    }
                                }


                            }

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


            if (statusCode == Codes.SUCCESS && AppState.getInstance().isLoginStatus()) {

                if (property.getFavourite() == null) {

                    int wishListApiStatusCode = 0;

                    if (new Utility().isNetworkConnected(getActivity())) {

                        WishListRequest wishListRequest = new WishListRequest();
                        wishListRequest.setUserId(AppState.getInstance().getUserId());
                        wishListRequest.setLanguage(new PreferenceManager().getValue(getActivity(), PrefKey.CURRENT_DATA));


                        Response<WishListResponse> response = WebServiceManager.getInstance().getWishList(wishListRequest);

                        if (response != null && response.isSuccessful() && response.raw().code() == 200) {

                            WishListResponse wishListResponse = response.body();

                            if (wishListResponse != null) {

                                if (wishListResponse.getIsSuccess() == 1 && wishListResponse.getPropertyList() != null && wishListResponse.getPropertyList().size() > 0) {

                                    if (wishListResponse.getPropertyList() != null && wishListResponse.getPropertyList().size() > 0) {

                                        for (WishListResponse.PropertyList propertyList : wishListResponse.getPropertyList()) {


                                            if (propertyList.getPropertyId().equals(String.valueOf(property.getId()))) {

                                                isAddedToWishList = true;
                                                break;
                                            }
                                        }
                                    }

                                    wishListApiStatusCode = Codes.SUCCESS;

                                } else if (wishListResponse.getIsSuccess() == 1 && (wishListResponse.getPropertyList() == null || wishListResponse.getPropertyList().size() == 0)) {

                                    wishListApiStatusCode = Codes.SUCCESS;

                                    isAddedToWishList = false;

                                } else {
                                    wishListApiStatusCode = Codes.ERROR_UNEXPECTED;
                                }

                            } else {
                                wishListApiStatusCode = Codes.ERROR_UNEXPECTED;
                            }


                        } else {
                            wishListApiStatusCode = Codes.ERROR_UNABLE_CONNECT_TO_SERVER;
                        }

                    } else {
                        wishListApiStatusCode = Codes.ERROR_NETWORK;
                    }
                }


            }

            wishListInitialStatus = isAddedToWishList;

            return statusCode;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);

            rlProgressHolder.setVisibility(View.GONE);

            if (result == Codes.SUCCESS) {


//                nearByFacilitiesAdapter.addItems(nearByFacilitiesList);
                nearByFacilitiesAdapter.notifyDataSetChanged();
                galleryAdapter.notifyDataSetChanged();

                isLoadingCompleted = true;

                if (isAdded()) {

                    getActivity().invalidateOptionsMenu();
                    subId = propertyDetailResponse.getPropertyList().get(0).getSubCategoryId();
                    tvTitle.setText(propertyDetailResponse.getPropertyList().get(0).getPropertyName());
                    tvAddress.setText(propertyDetailResponse.getPropertyList().get(0).getLocation());
                    tvPrice.setText(" " + propertyDetailResponse.getPropertyList().get(0).getPrice() + " OMR");
                    tvArea.setText(propertyDetailResponse.getPropertyList().get(0).getPlotArea() + " Sq. Area");

                    tvDescription.setText(Html.fromHtml(getResources().getString(R.string.property_description).replace("#", propertyDetailResponse.getPropertyList().get(0).getDescription())));
                    tvDescriptionFull.setText(Html.fromHtml(getResources().getString(R.string.property_description).replace("#", propertyDetailResponse.getPropertyList().get(0).getDescription())));
                    tvPropertyType.setText(Html.fromHtml(getResources().getString(R.string.property_type).replace("#", propertyDetailResponse.getPropertyList().get(0).getMainCategoryName())));


                    tvDealerName.setText(propertyDetailResponse.getPropertyList().get(0).getAgencyName());
                    tvDealerAddress.setText(getResources().getString(R.string.agent) + " " + propertyDetailResponse.getPropertyList().get(0).getAgencyName());
                    tvDealerEmail.setText(getResources().getString(R.string.officeemail) + " " + propertyDetailResponse.getPropertyList().get(0).getAgencyEmail());
                    tvDealerWebsite.setText(getResources().getString(R.string.website) + " " + propertyDetailResponse.getPropertyList().get(0).getAgencyWebsite());
                    tvDealerMobile.setText(getResources().getString(R.string.headoffice) + " " + propertyDetailResponse.getPropertyList().get(0).getAgencyContactNo());
                    mobileNumber = propertyDetailResponse.getPropertyList().get(0).getAgencymembercontactno();
                    emailto = propertyDetailResponse.getPropertyList().get(0).getAgencyEmail();
                    agencyId = propertyDetailResponse.getPropertyList().get(0).getAgencyid();


                    subId = propertyDetailResponse.getPropertyList().get(0).getSubCategoryId();


                    tvTypeOfProperty.setText(propertyDetailResponse.getPropertyList().get(0).getPropertyType());
                    tvToalBulidibgArea.setText(propertyDetailResponse.getPropertyList().get(0).getBuildingArea());
                    tvTotalPlotArea.setText(propertyDetailResponse.getPropertyList().get(0).getPlotArea());
                    tvBathroomCount.setText(propertyDetailResponse.getPropertyList().get(0).getBathroom_count());
                    tvBathroom.setText(propertyDetailResponse.getPropertyList().get(0).getBathroom_count() +" "+ getResources().getString(R.string.bathroom));
                    tvBedroomCount.setText(propertyDetailResponse.getPropertyList().get(0).getBedcount());
                    tvBed.setText(propertyDetailResponse.getPropertyList().get(0).getBedcount()+" "+ getResources().getString(R.string.beds));
                    tvLocation.setText(propertyDetailResponse.getPropertyList().get(0).getLocation());
                    tvKeyFacilities.setText(propertyDetailResponse.getPropertyList().get(0).getDescription());


                    Glide.with(getActivity()).load(WebConstant.BASE_IMAGE_URL + propertyDetailResponse.getPropertyList().get(0).getAgencyLogo()).placeholder(R.drawable.ic_idea).into(ivThumbnail);

                    if (propertyDetailResponse.getPropertydetailImages() != null && propertyDetailResponse.getPropertydetailImages().size() > 0) {

                        Glide.with(getActivity()).load(WebConstant.BASE_IMAGE_URL + propertyDetailResponse.getPropertydetailImages().get(0).getDetailImages()).into(ivMain);
                    } else {

                    }

                    if (imagesUrls.size() > 0) {
                        ivGallery.setVisibility(View.GONE);
                    } else {
                        ivGallery.setVisibility(View.GONE);
                    }

                    if (propertyDetailResponse.getTotalAmenities() != null && propertyDetailResponse.getTotalAmenities().size() > 0) {

                        for (PropertyDetailResponse.TotalAmenity amenity : propertyDetailResponse.getTotalAmenities()) {

                            if (amenity.getNearByName().equalsIgnoreCase("airport")) {

                                ivAirport.setVisibility(View.VISIBLE);
                            }

                            if (amenity.getNearByName().equalsIgnoreCase("atm")) {

                                ivAtm.setVisibility(View.VISIBLE);
                            }

                            if (amenity.getNearByName().equalsIgnoreCase("bank")) {

                                ivBank.setVisibility(View.VISIBLE);
                            }

                            if (amenity.getNearByName().equalsIgnoreCase("bus station")) {

                                ivBusStation.setVisibility(View.VISIBLE);
                            }

                            if (amenity.getNearByName().equalsIgnoreCase("hospital")) {

                                ivHospital.setVisibility(View.VISIBLE);
                            }

                            if (amenity.getNearByName().equalsIgnoreCase("mall")) {

                                ivMall.setVisibility(View.VISIBLE);
                            }

                            if (amenity.getNearByName().equalsIgnoreCase("mosque")) {

                                ivMosque.setVisibility(View.VISIBLE);
                            }

                            if (amenity.getNearByName().equalsIgnoreCase("park")) {

                                ivPark.setVisibility(View.VISIBLE);
                            }

                            if (amenity.getNearByName().equalsIgnoreCase("petrol pump")) {

                                ivPetrolPump.setVisibility(View.VISIBLE);
                            }

                            if (amenity.getNearByName().equalsIgnoreCase("school")) {

                                ivSchool.setVisibility(View.VISIBLE);
                            }

                            if (amenity.getNearByName().equalsIgnoreCase("store")) {

                                ivStore.setVisibility(View.VISIBLE);
                            }
                        }
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

    private class getSimiliarProprety extends AsyncTask<Void, Void, Integer> {

        private String errorMessage;
        private SimilarDataResponse similarDataResponse;
        private SimilarDataRequest similarDataRequest;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            isAddedToWishList = false;
            rlProgressHolder.setVisibility(View.VISIBLE);
        }

        @Override
        protected Integer doInBackground(Void... params) {


            int statusCode = 0;

            if (new Utility().isNetworkConnected(getActivity())) {

                LatLng latLng = property.getLatLng();


                similarDataRequest = new SimilarDataRequest();
                similarDataRequest.setPropertyId(String.valueOf(property.getId()));
                similarDataRequest.setLongitud(String.valueOf(latLng.longitude));
                similarDataRequest.setLatitude(String.valueOf(latLng.latitude));
                similarDataRequest.setLanguage(new PreferenceManager().getValue(getActivity(), PrefKey.CURRENT_DATA));
                similarDataRequest.setSubCategoryId(property.getSubCategory());

                Response<SimilarDataResponse> response = WebServiceManager.getInstance().getSimilarData(similarDataRequest);

                if (response != null && response.isSuccessful() && response.raw().code() == 200) {

                    similarDataResponse = response.body();

                    if (similarDataResponse != null) {

                        if (similarDataResponse.getIsSuccess() == 1) {


                            if (similarDataResponse.getPropertyList() != null && similarDataResponse.getPropertyList().size() > 0) {

                                for (SimilarDataResponse.PropertyList propertyList : similarDataResponse.getPropertyList()) {

                                    Property property = new Property();

                                    property.setId(Integer.parseInt(propertyList.getPropertyId()));
                                    property.setTitle(propertyList.getPropertyName());
                                    property.setAddress(propertyList.getLocation());
                                    property.setPropertyType(propertyList.getSubCategoryName());


                                    if (propertyList.getBuildingArea() != null && propertyList.getBuildingArea().length() > 0) {
                                        property.setArea(Integer.parseInt(propertyList.getBuildingArea()));
                                    }
                                    property.setPrice(Float.valueOf(propertyList.getPrice()));
                                    property.setImage(propertyList.getImage());
                                    property.setDescription(propertyList.getDescription());
                                    property.setLatLng(new LatLng(Double.valueOf(propertyList.getLat()), Double.valueOf(propertyList.getLong())));

                                    if (propertyList.getBedCount() != null) {

                                        property.setBedCount(Integer.parseInt(propertyList.getBedCount()));
                                    } else {
                                        property.setBedCount(0);
                                    }

                                    if (propertyList.getBathroomCount() != null) {

                                        property.setBathCount(Integer.parseInt(propertyList.getBathroomCount()));
                                    } else {
                                        property.setBathCount(0);
                                    }

                                    mSimilarPropertyList.add(property);
                                }
                            }


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

                mSimilarPropertyListAdapter.notifyDataSetChanged();

            } else {

                if (errorMessage != null && errorMessage.length() > 0) {
                    new Utility().showMessageAlertDialog(getActivity(), errorMessage);
                } else {
                    new Utility().showMessageAlertDialog(getActivity(), new Utility().getErrorMessage(getActivity(), result));
                }

            }


        }


    }

    public void setResult() {

        if (isAdded()) {
            Intent resultIntent = new Intent();

            if (property != null) {

                if (isAddedToWishList != wishListInitialStatus) {
                    resultIntent.putExtra("isRefreshRequired", true);
                }

            } else {
                resultIntent.putExtra("isRefreshRequired", false);
            }

            getActivity().setResult(RESULT_OK, resultIntent);
            getActivity().finish();
        }

    }
}
