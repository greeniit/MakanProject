package com.makan.app.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
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

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.model.LatLng;
import com.makan.R;
import com.makan.app.activity.DealerDetailActivity;
import com.makan.app.activity.GalleryActivity;
import com.makan.app.activity.LoginActivity;
import com.makan.app.activity.MapActivity;
import com.makan.app.activity.PropertyListActivity;
import com.makan.app.adapter.PropertyListAdapter;
import com.makan.app.app.AppState;
import com.makan.app.app.WebConstant;
import com.makan.app.callback.WishListAddDeleteOperationCallback;
import com.makan.app.core.Codes;
import com.makan.app.model.Property;
import com.makan.app.task.WishListAddDeleteOperationTask;
import com.makan.app.util.Utility;
import com.makan.app.web.WebServiceManager;
import com.makan.app.web.pojo.PropertyDetailRequest;
import com.makan.app.web.pojo.PropertyDetailResponse;
import com.makan.app.web.pojo.PropertyList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;

public class PropertyDetailFragment extends BaseFragment implements View.OnClickListener {

    private Toolbar toolbar;
    private RecyclerView rvSimilarProperty;
    private TextView tvShowAllFeaturedProperty;
    private Button btnBookNow;
    private LinearLayout llDealerSummaryHolder;
    private ImageView ivGallery;


