package com.makan.app.fragment;

import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.model.LatLng;
import com.makan.R;
import com.makan.app.activity.FilterActivity;
import com.makan.app.activity.LoginActivity;
import com.makan.app.activity.MapActivity;
import com.makan.app.adapter.PropertyListAdapter;
import com.makan.app.app.AppState;
import com.makan.app.callback.PropertyAdapterWishListOperationCallback;
import com.makan.app.callback.SortOptionSelectionCallback;
import com.makan.app.callback.WishListAddDeleteOperationCallback;
import com.makan.app.core.Codes;
import com.makan.app.model.Property;
import com.makan.app.task.WishListAddDeleteOperationTask;
import com.makan.app.util.Utility;
import com.makan.app.web.WebServiceManager;
import com.makan.app.web.pojo.FilterSearchRequest;
import com.makan.app.web.pojo.FilterSearchResponse;
import com.makan.app.web.pojo.GetCategoryPropertyRequest;
import com.makan.app.web.pojo.GetCategoryPropertyResponse;
import com.makan.app.web.pojo.GetPropertiesByTypeResponse;
import com.makan.app.web.pojo.GetPropertyByPlaceRequest;
import com.makan.app.web.pojo.GetPropertyByPlaceResponse;
import com.makan.app.web.pojo.GetPropertyByTypeRequest;
import com.makan.app.web.pojo.HomeResponse;
import com.makan.app.web.pojo.PropertyList;
import com.makan.app.web.pojo.SearchByNameRequest;
import com.makan.app.web.pojo.SearchByNameResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Response;

public class PropertyListFragment extends BaseFragment implements View.OnClickListener {

    private RecyclerView rvProperty;
    private Button btnFilter, btnMap, btnSort;
    private List<Property> mPropertyList;
    private PropertyListAdapter mPropertyAdapter;
    private int currentSortSelection = 0;

