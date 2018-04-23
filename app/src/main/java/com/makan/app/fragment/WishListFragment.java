package com.makan.app.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.makan.R;
import com.makan.app.activity.FilterActivity;
import com.makan.app.activity.MapActivity;
import com.makan.app.adapter.PropertyListAdapter;
import com.makan.app.app.AppState;
import com.makan.app.callback.PropertyAdapterWishListOperationCallback;
import com.makan.app.callback.WishListAddDeleteOperationCallback;
import com.makan.app.core.Codes;
import com.makan.app.model.Property;
import com.makan.app.task.WishListAddDeleteOperationTask;
import com.makan.app.util.Utility;
import com.makan.app.web.WebServiceManager;
import com.makan.app.web.pojo.WishListRequest;
import com.makan.app.web.pojo.WishListResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Response;

public class WishListFragment extends BaseFragment implements View.OnClickListener,PropertyAdapterWishListOperationCallback{

    private RecyclerView rvProperty;
    private Button btnFilter,btnMap;
    private List<Property> mPropertyList;
    private PropertyListAdapter mPropertyAdapter;

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
        ButterKnife.bind(this,rootView);
        initialiseComponents(rootView);
        setRecycleView();
        preparePropertyList();
        setListeners();
        return rootView;
    }

    private void initialiseComponents(View rootView) {
        btnFilter=(Button)rootView.findViewById(R.id.btnFilter);
        btnMap=(Button)rootView.findViewById(R.id.btnMap);
        rvProperty=(RecyclerView)rootView.findViewById(R.id.rvProperty);
    }

    private void setRecycleView() {

        mPropertyAdapter = new PropertyListAdapter(getActivity(), mPropertyList,PropertyListAdapter.STYLE_VERTICAL_LIST_CELL,this);
        rvProperty.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rvProperty.setAdapter(mPropertyAdapter);
    }

    /**
     * Adding few properties for testing
     */
    private void preparePropertyList() {

   /*     int[] featuredProperty = new int[]{
                R.drawable.image_1,
                R.drawable.image_2,
                R.drawable.image_3,
                R.drawable.image_4};

        Property property=new Property();

        property.setThumbnail(featuredProperty[0]);
        mPropertyList.add(property);

        property=new Property();
        property.setThumbnail(featuredProperty[1]);
        mPropertyList.add(property);

        property=new Property();
        property.setThumbnail(featuredProperty[2]);
        mPropertyList.add(property);

        property=new Property();
        property.setThumbnail(featuredProperty[3]);
        mPropertyList.add(property);

        mPropertyAdapter.notifyDataSetChanged();*/

       new WishListDataFetchTask().execute();
    }

    private void setListeners(){

        btnMap.setOnClickListener(this);
        btnFilter.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btnMap:
                new Utility().moveToActivity(getActivity(), MapActivity.class,null);
                getActivity().overridePendingTransition( R.anim.slide_in_up, R.anim.stay);
                break;

            case R.id.btnFilter:
                new Utility().moveToActivity(getActivity(), FilterActivity.class,null);
                break;
        }
    }

    @Override
    public void onWishListClicked(final Property property, final int pos) {

        showProgressDialog();
        new WishListAddDeleteOperationTask(getActivity(), String.valueOf(property.getId()), !property.isAddedToWishList(), new WishListAddDeleteOperationCallback() {
            @Override
            public void AddToWishListTaskSuccess() {

                dismissProgressDialog();

                property.setAddedToWishList(!property.isAddedToWishList());
                mPropertyAdapter.updateItem(property,pos);

            }

            @Override
            public void AddToWishListTaskFailure(String errorMessage) {

                if (errorMessage != null && errorMessage.length() > 0) {
                    new Utility().showMessageAlertDialog(getActivity(), errorMessage);
                }

                dismissProgressDialog();
            }

        }).execute();
    }

    private class WishListDataFetchTask extends AsyncTask<Void, Void, Integer> {

        private String errorMessage;
        private WishListResponse wishListResponse;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            rlProgressHolder.setVisibility(View.VISIBLE);
        }

        @Override
        protected Integer doInBackground(Void... params) {


            int statusCode = 0;

            if (new Utility().isNetworkConnected(getActivity())) {

                WishListRequest wishListRequest=new WishListRequest();
                wishListRequest.setUserId(AppState.getInstance().getUserId());

                Response<WishListResponse> response = WebServiceManager.getInstance().getWishList(wishListRequest);

                if (response != null && response.isSuccessful() && response.raw().code() == 200) {

                    wishListResponse = response.body();

                    if (wishListResponse != null) {

                        if (wishListResponse.getIsSuccess() == 1 && wishListResponse.getPropertyList().size()>0) {

                            if(wishListResponse.getPropertyList()!=null&&wishListResponse.getPropertyList().size()>0){

                                for (WishListResponse.PropertyList propertyList:wishListResponse.getPropertyList()){

                                    Property property=new Property();

                                    property.setId(Integer.parseInt(propertyList.getPropertyId()));
                                    property.setTitle(propertyList.getPropertyName());
                                    property.setAddress(propertyList.getLocation());
                                    property.setBedCount(Integer.parseInt(propertyList.getRooms()));
                                    property.setArea(Integer.parseInt(propertyList.getBuildingArea()));
                                    property.setPrice(propertyList.getPrice());
                                    property.setImage(propertyList.getImage());
                                    property.setDescription(propertyList.getDescription());
                                    property.setAddedToWishList(true);

                                    mPropertyList.add(property);
                                }
                            }

                            statusCode = Codes.SUCCESS;

                        } else if (wishListResponse.getIsSuccess() == 1 && wishListResponse.getPropertyList().size()==0){

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
}
