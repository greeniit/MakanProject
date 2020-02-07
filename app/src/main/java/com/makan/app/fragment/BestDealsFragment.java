package com.makan.app.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.Preference;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.model.LatLng;
import com.makan.R;
import com.makan.app.adapter.AdvertisementAdapter;
import com.makan.app.adapter.BestDealAdapter;
import com.makan.app.app.WebConstant;
import com.makan.app.core.Codes;
import com.makan.app.model.Property;
import com.makan.app.preference.PrefKey;
import com.makan.app.preference.PreferenceManager;
import com.makan.app.util.Utility;
import com.makan.app.web.WebServiceManager;
import com.makan.app.web.pojo.AdvertisementResponse;
import com.makan.app.web.pojo.FindDealsResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Response;


public class BestDealsFragment extends BaseFragment {

    private RecyclerView rvBestDeals,rvDealer;
    public com.makan.app.preference.PreferenceManager preferenceManager;

    @BindView(R.id.rlProgressHolder)
    RelativeLayout rlProgressHolder;

    private List<Property> mBestDeals;
    private List<AdvertisementResponse.Advertisement> advertisements;
    private BestDealAdapter mBestDealsAdapter;
    private AdvertisementAdapter advertisementAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBestDeals = new ArrayList<>();
        advertisements=new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_best_deals, container, false);
        ButterKnife.bind(this,rootView);
        initialiseComponents(rootView);
        setRecycleView();
        preparePropertyList();
        return rootView;
    }

    private void initialiseComponents(View rootView) {

        preferenceManager = new PreferenceManager();
        rvBestDeals=(RecyclerView)rootView.findViewById(R.id.rvBestDeals);
        rvDealer=(RecyclerView)rootView.findViewById(R.id.rvDealer);
    }

    private void setRecycleView() {

        mBestDealsAdapter = new BestDealAdapter(getActivity(), mBestDeals);
        advertisementAdapter = new AdvertisementAdapter(getActivity(), advertisements);
        rvBestDeals.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rvDealer.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rvBestDeals.setAdapter(mBestDealsAdapter);
        rvDealer.setAdapter(advertisementAdapter);
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
        mBestDeals.add(property);

        property=new Property();
        property.setThumbnail(featuredProperty[1]);
        mBestDeals.add(property);

        property=new Property();
        property.setThumbnail(featuredProperty[2]);
        mBestDeals.add(property);

        property=new Property();
        property.setThumbnail(featuredProperty[3]);
        mBestDeals.add(property);

        mBestDealsAdapter.notifyDataSetChanged();
        advertisementAdapter.notifyDataSetChanged();

        rvBestDeals.smoothScrollToPosition(0);
        rvDealer.smoothScrollToPosition(0);*/

        new GetBestDeals().execute();
    }


    private class GetBestDeals extends AsyncTask<Void, Void, Integer> {

        private String errorMessage;
        private FindDealsResponse findDealsResponse;
        private AdvertisementResponse advertisementResponse;
        private BestDealsRequest bestDealsRequest;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            rlProgressHolder.setVisibility(View.VISIBLE);
        }

        @Override
        protected Integer doInBackground(Void... params) {


            int statusCode = 0;

            if (new Utility().isNetworkConnected(getActivity())) {


                bestDealsRequest = new BestDealsRequest();
                bestDealsRequest.setLanguage(preferenceManager.getValue(getActivity(), PrefKey.CURRENT_DATA));
                Response<FindDealsResponse> response = WebServiceManager.getInstance().findDeals(bestDealsRequest);

                if (response != null && response.isSuccessful() && response.raw().code() == 200) {

                    findDealsResponse = response.body();

                    if (findDealsResponse != null) {

                        if (findDealsResponse.getIsSuccess() == 1 && findDealsResponse.getOfferProperty()!=null&& findDealsResponse.getOfferProperty().size()>0) {

                            if(findDealsResponse.getOfferProperty()!=null&& findDealsResponse.getOfferProperty().size()>0){

                                mBestDeals.clear();

                                for (FindDealsResponse.OfferProperty offerProperty: findDealsResponse.getOfferProperty()){

                                    Property property=new Property();

                                    property.setId(Integer.parseInt(offerProperty.getPropertyId()));
                                    property.setTitle(offerProperty.getPropertyName());
                                    property.setAddress(offerProperty.getLocation());
                                    property.setPropertyType(offerProperty.getSubCategoryName());

//                                    if(offerProperty.getRooms()!=null&&offerProperty.getRooms().length()>0) {
//                                        property.setBedCount(Integer.parseInt(offerProperty.getRooms()));
//                                    }

                                    if(offerProperty.getBuildingArea()!=null&&offerProperty.getBuildingArea().length()>0) {
                                        property.setArea(Integer.parseInt(offerProperty.getBuildingArea()));
                                    }

                                    property.setPrice(Float.valueOf(offerProperty.getPrice()));
                                    property.setImage(offerProperty.getImage());
                                    property.setLatLng(new LatLng(Double.valueOf(offerProperty.getLat()),Double.valueOf(offerProperty.getLong())));
                                    property.setDescription("");
                                    property.setFavourite(offerProperty.getFavourite());
                                    if (offerProperty.getBedcount().equals(null)||offerProperty.getBedcount()==null){
                                        property.setBedCount(0);
                                    }else {
                                        property.setBedCount(Integer.parseInt(offerProperty.getBedcount()));
                                    }

                                    if (offerProperty.getBathroomcount().equals(null)||offerProperty.getBathroomcount()==null){
                                        property.setBathCount(0);

                                    }else {
                                        property.setBathCount(Integer.parseInt(offerProperty.getBathroomcount()));

                                    }




                                    float discount=Float.parseFloat(offerProperty.getPrice())*(Float.parseFloat(offerProperty.getOffer()));

                                    discount=discount/100;

                                    float newPrice=Float.parseFloat(offerProperty.getPrice())-discount;

                                    property.setOfferPrice(String.valueOf(newPrice));
                                    property.setOfferPercentage(offerProperty.getOffer());

                                    mBestDeals.add(property);
                                }
                            }

                            statusCode = Codes.SUCCESS;

                        } else if (findDealsResponse.getIsSuccess() == 1 && (findDealsResponse.getOfferProperty()==null||findDealsResponse.getOfferProperty().size()==0)){

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



            if(statusCode== Codes.SUCCESS){

                if (new Utility().isNetworkConnected(getActivity())) {
                    bestDealsRequest.setLanguage(new PreferenceManager().getValue(getActivity(), PrefKey.CURRENT_DATA));
                    Response<AdvertisementResponse> response = WebServiceManager.getInstance().getAdds(bestDealsRequest);

                    if (response != null && response.isSuccessful() && response.raw().code() == 200) {

                        advertisementResponse = response.body();

                        if (advertisementResponse != null) {

                            if (advertisementResponse.getIsSuccess() == 1 && advertisementResponse!=null&& advertisementResponse.getAdds().size()>0) {

                                if(advertisementResponse.getAdds()!=null&& advertisementResponse.getAdds().size()>0){

                                    advertisements.clear();

                                    advertisements.addAll(advertisementResponse.getAdds());
                                }

                                statusCode = Codes.SUCCESS;

                            } else if (advertisementResponse.getIsSuccess() == 1 && (advertisementResponse.getAdds()==null||advertisementResponse.getAdds().size()==0)){

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
            }


            return statusCode;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);

            rlProgressHolder.setVisibility(View.GONE);

            if (result == Codes.SUCCESS) {
                advertisementAdapter.notifyDataSetChanged();
                mBestDealsAdapter.notifyDataSetChanged();
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