    @BindView(R.id.rlProgressHolder)
    RelativeLayout rlProgressHolder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPropertyList = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_property_list, container, false);
        initialiseComponents(rootView);
        ButterKnife.bind(this, rootView);
        setRecycleView();
        preparePropertyList();
        setListeners();
        return rootView;
    }

    private void initialiseComponents(View rootView) {
        btnFilter = (Button) rootView.findViewById(R.id.btnFilter);
        btnMap = (Button) rootView.findViewById(R.id.btnMap);
        btnSort = (Button) rootView.findViewById(R.id.btnSort);
        rvProperty = (RecyclerView) rootView.findViewById(R.id.rvProperty);
    }

    private void setRecycleView() {

        mPropertyAdapter = new PropertyListAdapter(getActivity(), mPropertyList, PropertyListAdapter.STYLE_VERTICAL_LIST_CELL, new PropertyAdapterWishListOperationCallback() {
            @Override
            public void onWishListClicked(final Property property, final int position) {

                if(AppState.getInstance().getUserId()!=null&&AppState.getInstance().getUserId().length()>0){

                    showProgressDialog();
                    new WishListAddDeleteOperationTask(getActivity(), String.valueOf(property.getId()), property.getFavourite().equalsIgnoreCase("1")?false:true, new WishListAddDeleteOperationCallback() {
                        @Override
                        public void AddToWishListTaskSuccess() {

                            dismissProgressDialog();

                            if(property.getFavourite().equalsIgnoreCase("1")){
                                property.setFavourite("0");
                            }else{
                                property.setFavourite("1");
                            }

                            mPropertyAdapter.updateItem(property,position);

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
            }
        });

        rvProperty.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rvProperty.setAdapter(mPropertyAdapter);
    }


    public void preparePropertyList() {

        if (getArguments().containsKey("subcategory_id")) {
            new PropertyListDataFetchTask().execute();
        } else if (getArguments().containsKey("type")) {
            new GetPropertyByTypeTask().execute();
        } else if (getArguments().containsKey("featured_properties")) {
            new DisplayPropertiesTask(getArguments().<HomeResponse.RecentProperty>getParcelableArrayList("featured_properties")).execute();
        } else if (getArguments().containsKey("recent_properties")) {
            new DisplayPropertiesTask(getArguments().<HomeResponse.RecentProperty>getParcelableArrayList("recent_properties")).execute();
        } else if (getArguments().containsKey("dealer_properties")) {
            new DisplayPropertiesTask(getArguments().<HomeResponse.RecentProperty>getParcelableArrayList("dealer_properties")).execute();
        } else if (getArguments().containsKey("place_name")) {
            new GetPropertyByPlaceTask().execute();
        } else if (getArguments().containsKey("filter_request")) {
            new PropertyListByFilterTask().execute();
        }else if (getArguments().containsKey("search_key")) {
            new SearchByNameTask().execute();
        }

    }

    private void setListeners() {

        btnMap.setOnClickListener(this);
        btnFilter.setOnClickListener(this);
        btnSort.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btnMap:

                if (mPropertyList != null && mPropertyList.size() > 0) {

                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("properties", (ArrayList<? extends Parcelable>) mPropertyList);

                    new Utility().moveToActivity(getActivity(), MapActivity.class, bundle);
                    getActivity().overridePendingTransition(R.anim.slide_in_up, R.anim.stay);
                }

                break;

            case R.id.btnFilter:

                if(getArguments().containsKey("filter_request")){
                    getActivity().finish();
                }else{
                    new Utility().moveToActivity(getActivity(), FilterActivity.class, null);
                }

                break;

            case R.id.btnSort:
                Dialog dialog = new Utility().onCreateDialogSingleChoice(getActivity(), new SortOptionSelectionCallback() {
                    @Override
                    public void onSortOptionSelected(int choicePosition) {

                        currentSortSelection = choicePosition;

                        if(mPropertyAdapter!=null&&mPropertyAdapter.getAddedItems()!=null&&mPropertyAdapter.getAddedItems().size()>0){
                            new SortPropertiesTask(mPropertyAdapter.getAddedItems()).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        }

                    }
                });

                dialog.show();
                break;
        }
    }

    private class PropertyListDataFetchTask extends AsyncTask<Void, Void, Integer> {

        private String errorMessage;
        private GetCategoryPropertyResponse getCategoryPropertyResponse;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            rlProgressHolder.setVisibility(View.VISIBLE);
            mPropertyList.clear();
        }

        @Override
        protected Integer doInBackground(Void... params) {


            int statusCode = 0;

            if (!(isAdded() && getActivity() != null)) {
                return statusCode;
            }

            if (new Utility().isNetworkConnected(getActivity())) {

                GetCategoryPropertyRequest getCategoryPropertyRequest = new GetCategoryPropertyRequest();
                getCategoryPropertyRequest.setSubCategoryId(getArguments().getString("subcategory_id"));

                if(AppState.getInstance().getUserId()!=null){
                    getCategoryPropertyRequest.setUserId(Integer.parseInt(AppState.getInstance().getUserId()));
                }

                Response<GetCategoryPropertyResponse> response = WebServiceManager.getInstance().getPropertiesByCategory(getCategoryPropertyRequest);

                if (response != null && response.isSuccessful() && response.raw().code() == 200) {

                    getCategoryPropertyResponse = response.body();

                    if (getCategoryPropertyResponse != null) {

                        if (getCategoryPropertyResponse.getIsSuccess() == 1 && getCategoryPropertyResponse.getPropertyList()!=null&& getCategoryPropertyResponse.getPropertyList().size() > 0) {

                            if (getCategoryPropertyResponse.getPropertyList() != null && getCategoryPropertyResponse.getPropertyList().size() > 0) {

                                for (PropertyList propertyList : getCategoryPropertyResponse.getPropertyList()) {

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
                                    property.setPrice(propertyList.getPrice());
                                    property.setImage(propertyList.getImage());
                                    property.setLatLng(new LatLng(Double.valueOf(propertyList.getLat()), Double.valueOf(propertyList.getLong())));
                                    property.setDescription(propertyList.getDescription());
                                    property.setFavourite(propertyList.getFavourite());

                                    mPropertyList.add(property);
                                }
                            }

                            statusCode = Codes.SUCCESS;

                        } else if (getCategoryPropertyResponse.getIsSuccess() == 1 && (getCategoryPropertyResponse.getPropertyList()==null||getCategoryPropertyResponse.getPropertyList().size() == 0)) {

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

            if (isAdded()) {

                rlProgressHolder.setVisibility(View.GONE);

                if (result == Codes.SUCCESS) {

                    mPropertyAdapter.addItems(mPropertyList);
                    mPropertyAdapter.notifyDataSetChanged();

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


    private class GetPropertyByTypeTask extends AsyncTask<Void, Void, Integer> {

        private String errorMessage;
        private GetPropertiesByTypeResponse getPropertiesByType;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            rlProgressHolder.setVisibility(View.VISIBLE);
            mPropertyList.clear();
        }

        @Override
        protected Integer doInBackground(Void... params) {


            int statusCode = 0;

            if (new Utility().isNetworkConnected(getActivity())) {

                GetPropertyByTypeRequest getPropertyByTypeRequest = new GetPropertyByTypeRequest();
                getPropertyByTypeRequest.setSelectedType(getArguments().getInt("type"));
                getPropertyByTypeRequest.setCurrentPageno(1);
                getPropertyByTypeRequest.setPerPage(50);

                if(AppState.getInstance().getUserId()!=null){
                    getPropertyByTypeRequest.setUserId(Integer.parseInt(AppState.getInstance().getUserId()));
                }


                Response<GetPropertiesByTypeResponse> response = WebServiceManager.getInstance().getPropertiesByType(getPropertyByTypeRequest);

                if (response != null && response.isSuccessful() && response.raw().code() == 200) {

                    getPropertiesByType = response.body();

                    if (getPropertiesByType != null) {

                        if (getPropertiesByType.getIsSuccess() == 1 &&getPropertiesByType.getPropertyList()!=null&&getPropertiesByType.getPropertyList().size() > 0) {

                            if (getPropertiesByType.getPropertyList() != null && getPropertiesByType.getPropertyList().size() > 0) {

                                for (PropertyList propertyList : getPropertiesByType.getPropertyList()) {

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
                                    property.setPrice(propertyList.getPrice());
                                    property.setImage(propertyList.getImage());
                                    property.setLatLng(new LatLng(Double.valueOf(propertyList.getLat()), Double.valueOf(propertyList.getLong())));
                                    property.setDescription(propertyList.getDescription());
                                    property.setFavourite(propertyList.getFavourite());

                                    mPropertyList.add(property);
                                }
                            }

                            statusCode = Codes.SUCCESS;

                        } else if (getPropertiesByType.getIsSuccess() == 1 && (getPropertiesByType.getPropertyList()==null||getPropertiesByType.getPropertyList().size() == 0)) {

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

                mPropertyAdapter.addItems(mPropertyList);
                mPropertyAdapter.notifyDataSetChanged();

            } else {

                if (errorMessage != null && errorMessage.length() > 0) {
                    new Utility().showMessageAlertDialog(getActivity(), errorMessage);
                } else {
                    new Utility().showMessageAlertDialog(getActivity(), new Utility().getErrorMessage(getActivity(), result));
                }

            }


        }
    }


    private class GetPropertyByPlaceTask extends AsyncTask<Void, Void, Integer> {

        private String errorMessage;
        private GetPropertyByPlaceResponse getPropertiesByType;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            rlProgressHolder.setVisibility(View.VISIBLE);
            mPropertyList.clear();
        }

        @Override
        protected Integer doInBackground(Void... params) {


            int statusCode = 0;

            if (new Utility().isNetworkConnected(getActivity())) {

                GetPropertyByPlaceRequest getPropertyByPlaceRequest = new GetPropertyByPlaceRequest();
                getPropertyByPlaceRequest.setPlace(getArguments().getString("place_name"));

                if(AppState.getInstance().getUserId()!=null){
                    getPropertyByPlaceRequest.setUserId(Integer.parseInt(AppState.getInstance().getUserId()));
                }


                Response<GetPropertyByPlaceResponse> response = WebServiceManager.getInstance().getPropertyByPlace(getPropertyByPlaceRequest);

                if (response != null && response.isSuccessful() && response.raw().code() == 200) {

                    getPropertiesByType = response.body();

                    if (getPropertiesByType != null) {

                        if (getPropertiesByType.getIsSuccess() == 1 && getPropertiesByType.getPropertyList()!=null&&getPropertiesByType.getPropertyList().size() > 0) {

                            if (getPropertiesByType.getPropertyList() != null && getPropertiesByType.getPropertyList().size() > 0) {

                                for (PropertyList propertyList : getPropertiesByType.getPropertyList()) {

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
                                    property.setPrice(propertyList.getPrice());
                                    property.setImage(propertyList.getImage());
                                    property.setLatLng(new LatLng(Double.valueOf(propertyList.getLat()), Double.valueOf(propertyList.getLong())));
                                    property.setDescription(propertyList.getDescription());
                                    property.setFavourite(propertyList.getFavourite());

                                    mPropertyList.add(property);
                                }
                            }

                            statusCode = Codes.SUCCESS;

                        } else if (getPropertiesByType.getIsSuccess() == 1 && (getPropertiesByType.getPropertyList()==null||getPropertiesByType.getPropertyList().size() == 0)) {

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

                mPropertyAdapter.addItems(mPropertyList);
                mPropertyAdapter.notifyDataSetChanged();

            } else {

                if (errorMessage != null && errorMessage.length() > 0) {
                    new Utility().showMessageAlertDialog(getActivity(), errorMessage);
                } else {
                    new Utility().showMessageAlertDialog(getActivity(), new Utility().getErrorMessage(getActivity(), result));
                }

            }


        }
    }


    private class DisplayPropertiesTask extends AsyncTask<Void, Void, Void> {

        private ArrayList<HomeResponse.RecentProperty> recentProperties;


        public DisplayPropertiesTask(ArrayList<HomeResponse.RecentProperty> recentProperties) {

            this.recentProperties = recentProperties;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            rlProgressHolder.setVisibility(View.VISIBLE);
            mPropertyList.clear();
        }

        @Override
        protected Void doInBackground(Void... params) {


            if (recentProperties != null && recentProperties.size() > 0) {

                if (!getArguments().containsKey("dealer_properties")) {

                    for (HomeResponse.RecentProperty propertyList : recentProperties) {

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
                        property.setPrice(propertyList.getPrice());
                        property.setImage(propertyList.getImage());
                        property.setLatLng(new LatLng(Double.valueOf(propertyList.getLat()), Double.valueOf(propertyList.getLong())));
                        property.setDescription("");
                        property.setFavourite(propertyList.getFavourite());

                        mPropertyList.add(property);
                    }

                } else {
                    mPropertyList = getArguments().getParcelableArrayList("dealer_properties");
                }

            }

            return null;

        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            rlProgressHolder.setVisibility(View.GONE);

            mPropertyAdapter.addItems(mPropertyList);
            mPropertyAdapter.notifyDataSetChanged();


        }
    }

    private class PropertyListByFilterTask extends AsyncTask<Void, Void, Integer> {

        private String errorMessage;
        private FilterSearchRequest filterSearchRequest;
        private FilterSearchResponse filterSearchResponse;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            rlProgressHolder.setVisibility(View.VISIBLE);
            mPropertyList.clear();

            filterSearchRequest=(FilterSearchRequest) getArguments().getParcelable("filter_request");
        }

        @Override
        protected Integer doInBackground(Void... params) {


            int statusCode = 0;

            if (!(isAdded() && getActivity() != null)) {
                return statusCode;
            }

            if (new Utility().isNetworkConnected(getActivity())) {

                if(AppState.getInstance().getUserId()!=null){
                    filterSearchRequest.setUserId(Integer.parseInt(AppState.getInstance().getUserId()));
                }

                Response<FilterSearchResponse> response = WebServiceManager.getInstance().getFilterSearchResult(filterSearchRequest);

                if (response != null && response.isSuccessful() && response.raw().code() == 200) {

                    filterSearchResponse = response.body();

                    if (filterSearchResponse != null) {

                        if (filterSearchResponse.getIsSuccess() == 1) {

                            if (filterSearchResponse.getPropertyList() != null && filterSearchResponse.getPropertyList()!=null&&filterSearchResponse.getPropertyList().size() > 0) {

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
                                    property.setPrice(propertyList.getPrice());
                                    property.setImage(propertyList.getImage());
                                    property.setLatLng(new LatLng(Double.valueOf(propertyList.getLat()), Double.valueOf(propertyList.getLong())));
                                    property.setDescription(propertyList.getDescription());
                                    property.setFavourite(propertyList.getFavourite());

                                    mPropertyList.add(property);
                                }
                            }

                            statusCode = Codes.SUCCESS;

                        } else if (filterSearchResponse.getIsSuccess() == 1 && (filterSearchResponse.getPropertyList()==null||filterSearchResponse.getPropertyList().size() == 0)) {

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

            if (isAdded()) {

                rlProgressHolder.setVisibility(View.GONE);

                if (result == Codes.SUCCESS) {

                    mPropertyAdapter.addItems(mPropertyList);
                    mPropertyAdapter.notifyDataSetChanged();

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


    private class SortPropertiesTask extends AsyncTask<Void, Void, Void> {

        private ArrayList<Property> properties;


        public SortPropertiesTask(ArrayList<Property> properties) {

            this.properties = properties;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            rlProgressHolder.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {


            if (properties != null && properties.size() > 0) {

                switch (currentSortSelection){

                    case 0:

                        Collections.sort(properties, new Comparator<Property>(){
                            public int compare(Property obj1, Property obj2) {

                                 return Integer.valueOf(obj1.getPrice()).compareTo(Integer.valueOf(obj2.getPrice()));
                            }
                        });

                        break;

                    case 1:

                        Collections.sort(properties, new Comparator<Property>(){
                            public int compare(Property obj1, Property obj2) {

                                return Integer.valueOf(obj2.getPrice()).compareTo(Integer.valueOf(obj1.getPrice()));
                            }
                        });

                        break;

                    case 2:

                        Collections.sort(properties, new Comparator<Property>(){
                            public int compare(Property obj1, Property obj2) {

                                return Integer.valueOf(obj1.getArea()).compareTo(obj2.getArea());
                            }
                        });

                        break;

                    case 3:


                        Collections.sort(properties, new Comparator<Property>(){
                            public int compare(Property obj1, Property obj2) {

                                return Integer.valueOf(obj2.getArea()).compareTo(obj1.getArea());
                            }
                        });

                        break;
                }

            }

            return null;

        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            rlProgressHolder.setVisibility(View.GONE);

            mPropertyAdapter.addItems(properties);
            mPropertyAdapter.notifyDataSetChanged();


        }
    }

    private class SearchByNameTask extends AsyncTask<Void, Void, Integer> {


        private String errorMessage;
        private SearchByNameResponse searchByNameResponse;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            rlProgressHolder.setVisibility(View.VISIBLE);
            mPropertyList.clear();
        }

        @Override
        protected Integer doInBackground(Void... params) {


            int statusCode = 0;

            if (new Utility().isNetworkConnected(getActivity())) {

                SearchByNameRequest searchByNameRequest = new SearchByNameRequest();

                if(AppState.getInstance().getUserId()!=null){
                    searchByNameRequest.setUserId(AppState.getInstance().getUserId());
                }

                searchByNameRequest.setKeyword(getActivity().getIntent().getExtras().getString("search_key"));

                Response<SearchByNameResponse> response = WebServiceManager.getInstance().searchByName(searchByNameRequest);

                if (response != null && response.isSuccessful() && response.raw().code() == 200) {

                    searchByNameResponse = response.body();

                    if (searchByNameResponse != null) {

                        if (searchByNameResponse.getIsSuccess() == 1 && searchByNameResponse.getPropertyList()!=null&&searchByNameResponse.getPropertyList().size()>0) {

                            for (PropertyList propertyList : searchByNameResponse.getPropertyList()) {

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
                                property.setPrice(propertyList.getPrice());
                                property.setImage(propertyList.getImage());
                                property.setLatLng(new LatLng(Double.valueOf(propertyList.getLat()), Double.valueOf(propertyList.getLong())));
                                property.setDescription(propertyList.getDescription());
                                property.setFavourite(propertyList.getFavourite());

                                mPropertyList.add(property);
                            }

                            statusCode = Codes.SUCCESS;

                        } else if (searchByNameResponse.getIsSuccess() == 1 && (searchByNameResponse.getPropertyList() ==null || searchByNameResponse.getPropertyList().size()==0)){

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

            if(isAdded()){

                rlProgressHolder.setVisibility(View.GONE);

                if (result == Codes.SUCCESS) {

                    mPropertyAdapter.addItems(mPropertyList);
                    mPropertyAdapter.notifyDataSetChanged();

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

}
