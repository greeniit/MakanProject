package com.makan.app.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.makan.R;
import com.makan.app.adapter.PropertyListAdapter;
import com.makan.app.core.Codes;
import com.makan.app.model.Property;
import com.makan.app.util.Utility;
import com.makan.app.web.WebServiceManager;
import com.makan.app.web.pojo.FilterSearchRequest;
import com.makan.app.web.pojo.FilterSearchResponse;
import com.makan.app.web.pojo.PropertyList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import retrofit2.Response;

public class MapActivity extends BaseActivity implements View.OnClickListener,OnMapReadyCallback,GoogleMap.OnMarkerClickListener {

    private ImageButton ibClose;
    private RecyclerView rvProperty;
    private Button btnSort,btnFilter;

    private List<Property> properties;
    private PropertyListAdapter mPropertyListAdapter;
    private Map<Marker,Integer> propertyMarkerMap=new HashMap<>();
    private GoogleMap googleMap;
    private RelativeLayout rlProgressHolder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        initialiseComponents();
        setRecycleView();
        setUpMap();
        preparePropertyList();
        setListeners();

    }

    private void initialiseComponents(){
        ibClose=(ImageButton)findViewById(R.id.ibClose);
        rvProperty=(RecyclerView)findViewById(R.id.rvProperty);
        btnFilter=(Button) findViewById(R.id.btnFilter);
        btnSort=(Button) findViewById(R.id.btnSort);
        rlProgressHolder=(RelativeLayout)findViewById(R.id.rlProgressHolder);
    }

    private void setListeners(){
        ibClose.setOnClickListener(this);
        btnSort.setOnClickListener(this);
        btnFilter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.ibClose:
                finish();
                break;

            case R.id.btnFilter:
                if(getIntent().getExtras()!=null&&getIntent().getExtras().containsKey("filter_request")){
                    finish();
                }else{
                    new Utility().moveToActivity(this,NewSearchActivity.class,null);
                }

                break;
        }
    }


    private void setRecycleView() {

        properties =new ArrayList<>();
        mPropertyListAdapter = new PropertyListAdapter(this, properties,PropertyListAdapter.STYLE_HORIZONTAL_LIST_CELL,null);
        rvProperty.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvProperty.setAdapter(mPropertyListAdapter);
    }

    private void setUpMap(){
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Adding few properties for testing
     */
    private void preparePropertyList() {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap=googleMap;
        googleMap.getUiSettings().setCompassEnabled(false);
        propertyMarkerMap.clear();

        if(getIntent().getExtras()!=null&&getIntent().getExtras().containsKey("properties")){

            properties.clear();
            properties=getIntent().getExtras().getParcelableArrayList("properties");
            setMarker(googleMap);
            rlProgressHolder.setVisibility(View.GONE);

        }else if(getIntent().getExtras()!=null&&getIntent().getExtras().containsKey("filter_request")){

            new PropertyListByFilterTask().execute();
        }

    }

    private void setMarker(GoogleMap googleMap) {
        mPropertyListAdapter.addItems(properties);
        mPropertyListAdapter.notifyDataSetChanged();
        rvProperty.smoothScrollToPosition(0);

        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        int pos=0;

        for (Property property:properties){

            LatLng latLong = property.getLatLng();

            Marker marker=googleMap.addMarker(new MarkerOptions()
                    .position(latLong)
                    .title(property.getTitle())
                    .snippet("Price: "+property.getPrice()+"OMR"));



            if(property.getPropertyType()!=null){

                if(property.getPropertyType().equalsIgnoreCase("house")){
                    marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pin_single_appartment));
                }else if(property.getPropertyType().equalsIgnoreCase("single villa")){
                    marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pin_single_villa));
                }else if(property.getPropertyType().equalsIgnoreCase("appartment")){
                    marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pin_appartment));
                }else if(property.getPropertyType().equalsIgnoreCase("studio apartment")){
                    marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pin_studio_appartment));
                }else if(property.getPropertyType().equalsIgnoreCase("land")){
                    marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pin_agricultural_land));
                }else if(property.getPropertyType().equalsIgnoreCase("twin villa")){
                    marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pin_twin_villa));
                }else if(property.getPropertyType().equalsIgnoreCase("office")){
                    marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pin_office));
                }else {
                    marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pin_single_appartment));

                }

            }else{

                marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pin_single_appartment));
            }

            propertyMarkerMap.put(marker,pos);
            builder.include(marker.getPosition());

            pos++;
        }


        LatLngBounds bounds = builder.build();

        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        int padding = (int) (width * 0.25);

        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);
        googleMap.moveCamera(cu);

        googleMap.animateCamera(CameraUpdateFactory.zoomTo( 5.0f ));

        googleMap.setOnMarkerClickListener(this);
    }


    @Override
    public boolean onMarkerClick(Marker marker) {

        rvProperty.smoothScrollToPosition(propertyMarkerMap.get(marker));
        return false;
    }

    private class PropertyListByFilterTask extends AsyncTask<Void, Void, Integer> {

        private String errorMessage;
        private FilterSearchResponse filterSearchResponse;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            rlProgressHolder.setVisibility(View.VISIBLE);
            properties.clear();

            FilterSearchRequest filterSearchRequest=(FilterSearchRequest) getIntent().getExtras().getParcelable("filter_request");
        }

        @Override
        protected Integer doInBackground(Void... params) {


            int statusCode = 0;

            if (new Utility().isNetworkConnected(MapActivity.this)) {

                Response<FilterSearchResponse> response = WebServiceManager.getInstance().getFilterSearchResult((FilterSearchRequest) getIntent().getExtras().getParcelable("filter_request"));

                if (response != null && response.isSuccessful() && response.raw().code() == 200) {

                    filterSearchResponse = response.body();

                    if (filterSearchResponse != null) {

                        if (filterSearchResponse.getIsSuccess() == 1) {

                            if (filterSearchResponse.getPropertyList() != null && filterSearchResponse.getPropertyList().size() > 0) {

                                for (PropertyList propertyList : filterSearchResponse.getPropertyList()) {

                                    Property property = new Property();

                                    property.setId(Integer.parseInt(propertyList.getPropertyId()));
                                    property.setTitle(propertyList.getPropertyName());
                                    property.setAddress(propertyList.getLocation());

                                    if (propertyList.getRooms() != null && propertyList.getRooms().length() > 0) {
                                        property.setBedCount(Integer.parseInt(propertyList.getRooms()));
                                    }

                                    if (propertyList.getBuildingArea() != null && propertyList.getBuildingArea().length() > 0) {
                                        property.setArea(Integer.parseInt(propertyList.getBuildingArea()));
                                    }

                                    property.setPropertyType(propertyList.getSubCategoryName());
                                    property.setPrice(Float.valueOf(propertyList.getPrice()));
                                    property.setImage(propertyList.getImage());
                                    property.setLatLng(new LatLng(Double.valueOf(propertyList.getLat()), Double.valueOf(propertyList.getLong())));
                                    property.setDescription(propertyList.getDescription());
                                    property.setFavourite("");
                                    property.setBathCount(Integer.parseInt(propertyList.getBathroom_count()));
                                    property.setBedCount(Integer.parseInt(propertyList.getRooms()));

                                    properties.clear();
                                    properties.add(property);
                                }
                            }

                            statusCode = Codes.SUCCESS;

                        } else if (filterSearchResponse.getIsSuccess() == 1 && filterSearchResponse.getPropertyList().size() == 0) {

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

            if (result == Codes.SUCCESS) {

                if(properties.size()>0){
                    setMarker(googleMap);
                }


            } else {

                if (errorMessage != null && errorMessage.length() > 0) {
                    new Utility().showMessageAlertDialog(MapActivity.this, errorMessage);
                } else {
                    new Utility().showMessageAlertDialog(MapActivity.this, new Utility().getErrorMessage(MapActivity.this, result));
                }

            }

            rlProgressHolder.setVisibility(View.GONE);

        }
    }
}