    private List<Property> mSimilarPropertyList;
    private PropertyListAdapter mSimilarPropertyListAdapter;
    private ArrayList<String> imagesUrls = new ArrayList<>();
    private Property property;
    private boolean isLoadingCompleted = false;
    private boolean isAddedToWishList = false;

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSimilarPropertyList = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_property_detail, container, false);
        ButterKnife.bind(this, rootView);
        initialiseComponents(rootView);
        setToolBar();
        setRecycleView();
        setHasOptionsMenu(true);
        preparePropertyList();
        setListeners();
        return rootView;
    }


    private void initialiseComponents(View view) {
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        tvDescription = (TextView) view.findViewById(R.id.tvDescription);
        rvSimilarProperty = (RecyclerView) view.findViewById(R.id.rvSimilarProperty);
        tvShowAllFeaturedProperty = (TextView) view.findViewById(R.id.tvShowAllFeaturedProperty);
        btnBookNow = (Button) view.findViewById(R.id.btnBookNow);
        llDealerSummaryHolder = (LinearLayout) view.findViewById(R.id.llDealerSummaryHolder);
        ivGallery = (ImageView) view.findViewById(R.id.ivGallery);
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

            if(!isAddedToWishList){
                inflater.inflate(R.menu.property_detail, menu);
            }else{
                inflater.inflate(R.menu.property_detail_added_to_wishlist, menu);
            }

        }

        super.onCreateOptionsMenu(menu, inflater);
    }


    @OnClick(R.id.btnMore)
    void onMoreClicked() {

        if(tvDescriptionFull.getVisibility()!=View.VISIBLE){

            tvDescriptionFull.setVisibility(View.VISIBLE);
            tvDescription.setVisibility(View.GONE);
            btnMore.setText("LESS");
        }else{

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
                    new WishListAddDeleteOperationTask(getActivity(), String.valueOf(property.getId()), !property.isAddedToWishList(), new WishListAddDeleteOperationCallback() {
                        @Override
                        public void AddToWishListTaskSuccess() {

                            isAddedToWishList=property.isAddedToWishList();
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

                }else{
                    new Utility().moveToActivity(getActivity(),LoginActivity.class,null);
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
    }

    /**
     * Adding few properties for testing
     */
    private void preparePropertyList() {

        /*int[] featuredProperty = new int[]{
                R.drawable.image_1,
                R.drawable.image_2,
                R.drawable.image_3,
                R.drawable.image_4};

        Property property=new Property();

        property.setThumbnail(featuredProperty[0]);
        mSimilarPropertyList.add(property);

        property=new Property();
        property.setThumbnail(featuredProperty[1]);
        mSimilarPropertyList.add(property);

        property=new Property();
        property.setThumbnail(featuredProperty[2]);
        mSimilarPropertyList.add(property);

        property=new Property();
        property.setThumbnail(featuredProperty[3]);
        mSimilarPropertyList.add(property);

        mSimilarPropertyListAdapter.notifyDataSetChanged();
        rvSimilarProperty.smoothScrollToPosition(0);*/

        if (getArguments().containsKey("property_id")) {
            new GetPropertyDetail().execute();
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
                new Utility().moveToActivity(getActivity(), PropertyListActivity.class, null);
                break;

            case R.id.btnBookNow:
                new Utility().moveToActivity(getActivity(), LoginActivity.class, null);
                break;

            case R.id.llDealerSummaryHolder:
                new Utility().moveToActivity(getActivity(), DealerDetailActivity.class, null);
                break;

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

    private class GetPropertyDetail extends AsyncTask<Void, Void, Integer> {

        private String errorMessage;
        private PropertyDetailResponse propertyDetailResponse;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            rlProgressHolder.setVisibility(View.VISIBLE);
        }

        @Override
        protected Integer doInBackground(Void... params) {


            int statusCode = 0;

            if (new Utility().isNetworkConnected(getActivity())) {

                PropertyDetailRequest propertyDetailRequest = new PropertyDetailRequest();
                propertyDetailRequest.setPropertyId(getArguments().getInt("property_id"));

                Response<PropertyDetailResponse> response = WebServiceManager.getInstance().getPropertyDetail(propertyDetailRequest);

                if (response != null && response.isSuccessful() && response.raw().code() == 200) {

                    propertyDetailResponse = response.body();

                    if (propertyDetailResponse != null) {

                        if (propertyDetailResponse.getIsSuccess() == 1) {

                            imagesUrls.clear();

                            if (propertyDetailResponse.getPropertydetailImages() != null) {

                                for (PropertyDetailResponse.PropertydetailImage propertydetailImage : propertyDetailResponse.getPropertydetailImages()) {

                                    imagesUrls.add(propertydetailImage.getDetailImages());
                                }
                            }

                            PropertyList propertyList = propertyDetailResponse.getPropertyList().get(0);

                            property = new Property();

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
                            property.setPrice(propertyList.getPrice());
                            property.setImage(propertyList.getImage());
                            property.setLatLng(new LatLng(Double.valueOf(propertyList.getLat()), Double.valueOf(propertyList.getLong())));
                            property.setDescription("");

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

                isLoadingCompleted = true;

                if (isAdded()) {

                    getActivity().invalidateOptionsMenu();
                    tvTitle.setText(propertyDetailResponse.getPropertyList().get(0).getPropertyName());
                    tvAddress.setText(propertyDetailResponse.getPropertyList().get(0).getLocation());
                    tvPrice.setText(" " + propertyDetailResponse.getPropertyList().get(0).getPrice() + " OMR");
                    tvArea.setText(propertyDetailResponse.getPropertyList().get(0).getBuildingArea() + " Sqft");
                    tvDescription.setText(Html.fromHtml(getResources().getString(R.string.property_description).replace("#", propertyDetailResponse.getPropertyList().get(0).getDescription())));
                    tvDescriptionFull.setText(Html.fromHtml(getResources().getString(R.string.property_description).replace("#", propertyDetailResponse.getPropertyList().get(0).getDescription())));
                    tvPropertyType.setText(Html.fromHtml(getResources().getString(R.string.property_type).replace("#", propertyDetailResponse.getPropertyList().get(0).getMainCategoryName())));


                    tvDealerName.setText(propertyDetailResponse.getPropertyList().get(0).getAgencyName());
                    tvDealerAddress.setText(propertyDetailResponse.getPropertyList().get(0).getAgencyAddress());
                    tvDealerEmail.setText(propertyDetailResponse.getPropertyList().get(0).getAgencyEmail());
                    tvDealerWebsite.setText(propertyDetailResponse.getPropertyList().get(0).getAgencyWebsite());

                    Glide.with(getActivity()).load(WebConstant.BASE_IMAGE_URL + propertyDetailResponse.getPropertydetailImages().get(0).getDetailImages()).into(ivThumbnail);

                    if (propertyDetailResponse.getPropertydetailImages() != null && propertyDetailResponse.getPropertydetailImages().size() > 0) {

                        Glide.with(getActivity()).load(WebConstant.BASE_IMAGE_URL + propertyDetailResponse.getPropertydetailImages().get(0).getDetailImages()).into(ivMain);
                    } else {

                    }

                    if (imagesUrls.size() > 0) {
                        ivGallery.setVisibility(View.VISIBLE);
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
}
