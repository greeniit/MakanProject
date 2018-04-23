package com.makan.app.task;

import android.content.Context;
import android.os.AsyncTask;

import com.makan.app.app.AppState;
import com.makan.app.callback.WishListAddDeleteOperationCallback;
import com.makan.app.core.Codes;
import com.makan.app.util.Utility;
import com.makan.app.web.WebServiceManager;
import com.makan.app.web.pojo.WishListOperationRequest;
import com.makan.app.web.pojo.WishListOperationResponse;

import retrofit2.Response;

public class WishListAddDeleteOperationTask extends AsyncTask<Void, Void, Integer> {

    private Context context;
    private String propertyId;
    private boolean isAddToWishListRequest;
    private WishListOperationResponse wishListOperationResponse;
    private WishListAddDeleteOperationCallback wishListAddDeleteOperationCallback;


    public WishListAddDeleteOperationTask(Context context, String propertyId,boolean isAddToWishListRequest, WishListAddDeleteOperationCallback wishListAddDeleteOperationCallback){

        this.context=context;
        this.propertyId=propertyId;
        this.isAddToWishListRequest=isAddToWishListRequest;
        this.wishListAddDeleteOperationCallback = wishListAddDeleteOperationCallback;
    }

    @Override
    protected Integer doInBackground(Void... params) {


        int statusCode = 0;

        if (new Utility().isNetworkConnected(context)) {

            WishListOperationRequest wishListOperationRequest=new WishListOperationRequest();
            wishListOperationRequest.setUserId(AppState.getInstance().getUserId());
            wishListOperationRequest.setPropertyId(propertyId);

            Response<WishListOperationResponse> response;

            if(isAddToWishListRequest){
                response=WebServiceManager.getInstance().addToWishList(wishListOperationRequest);
            }else{
                response=WebServiceManager.getInstance().deleteFromWishList(wishListOperationRequest);
            }


            if (response != null && response.isSuccessful() && response.raw().code() == 200) {

                wishListOperationResponse = response.body();

                if (wishListOperationResponse != null) {

                    if (wishListOperationResponse.getIsSuccess() == 1) {

                        statusCode = Codes.SUCCESS;

                    }  else {
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
            wishListAddDeleteOperationCallback.AddToWishListTaskSuccess();
        } else {

            wishListAddDeleteOperationCallback.AddToWishListTaskFailure("");

        }


    }
}
