package com.makan.app.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.makan.R;
import com.makan.app.activity.PropertyListActivity;
import com.makan.app.adapter.PropertyListAdapter;
import com.makan.app.app.WebConstant;
import com.makan.app.core.Codes;
import com.makan.app.model.Property;
import com.makan.app.util.Utility;
import com.makan.app.web.WebServiceManager;
import com.makan.app.web.pojo.Dealer;
import com.makan.app.web.pojo.DealerDetailRequest;
import com.makan.app.web.pojo.DealerDetailResponse;
import com.makan.app.web.pojo.PropertyList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;

public class DealerDetailFragment extends BaseFragment implements OnMapReadyCallback {

    private Toolbar toolbar;
    private RecyclerView rvSimilarProperty;

    @BindView(R.id.rlProgressHolder)
    RelativeLayout rlProgressHolder;

    @BindView(R.id.imgToolbar)
    ImageView imgToolbar;

    @BindView(R.id.tvTitle)
    TextView tvTitle;

    @BindView(R.id.tvAddress)
    TextView tvAddress;

    @BindView(R.id.tvDealerWebsite)
    TextView tvDealerWebsite;

    @BindView(R.id.tvDescription)
    TextView tvDescription;

    @BindView(R.id.tvShowAllDealerProperty)
    TextView tvShowAllDealerProperty;

    private List<Property> mSimilarPropertyList;
    private PropertyListAdapter mSimilarPropertyListAdapter;
    private GoogleMap googleMap;
    private boolean isLoadingCompleted=false;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSimilarPropertyList = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_dealer_detail, container, false);
        ButterKnife.bind(this,rootView);
        initialiseComponents(rootView);
        setToolBar();
        setRecycleView();
        setHasOptionsMenu(true);
        preparePropertyList();
        setUpMap(rootView);
        return rootView;
    }

    private void initialiseComponents(View view){
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        //tvDescription=(TextView)view.findViewById(R.id.tvDescription);
        rvSimilarProperty=(RecyclerView) view.findViewById(R.id.rvSimilarProperty);
        //tvDescription.setText(Html.fromHtml(getResources().getString(R.string.property_description)));
    }

    protected void setToolBar(){
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        if(((AppCompatActivity) getActivity()).getSupportActionBar()!=null){
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("");
        }

    }

    private void setUpMap(View view){
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();

        if(isLoadingCompleted){
            inflater.inflate(R.menu.dealer_detail, menu);
        }

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.action_share:
                new Utility().openShareIntent(getActivity(),"");
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setRecycleView() {

        mSimilarPropertyListAdapter = new PropertyListAdapter(getActivity(), mSimilarPropertyList,PropertyListAdapter.STYLE_HORIZONTAL_LIST_CELL,null);
        rvSimilarProperty.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rvSimilarProperty.setAdapter(mSimilarPropertyListAdapter);
    }

    /**
     * Adding few properties for testing
     */
    private void preparePropertyList() {

        new FetchDealerDetailTask().execute();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap=googleMap;

    }

    private class FetchDealerDetailTask extends AsyncTask<Void, Void, Integer> {

        String errorMessage;
        DealerDetailResponse dealerDetailResponse;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            rlProgressHolder.setVisibility(View.VISIBLE);
        }

        @Override
        protected Integer doInBackground(Void... params) {


            int statusCode = 0;

            if (new Utility().isNetworkConnected(getActivity())) {

                DealerDetailRequest dealerDetailRequest=new DealerDetailRequest();
                dealerDetailRequest.setAgencyId("134");

                Response<DealerDetailResponse> response = WebServiceManager.getInstance().getDealerDetail(dealerDetailRequest);

                if (response != null && response.isSuccessful() && response.raw().code() == 200) {

                    dealerDetailResponse = response.body();

                    if (dealerDetailResponse != null) {

                        if (dealerDetailResponse.getRes() == 1 && dealerDetailResponse.getIsSuccess() == 1) {

                            if(dealerDetailResponse.getPropertyList()!=null&& dealerDetailResponse.getPropertyList().size()>0){

                                for (PropertyList propertyList: dealerDetailResponse.getPropertyList()){

                                    Property property=new Property();

                                    property.setId(Integer.parseInt(propertyList.getPropertyId()));
                                    property.setTitle(propertyList.getPropertyName());
                                    property.setAddress(propertyList.getLocation());
                                    property.setPropertyType(propertyList.getSubCategoryName());

                                    if(propertyList.getRooms()!=null&&propertyList.getRooms().length()>0) {
                                        property.setBedCount(Integer.parseInt(propertyList.getRooms()));
                                    }

                                    if(propertyList.getBuildingArea()!=null&&propertyList.getBuildingArea().length()>0) {
                                        property.setArea(Integer.parseInt(propertyList.getBuildingArea()));
                                    }
                                    property.setPrice(propertyList.getPrice());
                                    property.setImage(propertyList.getImage());
                                    property.setDescription(propertyList.getDescription());
                                    property.setLatLng(new LatLng(Double.valueOf(propertyList.getLat()),Double.valueOf(propertyList.getLong())));

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

                isLoadingCompleted=true;

                if(isAdded()){

                    getActivity().invalidateOptionsMenu();
                }


                Dealer dealer=dealerDetailResponse.getDealerList().get(0);

                tvTitle.setText(dealer.getAgencyName());
                tvAddress.setText(dealer.getAgencyAddress());
                tvDealerWebsite.setText(dealer.getAgencyWebsite());

                tvDescription.setText(Html.fromHtml(getResources().getString(R.string.property_description).replace("#",dealer.getAgencyDescription())));

                try{

                    LatLng sydney = new LatLng(Double.valueOf(dealer.getAgencyLat()), Double.valueOf(dealer.getAgencyLong()));
                    googleMap.addMarker(new MarkerOptions().position(sydney)
                            .title(dealer.getAgencyName()).snippet(dealer.getAgencyAddress()));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                }catch (Exception e){

                }



                mSimilarPropertyListAdapter.addItems(mSimilarPropertyList);
                mSimilarPropertyListAdapter.notifyDataSetChanged();

                Glide.with(getActivity()).load(WebConstant.BASE_IMAGE_URL+dealer.getAgencyLogo()).into(imgToolbar);

            } else {

                if (errorMessage != null && errorMessage.length() > 0) {
                    new Utility().showMessageAlertDialog(getActivity(), errorMessage);
                } else {
                    new Utility().showMessageAlertDialog(getActivity(), new Utility().getErrorMessage(getActivity(), result));
                }

            }


        }
    }

    @OnClick(R.id.tvShowAllDealerProperty)
    void OnShowAllDealerProperty(){

        if(mSimilarPropertyList!=null&&mSimilarPropertyList.size()>0){
            Bundle bundle=new Bundle();
            bundle.putParcelableArrayList("dealer_properties", (ArrayList<? extends Parcelable>) mSimilarPropertyList);
            new Utility().moveToActivity(getActivity(), PropertyListActivity.class, bundle);
        }

    }
}
